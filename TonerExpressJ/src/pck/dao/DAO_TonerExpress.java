package pck.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

import pck.metier.Cartouche;
import pck.metier.Imprimante;
import pck.metier.TypeImprimante;

public class DAO_TonerExpress 
{
	private static Connection cnt;
	
	public static Vector <TypeImprimante> getLesTypes()
	{
		Vector <TypeImprimante> lesTypes = new Vector <TypeImprimante>();
		Statement traitPrincipal;
		ResultSet resType;
		try
		{
			cnt = OracleConnection.getConnection("IG2","scott","tiger");
			//cnt = MySqlConnection.getConnection("TonerExpress","root","superchef"); 	// pour MySql
			traitPrincipal = cnt.createStatement();	
			resType = traitPrincipal.executeQuery("select * from Type");
			while (resType.next())
			{
				lesTypes.add(new TypeImprimante(resType.getString(1).charAt(0), resType.getString(2)));
			}
			resType.close();
			traitPrincipal.close();
			cnt.close();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,"Probl�me d'acc�s aux donn�es "+e.getMessage());
		}
		return lesTypes;
	}


	public static Vector <Imprimante> getLesImprimantes()
	{
		Vector <Imprimante> lesImp = new Vector <Imprimante>();
		ResultSet resImp, resType, resCart;
		TypeImprimante leType;
		ArrayList <Cartouche> lesCartouches;
		Statement traitPrincipal, traitement;
		String texte, ref, desi;
		boolean encore;
		
		try
		{
			cnt = OracleConnection.getConnection("IG2","scott","tiger");	// pour Oracle
			//cnt = MySqlConnection.getConnection("TonerExpress","root","superchef"); 	// pour MySql
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
				lesImp.add(new Imprimante(ref,leType,desi,lesCartouches));
				encore = resImp.next();
			}	
			traitPrincipal.close();
			traitement.close();
			//JOptionPane.showMessageDialog(null,"Cr�ation de la collection des imprimantes");
			cnt.close();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,"Probl�me d'acc�s aux donn�es "+e.getMessage());
		}
		return lesImp;
	}
	
	public static Vector <Cartouche> getLesCartouches()
	{
		Vector <Cartouche> lesCartouches = new Vector <Cartouche>();
		Statement traitPrincipal;
		ResultSet resCart;
		try
		{
			cnt = OracleConnection.getConnection("IG2","scott","tiger");
			//cnt = MySqlConnection.getConnection("TonerExpress","root","superchef"); 	// pour MySql
			traitPrincipal = cnt.createStatement();	
			resCart = traitPrincipal.executeQuery("select * from Cartouche");
			while (resCart.next())
			{
				lesCartouches.add(new Cartouche(resCart.getString(1), resCart.getString(2), resCart.getDouble(3)));
			}
			resCart.close();
			traitPrincipal.close();
			cnt.close();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,"Probl�me d'acc�s aux donn�es "+e.getMessage());
		}
		return lesCartouches;
	}
	
	public static int ajouterCartouche(Cartouche cart)
	{
		Statement traitement;
		String texte;
		int nb=0;
		try
		{
			cnt = OracleConnection.getConnection("IG2","scott","tiger");
			//cnt = MySqlConnection.getConnection("TonerExpress","root","superchef"); 	
			traitement = cnt.createStatement();
			texte = "Insert into Cartouche values('"+cart.getRefCartouche()+"','"+cart.getDesCartouche()+"',"+cart.getPrixCartoucheNeuve()+")";
			nb=traitement.executeUpdate(texte);
			JOptionPane.showMessageDialog(null,nb+" cartouche ajout�e");
			cnt.close();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,"Probl�me de d'acc�s aux donn�es "+e.getMessage());
		}
		return nb;
	}
	public static int ajouterImprimante(Imprimante imp)
	{
		Statement traitement;
		String texte;
		int nb=0, i, nbc, nbca;
		Cartouche cart;
		
		try
		{	// On tente d'ajouter l'imprimante dans la base
			cnt = OracleConnection.getConnection("IG2","scott","tiger");
			//cnt = MySqlConnection.getConnection("TonerExpress","root","superchef"); 	
			traitement = cnt.createStatement();
			texte = "Insert into Imprimante values('"+imp.getRefImprimante()+"','"+imp.getDesImprimante()+"','"+imp.getTypeImprimante().getCode()+"')";
			nb=traitement.executeUpdate(texte);
			nbc=imp.getNbCartouchesCompatibles();
			nbca=0;
			for (i=0; i<nbc; i++)
			{
				cart = imp.getLesCartouches().get(i);
				texte = "Insert into Accepter values('"+imp.getRefImprimante()+"','"+ cart.getRefCartouche() +"')";
				nbca+=traitement.executeUpdate(texte);
			}
			JOptionPane.showMessageDialog(null,nb+" imprimante ajout�e avec "+nbca+" cartouches");
			cnt.close();
		}
		catch (Exception e)
		{	// Message affich� si l'imprimante existe d�j�
			JOptionPane.showMessageDialog(null,"Probl�me d'acc�s aux donn�es : "+e.getMessage());
		}
		return nb;
	}	
	
	public static int supprimerCartouche(Cartouche cart)
	{
		Statement traitement;
		String texte;
		int nb=0;
		try
		{
			cnt = OracleConnection.getConnection("IG2","scott","tiger");
			//cnt = MySqlConnection.getConnection("TonerExpress","root","superchef"); 	
			traitement = cnt.createStatement();
			texte = "Delete from Accepter where RefCartouche='"+cart.getRefCartouche()+"'";
			nb=traitement.executeUpdate(texte);
			texte = "Delete from Cartouche where RefCartouche='"+cart.getRefCartouche()+"'";
			nb=traitement.executeUpdate(texte);
			JOptionPane.showMessageDialog(null,nb+" cartouche supprim�e");
			cnt.close();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,"Probl�me de d'acc�s aux donn�es "+e.getMessage());
		}
		return nb;
	}
}
