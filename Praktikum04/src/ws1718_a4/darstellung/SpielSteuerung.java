package ws1718_a4.darstellung;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextFlow;
import ws1718_a4.basis.SpielZustand;
import ws1718_a4.controller.Controller;

public class SpielSteuerung implements Observer {

	private Button spielNeuStarten = new Button ("Spiel neu Starten!");
	private Button ok = new Button ("Ok");
	private Button löschen = new Button ("Löschen");

	private	 TextField ausgeschrieben;

	private TextFlow spielanweisung, befehle;



	public SpielSteuerung() {
		AnchorPane layoutPane = new AnchorPane();
		layoutPane.setMinSize(200, 400);

		spielNeuStarten.setPrefSize(Button.USE_COMPUTED_SIZE, Button.USE_COMPUTED_SIZE);
		layoutPane.getChildren().add(spielNeuStarten);
		AnchorPane.setLeftAnchor(spielNeuStarten, 41.0);
		AnchorPane.setTopAnchor(spielNeuStarten, 37.0);
		

		befehle.setPrefSize(145, 235);
		layoutPane.getChildren().add(befehle);
		AnchorPane.setLeftAnchor(befehle, 15.0);
		AnchorPane.setTopAnchor(befehle, 81.0);

		getSpielanweisung().setPrefSize(62, 235);
		layoutPane.getChildren().add(getSpielanweisung());
		AnchorPane.setLeftAnchor(getSpielanweisung(), 15.0);
		AnchorPane.setTopAnchor(getSpielanweisung(), 251.0);

		ok.setPrefSize(Button.USE_COMPUTED_SIZE, Button.USE_COMPUTED_SIZE);
		layoutPane.getChildren().add(ok);
		AnchorPane.setLeftAnchor(ok, 19.0);
		AnchorPane.setTopAnchor(ok, 329.0);

		löschen.setPrefSize(Button.USE_COMPUTED_SIZE, Button.USE_COMPUTED_SIZE);
		layoutPane.getChildren().add(löschen);
		AnchorPane.setLeftAnchor(löschen, 66.0);
		AnchorPane.setTopAnchor(löschen, 329.0);

		getSpielanweisung().setPrefSize(68, 235);
		layoutPane.getChildren().add(getAusgeschrieben());
		AnchorPane.setLeftAnchor(getAusgeschrieben(), 17.0);
		AnchorPane.setTopAnchor(getAusgeschrieben(), 367.0);

		SpielZustand.getInstance().addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		cleartext();
	}

	public void cleartext() {
		getAusgeschrieben().clear();
		getSpielanweisung().getChildren().clear();
	}

	
	public void anweisungCheck(){
	 Controller c1 = new Controller(null);
	 

		
	}

	public TextFlow getSpielanweisung() {
		return spielanweisung;
	}

	public TextField getAusgeschrieben() {
		return ausgeschrieben;
	}

}
