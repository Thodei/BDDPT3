package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import jdbc.OracleJDBC;
import main.Main;

public class Controller_fenetre_principale 
{
	@FXML private static TextField text_compte;
	@FXML private static TextField text_valeur;
	@FXML private static TextField text_montant;
	@FXML private static TextField text_requete;
	@FXML private static Spinner<Integer> spin_quantite;
	@FXML private static Button button_quitter;
	@FXML private static Button button_acheter;
	@FXML private static Button button_vendre;
	@FXML private static Button button_repartition;
	@FXML private static Button button_run;
	@FXML private static RadioButton radio_secteur;
	@FXML private static RadioButton radio_indice;
	@FXML private static RadioButton radio_deux;
	@FXML private static TextArea text_resultat;
	@FXML private static Label label_bdd;
	
	final static ToggleGroup group = new ToggleGroup();
	
	/**
	 * Associe tous les composants de la fen�tre � des variables ainsi que des listeners
	 */
	public static void initialiser()
	{
		//TextField text_compte
		text_compte = (TextField) Main.getElementById("text_compte");
		
		//TextField text_valeur
		text_valeur = (TextField) Main.getElementById("text_valeur");

		//TextField text_montant
		text_montant = (TextField) Main.getElementById("text_montant");
		
		//TextField text_requete
		text_requete = (TextField) Main.getElementById("text_requete");
		
		//Spinner spin_quantite
		spin_quantite = (Spinner<Integer>) Main.getElementById("spin_quantite");
		spin_quantite.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9));	
		
		//Button button_quitter
		button_quitter = (Button) Main.getElementById("button_quitter");
		button_quitter.setOnAction(e -> quitter());
		
		//Button button_acheter
		button_acheter = (Button) Main.getElementById("button_acheter");
		button_acheter.setOnAction(e -> acheter());
		
		//Button button_vendre
		button_vendre = (Button) Main.getElementById("button_vendre");
		button_vendre.setOnAction(e -> vendre());
		
		//Button button_repartition
		button_repartition = (Button) Main.getElementById("button_repartition");
		button_repartition.setOnAction(e -> repartition());
		
		//Button button_run
		button_run = (Button) Main.getElementById("button_run");
		button_run.setOnAction(e -> requete_personnalisee());

        //RadioButton radio_secteur
        radio_secteur = (RadioButton) Main.getElementById("radio_secteur");
        radio_secteur.setToggleGroup(group);
        radio_secteur.setSelected(true);
        
        //RadioButton radio_indice
        radio_indice = (RadioButton) Main.getElementById("radio_indice");
        radio_indice.setToggleGroup(group);
        
        //RadioButton radio_deux
        radio_deux = (RadioButton) Main.getElementById("radio_deux");
        radio_deux.setToggleGroup(group);
        
        //TextArea text_resultat
        text_resultat = (TextArea) Main.getElementById("text_resultat");
        
        //Label label_bdd
        label_bdd = (Label) Main.getElementById("label_bdd");
	}    
	
	
	/**
	 * Affiche les param�tres de la proc�dure (Compte, Valeur, Quantit�)
	 * Appelle la proc�dure "Acheter" et affiche son r�sultat
	 */
	private static void acheter()
	{
		if(verifierChampsAV())
		{
			String message;
			message = "Proc�dure : Acheter \n";
			message += "Compte : " 		+ 	getCompte() 	+ "\n";
			message += "Valeur : "		+	getValeur() 	+ "\n";
			message += "Montant : "		+	getMontant() 	+ "\n";
			message += "Quantit� : " 	+ 	getQuantite() 	+ "\n"; 
			message += "####################\n";
			
			try 
			{
				Controller_JDBC.acheter(Integer.parseInt(getCompte()), getValeur(), Integer.parseInt(getQuantite()), Double.parseDouble(getMontant()));
			} catch (Exception e) 
			{
				Main.afficherErreur("Erreur lors de l'execution de la requ�te : " + e.toString());
				e.printStackTrace();
			}
			
			afficher(message);
		}
	}
	
	/**
	 * Affiche les param�tres de la proc�dure (Compte, Valeur, Quantit�)
	 * Appelle la proc�dure "Vendre" et affiche son r�sultat
	 */
	private static void vendre()
	{	
		if(verifierChampsAV())
		{
			String message;
			message = "Proc�dure : Vendre \n";
			message += "Compte : " 		+ 	getCompte() 	+ "\n";
			message += "Valeur : "		+	getValeur() 	+ "\n";
			message += "Montant : "		+	getMontant() 	+ "\n";
			message += "Quantit� : " 	+ 	getQuantite() 	+ "\n"; 
			message += "####################\n";
			
			afficher(message);
		}
	}
	
	/**
	 * V�rifie que les champs "Compte" et "Valeur" ne sont pas vides
	 * @return Retourne "true" si les champs sont corrects, sinon "false" 
	 */
	private static boolean verifierChampsAV()
	{
		String valeur = getValeur();
		String compte = getCompte();
		String montant = getMontant();
		
		if(valeur.isEmpty() || compte.isEmpty() || montant.isEmpty())
		{
			Main.afficherErreur("Veuillez entrer un compte, une valeur ainsi qu'un montant.");
			return false;
		}
		else
			return true;
	}	
	
	/**
	 * V�rifie si le champ "Compte" n'est pas vide
	 * Affiche les param�tres de la proc�dure (Compte, Crit�re)
	 * Appelle la proc�dure "RepartitionPortefeuille" et affiche son r�sultat
	 */
	private static void repartition()
	{
		if(getCompte().isEmpty())
		{
			Main.afficherErreur("Veuillez entrer un compte.");
			return;
		}
		
		String message;
		message = "Proc�dure : R�partition portefeuille \n";
		message += "Compte : " + getCompte() + "\n";
		message += "Crit�re(s) : " + getCritere() + " \n";
		message += "####################\n";
		
		afficher(message);
	}
	
	private static void requete_personnalisee()
	{
		String requete = text_requete.getText();
		String resultat = requete + "\n ########## \n";
		resultat += Controller_JDBC.requetePersonnalisee(requete);

		afficher(resultat);
	}

	
	/**
	 * Affiche un message dans la TextArea
	 * @param Le message � afficher
	 */
	private static void afficher(String message)
	{
		text_resultat.setText(message);
	}

	/**
	 * Ferme l'application
	 */
	private static void quitter()
	{
    	try 
    	{
			OracleJDBC.getConnection().close();
			setBDD(false);
		} catch (Exception e) 
    	{
			Main.afficherErreur(e.toString());
		}
    	
    	Main.primaryStage.close();
	}
	

	
	/**
	 * @return Le RadioButton selectionn�
	 */
	private static String getCritere()
	{
		RadioButton button = (RadioButton) group.getSelectedToggle();
		return button.getText();
	}
	
	/**
	 * Change le statut du label d'info de connexion � la BDD
	 * @param "true" si la connexion est �tablie, sinon false
	 */
	public static void setBDD(boolean b) 
	{
		if(b)
		{
			label_bdd.setText("Connect�");
			label_bdd.setStyle("-fx-background-color:green");
		}
		else
		{
			label_bdd.setText("D�connect�");
			label_bdd.setStyle("-fx-background-color:rgb(255,50,50)");
		}
	}
	
	/**
	 * @return Le chiffre selectionn� dans le spinner
	 */
	private static String getQuantite()
	{
		return spin_quantite.getValue().toString();
	}
	
	private static String getValeur()
	{
		return text_valeur.getText();
	}
	
	private static String getCompte()
	{
		return text_compte.getText();
	}
	
	private static String getMontant()
	{
		return text_montant.getText();
	}
	

}
