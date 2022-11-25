/**
 * Snake representa que és un Animal del tipus Snake
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class Snake extends Animal {
    /**
     * Constructor
     * @param e Especie
     */
    Snake(Especie e) {
        super(e);
    }

    /**
     * Constructor
     * @param a Animal
     */
    Snake(Animal a) {
        //atributs pare al cridar fecundar
        super(a);
    }

}
