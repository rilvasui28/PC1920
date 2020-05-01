import java.io.*;
import java.net.Socket;

class CWorker implements Runnable{

    private Base base;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    
    public CWorker(Base base, Socket socket) throws IOException {
        this.base = base;
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream());
    }

    public void run(){
        try {
            out.println("Choose an option!\n1. Register\n2.Log in");

            String option = in.readLine();
            String username;
            String password;
            Client client;

            if(option.equals("1")){
                out.println("Choose an username");
                out.flush();
                username = in.readLine();
                out.println("Choose a password");
                out.flush();
                password = in.readLine();
                Client c = new Client(username, password);
                while(!base.register(c, out)){
                    out.println("Username already taken! Please choose another");
                    out.flush();
                    username = in.readLine();
                    c = new Client(username, password);
                }
                out.println("Now please login using your credentials!");
                out.flush();
                option = "2";
            }

            if(option.equals("2")){
                out.println("Username");
                out.flush();
                username = in.readLine();
                out.println("Password");
                out.flush();
                password = in.readLine();
                while(!base.login(username, password)){
                    out.println("Username or Password are incorrect! Please try again.");
                    out.flush();
                    out.println("Username");
                    out.flush();
                    username = in.readLine();
                    out.println("Password");
                    out.flush();
                    password = in.readLine();
                }
                
                client = base.getClient(username);
                client.setStatus(true);

                String pi = in.readLine();
                while(!pi.equals("quit") && pi != null){
                    try {
                        int count = Integer.parseInt(pi);
                        client.setCount(count);
                        int avg = base.calculate();
                        base.multicast(Integer.toString(avg));
                    } catch(Exception e) {
                        System.out.println("Please write a number!");
                        pi = in.readLine();
                    }
                }

                client.setStatus(false);
                socket.shutdownInput();
                socket.shutdownOutput();
                socket.close();
            }

            if((!option.equals("1")) && (!option.equals("2"))){
                out.println("Please, choose option 1 or 2.");
                out.flush();
            } 

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}