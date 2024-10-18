import java.io.*;
import java.time.LocalDate;

public class App {

  // Arquivo declarado fora de main() para ser poder ser usado por outros métodos
  private static Arquivo<Tarefa> arqTarefa;

  public static void main(String[] args) {
    LocalDate aux, auy;
    aux = LocalDate.of(2004, 9, 29);
    auy = LocalDate.of(2024, 9, 29);

    // Livros de exemplo
    Tarefa t1 = new Tarefa("Comprar Hades 2", auy, auy, (byte) 0, (byte) 2);
    Tarefa t2 = new Tarefa("Comprar Hades", auy, auy, (byte) 0, (byte) 2);
    Tarefa t3 = new Tarefa("Completar 2 Décadas", aux, auy, (byte) 0, (byte) 5);
    int id1, id2, id3;

    try {

      arqTarefa = new Arquivo<Tarefa>(Tarefa.class.getConstructor(), "tarefas");


      System.out.println(arqTarefa.read(1));
      System.out.println(arqTarefa.read(3));
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
