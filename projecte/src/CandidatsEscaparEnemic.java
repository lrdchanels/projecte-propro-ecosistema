/**
 * CandidatsEscaparEnemic s'encarrega de definir i operar els candidats
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class CandidatsEscaparEnemic {
    private int iCan;
    private int nObjs;

    /**
     * Constructor
     * @param max int
     */
    CandidatsEscaparEnemic(int max) {
        iCan = 0;
        nObjs = max;
    }

    /**
     * Retorna el candidat actual
     * @return int
     */
    int actual() {
        if (this.fi()) {}
            // TODO: throw("No hi ha més candidats");
        return iCan;
    }

    /**
     * Retorna si hem arribat al final dels candidats
     * @return boolean
     */
    boolean fi() {
        return this.iCan == this.nObjs;
    }

    /**
     * Incrementa l'index dels candidats
     */
    void seguent() {
        if (this.fi()) {}
            // TODO: throw("No hi ha més candidats");
        this.iCan++;
    }
}