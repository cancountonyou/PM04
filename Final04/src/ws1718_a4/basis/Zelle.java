package ws1718_a4.basis;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ws1718_a4.basis.Konstanten.Richtung;

/**
 * Eine Zelle in einem Spiel.
 * 
 * @author Philipp Jenke
 */
public class Zelle implements Jsonable {

  private static final String ZELLE_INDEX_I = "indexI";
  private static final String ZELLE_INDEX_J = "indexJ";
  private static final String ZELLE_SICHTBAR = "sichtbar";
  private static final Object NACHBARN = "nachbarn";

  /**
   * Hier wird festgelegt welche der Zellseiten Wände sind.
   */
  private Link[] nachbar = new Link[6];

  /**
   * Mittelpunkt der Zelle.
   */
  private Point index = null;

  /**
   * Gibt an, ob die Zelle aktuell sichtbar ist.
   */
  boolean istSichtbar = false;

  /**
   * Asset, das sich auf der Zelle befinden.
   */
  private Asset asset = null;

  public Zelle() {
    this.index = null;
  }

  public Zelle(Point index) {
    this.index = new Point(index);
  }

  /**
   * Liefert wahr, wenn die Seite in der angegebenen Richtung eine Wand ist,
   * sonst falsch.
   */
  public Zelle getNachbarZelle(Richtung richtung) {
    if (nachbar[richtung.ordinal()] == null) {
      return null;
    }
    return nachbar[richtung.ordinal()].getAndereZelle(this);
  }

  public Point getIndex() {
    return index;
  }

  public boolean istSichtbar() {
    return istSichtbar;
  }

  /**
   * Zelle sichtbar setzen.
   */
  public void setSichtbar(boolean sichtbar) {
    istSichtbar = sichtbar;
  }

  public void setzeNachbar(Richtung richtung, Zelle nachbarZelle) {
    Link link = new Link(this, nachbarZelle);
    nachbar[richtung.ordinal()] = link;
    nachbarZelle.setzeLink(richtung.getGegenueber(), link);
  }

  private void setzeLink(Richtung richtung, Link link) {
    nachbar[richtung.ordinal()] = link;
  }

  public void setWand(Richtung richtung, boolean hatWand) {
    Link link = nachbar[richtung.ordinal()];
    if (link != null) {
      link.setWand(hatWand);
    }
  }

  /**
   * Liefert wahr wenn in der angegebenen Richtung eine Wand liegt.
   */
  public boolean istWand(Richtung richtung) {
    return nachbar[richtung.ordinal()] == null
        || nachbar[richtung.ordinal()].istWand();
  }

  public void setAsset(Asset asset) {
    this.asset = asset;
  }

  public Asset getAsset() {
    return asset;
  }

  public Link getLink(Richtung richtung) {
    return nachbar[richtung.ordinal()];
  }

  /**
   * @param metaInformation
   *          Map zwischen Link und dessen Index (Links werden indirekt über den
   *          Index (Reihenfolge) abgespeichert.
   */
  @SuppressWarnings("unchecked")
  @Override
  public JSONObject toJson(Object metaInformation) {
    JSONObject zellenObjekt = new JSONObject();
    Map<Link, Integer> linkMap = (Map<Link, Integer>) metaInformation;
    JSONArray links = new JSONArray();
    for (int i = 0; i < 6; i++) {
      Link link = getLink(Richtung.values()[i]);
      if (link == null) {
        links.add(-1);
      } else {
        links.add(linkMap.get(link));
      }
    }
    zellenObjekt.put(NACHBARN, links);
    zellenObjekt.put(ZELLE_INDEX_I, getIndex().x);
    zellenObjekt.put(ZELLE_INDEX_J, getIndex().y);
    zellenObjekt.put(ZELLE_SICHTBAR, istSichtbar);
    // Asset wird vom Asset selber gesetzt
    return zellenObjekt;
  }

  /**
   * @param metaInformation
   *          Liste aller Links (damit die Nachbarn gesetzt werden können).
   */
  @Override
  public void fromJson(JSONObject jsonObjekt, Object metaInformation) {
    @SuppressWarnings("unchecked")
    List<Link> links = (List<Link>) metaInformation;
    index = new Point(((Long) jsonObjekt.get(ZELLE_INDEX_I)).intValue(),
        ((Long) jsonObjekt.get(ZELLE_INDEX_J)).intValue());
    istSichtbar = (boolean) jsonObjekt.get(ZELLE_SICHTBAR);
    JSONArray nachbarn = (JSONArray) jsonObjekt.get(NACHBARN);
    for (int i = 0; i < 6; i++) {
      int nachbarIndex = ((Long) nachbarn.get(i)).intValue();
      if (nachbarIndex < 0) {
        nachbar[i] = null;
      } else {
        Link link = links.get(nachbarIndex);
        nachbar[i] = link;
        link.setZelle(this);
      }
    }

  }
}
