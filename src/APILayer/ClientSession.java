package APILayer;

import ServiceLayer.JSONBuilderService;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Date;

public class ClientSession implements Runnable {
    private Socket socket;
    private InputStream in = null;
    private OutputStream out = null;

    @Override
    public void run() {
        try {
            String header = readHeader();
            ProviderCityIdContainer providerAndCityIdFromHeader = getProviderAndCityIdFromHeader(header);
            if (providerAndCityIdFromHeader!=null) {
                JSONObject jsonObject = JSONBuilderService.prepareAndGetJSON(providerAndCityIdFromHeader.provider,providerAndCityIdFromHeader.cityId);
                send(jsonObject);
            } else {
                send404();
            }
            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ClientSession(Socket socket) throws IOException {
        this.socket = socket;
        initialize();
    }

    private void initialize() throws IOException {
        in = socket.getInputStream();
        out = socket.getOutputStream();
    }

    private String readHeader() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder builder = new StringBuilder();
        String ln = null;
        while (true) {
            ln = reader.readLine();
            if (ln == null || ln.isEmpty()) {
                break;
            }
            builder.append(ln + System.getProperty("line.separator"));
        }
        return builder.toString();
    }

    private ProviderCityIdContainer getProviderAndCityIdFromHeader(String header) {
        int from = header.indexOf("/")+1;
        int to = header.indexOf("/", from);
        if (from!=-1 && to !=-1) {
            String weatherProvider = header.substring(from, to);
            if (!(weatherProvider.equals("WUA") || weatherProvider.equals("Yandex"))) {
                return null;
            }
            int cityIdEndIndex = header.indexOf(" ", to);

            if (cityIdEndIndex != to + 1) {
                String cityId = header.substring(to + 1, cityIdEndIndex);
                ProviderCityIdContainer providerIdCityId = new ProviderCityIdContainer();
                providerIdCityId.cityId = Integer.parseInt(cityId);
                providerIdCityId.provider = weatherProvider;
                return providerIdCityId;
            }
        }
        return null;
    }

    private void send(JSONObject jsonObject) {
        int code;
        if (jsonObject != null) {
            code = 200;
        } else {
            code = 404;
        }
        String header = getHeader(code); // "UTF-8"
        PrintStream answer = new PrintStream(out, true);
        answer.print(header);
        if (code == 200) {
            byte[] b = jsonObject.toJSONString().getBytes();
            try {
                out.write(b, 0, b.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void send404() throws UnsupportedEncodingException {
        String header = getHeader(404); // "UTF-8"
        PrintStream answer = new PrintStream(out, true);
        answer.print(header);
    }

    private String getHeader(int code) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("HTTP/1.1 " + code + " " + getAnswer(code) + "\n");
        buffer.append("Date: " + new Date().toGMTString() + "\n");
        buffer.append("Accept-Ranges: none\n");
        buffer.append("\n");
        return buffer.toString();
    }

    private String getAnswer(int code) {
        switch (code) {
            case 200:
                return "OK";
            case 404:
                return "Not Found";
            default:
                return "Internal Server Error";
        }
    }
}

class ProviderCityIdContainer {
    int cityId;
    String provider;
}