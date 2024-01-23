package application;

import java.io.IOException;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modele.Client;
import modele.Reservation;
import service.ReservationService;

public class ListeReservationController implements Initializable {
	
	@FXML
    private Label TitrePrincipale;
	
	@FXML
	private TableView<Reservation> tableListeReservation;
	
	@FXML
    private TableColumn<Reservation, String> colonneNumero;

    @FXML
    private TableColumn<Reservation, String> colonneNom;

    @FXML
    private TableColumn<Reservation, String> colonneNbPersonnes;

    @FXML
    private TableColumn<Reservation, String> colonneDate;

    @FXML
    private TableColumn<Reservation, String> colonneTable;
	
	@FXML
    private MenuItem btnSupprimer;
	
	@FXML
    private Button btnAccueil;
	
	@FXML
    private Button btnSupprimerReservation;

    @FXML
    private TextField inputReservationSelectionnee;
    
    @FXML
    private TextField inputRecherche;
    
    @FXML
    private DatePicker inputDateSelectionnee;
    
    private FilteredList<Reservation> recherche;
    private ObservableList<Reservation> data;
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    
    ReservationService reservationService = new ReservationService();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		inputDateSelectionnee.setValue(LocalDate.now()); // Date par défaut dans le champ de selection de date
				
		//////////////////// Récupération de toutes les réservations ////////////////////
		
		data = FXCollections.observableArrayList();
	    recherche = new FilteredList<>(data, p -> true);
	    
	    tableListeReservation.setItems(recherche);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		//////////////////// Lie les colonnes de la tableView aux données des réservations ////////////////////
		
		colonneNumero.setCellValueFactory(new PropertyValueFactory<Reservation, String>("Numero"));
		colonneNom.setCellValueFactory(new PropertyValueFactory<Reservation, String>("Nom"));
		colonneNbPersonnes.setCellValueFactory(cellData -> cellData.getValue().getNombreDePersonnes().asString());
		colonneDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().format(formatter)));
		colonneTable.setCellValueFactory(new PropertyValueFactory<Reservation, String>("Table"));

	    //////////////////// Méthode pour rechercher des données dans le tableau ////////////////////
	    
	    inputRecherche.textProperty().addListener((observable, oldValue, newValue) -> {
	    	recherche.setPredicate(reservation -> {
	            if (newValue == null || newValue.isEmpty()) {
	                return true;
	            }

	            String valeurRecherchee = newValue.toLowerCase();

	            if (reservation.getNumero().toLowerCase().contains(valeurRecherchee)) {
	                return true;
	            } else if (reservation.getNom().toLowerCase().contains(valeurRecherchee)) {
	                return true;
	            } else if (reservation.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toLowerCase().contains(valeurRecherchee)) {
	                return true;
	            } else if (String.valueOf(reservation.getNombreDePersonnes()).contains(valeurRecherchee)) {
	                return true;
	            } else if (reservation.getTable().toLowerCase().contains(valeurRecherchee)) {
	                return true;
	            }
	            return false;
	        });
	    });
	    
	    //////////////////// Récupère l'id de la réservation sélectionnée ////////////////////
	    
	    tableListeReservation.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	            Reservation reservationSelectionnee = tableListeReservation.getSelectionModel().getSelectedItem();
	            inputReservationSelectionnee.setText(reservationSelectionnee.getNumero());
	        }
	    });
	    
	    //////////////////// Récupère et mes à jour les donnes du tableau de réservation ////////////////////
	    getAllReservationByDate();
	}
	
	@FXML
	private void getAllReservationByDate() {

		LocalDate dateSelectionnee = inputDateSelectionnee.getValue();
	    ArrayList<Reservation> reservations = reservationService.selectAllReservationByDate(dateSelectionnee);

	    data.setAll(reservations);
	}
	
	@FXML
	private void onClickDeleteReserv(MouseEvent e) throws IOException
	{
		int idReservation = Integer.parseInt(inputReservationSelectionnee.getText());
		if(reservationService.deleteReservation(idReservation))
		{
	        alert.setHeaderText("Réservation " + idReservation + " supprimée !");
	
	        ButtonType buttonTypeValider = new ButtonType("Ok");
	
	        alert.getButtonTypes().setAll(buttonTypeValider);
	
	        Optional<ButtonType> choix = alert.showAndWait();
	        
	        getAllReservationByDate();
		}
	}

	public void onClickAccueil(MouseEvent e) throws IOException
	{
		Parent vueAccueil = FXMLLoader.load(getClass().getResource("../vue/Accueil.fxml"));
		Scene sceneAccueil = new Scene(vueAccueil);
		sceneAccueil.getStylesheets().add(getClass().getResource("../css/application.css").toExternalForm());
		
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(sceneAccueil);
		stage.show();
	}


}
