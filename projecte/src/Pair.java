/**
 * Classe Pair que agrupa per key value dos objectes
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */
public class Pair<U, V> {
    /**
     * Key del Pair
     */
    private U objecte;
    /**
     * Value del Pair
     */
    private V valor;

    /**
     * Constructor
     * @param key U
     * @param value V
     */
    Pair(U key, V value) {
        this.objecte = key;
        this.valor = value;
    }

    /**
     * @return l'objecte key del Pair
     */
    public U objecte() {
        return this.objecte;
    }

    /**
     * @return l'objecte value del Pair
     */
    public V valor() {
        return this.valor;
    }
}
