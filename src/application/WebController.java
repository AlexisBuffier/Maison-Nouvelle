package application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modele.Table;
import service.ClientService;
import service.ParticulierService;
import service.ProfessionnelService;
import service.ReservationService;
import service.ReserverService;
import service.TableService;

public class WebController {

	@FXML
	private Button btnResaWeb;

	@FXML
	private Label TitrePrincipale;
	
	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	
    ParticulierService particulierService = new ParticulierService();
    ProfessionnelService professionnelService = new ProfessionnelService();
    TableService tableService = new TableService();
    ClientService clientService = new ClientService();
    ReservationService reservationService = new ReservationService();
    ReserverService reserverService = new ReserverService();

	@SuppressWarnings("unchecked")
	@FXML
	private void onClickReservationWeb(MouseEvent event) {
		try {
		    JSONParser parser = new JSONParser();
		    JSONObject json = (JSONObject) parser.parse(new FileReader("C:\\Users\\Eleve.TAR004287\\Documents\\cours-M1\\Java\\Workspace\\Restaurant\\src\\application\\reservations.json"));

		    json.keySet().forEach(key -> {
		        JSONObject reservation = (JSONObject) json.get(key);
		        int nbPersonne = ((Long) reservation.get("nbPersonne")).intValue();
		        String nomPersonne = (String) reservation.get("nom");
		        String nomSociete = ((String) reservation.get("nomSociete")).trim();
		        String dateResaString = (String) reservation.get("dateReservation");
		        LocalDate dateResa = LocalDate.parse(dateResaString);
		        String tel = (String) reservation.get("telephone");
		        String heureResa = (String) reservation.get("heureReservation");
		        String prenomP = (String) reservation.get("prenom");
		        
		        if(verifTableDispo(nbPersonne, dateResa)) {

		        	if(!nomSociete.isEmpty()) {
		        		
		        		verifProfessionnelExiste(tel, nomSociete);
		        		
		        		ajouterNouvelleReservationProfessionnel(tel, nomSociete, dateResa, nbPersonne, heureResa);
		        	}
		        	else {
		        		verifParticulierExiste(tel, nomPersonne, prenomP);
		        		
		        		ajouterNouvelleReservationParticulier(tel, nomPersonne, prenomP, dateResa, nbPersonne, heureResa);
		        	}
		        }
		    });
		    
		    alert.setHeaderText("Réservation ajoutée !");
			
	        ButtonType buttonTypeValider = new ButtonType("Ok");
	
	        alert.getButtonTypes().setAll(buttonTypeValider);
	
	        Optional<ButtonType> choix = alert.showAndWait();

		} catch (IOException e) {
		    e.printStackTrace();
		} catch (ParseException e) {
		    e.printStackTrace();
		}
	}
	
	private void ajouterNouvelleReservationParticulier(String telephone, String nom, String prenom, LocalDate dateReservation, int nbPersonne, String heureReservation ) {
		
		try {
	  		
			int idCli = particulierService.selectIdParticulierByName(nom, prenom);
			int nbTableNecessaire = getNbTableNecessaire(nbPersonne);
			
			if(reservationService.insertNouvelleReservation(idCli, dateReservation, nbPersonne, heureReservation)) {
				
				int idReservation = reservationService.selectDerniereReservation();			
				
				ArrayList<Table> tablesAReserver = tableService.selectTableAReserver(nbTableNecessaire, dateReservation);
				
				for (Table table : tablesAReserver) {
				    int idTable = table.getIdTable();
				    
				    reserverService.lieReservationTable(idReservation, idTable);
				}
			}
		} catch (NumberFormatException e) {
	        System.out.println("Erreur de format numérique : " + e.getMessage());
	    } catch (Exception e) {
	        System.out.println("Erreur : " + e.getMessage());
	    }
	}
	
	private void ajouterNouvelleReservationProfessionnel(String telephone, String nomSociete, LocalDate dateReservation, int nbPersonne, String heureReservation) {
	
		int idCli = professionnelService.selectIdProfessionnelByName(nomSociete);
		int nbTableNecessaire = getNbTableNecessaire(nbPersonne);
		
		if(reservationService.insertNouvelleReservation(idCli, dateReservation, nbPersonne, heureReservation)) {
			
			int idReservation = reservationService.selectDerniereReservation();			
			
			ArrayList<Table> tablesAReserver = tableService.selectTableAReserver(nbTableNecessaire, dateReservation);
			
			for (Table table : tablesAReserver) {
			    int idTable = table.getIdTable();
			    
			    reserverService.lieReservationTable(idReservation, idTable);
			}
		}
	}
	
	private Boolean verifTableDispo(int nbPersonne, LocalDate date) {
		try {
		    
		    if(nbPersonne > 0 && date != null) {
		    	
		    	int nbTableDispo = tableService.countTableDispoByDate(date);
		    	
		    	int nbTableNecessaire = getNbTableNecessaire(nbPersonne);

		        if (nbTableDispo >= nbTableNecessaire) {
		            return true;
		        } else {
		            return false;
		        }
		    }
		} catch (NumberFormatException e) {
		    System.out.println("Erreur : " + e);
		}
		return false;
    }
	
	private int getNbTableNecessaire(int nbPersonne) {
		
		return (nbPersonne + 1) / 2;
	}
	
	private void verifParticulierExiste(String telephone, String nom, String prenom) {
  		 
  		if(!particulierService.selectParticulierByName(nom, prenom)) {
  			if(particulierService.insertNouveauParticulier(nom, prenom, telephone)) {
  				 System.out.println("Particulier ajouté");
  			}
  		}
	}
	
	private void verifProfessionnelExiste(String telephone, String nomSociete) {
			
			if(!professionnelService.selectProfessionnelByName(nomSociete)) {
				if(professionnelService.insertNouveauProfessionnel(nomSociete, telephone)) {
   				 System.out.println("Professionnel ajouté");
   			 }
   		 }
	}

	@FXML
	public void onClickAccueil(MouseEvent e) throws IOException {
		Parent vueAccueil = FXMLLoader.load(getClass().getResource("../vue/Accueil.fxml"));
		Scene sceneAccueil = new Scene(vueAccueil);
		sceneAccueil.getStylesheets().add(getClass().getResource("../css/application.css").toExternalForm());

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(sceneAccueil);
		stage.show();
	}
}