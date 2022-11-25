import java.util.Random;

import javax.swing.ImageIcon;

/**
 * Organisme tots els éssers vius són organismes, això ens permet tenir variables globals
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public abstract class Organisme {
    /**
     * Edat de l'animal.
     */
    protected double edat;
    /**
     * Vida total de l'animal.
     */
    protected double vida;
    /**
     * Progrés de la reproducció. Si és negativa acaba de parir i ha d'esperar.
     */
    protected double reproduccio;
    /**
     * Posició de l'organisme
     */
    protected Posicio posicio;
    /**
     * Posició de anterior l'organisme
     */
    protected Posicio posicio_anterior;
    /**
     * Especie de l'organisme
     */
    protected Especie especie;

    /**
     * Constructor
     */
    Organisme() {
        this.reproduccio = 0.0;
        this.edat = 0.0;
        this.vida = 100.0;
    }

    /**
     * Ens retorna si l'animal ha mort
     * @return boolean
     */
    public boolean estaMort() {
        return vida <= 0;
    } 

    /**
     * Ens retorna si l'animal està preparat per tenir cries
     * @return boolean
     */
    public boolean teCries() {
        return reproduccio >= 100;
    } 

    /**
     * Ens retorna si es pot continuar reproduint
     * @return boolean
     */
    public boolean esPotReproduir() {
        return especie.esPotReproduir(this.edat);
    }

    /**
     * Actualitza la posició de l'organisme
     * @param p Posicio
     */
    public void posicio(Posicio p) {
        this.posicio_anterior = this.posicio;
        this.posicio = p;
    }

    /**
     * Retorna la posició on es troba l'organisme
     * @return Posicio
     */
    public Posicio posicio() {
        return this.posicio;
    }

    /**
     * Retorna la posició anterior on es trobava l'organisme
     * @return Posicio
     */
    public Posicio posicioAnterior() {
        return this.posicio_anterior;
    }

    /**
     * Retorna l'espai que ocupa l'organisme
     * @return int
     */
    public int espaiCasella() {
        return this.especie.espaiCasella();
    }

    /**
     * Torna si l'organisme pot entrar al terreny
     * @param tipusTerreny TipusTerreny
     * @return boolean
     */
    public boolean terrenyValid(TipusTerreny tipusTerreny) {
        for (TipusTerreny tt : this.especie.tipusTerreny()) {
            if (tt.codi() == tipusTerreny.codi())
                return true;
        }

        return false;
    }

    /**
     * Modifica la vida de l'organisme segons el cost de vida
     */
    public void modificarVida() {
        this.vida -= randomitzar(especie.costVida());
        if(this.vida < 0) this.vida = 0.0;
    }

    /**
     * Modifica la vida de l'organisme segons el passat per paràmetre
     * @param num Double
     */
    public void modificarVida(Double num) {
        this.vida += num;
        if(this.vida > 100) this.vida = 100.0;
        if(this.vida < 0) this.vida = 0.0;
    }

    /**
     * Mata a l'organisme en una unitat de temps
     */
    public void matar() {
        this.modificarVida(-100.0);
    }

    /**
     * Augmenta l'edat de l'organisme
     */
    public void modificarEdat() {
        this.edat += 0.1;
    }

    /**
     * Avança la reproducció de l'organisme si està disponible
     */
    public void modificarReproduccio() {
        if(this.esPotReproduir()) {
            this.reproduccio += randomitzar(especie.velocitatReproduccio());
        }
    }

    /**
     * Retorna el número de cries que pot tenir l'organisme en aquest cas (aleatòri)
     * @return int
     */
    public int numeroCries() {
        Random r = new Random();
        int low = especie.minimCries();
        int high = especie.maximCries() + 1;
        return r.nextInt(high-low) + low;
    }

    /**
     * Reinicia la reproducció de l'organisme
     */
    public void reiniciarReproduccio() {
        this.reproduccio = -100.0;
    }

    /**
     * Retorna el valor amb un petit increment o decrement
     * 
     * @param num double
     * @return double
     */
    private double randomitzar(double num) {
        Random r = new Random();
        int low = (int)((num - (num/5))*1000);
        int high = (int)((num + (num/5))*1000);
        return Math.abs(r.nextInt(high-low) + low)/1000;
    }
}
