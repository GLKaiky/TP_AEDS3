package App;
import seeds.Popular;

public class App {

  // Arquivoa declarado fora de main() para ser poder ser usado por outros métodos
  //private static Arquivo<Tarefa> arqTarefa;

  public static void main(String[] args) throws Exception{
    Popular pop = new Popular();
    pop.list();
  }

}
