package Controller;

import Manager.AgendaManager;
import Manager.CircuitManager;
import Manager.Entities.Circuit;
import Manager.Entities.Conducteur;
import Manager.Entities.Creneau;
import Manager.Entities.Voiture;
import Manager.VoitureManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class AgendaController {

    @FXML
    private TableView<Voiture> TAB_Voiture;
    @FXML
    private TableColumn<Voiture, Integer> VO_idCol;
    @FXML
    private TableColumn<Voiture, String> modeleCol, VO_descriptionCol;
    @FXML
    private TableColumn<Voiture, Boolean> VO_disponibleCol;
    @FXML
    private TableView<Circuit> TAB_Circuit;
    @FXML
    private TableColumn<Circuit, Integer> CI_idCol, nbVoitureCol;
    @FXML
    private TableColumn<Circuit, String> CI_descriptionCol;
    @FXML
    private TableColumn<Circuit, Boolean> CI_disponibleCol;
    @FXML
    private DatePicker DATE_Reserv;
    @FXML
    private Button BTN_Afficher;
    @FXML
    private Label TXT_erreurInfo;
    @FXML
    private TextField SAI_Nom;
    @FXML
    private TextField SAI_Prenom;
    @FXML
    private DatePicker DATE_Naissance;
    @FXML
    private TextField SAI_Tel;
    @FXML
    private TextField SAI_Permis;
    @FXML
    private TableView<Creneau> TAB_Creneau;
    @FXML
    private TableColumn<Creneau, Integer> CR_idCol;
    @FXML
    private TableColumn<Creneau, String> CR_creneauCol;
    @FXML
    private Button BTN_Valider;
    @FXML
    private Label TXT_infoConduc;
    @FXML
    private Label TXT_Nom;
    @FXML
    private Label TXT_Prenom;
    @FXML
    private Label TXT_Naissance;
    @FXML
    private Label TXT_Tel;
    @FXML
    private Label TXT_Permis;
    @FXML
    private Label TXT_crDispo;
    private Integer selectedVOID;
    private Integer selectedCIID;
    private Integer selectedCRID;

    public void initialize() {
        listCircuitVoiture();
    }
    public void setTables(){
        TableColumn<Voiture, Integer> VO_idCol = (TableColumn<Voiture, Integer>) TAB_Voiture.getColumns().get(0);
        TableColumn<Voiture, String> modeleCol = (TableColumn<Voiture, String>) TAB_Voiture.getColumns().get(1);
        TableColumn<Voiture, String> VO_descriptionCol = (TableColumn<Voiture, String>) TAB_Voiture.getColumns().get(2);
        TableColumn<Voiture, Boolean> VO_disponibleCol = (TableColumn<Voiture, Boolean>) TAB_Voiture.getColumns().get(3);

        VO_idCol.setCellValueFactory(new PropertyValueFactory<>("VO_id"));
        modeleCol.setCellValueFactory(new PropertyValueFactory<>("VO_modele"));
        VO_descriptionCol.setCellValueFactory(new PropertyValueFactory<>("VO_description"));
        VO_disponibleCol.setCellValueFactory(new PropertyValueFactory<>("VO_disponible"));

        TableColumn<Circuit, Integer> CI_idCol = (TableColumn<Circuit, Integer>) TAB_Circuit.getColumns().get(0);
        TableColumn<Circuit, String> CI_descriptionCol = (TableColumn<Circuit, String>) TAB_Circuit.getColumns().get(1);
        TableColumn<Circuit, Boolean> CI_disponibleCol = (TableColumn<Circuit, Boolean>) TAB_Circuit.getColumns().get(2);
        TableColumn<Circuit, Integer> nbVoitureCol = (TableColumn<Circuit, Integer>) TAB_Circuit.getColumns().get(3);

        CI_idCol.setCellValueFactory(new PropertyValueFactory<>("CI_id"));
        CI_descriptionCol.setCellValueFactory(new PropertyValueFactory<>("CI_description"));
        CI_disponibleCol.setCellValueFactory(new PropertyValueFactory<>("CI_disponible"));
        nbVoitureCol.setCellValueFactory(new PropertyValueFactory<>("CI_nbVoituresMax"));

        TableColumn<Creneau, Integer> CR_idCol = (TableColumn<Creneau, Integer>) TAB_Creneau.getColumns().get(0);
        TableColumn<Creneau, String> CR_creneauCol = (TableColumn<Creneau, String>) TAB_Creneau.getColumns().get(1);

        CR_idCol.setCellValueFactory(new PropertyValueFactory<>("CR_id"));
        CR_creneauCol.setCellValueFactory(new PropertyValueFactory<>("CR_creneau"));
    }

    @FXML
    public void onSelecTable() {
        this.TAB_Circuit.getSelectionModel().selectedItemProperty().addListener((obs, oldCicruit, newCircuit) -> {
            if (newCircuit != null) {
                this.selectedCIID = newCircuit.getCI_id();
            }
        });
        this.TAB_Voiture.getSelectionModel().selectedItemProperty().addListener((obs, oldVoiture, newVoiture) -> {
            if (newVoiture != null) {
                this.selectedVOID = newVoiture.getVO_id();
            }
        });
        this.TAB_Creneau.getSelectionModel().selectedItemProperty().addListener((obs, oldCreneau, newCreneau) -> {
            if (newCreneau != null) {
                this.selectedCRID = newCreneau.getCR_id();
            }
        });
    }

    public void listCircuitVoiture(){
        System.out.println("liste des circuit et des voitures");
        CircuitManager circuitManager = new CircuitManager();
        List<Circuit> allCircuit = circuitManager.allCircuit();
        VoitureManager voitureManager = new VoitureManager();
        List<Voiture> allVoiture = voitureManager.allVoiture();
        setTables();
        this.TAB_Circuit.getItems().clear();
        this.TAB_Circuit.setItems(FXCollections.observableList(allCircuit));
        this.TAB_Voiture.getItems().clear();
        this.TAB_Voiture.setItems(FXCollections.observableList(allVoiture));
        onSelecTable();
    }

    @FXML
    public void onClickAfficher(){
        System.out.println("Click afficher");
        LocalDate dateReserv = this.DATE_Reserv.getValue();
        if(this.selectedVOID == null || this.selectedCIID == null || dateReserv == null){
            this.TXT_erreurInfo.setText("Veuillez commencer par selectionner les informations nécessaires");
            this.TXT_erreurInfo.setVisible(false);
            return;
        }else{
            AgendaManager agendaManager = new AgendaManager();
            List<Creneau> allCreneau = agendaManager.listCreneau(this.selectedCIID, this.selectedVOID, dateReserv);
            this.TAB_Creneau.getItems().clear();
            this.TAB_Creneau.getItems().addAll(allCreneau);
            showInfos();
        }
    }

    public void onClickValider(){
        String newPrenom = this.SAI_Prenom.getText();
        String newNom = this.SAI_Nom.getText();
        LocalDate newDateNaissance = this.DATE_Naissance.getValue();
        if(this.SAI_Permis.getLength() != 12 || this.SAI_Tel.getLength() != 10){
            this.TXT_erreurInfo.setText("Le numéro de téléphone ou de permis de conduire n'est pas valide veuillez réessayer");
            this.TXT_erreurInfo.setVisible(true);
        }else{
            Integer newTel = Integer.parseInt(this.SAI_Tel.getText());
            Long newPermis = Long.parseLong(this.SAI_Permis.getText());
            if(newPermis.equals(0) || newNom.isEmpty() || newPrenom.isEmpty() || newDateNaissance == null || newTel.equals(0)){
                this.TXT_erreurInfo.setText("Veuillez commencer par remplir les champs");
                this.TXT_erreurInfo.setVisible(true);
            }else {
                System.out.println("Click Valider");
                Conducteur newConducteur = new Conducteur(newNom, newPrenom, newDateNaissance, newTel, newPermis);
                AgendaManager agendaManager = new AgendaManager();
                Integer CO_id = agendaManager.verifConducteur(newConducteur);
                if(CO_id == 0){
                    this.TXT_erreurInfo.setText("Une erreur est survenue lors de l'enregistrement du conducteur");
                    this.TXT_erreurInfo.setVisible(true);
                }else {
                    LocalDate dateReserv = this.DATE_Reserv.getValue();
                    Boolean isCircuitDispo = agendaManager.isCircuitComplet(selectedCIID, selectedCRID, dateReserv);
                    if (isCircuitDispo == false) {
                        this.TXT_erreurInfo.setText("Le circuit sélectionné est complet pour le créneau choisi");
                        this.TXT_erreurInfo.setVisible(true);
                    } else {
                        agendaManager.addReserv(selectedVOID, selectedCIID, CO_id, selectedCRID, dateReserv);
                        this.TXT_erreurInfo.setText("Réservation errigistrée avec succès");
                        this.TXT_erreurInfo.setVisible(true);
                        hideInfo();
                    }
                }
            }
        }
    }

    public void hideInfo(){
        this.SAI_Nom.clear();
        this.SAI_Prenom.clear();
        this.SAI_Permis.clear();
        this.SAI_Tel.clear();
        this.DATE_Naissance.setValue(null);
        this.TXT_infoConduc.setVisible(false);
        this.SAI_Nom.setVisible(false);
        this.TXT_Nom.setVisible(false);
        this.SAI_Prenom.setVisible(false);
        this.TXT_Prenom.setVisible(false);
        this.DATE_Naissance.setVisible(false);
        this.TXT_Naissance.setVisible(false);
        this.SAI_Tel.setVisible(false);
        this.TXT_Tel.setVisible(false);
        this.SAI_Permis.setVisible(false);
        this.TXT_Permis.setVisible(false);
        this.TAB_Creneau.setVisible(false);
        this.TXT_crDispo.setVisible(false);
        this.BTN_Valider.setVisible(false);

    }
    public void showInfos(){
        this.TXT_infoConduc.setVisible(true);
        this.SAI_Nom.setVisible(true);
        this.TXT_Nom.setVisible(true);
        this.SAI_Prenom.setVisible(true);
        this.TXT_Prenom.setVisible(true);
        this.DATE_Naissance.setVisible(true);
        this.TXT_Naissance.setVisible(true);
        this.SAI_Tel.setVisible(true);
        this.TXT_Tel.setVisible(true);
        this.SAI_Permis.setVisible(true);
        this.TXT_Permis.setVisible(true);
        this.TAB_Creneau.setVisible(true);
        this.TXT_crDispo.setVisible(true);
        this.BTN_Valider.setVisible(true);
    }
}
