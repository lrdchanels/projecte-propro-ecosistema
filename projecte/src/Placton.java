/**
 * Placton representa que és un Vegetal del tipus Placton
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class Placton extends Vegetal {
    /**
     * Constructor
     * @param e Especie
     */
    Placton(Especie e) {
        super(e);
    }

    /**
     * Constructor
     * @param v Vegetal
     */
    Placton(Vegetal v) {
        //atributs pare al cridar fecundar
        super(v);
    }
}
