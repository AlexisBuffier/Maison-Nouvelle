package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import modele.Reservation;
import service.ReservationService;

public class SalleController implements Initializable {

	@FXML
    private Rectangle t1;

    @FXML
    private Rectangle t2;
    
    @FXML
    private Rectangle t3;
    
    @FXML
    private Rectangle t4;

    @FXML
    private Rectangle t5;

    @FXML
    private Rectangle t7;

    @FXML
    private Rectangle t6;

    @FXML
    private DatePicker dtReservation;

    @FXML
    private Label TitrePrincipale;

    @FXML
    private Button btnAccueil;
    
    ReservationService reservationService = new ReservationService();
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
    	dtReservation.setValue(LocalDate.now()); // Date par défaut dans le champ de selection de date
		
    	updateSalle();
	}
    
    @FXML
    private void updateSalle() {
    	
    	resetSalle();
    	
    	LocalDate dateSelectionnee = dtReservation.getValue();
    	
    	ArrayList<Reservation> reservations = getAllReservationByDate(dateSelectionnee);
    	
    	for(Reservation reservation: reservations) {
    		
    		String numerosTable = reservation.getTable();
    		
    		String[] numeros = numerosTable.split(",");

    		for(String numero : numeros) {
    		    int tableNum = Integer.parseInt(numero.trim());

    		    updateTableStatus(tableNum);
    		}
    	}
    }
    
	private ArrayList<Reservation> getAllReservationByDate(LocalDate dateSelectionnee) {
		
	    ArrayList<Reservation> reservations = reservationService.selectAllReservationByDate(dateSelectionnee);

	    return reservations;
	}
	
	private void resetSalle() {
		t1.setFill(Color.web("#06e047"));
		t2.setFill(Color.web("#06e047"));
		t3.setFill(Color.web("#06e047"));
		t4.setFill(Color.web("#06e047"));
		t5.setFill(Color.web("#06e047"));
		t6.setFill(Color.web("#06e047"));
		t7.setFill(Color.web("#06e047"));
	}
	
	private void updateTableStatus(int tableNum) {
	    switch(tableNum) {
	        case 1:
	            t1.setFill(Color.RED);
	            break;
	        case 2:
	            t2.setFill(Color.RED);
	            break;
	        case 3:
	            t3.setFill(Color.RED);
	            break;
	        case 4:
	            t4.setFill(Color.RED);
	            break;
	        case 5:
	            t5.setFill(Color.RED);
	            break;
	        case 6:
	            t6.setFill(Color.RED);
	            break;
	        case 7:
	            t7.setFill(Color.RED);
	            break;
	    }
	}

	@FXML
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
