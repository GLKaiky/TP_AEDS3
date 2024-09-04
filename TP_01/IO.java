import java.time.LocalDate;
import java.io.File;
import java.io.IOException;


public class IO {
    public static void main(String[] args) throws Exception{
        Arquivo<Tarefa> arqTarefa;         
        LocalDate criacao = LocalDate.of(2024, 5, 9);
        LocalDate conclusao = LocalDate.of(2024, 8, 2);
        Tarefa t = new Tarefa("tarefa6-teste", criacao, conclusao, (byte) 1,(byte) 2);

        try{
            File f = new File("." + File.separator + "dados" + File.separator + "clientes.db");
            f.delete(); 

            arqTarefa= new Arquivo<>("tarefas.db", Tarefa.class.getConstructor());  
            
            arqTarefa.Create(t);

            arqTarefa.Read();      
            
        }catch(IOException e){  
            System.out.println(e.getMessage());
        }
    }       
}   
