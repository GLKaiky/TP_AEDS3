

/* Ensure objects can be safely transformed and created from
 * byte arrays
 *
 * The method must have its own UID which will be created only from Arquivo, and
 * acessible through 
 *   -setId()
 *   -getId()  
 * 
 * Warning: Methods that use Registro rely on complete functionality and 
 * interchangeable capacity of both methods:
 *   -toByteArray()
 *   -fromByteArray()
 * Failure to do so will likely lead to runtime exception 
 * 
 * Both methods must also ensure that id is first argument printed / extracted from 
 * the byte array
 * 
 */

public interface Registro {
    public void setId(int id);
    public int getId();
    public byte [] toByteArray();
    public void fromByteArray(byte [] array);
}
