package client;

public class ExHandler {

    private final Client client;

    public ExHandler(Client client){
        this.client = client;
    }

    public void handle(){
        while(true){
            try{
                client.run();
            }catch (RuntimeException e){
                System.out.println(e.getMessage());
            }
        }
    }

}
