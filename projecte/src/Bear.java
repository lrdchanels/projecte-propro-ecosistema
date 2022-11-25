/**
 * Bear representa que és un Animal del tipus Bear
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class Bear extends Animal {
    /**
     * Constructor
     * @param e Especie
     */
    Bear(Especie e) {
        super(e);
    }

    /**
     * Constructor
     * @param a Animal
     */
    Bear(Animal x) {
        //atributs pare al cridar fecundar
        super(x);
    }

}
