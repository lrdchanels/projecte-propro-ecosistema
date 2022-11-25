import java.util.ArrayList;
import org.json.simple.JSONObject;

/**
 * Especie Tots els organismes formen part d'una espècie, això ens permet tenir variables comunes entre espècies
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class Especie {
    private String nom;
    private ArrayList<Especie> aliments;
    private ArrayList<Especie> enemics;
    private ArrayList<TipusTerreny> tipusTerreny;

    private int CRIES_MINIM; // Cries mínimes que pot tenir una animal.
    private int CRIES_MAXIM; // Cries màximes que pot tenir un animal.
    private int VISIBILITAT; // Poden veure o ensumar a una distància.
    private int VELOCITAT; // Velocitat en la que l'animal pot córrer.
    private int ESPAI_CASELLA; // Espai que ocupa l'animal a la casella.
    private int VALOR_NUTRITIU; // Lo que atipa quan se'l mengen
    private int EDAT_REPRODUCCIO; // Edat en que l'animal es comença a reproduir.
    private String ORIGEN; // Origen de l'especie
    private double VELOCITAT_REPRODUCCIO; // Velocitat en que es reprodueix.
    private double COST_VIDA; // Cost de vida per unitat de temps.

    /**
     * Constructor inicialitza amb totes les dades agafades del fitxer de configuració
     * @param data JSONObject
     */
    Especie(JSONObject data) {
        this.aliments = new ArrayList<>();
        this.enemics = new ArrayList<>();
        this.tipusTerreny = new ArrayList<>();

        // Carreguem totes les dades
        this.nom = (String)data.get("nom");
        this.CRIES_MINIM = (int)(long)data.get("cries_minim");
        this.CRIES_MAXIM = (int)(long)data.get("cries_maxim");
        this.VISIBILITAT = (int)(long)data.get("visibilitat");
        this.VELOCITAT = (int)(long)data.get("velocitat");
        this.ESPAI_CASELLA = (int)(long)data.get("espai_casella");
        this.VALOR_NUTRITIU = (int)(long)data.get("valor_nutritiu");
        this.EDAT_REPRODUCCIO = (int)(long)data.get("edat_reproduccio");
        this.ORIGEN = (String)data.get("origen");
        this.VELOCITAT_REPRODUCCIO = (double)(double)data.get("velocitat_reproduccio");
        this.COST_VIDA = (double)(double)data.get("cost_vida");
    }

    /**
     * Torna el nom de l'especie
     * @return String
     */
    public String nom() {
        return this.nom;
    }

    /**
     * Torna els tipus de terreny
     * @return Torna els tipus de terreny
     */
    public ArrayList<TipusTerreny> tipusTerreny() {
        return this.tipusTerreny;
    }


    /**
     * Torna l'espai que ocupa la especie
     * @return int
     */
    public int espaiCasella() {
        return this.ESPAI_CASELLA;
    }

    /**
     * Torna la visibilitat de la especie
     * @return int
     */
    public int visibilitat() {
        return this.VISIBILITAT;
    }

    /**
     * Torna el cost de vida de l'especie
     * @return Double
     */
    public Double costVida() {
        return this.COST_VIDA;
    }

    /**
     * Torna la velocitat de l'especie
     * @return int
     */
    public int velocitat() {
        return this.VELOCITAT;
    }

    /**
     * Torna la velocitat de reproducció de l'especie
     * @return double
     */
    public double velocitatReproduccio() {
        return this.VELOCITAT_REPRODUCCIO;
    }

    /**
     * Torna el valor nutritiu de l'especie
     * @return double
     */
    public double valorNutritiu() {
        return this.VALOR_NUTRITIU;
    }

    /**
     * Torna el mínim de cries de l'especie
     * @return int
     */
    public int minimCries() {
        return this.CRIES_MINIM;
    }

    /**
     * Torna el màxim de cries de l'especie
     * @return int
     */
    public int maximCries() {
        return this.CRIES_MAXIM;
    }

    /**
     * Torna els enemics de la especie
     * @return Torna els enemics de la especie
     */
    public ArrayList<Especie> enemics() {
        return this.enemics;
    }

    /**
     * Afegeix un aliment al llistat
     * @param aliment Especie
     */
    public void afegirAliment(Especie aliment) {
        aliments.add(aliment);
    }

    /**
     * Afegeix un enemic al llistat
     * @param enemic Especie
     */
    public void afegirEnemic(Especie enemic) {
        enemics.add(enemic);
    }

    /**
     * Afegeix un terreny al llistat
     * @param t TipusTerreny
     */
    public void afegirTipusTerreny(TipusTerreny t) {
        tipusTerreny.add(t);
    }

    /**
     * Retorna cert si l'animal es pot reproduir
     * @param edat double
     * @return boolean
     */
    public boolean esPotReproduir(double edat) {
        return edat >= EDAT_REPRODUCCIO;
    }

    /**
     * Retorna els aliments d'una especie
     * @return Retorna els aliments d'una especie
     */
    public ArrayList<Especie> aliments() {
        return this.aliments;
    }

    /**
     * Retorna si una especie és depredadora
     * @return Boolean
     */
    public Boolean esDepredador() {
       return this.ORIGEN.equals("Animal") ? true : false;
    }

    /**
     * Retorna l'origen de l'animal
     * @return String
     */
    public String origen() {
        return this.ORIGEN;
     }

    /**
     * Retorna si l'espècie passada per paràmetre és igual a l'espècie actual
     * @param o Object
     * @return Boolean
     */
    @Override
    public boolean equals(Object o) {
        Especie e = (Especie)o;
        return nom == e.nom;
    }
}
