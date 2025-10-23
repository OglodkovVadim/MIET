package Lab4;

import java.io.FileWriter;
import java.io.IOException;

public class LogWriter {
    protected Boolean status;
    public LogWriter(Boolean status) {
        this.status = status;
    }

    public void SetStatus(Boolean status) {
        this.status = status;
    }

    public void WriteLog(String data) throws IOException {
        if (status) {
            FileWriter fw = new FileWriter("D:\\Programming\\Java\\Lab4\\Resourse\\log.txt", true);
            fw.write(data);
            fw.close();
        }
    }

}
