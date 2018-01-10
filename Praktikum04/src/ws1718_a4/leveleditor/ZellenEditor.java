package ws1718_a4.leveleditor;

import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import ws1718_a4.assets.Assets.AssetTyp;
import ws1718_a4.basis.Asset;
import ws1718_a4.basis.Boesewicht;
import ws1718_a4.basis.SpielZustand;
import ws1718_a4.basis.Spielfigur;
import ws1718_a4.basis.SpielfigurNPC;
import ws1718_a4.basis.Zelle;
import ws1718_a4.basis.Konstanten.Richtung;

public class ZellenEditor extends GridPane implements Observer {

  private CheckBox cbWand0Uhr, cbWand2Uhr, cbWand4Uhr, cbWand6Uhr, cbWand8Uhr,
      cbWand10Uhr;
  private ComboBox<String> cbAssetTyp;
  private final SpielfeldEditorRenderer spielfeldEditorRenderer;

  public ZellenEditor(SpielfeldEditorRenderer spielfeldEditorRenderer) {
    this.spielfeldEditorRenderer = spielfeldEditorRenderer;

    SpielZustand.getInstance().addObserver(this);
    cbWand0Uhr = new CheckBox("0 Uhr");
    cbWand0Uhr.setOnAction(ereignis -> spielfeldEditorRenderer
        .setWandAktuelleZelle(Richtung.UHR_0, cbWand0Uhr.isSelected()));
    cbWand2Uhr = new CheckBox("2 Uhr");
    cbWand2Uhr.setOnAction(ereignis -> spielfeldEditorRenderer
        .setWandAktuelleZelle(Richtung.UHR_2, cbWand2Uhr.isSelected()));
    cbWand4Uhr = new CheckBox("4 Uhr");
    cbWand4Uhr.setOnAction(ereignis -> spielfeldEditorRenderer
        .setWandAktuelleZelle(Richtung.UHR_4, cbWand4Uhr.isSelected()));
    cbWand6Uhr = new CheckBox("6 Uhr");
    cbWand6Uhr.setOnAction(ereignis -> spielfeldEditorRenderer
        .setWandAktuelleZelle(Richtung.UHR_6, cbWand6Uhr.isSelected()));
    cbWand8Uhr = new CheckBox("8 Uhr");
    cbWand8Uhr.setOnAction(ereignis -> spielfeldEditorRenderer
        .setWandAktuelleZelle(Richtung.UHR_8, cbWand8Uhr.isSelected()));
    cbWand10Uhr = new CheckBox("10 Uhr");
    cbWand10Uhr.setOnAction(ereignis -> spielfeldEditorRenderer
        .setWandAktuelleZelle(Richtung.UHR_10, cbWand10Uhr.isSelected()));

    ObservableList<String> items = FXCollections.observableArrayList();
    items.add("kein Asset");
    for (AssetTyp typ : AssetTyp.values()) {
      items.add(typ.toString());
    }
    cbAssetTyp = new ComboBox<String>(items);
    cbAssetTyp.setOnAction(event -> assetAusgewaehlt());

    ZellenEditorRenderer renderer = new ZellenEditorRenderer();
    add(renderer, 1, 1, 2, 2);
    add(cbWand0Uhr, 1, 0, 2, 1);
    add(cbWand10Uhr, 0, 1);
    add(cbWand8Uhr, 0, 2);
    add(cbWand2Uhr, 3, 1);
    add(cbWand4Uhr, 3, 2);
    add(cbWand6Uhr, 1, 3, 2, 1);
    add(new Label("Asset:"), 0, 4);
    add(cbAssetTyp, 1, 4, 4, 1);
  }

  /**
   * Reaktion auf die Auswahl eines Assets mit der ComboBox
   */
  private void assetAusgewaehlt() {
    Zelle aktuelleZelle = SpielZustand.getInstance().getAktuelleZelle();
    if (aktuelleZelle == null) {
      return;
    }
    int index = cbAssetTyp.getSelectionModel().getSelectedIndex();
    if (index == 0) {
      aktuelleZelle.setAsset(null);
      return;
    }

    // Aufräumen was vorher in der Zelle stand
    Asset altesAsset = aktuelleZelle.getAsset();
    if (altesAsset != null) {
      switch (altesAsset.getTyp()) {
      case BOESEWICHT:
        SpielZustand.getInstance().getAktuellerLevel()
            .removeBoesewicht((Boesewicht) altesAsset);
        break;
      case SPIELER:
        SpielZustand.getInstance().getAktuellerLevel().getSpielfigur()
            .setZelle(null);
        break;
      default:
        System.out.println();
        break;
      }
    }

    // Neuen Asset setzen
    AssetTyp typ = AssetTyp.values()[index - 1];
    switch (typ) {
    case SPIELER:
      Spielfigur spielfigur = SpielZustand.getInstance().getAktuellerLevel()
          .getSpielfigur();
      if (spielfigur == null) {
        spielfigur = new Spielfigur();
      }
      spielfigur.setZelle(aktuelleZelle);
      break;
    case BOESEWICHT:
      Boesewicht boesewicht = new Boesewicht();
      boesewicht.setZelle(aktuelleZelle);
      SpielZustand.getInstance().getAktuellerLevel().addBoesewicht(boesewicht);
      break;
    case NPC:
      SpielfigurNPC npc = SpielZustand.getInstance().getAktuellerLevel()
          .getSpielfigurNPC();
      if (npc == null) {
        npc = new SpielfigurNPC();
      }
      npc.setZelle(aktuelleZelle);
      break;
    default:
      System.out.println("Typ " + typ.toString() + " nicht behandelt!");
    }
    spielfeldEditorRenderer.neuzeichnen();
  }

  @Override
  public void update(Observable o, Object arg) {
    updateCheckboxes();
  }

  /**
   * Aktualisiere alle Checkbox-Zustände.
   */
  private void updateCheckboxes() {
    Zelle aktuelleZelle = SpielZustand.getInstance().getAktuelleZelle();
    if (aktuelleZelle == null) {
      // TODO: Make inactive
      return;
    }
    cbWand0Uhr.setSelected(aktuelleZelle.istWand(Richtung.UHR_0));
    cbWand2Uhr.setSelected(aktuelleZelle.istWand(Richtung.UHR_2));
    cbWand4Uhr.setSelected(aktuelleZelle.istWand(Richtung.UHR_4));
    cbWand6Uhr.setSelected(aktuelleZelle.istWand(Richtung.UHR_6));
    cbWand8Uhr.setSelected(aktuelleZelle.istWand(Richtung.UHR_8));
    cbWand10Uhr.setSelected(aktuelleZelle.istWand(Richtung.UHR_10));

    if (aktuelleZelle.getAsset() == null) {
      cbAssetTyp.getSelectionModel().select(0);
    } else {
      cbAssetTyp.getSelectionModel()
          .select(aktuelleZelle.getAsset().getTyp().ordinal() + 1);
    }
  }

}
