import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Base {
    
    private HashMap<String, PrintWriter> cliprint;
    private HashMap<String, List<String>> clinfo; 

    public Base(){
        this.cliprint = new HashMap<String, PrintWriter>();
        this.clinfo = new HashMap<String, List<String>>();
    }
    
    public void setCount(String username, String count){
        this.clinfo.get(username).set(2,count);
    }

    public void setStatus(String username, String status){
        this.clinfo.get(username).set(1,status);
    }

    public synchronized void calculate(){
        double sum = 0;
        int nc = 0;
        double temp = 0;

        for(String s: this.clinfo.keySet()){
            temp = Double.parseDouble(this.clinfo.get(s).get(2));
            if (temp >= 0){
                sum+=temp;
                nc++;
            }
        }
        multicast(String.valueOf(sum/(150*nc)*100));
    } 

    public synchronized boolean register(String user, String pwd, PrintWriter p){
        if (!this.clinfo.containsKey(user) && !this.cliprint.containsKey(user)){
            this.cliprint.put(user, p);
            
            List<String> info = new ArrayList<String>();
            info.add(pwd);
            info.add("false");
            info.add("0");
            this.clinfo.put(user,info);
           
            System.out.println("Client successfully registered with username: " + user);
            p.println("User registered");
            p.flush();
            
            return true;
        }
        return false;
    }

    public synchronized boolean login(String user, String pass){
        if(this.clinfo.containsKey(user) && this.cliprint.containsKey(user) && this.clinfo.get(user).get(0).equals(pass)){
            PrintWriter writer = this.cliprint.get(user);

            System.out.println("Client successfully logged in!");
            writer.println("User logged");
            writer.flush();

            this.clinfo.get(user).set(1,"true");
            return true;
        }
        return false;
    }

    public synchronized void multicast(String msg){
        String finalMsg = "The average infection rate is: " + msg;

        for(String s : this.clinfo.keySet()){
            if(this.clinfo.get(s).get(1).equals("true")){
                
                PrintWriter pw = this.cliprint.get(s);
                pw.println(finalMsg);
                pw.flush();
            }
        }
    }
}