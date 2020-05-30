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
            String option = in.readLine();
            String username;
            String password;

            if(option.equals("1")){
                username = in.readLine();
                password = in.readLine();
                while(!base.register(username, password, out)){
                    System.out.println("Username already taken! Please choose another");
                    out.println("false");
                    out.flush();
                    username = in.readLine();
                    password = in.readLine();
                }
                option = "2";
            }

            if(option.equals("2")){
                username = in.readLine();
                password = in.readLine();
                while(!base.login(username, password, out)){
                    out.println("false");
                    out.flush();
                    username = in.readLine();
                    password = in.readLine();
                }
                String pi = in.readLine();
                while(pi != null){
                    try {
                        int num = Integer.parseInt(pi);
                        if (num <= 150 && num >= 0){
                            base.setCount(username, pi);
                            base.calculate();
                        } else {
                            out.println("Please write a number between 1 and 150");
                            out.flush();
                        }
                        pi = in.readLine();
                        continue;
                    } catch(Exception e) {
                        out.println("Please write a number between 1 and 150");
                        out.flush();
                        pi = in.readLine();
                        continue;
                    } 
                }
                System.out.println("User " + username + " Disconnected");
                base.setStatus(username, "false");
                socket.shutdownOutput();
                socket.shutdownInput();
                socket.close();
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}