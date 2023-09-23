package Menu;

import java.io.*;

public interface Menu {
    static Repository repository = new testRepository();
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public void run() throws IOException;
}
