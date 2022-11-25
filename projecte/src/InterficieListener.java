import java.util.ArrayList;

/**
 * InterficieListener ens declara els atributs i mètodes de la classes Descriptiva i Mapa 
 * per a poder crear interficies
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public interface InterficieListener {
    /**
     * Llista especies
     */
    ArrayList<Especie> especies = new ArrayList<>();
    /**
     * Llista terrenys
     */
    ArrayList<Terreny> terrenys = new ArrayList<>();
    /**
     * Llista animals
     */
    ArrayList<Animal> animals = new ArrayList<>();
    /**
     * Llista vegetals
     */
    ArrayList<Vegetal> vegetals = new ArrayList<>();
    /**
     * Llista virus
     */
    ArrayList<Virus> virus = new ArrayList<>();
    /**
     * Llista parasits
     */
    ArrayList<Parasit> parasits = new ArrayList<>();

    /**
     * Actualitza el terreny
     * @param t Terreny
     */
    void actualitzar(Terreny t);

    /**
     * Actualitza l'animal
     * @param a Animal
     */
    void actualitzar(Animal a);

    /**
     * Actualitza el vegetal
     * @param v Vegetal
     */
    void actualitzar(Vegetal v);

    /**
     * Actualitza el virus
     * @param v Virus
     */
    void actualitzar(Virus v);

    /**
     * Actualitza el paràsit
     * @param p Parasit
     */
    void actualitzar(Parasit p);

    /**
     * Afegeix el terreny
     * @param t Terreny
     */
    void afegir(Terreny t);

    /**
     * Afegeix l'animal
     * @param a Animal
     */
    void afegir(Animal a);

    /**
     * Afegeix el vegetal
     * @param v Vegetal
     */
    void afegir(Vegetal v);

    /**
     * Afegeix el virus
     * @param v Virus
     */
    void afegir(Virus v);

    /**
     * Afegeix el paràsit
     * @param p Parasit
     */
    void afegir(Parasit p);

    /**
     * Elimina l'animal
     * @param a Animal
     */
    void eliminar(Animal a);

    /**
     * Elimina el vegetal
     * @param v Vegetal
     */
    void eliminar(Vegetal v);

    /**
     * Elimina el virus
     * @param v Virus
     */
    void eliminar(Virus v);

    /**
     * Elimina el paràsit
     * @param p Parasit
     */
    void eliminar(Parasit p);

    /**
     * S'ha infectat l'animal amb un virus
     * @param a Animal
     * @param v Virus
     */
    void infectar(Animal a, Virus v);

    /**
     * S'ha contagiat l'animal amb un paràsit
     * @param a Animal
     * @param v Parasit
     */
    void contagiar(Animal a, Parasit p);

    /**
     * Inicialitza l'ecosistema
     * @param ecosistema Ecosistema
     * @param automatic Boolean
     */
    void iniciar(Ecosistema ecosistema, Boolean automatic);
}
