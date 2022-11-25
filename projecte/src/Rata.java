/**
 * Rata representa que és un Animal del tipus Rata
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class Rata extends Animal {
    /**
     * Constructor
     * @param e Especie
     */
    Rata(Especie e) {
        super(e);
    }

    /**
     * Constructor
     * @param a Animal
     */
    Rata(Animal a) {
        //atributs pare al cridar fecundar
        super(a);
    }

}
