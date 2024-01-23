package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modele.Table;
import service.ClientService;
import service.ParticulierService;
import service.ProfessionnelService;
import service.ReservationService;
import service.ReserverService;
import service.TableService;

public class ReservationController implements Initializable {
	
	@FXML
    private Label TitrePrincipale;

    @FXML
    private Button btnAccueil;

    @FXML
    private RadioButton btnParticulier;

    @FXML
    private ToggleGroup typeReservation;

    @FXML
    private RadioButton btnProfessionnel;

    @FXML
    private TextField inputNom;

    @FXML
    private TextField inputPrenom;

    @FXML
    private Label labelNom;

    @FXML
    private Label labelPrenom;

    @FXML
    private TextField inputTelephone;

    @FXML
    private TextField inputNbPersonne;

    @FXML
    private Button btnAjouterReservation;

    @FXML
    private TextField inputNomSociete;

    @FXML
    private Label labelNomSociete;

    @FXML
    private DatePicker inputDate;
    
    @FXML
    private ComboBox<String> inputHeure;
    
    @FXML
    private Label labelTableDispo;
    
    ParticulierService particulierService = new ParticulierService();
    ProfessionnelService professionnelService = new ProfessionnelService();
    TableService tableService = new TableService();
    ClientService clientService = new ClientService();
    ReservationService reservationService = new ReservationService();
    ReserverService reserverService = new ReserverService();
    
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		for (int heure = 12; heure <= 13; heure++) {
            for (int minute = 0; minute < 60; minute += 10) {
                if (heure == 13 && minute > 30) {
                    break;
                }

                String time = String.format("%02dh%02d", heure, minute);

                inputHeure.getItems().add(time);
            }
        }
	}
	
	@FXML
	private void validerAjoutReservation() {
		if(verifTableDispo())
		{
	        alert.setTitle("Confirmation");
	        alert.setHeaderText("Ajouter la réservation ?");
	
	        ButtonType buttonTypeValider = new ButtonType("Valider");
	        ButtonType buttonTypeAnnuler = new ButtonType("Annuler");
	
	        alert.getButtonTypes().setAll(buttonTypeValider, buttonTypeAnnuler);
	
	        Optional<ButtonType> choix = alert.showAndWait();
	        
	        if (choix.isPresent() && choix.get() == buttonTypeValider) {
	       
		   		 if(btnParticulier.isSelected()) {
		   			 
		   			verifParticulierExiste();
		   			
		   			ajouterNouvelleReservationParticulier();
		   		 } 
		   		 else if(btnProfessionnel.isSelected()) {
		   			 
		   			verifProfessionnelExiste();
		   			
		   			ajouterNouvelleReservationProfessionnel();
		   		 }
	        } 
	        else {
	        	
	        }
		}
    }
	
	@FXML
	private void ajouterNouvelleReservationParticulier() {
		
		try {
			String telephone = inputTelephone.getText().trim();
			String nom = inputNom.getText().trim();
	  		String prenom = inputPrenom.getText().trim();
	  		LocalDate dateReservation = inputDate.getValue();
	  		int nbPersonne = Integer.parseInt(inputNbPersonne.getText());
	  		
			int idCli = particulierService.selectIdParticulierByName(nom, prenom);
			String heureReservation = inputHeure.getValue();
			int nbTableNecessaire = getNbTableNecessaire(nbPersonne);
			
			if(reservationService.insertNouvelleReservation(idCli, dateReservation, nbPersonne, heureReservation)) {
				
				int idReservation = reservationService.selectDerniereReservation();			
				
				ArrayList<Table> tablesAReserver = tableService.selectTableAReserver(nbTableNecessaire, dateReservation);
				
				for (Table table : tablesAReserver) {
				    int idTable = table.getIdTable();
				    
				    reserverService.lieReservationTable(idReservation, idTable);
				}
				
				alert.setHeaderText("Réservation ajoutée !");
				
		        ButtonType buttonTypeValider = new ButtonType("Ok");
		
		        alert.getButtonTypes().setAll(buttonTypeValider);
		
		        Optional<ButtonType> choix = alert.showAndWait();
			}
		} catch (NumberFormatException e) {
	        System.out.println("Erreur de format numérique : " + e.getMessage());
	    } catch (Exception e) {
	        System.out.println("Erreur : " + e.getMessage());
	    }
	}
	
	@FXML
	private void ajouterNouvelleReservationProfessionnel() {
		
		String telephone = inputTelephone.getText().trim();
		String nomSociete = inputNomSociete.getText().trim();
		LocalDate dateReservation = inputDate.getValue();
		int nbPersonne = Integer.parseInt(inputNbPersonne.getText());
		
		int idCli = professionnelService.selectIdProfessionnelByName(nomSociete);
		String heureReservation = inputHeure.getValue();
		int nbTableNecessaire = getNbTableNecessaire(nbPersonne);
		
		if(reservationService.insertNouvelleReservation(idCli, dateReservation, nbPersonne, heureReservation)) {
			
			int idReservation = reservationService.selectDerniereReservation();			
			
			ArrayList<Table> tablesAReserver = tableService.selectTableAReserver(nbTableNecessaire, dateReservation);
			
			for (Table table : tablesAReserver) {
			    int idTable = table.getIdTable();
			    
			    reserverService.lieReservationTable(idReservation, idTable);
			}
			
			alert.setHeaderText("Réservation ajoutée !");
			
	        ButtonType buttonTypeValider = new ButtonType("Ok");
	
	        alert.getButtonTypes().setAll(buttonTypeValider);
	
	        Optional<ButtonType> choix = alert.showAndWait();
		}
	}
	
	@FXML
	private void verifProfessionnelExiste() {
		
		String telephone = inputTelephone.getText().trim();
		String nomSociete = inputNomSociete.getText().trim();
			
			if(!professionnelService.selectProfessionnelByName(nomSociete)) {
				if(professionnelService.insertNouveauProfessionnel(nomSociete, telephone)) {
   				 System.out.println("Professionnel ajouté");
   			 }
   		 }
	}
	
	@FXML
	private void verifParticulierExiste() {
		
		String telephone = inputTelephone.getText().trim();
		String nom = inputNom.getText().trim();
  		String prenom = inputPrenom.getText().trim();
  		 
  		if(!particulierService.selectParticulierByName(nom, prenom)) {
  			if(particulierService.insertNouveauParticulier(nom, prenom, telephone)) {
  				 System.out.println("Particulier ajouté");
  			}
  		}
	}
	
	@FXML
	private int getNbTableNecessaire(int nombreDePersonnes) {
		
		return (nombreDePersonnes + 1) / 2;
	}
	
	
	@FXML
    private Boolean verifTableDispo() {
		try {
		    int nbPersonne = Integer.parseInt(inputNbPersonne.getText());
		    LocalDate date = inputDate.getValue();
		    
		    
		    if(nbPersonne > 0 && date != null) {
		    	
		    	int nbTableDispo = tableService.countTableDispoByDate(date);
		    	
		    	int nbTableNecessaire = getNbTableNecessaire(nbPersonne);

		        if (nbTableDispo >= nbTableNecessaire) {
		            afficheTableDispo(date, nbTableDispo);
		            return true;
		        } else {
		            labelTableDispo.setVisible(true);
		            labelTableDispo.setText("Aucune table disponible le " + date);
		            return false;
		        }
		    }
		} catch (NumberFormatException e) {
		    System.out.println("Erreur : " + e);
		}
		return false;
    }
	
	 @FXML
    private void afficheTableDispo(LocalDate date, int nbTable) {
		 
		labelTableDispo.setVisible(true);
		labelTableDispo.setText(nbTable + " tables disponible le " + date);
    }
	
	 @FXML
    private void onClickParticulier(MouseEvent event) {
		 changeTypeReservation("Particulier");
    }

    @FXML
    private void onClickProfessionnel(MouseEvent event) {
    	changeTypeReservation("Professionnel");
    }
    
    private void changeTypeReservation(String type)
    {
    	if(type == "Particulier")
    	{
    		labelNom.setVisible(true);
	   		inputNom.setVisible(true);
	   		 
	   		labelPrenom.setVisible(true);
	   		inputPrenom.setVisible(true);
	   		 
	   		labelNomSociete.setVisible(false);
	   		inputNomSociete.setVisible(false);
    	}
    	else if(type == "Professionnel")
    	{
    		labelNom.setVisible(false);
	   		inputNom.setVisible(false);
	   		 
	   		labelPrenom.setVisible(false);
	   		inputPrenom.setVisible(false);
	   		 
	   		labelNomSociete.setVisible(true);
	   		inputNomSociete.setVisible(true);
    	}
    }
    
    @FXML
    private void getTelephoneParticulierByName()
    {
    	String nom = inputNom.getText().trim();
    	String prenom = inputPrenom.getText().trim();
    	
    	if(nom != "" && prenom != "")
    	{
	    	String telephone = particulierService.selectTelephoneParticulierByName(nom, prenom);
	    	
	    	inputTelephone.setText(telephone);
    	}
    }
    
    @FXML
    private void getTelephoneProfessionnelByName()
    {
    	String nomSociete = inputNomSociete.getText().trim(); 
    	
    	if(nomSociete != "")
    	{
	    	String telephone = professionnelService.selectTelephoneProfessionnelByName(nomSociete);
	    	
	    	inputTelephone.setText(telephone);
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
