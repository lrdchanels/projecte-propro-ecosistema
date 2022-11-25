/**
 * Posicio ens serveix per manipular posicions sobre el taulell
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */


public class Posicio {
    private int _x, _y;

    /**
     * Constructor
     * @param p Posicio
     */
    Posicio(Posicio p) {
        this(p.x(),p.y());
    }

    /**
     * Constructor
     * @param x int
     * @param y int
     */
    Posicio(int x, int y) {
        this._x = x;
        this._y = y;
    }

    /**
     * Retorna x
     * @return int
     */
    public int x() {
        return _x;
    }

    /**
     * Retorna y
     * @return int
     */
    public int y() {
        return _y;
    }

    /**
     * Retorna la suma entre la posició passada per paràmetre i l'actual
     * @param p1 Posicio
     * @param p2 Posicio
     * @return Posicio
     */
    public static Posicio sumar(Posicio p1, Posicio p2) {
        return new Posicio(p1.x() + p2.x(), p1.y() + p2.y());
    }

    /**
     * Converteix l'objecte actual a una sortida String
     * @return String
     */
    @Override
    public String toString() {
        return "Posicio{" +
                "_x=" + _x +
                ", _y=" + _y +
                '}';
    }
}
