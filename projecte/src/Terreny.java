import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * Cada cel·la del nostre ecosistema en realitat és un terreny on a cada terreny hi ha animals, vegetals...
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class Terreny {
    /**
     * Capacitat total del terreny.
     */
    private int capacitat;
    /**
     * Tipus del terreny.
     */
    protected TipusTerreny tipusTerreny;
    /**
     * Animals presents al terreny.
     */
    private ArrayList<Animal> animals;
    /**
     * Vegetals presents al terreny.
     */
    private ArrayList<Vegetal> vegetals;
    /**
     * Virus presents al terreny.
     */
    private ArrayList<Virus> virus;
    /**
     * Paràsits presents al terreny.
     */
    private ArrayList<Parasit> parasits;
    /**
     * Posició del terreny.
     */
    private Posicio posicio;

    /**
     * Constructor
     * @param t TipusTerreny
     */
    Terreny(TipusTerreny t) {
        animals     = new ArrayList<>();
        vegetals    = new ArrayList<>();
        virus       = new ArrayList<>();
        parasits    = new ArrayList<>();

        this.tipusTerreny = t;
        this.capacitat    = 0;
    }

    /**
     * Constructor
     * @param t Terreny
     */
    Terreny(Terreny t) {
        this.capacitat      = t.capacitat;
        this.tipusTerreny   = t.tipusTerreny;

        this.animals        = new ArrayList<>(t.animals);
        this.vegetals       = new ArrayList<>(t.vegetals);
        this.virus          = new ArrayList<>(t.virus);
        this.parasits       = new ArrayList<>(t.parasits);
    }

    /**
     * Afegir un animal al terreny
     * @param a Animal
     */
    public void afegirAnimal(Animal a) {
        animals.add(a);
        this.capacitat += a.espaiCasella();
    }

    /**
     * Eliminar un animal del terreny
     * @param a Animal
     */
    public void eliminarAnimal(Animal a) {
        animals.remove(a);
        this.capacitat -= a.espaiCasella();
    }

    /**
     * Afegir un vegetal al terreny
     * @param v Vegetal
     */
    public void afegirVegetal(Vegetal v) {
        vegetals.add(v);
        this.capacitat += v.espaiCasella();
    }

    /**
     * Eliminar un vegetal del terreny
     * @param v Vegetal
     */
    public void eliminarVegetal(Vegetal v) {
        vegetals.remove(v);
        this.capacitat -= v.espaiCasella();
    }

    /**
     * Afegir un virus al terreny
     * @param v Virus
     */
    public void afegirVirus(Virus v) {
        virus.add(v);
        this.capacitat += v.espaiCasella();
    }

    /**
     * Eliminar un virus del terreny
     * @param v Virus
     */
    public void eliminarVirus(Virus v) {
        virus.remove(v);
        this.capacitat -= v.espaiCasella();
    }

    /**
     * Afegir un parasit al terreny
     * @param p Parasit
     */
    public void afegirParasit(Parasit p) {
        parasits.add(p);
        this.capacitat += p.espaiCasella();
    }

    /**
     * Eliminar un parasit del terreny
     * @param p Parasit
     */
    public void eliminarParasit(Parasit p) {
        parasits.remove(p);
        this.capacitat -= p.espaiCasella();
    }

    /**
     * Eliminar una especie del terreny
     * @param e Especie
     */
    public void eliminarEspecie(Especie e) {

    }

    /**
     * Retorna si un Organisme pot entrar al terre
     * @param o Organisme
     * @return boolean
     */
    public boolean potEntrar(Organisme o) {

        if (o == null) { return false; }
            //TODO: excepció error intern

        if (!o.terrenyValid(this.tipusTerreny)) { return false; }
            //TODO: excepció aquest animal no té permìs per entrar a aquest terreny

        if (!((this.tipusTerreny.capacitat() - this.capacitat - o.espaiCasella()) >= 0)) { return false; }
            //TODO: excepció no hi ha prou espai en aquest terreny

        return true;
    }

    /**
     * Retorna si al terreny ja s'hi troba un tipus d'Especie
     * @param e Especie
     * @return boolean
     */
    public boolean hiHaVegetal(Especie e) {
        for(Vegetal v : vegetals) 
            if(v.especie.equals(e)) return true;

        return false;
    }

    /**
     * Retorna si al terreny ja s'hi troba un tipus d'Especie del llistat
     * @param e Especie
     * @param llistat String
     * @return boolean
     */
    public boolean hiHaOrganisme(Especie e, String llistat) {
        try {
            Method mPotato = getClass().getMethod(llistat);
            for(Organisme o : (ArrayList<Organisme>)mPotato.invoke(this)) 
                if(o.especie.equals(e)) return true; 

        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
            e1.printStackTrace();
        }

        return false;
    }

    /**
     * Retorna la llista d'animals
     * @return Retorna la llista d'animals
     */
    public ArrayList<Animal> animals() {
        return this.animals;
    }

    /**
     * Retorna la llista de vegetals
     * @return Retorna la llista de vegetals
     */
    public ArrayList<Vegetal> vegetals() {
        return this.vegetals;
    }

    /**
     * Retorna la llista de virus
     * @return Retorna la llista de virus
     */
    public ArrayList<Virus> virus() {
        return this.virus;
    }

    /**
     * Retorna la llista de Parasits
     * @return Retorna la llista de Parasits
     */
    public ArrayList<Parasit> parasits() {
        return this.parasits;
    }

    /**
     * Actualitza la posició del terreny
     * @param p Posicio
     */
    public void posicio(Posicio p) {
        this.posicio = p;
    }

    /**
     * Retorna la posició del terreny
     * @return Posicio
     */
    public Posicio posicio() {
        return this.posicio;
    }

}

