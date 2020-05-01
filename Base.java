import java.io.PrintWriter;
import java.util.HashMap;

class Base {
    
    private HashMap<Client, PrintWriter> clients;

    public Base(){
        this.clients = new HashMap<Client, PrintWriter>();
    }

    public int calculate(){
        int sum = 0;
        int nc = 0;

        for(Client client: this.clients.keySet()){
            
            if (client.getCount() >= 0){
                sum+=client.getCount();
                nc++;
            }
        }
        return (sum/(150*nc)*100);
    }

    public Client getClient(String username){
        for(Client client : this.clients.keySet()){
            if (client.getUser().equals(username)){
                return client;
            }
        }
        return null;
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
            
            System.out.println("Client successfully registered with username: " + c.getUser());
            p.println("User registered!");
            p.flush();
            return true;
        }  
        return false;
    }

    public synchronized boolean login(String user, String pass){
        
        for(Client client : this.clients.keySet()){
            if(client.getUser().equals(user) && client.getPass().equals(pass)){

                client.setStatus(true);

                PrintWriter writer = this.clients.get(client);

                System.out.println("Client successfully logged in!");
                writer.println("User registered!");
                writer.flush();

                return true;
            }
        }
        return false;
    }

    public synchronized void multicast(String msg){
        String finalMsg = "The average infection rate is: " + msg;

        for(Client client: this.clients.keySet()){
            if(client.getStatus()){
                PrintWriter pw = this.clients.get(client);

                pw.println(finalMsg);
                pw.flush();
            }
        }
    }
}