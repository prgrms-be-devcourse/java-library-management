import client.Client;
import client.ConsoleManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Client client = new Client(new ConsoleManager());
        client.run();
    }
}