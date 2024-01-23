package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AccueilController {

    @FXML
    private Button btnRerservationWeb;

    @FXML
    private Button btnReservation;

    @FXML
    private Button btnSalle;

    @FXML
    private Button btnListeReservation;

    @FXML
    void onClickListeReservation(MouseEvent e) throws IOException {
    	Parent vueSalle = FXMLLoader.load(getClass().getResource("../vue/ListeReservation.fxml"));
		Scene sceneSalle = new Scene(vueSalle);
		sceneSalle.getStylesheets().add(getClass().getResource("../css/application.css").toExternalForm());
		
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(sceneSalle);
		stage.show();
    }

    @FXML
    void onClickReservation(MouseEvent e) throws IOException {
    	Parent vueReservation = FXMLLoader.load(getClass().getResource("../vue/Reservation.fxml"));
		Scene sceneReservation = new Scene(vueReservation);
		sceneReservation.getStylesheets().add(getClass().getResource("../css/application.css").toExternalForm());
		
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(sceneReservation);
		stage.show();
    }

    @FXML
    void onClickReservationWeb(MouseEvent e) throws IOException {

    	Parent vueReservationWeb = FXMLLoader.load(getClass().getResource("../vue/ReservationWeb.fxml"));
		Scene sceneReservationWeb = new Scene(vueReservationWeb);
		sceneReservationWeb.getStylesheets().add(getClass().getResource("../css/application.css").toExternalForm());
		
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(sceneReservationWeb);
		stage.show();
    }

    @FXML
    void onClickSalle(MouseEvent e) throws IOException {
    	Parent vueSalle = FXMLLoader.load(getClass().getResource("../vue/Salle.fxml"));
		Scene sceneSalle = new Scene(vueSalle);
		sceneSalle.getStylesheets().add(getClass().getResource("../css/application.css").toExternalForm());
		
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(sceneSalle);
		stage.show();
    }

}
