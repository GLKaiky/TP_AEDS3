import java.lang.reflect.Constructor;


public class ArquivoTarefa extends Arquivo<Tarefa> {
    /* 
    * Creates a new FileHandler with its own RandomAccessFile.
    * 
    * Parameters:
    *   - constructor: Constructor for the Tarefa class.
    *     WARNING: An error occurs if the constructor is null or if more than one 
    *     argument is passed.
    * 
    *   - name: Base name shared by all related files. The following files are created:
    *       - \\dados\\{name}.db
    *       - \\dados\\{name}.hash_d.db
    *       - \\dados\\{name}.hash_c.db
    * 
    * WARNINGS:
    *   - This class does NOT handle synchronization. Multiple threads using the 
    *     same instance may cause inconsistent behavior.
    *   
    *   - No static read-write locks are implemented. Simultaneous access to multiple
    *     instances can lead to data corruption during concurrent read/write operations.
    */
    public ArquivoTarefa(Constructor<Tarefa> construtor, String name) throws Exception {
        // Handles ParIdEndereco files and Arquivo files
        super(construtor, name);
    }
}