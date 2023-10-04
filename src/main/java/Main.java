import client.Client;
import client.ConsoleManager;

public class Main {
    public static void main(String[] args){
        Client client = new Client(new ConsoleManager());
        client.run();
    }
}