/**
 * Llop representa que és un Animal del tipus Llop
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class Llop extends Animal {
    /**
     * Constructor
     * @param e Especie
     */
    Llop(Especie e) {
        super(e);
    }

    /**
     * Constructor
     * @param a Animal
     */
    Llop(Animal a) {
        //atributs pare al cridar fecundar
        super(a);
    }


}
