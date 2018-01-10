package ws1718_a4.controller;

import java.util.List;

import ws1718_a4.basis.Konstanten.Befehl;

/**
 * Knoten im Aktionsbaum.
 * 
 * @author Philipp Jenke
 */
public interface Aktion {

  /**
   * Verarbeitung des nächsten Befehls in der Befehlskette.
   * 
   * @return Liefert das Ergebnis der Verarbeitung.
   */
  public String verarbeite(List<Befehl> befehlskette);

}
