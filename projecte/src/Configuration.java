import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import javax.imageio.ImageIO;
import java.awt.Image;

/**
 * Configuration s'encarrega de carregar totes les dades que tenim al config.json en el nostre ecosistema
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class Configuration {
    /**
     * Terrenys disponibles.
     */
    private ArrayList<TipusTerreny> tipusTerrenys;
    /**
     * Especies disponibles.
     */
    private ArrayList<Especie> especies;
    /**
     * Configuració.
     */
    public JSONObject conf;
    /**
     * Velocitat en ms.
     */
    public int VELOCITAT;
    /**
     * Mida del taulell.
     */
    public int MIDA_TERRENY;
    /**
     * n columnes.
     */
    public int x;
    /**
     * n files.
     */
    public int y;

    /**
     * Constructor
     * @param nom_fitxer String
     */
    Configuration(String nom_fitxer) {
        tipusTerrenys = new ArrayList<>();
        especies = new ArrayList<>();

        try {
            String path = System.getProperty("user.dir");
            conf = (JSONObject)new JSONParser().parse(new FileReader(path + "./src/" + nom_fitxer));
            JSONObject llistat_mapa = (JSONObject) conf.get("mapa");
            x = (int)(long)llistat_mapa.get("x");
            y = (int)(long)llistat_mapa.get("y");
            MIDA_TERRENY = (int)(long)llistat_mapa.get("mida_bloc");
            VELOCITAT = (int)(long)llistat_mapa.get("velocitat");
        } catch (IOException | ParseException e) {
            String path = System.getProperty("user.dir");
            System.out.println("No s'ha pogut carregar el fitxer de configuració: config.json al directori " + path);
        }
    }

    /**
     * Torna tots els tipus de terreny de la configuració
     * @return Torna tots els tipus de terreny de la configuració
     */
    public ArrayList<TipusTerreny> agafarTipusTerrenys() {
        tipusTerrenys = new ArrayList<>();
        JSONArray llistat_terrenys = (JSONArray) conf.get("tipusTerrenys");
        Iterator it = llistat_terrenys.iterator();
        while (it.hasNext()) {
            // Afegim l'especie
            JSONObject aux = (JSONObject) it.next();
            tipusTerrenys.add(new TipusTerreny(aux));
        }
        return tipusTerrenys;
    }

    /**
     * Carrega el mapa amb els terrenys
     * @return un array 2D de Terrenys
     */
    public Terreny[][] agafarMapa() {
        Terreny[][] terrenys = new Terreny[x][y]; 

        JSONObject llistat_mapa = (JSONObject) conf.get("mapa");

        JSONArray rows = (JSONArray)llistat_mapa.get("terrenys");
        for (int i = 0; i < rows.size(); i++) {
            JSONArray jsonArr = (JSONArray) rows.get(i);

            for (int x = 0; x < jsonArr.size(); x++) {
                int tipusTerreny = Integer.parseInt(jsonArr.get(x).toString());
                TipusTerreny tipusTerreny_aux = buscarTipusTerreny(tipusTerreny);

                try {
                    Terreny terreny = (Terreny)Class.forName(tipusTerreny_aux.nom()).getDeclaredConstructor(TipusTerreny.class).newInstance(tipusTerreny_aux);
                    terreny.posicio(new Posicio(x, i));
                    terrenys[x][i] = terreny;
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException | NoSuchMethodException | SecurityException
                        | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return terrenys;
    }

    /**
     * Torna totes les especies
     * @return Torna totes les especies
     */
    public ArrayList<Especie> agafarEspecies() {
        especies = new ArrayList<>();

        JSONArray llistat_especies = (JSONArray) conf.get("especies");
        Iterator it = llistat_especies.iterator();
        while (it.hasNext()) {
            // Afegim l'especie
            JSONObject aux = (JSONObject)it.next();
            Especie esp = new Especie(aux);
            especies.add(esp);
        }

        for (int i = 0; i < especies.size(); i++) {
            JSONObject data = (JSONObject)llistat_especies.get(i);

            // Afegim els aliments
            JSONArray llistat = (JSONArray)data.get("aliments");
            Iterator it1 = llistat.iterator();
            while (it1.hasNext()) {
                Especie aux = buscarEspecie((String)it1.next());
                especies.get(i).afegirAliment(aux);
            }

            // Afegim els enemics
            llistat = (JSONArray)data.get("enemics");
            it1 = llistat.iterator();
            while (it1.hasNext()) {
                Especie aux = buscarEspecie((String)it1.next());
                especies.get(i).afegirEnemic(aux);
            }

            // Afegim els terrenys
            llistat = (JSONArray)data.get("terrenys");
            it1 = llistat.iterator();
            while (it1.hasNext()) {
                TipusTerreny tipusTerreny = buscarTipusTerreny((int)(long)it1.next());
                especies.get(i).afegirTipusTerreny(tipusTerreny);
            }
        }

        return especies;
    }

    /**
     * Torna tots els animals
     * @return Torna tots els animals
     */
    public ArrayList<Animal> agafarAnimals() {
        ArrayList<Animal> animals = new ArrayList<>();

        JSONArray llistat_animals = (JSONArray) conf.get("animals");
        Iterator it = llistat_animals.iterator();
        while (it.hasNext()) {
            JSONObject aux = (JSONObject)it.next();
            String especie = (String)aux.get("especie");
            Especie especie_aux = buscarEspecie(especie);
            JSONArray pos = (JSONArray)aux.get("posicio");
            Posicio posicio = new Posicio((int)(long)pos.get(0), (int)(long)pos.get(1));
            
            try {
                Animal animal = (Animal)Class.forName(especie).getDeclaredConstructor(Especie.class).newInstance(especie_aux);
                animal.posicio(posicio);
                animals.add(animal);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException
                    | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return animals;
    }

    /**
     * Torna tots els vegetals
     * @return Torna tots els vegetals
     */
    public ArrayList<Vegetal> agafarVegetals() {
        ArrayList<Vegetal> vegetals = new ArrayList<>();

        JSONArray llistat_vegetals = (JSONArray) conf.get("vegetals");
        Iterator it = llistat_vegetals.iterator();
        while (it.hasNext()) {
            JSONObject aux = (JSONObject)it.next();
            String especie = (String)aux.get("especie");
            Especie especie_aux = buscarEspecie(especie);
            JSONArray pos = (JSONArray)aux.get("posicio");
            Posicio posicio = new Posicio((int)(long)pos.get(0), (int)(long)pos.get(1));
            
            try {
                Vegetal vegetal = (Vegetal)Class.forName(especie).getDeclaredConstructor(Especie.class).newInstance(especie_aux);
                vegetal.posicio(posicio);
                vegetals.add(vegetal);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException
                    | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return vegetals;
    }

    /**
     * Torna tots els virus
     * @return Torna tots els virus
     */
    public ArrayList<Virus> agafarVirus() {
        ArrayList<Virus> viruss = new ArrayList<>();

        JSONArray llistat_virus = (JSONArray) conf.get("virus");
        Iterator it = llistat_virus.iterator();
        while (it.hasNext()) {
            JSONObject aux = (JSONObject)it.next();
            String especie = (String)aux.get("especie");
            Especie especie_aux = buscarEspecie(especie);
            JSONArray pos = (JSONArray)aux.get("posicio");
            Posicio posicio = new Posicio((int)(long)pos.get(0), (int)(long)pos.get(1));

            try {
                Virus virus = (Virus)Class.forName(especie).getDeclaredConstructor(Especie.class).newInstance(especie_aux);
                virus.posicio(posicio);
                viruss.add(virus);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException
                    | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return viruss;
    }

    /**
     * Torna tots els paràsits
     * @return Torna tots els paràsits
     */
    public ArrayList<Parasit> agafarParasits() {
        ArrayList<Parasit> parasits = new ArrayList<>();

        JSONArray llistat_parasits = (JSONArray) conf.get("parasits");
        Iterator it = llistat_parasits.iterator();
        while (it.hasNext()) {
            JSONObject aux = (JSONObject)it.next();
            String especie = (String)aux.get("especie");
            Especie especie_aux = buscarEspecie(especie);
            JSONArray pos = (JSONArray)aux.get("posicio");
            Posicio posicio = new Posicio((int)(long)pos.get(0), (int)(long)pos.get(1));
               
            
            try {
                Parasit parasit = (Parasit) Class.forName(especie).getDeclaredConstructor(Especie.class).newInstance(especie_aux);
                parasit.posicio(posicio);
                parasits.add(parasit);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException
                    | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return parasits;
    }

    /**
     * Carrega les imatges de les especies
     * @return Carrega les imatges de les especies
     */
    public ArrayList<Pair<Especie, ImageIcon>> agafarImatgesEspecies() {
        ArrayList<Pair<Especie, ImageIcon>> llistat = new ArrayList<Pair<Especie, ImageIcon>>();

        JSONArray llistat_especies = (JSONArray) conf.get("especies");
        Iterator it = llistat_especies.iterator();
        while (it.hasNext()) {
            JSONObject aux = (JSONObject)it.next();
            Especie esp = buscarEspecie((String)aux.get("nom"));
            ImageIcon imatge = carregarImatge((String)aux.get("imatge"));
            llistat.add(new Pair(esp, imatge));
        }

        return llistat;
    }

    /**
     * Carrega les imatges dels tipus de terreny
     * @return Carrega les imatges dels tipus de terreny
     */
    public ArrayList<Pair<TipusTerreny, ImageIcon>> agafarImatgesTerrenys() {
        ArrayList<Pair<TipusTerreny, ImageIcon>> llistat = new ArrayList<Pair<TipusTerreny, ImageIcon>>();

        JSONArray llistat_especies = (JSONArray) conf.get("tipusTerrenys");
        Iterator it = llistat_especies.iterator();
        while (it.hasNext()) {
            JSONObject aux = (JSONObject)it.next();
            TipusTerreny tt = buscarTipusTerreny((String)aux.get("nom"));
            ImageIcon imatge = carregarImatge((String)aux.get("imatge"));
            llistat.add(new Pair(tt, imatge));
        }

        return llistat;
    }

    /**
     * Retorna un TipusTerreny a partir del codi
     * @param codi int
     * @return TipusTerreny
     */
    private TipusTerreny buscarTipusTerreny(int codi) {
        for(TipusTerreny t : tipusTerrenys) {
            if(t.codi() == codi) return t;
        }
        // TODO - llançar excepció de que no s'ha trobat
        return null;
    }

    /**
     * Retorna una Especie a partir del nom
     * @param nom String
     * @return Especie
     */
    private Especie buscarEspecie(String nom) {
        for(Especie e : especies) {
            if(e.nom().equals(nom)) return e;
        }
        // TODO - llançar excepció de que no s'ha trobat
        return null;
    }

    /**
     * Retorna un TipusTerreny a partir del nom
     * @param nom String
     * @return TipusTerreny
     */
    private TipusTerreny buscarTipusTerreny(String nom) {
        for(TipusTerreny t : tipusTerrenys) {
            if(t.nom().equals(nom)) return t;
        }
        // TODO - llançar excepció de que no s'ha trobat
        return null;
    }

    /**
     * Carrega una imatge a l'ecosistema
     * @param url String
     * @return ImageIcon
     */
    private ImageIcon carregarImatge(String url) {
        try {
            if(url != null && url != "") {
                String path = System.getProperty("user.dir");
                ImageIcon imatge = new ImageIcon(ImageIO.read(new File(path + "\\src\\img\\" + url)));
                // Li fem un resize
                Image image = imatge.getImage(); 
                Image newimg = image.getScaledInstance(MIDA_TERRENY, MIDA_TERRENY,  Image.SCALE_SMOOTH); 
                return new ImageIcon(newimg);  
            }
        }  catch (IOException e) {
            String path = System.getProperty("user.dir");
            System.out.println("No s'ha trobat l'imatge al directori " + path + "\\src\\img\\" + url);
        }
        return null;
    }

}
