package ws1718_a4.controller;

import java.util.List;

import ws1718_a4.basis.Asset;
import ws1718_a4.basis.SpielZustand;
import ws1718_a4.basis.Spielfigur;
import ws1718_a4.basis.Zelle;
import ws1718_a4.basis.Konstanten.Befehl;
import ws1718_a4.basis.Konstanten.Richtung;
import ws1718_a4.basis.Konstanten.SpielStatus;

/**
 * Aktion für die Bewegung in eine Richtung.
 * 
 * @author Philipp Jenke
 *
 */
public class AktionGehe extends RichtungsAktion {

  @Override
  public String verarbeite(List<Befehl> befehlskette) {
    if (befehlskette.size() != 1) {
      return "Ungültige Befehlskette.";
    }

    Befehl befehl = befehlskette.get(0);
    if (!richtungsBefehle.contains(befehl)) {
      return "Ungültiger Richtungsbefehl: " + befehl.toString();
    }

    // Bewegung in die angegebene Richtung
    Richtung richtung = befehl2Richtung(befehl);
    Spielfigur spielfigur = SpielZustand.getInstance().getAktuellerLevel()
        .getSpielfigur();
    Zelle spielfigurZelle = spielfigur.getZelle();

    // Wand?
    if (spielfigurZelle.istWand(richtung)) {
      return "Da ist eine Wand!";
    }

    // Ziel-Zelle belegt?
    Zelle zielZelle = spielfigurZelle.getNachbarZelle(richtung);
    Asset asset = zielZelle.getAsset();
    if (asset != null) {
      switch (asset.getTyp()) {
      case BOESEWICHT:
        SpielZustand.getInstance().setSpielStatus(SpielStatus.VERLOREN);
        return "Vom Boesewicht gefressen - Game over!";
      default:
        return "Hier steht doch schon jemand!!";
      }
    }
    spielfigur.setZelle(zielZelle);

    return "Bewege Spielfigur in Richtung " + befehl.toString();
  }

}
