package ws1718_a4.darstellung;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ws1718_a4.basis.Konstanten;

public class Spiel extends Application {
	private SpielfeldRenderer spielfeld;

	@Override
	public void start(Stage buehne) throws Exception {
		BorderPane wurzel = new BorderPane();
		Scene szene = new Scene(wurzel, 700	, 500);
		buehne.setTitle("WS 17/18 - PM2 - Rette den Elf!");
		buehne.setScene(szene);
		 BorderPane ui = new BorderPane();
		   ui.setCenter(spielfeld);
		   ui.setLeft();
	}

}
