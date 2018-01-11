package ws1718_a4.basis;

import org.json.simple.JSONObject;

import ws1718_a4.assets.Assets.AssetTyp;

/**
 * Die Spielfigur, die vom Anwender gesteuert wird.
 * 
 * @author Philipp Jenke
 */
public class SpielfigurNPC extends Asset {

  public SpielfigurNPC() {
    super(AssetTyp.NPC);
  }

  @Override
  public void setZelle(Zelle zelle) {
    // Von alter Zelle entfernen.
    if (this.zelle != null) {
      this.zelle.setAsset(null);
    }
    super.setZelle(zelle);
  }

  @Override
  public void fromJson(JSONObject jsonObjekt, Object metaInformation) {
    if (jsonObjekt == null) {
      return;
    }
    super.fromJson(jsonObjekt, metaInformation);
    setZelle(zelle);
  }
}
