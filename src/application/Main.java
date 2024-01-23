package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent rootFXML = FXMLLoader.load(getClass().getResource("../vue/Accueil.fxml"));
			Scene sceneFXML = new Scene(rootFXML);
			
			sceneFXML.getStylesheets().add(getClass().getResource("../css/application.css").toExternalForm());
			
			primaryStage.getIcons().add(new Image("image/Logo-Maison-Nouvelle.png"));
			primaryStage.setTitle("Maison Nouvelle");
			
			primaryStage.setResizable(false);
			
			primaryStage.setScene(sceneFXML);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
