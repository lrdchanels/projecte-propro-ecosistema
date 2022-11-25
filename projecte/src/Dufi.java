/**
 * Dufi representa que és un Animal del tipus Dufi
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class Dufi extends Animal {
    /**
     * Constructor
     * @param e Especie
     */
    Dufi(Especie e) {
        super(e);
    }

    /**
     * Constructor
     * @param a Animal
     */
    Dufi(Animal x) {
        //atributs pare al cridar fecundar
        super(x);
    }

}
