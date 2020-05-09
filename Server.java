import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Server {
    
    private ServerSocket serverSocket;
    private int port;
    private Base base;

    public Server(int port){
        this.port = port;
        this.base = new Base();
    }

    public void start(){
        try {
            System.out.println("------ SERVER IS WORKING ------");
            this.serverSocket = new ServerSocket(this.port);

            while(true){
                System.out.println("Waiting for new connections");
                Socket socket = serverSocket.accept();
                System.out.println("A client entered the chat");
                new Thread(new CWorker(this.base, socket)).start();
            }
            
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException{
        Server server = new Server(12345);
        server.start();
    }
}