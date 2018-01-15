/**
* Praktikum PM2, WS17/18
* Gruppe: Daniel Biederman, Katerina Milenkovski 
* Aufgabe: Aufgabenblatt 4
* 
*/
package ws1718_a4.darstellung;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ws1718_a4.assets.Assets;
import ws1718_a4.basis.Konstanten;
import ws1718_a4.basis.Konstanten.SpielStatus;
import ws1718_a4.basis.Level;
import ws1718_a4.basis.LevelIO;
import ws1718_a4.basis.Neuzeichnen;
import ws1718_a4.basis.SpielZustand;
import ws1718_a4.controller.Controller;
/**
 * 
 * Application zum Starten eines Spieles.
 *  
 * @author Daniel Biedermann, Katerina Milenkovski
 *
 */
public class Spiel extends Application {
	private SpielfeldRenderer spielfeld;
	private SpielSteuerung spielsteuerung;
	public static String leveldatei = "level01.json";

	/**
	 * Start-Methode.Erzeugt Fenster und zugehörige Komponenten und startet bei Aufruf ein Spiel.
	 * 
	 * @param Stage
	 * @exception Exception
	 * 
	 */
	@Override
	public void start(Stage buehne) throws Exception {
		//Erstellen der Spiel Komponenten , Spielfeld / Controller / Spielsteuerung
		spielfeld = new SpielfeldRenderer();
		Controller c1 = new Controller((Neuzeichnen) spielfeld::neuzeichnen);
		spielsteuerung = new SpielSteuerung(c1);
		BorderPane wurzel = new BorderPane();
		
		//Erstellung des Fensters, initialiseren des Levels und neuzeichnen des Spielfelds
		Scene szene = new Scene(wurzel, Konstanten.FENSTER_BREITE,Konstanten.FENSTER_HOEHE);
		buehne.setTitle("WS 17/18 - PM2 - Rette den Elf!");
		wurzel.setCenter(spielfeld);
		wurzel.setLeft(spielsteuerung.getLayoutPane());
		Level level = LevelIO.levelLaden(Assets.class.getResourceAsStream(leveldatei));
		SpielZustand.getInstance().setAktuellerLevel(level);
		SpielZustand.getInstance().setSpielStatus(SpielStatus.SPIELER_ZUG);
		buehne.setScene(szene);
		buehne.show();
		spielfeld.neuzeichnen();
	}

	public static void main(String[] args) {
		launch();
	}

}
