package Hash;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import Interfaces.RegistroHashExtensivel;

public class ParNomeIDTarefa implements RegistroHashExtensivel<ParNomeIDTarefa>{
    private String nome;
    private int idTarefa;
    private final short TAMANHO = 54; //4 bytes destinados ao ID + 50 bytes destinados ao Nome

    public ParNomeIDTarefa(){
        this.nome = " ";
        this.idTarefa = -1;
    }

    public ParNomeIDTarefa(String nome, int idTarefa){
        this.nome = nome;
        this.idTarefa = idTarefa;
    }

    public String getNome(){
        return this.nome;
    }

    public int getIDTarefa(){
        return this.idTarefa;
    }
 
    @Override
    public int hashCode() {
        return Math.abs(this.nome.hashCode());
    }
    
    public short size(){
        return this.TAMANHO;
    }

    public String toString(){
        return "(" + this.nome + ";" + this.idTarefa + ")";
    }

    public byte[] toByteArray() throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        System.out.println(this.nome);
        dos.writeUTF(this.nome);
        dos.writeInt(this.idTarefa);

        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException{
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.nome = dis.readUTF();
        this.idTarefa = dis.readInt();
    } 

}
