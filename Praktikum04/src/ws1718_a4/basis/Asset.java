package ws1718_a4.basis;

import java.awt.Point;
import java.util.List;

import org.json.simple.JSONObject;

import ws1718_a4.assets.Assets.AssetTyp;

/**
 * Basisklasse für alle Assets im Spiel.
 * 
 * @author Philipp Jenke
 */
public abstract class Asset implements Jsonable {

  private static final String ZELLE_INDEX_I = "indexI";
  private static final String ZELLE_INDEX_J = "indexJ";
  private static final Object TYP = "TYP";

  /**
   * Zelle auf der sich der Spieler befindet.
   */
  protected Zelle zelle = null;

  /**
   * Typ des Assets
   */
  protected AssetTyp typ;

  protected Asset(AssetTyp typ) {
    this.typ = typ;
  }

  public Zelle getZelle() {
    return zelle;
  }

  public void setZelle(Zelle zelle) {
    this.zelle = zelle;
    if (zelle != null) {
      zelle.setAsset(this);
    }
  }

  public AssetTyp getTyp() {
    return typ;
  }

  @SuppressWarnings("unchecked")
  @Override
  public JSONObject toJson(Object metaInformation) {
    JSONObject jsonObjekt = new JSONObject();
    Zelle zelle = getZelle();
    if (zelle != null) {
      jsonObjekt.put(ZELLE_INDEX_I, zelle.getIndex().x);
      jsonObjekt.put(ZELLE_INDEX_J, zelle.getIndex().y);
    }
    jsonObjekt.put(TYP, typ.toString());
    return jsonObjekt;
  }

  /**
   * @param metaInformation
   *          Liste aller Zellen (damit die zugehörige Zelle gefunden werden
   *          kann).
   */
  @SuppressWarnings("unchecked")
  @Override
  public void fromJson(JSONObject jsonObjekt, Object metaInformation) {
    List<Zelle> zellenliste = ((List<Zelle>) metaInformation);
    typ = AssetTyp.valueOf((String) jsonObjekt.get(TYP));
    if (jsonObjekt.get(ZELLE_INDEX_I) != null
        && jsonObjekt.get(ZELLE_INDEX_J) != null) {

      Point zellenindex = new Point(
          ((Long) jsonObjekt.get(ZELLE_INDEX_I)).intValue(),
          ((Long) jsonObjekt.get(ZELLE_INDEX_J)).intValue());

      for (Zelle zelle : zellenliste) {
        if (zelle.getIndex().x == zellenindex.x
            && zelle.getIndex().y == zellenindex.y) {
          this.zelle = zelle;
          zelle.setAsset(this);
          break;
        }
      }
    }
  }
}
