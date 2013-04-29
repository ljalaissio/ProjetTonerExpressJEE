package pck.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class OracleConnection {
	/*	Driver pour Oracle	*/
	private static final String pilote="oracle.jdbc.driver.OracleDriver";
	private static final String url ="jdbc:oracle:thin:@172.20.64.253:1521:";
	private static Connection cnt;
	
	public static Connection getConnection(String base, String util, String mdp)
	{
		cnt=null;
		try
		{
			Class.forName(pilote);
			cnt = DriverManager.getConnection(url+base,util,mdp); 
		
		
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,"Probl�me de connexion � la base "+e.getMessage());
		}
		return(cnt);
	}

}
