package controllers;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import jdbc.OracleJDBC;
import main.Main;
import procedures.Procedures;

public class Controller_JDBC 
{
	public static void connexion_bdd() throws SQLException, Exception
	{
		try 
		{
			OracleJDBC.demarrer();
		} catch (Exception e) 
		{
			Main.afficherErreur(e.toString());
		}
		
		if(!OracleJDBC.getConnection().isClosed())
		{
			Controller_fenetre_principale.setBDD(true);
			creerProcedureAcheter();
			creerProcedureVendre();
		}
		else
			Controller_fenetre_principale.setBDD(false);
	}
	
	public static String acheter(int NumCpte, String Code, int Quant, double MA) throws SQLException, Exception
	{
		System.out.println("NumCpte : " + NumCpte + " Code : " + Code + " Quant : " + Quant + " MA : " + MA);
		
		
		Date utilDate = new Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate .getTime());
		
		String sql = "{call acheter(?,?,?,?,?)}";
		CallableStatement call = OracleJDBC.getConnection().prepareCall(sql); 
		call.setInt(1,NumCpte); // NumCpte 
		call.setString(2,Code); // Code
		call.setDate(3,sqlDate); // DateA
		call.setInt(4,Quant); // Quant
		call.setDouble(5,MA); // MA
		
		if(call.execute())
		{
		    //récupération des ResultSet 
		    ResultSet resultat1 = call.getResultSet(); 
		    call.getMoreResults(Statement.KEEP_CURRENT_RESULT); 
		    ResultSet resultat2 = call.getResultSet(); 
		    
		    //traitement des informations 
		    while(resultat1.next())
		    {
		        for(int i=0;i<resultat1.getMetaData().getColumnCount();i++)
		        { 
		            System.out.print(resultat1.getObject(i+1)+", "); 
		        } 
		        System.out.println(""); 
		    }
		    
		    resultat2.next(); 
		    System.out.println("Nombre de lignes = "+resultat2.getObject(1)); 
		    resultat1.close(); 
		    resultat2.close();
		}
		return "retour à faire";
	}
	
	public static String vendre()
	{
		return "";
	}
	
	public static String repartition()
	{
		return "";
	}

	
	
	/**
	 *  Retourne le résultat d'une requête passée en paramètre
	 * @param La requete
	 * @return Le resultat de la requete
	 */
	public static String requetePersonnalisee(String requete)
	{
		try 
		{
			Statement state = OracleJDBC.getConnection().createStatement();
			ResultSet set = state.executeQuery(requete);
			ResultSetMetaData rsmd = set.getMetaData();
			String resultat = "";
			
			while (set.next())
			{
				for (int j = 0; j < rsmd.getColumnCount(); j++) 
				{
					resultat += set.getString(j+1) + " | ";
				}
				resultat += "\n";
			}
			return resultat;

		} catch (Exception e) 
		{
			Main.afficherErreur("Erreur lors de la création de la requête : " + e.toString());
		}
		
		return null;
	}

	/**
	 * Créé la procédure Acheter dans la BDD
	 */
	public static void creerProcedureAcheter()
	{
		try 
		{
			Statement state = OracleJDBC.getConnection().createStatement();
			ResultSet set = state.executeQuery(Procedures.acheter);
			ResultSetMetaData rsmd = set.getMetaData();
			String resultat = "";
			
			while (set.next())
			{
				for (int j = 0; j < rsmd.getColumnCount(); j++) 
				{
					resultat += set.getString(j+1) + " | ";
				}
				resultat += "\n";
			}
			System.out.println(resultat); 

		} catch (Exception e) 
		{
			Main.afficherErreur("Erreur lors de la création de la procédure : " + e.toString());
		}
	}
	
	/**
	 * Créé la procédure Vendre dans la BDD
	 */
	public static void creerProcedureVendre()
	{
		try 
		{
			Statement state = OracleJDBC.getConnection().createStatement();
			ResultSet set = state.executeQuery(Procedures.vendre);
			ResultSetMetaData rsmd = set.getMetaData();
			String resultat = "";
			
			while (set.next())
			{
				for (int j = 0; j < rsmd.getColumnCount(); j++) 
				{
					resultat += set.getString(j+1) + " | ";
				}
				resultat += "\n";
			}
			System.out.println(resultat); 

		} catch (Exception e) 
		{
			Main.afficherErreur("Erreur lors de la création de la procédure : " + e.toString());
		}
	}
	
	
	
	
	
	
	
	
}
