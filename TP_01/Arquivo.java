    import java.io.File;
    import java.io.IOException;
    import java.io.RandomAccessFile;
    import java.lang.reflect.Constructor;

    public class Arquivo<T extends Registro>{
        //Atributos privados
        private final int TAM_CABECALHO = 4; //Definindo tamanho do cabeçalho do arquivo
        private RandomAccessFile arquivo;
        private String nomedoArquivo;
        
        Constructor<T> construtor;

        public Arquivo(String nomeArquivo, Constructor<T> c) throws IOException{
            File d = new File("." + File.separator + "dados");
            if(!d.exists()){
                d.mkdir();
            }

            this.nomedoArquivo = "." + File.separator + "dados" + File.separator + nomeArquivo;
            this.construtor = c;

            this.arquivo = new RandomAccessFile(this.nomedoArquivo, "rw");

            if(arquivo.length()<TAM_CABECALHO){
                arquivo.writeInt(0);
            }
        }   

        public int Create(T obj) throws IOException{    
            this.arquivo.seek(0);
            int proximoID = arquivo.readInt() + 1;
            arquivo.seek(0);
            arquivo.writeInt(proximoID);
            
            obj.setID(proximoID);

            arquivo.seek(arquivo.length());

            byte[] b = obj.toByteArray();

            arquivo.writeByte(' ');
            arquivo.writeShort(b.length);
            arquivo.write(b);

            return obj.getID();
        }

        public void Read()throws IOException{
            Tarefa obj = new Tarefa();
            this.arquivo.seek(0); 
            int qunatidadeRegistros = arquivo.readInt(); //Vai ler o id
            while(qunatidadeRegistros > 0){
                if(arquivo.readByte() == ' '){
                    short tam = arquivo.readShort();
                    byte[] b = new byte[tam];
                    arquivo.readFully(b, 0, tam);
                    obj.fromByteArray(b);

                    System.out.println(obj.toString());
                    qunatidadeRegistros--;

                }
            }
            
        }

            public void Delete(int id)throws IOException{        
                Tarefa obj = new Tarefa();
                this.arquivo.seek(0);
                int qunatidadeRegistros = arquivo.readInt();

                while(qunatidadeRegistros > 0){
                    arquivo.readByte();
                    long posicaoLapide = arquivo.getFilePointer();

                    short tam = arquivo.readShort();
                    
                    byte[] b = new byte[tam];
                    arquivo.readFully(b, 0, tam);
                    obj.fromByteArray(b);

                    long fim = arquivo.getFilePointer();

                    if(obj.getID() == id){
                        arquivo.seek(posicaoLapide-1);
                        arquivo.writeByte('*');
                        break;
                    }else{
                        arquivo.seek(fim);
                    }
                    qunatidadeRegistros--;
                }
            }       
    }
