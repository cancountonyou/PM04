package ws1718_a4.darstellung;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

public class SpielSteuerung {

	private Button spielNeuStarten, ok, löschen;

	private TextField ausgeschrieben, spielanweisung;

	private TextFlow befehle;
	
	private VBox layoutPane;
	
	private HBox okLöschen;

	public SpielSteuerung(Controller c1) {
		layoutPane = new VBox(10);
		layoutPane.setPadding(new Insets(20));
		

		spielNeuStarten = new Button("Spiel neu Starten!");
		spielNeuStarten.setOnAction((ereignis) -> {
			Level level = LevelIO.levelLaden(Assets.class.getResourceAsStream(Spiel.leveldatei));
			SpielZustand.getInstance().setAktuellerLevel(level);
			SpielZustand.getInstance().setSpielStatus(SpielStatus.SPIELER_ZUG);
			spielanweisung.clear();
			ausgeschrieben.clear();
		});
		layoutPane.getChildren().add(spielNeuStarten);
	

		befehle = new TextFlow();
		
		for (Befehl befehl : Konstanten.Befehl.values()) {
			Label label = new Label(befehl.name());
			label.setTextFill(Color.BLACK);
			label.setStyle("-fx-background-color: rgba(231, 116, 113, 1);"); // Color code "Light Coral"
			befehle.getChildren().add(label);
			Label placeholder = new Label(" ");
			befehle.getChildren().add(placeholder);

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
		

		spielanweisung = new TextField();
		spielanweisung.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				if (event.getGestureSource() != befehle && event.getDragboard().hasString()) {
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
				event.consume();
			}
		});

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
	
		
		okLöschen = new HBox();
		okLöschen.setSpacing(10);
		ok = new Button("OK");
		ok.setOnAction((ereignis) -> {
			String cmd = spielanweisung.getText();
			AnweisungsCheck zuchecken = new AnweisungsCheck(cmd);
			if (zuchecken.check()) {
				String mitteilung = c1.befehlVerarbeiten(cmd);
				ausgeschrieben.setText(mitteilung);
			} else {
				ausgeschrieben.setText("Ungültiger Befehl");
			}
		});
		
		okLöschen.getChildren().add(ok);
		

		löschen = new Button("löschen");
		löschen.setOnAction((ereignis) -> {
			spielanweisung.clear();
		});
		
		okLöschen.getChildren().add(löschen);
		
		layoutPane.getChildren().add(okLöschen);
	

		ausgeschrieben = new TextField();
		layoutPane.getChildren().add(ausgeschrieben);
		
	}
	
	public VBox getLayoutPane() {
		return this.layoutPane;
	}
}
