/**
 * Llobarro que és un Animal del tipus Llobarro
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */


public class Llobarro extends Animal {
    /**
     * Constructor
     * @param e Especie
     */
    Llobarro(Especie e) {
        super(e);
    }

    /**
     * Constructor
     * @param a Animal
     */
    Llobarro(Animal a) {
        //atributs pare al cridar fecundar
        super(a);
    }
}
