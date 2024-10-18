package ArquivoClass;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

import Interfaces.*;
import Hash.*;

import java.io.File;
public class Arquivo<T extends Registro>{
    //Atributos da Classe Genérica Arquivo
    private T objeto;
    public Constructor<T> construtor;
    //private File arq;
    private File diretorio;
    String fileName;
    final int fimCabecalho = 4;
    //A partir seguindo basicamente o que o professor fez 
    HashExtensivel<ParIDEndereco> indiceDireto;

    //Método Create
    public int create(T objeto) throws Exception{
        RandomAccessFile raf = new RandomAccessFile(this.fileName, "rw");

        // Pegar novo ID
        raf.seek(0);
        int proxId = raf.readInt() + 1;
        objeto.setId(proxId);

        // Ir para EOF
        raf.seek(raf.length());
         
        // Criar novo registro
        long endereco = raf.getFilePointer(); // pos inicial
        raf.writeByte(0); 
        byte [] array = objeto.toByteArray();
        raf.writeInt(array.length);
        raf.write(array);

        // Resetar id do header
        raf.seek(0);
        raf.writeInt(proxId);
        raf.close();
        
        // Atualizar hash extensivel com id / endereco
        indiceDireto.create(new ParIDEndereco(proxId, endereco));

        return proxId;
    }

    //Método Read
    public T read(int id) throws Exception{
        try (RandomAccessFile raf = new RandomAccessFile(this.fileName, "rw")) {
            ParIDEndereco pid = indiceDireto.read(id);

            if(pid != null) {
                raf.seek(pid.getEndereco());

                // Obter metadados do registro
                byte lapide = raf.readByte();
                int tamArq = raf.readInt();
                if(lapide == 0) { 
                    byte[] array = new byte[tamArq];
                    raf.read(array);

                    this.objeto = construtor.newInstance();
                    this.objeto.fromByteArray(array);

                    if(objeto.getId() == id){
                        raf.close();
                        return this.objeto;
                    }
                }
            }
        } catch(Exception e){
            System.out.println("Deu bobs na leitura de uma tarefa");
            e.printStackTrace();
        }
        return null;
    }

    //Método Delete
    public boolean delete(int id) throws Exception {
        RandomAccessFile raf = new RandomAccessFile(this.fileName, "rw");
        boolean success = false;

        ParIDEndereco pie = indiceDireto.read(id);
        if(pie != null) {
            // Let metadados do arquivo
            raf.seek(pie.getEndereco());
            long posAtual = raf.getFilePointer();
            byte lapide = raf.readByte();
            if(lapide == 0){
                raf.seek(posAtual);
                raf.write(1); // Escrever morto na lapide
                success = true;
            }
        }
        raf.close();
        return success;
    }

    //Método Update
    public boolean update(T novoObjeto)throws Exception{
        RandomAccessFile raf = new RandomAccessFile(this.fileName, "rw");
        boolean success = false;

        ParIDEndereco pie = indiceDireto.read(novoObjeto.getId());
        if(pie != null){
            //Ler metadados
            raf.seek(pie.getEndereco());
            long posAtual = raf.getFilePointer();
            byte lapide = raf.readByte();

            if(lapide == 0){
                success = true;

                int tamArq = raf.readInt();
                byte [] array = novoObjeto.toByteArray();
                if(array.length <= tamArq) { //Se cabe dentro do registro
                    raf.seek(posAtual + 5); //Pular metadados
                    raf.write(array);
                }
                else{
                    raf.seek(posAtual);
                    raf.write(1); //Marcar registro morto

                    // Escrever novo registro no final
                    raf.seek(raf.length());
                    long novoEndereco = raf.getFilePointer();
                    raf.writeByte(0);
                    raf.writeInt(array.length);
                    raf.write(array);

                    // Atualizar indice
                    indiceDireto.update(new ParIDEndereco(novoObjeto.getId(), novoEndereco));
                }
            }
        }
        raf.close();
        return success;
    }

    //Método Construtor
    public Arquivo(Constructor<T> construtor, String fN) throws Exception{
        this.construtor = construtor;
        this.diretorio = new File("dados");
        if(!diretorio.exists()){
            diretorio.mkdir();
        }
        this.fileName = "dados/" + fN + ".db";
        RandomAccessFile raf = new RandomAccessFile(this.fileName, "rw");
        raf.seek(0);
        raf.writeInt(0);
        raf.close();
        indiceDireto = new HashExtensivel<>(
            ParIDEndereco.class.getConstructor(), 
            4, 
            "dados/" + fN + ".hash_d.db", 
            "dados/" + fN + ".hash_c.db"
        );
    }

    // Método que lista todas as entradas do arquivo
public ArrayList<T> list() throws Exception {
    ArrayList<T> objects = new ArrayList<>();
    try (RandomAccessFile raf = new RandomAccessFile(this.fileName, "rw")) {
        long pos = fimCabecalho; // Ignora o cabeçalho inicial
        // Percorre todo o arquivo
        while (pos < raf.length()) {
            raf.seek(pos);
            
            // Ler o metadado (lápide)
            byte lapide = raf.readByte();
            int tamArq = raf.readInt();
            
            // Se não está excluído
            if (lapide == 0) {
                byte[] array = new byte[tamArq];
                raf.read(array);
                
                T obj = construtor.newInstance();
                obj.fromByteArray(array); // Reconstrói o objeto a partir do array de bytes
                objects.add(obj);
                
            }
            
            // Avança para o próximo registro
            pos = raf.getFilePointer();
        }
    } catch (Exception e) {
        System.out.println("Erro ao listar os registros.");
        e.printStackTrace();
    }
    return objects;
}


    public Arquivo(){}
}