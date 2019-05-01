package jdbc;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleJDBC 
{

	private static final String user = "system";
	private static final String password = "password";
	private static final String ip = "localhost";
	private static final String context = "xe";
	private static final int port = 1521;
	private static Connection connection;

	
	public static boolean demarrer() throws Exception
	{
        try 
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) 
        {
            throw new Exception("Erreur lors du chargement du driver JDBC.");
        }
        
        connection = null;
        
        try 
        {
            connection = DriverManager.getConnection("jdbc:" + "oracle" + ":"+ "thin"+ ":@"+ ip+ ":"+ port+ ":"+ context, user, password);

        } catch (SQLException e) 
        {
        	throw new Exception("Erreur lors de la connexion à la base de données : " + e.toString());
        }
        
        if (connection != null) 
		    return true;
        else 
        	return false;
	}
	
	
    public static void stop() throws Exception
    {
		try 
		{
			connection.close();
		} catch (SQLException e) 
		{
			throw new Exception("Erreur lors de la fermeture de la base de données : " + e.toString());
		}
    }
    
    public static Connection getConnection() throws Exception
    {
        if (connection != null) 
        	return connection;
        else 
        	throw new Exception("La connexion n'est pas encore établie.");
    }
    
    public static Statement createStatement() throws Exception
    {
    	try 
    	{
			return connection.createStatement();
		} catch (SQLException e) 
    	{
			throw new Exception("Impossible de créer une nouvelle requête : " + e.toString());
		}
    }
    
}
