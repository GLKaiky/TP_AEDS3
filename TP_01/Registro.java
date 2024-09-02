import java.io.IOException;

public interface Registro {
    public void setID(int id);
    public int getID();
    public byte[] toByteArray() throws IOException;
    public void fromByteArray(byte[] b) throws IOException;
 }  
