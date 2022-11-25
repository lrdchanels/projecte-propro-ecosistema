import java.util.ArrayList;
import java.lang.Math; // importing java.lang package

/**
 * SolucioEscaparEnemic solució del Backtracking (En realitat simula un Backtacking)
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class SolucioEscaparEnemic {
    ArrayList<Posicio> rec, desp;
    Posicio posicioEnemic;
    Terreny terrenys[][];
    boolean usats[][];
    Animal animal;
    int nObjs, niv;
    double distancia;

    /**
     * Constructor
     * @param posicionsEnemics ArrayList<Posicio>
     * @param animal Animal
     * @param terreny Terreny[][]
     */
    SolucioEscaparEnemic(ArrayList<Posicio> posicionsEnemics, Animal animal, Terreny[][] terreny) {
        this.reserva();
        this.posicioEnemic = this.mitjanaPosicioEnemic(posicionsEnemics);
        this.rec = new ArrayList<>();
        desp = new ArrayList<>(Ecosistema.posicioDisponibles()); // Inicialitzem les posicions disponibles

        this.niv = 0;
        this.nObjs = animal.especie.velocitat();
        this.distancia = 0;

        this.terrenys = terreny;
        this.animal = animal;

        this.rec.add(animal.posicio); // Afegim la posicio de l'animal a la primera posició del recorregut
        this.usats[animal.posicio.x()][animal.posicio.x()] = true;
    }

    /**
     * Constructor
     * @param s SolucioEscaparEnemic
     */
    SolucioEscaparEnemic(SolucioEscaparEnemic s) {
        this.rec = new ArrayList<>(s.rec);
        this.desp = s.desp;
        this.posicioEnemic = s.posicioEnemic;
        this.terrenys = s.terrenys;
        this.animal = s.animal;
        this.nObjs = s.nObjs;
        this.niv = s.niv;
        this.distancia = s.distancia;
        this.usats = s.usats;
    }

    /**
     * Inicialitza els candidats dinàmicament
     * @return CandidatsEscaparEnemic
     */
    CandidatsEscaparEnemic inicialitzarCandidats() {
        return new CandidatsEscaparEnemic(desp.size());
    }

    /**
     * Cert si el candidat es acceptable
     * @param iCan CandidatsEscaparEnemic
     * @return boolean
     */
    boolean acceptable(CandidatsEscaparEnemic iCan) {
        boolean result = false;
        Posicio candidata = new Posicio((rec.get(niv).x() + desp.get(iCan.actual()).x()), (rec.get(niv).y() + desp.get(iCan.actual()).y()));

        if (Ecosistema.posicioValida(candidata) && terrenys[candidata.x()][candidata.y()].potEntrar(animal) && !usats[candidata.x()][candidata.y()]) {
            result = true;
        }

        return result;
    }

    /**
     * Anota el candidat a la solució
     * @param iCan CandidatsEscaparEnemic
     */
    void anotar(CandidatsEscaparEnemic iCan) {
        // inserim la posicio
        rec.add(new Posicio(rec.get(niv).x() + desp.get(iCan.actual()).x(), rec.get(niv).y() + desp.get(iCan.actual()).y()));
        /// incrementem el nivell
        niv++;
        // posem la cel·la a true
        usats[rec.get(niv).x()][rec.get(niv).y()] = true;
        //calculem distancia
        distancia = Math.sqrt(Math.pow((double)rec.get(niv).x() - (double)posicioEnemic.x(), 2) + Math.pow((double)rec.get(niv).y() - (double)posicioEnemic.y(), 2));
    }

    /**
     * Desanota l'últim candidat de la solució
     */
    void desanotar() {
        // posem la cel·la a true
        usats[rec.get(niv).x()][rec.get(niv).y()] = false;
        // decrementem el nivell
        niv--;
        /// eliminem la posicio
        rec.remove(rec.size() - 1);

        //calculem distancia
        distancia = Math.sqrt(Math.pow((double)rec.get(niv).x() - (double)posicioEnemic.x(), 2) + Math.pow((double)rec.get(niv).y() - (double)posicioEnemic.x(), 2));
    }

    /**
     * Retorna si la solució es completa
     * @return boolean
     */
    boolean completa() {

        return niv == nObjs;
    }

    /**
     * Retorna si la solució actual és millor a la passada per paràmetre
     * @param optima SolucioEscaparEnemic
     * @return boolean
     */
    boolean esMillor(SolucioEscaparEnemic optima) {
        if (distancia > optima.distancia)
            return true;

        if (optima.distancia == 0)
            return true;

        if (distancia == optima.distancia) {
            if (optima.rec.get(optima.rec.size() - 1).x() == 0 && optima.rec.get(optima.rec.size() - 1).y() == 0)
                return true;

            if (optima.rec.get(optima.rec.size() - 1).x() == Ecosistema.x && optima.rec.get(optima.rec.size() - 1).y() == 0)
                return true;

            if (optima.rec.get(optima.rec.size() - 1).x() == 0 && optima.rec.get(optima.rec.size() - 1).y() == Ecosistema.y)
                return true;

            if (optima.rec.get(optima.rec.size() - 1).x() == Ecosistema.x && optima.rec.get(optima.rec.size() - 1).y() == Ecosistema.y)
                return true;
        }

        return false;
    }

    /**
     * Retorna la mitjana entre varies posicions
     * @param posicionsEnemics ArrayList<Posicio>
     * @return Posicio
     */
    private Posicio mitjanaPosicioEnemic(ArrayList<Posicio> posicionsEnemics) {
        int suma_x = 0, suma_y = 0, mitjana_x, mitjana_y;

        // Sumem les posicions
        for (Posicio p : posicionsEnemics) {
            suma_x += p.x();
            suma_y += p.y();
        }

        // Calculem la mitjana
        mitjana_x = suma_x / posicionsEnemics.size();
        mitjana_y = suma_y / posicionsEnemics.size();

        // Posició amb la mitjana
        return new Posicio(mitjana_x, mitjana_y);
    }

    /**
     * Inicialitza variables
     */
    void reserva() {
        this.usats = new boolean[Ecosistema.x][Ecosistema.y];

        for (int i = 0; i < Ecosistema.x; i++) {
            for (int j = 0; j < Ecosistema.y; j++) {
                this.usats[i][j] = false;
            }
        }
    }
}
