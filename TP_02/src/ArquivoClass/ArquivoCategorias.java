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

    /* Criando as Categorias */
    public int create(String nomeCategoria)throws Exception{
        Categoria categoria = new Categoria(nomeCategoria);
        return this.create1(categoria);
    }

    private int create1(Categoria categoria) throws Exception{
        int id = super.create(categoria);
        categoria.setId(id);
        arvoreB.create(new ParNomeId(categoria.getNome(),categoria.getId()));

        return id;
    } 

    public ArrayList<Tarefa> read(String nomeCategoria)throws Exception{
        ArrayList<ParNomeId> categorias = arvoreB.read(new ParNomeId(nomeCategoria));
        if(categorias.isEmpty()){
            System.out.println("Categoria inexistenteeeee");
            return null;
        }
        ArrayList<Tarefa> t = new ArrayList<>();
        ArquivoTarefas tarefas = new ArquivoTarefas();

        t = tarefas.read(categorias.get(0));
        return t;
    }

    public boolean updateTarefa(String nomeCategoria, String nomeTarefa, Tarefa updateTarefa)throws Exception{
        ArrayList<ParNomeId> categorias = arvoreB.read(new ParNomeId(nomeCategoria));
        if(categorias.isEmpty()){
            System.out.println("Categoria inexistente");
            return false;
        }
        ArquivoTarefas tarefas = new ArquivoTarefas();

        return tarefas.update(categorias.get(0), nomeTarefa, updateTarefa);
    }
}
