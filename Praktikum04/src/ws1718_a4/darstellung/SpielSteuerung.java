package ws1718_a4.darstellung;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import ws1718_a4.basis.SpielZustand;

public class SpielSteuerung implements Observer {
	
	private Button spielNeuStarten,ok,löschen;
	
	private TextField spielanweisung,ausgeschrieben;
	
	private TilePane befehle;
	
	
	public SpielSteuerung(){
		Pane steuerungsGrid = new GridPane();
		
		SpielZustand.getInstance().addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
