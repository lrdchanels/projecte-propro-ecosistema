/**
 * Yeti representa que és un Animal del tipus Yeti
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class Yeti extends Animal {
    /**
     * Constructor
     * @param e Especie
     */
    Yeti(Especie e) {
        super(e);
    }

    /**
     * Constructor
     * @param a Animal
     */
    Yeti(Animal a) {
        //atributs pare al cridar fecundar
        super(a);
    }

}
