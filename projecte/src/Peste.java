/**
 * Peste representa que és un Virus del tipus Peste
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class Peste extends Virus {
    /**
     * Constructor
     * @param e Especie
     */
    Peste(Especie e) {
        super(e);
    }

    /**
     * Constructor
     * @param v Virus
     */
    Peste(Virus v) {
        //atributs pare al cridar fecundar
        super(v);
    }
}
