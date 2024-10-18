/* Classe com o foco em Popular as categorias necessarias para criação do registro de tarefas*/

package seeds;
import java.util.ArrayList;
import App.App;
import ArquivoClass.*;
import Tarefa.*; 

public class Popular {

    private Arquivo<Categoria> categorias;
    private Categoria categoria;
    
    /* População do Sistema */
    public Popular() throws Exception{
        this.categoria = null;

        this.categorias = new Arquivo<>(Categoria.class.getConstructor(), "categorias");
    }   
    
    
    /* Criando Categoria */
    public boolean create(String nomeCategoria)throws Exception{
        try{    
            this.categoria = new Categoria(nomeCategoria);
            categorias.create(categoria);
            categoria = null;
            
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new Exception();
            
        }
        return true;
    } 

    /* Listar as Categoria */
    public void list()throws Exception{
        ArrayList<Categoria> objects = new ArrayList<>();
        objects = categorias.list();
        
        for(int i = 0; i<objects.size(); i++){
            System.out.println("[ " + objects.get(i).getNome() + " ]");
        }
    }

}
