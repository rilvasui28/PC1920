import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class Client {
    
    private String host;
    private int port;
    public Socket socket;
    public BufferedReader in;
    public PrintWriter out;
    public String username;
    private String password;

    public Client(String host, int port){
        this.port = port;
        this.host = host;
    }

    public void start(){
        try{
            socket = new Socket(this.host, this.port);

            BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            System.out.println("Choose an option!\n1. Register\n2. Log in");
            String option = systemIn.readLine();
            String response;
            while(!option.equals("1") && !option.equals("2")){
                System.out.println("Please, pick an available option!\n1. Register\n2. Log in");
                option = systemIn.readLine();
            }
            out.println(option);
            out.flush();
            //Ja temos uma opção!
            if(option.equals("1")){
                System.out.println("Choose an username:");
                this.username = systemIn.readLine();
                out.println(username);
                out.flush();
                System.out.println("Choose an password:");
                this.password = systemIn.readLine();
                out.println(password);
                out.flush();
                while((response = in.readLine()) != null && (!response.equals("User registered"))){
                    System.out.println("Username is already being used! Please choose another!");
                    System.out.println("Choose an username:");
                    this.username = systemIn.readLine();
                    out.println(username);
                    out.flush();
                    System.out.println("Choose an password:");
                    this.password = systemIn.readLine();
                    out.println(password);
                    out.flush();
                }
                System.out.println("User successfully registered!");
                System.out.println("Now please login using your credentials!");
                option = "2";
            }
            if(option.equals("2")){
                System.out.println("Username:");
                this.username = systemIn.readLine();
                out.println(username);
                out.flush();
                System.out.println("Password:");
                this.password = systemIn.readLine();
                out.println(password);
                out.flush();
                while((response = in.readLine()) != null && (!response.equals("User logged"))){
                    System.out.println("Username or Password are incorrect! Please try again.");
                    System.out.println("Username:");
                    this.username = systemIn.readLine();
                    out.println(username);
                    out.flush();
                    System.out.println("Password:");
                    this.password = systemIn.readLine();
                    out.println(password);
                    out.flush();
                }
                System.out.println("User successfully loged in!");

                Thread listener = new Thread(new ClientListener(in));
                listener.start();

                String userInput;
                while((userInput = systemIn.readLine()) != null && (!userInput.equals("quit"))){
                    out.println(userInput);
                    out.flush();
                }

                systemIn.close();
                socket.shutdownOutput();
                socket.shutdownInput();
                socket.close();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client cl = new Client("127.0.0.1", 12345);
        cl.start();
    }
}

class ClientListener implements Runnable{

    public BufferedReader in;

    public ClientListener(BufferedReader in){
        this.in = in;
    }

    public void run(){
        String message;
        try{
            while((message = in.readLine()) != null){
                System.out.println(message);
            }
        }
        catch(SocketException c){}
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}