package ArquivoClass;
import java.io.IOException;

import Hash.HashExtensivel;
import Hash.ParNomeIDTarefa;
import Tarefa.Tarefa;

public class ArquivoTarefas extends Arquivo<Tarefa>{

    HashExtensivel<ParNomeIDTarefa> indiceIndiretoNome;
    public ArquivoTarefas()throws Exception{
        super(Tarefa.class.getConstructor(), "tarefas");

        this.indiceIndiretoNome = new HashExtensivel<ParNomeIDTarefa>(
            ParNomeIDTarefa.class.getConstructor(), 
            4, 
            "dados/" + "tarefas" + ".hash_e.db", 
            "dados/" + "tarefas" + ".hash_f.db"
        );
    }


    @Override
    public int create(Tarefa tarefa) throws Exception{
        int id = -1;
        try{
            id = super.create(tarefa);
            tarefa.setId(id);
            System.out.println("INICIO CRIAR\n\n\n");
            indiceIndiretoNome.create(new ParNomeIDTarefa(tarefa.getNome(), id));
            System.out.println("FIM CRIAR\n\n\n");

        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(indiceIndiretoNome);
        return id;
    }

    public Tarefa read(String nome) throws Exception{
        ParNomeIDTarefa t;
        Tarefa t2 = new Tarefa();
        try{
            System.out.println(indiceIndiretoNome.read(nome.hashCode()));
            /*System.out.println("Inicio pesquisa\n\n\n");
            System.out.println(indiceIndiretoNome.read(Math.abs(nome.hashCode())));
            System.out.println("Fim pesquisa\n\n\n");*/
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Fudeu" + e.getMessage());
        }
        return t2;
    }

    public boolean update(Tarefa updatedTask) throws Exception{
        boolean success = false;
        try{

            success = super.update(updatedTask) ? indiceIndiretoNome.update(new ParNomeIDTarefa(updatedTask.getNome(), updatedTask.getId())) : false;

        }catch(Exception e){
            System.out.println("Erro no Update do HashExtensivel " + e.getMessage());
        }
        
        return success;
    }
    
}
