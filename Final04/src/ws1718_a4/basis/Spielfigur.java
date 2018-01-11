package ws1718_a4.basis;

import org.json.simple.JSONObject;

import ws1718_a4.assets.Assets.AssetTyp;
import ws1718_a4.basis.Konstanten.Richtung;

/**
 * Die Spielfigur, die vom Anwender gesteuert wird.
 * 
 * @author Philipp Jenke
 */
public class Spielfigur extends Asset {

  public Spielfigur() {
    super(AssetTyp.SPIELER);
  }

  @Override
  public void setZelle(Zelle zelle) {
    // Von alter Zelle entfernen.
    if (this.zelle != null) {
      this.zelle.setAsset(null);
    }

    super.setZelle(zelle);

    // Sichtbarkeit erneuern.
    if (zelle != null) {
      zelle.setSichtbar(true);
      for (Richtung richtung : Richtung.values()) {
        if (zelle.getNachbarZelle(richtung) != null
            && !zelle.getLink(richtung).istWand()) {
          zelle.getNachbarZelle(richtung).setSichtbar(true);
        }
      }
    }
  }

  @Override
  public void fromJson(JSONObject jsonObjekt, Object metaInformation) {
    super.fromJson(jsonObjekt, metaInformation);
    setZelle(zelle);
  }
}
