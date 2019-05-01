package main;

import controllers.Controller_JDBC;
import controllers.Controller_fenetre_principale;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	public static Stage primaryStage;

	public static void main(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		try
			{
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vues/fenetre_principale.fxml"));
			final VBox vbox = fxmlLoader.load();
			Main.primaryStage = primaryStage;
			Scene scene = new Scene(vbox);
			Main.primaryStage.setScene(scene);
			Main.primaryStage.setResizable(false);
			Main.primaryStage.show();
			Controller_fenetre_principale.initialiser();
			Controller_JDBC.connexion_bdd();
		} catch(Exception e)
		{
			System.out.print("Impossible d'ouvrir la fenêtre! Erreur: " + e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * Affiche une popup d'erreur avec un message personnalisé
	 * @param Le message à afficher
	 */
	public static void afficherErreur(String err)
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("");
		alert.setHeaderText("Erreur");
		alert.setContentText(err);
		alert.showAndWait();
	}
	
	/**
	 * Cherche un élément dans la fenêtre principale
	 * @param l'élément à rechercher
	 * @return l'élément (à caster car en Object)
	 */
	public static Object getElementById(String element)
	{
		return Main.primaryStage.getScene().lookup("#" + element);
		
	}
}
