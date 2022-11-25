import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Classe on inicia el programa.
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class Main {
    /**
     * Color negre
     */
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";

    /**
     * main
     * @param args String[]
     * @throws InterruptedException Errada a l'obtenir dades del JSON.
     * @throws IOException Errada a l'obtenir dades del JSON.
     */
    public static void main (String [ ] args) throws InterruptedException, IOException {
        
        InterficieListener interficie;
        Boolean interficie_grafica = false;
        Boolean automatica = false;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Introdueix el nom del fitxer de configuració:");
        String fitxer_configuracio = reader.readLine();
        System.out.println("Tria la interfície: gràfica o descriptiva (g/d):");
        String tipus_interficie = reader.readLine();
        System.out.println("Tria la velocitat: automàtica o manual (a/m):");
        String tipus_velocitat = reader.readLine();

        Configuration conf = new Configuration(fitxer_configuracio);
        Ecosistema ecosistema = new Ecosistema(conf.x, conf.y);

        interficie_grafica = tipus_interficie.equals("g");
        automatica = tipus_velocitat.equals("a");
        
        if(interficie_grafica) interficie = new Mapa(conf);
        else interficie = new Descriptiva(conf);

        ecosistema.setCustomObjectListener(interficie);

        // Afegim els tipus terreny trets de la configuració
        ArrayList<TipusTerreny> tipusTerrenys = conf.agafarTipusTerrenys();
        for(TipusTerreny tt : tipusTerrenys) ecosistema.afegirTipusTerreny(tt);
        if(interficie_grafica) ((Mapa)interficie).afegirImatgesTerrenys(conf.agafarImatgesTerrenys());

        // Carreguem les especies
        ArrayList<Especie> especies = conf.agafarEspecies();
        for(Especie e : especies) ecosistema.afegirEspecie(e);
        if(interficie_grafica) ((Mapa)interficie).afegirImatgesEspecies(conf.agafarImatgesEspecies());

        // Carreguem tots els terrenys
        Terreny[][] terrenys = conf.agafarMapa();
        for(Terreny[] terreny_row : terrenys) {
            for(Terreny terreny_col : terreny_row) {
                ecosistema.afegirTerreny(terreny_col);
            }
        }

        // Carreguem els vegetals
        ArrayList<Vegetal> vegetals = conf.agafarVegetals();
        for(Vegetal v : vegetals) ecosistema.afegirVegetal(v);

        // Carreguem els virus
        ArrayList<Virus> virus = conf.agafarVirus();
        for(Virus v : virus) ecosistema.afegirVirus(v);

        // Carreguem els animals
        ArrayList<Animal> animals = conf.agafarAnimals();
        for(Animal a : animals) ecosistema.afegirAnimal(a);

        interficie.iniciar(ecosistema, automatica);
    }




   

    
}
