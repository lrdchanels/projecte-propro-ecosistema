/**
 * Parasit representa que un organisme és de tipus Parasit
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public abstract class Parasit extends Organisme {
    /**
     * Constructor
     * @param e Especie
     */
    Parasit(Especie e) {
        super(); 
        this.vida = 100.0;
        this.especie = e;
    }

    /**
     * Constructor
     * @param p Parasit
     */
    Parasit(Parasit p) {
        this(p.especie);
        this.posicio = p.posicio();
        this.posicio_anterior = p.posicioAnterior();
    }
}
