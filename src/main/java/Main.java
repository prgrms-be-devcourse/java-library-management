import client.Client;
import client.ConsoleManager;
import client.ExHandler;

public class Main {
    public static void main(String[] args){
        Client client = new Client(new ConsoleManager());
        ExHandler exHandler = new ExHandler(client);
        exHandler.handle();
    }
}