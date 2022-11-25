import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JPanel;

/**
 * MapaGrafic és el JPanel de l'interficie
 * @author Sergi Canals Ortega u1972852
 * @author Josep Maria Pou Comerma u1972857
 */

public class MapaGrafic extends JPanel {
    /**
     * Constructor
     * @param x int
     * @param y int
     * @param m_terreny int
     */
    public MapaGrafic(int x, int y, int m_terreny) {
        this.setPreferredSize(new Dimension(x*m_terreny, y*m_terreny));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.setLayout(null);
    }
}
