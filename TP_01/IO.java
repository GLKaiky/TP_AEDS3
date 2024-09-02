import java.time.LocalDate;
import java.io.File;
import java.io.IOException;


public class IO {
    public static void main(String[] args) throws Exception{
        Arquivo<Tarefa> arqTarefa;         
        LocalDate criacao = LocalDate.of(2024, 5, 9);
        LocalDate conclusao = LocalDate.of(2024, 8, 2);
        Tarefa t = new Tarefa("tarefa5-teste", criacao, conclusao, (byte) 1,(byte) 2);

        try{
            File f = new File(".\\dados\\clientes.db");
            f.delete(); 

            arqTarefa= new Arquivo<>("tarefas.db", Tarefa.class.getConstructor());  
            
            int a = arqTarefa.Create(t);

            System.out.println(a);
            
            arqTarefa.Read();

        }catch(IOException e){  
            System.out.println(e.getMessage());
        }
    }       
}   
