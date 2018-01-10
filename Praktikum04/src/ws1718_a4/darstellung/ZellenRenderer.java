package ws1718_a4.darstellung;

import java.awt.Point;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ws1718_a4.basis.Asset;
import ws1718_a4.basis.Konstanten;
import ws1718_a4.basis.SpielZustand;
import ws1718_a4.basis.Zelle;
import ws1718_a4.basis.Konstanten.Richtung;

/**
 * Renderer für eine Zelle.
 * 
 * @author Philipp Jenke
 *
 */
public class ZellenRenderer {
  /**
   * Zeichne die Zelle im einen gegebenen Mittelpunkt.
   */
  public static void zeichneZelle(Zelle zelle, GraphicsContext gc,
      Point mittelpunkt) {

    if (zelle == null) {
      return;
    }

    boolean istSichtbar = (zelle.istSichtbar()
        || (SpielZustand.getInstance().getRenderFlags()
            & Konstanten.RENDER_FLAG_ALLE_ZELLEN_SICHTBAR) != 0);

    if (!istSichtbar) {
      return;
    }

    Point[] punkt = new Point[6];
    double[] xPoints = new double[6];
    double[] yPoints = new double[6];
    punkt[0] = Konstanten.getBildKoordinaten(
        new Point((int) (mittelpunkt.x - 0.5 * Konstanten.ZELLEN_SEITENLAENGE),
            (int) (mittelpunkt.y - Konstanten.ZELLEN_HOEHE)));
    punkt[1] = Konstanten.getBildKoordinaten(
        new Point((int) (mittelpunkt.x + 0.5 * Konstanten.ZELLEN_SEITENLAENGE),
            (int) (mittelpunkt.y - Konstanten.ZELLEN_HOEHE)));
    punkt[2] = Konstanten.getBildKoordinaten(
        new Point((int) (mittelpunkt.x + Konstanten.ZELLEN_SEITENLAENGE),
            (int) (mittelpunkt.y)));
    punkt[3] = Konstanten.getBildKoordinaten(
        new Point((int) (mittelpunkt.x + 0.5 * Konstanten.ZELLEN_SEITENLAENGE),
            (int) (mittelpunkt.y + Konstanten.ZELLEN_HOEHE)));
    punkt[4] = Konstanten.getBildKoordinaten(
        new Point((int) (mittelpunkt.x - 0.5 * Konstanten.ZELLEN_SEITENLAENGE),
            (int) (mittelpunkt.y + Konstanten.ZELLEN_HOEHE)));
    punkt[5] = Konstanten.getBildKoordinaten(
        new Point((int) (mittelpunkt.x - Konstanten.ZELLEN_SEITENLAENGE),
            (int) (mittelpunkt.y)));
    for (int i = 0; i < 6; i++) {
      xPoints[i] = punkt[i].x;
      yPoints[i] = punkt[i].y;
    }

    // Zellen-Inneres
    gc.beginPath();
    gc.setFill(Color.LIGHTGRAY);
    gc.fillPolygon(xPoints, yPoints, 6);

    // Zellen-Rand
    for (int i = 0; i < 6; i++) {
      gc.beginPath();
      if (zelle.istWand(Richtung.values()[i])) {
        // Levelrand
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
      } else {
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(1);
      }
      gc.moveTo(punkt[i].x, punkt[i].y);
      gc.lineTo(punkt[(i + 1) % 6].x, punkt[(i + 1) % 6].y);
      gc.stroke();
    }

    // Assets
    Asset asset = zelle.getAsset();
    if (asset != null) {
      AssetRenderer.zeichneAsset(gc, mittelpunkt, asset.getTyp());
    }
  }

  /**
   * Zeichnet eine Zelle des Spielfelds.
   */
  public static void zeichneZelle(Zelle zelle, GraphicsContext gc) {
    if (zelle == null ) {
      return;
    }
    Point mittelpunkt = Konstanten
        .getWeltkoordinatenVonZellenIndex(zelle.getIndex());
    zeichneZelle(zelle, gc, mittelpunkt);
  }
}
