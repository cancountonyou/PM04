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

public class Spiel extends Application {
	private SpielfeldRenderer spielfeld;
	private SpielSteuerung spielsteuerung;
	public static String leveldatei = "level01.json";

	@Override
	public void start(Stage buehne) throws Exception {
		spielfeld = new SpielfeldRenderer();
		Controller c1 = new Controller((Neuzeichnen) spielfeld::neuzeichnen);
		BorderPane wurzel = new BorderPane();
		spielsteuerung = new SpielSteuerung(c1);
		wurzel.setCenter(spielfeld);
		wurzel.setLeft(spielsteuerung.getLayoutPane());
		Level level = LevelIO.levelLaden(Assets.class.getResourceAsStream(leveldatei));
		SpielZustand.getInstance().setAktuellerLevel(level);
		SpielZustand.getInstance().setSpielStatus(SpielStatus.SPIELER_ZUG);
		Scene szene = new Scene(wurzel, Konstanten.FENSTER_BREITE,Konstanten.FENSTER_HOEHE);
		buehne.setTitle("WS 17/18 - PM2 - Rette den Elf!");
		buehne.setScene(szene);
		buehne.show();
		spielfeld.neuzeichnen();
	}

	public static void main(String[] args) {
		launch();
	}

}
