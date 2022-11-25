/**
 * Placton representa que és un Vegetal del tipus Placton
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class Cactus extends Vegetal {
    /**
     * Constructor
     * @param e Especie
     */
    Cactus(Especie e) {
        super(e);
    }

    /**
     * Constructor
     * @param v Vegetal
     */
    Cactus(Vegetal v) {
        //atributs pare al cridar fecundar
        super(v);
    }
}
