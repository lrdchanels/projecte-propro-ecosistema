/**
 * Vegetal representa que un organisme és de tipus Vegetal
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public abstract class Vegetal extends Organisme {

    /**
     * Constructor
     * @param e Especie
     */
    Vegetal(Especie e) {
        super(); 
        this.vida = 100;
        this.especie = e;
    }

    /**
     * Constructor
     * @param v Vegetal
     */
    Vegetal(Vegetal v) {
        this(v.especie);
    }
}
