package ws1718_a4.leveleditor;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ws1718_a4.basis.Konstanten;
import ws1718_a4.basis.SpielZustand;
import ws1718_a4.basis.Zelle;
import ws1718_a4.darstellung.ZellenRenderer;

/**
 * Zeichnet die aktuelle Zelle
 * 
 * @author Philipp Jenke
 *
 */
public class ZellenEditorRenderer extends Canvas implements Observer {

  public ZellenEditorRenderer() {
    super(Konstanten.ZELLEN_SEITENLAENGE * 2 + Konstanten.CANVAS_RAND * 2,
        Konstanten.ZELLEN_SEITENLAENGE * 2 + Konstanten.CANVAS_RAND * 2);
    SpielZustand.getInstance().addObserver(this);
  }

  public void neuZeichnen() {
    GraphicsContext gc = getGraphicsContext2D();
    double w = getWidth();
    double h = getHeight();
    gc.clearRect(0, 0, w, h);
    gc.setFill(Color.WHITE);

    Zelle aktuelleZelle = SpielZustand.getInstance().getAktuelleZelle();
    ZellenRenderer.zeichneZelle(aktuelleZelle, gc,
        new Point((int) (getWidth() / 2 - Konstanten.CANVAS_RAND),
            (int) (getHeight() / 2 - Konstanten.CANVAS_RAND)));
  }

  @Override
  public void update(Observable o, Object arg) {
    neuZeichnen();
  }

}
