package ArquivoClass;
import java.io.IOException;
import java.util.ArrayList;
import ArvoreB.*;
import Tarefa.Tarefa;


public class ArquivoTarefas extends Arquivo<Tarefa>{

    /* Instanciando o Objeto "ArvoreBMais" */
    ArvoreBMais<ParIDcIDt> arvoreB;

    public ArquivoTarefas() throws Exception{
        super(Tarefa.class.getConstructor(), "arquivoTarefas");
        try{
            arvoreB = new ArvoreBMais<>(ParIDcIDt.class.getConstructor(), 5, "./dados/ArvoreTarefas");        
        }catch(Exception e){
            throw new Exception("Erro na criação da Arvore");
        }
    }

    /* Criando a Tarefa */
    @Override
    public int create(Tarefa tarefa)throws Exception{
        int id = super.create(tarefa);
        tarefa.setId(id);
        arvoreB.create(new ParIDcIDt(tarefa.getIDCategoria(), id));
        return id;
    }
    
    /* Lendo os ID's do ArrayList para retornar as Categorias */
    public ArrayList<Tarefa> read(ParNomeId parNomeId) throws Exception{
        ArrayList<Tarefa> t = new ArrayList<>();
        ArrayList<ParIDcIDt> id = new ArrayList<>();
        id = arvoreB.read(new ParIDcIDt(parNomeId.getId()));
        for(int i = 0; i<id.size(); i++){
            t.add(super.read(id.get(i).getId2())); 
            System.out.println(t.get(i));
        }
        return t;
    }
    
}
