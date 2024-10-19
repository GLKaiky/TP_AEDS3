package ArquivoClass;

import Tarefa.Categoria;
import ArvoreB.ArvoreBMais;
import ArvoreB.ParNomeId;
import Tarefa.Tarefa;

import java.util.ArrayList;

public class ArquivoCategorias extends Arquivo<Categoria> {
    ArvoreBMais<ParNomeId> arvoreB;

    /* Criando o Arquivo de Categorias */
    public ArquivoCategorias()throws Exception{
        super(Categoria.class.getConstructor(), "ArquivoCategoria");
        try{
            arvoreB = new ArvoreBMais<>(ParNomeId.class.getConstructor(), 5, "./dados/ArvoreCategorias");
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new Exception();
        }
    }

    /* CRUD DE CATEGORIA */

    /* Método Publico para a criação de Categoria. Retorna a Categoria criada */
    public int create(String nomeCategoria)throws Exception{
        Categoria categoria = new Categoria(nomeCategoria);
        return this.create1(categoria);
    }
    
    /* Método Privado da criação de Categoria. Retorna o ID da Categoria */
    private int create1(Categoria categoria) throws Exception{
        int id = super.create(categoria);
        categoria.setId(id);
        arvoreB.create(new ParNomeId(categoria.getNome(),categoria.getId()));

        return id;
    } 
    
    /* Método de leitura listando as Tarefas da Categoria passada como parametro. Retorna as Tarefas */
    public ArrayList<Tarefa> read(String nomeCategoria)throws Exception{
        ArrayList<ParNomeId> categorias = arvoreB.read(new ParNomeId(nomeCategoria));
        
        /*Se a Categoria não existe, incapaz de fazer o método*/
        if(categorias.isEmpty()){
            System.out.println("Categoria inexistente");
            return null;
        }
        ArrayList<Tarefa> t = new ArrayList<>();
        ArquivoTarefas tarefas = new ArquivoTarefas();

        t = tarefas.read(categorias.get(0));
        return t;
    }

    /* Método de atualização do nome de uma Categoria. Retornando se foi feito com Sucesso ou Não. */
    public boolean update(String nomeCategoria, String novaCategoria)throws Exception{
        ArrayList<ParNomeId> categorias = arvoreB.read(new ParNomeId(nomeCategoria));
        Categoria cat = new Categoria(novaCategoria);
        
        /*Se a Categoria não existe, incapaz de fazer o método*/
        if(categorias.isEmpty()){
            System.out.println("Categoria inexistente");
            return false;
        }
        cat.setId(categorias.get(0).getId());
        
        super.update(cat);
        arvoreB.create(new ParNomeId(cat.getNome(), cat.getId()));

        arvoreB.delete(categorias.get(0));

        return true;
    }

    /* Método de atualização chamando o Método Update de Tarefa. Retorna um booleano. */
    public boolean updateTarefa(String nomeCategoria, String nomeTarefa, Tarefa updateTarefa)throws Exception{
        ArrayList<ParNomeId> categorias = arvoreB.read(new ParNomeId(nomeCategoria));
        
        /*Se a Categoria não existe, incapaz de fazer o método*/
        if(categorias.isEmpty()){
            System.out.println("Categoria inexistente");
            return false;
        }
        ArquivoTarefas tarefas = new ArquivoTarefas();

        return tarefas.update(categorias.get(0), nomeTarefa, updateTarefa);
    }

    public boolean deleteTarefa(String nomeCategoria, String nomeTarefa)throws Exception{
        ArrayList<ParNomeId> categorias = arvoreB.read(new ParNomeId(nomeCategoria));

        if(categorias.isEmpty()){
            System.out.println("Categoria inesistente");
            return false;
        }

        ArquivoTarefas tarefas = new ArquivoTarefas();

        return tarefas.delete(categorias.get(0), nomeTarefa);
    }
}
