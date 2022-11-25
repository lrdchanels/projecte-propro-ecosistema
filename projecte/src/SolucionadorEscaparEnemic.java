/**
 * SolucionadorEscaparEnemic solucionador del Backtracking (En realitat simula un Backtacking)
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class SolucionadorEscaparEnemic {
    private SolucioEscaparEnemic optima, actual;

    /**
     * Soluciona la solució passada per paràmetre
     * @param inicial SolucioEscaparEnemic
     * @return Posicio
     */
    Posicio solucionar(SolucioEscaparEnemic inicial) {
        actual = new SolucioEscaparEnemic(inicial);
        optima = new SolucioEscaparEnemic(inicial);

        this.trobarMillor();

        return optima.rec.get(optima.rec.size() - 1);
    }

    /**
     * Troba la millot solució
     */
    void trobarMillor() {
        CandidatsEscaparEnemic iCan = actual.inicialitzarCandidats();

        while (!iCan.fi()) {
            if (actual.acceptable(iCan)) {
                actual.anotar(iCan);
                if (actual.esMillor(optima)){
                    optima = new SolucioEscaparEnemic(actual); // operador d’assignacio
                }
                if (!actual.completa()) {
                    trobarMillor();
                }
                actual.desanotar();
            }
            iCan.seguent();
        }

    }
}
