import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Animal representa que un organisme és de tipus Animal
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public abstract class Animal extends Organisme {
    /**
     * Sexe de l'animal: F o M.
     */
    protected char SEXE;
    /**
     * Aliment que te l'animal al cos.
     */
    protected Double alimentacio;
    /**
     * Posicio anterior de l'animal.
     */
    protected Integer posicioAnterior;
    /**
     * Virus actius que maten organismes.
     */
    protected ArrayList<Virus> virus;
    /**
     * Parasit actius que maten organismes.
     */
    protected ArrayList<Parasit> parasits;

    /**
     * Constructor
     * @param e Especie
     */
    Animal(Especie e) {
        super();
        
        this.alimentacio = 100.0;
        this.especie = e;
        this.vida = 100;
        this.edat = 0.0;
        this.SEXE = (new Random()).nextBoolean() ? 'F' : 'M';
        this.virus = new ArrayList<>();
        this.parasits = new ArrayList<>();
        this.posicioAnterior = 0;
        this.reproduccio = 0.0;
    }

    /**
     * Constructor
     * @param a Animal
     */
    Animal(Animal a) {
        this(a.especie);
        this.posicio = a.posicio();
        this.posicio_anterior = a.posicioAnterior();
    }

    /**
     * Redueix l'alimentació de l'animal per ordre natural
     */
    public void modificarAlimentacio() {
        this.modificarAlimentacio(-0.2);
    }

    /**
     * Augmenta o redueix l'alimentació de l'animal
     * @param n double
     */
    public void modificarAlimentacio(double n) {
        this.alimentacio += n;
        if(this.alimentacio > 100) this.alimentacio = 100.0;
        if(this.alimentacio <= 0) { matar(); return; }
    }

    /**
     * Posa o no un paràsit a l'animal
     * @param especies ArrayList<Especie>
     * @return si n'ha agafat algun i quin
     */
    public Parasit agafarParasit(ArrayList<Especie> especies) {
        for(Especie e : especies) {
            if(e.origen().equals("Parasit")) {
                if(new Random().nextDouble()*100 <= this.numeroCries()) {
                    try {
                        System.out.println("ha agafat paràsit");
                        Parasit parasit = (Parasit)Class.forName(e.nom()).getDeclaredConstructor(Especie.class).newInstance(e);
                        afegirParasit(parasit);
                        return parasit;
                    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                            | InvocationTargetException | NoSuchMethodException | SecurityException
                            | ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Avança la reproducció de l'organisme si està disponible i és femella
     */
    public void modificarReproduccio() {
        if(this.esPotReproduir() && this.SEXE == 'F') {
            this.reproduccio += this.especie.velocitatReproduccio();
        }
    }

    /**
     * Modifica la vida de l'animal segons el cost de vida
     */
    @Override
    public void modificarVida() {
        super.modificarVida();

        for(Virus v : virus) {
            this.vida += v.especie.valorNutritiu();
        }

        for(Parasit p : parasits) {
            System.out.println("te parasit al bucle");
            this.vida += p.especie.valorNutritiu();
        }

        if(this.vida < 0) this.vida = 0.0;
    }

    /**
     * Afegir un virus a l'animal
     * @param virus Virus
     */
    public void afegirVirus(Virus virus) {
        this.virus.add(virus);
    }

    /**
     * Afegeix un paràsit a l'animal
     * @param parasit Parasit
     */
    public void afegirParasit(Parasit parasit) {
        this.parasits.add(parasit);
    }
}
