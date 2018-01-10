package ws1718_a4.leveleditor;

import java.io.File;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ws1718_a4.basis.Konstanten;
import ws1718_a4.basis.Level;
import ws1718_a4.basis.LevelGenerator;
import ws1718_a4.basis.LevelIO;
import ws1718_a4.basis.SpielZustand;

/**
 * Ein Editor zum Gestalten von Leveln.
 * 
 * @author Philipp Jenke
 */
public class LevelEditor extends Application {
  private SpielfeldEditorRenderer spielfeldEditor;

  @Override
  public void start(Stage buehne) throws Exception {

    BorderPane wurzel = new BorderPane();
    Scene szene = new Scene(wurzel, Konstanten.FENSTER_BREITE,
        Konstanten.FENSTER_HOEHE);
    buehne.setTitle("WS 17/18 - PM2 - Rette den Elf! - Level-Editor");
    buehne.setScene(szene);

    // Spiel setup
    SpielZustand.getInstance()
        .setRenderFlag(Konstanten.RENDER_FLAG_ALLE_ZELLEN_SICHTBAR);
    SpielZustand.getInstance()
        .setAktuellerLevel(LevelGenerator.generiereLevel(7, 8));

    // Editor-Logik und Spielfeld-Darstellung.
    spielfeldEditor = new SpielfeldEditorRenderer();

    // Editor für die aktuelle Zelle
    ZellenEditor zelleEditor = new ZellenEditor(spielfeldEditor);

    // GUI-Komponenten
    BorderPane ui = new BorderPane();
    ui.setCenter(spielfeldEditor);
    ui.setRight(zelleEditor);
    MenuBar menuBar = new MenuBar();
    Menu menu = new Menu("Datei");
    menuBar.getMenus().add(menu);

    MenuItem itemNeu = new MenuItem("Neu");
    itemNeu.setOnAction(ereignis -> neu());
    menu.getItems().add(itemNeu);

    MenuItem itemLaden = new MenuItem("Laden");
    itemLaden.setOnAction(ereignis -> laden());
    menu.getItems().add(itemLaden);

    MenuItem itemSpeichern = new MenuItem("Speichern");
    itemSpeichern.setOnAction(ereignis -> speichern());
    menu.getItems().add(itemSpeichern);

    wurzel.setTop(menuBar);
    wurzel.setCenter(ui);

    buehne.show();
  }

  /**
   * Erzeuge neuen Level
   */
  private void neu() {
    GridPane wurzel = new GridPane();
    wurzel.setPadding(new Insets(10));
    wurzel.setHgap(10);
    wurzel.setVgap(10);
    Stage stage = new Stage();
    stage.setTitle("Neuen Level erzeugen");
    stage.setScene(new Scene(wurzel, 140, 140));

    wurzel.add(new Label("Breite: "), 0, 0);
    wurzel.add(new Label("Hoehe: "), 0, 1);
    ObservableList<String> items = FXCollections.observableArrayList();
    for (int i = 2; i <= 10; i++) {
      items.add("" + i);
    }
    ComboBox<String> cbBreite = new ComboBox<String>(items);
    cbBreite.getSelectionModel().select(0);
    ComboBox<String> cbHoehe = new ComboBox<String>(items);
    cbHoehe.getSelectionModel().select(0);
    wurzel.add(cbBreite, 1, 0);
    wurzel.add(cbHoehe, 1, 1);
    Button buttonOk = new Button("Ok");
    wurzel.add(buttonOk, 0, 2);
    Button buttonCancel = new Button("Cancel");
    buttonOk.setOnAction(ereignis -> {
      SpielZustand.getInstance()
          .setAktuellerLevel(LevelGenerator.generiereLevel(
              Integer.valueOf(cbBreite.getSelectionModel().getSelectedItem())
                  .intValue(),
              Integer.valueOf(cbHoehe.getSelectionModel().getSelectedItem())
                  .intValue()));
      stage.hide();
      allesNeuZeichnen();
    });
    wurzel.add(buttonCancel, 1, 2);
    buttonCancel.setOnAction(ereignis -> stage.hide());

    stage.show();
  }

  /**
   * Zeigt einen Datei-Dialog und speichert an der ausgewählten Stelle den
   * Level.
   */
  private void speichern() {
    FileChooser dialog = new FileChooser();
    File datei = dialog.showSaveDialog(null);
    if (datei != null) {
      Level level = SpielZustand.getInstance().getAktuellerLevel();
      level.alleZellenUnsichtbar();
      LevelIO.levelSpeichern(level, datei.getAbsolutePath());
    }
  }

  /**
   * Zeigt einen Datei-Dialog und lädt von der ausgewählten Stelle den Level.
   */
  private void laden() {
    FileChooser dialog = new FileChooser();
    File datei = dialog.showOpenDialog(null);
    if (datei != null) {
      Level level = LevelIO.levelLaden(datei.getAbsolutePath());
      SpielZustand.getInstance().setAktuellerLevel(level);
      allesNeuZeichnen();
    }
  }

  private void allesNeuZeichnen() {
    spielfeldEditor.neuzeichnen();
  }

  public static void main(String[] args) {
    Application.launch(args);
  }

}
