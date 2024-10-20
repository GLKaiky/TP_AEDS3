package ArquivoClass;
import java.io.IOException;
import java.util.ArrayList;
import ArvoreB.*;
import Tarefa.Tarefa;


public class ArquivoTarefas extends Arquivo<Tarefa>{
    public static final String AMARELO = "\033[33m"; // Amarelo
    public static final String VERDE = "\033[32m"; // Verde
    public static final String RESET = "\033[0m"; // Resetar cor

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

    /* Método de Criação da Tarefa. Retornando o ID */
    @Override
    public int create(Tarefa tarefa)throws Exception{
        
        int id = super.create(tarefa);
        tarefa.setId(id);
        System.out.println(id);
        arvoreB.create(new ParIDcIDt(tarefa.getIDCategoria(), id));
        return id;
    }
    
    /* Método de Leitura. Lendo os ID's do ArrayList. Retorna as Tarefas */
    public ArrayList<Tarefa> read(ParNomeId parNomeId) throws Exception{
        
        ArrayList<Tarefa> t = new ArrayList<>();
        ArrayList<ParIDcIDt> id = new ArrayList<>();
        id = arvoreB.read(new ParIDcIDt(parNomeId.getId()));
        for(int i = 0; i<id.size(); i++){
            t.add(super.read(id.get(i).getId2())); 
        }
        return t;
    }

    /* Método de Atualização. Procura o nome da Tarefa desejada para poder muda-la. Retorna booleano. */
    public boolean update(ParNomeId parNomeId,String nomeTarefa, Tarefa updateTarefa) throws Exception{
        
        ArrayList<Tarefa> t = read(parNomeId);

        for(int i = 0; i<t.size(); i++){
            if(t.get(i).getNome().equals(nomeTarefa)){
                updateTarefa.setId(t.get(i).getId());
                i = t.size();
            }
        }

       return super.update(updateTarefa);
    }

    /* Método de Delete. Procura pelo nome da Tarefa para ser Deletada. Retorna um booleano. */
    public boolean delete(ParNomeId parNomeId ,String nomeTarefa)throws Exception{
        
        ArrayList<Tarefa> t = read(parNomeId);
        Tarefa delete = new Tarefa();

        for(int i = 0; i<t.size(); i++){
            if(t.get(i).getNome().equals(nomeTarefa)){
                delete = t.get(i);   
            }
        }
        
        return super.delete(delete.getId()) ? arvoreB.delete(new ParIDcIDt(parNomeId.getId(), delete.getId())) : false;      
    }

    /* Listando as Tarefas */
    public void listar() throws Exception{

        try{
            ArrayList<Tarefa> categorias = super.list();
    
            if(categorias.isEmpty())
                throw new Exception("Categorias ainda não foram criadas");
            
            for(int i = 0; i<categorias.size(); i++){
                System.out.println(VERDE + " Nome da Tarefa: " + categorias.get(i).getNome() + "Data de Inicio: " + categorias.get(i).getInicio() + "Data de Fim: " + categorias.get(i).getFim() +
                "Status: " + categorias.get(i).getStatus() + "Prioridade: " + categorias.get(i).getPrioridade() + "" + RESET);
            }
            }catch(Exception e){
                System.out.println(e.getMessage());
        }
    }
    
}
