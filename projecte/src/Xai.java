/**
 * Xai representa que és un Animal del tipus Xai
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class Xai extends Animal {
    /**
     * Constructor
     * @param e Especie
     */
    Xai(Especie e) {
        super(e);
    }

    /**
     * Constructor
     * @param a Animal
     */
    Xai(Animal a) {
        //atributs pare al cridar fecundar
        super(a);
    }

}
