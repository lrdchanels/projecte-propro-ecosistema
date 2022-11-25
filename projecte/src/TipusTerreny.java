import org.json.simple.JSONObject;

/**
 * TipusTerreny ens serveix per tindre identificats quins tipus de terrenys tenim a l'ecosistema
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class TipusTerreny {
    private String nom;
    private int codi, capacitat;

    /**
     * Constructor inicialitza amb totes les dades agafades del fitxer de configuració
     * @param data JSONObject
     */
    TipusTerreny(JSONObject data) {
        // Carreguem totes les dades
        nom = (String)data.get("nom");
        codi = (int)(long)data.get("codi");
        capacitat = (int)(long)data.get("capacitat");
    }

    /**
     * Torna el nom del terreny
     * @return String
     */
    String nom() {
        return nom;
    }

    /**
     * Torna el codi del terreny
     * @return int
     */
    int codi() {
        return codi;
    }

    /**
     * Torna la capacitat del terreny
     * @return int
     */
    int capacitat() {
        return capacitat;
    }
}
