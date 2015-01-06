package Utils;

import javax.swing.*;
import java.io.PipedInputStream;
import java.util.Scanner;

/**
 * Created by yuraf_000 on 25.12.2014.
 */
public class ConsoleWorker extends SwingWorker<Void,String>{
    private Object outLabel;
    private Object outTextArea;
    private PipedInputStream outPipe;

    public ConsoleWorker(Object outLabel,Object outTextArea, PipedInputStream outPipe) {
        this.outLabel = outLabel;
        this.outTextArea = outTextArea;
        this.outPipe=outPipe;
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
                if (line.contains("ref")) ((JLabel)outLabel).setText("Идет обновление...");
                else {
                    ((JLabel)outLabel).setText("Обновление через " + line.substring(3) + " мин.");
                }
            }
            else ((JTextArea)outTextArea).append('\n'+line);
        }
    }
}
