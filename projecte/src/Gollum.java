/**
 * Gollum representa que és un Animal del tipus Gollum
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */
public class Gollum extends Animal {
    /**
     * Constructor
     * @param e Especie
     */
    Gollum(Especie e) {
        super(e);
    }

    /**
     * Constructor
     * @param a Animal
     */
    Gollum(Animal a) {
        //atributs pare al cridar fecundar
        super(a);
    }
}
