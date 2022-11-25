import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.Thread.sleep;

/**
 * Descriptiva s'encarrega d'actualitzar el taulell descriptiu de l'ecosistema en cada unitat de temps
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class Descriptiva implements InterficieListener {
    /**
     * Reset.
     */
    public static final String ANSI_RESET = "\u001B[0m";
    /**
     * Color vermell.
     */
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m"; 
    /**
     * Color verd.
     */
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    /**
     * Color blau.
     */
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m"; 
    /**
     * Color negre.
     */
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    /**
     * Color groc
     */
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";

    /**
     * Velocitat.
     */
    private int VELOCITAT;

    /**
     * Constructor
     * @param conf Configuration
     */
    Descriptiva(Configuration conf) {
        this.VELOCITAT = conf.VELOCITAT;
    }

    /**
     * Actualitza el terreny
     * @param terreny Terreny
     */
    @Override
    public void actualitzar(Terreny terreny) {
        
    }

    /**
     * Actualitza el vegetal
     * @param vegetal Vegetal
     */
    @Override
    public void actualitzar(Vegetal vegetal) {
        
    }

    /**
     * Actualitza el virus
     * @param virus Virus
     */
    @Override
    public void actualitzar(Virus virus) {
        
    }

    /**
     * Actualitza el paràsit
     * @param parasit Parasit
     */
    @Override
    public void actualitzar(Parasit parasit) {
        
    }

    /**
     * Actualitza l'animal
     * @param animal Animal
     */
    @Override
    public void actualitzar(Animal animal) {
        if(animal.posicioAnterior() != null) {
            System.out.print(ANSI_CYAN_BACKGROUND + "[ " + String.format("%02d", animal.posicioAnterior().x()) + ", " + String.format("%02d", animal.posicioAnterior().y()) + " ]");
            System.out.print(" => ");
            System.out.print("[ " + String.format("%02d", animal.posicio().x()) + ", " + String.format("%02d", animal.posicio().y()) + " ] ");
            System.out.print("L'animal tipus " + animal.especie.nom() + " s'ha mogut");
            System.out.println(ANSI_RESET);
        }
    }

    /**
     * Afegeix el terreny
     * @param t Terreny
     */
    @Override
    public void afegir(Terreny t) {
        
    }

    /**
     * Afegeix l'animal
     * @param a Animal
     */
    @Override
    public void afegir(Animal a) {
        System.out.print(ANSI_GREEN_BACKGROUND + "[ " + String.format("%02d", a.posicio().x()) + ", " + String.format("%02d", a.posicio().y()) + " ] ");
        System.out.print("L'Animal tipus " + a.especie.nom() + " s'ha afegit");
        System.out.println(ANSI_RESET);
    }

    /**
     * Afegeix el vegetal
     * @param v Vegetal
     */
    @Override
    public void afegir(Vegetal v) {
        System.out.print(ANSI_GREEN_BACKGROUND + "[ " + String.format("%02d", v.posicio().x()) + ", " + String.format("%02d", v.posicio().y()) + " ] ");
        System.out.print("El vegetal tipus " + v.especie.nom() + " s'ha afegit");
        System.out.println(ANSI_RESET);
    }

    /**
     * Afegeix el virus
     * @param v Virus
     */
    @Override
    public void afegir(Virus v) {
        System.out.print(ANSI_GREEN_BACKGROUND + "[ " + String.format("%02d", v.posicio().x()) + ", " + String.format("%02d", v.posicio().y()) + " ] ");
        System.out.print("El Virus tipus " + v.especie.nom() + " s'ha afegit");
        System.out.println(ANSI_RESET);
    }

    /**
     * Afegeix el paràsit
     * @param p Parasit
     */
    @Override
    public void afegir(Parasit p) {
        System.out.print(ANSI_GREEN_BACKGROUND + "[ " + String.format("%02d", p.posicio().x()) + ", " + String.format("%02d", p.posicio().y()) + " ] ");
        System.out.print("El Paràsit tipus " + p.especie.nom() + " s'ha afegit");
        System.out.println(ANSI_RESET);
    }

    /**
     * Elimina a l'animal
     * @param a Animal
     */
    @Override
    public void eliminar(Animal a) {
        System.out.print(ANSI_RED_BACKGROUND + "[ " + String.format("%02d", a.posicio().x()) + ", " + String.format("%02d", a.posicio().y()) + " ] ");
        System.out.print("L'Animal tipus " + a.especie.nom() + " s'ha mort");
        System.out.println(ANSI_RESET);
    }

    /**
     * Elimina el vegetal
     * @param v Vegetal
     */
    @Override
    public void eliminar(Vegetal v) {
        System.out.print(ANSI_RED_BACKGROUND + "[ " + String.format("%02d", v.posicio().x()) + ", " + String.format("%02d", v.posicio().y()) + " ] ");
        System.out.print("El Vegetal tipus " + v.especie.nom() + " s'ha mort");
        System.out.println(ANSI_RESET);
    }

    /**
     * Elimina el virus
     * @param v Virus
     */
    @Override
    public void eliminar(Virus v) {
        System.out.print(ANSI_RED_BACKGROUND + "[ " + String.format("%02d", v.posicio().x()) + ", " + String.format("%02d", v.posicio().y()) + " ] ");
        System.out.print("El virus tipus " + v.especie.nom() + " s'ha mort");
        System.out.println(ANSI_RESET);
    }

    /**
     * Elimina el paràstit
     * @param p Parasit
     */
    @Override
    public void eliminar(Parasit p) {
        System.out.print(ANSI_RED_BACKGROUND + "[ " + String.format("%02d", p.posicio().x()) + ", " + String.format("%02d", p.posicio().y()) + " ] ");
        System.out.print("El Paràsit tipus " + p.especie.nom() + " s'ha mort");
        System.out.println(ANSI_RESET);
    }

    /**
     * S'ha infectat l'animal amb un virus
     * @param a Animal
     * @param v Virus
     */
    @Override
    public void infectar(Animal a, Virus v) {
        System.out.print(ANSI_YELLOW_BACKGROUND + "[ " + String.format("%02d", a.posicio().x()) + ", " + String.format("%02d", a.posicio().y()) + " ] ");
        System.out.print("L'Animal tipus " + a.especie.nom() + " s'ha infectat amb el virus " + v.especie.nom());
        System.out.println(ANSI_RESET);
    }

    /**
     * S'ha contagiat l'animal amb un paràsit
     * @param a Animal
     * @param v Parasit
     */
    @Override
    public void contagiar(Animal a, Parasit p) {
        System.out.print(ANSI_YELLOW_BACKGROUND + "[ " + String.format("%02d", a.posicio().x()) + ", " + String.format("%02d", a.posicio().y()) + " ] ");
        System.out.print("L'Animal tipus " + a.especie.nom() + " s'ha contagiat amb el paràsit " + p.especie.nom());
        System.out.println(ANSI_RESET);
    }

    /**
     * Inicia l'ecosistema sigui manualment o automàticament
     * @param ecosistema Ecosistema
     * @param automatica Boolean
     */
    @Override
    public void iniciar(Ecosistema ecosistema, Boolean automatica) {
        try {
            if(automatica) {
                while(true) {
                    ecosistema.moviment();
                    System.out.println(ANSI_BLACK_BACKGROUND); 
                    sleep(this.VELOCITAT);
                }
            } else { 
                String text = "";
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                ecosistema.moviment();
                while(!text.equals("exit")) {
                    text = reader.readLine();
                    System.out.print("\033[H\033[2J");  
                    System.out.flush(); 
                    ecosistema.moviment();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
