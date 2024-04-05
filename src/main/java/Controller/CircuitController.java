package Controller;

import Manager.CircuitManager;
import Manager.Entities.Circuit;
import Manager.Entities.Voiture;
import Manager.VoitureManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.util.List;

import static java.lang.Integer.parseInt;

public class CircuitController {
    @FXML
    private TableView<Circuit> TAB_Circuit;
    @FXML
    private TableColumn<Circuit, Integer> idCol, nbVoitureCol;
    @FXML
    private TableColumn<Circuit, String> descriptionCol;
    @FXML
    private TableColumn<Circuit, Boolean> disponibleCol;
    @FXML
    private Label TXT_ErreurInfo;
    @FXML
    private TextField SAI_Id;
    @FXML
    private TextField SAI_Description;
    @FXML
    private TextField SAI_nbVoiture;
    @FXML
    private CheckBox INT_Dispo;
    @FXML
    private Button BTN_Supp;
    @FXML
    private Button BTN_Save;
    @FXML
    private Button BTN_Add;


    public void setTableCircuit(){
        TableColumn<Circuit, Integer> idCol = (TableColumn<Circuit, Integer>) TAB_Circuit.getColumns().get(0);
        TableColumn<Circuit, String> descriptionCol = (TableColumn<Circuit, String>) TAB_Circuit.getColumns().get(1);
        TableColumn<Circuit, Boolean> disponibleCol = (TableColumn<Circuit, Boolean>) TAB_Circuit.getColumns().get(2);
        TableColumn<Circuit, Integer> nbVoitureCol = (TableColumn<Circuit, Integer>) TAB_Circuit.getColumns().get(3);

        idCol.setCellValueFactory(new PropertyValueFactory<>("CI_id"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("CI_description"));
        disponibleCol.setCellValueFactory(new PropertyValueFactory<>("CI_disponible"));
        nbVoitureCol.setCellValueFactory(new PropertyValueFactory<>("CI_nbVoituresMax"));
    }

    public void initialize() {
        listAllCircuit();
    }

    @FXML
    protected void listAllCircuit() {
        System.out.println("liste des circuit");
        CircuitManager circuitManager = new CircuitManager();
        List<Circuit> allCircuit = circuitManager.allCircuit();
        setTableCircuit();
        this.TAB_Circuit.getItems().clear();
        this.TAB_Circuit.setItems(FXCollections.observableList(allCircuit));
        onDoubleClickTableCI();
    }

    @FXML
    protected void onClickAddCI(){
        System.out.println("Click ajout voiture");
        String newDescription = this.SAI_Description.getText();
        Boolean newDisponible = this.INT_Dispo.isSelected();
        Integer newNBVoiture = Integer.parseInt(this.SAI_nbVoiture.getText());
        if (newNBVoiture.equals(0) || newDescription.isEmpty()) {
            this.TXT_ErreurInfo.setText("Veuillez commencer par remplir les différents champs");
            this.TXT_ErreurInfo.setDisable(false);
            return;
        }
        Circuit newCircuit = new Circuit(0, newDescription, newDisponible, newNBVoiture);
        CircuitManager circuitManager = new CircuitManager();
        circuitManager.addCircuit(newCircuit);
        this.TXT_ErreurInfo.setText("Voiture ajouter à notre base de données");
        List<Circuit> allCircuit = circuitManager.allCircuit();
        setTableCircuit();
        this.TAB_Circuit.getItems().clear();
        this.TAB_Circuit.getItems().addAll( allCircuit );
        this.SAI_Description.clear();
        this.INT_Dispo.equals(false);
        this.SAI_nbVoiture.clear();
    }

    protected void onDoubleClickTableCI(){
        this.TAB_Circuit.setRowFactory(tv -> {
            TableRow<Circuit> row = new TableRow<Circuit>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    System.out.println("Selection d'un circuit dans la table");
                    Circuit selectedCircuit = row.getItem();
                    this.SAI_Id.setText(String.valueOf(selectedCircuit.getCI_id()));
                    this.SAI_Description.setText(selectedCircuit.getCI_description());
                    this.INT_Dispo.setSelected(selectedCircuit.getCI_disponible());
                    this.SAI_nbVoiture.setText(String.valueOf(selectedCircuit.getCI_nbVoituresMax()));
                    this.BTN_Save.setDisable(false);
                    this.BTN_Supp.setDisable(false);
                }
            });
            return row;
        });
    }

    @FXML
    protected void onClickSupprimer(){
        System.out.println("Suppression de circuit");
        Integer selectedID = Integer.parseInt(this.SAI_Id.getText());
        String selectedDescription = this.SAI_Description.getText();
        Boolean selectedDispo = this.INT_Dispo.isSelected();
        Integer selectedNBVoiture = Integer.parseInt(this.SAI_nbVoiture.getText());
        Circuit circuitSelected = new Circuit(selectedID, selectedDescription, selectedDispo, selectedNBVoiture);
        CircuitManager circuitManager = new CircuitManager();
        circuitManager.deleteCircuit(circuitSelected);
        List<Circuit> allCircuit = circuitManager.allCircuit();
        setTableCircuit();
        this.TAB_Circuit.getItems().clear();
        this.TAB_Circuit.getItems().addAll( allCircuit);
        this.SAI_Id.clear();
        this.SAI_nbVoiture.clear();
        this.SAI_Description.clear();
        this.INT_Dispo.setSelected(false);
        this.TXT_ErreurInfo.setText("Suppression réussis");
        this.BTN_Supp.setDisable(true);
        this.BTN_Save.setDisable(true);
    }

    @FXML
    protected void onClickEnregistrer(){
        System.out.println("Modification de voiture");
        Integer selectedID = parseInt(this.SAI_Id.getText());
        Integer selectedNBVoiture = Integer.parseInt(this.SAI_nbVoiture.getText());
        String selectedDescription = this.SAI_Description.getText();
        Boolean selectedDispo = this.INT_Dispo.isSelected();
        if(selectedNBVoiture.equals(0) || selectedDescription.isEmpty()){
            this.TXT_ErreurInfo.setText("Veuillez commencer par remplir les différents champs");
            this.TXT_ErreurInfo.setVisible(true);
        }
        Circuit selectedCircuit = new Circuit( selectedID, selectedDescription, selectedDispo, selectedNBVoiture);
        CircuitManager circuitManager = new CircuitManager();
        circuitManager.updateCircuit(selectedCircuit);
        List<Circuit> allCircuit = circuitManager.allCircuit();
        setTableCircuit();
        this.TAB_Circuit.getItems().clear();
        this.TAB_Circuit.getItems().addAll( allCircuit);
        this.SAI_Id.clear();
        this.SAI_nbVoiture.clear();
        this.SAI_Description.clear();
        this.INT_Dispo.setSelected(false);
        this.TXT_ErreurInfo.setText("Modification de la voiture enregistré");
        this.TXT_ErreurInfo.setVisible(true);
        this.BTN_Supp.setDisable(true);
        this.BTN_Save.setDisable(true);
    }
}
