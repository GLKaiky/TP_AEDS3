package ArquivoClass;

import Tarefa.Etiqueta;
import ArvoreB.ArvoreBMais;
import ArvoreB.ParEtiquetaId;
import Tarefa.Tarefa;
import java.io.File;

import java.util.ArrayList;

public class ArquivoEtiqueta extends Arquivo<Etiqueta> {
    public static final String AMARELO = "\033[33m"; // Amarelo
    public static final String VERDE = "\033[32m"; // Verde
    public static final String RESET = "\033[0m"; // Resetar cor
    ArvoreBMais<ParEtiquetaId> arvoreB;

    /* Criando o Arquivo de Etiqueta */
    public ArquivoEtiqueta()throws Exception{
        super(Etiqueta.class.getConstructor(), "ArquivoEtiqueta");
        try{
            arvoreB = new ArvoreBMais<>(ParEtiquetaId.class.getConstructor(), 5, "./dados/ArvoresEtiquetas");
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new Exception();
        }
    }

    /* CRUD DE ETIQUETA */

    /* Método Publico para a criação de Etiqueta. Retorna a Etiqueta criada */
    public int create(String nomeEtiqueta)throws Exception{
        Etiqueta etiqueta = new Etiqueta(nomeEtiqueta);
        return this.create1(etiqueta);
    }
    
    /* Método Privado da criação de Etiqueta. Retorna o ID da Etiqueta */
    private int create1(Etiqueta etiqueta) throws Exception{
        int id = super.create(etiqueta);
        etiqueta.setId(id);
        try{
            arvoreB.create(new ParEtiquetaId(etiqueta.getNome(),etiqueta.getId()));
        }catch(Exception e){
            System.out.println("Erro na criação de uma nova etiqueta");
            System.out.println(e.getMessage());
        }
        return id;
    } 
    
    /* Método de leitura listando as Tarefas da Etiqueta passada como parametro. Retorna as Tarefas */
    public ArrayList<Tarefa> read(String nomeEtiqueta)throws Exception{
        ArrayList<Tarefa> t = new ArrayList<>();
        ArquivoTarefas tarefas = new ArquivoTarefas();
        try{
            ArrayList<ParEtiquetaId> etiqueta = arvoreB.read(new ParEtiquetaId(nomeEtiqueta));
        
            /*Se a Etiqueta estiver vazia, incapaz de fazer o método*/
            if(etiqueta.isEmpty()){
                throw new Exception("Etiqueta inxistente");
            }
            t = tarefas.read(etiqueta.get(0));   
        }catch(Exception e){
            System.out.println("Erro na leitura do Arquivo");
            System.out.println(e.getMessage());
        }
        return t;
    }

    /* Método de atualização do nome de uma Etiqueta. Retornando se foi feito com Sucesso ou Não. */
    public boolean update(String nomeEtiqueta, String novaEtiqueta)throws Exception{
        Etiqueta eti = new Etiqueta(novaEtiqueta);
        
        try{
            ArrayList<ParEtiquetaId> etiqueta = arvoreB.read(new ParEtiquetaId(nomeEtiqueta));
            /*Se a Etiqueta estiver vazia, incapaz de fazer o método*/
            if(etiqueta.isEmpty()){
                throw new Exception("Etiqueta Inexistente");
            }
            eti.setId(etiqueta.get(0).getId());
            
            if(super.update(eti)){
                System.out.println("Atualizo");
            }

            
            arvoreB.delete(etiqueta.get(0));
            arvoreB.create(new ParEtiquetaId(eti.getNome(), eti.getId()));
        }
        catch (Exception e){
            System.out.println("Erro no update do Arquivo");
            System.out.println(e.getMessage());
        }
        
        return true;
    }

    /* Método de atualização chamando o Método Update de Tarefa. Retorna um booleano. */
    /*public boolean updateTarefa(String nomeEtiqueta, String nomeTarefa, Tarefa updateTarefa)throws Exception{
        ArrayList<ParEtiquetaId> etiqueta = arvoreB.read(new ParEtiquetaId(nomeEtiqueta));
        ArquivoTarefas tarefas = new ArquivoTarefas();
        
        try{
            //Se a Etiqueta estiver vazia, incapaz de fazer o método
            if(etiqueta.isEmpty()){
                throw new Exception("Etiqueta Inexistente");
            }
            
        }
        catch(Exception e){
            System.out.println("Erro no updateTarefa");
            System.out.println(e.getMessage());
        }
        
        return tarefas.update(etiqueta.get(0), nomeTarefa, updateTarefa);
    }*/

    /* Método de Delete. Procura pelo nome. Retorna Booleano */
    /*public boolean deleteTarefa(String nomeEtiqueta, String nomeTarefa)throws Exception{
        ArrayList<ParEtiquetaId> etiqueta = arvoreB.read(new ParEtiquetaId(nomeEtiqueta));
        ArquivoTarefas tarefas = new ArquivoTarefas();
        
        try{

        Se a Etiqueta estiver vazia, incapaz de fazer o método
            if(etiqueta.isEmpty()){
                throw new Exception("Etiqueta Inexistente");
            }
        }
        catch(Exception e){
            System.out.println("Erro no Delete");
            System.out.println(e.getMessage());
        }
        
        return tarefas.delete(etiqueta.get(0), nomeTarefa);
    }*/

    /* Método de Deletar Etiqueta. Procura pelo nome da Etiqueta e a deleta. Retorna booleano */
    public boolean delete(String nomeEtiqueta) throws Exception{
        try{
            ArrayList<ParEtiquetaId> eti = arvoreB.read(new ParEtiquetaId(nomeEtiqueta));

            /*Se a Etiqueta estiver vazia, incapaz de fazer o método*/
            if(eti.isEmpty()){
                throw new Exception("Etiqueta Inesistente");
            }

            ArquivoTarefas tarefas = new ArquivoTarefas();
            ArrayList<Tarefa> t = tarefas.read(eti.get(0));
    
            if(!t.isEmpty())
                throw new Exception("Tarefas existentes dentro desta etiqueta");
            
            return super.delete(eti.get(0).getId()) ? arvoreB.delete(eti.get(0)) : false;
        }catch(Exception e){
            System.out.println("Erro em deletar");
            System.out.println(e.getMessage());
        }
        return false;
    }

    /* Listando as etiqueta */
    public ArrayList<Etiqueta> listar() throws Exception{
        ArrayList<Etiqueta> etiquetas = new ArrayList<>();
        try{
             etiquetas = super.list();

            if(etiquetas.isEmpty())
                throw new Exception("Etiqueta ainda não foram criadas");
            
            for(int i = 0; i<etiquetas.size(); i++){
                System.out.println(VERDE + "Indice: " + etiquetas.get(i).getId() + " Nome da Etiqueta: " + etiquetas.get(i).getNome() + RESET);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return etiquetas;
    }

}
