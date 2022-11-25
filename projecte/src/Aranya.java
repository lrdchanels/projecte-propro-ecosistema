/**
 * Aranya representa que és un Animal del tipus Aranya
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class Aranya extends Animal {
    /**
     * Constructor
     * @param e Especie
     */
    Aranya(Especie e) {
        super(e);
    }

    /**
     * Constructor
     * @param a Animal
     */
    Aranya(Animal a) {
        //atributs pare al cridar fecundar
        super(a);
    }
}
