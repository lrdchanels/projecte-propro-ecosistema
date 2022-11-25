/**
 * PeixGlobus representa que és un Animal del tipus PeixGlobus
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class PeixGlobus extends Animal {
    /**
     * Constructor
     * @param e Especie
     */
    PeixGlobus(Especie e) {
        super(e);
    }

    /**
     * Constructor
     * @param a Animal
     */
    PeixGlobus(Animal a) {
        //atributs pare al cridar fecundar
        super(a);
    }
}
