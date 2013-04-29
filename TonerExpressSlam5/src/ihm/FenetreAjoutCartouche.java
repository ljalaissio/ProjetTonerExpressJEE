package ihm;

import java.awt.Container;
import java.awt.event.*;
import javax.swing.*;

import dao.DAO_TonerExpress;

import metier.*;

public class FenetreAjoutCartouche extends JDialog implements ActionListener
{
	private Container contenu;					// déclaration des composants
	private JLabel lblRef;
	private JLabel lblDesi;
	private JLabel lblPrix;
	private JTextField txtRef;
	private JTextField txtDesi;
	private JTextField txtPrix;
	private JButton btnAnnuler;
	private JButton btnValider;
	
	public FenetreAjoutCartouche()
	{
		contenu = this.getContentPane();		// gestion du conteneur
		contenu.setLayout(null);

		lblRef = new JLabel("Référence");		// gestion des étiquettes
		lblDesi = new JLabel("Désignation");
		lblPrix = new JLabel("Prix neuve");
		lblRef.setBounds(50,50, 100, 20);
		lblDesi.setBounds(250,50, 100, 20);
		lblPrix.setBounds(450,50, 100, 20);
		contenu.add(lblRef);
		contenu.add(lblDesi);
		contenu.add(lblPrix);
		
		txtRef = new JTextField();				// gestion des zones de texte
		txtDesi = new JTextField();
		txtPrix = new JTextField();
		txtRef.setBounds(50,80,100,20);
		txtDesi.setBounds(200,80,200,20);
		txtPrix.setBounds(450,80,100,20);
		contenu.add(txtRef);
		contenu.add(txtDesi);
		contenu.add(txtPrix);
		
		btnAnnuler = new JButton("Annuler");	// gestion des boutons de commande
		btnValider = new JButton("Valider");
		btnAnnuler.setBounds(100,120,100,20);
		btnValider.setBounds(300,120,100,20);
		btnAnnuler.addActionListener(this);
		btnValider.addActionListener(this);
		contenu.add(btnAnnuler);
		contenu.add(btnValider);
	}
	public void actionPerformed(ActionEvent evt)
	{
		if (evt.getSource() == btnAnnuler)
			this.dispose();
		if (evt.getSource() == btnValider)
		{
			if (txtRef.getText().isEmpty() || txtDesi.getText().isEmpty() || txtPrix.getText().isEmpty())
				JOptionPane.showMessageDialog(null,"Vous devez renseigner tous les champs !!!");
			else
			{
				Cartouche cart = new Cartouche(txtRef.getText(), txtDesi.getText(), Double.valueOf(txtPrix.getText()));
				DAO_TonerExpress.ajouterCartouche(cart);
			}
		}
	}
}
