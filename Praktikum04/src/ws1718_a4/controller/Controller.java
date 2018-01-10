package ws1718_a4.controller;

import java.util.ArrayList;
import java.util.List;

import ws1718_a4.basis.Konstanten;
import ws1718_a4.basis.Neuzeichnen;
import ws1718_a4.basis.SpielZustand;
import ws1718_a4.basis.Konstanten.Befehl;
import ws1718_a4.basis.Konstanten.SpielStatus;

/**
 * Zentraler Controller für die Interaktion mit einem Spiel.
 * 
 * @author Philipp Jenke
 *
 */
public class Controller {

  /**
   * Wurzelknoten im Aktionsbaum
   */
  private Aktion wurzelknoten;

  private Neuzeichnen neuzeichnenCallback;

  public Controller(Neuzeichnen neuzeichnenCallback) {
    wurzelknoten = new AktionWurzel();
    this.neuzeichnenCallback = neuzeichnenCallback;
  }

  /**
   * Zentraler Einstiegspunkt: Übergabe eines Befehls an den Controller. Der
   * Befehl ist ein String und muss dem Regelsystem für Befehle genügen.
   * 
   * @return Liefert eine Rückmeldung zum durchgeführten Befehl.
   */
  public String befehlVerarbeiten(String text) {

    if (SpielZustand.getInstance()
        .getSpielStatus() != SpielStatus.SPIELER_ZUG) {
      return "Spieler ist nicht am Zug!";
    }

    // Liste von Befehlen aus Befehlstext extrahieren.
    List<Konstanten.Befehl> befehlskette = new ArrayList<Konstanten.Befehl>();
    text = text.trim();
    boolean found = true;
    while (found) {
      found = false;
      for (Befehl befehl : Befehl.values()) {
        if (text.startsWith(befehl.toString())) {
          befehlskette.add(befehl);
          text = text.substring(befehl.toString().length()).trim();
          found = true;
          break;
        }
      }
    }

    if (befehlskette.size() == 0) {
      return "Ungültige Befehle: " + text;
    }

    String rueckmeldeung = wurzelknoten.verarbeite(befehlskette);
    neuzeichnenCallback.neuzeichnen();
    return rueckmeldeung;
  }

}
