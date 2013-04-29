package dao;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import metier.*;

public class DAO_TonerExpress 
{
	private static Connection cnt;

	public static Vector <Imprimante> getLesImprimantes()
	{
		Vector <Imprimante> lesImp;
		ResultSet resImp, resType, resCart;
		TypeImprimante leType;
		ArrayList <Cartouche> lesCartouches;
		Statement traitPrincipal, traitement;
		String texte, ref, desi;
		boolean encore;
		
		try
		{
			cnt = OracleConnection.getConnection("IG2","scott","tiger");		
			//cnt = MySqlConnection.getConnection("TonerExpress","root",""); 
			traitPrincipal = cnt.createStatement();	
			traitement = cnt.createStatement();
			resImp = traitPrincipal.executeQuery("select * from Imprimante");
			encore = resImp.next();
			while (encore)
			{
				ref = resImp.getString(1);	// m�morisation des donn�es avant la requ�te suivante  
				desi = resImp.getString(2);
				// Recherche du type
				leType=null;
				texte = "select * from TYPE where CodeType = '"+resImp.getString(3)+"'";
				//JOptionPane.showMessageDialog(null,texte);
				resType=traitement.executeQuery(texte);
				if (resType.next())
					leType=new TypeImprimante(resType.getString(1).charAt(0), resType.getString(2));
				
				// Recherche des cartouches
				lesCartouches=new ArrayList<Cartouche>();
				texte = "select CARTOUCHE.RefCartouche, DesCartouche, PrixCartouche from CARTOUCHE, ACCEPTER where CARTOUCHE.RefCartouche=ACCEPTER.RefCartouche and RefImprimante = '"+resImp.getString(1)+"'";
				//JOptionPane.showMessageDialog(null,texte);
				resCart=traitement.executeQuery(texte);
				while (resCart.next())
				{
					lesCartouches.add(new Cartouche(resCart.getString(1),resCart.getString(2),resCart.getDouble(3)));
				}
				
				// Cr�ation de l'imprimante avec les donn�es m�moris�es 
				lesImp.add(new Imprimante(resImp.getString(1),leType,desi,lesCartouches));
				encore = resImp.next();
			}	
			traitPrincipal.close();
			traitement.close();
		
			cnt.close();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,"Probl�me d'acc�s aux donn�es "+e.getMessage());
		}
		return lesImp;
	}
	public static void ajouterCartouche(Cartouche cart)
	{
		ResultSet resCart;	
		Statement traitement;
		String texte;
		int nb;
		try
		{
			
			cnt = OracleConnection.getConnection("IG2","scott","tiger"); 	
			traitement = cnt.createStatement();
			resCart = traitement.executeQuery("select * from Cartouche where RefCartouche = '"+cart.getRefCartouche()+"'");
			if (resCart.next())
				JOptionPane.showMessageDialog(null,"Cette cartouche existe d�j� !!!");
			else
			{
				texte = "Insert into Cartouche values('"+cart.getRefCartouche()+"','"+cart.getDesCartouche()+"','"+cart.getPrixCartoucheNeuve()+"')";
				nb=traitement.executeUpdate(texte);
				JOptionPane.showMessageDialog(null,nb+" cartouche ajout�e");
			}
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,"Probl�me de d'acc�s aux donn�es "+e.getMessage());
		}

	}
}
