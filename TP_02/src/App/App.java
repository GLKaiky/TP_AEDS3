package App;
import ArquivoClass.*;

public class App {

  // Arquivoa declarado fora de main() para ser poder ser usado por outros métodos
  //private static Arquivo<Tarefa> arqTarefa;

  public static void main(String[] args) throws Exception{
      ArquivoCategorias categorias = new ArquivoCategorias();
      
      System.out.println(categorias.read("TP_Aeds2"));  

  }

}
