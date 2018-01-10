package ws1718_a4.darstellung;

import java.awt.Point;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import ws1718_a4.assets.Assets;
import ws1718_a4.assets.Assets.AssetTyp;
import ws1718_a4.basis.Konstanten;

public class AssetRenderer {

  /**
   * Mapping zwischen Asset-Typ und Bild.
   */
  private static Map<AssetTyp, Image> assetImageMap = new HashMap<AssetTyp, Image>();

  /**
   * Zeichne das Asset in der angegebenen Zelle
   * 
   */
  public static void zeichneAsset(GraphicsContext gc, Point center,
      AssetTyp typ) {
    Image bild = assetImageMap.get(typ);
    if (bild == null) {
      return;
    }

    // Bild zeichnen
    Point pos = Konstanten.getBildKoordinaten(
        new Point((int) (center.getX() - Konstanten.ASSET_BILD_GROESSE / 2),
            (int) (center.getY() - Konstanten.ASSET_BILD_GROESSE / 2)));
    gc.drawImage(bild, pos.x, pos.y, Konstanten.ASSET_BILD_GROESSE,
        Konstanten.ASSET_BILD_GROESSE);
  }

  /**
   * Einlesen eines Bildes für ein Asset.
   * 
   * @param typ
   *          Typ des Assets
   * @param dateiName
   *          Dateiname des zugehörigen Bildes.
   */
  public static void ladeAssetBild(AssetTyp typ, String dateiName) {
    InputStream s = Assets.class.getResourceAsStream(dateiName);
    Image image = new Image(s);
    assetImageMap.put(typ, image);
  }

  /**
   * Laden aller Assets (Bilder).
   */
  public static void ladeAlleAssets() {
    ladeAssetBild(AssetTyp.SPIELER, "spielfigur_frau.png");
    ladeAssetBild(AssetTyp.NPC, "spielfigur_mann.png");
    ladeAssetBild(AssetTyp.BOESEWICHT, "boesewicht.png");
  }
}
