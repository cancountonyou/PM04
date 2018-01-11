package ws1718_a4.leveleditor;

import java.awt.Point;
import java.util.Iterator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import ws1718_a4.basis.Konstanten;
import ws1718_a4.basis.SpielZustand;
import ws1718_a4.basis.Zelle;
import ws1718_a4.basis.Konstanten.Richtung;
import ws1718_a4.darstellung.SpielfeldRenderer;
import ws1718_a4.darstellung.ZellenRenderer;

/**
 * Level-Editor für das Spiel
 * 
 * @author Philipp Jenke
 */
public class SpielfeldEditorRenderer extends SpielfeldRenderer {

  public SpielfeldEditorRenderer() {
    setOnMouseClicked(
        ereignis -> waehleZelle(ereignis.getX(), ereignis.getY()));

    // Neuzeichnen anfordern.
    neuzeichnen();
  }

  /**
   * Wähle aktuelle Zelle durch Mausklick.
   */
  private void waehleZelle(double x, double y) {
    Point weltPunkt = Konstanten
        .getWeltKoordinaten(new Point((int) x, (int) y));
    setzeAktuelleZelle(weltPunkt);
  }

  /**
   * Setze als aktuelle Zelle die, deren Mittelpunkt dem Punkt am nächsten
   * liegt.
   */
  private void setzeAktuelleZelle(Point weltPunkt) {
    SpielZustand.getInstance().setAktuelleZelle(null);
    double besteEntfernung = Double.POSITIVE_INFINITY;
    for (Iterator<Zelle> it = SpielZustand.getInstance().getAktuellerLevel()
        .getZellenIterator(); it.hasNext();) {
      Zelle zelle = it.next();
      Point zellenMittelpunkt = Konstanten
          .getWeltkoordinatenVonZellenIndex(zelle.getIndex());
      double entfernung = zellenMittelpunkt.distanceSq(weltPunkt);
      if (entfernung < besteEntfernung) {
        SpielZustand.getInstance().setAktuelleZelle(zelle);
        besteEntfernung = entfernung;
      }
    }
    neuzeichnen();
  }

  @Override
  protected void zeichneZellen(GraphicsContext gc) {
    for (Iterator<Zelle> it = SpielZustand.getInstance().getAktuellerLevel()
        .getZellenIterator(); it.hasNext();) {
      Zelle zelle = it.next();

      ZellenRenderer.zeichneZelle(zelle, gc);
      if (zelle == SpielZustand.getInstance().getAktuelleZelle()) {
        Point mittelpunkt = Konstanten
            .getWeltkoordinatenVonZellenIndex(zelle.getIndex());
        Point[] punkt = new Point[6];
        double[] xPoints = new double[6];
        double[] yPoints = new double[6];
        punkt[0] = Konstanten.getBildKoordinaten(new Point(
            (int) (mittelpunkt.x - 0.5 * Konstanten.ZELLEN_SEITENLAENGE),
            (int) (mittelpunkt.y - Konstanten.ZELLEN_HOEHE)));
        punkt[1] = Konstanten.getBildKoordinaten(new Point(
            (int) (mittelpunkt.x + 0.5 * Konstanten.ZELLEN_SEITENLAENGE),
            (int) (mittelpunkt.y - Konstanten.ZELLEN_HOEHE)));
        punkt[2] = Konstanten.getBildKoordinaten(
            new Point((int) (mittelpunkt.x + Konstanten.ZELLEN_SEITENLAENGE),
                (int) (mittelpunkt.y)));
        punkt[3] = Konstanten.getBildKoordinaten(new Point(
            (int) (mittelpunkt.x + 0.5 * Konstanten.ZELLEN_SEITENLAENGE),
            (int) (mittelpunkt.y + Konstanten.ZELLEN_HOEHE)));
        punkt[4] = Konstanten.getBildKoordinaten(new Point(
            (int) (mittelpunkt.x - 0.5 * Konstanten.ZELLEN_SEITENLAENGE),
            (int) (mittelpunkt.y + Konstanten.ZELLEN_HOEHE)));
        punkt[5] = Konstanten.getBildKoordinaten(
            new Point((int) (mittelpunkt.x - Konstanten.ZELLEN_SEITENLAENGE),
                (int) (mittelpunkt.y)));
        for (int i = 0; i < 6; i++) {
          xPoints[i] = punkt[i].x;
          yPoints[i] = punkt[i].y;
        }

        // Zelle-Markierung
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
        gc.setGlobalAlpha(0.5);
        gc.beginPath();
        gc.setFill(Color.RED);
        gc.fillPolygon(xPoints, yPoints, 6);
        gc.setGlobalAlpha(1);
      }
    }
  }

  public void setWandAktuelleZelle(Richtung richtung, boolean hatWand) {
    if (SpielZustand.getInstance().getAktuelleZelle() == null) {
      return;
    }
    SpielZustand.getInstance().getAktuelleZelle().setWand(richtung, hatWand);
    neuzeichnen();
  }
}
