package ws1718_a4.darstellung;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.TextFlow;
import ws1718_a4.controller.Controller;

public class SpielSteuerungEventHandler extends SpielSteuerung implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		Object ereignisverursacher = event.getSource();
		if (ereignisverursacher instanceof Button) {
			Button button = (Button) ereignisverursacher;
			switch (button.getText()) {
			case "Spiel Neu Starten":
				neuStart();
			case "Ok":
			
			case "Löschen":
			}

		} else if (ereignisverursacher instanceof TextFlow) {

		}

	}

	public void neuStart() {
		NeuesSpiel spiel = new NeuesSpiel();
	}

	public void anweisungsCheck(){
		Controller c1 = new Controller(null);
		getAusgeschrieben().setText(c1.befehlVerarbeiten(getSpielanweisung().getChildren().toString()));
	}
}
