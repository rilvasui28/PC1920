import java.io.*;
import java.util.HashMap;
import java.util.List;
public class DeserializeInfo {
    @SuppressWarnings("unchecked")
    public static HashMap<String, List<String>> start(String filePathString) {
        HashMap<String, List<String>> b = null;
        try {
            FileInputStream fileIn = new FileInputStream(filePathString);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            b = (HashMap<String, List<String>>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Base class not found");
            c.printStackTrace();
        }
        
        return b;
    }
}