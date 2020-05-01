import java.io.PrintWriter;
import java.util.HashMap;

class Base {
    
    private HashMap<Client, PrintWriter> clients;

    public Base(){
        this.clients = new HashMap<Client, PrintWriter>();
    }

    private boolean check(Client c){
        for(Client client : this.clients.keySet()){
            if (client.getUser().equals(c.getUser())){
                return true;
            }
        }
        return false;
    }

    public synchronized boolean register(Client c, PrintWriter p){
        if(!check(c)){
            this.clients.put(c, p);
            System.out.println("Client registered with username: " + c.)
        }
    }

}