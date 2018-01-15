/**
* Praktikum PM2, WS17/18
* Gruppe: Daniel Biederman, Katerina Milenkovski 
* Aufgabe: Aufgabenblatt 4
* 
*/

package ws1718_a4.darstellung;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import ws1718_a4.assets.Assets;
import ws1718_a4.basis.Konstanten;
import ws1718_a4.basis.Level;
import ws1718_a4.basis.LevelIO;
import ws1718_a4.basis.SpielZustand;
import ws1718_a4.basis.Konstanten.Befehl;
import ws1718_a4.basis.Konstanten.SpielStatus;
import ws1718_a4.controller.Controller;

/**
 * Erzeug eine Gui Spielsteurungseinheit. Sie besteht jeweils einer Anordnung
 * von Kn�pfen und Textfeldern zur Interaktion.
 *
 * @author Daniel Biedermann, Katerina Milenkovski
 *
 */

public class SpielSteuerung {
	/**
	 * Kn�pfe zur Bedienung des Spiels.
	 */
	private Button spielNeuStarten, ok, l�schen;
	/**
	 * Textfeld zum Platzieren der Spielanweisung.
	 */
	private TextField spielanweisung;
	/**
	 * TextFelder mit m�glichen Befehlen bzw. Ausgabefeld der Mitteilung nach
	 * abgeschlossener Spielanweisung.
	 */
	private TextFlow befehle, ausgabe;
	/**
	 * �bergeordnete VBox.
	 */
	private VBox layoutPane;
	/**
	 * �bergeordnete HBox f�r Buttons.
	 */
	private HBox okL�schen;

	/**
	 * Konstruktor der Spielsteuerung.
	 * 
	 * @param controller
	 */
	public SpielSteuerung(Controller controller) {
		// Erstellen der �bergeordneten VBox
		layoutPane = new VBox(10);
		layoutPane.setPadding(new Insets(20));
		// Erstellen des AusgabeFelds
		ausgabe = new TextFlow();
		// Erstellen des "Spiel neu starten"-Buttons, Zuordnung der Funktion
		// beim Klick
		spielNeuStarten = new Button("Spiel neu Starten!");
		spielNeuStarten.setOnAction((ereignis) -> {
			Level level = LevelIO.levelLaden(Assets.class.getResourceAsStream(Spiel.leveldatei));
			SpielZustand.getInstance().setAktuellerLevel(level);
			SpielZustand.getInstance().setSpielStatus(SpielStatus.SPIELER_ZUG);
			spielanweisung.clear();
			ausgabe.getChildren().clear();
		});
		layoutPane.getChildren().add(spielNeuStarten);
		// Erstelen des Feldes f�r Befehle, F�llen des Feldes mit den Befehlen
		befehle = new TextFlow();
		befehle.setPrefWidth(200);
		for (Befehl befehl : Konstanten.Befehl.values()) {
			Label label = new Label(befehl.name());
			label.setTextFill(Color.BLACK);
			label.setStyle("-fx-background-color: rgba(231, 116, 113, 1);"); // Color
																				// code
																				// "Light
																				// Coral"
			befehle.getChildren().add(label);
			Label placeholder = new Label(" ");
			befehle.getChildren().add(placeholder);
			// Zuweisung der Drag-Action f�r jeden m�glichen Befehl
			label.setOnDragDetected(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					Dragboard db = label.startDragAndDrop(TransferMode.ANY);
					ClipboardContent content = new ClipboardContent();
					content.putString(label.getText());
					db.setContent(content);
					event.consume();
				}
			});
		}
		layoutPane.getChildren().add(befehle);
		// Textfeld f�r Spielanweisung und �berpr�fung auf richtige Quelle des
		// Objekts Initialisierung als M�glichkeit f�r
		// den Drop
		spielanweisung = new TextField();
		spielanweisung.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				if (event.getGestureSource() != befehle && event.getDragboard().hasString()) {
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
				event.consume();
			}
		});
		// Durchf�hren des Drop-Events und String-Ausgabe im Spielanweisungsfeld
		spielanweisung.setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean fertig = false;
				if (db.hasString()) {
					if (spielanweisung.getText().isEmpty()) {
						spielanweisung.setText(db.getString());
					} else {
						spielanweisung.setText(spielanweisung.getText() + " " + db.getString());
					}
					fertig = true;
				}
				event.setDropCompleted(fertig);
				event.consume();
			}
		});
		layoutPane.getChildren().add(spielanweisung);
		// Erstellen der HBox f�r die Buttons Ok und L�schen
		okL�schen = new HBox();
		okL�schen.setSpacing(10);
		ok = new Button("OK");
		// Ereignisverarbeitung beim Klicken des Ok-Buttons, Check auf
		// Richtigkeit des Ausdruck und ggf Ausgabe- und Alert-Field- Anzeige
		ok.setOnAction((ereignis) -> {
			ausgabe.getChildren().clear();
			String cmd = spielanweisung.getText();
			AnweisungsCheck zuchecken = new AnweisungsCheck(cmd);
			if (zuchecken.check()) {
				String mitteilung = controller.befehlVerarbeiten(cmd);
				Label mitteilungLabel = new Label(mitteilung);
				ausgabe.getChildren().add(mitteilungLabel);
				if (SpielZustand.getInstance().getSpielStatus() == SpielStatus.GEWONNEN) {
					this.benachrichtigung("Gewonnen", "Gluekwunsch sie haben Gewonnen");
				} else if (SpielZustand.getInstance().getSpielStatus() == SpielStatus.VERLOREN) {
					this.benachrichtigung("Verloren",
							"Leider verloren, vielleich hilft dir das hier https://www.123test.com/iq-test/");
				}
			} else {
				Label mitteilungLabel = new Label("Ung�ltiger Befehl");
				ausgabe.getChildren().add(mitteilungLabel);
			}
		});
		okL�schen.getChildren().add(ok);
		// Erstellen des L�schen-Buttons und Funktionalit�t
		l�schen = new Button("l�schen");
		l�schen.setOnAction((ereignis) -> {
			spielanweisung.clear();
		});
		okL�schen.getChildren().add(l�schen);
		layoutPane.getChildren().add(okL�schen);
		layoutPane.getChildren().add(ausgabe);
	}

	/**
	 * Getter des Layouts zur Verwendung im Spiel
	 * 
	 * @return VBox
	 */
	public VBox getLayoutPane() {
		return this.layoutPane;
	}

	/**
	 * Benachrichtigungs-Methode, die einen Alert schaltet
	 * 
	 * @param name
	 * @param text
	 */
	public void benachrichtigung(String name, String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(name);
		alert.setHeaderText(null);
		alert.setContentText(text);
		alert.showAndWait();
	}
}
