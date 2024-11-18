package ListaInvertida;

/*********
 * LISTA INVERTIDA
 * String chave, int dado
 * 
 * Os nomes dos métodos foram mantidos em inglês
 * apenas para manter a coerência com o resto da
 * disciplina:
 * - boolean create(String chave, int dado)
 * - int[] read(int chave)
 * - boolean delete(String chave, int dado)
 * 
 * Implementado pelo Prof. Marcos Kutova
 * v1.0 - 2020
 */

public class ElementoLista implements Comparable<ElementoLista>, Cloneable {
    
    private int id;
    private float frequencia;

    public ElementoLista(int i, float f) {
        this.id = i;
        this.frequencia = f;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(float frequencia) {
        this.frequencia = frequencia;
    }

    @Override
    public String toString() {
        return "("+this.id+";"+this.frequencia+")";
    }

    @Override
    public ElementoLista clone() {
        try {
            return (ElementoLista) super.clone();
        } catch (CloneNotSupportedException e) {
            // Tratamento de exceção se a clonagem falhar
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int compareTo(ElementoLista outro) {
        return Integer.compare(this.id, outro.id);
    }
}
