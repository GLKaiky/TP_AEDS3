package App;
import ArquivoClass.*;
import Tarefa.*;
import java.time.LocalDate;

public class App {

  // Arquivoa declarado fora de main() para ser poder ser usado por outros métodos
  //private static Arquivo<Tarefa> arqTarefa;

  
  public static void main(String[] args) throws Exception{
      LocalDate aux, auy;
      aux = LocalDate.of(2004, 9, 29);
      auy = LocalDate.of(2024, 10,29);
      ArquivoTarefas tarefas = new ArquivoTarefas();
      ArquivoCategorias categorias = new ArquivoCategorias();

      Tarefa tarefa = new Tarefa("TPaids3", aux, auy, (byte) 0, (byte) 2);
      tarefa.setIdCategoria(1);

    System.out.println(categorias.deleteTarefa("Estudar2", "TPaids3"));

    System.out.println(categorias.read("Estudar2"));
  }

}
