package ws1718_a4.darstellung;

import java.util.Iterator;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ws1718_a4.basis.Konstanten;
import ws1718_a4.basis.Neuzeichenbar;
import ws1718_a4.basis.SpielZustand;
import ws1718_a4.basis.Zelle;

/**
 * Renderer für ein Level
 * 
 * @author Philipp Jenke
 *
 */
public class SpielfeldRenderer extends Canvas implements Neuzeichenbar {

  public SpielfeldRenderer() {
    super(Konstanten.CANVAS_GROESSE, Konstanten.CANVAS_GROESSE);
    AssetRenderer.ladeAlleAssets();
  }

  /**
   * Zeichnet das gesamte Spielfeld
   */
  public void neuzeichnen() {
    GraphicsContext gc = getGraphicsContext2D();
    double w = getWidth();
    double h = getHeight();
    gc.clearRect(0, 0, w, h);
    gc.setFill(Color.WHITE);

    zeichneZellen(gc);
  }

  /**
   * Zeichnet alle Zellen des Spielfelds. Zentrierung auf der Zeichenfläche.
   */
  protected void zeichneZellen(GraphicsContext gc) {
    for (Iterator<Zelle> it = SpielZustand.getInstance().getAktuellerLevel().getZellenIterator(); it.hasNext();) {
      Zelle zelle = it.next();
      ZellenRenderer.zeichneZelle(zelle, gc);
    }
  }
}
