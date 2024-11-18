package ArquivoClass;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import ArvoreB.*;
import ListaInvertida.*;
import Tarefa.Tarefa;


public class ArquivoTarefas extends Arquivo<Tarefa>{
    public static final String AMARELO = "\033[33m"; // Amarelo
    public static final String VERDE = "\033[32m"; // Verde
    public static final String RESET = "\033[0m"; // Resetar cor

    /* Instanciando o Objeto "ArvoreBMais" */
    ArvoreBMais<ParIDcIDt> arvoreB;

    /* Instanciando o Objeto "StopWords" que representa o centro de atividades
     * da lista invertida
     */
    StopWords stopWords;

    public ArquivoTarefas() throws Exception{
        
        super(Tarefa.class.getConstructor(), "arquivoTarefas");
        try{
            arvoreB = new ArvoreBMais<>(ParIDcIDt.class.getConstructor(), 5, "./dados/ArvoreTarefas");        
        }catch(Exception e){
            throw new Exception("Erro na criação da Arvore");
        }
        try{
            stopWords = new StopWords();
        }
        catch(Exception e){
            throw new Exception("Erro na criação da lista invertida");
        }
    }

    /* Método de Criação da Tarefa. Retornando o ID */
    @Override
    public int create(Tarefa tarefa)throws Exception{
        int id = super.create(tarefa);
        tarefa.setId(id);
        System.out.println(id);
        arvoreB.create(new ParIDcIDt(tarefa.getIDCategoria(), id));
        stopWords.inserir(tarefa.getNome(), id);
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
    /*public boolean update(ParNomeId parNomeId,String nomeTarefa, Tarefa updateTarefa) throws Exception{
        ArrayList<Tarefa> t = read(parNomeId);

        for(int i = 0; i<t.size(); i++){
            if(t.get(i).getNome().equals(nomeTarefa)){
                updateTarefa.setId(t.get(i).getId());
                i = t.size();
            }
        }

       return super.update(updateTarefa);
    }*/

    public boolean update(Tarefa tarefa, Tarefa update){
        boolean result = false;
        update.setId(tarefa.getId());

        try{
            String [] chaves = stopWords.stopWordsCheck(tarefa.getNome());
            for(int i = 0; i < chaves.length; i++){
                System.out.println(chaves[i]);
                chaves[i] = chaves[i].toLowerCase();
                stopWords.lista.delete(chaves[i], tarefa.getId());
            }
            stopWords.inserir(update.getNome(), update.getId());
            result = super.update(update);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    /* Método de Delete. Procura pelo nome da Tarefa para ser Deletada. Retorna um booleano. */
    /*public boolean delete(ParNomeId parNomeId ,String nomeTarefa)throws Exception{
        
        ArrayList<Tarefa> t = read(parNomeId);
        Tarefa delete = new Tarefa();

        for(int i = 0; i<t.size(); i++){
            if(t.get(i).getNome().equals(nomeTarefa)){
                delete = t.get(i);   
            }
        }
        
        return super.delete(delete.getId()) ? arvoreB.delete(new ParIDcIDt(parNomeId.getId(), delete.getId())) : false;      
    }*/

    public boolean delete(Tarefa tarefa){
        boolean result = false;
        try{
            result = super.delete(tarefa.getId()) ? arvoreB.delete(new ParIDcIDt(tarefa.getIDCategoria(), tarefa.getId())) : false;
            String [] chaves = stopWords.stopWordsCheck(tarefa.getNome());
            
            for(int i = 0; i < chaves.length; i++){
                chaves[i] = chaves[i].toLowerCase();
                stopWords.lista.delete(chaves[i], tarefa.getId());
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public ArrayList<Tarefa> listar(String titulo) throws Exception{
        ArrayList<Tarefa> tarefas = new ArrayList<>();
        String [] chaves = stopWords.stopWordsCheck(titulo);
        for(int i = 0; i < chaves.length; i++){
            System.out.println(chaves[i]);
            if(chaves[i] != "" && chaves[i] != " "){
                try{
                    ElementoLista [] elementoEncontrados;
                    elementoEncontrados = stopWords.lista.read(chaves[i]);
                    if(elementoEncontrados == null){
                        System.out.println(AMARELO + "Nenhuma tarefa encontrada com esse termo" + RESET);
                    } 
                    else {
                        for(int j = 0; j < elementoEncontrados.length; j++){
                            Tarefa tarefa = super.read(elementoEncontrados[j].getId());
                            boolean existe = false;
                            for(int k = 0; k < tarefas.size(); k++){
                                if(tarefas.get(k).getId() == tarefa.getId()){
                                    existe = true;
                                    k = tarefas.size();
                                }
                            }
                            if(!existe){
                                tarefas.add(tarefa);
                            }
                        }
                    }
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
         }
        }
        return tarefas;
    }
    
}
