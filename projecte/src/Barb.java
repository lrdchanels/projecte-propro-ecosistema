/**
 * Barb representa que és un Animal del tipus Barb
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class Barb extends Animal {
    /**
     * Constructor
     * @param e Especie
     */
    Barb(Especie e) {
        super(e);
    }

    /**
     * Constructor
     * @param a Animal
     */
    Barb(Animal a) {
        //atributs pare al cridar fecundar
        super(a);
    }
}

