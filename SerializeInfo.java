import java.io.*;
import java.util.HashMap;
import java.util.List;
public class SerializeInfo  {

    public static boolean start(String fps, HashMap<String, List<String>> b) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fps);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(b);
            out.close();
            fileOut.close();
            return true;
        } catch (IOException i) {
            i.printStackTrace();
            return false;
        }
    }
}