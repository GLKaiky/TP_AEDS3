package App;

import ArquivoClass.*;
import Tarefa.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;

public class App {

  // Arquivoa declarado fora de main() para ser poder ser usado por outros métodos
  // private static Arquivo<Tarefa> arqTarefa;
  public static final String AMARELO = "\033[33m"; // Amarelo
  public static final String VERDE = "\033[32m"; // Verde
  public static final String RESET = "\033[0m"; // Resetar cor

  public static void main(String[] args) throws Exception {
    CrudTarefas crudTarefas = new CrudTarefas();
    CrudCategorias crudCategorias = new CrudCategorias();
    /* Criando o Scanner e Resposta para ler a aentrada do usuário */
    Scanner scanf = new Scanner(System.in);
    int resposta = 0;
    while (true) {
      try {

        /* Printando na telas as Opções */
        System.out.println(AMARELO + ">Inicio" + RESET);

        System.out.println(VERDE + "1) Tarefas" + RESET);
        System.out.println(VERDE + "2) Categorias" + RESET);
        System.out.println(VERDE + "3) Sair" + RESET);
        resposta = scanf.nextInt();

        /* Switch de acordo com a escolha do usuário */
        switch (resposta) {
          case 1:
            crudTarefas.iniciarTarefas();
            break;
          case 2:
            crudCategorias.iniciarCategoria();
            break;
          case 3:
            scanf.close();
            System.out.println("Saindo...");
            System.exit(0);
          default:
            System.out.println("Opção Inválida");
        }

      } catch (Exception e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
      }
    }

  }

  public static class CrudTarefas {
    public static ArquivoTarefas arquivoTarefas;
    public static ArquivoCategorias arquivoCategorias;
    public static final String AMARELO = "\033[33m"; // Amarelo
    public static final String VERDE = "\033[32m"; // Verde
    public static final String RESET = "\033[0m"; // Resetar cor
    Scanner scanf = new Scanner(System.in);

    // Método para iniciar as operações de tarefas
    public void iniciarTarefas() throws Exception {
      arquivoTarefas = new ArquivoTarefas();
      arquivoCategorias = new ArquivoCategorias();
      int resposta = 0;
      System.out.println(VERDE + ">Inicio>Tarefas" + RESET);

      System.out.println(AMARELO + "1) Incluir" + RESET);
      System.out.println(AMARELO + "2) Buscar" + RESET);
      System.out.println(AMARELO + "3) Alterar" + RESET);
      System.out.println(AMARELO + "4) Excluir" + RESET);
      System.out.println(AMARELO + "5) Retornar ao Menu Anterior" + RESET);

      resposta = scanf.nextInt();

      switch (resposta) {
        case 1:
          criarTarefa();
          break;
        case 2:
          listarTarefas();
          break;
        case 3:
          atualizarTarefa();
          break;
        case 4:
          Deletar();
          break;
        case 5:
          break;
        default:
          System.out.println("Opção Invalida");
      }

    }

    private void listadeTarefas(String nomeCategoria)throws Exception{
    try{
      ArrayList<Tarefa> t = arquivoCategorias.read(nomeCategoria);

      for (int i = 0; i < t.size(); i++) {
        System.out.println(VERDE + "[" + "Nome da Tarefa: " + t.get(i).getNome() + "||" + "Data de Inicio: "
            + t.get(i).getInicio() + "||" + "Data de Fim: " + t.get(i).getFim() + "||" +
            "Status: " + t.get(i).getStatus() + "||" + "Prioridade: " + t.get(i).getPrioridade() + "]" + RESET);
      }
    }catch(Exception e){
      System.out.println(e.getLocalizedMessage());
    }
    }

