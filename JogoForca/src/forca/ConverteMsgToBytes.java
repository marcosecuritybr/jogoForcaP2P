package forca;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
 
public class ConverteMsgToBytes {
     
    /**
     * Converte um Objeto para um array de bytes
     * @param object
     * @return byte[]
     */
    static byte[] bytes = null;
    
    public static byte[] convertObjectToByteArray(Object object) {
        
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            objectOutputStream.close();
            byteArrayOutputStream.close();
            ConverteMsgToBytes.bytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
        return bytes;
    }
 
    public int getLenght(){
        return bytes.length;
    }
    
    /**
     * Converte um array de bytes para um objeto
     * @param bytes
     * @return object
     */
    public Object convertByteArrayToObject(byte[] bytes) {
        Object object = null;
 
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            object = objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
         
        return object;
    }
     
}