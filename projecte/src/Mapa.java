import static java.lang.Thread.sleep;

import java.util.ArrayList;
import java.util.Random;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.AlphaComposite;

import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

/**
 * Mapa s'encarrega de detectar clics i actualitzar l'entorn visual
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class Mapa implements InterficieListener {
    /**
     * Index terrenys.
     */
    public static final int INDEX_TERRENYS = 0;
    /**
     * Index vegetals.
     */
    public static final int INDEX_VEGETALS = 4;
    /**
     * Index terrenys.
     */
    public static final int INDEX_ANIMALS  = 1;
    /**
     * Index obstacles.
     */
    public static final int INDEX_OBSTACLES  = 5;
    /**
     * Index virus.
     */
    public static final int INDEX_VIRUS  = 2;
    /**
     * Index virus.
     */
    public static final int INDEX_PARASIT  = 3;
    /**
     * Llista de Pairs, on cada espècie té una imatge.
     */
    ArrayList<Pair<Especie, ImageIcon>> especies_imatge = new ArrayList<>();
    /**
     * Llista de Pairs, on cada Terreny té un label.
     */
    ArrayList<Pair<Terreny, JLabel>> terrenys_label = new ArrayList<>();
    /**
     * Llista de Pairs, on cada Animal té un label.
     */
    ArrayList<Pair<Animal, JLabel>> animals_label = new ArrayList<>();
    /**
     * Llista de Pairs, on cada Vegetal té un label.
     */
    ArrayList<Pair<Vegetal, JLabel>> vegetals_label = new ArrayList<>();
    /**
     * Llista de Pairs, on cada Virus té un label.
     */
    ArrayList<Pair<Virus, JLabel>> virus_label = new ArrayList<>();
    /**
     * Llista de Pairs, on cada Parasit té un label.
     */
    ArrayList<Pair<Parasit, JLabel>> parasits_label = new ArrayList<>();
    /**
     * Llista de Pairs, on cada Terrent té una imatge.
     */
    ArrayList<Pair<TipusTerreny, ImageIcon>> tipusTerrenys_imatges = new ArrayList<>();
    /**
     * Llista de Pairs, on cada Especie té una imatge.
     */
    ArrayList<Pair<Especie, ImageIcon>> especies_imatges = new ArrayList<>();
    /**
     * Mida del terreny.
     */
    private int MIDA_TERRENY;
    /**
     * Velocitat en ms.
     */
    private int VELOCITAT;
    /**
     * Finestre.
     */
    private JFrame finestra;
    /**
     * Mapa gràfic.
     */
    private MapaGrafic mapa;
    /**
     * Ecosistema.
     */
    private Ecosistema ecosistema;
    /**
     * Si és automàtic.
     */
    private Boolean automatica;

    /**
     * Constructor
     * @param conf Configuration
     */
    Mapa(Configuration conf) {
        this.MIDA_TERRENY = conf.MIDA_TERRENY;
        this.VELOCITAT = conf.VELOCITAT;
        this.mapa = new MapaGrafic(conf.x, conf.y, MIDA_TERRENY);
        this.finestra = new JFrame();
        
        finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finestra.setResizable(false);
        finestra.setTitle("ECOSISTEMA - Sergi i Josep");
        
        finestra.add(mapa);
        finestra.pack();
        finestra.setLocationRelativeTo(null);

        finestra.setVisible(true);

        mapa.setPreferredSize(new Dimension(conf.x*MIDA_TERRENY, conf.y*MIDA_TERRENY));
        mapa.setBackground(Color.WHITE);
        mapa.setDoubleBuffered(true);
        mapa.setLayout(null);

        finestra.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    ecosistema.moviment();
                } catch (ArrayIndexOutOfBoundsException e1) {
                    //e1.printStackTrace();
                } catch (InterruptedException e1) {
                    //e1.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) { }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) { }
        });
    }

    /**
     * Inicia l'ecosistema
     * @param ecosistema Ecosistema
     * @param automatica Boolean
     */
    @Override
    public void iniciar(Ecosistema ecosistema, Boolean automatica) {
        this.ecosistema = ecosistema;
        this.automatica = automatica;
        try {
            // Si és automàtic
            if(automatica) {
                while(true) {
                    ecosistema.moviment();
                    sleep(this.VELOCITAT);
                }
            } else { // Si la vol manual
                // Actuarà el listener
                ecosistema.moviment();
            }
        } catch (InterruptedException e) { }
    }

    /**
     * Actualitza el terreny al mapa visual
     * @param terreny Terreny
     */
    @Override
    public void actualitzar(Terreny terreny) {
        JLabel imatge = buscarLabel(terreny, terrenys_label);

        Posicio pos = terreny.posicio();
        int x = pos.x() * MIDA_TERRENY;
        int y = pos.y() * MIDA_TERRENY;

        imatge.setLocation(x, y);
        imatge.setVisible(true);
    }

    /**
     * Actualitza el vegetal al mapa visual
     * @param vegetal Vegetal
     */
    @Override
    public void actualitzar(Vegetal vegetal) {
        actualitzarOrganisme(vegetal, vegetals_label, INDEX_VEGETALS);
    }

    /**
     * Actualitza el vegetal al mapa visual
     * @param vegetal Vegetal
     */
    @Override
    public void actualitzar(Virus virus) {
        actualitzarOrganisme(virus, virus_label, INDEX_VIRUS);
    }

    /**
     * Actualitza el paràsit al mapa visual
     * @param parasit Parasit
     */
    @Override
    public void actualitzar(Parasit parasit) {
        actualitzarOrganisme(parasit, parasits_label, INDEX_PARASIT);

    }

    /**
     * Actualitza l'animal al mapa visual
     * @param animal Animal
     */
    @Override
    public void actualitzar(Animal animal) {
        JLabel imatge = buscarLabel(animal, animals_label);
        moure(animal, imatge);
    }

    /**
     * Afegeix el terreny al mapa visual
     * @param t Terreny
     */
    @Override
    public void afegir(Terreny t) {
        JLabel label = new JLabel(buscarImatge(t.tipusTerreny));
        label.setVisible(false);
        mapa.add(label);

        mapa.setComponentZOrder(label, INDEX_TERRENYS);
        label.setSize(MIDA_TERRENY, MIDA_TERRENY);

        terrenys.add(t);
        terrenys_label.add(new Pair<Terreny,JLabel>(t, label));

        this.actualitzar(t);
    }

    /**
     * Afegeix l'animal al mapa visual
     * @param a Animal
     */
    @Override
    public void afegir(Animal a) {
        afegirOrganisme(a);

    }

    /**
     * Afegeix el vegetal al mapa visual
     * @param v Vegetal
     */
    @Override
    public void afegir(Vegetal v) {
        afegirOrganisme(v);
    }

    /**
     * Afegeix el virus al mapa visual
     * @param v Virus
     */
    @Override
    public void afegir(Virus v) {
        afegirOrganisme(v);
    }

    /**
     * Afegeix el paràsit al mapa visual
     * @param p Parasit
     */
    @Override
    public void afegir(Parasit p) {
        afegirOrganisme(p);
    }

    /**
     * Elimina a l'animal del mapa visual
     * @param a Animal
     */
    @Override
    public void eliminar(Animal a) {
        JLabel label = buscarLabel(a, animals_label);
        if(label == null) return;

        // Afegim el zombie de l'animal mort
        JLabel label_mort = pintar(a.especie, new Color(255,0,0,128));
        label_mort.setVisible(false);
        mapa.add(label_mort);

        mapa.setComponentZOrder(label_mort, INDEX_ANIMALS);
        label_mort.setSize(MIDA_TERRENY, MIDA_TERRENY);
        label_mort.setLocation(label.getX(), label.getY());
        label_mort.setVisible(true);

        // Eliminem l'animal antic
        int index = buscarElement(a, animals_label);
        mapa.remove(label);
        mapa.validate();
        mapa.repaint();

        try {
            new Thread(() -> {
                for(int i = 0; i < 10; i++) {
                    try {
                        mapa.setComponentZOrder(label_mort, INDEX_ANIMALS);
                        label_mort.setVisible(true);
                        sleep(100);
                        label_mort.setVisible(false);
                        sleep(100);
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                    }
                }
                mapa.remove(label_mort);
                mapa.validate();
                mapa.repaint();
            }).start();
        } catch (ArrayIndexOutOfBoundsException e) {

        }

        

        animals_label.remove(index);
        animals.remove(a); 
    }

    /**
     * Elimina el vegetal del mapa visual
     * @param v Vegetal
     */
    @Override
    public void eliminar(Vegetal v) {
        eliminarOrganisme(v, vegetals, vegetals_label);
    }

    /**
     * Elimina el virus del mapa visual
     * @param v Virus
     */
    @Override
    public void eliminar(Virus v) {
        eliminarOrganisme(v, virus, virus_label);
    }

    /**
     * Elimina el paràstit del mapa visual
     * @param p Parasit
     */
    @Override
    public void eliminar(Parasit p) {
        eliminarOrganisme(p, parasits, parasits_label);
    }

    /**
     * Afegim les imatges carregades de les espècies
     * @param llistat llista de pairs on cada Especie tè una icona.
     */
    public void afegirImatgesEspecies(ArrayList<Pair<Especie, ImageIcon>> llistat) {
        especies_imatges = llistat;
    }

    /**
     * Afegim les imatges carregades dels tipus de terreny
     * @param llistat llista de pairs on cada Terreny tè una icona.
     */
    public void afegirImatgesTerrenys(ArrayList<Pair<TipusTerreny, ImageIcon>> llistat) {
        tipusTerrenys_imatges = llistat;
    }

    /**
     * S'ha infectat l'animal amb un virus
     * @param a Animal
     * @param v Virus
     */
    @Override
    public void infectar(Animal a, Virus v) {
        JLabel label = buscarLabel(a, animals_label);
        if(label == null) return;

        // Canviem el color de l'animal
        JLabel label_pintat = pintar(a.especie, new Color(124,255,0,128));
        label_pintat.setVisible(false);
        mapa.add(label_pintat);

        mapa.setComponentZOrder(label_pintat, INDEX_ANIMALS);
        label_pintat.setSize(MIDA_TERRENY, MIDA_TERRENY);
        label_pintat.setLocation(label.getX(), label.getY());
        label_pintat.setVisible(true);

        // Eliminem l'animal antic
        int index = buscarElement(a, animals_label);
        mapa.remove(label);
        mapa.validate();
        mapa.repaint();

        animals_label.remove(index);
        animals_label.add(new Pair<Animal,JLabel>(a, label_pintat));
    }

    /**
     * S'ha contagiat l'animal amb un paràsit
     * @param a Animal
     * @param v Parasit
     */
    @Override
    public void contagiar(Animal a, Parasit p) {
        JLabel label = buscarLabel(a, animals_label);
        if(label == null) return;

        // Canviem el color de l'animal
        JLabel label_pintat = pintar(a.especie, new Color(255,165,0,128));
        label_pintat.setVisible(false);
        mapa.add(label_pintat);

        mapa.setComponentZOrder(label_pintat, INDEX_ANIMALS);
        label_pintat.setSize(MIDA_TERRENY, MIDA_TERRENY);
        label_pintat.setLocation(label.getX(), label.getY());
        label_pintat.setVisible(true);

        // Eliminem l'animal antic
        int index = buscarElement(a, animals_label);
        mapa.remove(label);
        mapa.validate();
        mapa.repaint();

        animals_label.remove(index);
        animals_label.add(new Pair<Animal,JLabel>(a, label_pintat));
    }



    /**
     * Busca al mapa el label de l'objecte
     * @param e Object
     * @param llista ArrayList
     * @return JLabel
     */
    private JLabel buscarLabel(Object e, ArrayList llista) {
        int index = buscarElement(e, llista);
        if(index == -1) return null;
        return ((Pair<Object, JLabel>) llista.get(index)).valor();
    }

    /**
     * Busca al mapa el label de l'objecte i retorna l'index
     * @param e Object
     * @param llista ArrayList
     * @return int
     */
    private int buscarElement(Object e, ArrayList llista) {
        int index = 0;
        for(Pair<? extends Object, JLabel> i : (ArrayList<Pair>)llista) {
            if(i.objecte().equals(e)) return index;
            index++;
        }
        return -1;
    }

    /**
     * Busca l'imatge de l'especie
     * @param o Especie
     * @return ImageIcon
     */
    private ImageIcon buscarImatge(Especie o) {
        for(Pair<Especie, ImageIcon> e : especies_imatges) {
            if(e.objecte().equals(o)) return e.valor();
        }
        return null;
    }

    /**
     * Busca l'imatge del terreny
     * @param o TipusTerreny
     * @return ImageIcon
     */
    private ImageIcon buscarImatge(TipusTerreny o) {
        for(Pair<TipusTerreny, ImageIcon> tt : tipusTerrenys_imatges) {
            if(tt.objecte().equals(o)) return tt.valor();
        }
        return null;
    }

    /**
     * Mou l'animal a la nova posició de manera progressiva
     * @param animal Animal
     * @param label JLabel
     * @return ImageIcon
     */
    private void moure(Animal animal, JLabel label) {
        Posicio pos = animal.posicio();
        Posicio a_pos = animal.posicioAnterior();
        Random random = new Random(); // Per a que no estigui al mig del bloc

        int new_x = pos.x() * MIDA_TERRENY + random.nextInt((MIDA_TERRENY/3) + (MIDA_TERRENY/3)) - (MIDA_TERRENY/3);
        int new_y = pos.y() * MIDA_TERRENY + random.nextInt((MIDA_TERRENY/3) + (MIDA_TERRENY/3)) - (MIDA_TERRENY/3);
        int old_x = label.getX();
        int old_y = label.getY();
        int DIRECTION_X = new_x > old_x ? 1 : -1;
        int DIRECTION_Y = new_y > old_y ? 1 : -1;

        mapa.setComponentZOrder(label, INDEX_ANIMALS);

        new Thread(() -> {
            try {
                // Si en mode manual es va molt ràpid poden haver errors al ser asincron, aquí comprovarem que estigui tot be
                if((VELOCITAT < 200 && automatica) || a_pos == null) { // Si el programa ha d'anar molt ràpid no hi haurà animacions
                    label.setLocation(new_x, new_y);
                } else if(Math.abs(a_pos.x()*MIDA_TERRENY - old_x) > MIDA_TERRENY*1.33 || Math.abs(a_pos.y()*MIDA_TERRENY - old_y) > MIDA_TERRENY*1.33) {
                    label.setLocation(new_x, new_y);
                } else {
                    // Calculem la velocitat que utilitzarem per a moure els animals
                    int ratio = VELOCITAT / MIDA_TERRENY;
                    int temps = 1;
                    if(!automatica) temps = 1;
                    else if(ratio > 1000) temps = 50;
                    else if(ratio > 500) temps = 10;
                    else if(ratio > 400) temps = 6;
                    else if(ratio > 300) temps = 3;
                    else if(ratio > 200) temps = 1;

                    int tmp_x = label.getX();
                    int tmp_y = label.getY();

                        while(new_x != tmp_x || new_y != tmp_y) {
                            if(new_x != tmp_x) label.setLocation(tmp_x+DIRECTION_X, tmp_y);
                            if(new_y != tmp_y) label.setLocation(tmp_x, tmp_y+DIRECTION_Y);

                            tmp_x = label.getX();
                            tmp_y = label.getY();
                            sleep(temps);
                        }
                }
            } catch (InterruptedException | NullPointerException | ArrayIndexOutOfBoundsException e1) {
                //e1.printStackTrace();
            }
        }).start();
    }

    /**
     * Torna un l'imatge de l'especie pintada amb el color passat per paràmetre
     * @param e Especie
     * @param color Color
     * @return JLabel
     */
    private JLabel pintar(Especie e, Color color) {
        Image image = buscarImatge(e).getImage(); 
        BufferedImage buffered = new BufferedImage(
            image.getWidth(null), 
            image.getHeight(null),
            BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g = buffered.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        int w = buffered.getWidth();
        int h = buffered.getHeight();
        BufferedImage dyed = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = dyed.createGraphics();
        g2.drawImage(buffered, 0,0, null);
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.setColor(color);
        g2.fillRect(0,0,w,h);
        g2.dispose();
        
        return new JLabel(new ImageIcon(dyed));
    }

    /**
     * Elimina l'organisme del mapa visual
     * @param o Organisme
     * @param llistat ArrayList<Parasit>
     * @param llistat_label ArrayList<Pair<Virus, JLabel>>
     */
    private void eliminarOrganisme(Organisme o, ArrayList<?extends Organisme> llistat, ArrayList<? extends Pair> llistat_label) {
        JLabel label = buscarLabel(o, llistat_label);
        if(label == null);
        int index = buscarElement(o, llistat_label);
        mapa.remove(label);
        mapa.validate();
        mapa.repaint();

        llistat_label.remove(index);
        llistat.remove(o);
    }

    /**
     * Afegeix l'organisme del mapa visual
     * @param o Organisme
     */
    private void afegirOrganisme(Organisme o) {
        JLabel label = new JLabel(buscarImatge(o.especie));
        label.setVisible(false);
        mapa.add(label);

        mapa.setComponentZOrder(label, INDEX_VIRUS);
        label.setSize(MIDA_TERRENY, MIDA_TERRENY);  
        label.setLocation(o.posicio.x()*MIDA_TERRENY, o.posicio.y()*MIDA_TERRENY); 
        label.setVisible(true);

        if(o.especie.origen().equals("Animal")) {
            animals.add((Animal)o);
            animals_label.add(new Pair<Animal,JLabel>((Animal)o, label));
            mapa.setComponentZOrder(label, INDEX_ANIMALS);
            this.actualitzar((Animal)o);
            return;
        }

        if(o.especie.origen().equals("Vegetal")) {
            vegetals.add((Vegetal)o);
            vegetals_label.add(new Pair<Vegetal,JLabel>((Vegetal)o, label));
            mapa.setComponentZOrder(label, INDEX_VEGETALS);
            this.actualitzar((Vegetal)o);
            return;
        }

        if(o.especie.origen().equals("Virus")) {
            virus.add((Virus)o);
            virus_label.add(new Pair<Virus,JLabel>((Virus)o, label));
            mapa.setComponentZOrder(label, INDEX_VIRUS);
            this.actualitzar((Virus)o);
            return;
        }

        if(o.especie.origen().equals("Parasit")) {
            parasits.add((Parasit)o);
            parasits_label.add(new Pair<Parasit,JLabel>((Parasit)o, label));
            mapa.setComponentZOrder(label, INDEX_PARASIT);
            this.actualitzar((Parasit)o);
            return;
        }
    }

    /**
     * Actualitza l'organisme
     * @param o Organisme
     * @param labels ArrayList<Pair<Virus, JLabel>>
     * @param index int
     */
    private void actualitzarOrganisme(Organisme o, ArrayList<? extends Pair> labels, int index) {
        JLabel imatge = buscarLabel(o, labels);
        Posicio pos = o.posicio();

        int x = pos.x() * MIDA_TERRENY;
        int y = pos.y() * MIDA_TERRENY;

        if(!o.especie.origen().equals("Vegetal")) 
            mapa.setComponentZOrder(imatge, index);

        imatge.setLocation(x, y);
        imatge.setVisible(true);
    }

    
}
