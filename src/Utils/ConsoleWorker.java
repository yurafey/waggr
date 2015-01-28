package Utils;


import javax.swing.*;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by yuraf_000 on 25.12.2014.
 */
public class ConsoleWorker extends SwingWorker<Void,String>{


    //    private JLabel outLabel;
//    private JTextArea outTextArea;
    private String logs;
    private String refreshLog;
    private PipedInputStream outPipe = new PipedInputStream();

    public ConsoleWorker(String logs, String refreshLog) {
        this.logs = logs;
        this.refreshLog = refreshLog;
        try {
            System.setOut(new PrintStream(new PipedOutputStream(outPipe), true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Void doInBackground() throws Exception {
        Scanner s = new Scanner(outPipe);
        while (s.hasNextLine()) {
            String line = s.nextLine();
            publish(line);
        }
        return null;
    }

    protected void process(java.util.List<String> chunks) {
        for (String line : chunks) {
            if (line.contains("msg")) {
                if (line.contains("ref")) this.refreshLog = "Идет обновление...";
                else {
                    this.refreshLog = "Обновление через " + line.substring(3) + " мин.";
                }
            }
            else {
                StringBuilder sb = new StringBuilder(this.logs);
                sb.append('\n'+line);
                this.logs = sb.toString();
            }
        }
    }

    public String getRefreshLog() {
        return refreshLog;
    }
    public String getLogs() {
        return logs;
    }

}
