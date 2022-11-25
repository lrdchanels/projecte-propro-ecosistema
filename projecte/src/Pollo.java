/**
 * Pollo representa que és un Animal del tipus Pollo
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class Pollo extends Animal {
    /**
     * Constructor
     * @param e Especie
     */
    Pollo(Especie e) {
        super(e);
    }

    /**
     * Constructor
     * @param a Animal
     */
    Pollo(Animal a) {
        //atributs pare al cridar fecundar
        super(a);
    }

}
