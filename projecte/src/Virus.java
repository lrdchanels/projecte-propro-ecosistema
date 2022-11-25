/**
 * Virus representa que un organisme és de tipus Virus
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public abstract class Virus extends Organisme {

    /**
     * Vida que treu el virus per a cada unitat de temps
     */
    protected double multiplicador;

    /**
     * Constructor
     * @param e Especie
     */
    Virus(Especie e) {
        super(); 
        this.multiplicador = 0.0;
        this.especie = e;
        this.vida = 100.0;
    }

    /**
     * Constructor
     * @param v Virus
     */
    Virus(Virus v) {
        this(v.especie);
        this.posicio = v.posicio();
        this.posicio_anterior = v.posicioAnterior();
    }
}
