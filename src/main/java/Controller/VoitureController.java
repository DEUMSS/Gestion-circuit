package Controller;

import Manager.Entities.Voiture;
import Manager.VoitureManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static java.lang.Integer.parseInt;

public class VoitureController {
    @FXML
    private TableView<Voiture> TAB_Voitures;
    @FXML
    private TableColumn<Voiture, Integer> idCol;
    @FXML
    private TableColumn<Voiture, String> modeleCol, descriptionCol;
    @FXML
    private TableColumn<Voiture, Boolean> disponibleCol;
    @FXML
    private TextField SAI_Modele;
    @FXML
    private TextField SAI_Description;
    @FXML
    private CheckBox INT_Disponible;
    @FXML
    private Label TXT_ErreurInfo;
    @FXML
    private Button BTN_SaveModif;
    @FXML
    private Button BTN_Supp;
    @FXML
    private TextField SAI_Id;


    private void setTableVoiture(){
        TableColumn<Voiture, Integer> idCol = (TableColumn<Voiture, Integer>) TAB_Voitures.getColumns().get(0);
        TableColumn<Voiture, String> modeleCol = (TableColumn<Voiture, String>) TAB_Voitures.getColumns().get(1);
        TableColumn<Voiture, String> descriptionCol = (TableColumn<Voiture, String>) TAB_Voitures.getColumns().get(2);
        TableColumn<Voiture, Boolean> disponibleCol = (TableColumn<Voiture, Boolean>) TAB_Voitures.getColumns().get(3);

        idCol.setCellValueFactory(new PropertyValueFactory<>("VO_id"));
        modeleCol.setCellValueFactory(new PropertyValueFactory<>("VO_modele"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("VO_description"));
        disponibleCol.setCellValueFactory(new PropertyValueFactory<>("VO_disponible"));
    }

    public void initialize(){
        listAllVoiture();
    }

    @FXML
    protected void listAllVoiture() {
        System.out.println("liste des voitures");
        VoitureManager voitureManager = new VoitureManager();
        List<Voiture> allVoiture = voitureManager.allVoiture();
        setTableVoiture();
        this.TAB_Voitures.getItems().clear();
        this.TAB_Voitures.setItems(FXCollections.observableList(allVoiture));
        onDoubleClickTableVO();
    }
    @FXML
    protected void onClickAddVo(){
        System.out.println("Click ajout voiture");
        String newModele = this.SAI_Modele.getText();
        String newDescription = this.SAI_Description.getText();
        Boolean newDisponible = this.INT_Disponible.isSelected();
        if (newModele.isEmpty() || newDescription.isEmpty()) {
            this.TXT_ErreurInfo.setText("Veuillez commencer par remplir les différents champs");
            this.TXT_ErreurInfo.setDisable(false);
            return;
        }
        Voiture newVoiture = new Voiture(0, newModele, newDescription, newDisponible);
        VoitureManager voitureManager = new VoitureManager();
        voitureManager.addVoiture(newVoiture);
        this.TXT_ErreurInfo.setText("Voiture ajouter à notre base de données");
        List<Voiture> allVoiture = voitureManager.allVoiture();
        setTableVoiture();
        this.TAB_Voitures.getItems().clear();
        this.TAB_Voitures.getItems().addAll( allVoiture);
        this.SAI_Modele.clear();
        this.SAI_Description.clear();
        this.INT_Disponible.equals(false);
    }

    protected void onDoubleClickTableVO(){
        this.TAB_Voitures.setRowFactory(tv -> {
            TableRow<Voiture> row = new TableRow<Voiture>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    System.out.println("Selection d'une voiture dans la table");
                    Voiture selectedVoiture = row.getItem();
                    this.SAI_Id.setText(String.valueOf(selectedVoiture.getVO_id()));
                    this.SAI_Modele.setText(selectedVoiture.getVO_modele());
                    this.SAI_Description.setText(selectedVoiture.getVO_description());
                    this.INT_Disponible.setSelected(selectedVoiture.getVO_disponible());
                    this.BTN_SaveModif.setDisable(false);
                    this.BTN_Supp.setDisable(false);
                }
            });
            return row;
        });
    }
    @FXML
    protected void onClickSupprimer(){
        System.out.println("Suppression de voiture");
        Integer selectedID = Integer.parseInt(this.SAI_Id.getText());
        String selectedModele = this.SAI_Modele.getText();
        String selectedDescription = this.SAI_Description.getText();
        Boolean selectedDispo = this.INT_Disponible.isSelected();
        Voiture voitureSelected = new Voiture(selectedID, selectedModele, selectedDescription, selectedDispo);
        VoitureManager voitureManager = new VoitureManager();
        voitureManager.deleteVoiture(voitureSelected);
        List<Voiture> allVoiture = voitureManager.allVoiture();
        setTableVoiture();
        this.TAB_Voitures.getItems().clear();
        this.TAB_Voitures.getItems().addAll( allVoiture);
        this.SAI_Id.clear();
        this.SAI_Modele.clear();
        this.SAI_Description.clear();
        this.INT_Disponible.setSelected(false);
        this.TXT_ErreurInfo.setText("Suppression réussis");
        this.BTN_Supp.setDisable(true);
        this.BTN_SaveModif.setDisable(true);
    }
    @FXML
    protected void onClickEnregistrer(){
        System.out.println("Modification de voiture");
        Integer selectedID = parseInt(this.SAI_Id.getText());
        String selectedModele = this.SAI_Modele.getText();
        String selectedDescription = this.SAI_Description.getText();
        Boolean selectedDispo = this.INT_Disponible.isSelected();
        if(selectedModele.isEmpty() || selectedDescription.isEmpty()){
            this.TXT_ErreurInfo.setText("Veuillez commencer par remplir les différents champs");
            this.TXT_ErreurInfo.setVisible(true);
        }
        Voiture selectedVoiture = new Voiture( selectedID, selectedModele, selectedDescription, selectedDispo);
        VoitureManager voitureManager = new VoitureManager();
        voitureManager.updateVoiture(selectedVoiture);
        List<Voiture> allVoiture = voitureManager.allVoiture();
        setTableVoiture();
        this.TAB_Voitures.getItems().clear();
        this.TAB_Voitures.getItems().addAll( allVoiture);
        this.SAI_Id.clear();
        this.SAI_Modele.clear();
        this.SAI_Description.clear();
        this.INT_Disponible.setSelected(false);
        this.TXT_ErreurInfo.setText("Modification de la voiture enregistré");
        this.TXT_ErreurInfo.setVisible(true);
        this.BTN_Supp.setDisable(true);
        this.BTN_SaveModif.setDisable(true);
    }

}