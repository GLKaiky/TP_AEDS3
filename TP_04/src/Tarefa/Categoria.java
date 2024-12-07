package Tarefa;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Constructor;

import ArquivoClass.Arquivo;
import Interfaces.Registro;

import java.io.DataInputStream;

public class Categoria implements Registro{

Arquivo categorias;

/* Used to store recorded Tasks from the Arquivo database
 * Contains:
 *   id: Attribute given only by Arquivo to indicate its position
 *   nome: 
 */
    private int id;
    
    
    //Atributos da classe Categoria
    private String nome;


    //Métodos Set's
    public void setId(int id){
        this.id = id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
    //Fim Métodos Set's
    
    //Métodos Get's
    public int getId(){
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

    //Fim Métodos Get's

    //Método toByteArray
    public byte[] toByteArray(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try{
            dos.writeInt(this.id);
            dos.writeUTF(this.nome);
        } catch(Exception e){
            System.out.println("Deu bobs ao converter Tarefa para array de byte");
            System.out.println(e.getMessage());
        }
        return baos.toByteArray();
    }
    
    //Método fromByteArray
    public void fromByteArray(byte [] array){
        ByteArrayInputStream bais = new ByteArrayInputStream(array);
        DataInputStream dis = new DataInputStream(bais);
        try{
            this.id = dis.readInt();
            this.nome = dis.readUTF();
        } catch(Exception e){
            System.out.println("Deu bobs ao converter vetor de byte pra objeto tarefa");
            e.printStackTrace();
        }
    }

    //Construtores

    public Categoria(String nome){
        this.nome = nome;
        this.id = -1;
    }

    public Categoria(){}
    //Fim Construtores




    @Override
    public String toString() {
        return getArgumentList();
    }

    //Método toString
    private String getArgumentAsLines(){
        String s = "";
        s += Integer.toString(this.id);
        s += "\n";
        s += this.nome;
        s += "\n";
        return s;
    }

    private String getArgumentList() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("id: ").append(Integer.toString(this.id)).append(", ");
        sb.append("nome: ").append(nome).append(", ");
        return sb.toString();
    }

}
