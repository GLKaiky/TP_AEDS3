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
      File arq = new File("dados/tarefas.db");
      if(arq.exists()){
        arq.delete();
        File hashDirectory = new File("dados/tarefas.hash_d.db");
        hashDirectory.delete();
        File hashBuckets = new File("dados/tarefas.hash_c.db");
        hashBuckets.delete();
      }

      arqTarefa = new Arquivo<Tarefa>(Tarefa.class.getConstructor(), "tarefas");

      // Insere os três livros
      id1 = arqTarefa.create(t1); 
      t1.setId(id1);
      id2 = arqTarefa.create(t2);
      t2.setId(id2);
      id3 = arqTarefa.create(t3);
      t3.setId(id3);
        
      System.out.println(arqTarefa.read(id3));
      System.out.println(arqTarefa.read(id1));

      // Altera um livro para um tamanho maior e exibe o resultado
      t2.setNome("Instalar Memória RAM");
      arqTarefa.update(t2);
      System.out.println(arqTarefa.read(id2));

      // Altera um livro para um tamanho menor e exibe o resultado
      t1.setNome("Comprar Hades 3");
      arqTarefa.update(t1);
      System.out.println(arqTarefa.read(id1));

      // Excluir um livro e mostra que não existe mais
      arqTarefa.delete(id3);
      Tarefa t = arqTarefa.read(id3);
      if(t==null)
        System.out.println("Tarefa excluída");
      else
        System.out.println(t.getNome());
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
