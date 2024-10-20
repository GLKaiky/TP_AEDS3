package Hash;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import Interfaces.RegistroHashExtensivel;


/* Generic RegistroHashExtensivel used on Arquivo to create an Extensible Hash
 * between the register id and its current physical address
 */
public class ParIDEndereco implements RegistroHashExtensivel<ParIDEndereco> {
    
    private int id; // key
    private long endereco; // value

    // Fixed size: Must be >= any possible outputs 
    // Warning: If that does not happen, The bucket inner element will be trimmed when stored
    private final short TAMANHO = 12;
    
    /* Construtores */
    public ParIDEndereco() {
        this.id = -1;
        this.endereco = -1;
    }

    public ParIDEndereco(int id, long end) {
        this.id = id;
        this.endereco = end;
    }

    /* Métodos GET */
    public int getId() {
        return id;
    }

    public long getEndereco() {
        return endereco;
    }

    /* Outros Métodos */
    @Override
    public int hashCode() {
        return this.id;
    }

    public short size() {
        return this.TAMANHO;
    }

    public String toString() {
        return "("+this.id + ";" + this.endereco+")";
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeLong(this.endereco);
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.id = dis.readInt();
        this.endereco = dis.readLong();
    }

}
