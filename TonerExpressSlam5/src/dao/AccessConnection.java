package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class AccessConnection {
	/*	Driver pour Access	*/
	private static final String pilote="sun.jdbc.odbc.JdbcOdbcDriver";
	private static final String url ="jdbc:odbc:";
	private static Connection cnt;
	
	public static Connection getConnection(String base, String util, String mdp)
	{
		cnt=null;
		try
		{
			Class.forName(pilote);
			cnt = DriverManager.getConnection(url+base); 
		
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,"Probl�me de connexion � la base "+e.getMessage());
		}
		return(cnt);
	}
	public static void close()
	{
		try
		{
			cnt.close();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,"Probl�me de d�connexion � la base "+e.getMessage());
		}
	}
}
