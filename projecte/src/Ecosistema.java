import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Ecosistema és la classe principal del nostre ecosistema. Aquí hi ha tota la lògica dels moviments dels animals i la lògica dels terrenys
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class Ecosistema {
    /**
     * Amplada del mapa
     */
    public static int x;
    /**
     * Altura del mapa
     */
    public static int y;
    /**
     * Tipus de Terreny de l'ecosistema
     */
    private ArrayList<TipusTerreny> tipusTerrenys;
    /**
     * Especies de l'ecosistema
     */
    private ArrayList<Especie> especies;
    /**
     * Totes les caselles del mapa
     */
    private Terreny[][] terrenys;
    /**
     * Listener dels esdeveniments de l'ecostistema
     */
    private InterficieListener listener;

    /**
     * Constructor
     * @param x int
     * @param y int
     */
    Ecosistema(int x, int y) {
        this.listener = null; 
        this.x = x;
        this.y = y;
        this.tipusTerrenys = new ArrayList<>();
        this.especies = new ArrayList<>();
        this.terrenys = new Terreny[x][y];
    }

    /**
     * Definim el listener
     * @param listener InterficieListener
     */
    public void setCustomObjectListener(InterficieListener listener) {
        this.listener = listener;
    }

    /**
     * Comença a córrer l'ecosistema
     */
    void moviment() throws InterruptedException {
        Terreny[][] copiaterrenys = copiarTerrenys(terrenys);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                ArrayList<Animal> animals = new ArrayList<>(copiaterrenys[i][j].animals());
                ArrayList<Vegetal> vegetals = new ArrayList<>(copiaterrenys[i][j].vegetals());
                ArrayList<Virus> virus = new ArrayList<>(copiaterrenys[i][j].virus());


                this.actualitzarVegetals(vegetals);
                this.actualitzarVirus(virus, animals);
                this.actualitzarAnimals(animals, vegetals);

                this.moureAnimals(animals);
            }
        }
    }

    /**
     * Còpia el llistat de terrenys
     * @param terr Terreny[][]
     * @return Retorna un array 2D de Terrenys
     */
    private Terreny[][] copiarTerrenys(Terreny[][] terr) {
        Terreny[][] temp = new Terreny[x][y];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                temp[i][j] = new Terreny(terr[i][j]);
            }
        }

        return temp;
    }

    /**
     * Afegir un tipus de terreny
     * @param t TipusTerreny
     */
    void afegirTipusTerreny(TipusTerreny t) {
        tipusTerrenys.add(t);
    }

    /**
     * Afegir un terreny
     * @param t Terreny
     */
    void afegirTerreny(Terreny t) {
        terrenys[t.posicio().x()][t.posicio().y()] = t;
        listener.afegir(t);
    }

    /**
     * Afegir una especie
     * @param e Especie
     */
    void afegirEspecie(Especie e) {
        especies.add(e);
    }

    /**
     * Afegir un animal
     * @params Animal a
     */
    void afegirAnimal(Animal a) {
        if (terrenys[a.posicio().x()][a.posicio().y()].potEntrar(a)) {
            terrenys[a.posicio().x()][a.posicio().y()].afegirAnimal(a);
            listener.afegir(a);
        }
    }

    /**
     * Afegir un vegetal
     * @params Vegetal v
     */
    void afegirVegetal(Vegetal v) {
        terrenys[v.posicio().x()][v.posicio().y()].afegirVegetal(v);
        listener.afegir(v);
    }

    /**
     * Afegir un virus
     * @params Virus v
     */
    void afegirVirus(Virus v) {
        terrenys[v.posicio().x()][v.posicio().y()].afegirVirus(v);
        listener.afegir(v);
    }


    /**
     * Retorna si és una posició vàlida
     * @param p Posicio
     * @return boolean
     */
    public static boolean posicioValida(Posicio p) {
        return ((p.x() >= 0 && p.x() < x) && (p.y() >= 0 && p.y() < y));
    }

    /**
     * Comprova si hi ha enemics en el seu espai de visió, retorna un ArrayList amb les posicions on hi ha enemics.
     * @param a Animal
     * @return Retorna una llista amb totes les posicions on hi ha enemics.
     */
    private ArrayList<Posicio> buscarEnemics(Animal a) {
        int visibilitatAnimal = a.especie.visibilitat();
        Posicio posicioAnimal = a.posicio();
        ArrayList<Especie> enemicsAnimal = a.especie.enemics();
        ArrayList<Posicio> posicionsEnemics = new ArrayList<>();

        int visibilitatAnimalModificadaEsquerra = ((posicioAnimal.x() - visibilitatAnimal) < 0) ? 0 : (posicioAnimal.x() - visibilitatAnimal);
        int visibilitatAnimalModificadaDreta = ((posicioAnimal.x() + visibilitatAnimal) >= this.x) ? this.x - 1 : (posicioAnimal.x() + visibilitatAnimal);
        int visibilitatAnimalModificadaAmunt = ((posicioAnimal.y() - visibilitatAnimal) < 0) ? 0 : (posicioAnimal.y() - visibilitatAnimal);
        int visibilitatAnimalModificadaAbaix = ((posicioAnimal.y() + visibilitatAnimal) >= this.y) ? this.y - 1 : (posicioAnimal.y() + visibilitatAnimal);

        for (int i = visibilitatAnimalModificadaEsquerra; i <= visibilitatAnimalModificadaDreta; i++) {
            for (int j = visibilitatAnimalModificadaAmunt; j <= visibilitatAnimalModificadaAbaix; j++) {
                if(this.comparteixenEspecie(terrenys[i][j].animals(), enemicsAnimal)) {
                    posicionsEnemics.add(new Posicio(i, j));
                }
            }
        }

        return posicionsEnemics;
    }

    /**
     * Retorna una Posició "aleatoriment"
     * @param a Animal
     * @return Posicio
     */
    private Posicio buscarPosicioAleatoria(Animal a) {
        Posicio posicioAnimal = a.posicio();
        ArrayList<Posicio> posicionsDisponibles = this.posicioDisponibles();
        Posicio posicioAliment = null;

        for (int i = a.posicioAnterior; i < a.posicioAnterior + posicionsDisponibles.size(); i++) {
            int index = i % posicionsDisponibles.size();

            Posicio aux = posicionsDisponibles.get(index);
            Posicio posicioCandidata = Posicio.sumar(posicioAnimal, aux);

            // La posició ha de ser vàlida i l'animal ha de tenir espai a la nova parcel·la
            if ((this.posicioValida(posicioCandidata)) && (terrenys[posicioCandidata.x()][posicioCandidata.y()].potEntrar(a))) {
                posicioAliment = posicioCandidata;
                // Actualitzem la variable posicioAnterior a l'animal
                a.posicioAnterior = index;
                break;
            }
        }

        return posicioAliment;
    }

    /**
     * Mou un animal a una posició determinada
     * @param a Animal
     * @param p Posicio
     */
    private void moureAnimal(Animal a, Posicio p) {
        if(!posicioValida(p)) {} // TODO: excepció la posició no es valida

        // Eliminem l'animal de la seva posició actual
        terrenys[a.posicio().x()][a.posicio().y()].eliminarAnimal(a);
        // Canviem la posició de l'animal
        a.posicio(p);
        // Posem l'animal a la nova casella
        terrenys[p.x()][p.y()].afegirAnimal(a);
    }

    /**
     * Retorna un ArrayList amb les posicions disponibles
     * @return Retorna un ArrayList amb les posicions disponibles
     */
    public static ArrayList<Posicio> posicioDisponibles() {
        ArrayList<Posicio> posicionsDisponibles = new ArrayList<>();
        posicionsDisponibles.add(new Posicio(-1, 0));
        posicionsDisponibles.add(new Posicio(0, -1));
        posicionsDisponibles.add(new Posicio(1, 0));
        posicionsDisponibles.add(new Posicio(0, 1));

        return posicionsDisponibles;
    }

    /**
     * Retorna les posicions on hi ha aliments
     * @param a Animal
     * @return Retorna les posicions on hi ha aliments
     */
    private ArrayList<Posicio> buscarAlimentsAnimals(Animal a) {
        int velocitatAnimal = a.especie.velocitat();
        Posicio posicioAnimal = a.posicio();
        ArrayList<Especie> alimentsAnimal = new ArrayList<>(a.especie.aliments());
        ArrayList<Posicio> posicionsAliments = new ArrayList<>();

        for (Iterator<Especie> it = alimentsAnimal.iterator(); it.hasNext();) {
            if ( !it.next().esDepredador() ) {
                it.remove();
            }
        }

        if (!alimentsAnimal.isEmpty()) {
            int visibilitatAnimalModificadaEsquerra = ((posicioAnimal.x() - velocitatAnimal) < 0) ? 0 : (posicioAnimal.x() - velocitatAnimal);
            int visibilitatAnimalModificadaDreta = ((posicioAnimal.x() + velocitatAnimal) >= this.x) ? this.x - 1 : (posicioAnimal.x() + velocitatAnimal);
            int visibilitatAnimalModificadaAmunt = ((posicioAnimal.y() - velocitatAnimal) < 0) ? 0 : (posicioAnimal.y() - velocitatAnimal);
            int visibilitatAnimalModificadaAbaix = ((posicioAnimal.y() + velocitatAnimal) >= this.y) ? this.y - 1 : (posicioAnimal.y() + velocitatAnimal);

            for (int i = visibilitatAnimalModificadaEsquerra; i <= visibilitatAnimalModificadaDreta; i++) {
                for (int j = visibilitatAnimalModificadaAmunt; j <= visibilitatAnimalModificadaAbaix; j++) {
                    if(this.comparteixenEspecie(terrenys[i][j].animals(), alimentsAnimal)) {
                        posicionsAliments.add(new Posicio(i, j));
                    }
                }
            }
        }

        return posicionsAliments;
    }

    /**
     * Retorna la posició on es troba l'aliment mes proper
     * @param posicionsAliments ArrayList<Posicio>
     * @param a Animal
     * @return Posicio
     */
    private Posicio alimentMesProper(ArrayList<Posicio> posicionsAliments, Animal a) {
        Posicio posicioAnimal = a.posicio();
        Posicio posicioAliment = null;
        Double distancia = null, distanciaTemp = null;

        for ( Posicio pa : posicionsAliments) {
            if(posicioValida(pa) && terrenys[pa.x()][pa.y()].potEntrar(a)) {
                distanciaTemp = Math.sqrt(Math.pow((double)posicioAnimal.x() - (double)pa.x(), 2) + Math.pow((double)posicioAnimal.y() - (double)pa.y(), 2));
                if (distancia == null || distanciaTemp < distancia) {
                    posicioAliment = pa;
                }
            }
        }

        return posicioAliment;
    }

    /**
     * Retorna la posició on es troba el ramat mes proper
     * @param PosicioRamat Posicio
     * @param a Animal
     * @return Posicio
     */
    private Posicio ramatMesProper(Posicio PosicioRamat, Animal a) {
        Posicio posicioAnimal = a.posicio();
        Posicio poscicioRamat = null;
        Double distancia = null, distanciaTemp = null;
        ArrayList<Posicio> posicionsDisponibles = this.posicioDisponibles();
        int i = 0;

        for ( Posicio p : posicionsDisponibles) {
            Posicio posicioCandidata = Posicio.sumar(posicioAnimal, p);

            if(posicioValida(posicioCandidata) && terrenys[posicioCandidata.x()][posicioCandidata.y()].potEntrar(a)) {
                distanciaTemp = Math.sqrt(Math.pow((double) PosicioRamat.x() - (double) posicioCandidata.x(), 2) + Math.pow((double) PosicioRamat.y() - (double) posicioCandidata.y(), 2));
                if (distancia == null || distanciaTemp < distancia) {

                    if (a.posicioAnterior == 0 && i == 2) {
                        return null;
                    }
                    if(a.posicioAnterior == 1 && i == 3) {
                        return null;
                    }
                    if(a.posicioAnterior == 2 && i == 0) {
                        return null;
                    }
                    if(a.posicioAnterior == 3 && i == 1) {
                        return null;
                    }
                    poscicioRamat = posicioCandidata;
                    distancia = new Double(distanciaTemp);
                }
            }
            i++;
        }

        return poscicioRamat;
    }

    /**
     * Retorna una Posició on hi ha aliment per l'animal
     * @param a Animal
     * @return Posicio
     */
    private Posicio buscarAliment(Animal a) {
        Posicio posicioAnimal = a.posicio();
        ArrayList<Posicio> posicionsDisponibles = this.posicioDisponibles();
        Posicio posicioAliment = null;

        for(Posicio posicio : posicionsDisponibles) {
            Posicio posicioCandidata = Posicio.sumar(posicioAnimal, posicio);

            // La posició ha de ser vàlida, l'animal ha de tenir espai a la nova parcel·la
            if ((this.posicioValida(posicioCandidata)) && (terrenys[posicioCandidata.x()][posicioCandidata.y()].potEntrar(a))) {
                // Hi ha d'haver menjar a la casella
                if(
                    comparteixenEspecie(terrenys[posicioCandidata.x()][posicioCandidata.y()].animals(), a.especie.aliments()) || 
                    comparteixenEspecie(terrenys[posicioCandidata.x()][posicioCandidata.y()].vegetals(), a.especie.aliments())
                ) {
                    posicioAliment = posicioCandidata;
                    break;
                }
            }
        }

        return posicioAliment;
    }

    /**
     * Retorna una Posició on hi ha un remat de la mateixa espècie que l'animal
     * @param a Animal
     * @return Posicio
     */
    private Posicio buscarRamat(Animal a) {
        int visibilitatAnimal = a.especie.visibilitat();
        Posicio posicioAnimal = a.posicio();
        Posicio posicioRamat = null;

        int visibilitatAnimalModificadaEsquerra = ((posicioAnimal.x() - visibilitatAnimal) < 0) ? 0 : (posicioAnimal.x() - visibilitatAnimal);
        int visibilitatAnimalModificadaDreta = ((posicioAnimal.x() + visibilitatAnimal) >= this.x) ? this.x - 1 : (posicioAnimal.x() + visibilitatAnimal);
        int visibilitatAnimalModificadaAmunt = ((posicioAnimal.y() - visibilitatAnimal) < 0) ? 0 : (posicioAnimal.y() - visibilitatAnimal);
        int visibilitatAnimalModificadaAbaix = ((posicioAnimal.y() + visibilitatAnimal) >= this.y) ? this.y - 1 : (posicioAnimal.y() + visibilitatAnimal);

        for (int i = visibilitatAnimalModificadaEsquerra; i <= visibilitatAnimalModificadaDreta; i++) {
            for (int j = visibilitatAnimalModificadaAmunt; j <= visibilitatAnimalModificadaAbaix; j++) {

                if (i != posicioAnimal.x() || j != posicioAnimal.y()) {
                    Posicio posicioCandidata = new Posicio(i, j);

                    // La posició ha de ser vàlida, l'animal ha de tenir espai a la nova parcel·la
                    if ((this.posicioValida(posicioCandidata)) && (terrenys[posicioCandidata.x()][posicioCandidata.y()].potEntrar(a))) {
                        // Hi ha d'haver un animal de la mateixa especie a la casellaminecraft
                        if(this.comparteixenEspecie(terrenys[posicioCandidata.x()][posicioCandidata.y()].animals(), a.especie)) {

                            return posicioCandidata;
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * Retorna si algun dels organismes del llistat comparteix espècie amb la passada per paràmetre
     * @param organismes ArrayList<Organisme>
     * @param especie Especie
     * @return boolean
     */
    boolean comparteixenEspecie(ArrayList<? extends Organisme> organismes, Especie especie) {
        for (Organisme organisme : organismes) {
            if(organisme.especie.equals(especie)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna si algun dels organismes del llistat comparteix espècie amb les passades per paràmetre
     * @param organismes ArrayList<Organisme>
     * @param especies ArrayList<Especie>
     * @return boolean
     */
    boolean comparteixenEspecie(ArrayList<? extends Organisme> organismes, ArrayList<Especie> especies) {
        for (Organisme organisme : organismes) {
            for (Especie especie : especies) {
                if(organisme.especie.equals(especie)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retorna l'organisme comparteix espècie amb les passades per paràmetre
     * @param organisme Organisme
     * @param especies ArrayList<Especie>
     * @return boolean
     */
    boolean comparteixenEspecie(Organisme organisme, ArrayList<Especie> especies) {
        for (Especie especie : especies) {
            if(organisme.especie.equals(especie)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calcula i canvia la nova posició dels animals del llistat
     * @param animals ArrayList<Animal>
     */
    void moureAnimals(ArrayList<Animal> animals) {
        for (Animal animal : animals) {
            // Fugir dels seus enemics en el rang de visió
            ArrayList<Posicio> posicionsEnemics = this.buscarEnemics(animal);
            if (!posicionsEnemics.isEmpty()) {
                SolucioEscaparEnemic solucio = new SolucioEscaparEnemic(posicionsEnemics, animal, terrenys);
                SolucionadorEscaparEnemic solucionador = new SolucionadorEscaparEnemic();

                Posicio posicioEscaparEnemic = solucionador.solucionar(solucio);
                this.moureAnimal(animal, posicioEscaparEnemic);
                return;
            }

            // Acostar-se al menjar més proper
            Posicio posicioAliment = null;
            ArrayList<Posicio> posicionsAlimentsAnimals = this.buscarAlimentsAnimals(animal);
            if (!posicionsAlimentsAnimals.isEmpty()) {
                posicioAliment = this.alimentMesProper(posicionsAlimentsAnimals, animal);
            }
            else {
                posicioAliment = this.buscarAliment(animal);
            }

            if (posicioAliment != null) {
                this.moureAnimal(animal, posicioAliment);
                return;
            }

            // Acostar-se al xai més proper
            Posicio posicioRamat = buscarRamat(animal);
            if (posicioRamat != null) {
                Posicio posicioCandidataAnarRamat = this.ramatMesProper(posicioRamat, animal);

                if(posicioCandidataAnarRamat != null) {
                    this.moureAnimal(animal, posicioCandidataAnarRamat);
                    return;
                }
            }

            // Moure's direcció aleatòria
            Posicio posicioAleatoria = buscarPosicioAleatoria(animal);
            if (posicioAleatoria != null) {
                this.moureAnimal(animal, posicioAleatoria);
                return;
            }
        }
    }

    /**
     * Actualitza l'estat de tots els animals del llistat
     * @param animals ArrayList<Animal>
     * @param vegetals ArrayList<Vegetal>
     */
    void actualitzarAnimals(ArrayList<Animal> animals, ArrayList<Vegetal> vegetals) {
        for (Animal animal : animals) {
            if(animal.estaMort()) {
                Posicio p = animal.posicio;
                terrenys[p.x()][p.y()].eliminarAnimal(animal);
                listener.eliminar(animal);
                continue;
            }

            if(animal.teCries()) {
                int cries = animal.numeroCries();
                animal.reiniciarReproduccio();

                ArrayList<Animal> cries_llistat = new ArrayList<>();
                for(int i = 0; i < cries; i++) {
                    try {
                        Animal cria = (Animal)Class.forName(animal.especie.nom()).getDeclaredConstructor(Animal.class).newInstance(animal);
                        cries_llistat.add(cria);
                        afegirAnimal(cria);
                    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                            | InvocationTargetException | NoSuchMethodException | SecurityException
                            | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                moureAnimals(cries_llistat);
            }

            animal.modificarAlimentacio();
            animal.modificarVida();
            animal.modificarEdat();
            animal.modificarReproduccio();

            // Ajutnem animals i vegetals al mateix llistat
            ArrayList<Organisme> victimes = new ArrayList<Organisme>(animals);
            victimes.addAll(vegetals);

            // Mirem si pot menjar altres animals i vegetals al terreny
            for (Organisme victima : victimes) {
                for(Especie especie : animal.especie.aliments()) {
                    if(especie.equals(victima.especie)) {
                        animal.modificarAlimentacio(victima.especie.valorNutritiu());
                        Parasit parasit = animal.agafarParasit(especies);
                        if(parasit != null) {
                            listener.contagiar(animal, parasit);
                        }
                        victima.matar();
                    }
                }    
            }

            listener.actualitzar(animal);
        }
    }

    /**
     * Actualitza l'estat de tots els vegetals del llistat
     * @param vegetals ArrayList<Vegetal>
     */
    void actualitzarVegetals(ArrayList<Vegetal> vegetals) {
        for (Vegetal vegetal : vegetals) {
            vegetal.modificarVida();
            vegetal.modificarEdat();
            vegetal.modificarReproduccio();

            if(vegetal.estaMort()) {
                Posicio p = vegetal.posicio;
                terrenys[p.x()][p.y()].eliminarVegetal(vegetal);
                listener.eliminar(vegetal);
                continue;
            }

            if(vegetal.teCries()) {
                int cries = vegetal.numeroCries();
                vegetal.reiniciarReproduccio();
                reproduir(vegetal, cries, "vegetals");
            }

            listener.actualitzar(vegetal);
        }
    }

    /**
     * Actualitza l'estat de tots els virus del llistat
     * @param virus ArrayList<Virus>
     */
    void actualitzarVirus(ArrayList<Virus> virus, ArrayList<Animal> animals) {
        for (Virus _virus : virus) {
            _virus.modificarVida();
            _virus.modificarEdat();
            _virus.modificarReproduccio();

            if(_virus.estaMort()) {
                Posicio p = _virus.posicio;
                terrenys[p.x()][p.y()].eliminarVirus(_virus);
                listener.eliminar(_virus);
                continue;
            }

            if(_virus.teCries()) {
                int cries = 1;
                _virus.reiniciarReproduccio();
                reproduir(_virus, cries, "virus");
            }

            for(Animal a : animals) {
                // Calculem amb la probabilitat si s'ha d'infectar l'animal o no
                if(new Random().nextDouble()*100 <= _virus.numeroCries()) {
                    listener.infectar(a, _virus);
                    a.afegirVirus(_virus);
                }
            }

            listener.actualitzar(_virus);
        }
    }

    /**
     * Reprodueix l'organisme a terrenys propers
     * @param organisme Organisme
     * @param num_cries int
     * @param llistat String
     */
    private void reproduir(Organisme organisme, int num_cries, String llistat) {
        String origen = organisme.especie.origen();
        organisme.reiniciarReproduccio();

        try {
            Organisme cria = null;
            if(origen.equals("Vegetal")) cria = (Vegetal)Class.forName(organisme.especie.nom()).getDeclaredConstructor(Vegetal.class).newInstance(organisme);
            if(origen.equals("Virus")) cria = (Virus)Class.forName(organisme.especie.nom()).getDeclaredConstructor(Virus.class).newInstance(organisme);
            
            // Definir la posició on anirà
            ArrayList<Posicio> posicionsDisponibles = this.posicioDisponibles();

            for(Posicio posicio : posicionsDisponibles) {
                if(num_cries == 0) break;

                Posicio posicioCandidata = Posicio.sumar(organisme.posicio, posicio);

                // La posició ha de ser vàlida
                if (
                    this.posicioValida(posicioCandidata) && 
                    !terrenys[posicioCandidata.x()][posicioCandidata.y()].hiHaOrganisme(organisme.especie, llistat) && 
                    terrenys[posicioCandidata.x()][posicioCandidata.y()].potEntrar(cria)
                ) {
                    cria.posicio(posicioCandidata);
                    if(origen.equals("Vegetal")) afegirVegetal((Vegetal)cria);
                    if(origen.equals("Virus")) afegirVirus((Virus)cria);
                    
                    num_cries--;
                }
            }
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException
                | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}