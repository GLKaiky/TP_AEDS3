    //Entity "Tarefa"
    import java.time.LocalDate;
    import java.io.IOException;
    import java.io.ByteArrayOutputStream;
    import java.io.ByteArrayInputStream;
    import java.io.DataInputStream;
    import java.io.DataOutputStream;
    

    public class Tarefa implements Registro{
        //private attributes

        private int ID;
        private String nome;
        private LocalDate criacao;
        private LocalDate conclusao;
        private Byte status;
        private Byte prioridade;

        //constructors 

        public Tarefa(){}

        public Tarefa(String nome, LocalDate criacao, LocalDate conclusao, Byte status, Byte prioridade){
            this(-1, nome, criacao, conclusao, status,prioridade);
        }

        public Tarefa(int ID,String nome, LocalDate criacao, LocalDate conclusao, Byte status, Byte prioridade){
            this.ID = ID;
            this.nome = nome;
            this.criacao = criacao;
            this.conclusao = conclusao;
            this.status = status;
            this.prioridade = prioridade;
        }

        //gets and sets

        public int getID(){
            return this.ID;
        }

        public String getNome(){
            return this.nome;
        }

        public LocalDate getCriacao(){
            return this.criacao;
        }

        public LocalDate getConclusao(){
            return this.conclusao;
        }

        public Byte getStatus(){
            return this.status;
        }

        public Byte getPrioridade(){
            return this.prioridade;
        }

        //
        //

        public void setID(int ID){
            this.ID = ID;
        }
        
        public void setNome(String nome){
            this.nome = nome;
        }

        public void setCriacao(LocalDate criacao){
            this.criacao = criacao;
        }

        public void setConclusao(LocalDate conclusao){
            this.conclusao = conclusao;
        }

        public void setStatus(Byte status){
            this.status = status;
        }

        public void setPrioridade(Byte prioridade){
            this.prioridade = prioridade;
        }

        //methods

        public byte[] toByteArray() throws IOException{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeInt(this.ID);
            dos.writeUTF(this.nome);
            dos.writeInt((int) this.criacao.toEpochDay());
            dos.writeInt((int) this.conclusao.toEpochDay());
            dos.writeByte(this.status);
            dos.writeByte(this.prioridade);

            return baos.toByteArray();
        }

        public void fromByteArray(byte[] b)throws IOException{
            ByteArrayInputStream bais = new ByteArrayInputStream(b);
            DataInputStream dis = new DataInputStream(bais);

            this.ID = dis.readInt();
            this.nome = dis.readUTF();
            this.criacao =  LocalDate.ofEpochDay(dis.readInt());
            this.conclusao = LocalDate.ofEpochDay(dis.readInt());
            this.status = dis.readByte();
            this.prioridade = dis.readByte();
        }   

        
    }