    /* Interface Deletando Tarefa */
    public void Deletar() throws Exception {

      String nomeCategoria, nomeTarefa;

      try {
        scanf.nextLine();
        System.out.println("Digite o nome da Categoria a qual pertence a tarefa que deseja deletar");
        arquivoCategorias.listar();
        nomeCategoria = scanf.nextLine();

        System.out.println("Digite o nome da Tarefa que deseja deletar");
        this.listadeTarefas(nomeCategoria);
        nomeTarefa = scanf.nextLine();

        arquivoCategorias.deleteTarefa(nomeCategoria, nomeTarefa);

      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    /* Interface Listando Tarefa */
    public void listarTarefas() throws Exception {
      String nomeCategoria;
      try {
        scanf.nextLine();
        System.out.println("Digite o nome da Categoria a qual pertence a tarefa que deseja Listar");
        arquivoCategorias.listar();
        nomeCategoria = scanf.nextLine();

        this.listadeTarefas(nomeCategoria);

      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    /* Interface Atualizando Tarefa */
    public void atualizarTarefa() throws Exception {

      Tarefa t = new Tarefa();
      String input, nomedaTarefa, nomedaCategoria;
      try {
        /* Evitando Buffer */
        scanf.nextLine();

        /* Scanneando o nome da Categoria */
        System.out.println("Digite o nome da Categoria a qual ela pertence");
        arquivoCategorias.listar();
        nomedaCategoria = scanf.nextLine();

        /* Scanneando o Nome da Tarefa */
        System.out.println("Digite o nome da tarefa que deseja Alterar");

        this.listadeTarefas(nomedaCategoria);

        nomedaTarefa = scanf.nextLine();

        System.out.println("Digite seu novo nome");
        t.setNome(scanf.nextLine());

        /* Scanneando a Data de Inicio */
        System.out.println("Digite a data de inicio (No formato dd/MM/yyyy)");
        input = scanf.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(input, formatter);
        t.setInicio(data);

        /* Scanneando a Data Final */
        System.out.println("Digite a data do Fim (No formato dd/MM/yyyy)");
        input = scanf.nextLine();
        data = LocalDate.parse(input, formatter);
        t.setFim(data);

        /* Scanneando o Status da Tarefa */
        System.out.println("Digite os Status da nova tarefa (Um numero)");
        t.setStatus((byte) scanf.nextInt());

        /* Scanneando a Prioridade da Tarefa */
        System.out.println("Digite a prioridade da nvoa Tarefa (Um numero inteiro)");
        t.setPrioridade((byte) scanf.nextInt());

        arquivoCategorias.updateTarefa(nomedaCategoria, nomedaTarefa, t);

        t = null;

      } catch (Exception e) {
        e.printStackTrace();
      }

    }

    /* Interface de Criação da Tarefa */
    public void criarTarefa() throws Exception {

      Tarefa t = new Tarefa();
      String input;
      try {
        /* Evitando Buffer */
        scanf.nextLine();

        /* Scanneando o Nome da Tarefa */
        System.out.println("Digite o nome da tarefa");
        t.setNome(scanf.nextLine());

        System.out.println("Digite o índice da categoria que desseja adicionar esta tarefa");
        System.out.println();
        arquivoCategorias.listar();
        System.out.println();
        t.setIdCategoria(scanf.nextInt());

        /* Scanneando a Data de Inicio */
        scanf.nextLine();
        System.out.println("Digite a data de inicio (No formato dd/MM/yyyy)");
        input = scanf.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(input, formatter);
        t.setInicio(data);

        /* Scanneando a Data Final */
        System.out.println("Digite a data do Fim (No formato dd/MM/yyyy)");
        input = scanf.nextLine();
        data = LocalDate.parse(input, formatter);
        t.setFim(data);

        /* Scanneando o Status da Tarefa */
        System.out.println("Digite os Status da tarefa (Um numero)");
        t.setStatus((byte) scanf.nextInt());

        /* Scanneando a Prioridade da Tarefa */
        System.out.println("Digite a prioridade da Tarefa (Um numero inteiro)");
        t.setPrioridade((byte) scanf.nextInt());

        arquivoTarefas.create(t);

        t = null;

      } catch (Exception e) {
        e.printStackTrace();
      }
    }

  }

  public static class CrudCategorias {
    public static ArquivoCategorias categoria;
    public static final String AMARELO = "\033[33m"; // Amarelo
    public static final String VERDE = "\033[32m"; // Verde
    public static final String RESET = "\033[0m"; // Resetar cor
    Scanner scanf = new Scanner(System.in);

    public void iniciarCategoria() throws Exception {
      categoria = new ArquivoCategorias();
      int resposta = 0;
      System.out.println(VERDE + ">Inicio>Categorias" + RESET);

      System.out.println(AMARELO + "1) Incluir" + RESET);
      System.out.println(AMARELO + "2) Buscar" + RESET);
      System.out.println(AMARELO + "3) Alterar" + RESET);
      System.out.println(AMARELO + "4) Excluir" + RESET);
      System.out.println(AMARELO + "5) Retornar ao Menu Anterior" + RESET);

      resposta = scanf.nextInt();

      switch (resposta) {
        case 1:
          criarCategoria();
          break;
        case 2:
          listarCategoria();
          break;
        case 3:
          atualizarCategoria();
          break;
        case 4:
          deletarCategoria();
          break;
        case 5:
          break;
        default:
          System.out.println("Opção Inválida");
          break;
      }
    }
    

    public void criarCategoria() throws Exception {
      try {
        /* Limpar o buffer */
        scanf.nextLine();
        System.out.println("Digite o nome da Categoria a ser Criada");
        categoria.create(scanf.nextLine());
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
      System.out.println("Criado com sucesso");
      System.out.println();
      categoria.listar();
    }

    public void listarCategoria() throws Exception {
      String nomeCategoria;
      try {
        scanf.nextLine();
        System.out.println("Digite o nome da Categoria que deseja listar as tarefas");
        System.out.println();
        categoria.listar();

        nomeCategoria = scanf.nextLine();

        ArrayList<Tarefa> t = categoria.read(nomeCategoria);

        for (int i = 0; i < t.size(); i++) {
          System.out.println(VERDE + "[" + "Nome da Tarefa: " + t.get(i).getNome() + "||" + "Data de Inicio: "
              + t.get(i).getInicio() + "||" + "Data de Fim: " + t.get(i).getFim() + "||" +
              "Status: " + t.get(i).getStatus() + "||" + "Prioridade: " + t.get(i).getPrioridade() + "]" + RESET);
        }

      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    public void atualizarCategoria() throws Exception {
      String nomeCategoria, novaCategoria;
      try {
        scanf.nextLine();
        System.out.println("Digite o nome da Categoria que deseja atualizar");
        System.out.println();
        categoria.listar();
        nomeCategoria = scanf.nextLine();

        System.out.println("Digite o nome da nova categoria");
        novaCategoria = scanf.nextLine();

        categoria.update(nomeCategoria, novaCategoria);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
      System.out.println("Atualizado com sucesso");
    }

    public void deletarCategoria() throws Exception {
      String nomeCategoria;
      try {
        scanf.nextLine();
        System.out.println("Digite o nome da Categoria que deseja deletar");
        System.out.println();
        categoria.listar();

        nomeCategoria = scanf.nextLine();

        if (categoria.delete(nomeCategoria)) {
          System.out.println("Deletado com sucesso");
        }
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
