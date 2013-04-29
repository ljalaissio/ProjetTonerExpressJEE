package ihm;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Container;

public class FenetreMenu extends JDialog implements ActionListener 
{
	private Container contenu;
	private JLabel lblTitre;
	private ButtonGroup bgOptions;
	private JRadioButton rbAjoutImp;
	private JRadioButton rbAjoutCart;
	private JRadioButton rbListerCart;
	private JButton btnAnnuler;
	private JButton btnValider;
	
	public FenetreMenu()
	{
		contenu = this.getContentPane();
		contenu.setLayout(null);
		
		lblTitre = new JLabel("Entreprise Toner Express");
		lblTitre.setBounds(170, 50, 150, 20);
		contenu.add(lblTitre);

		bgOptions=new ButtonGroup();
		rbAjoutImp=new JRadioButton("Ajouter une imprimante",false);
		rbAjoutCart=new JRadioButton("Ajouter une cartouche",false);
		rbListerCart=new JRadioButton("Lister les cartouches",true);
		rbAjoutImp.setBounds(100, 100, 300, 20);
		rbAjoutCart.setBounds(100, 120, 300, 20);
		rbListerCart.setBounds(100, 140, 300, 20);
		bgOptions.add(rbAjoutImp);
		bgOptions.add(rbAjoutCart);
		bgOptions.add(rbListerCart);
		contenu.add(rbAjoutImp);
		contenu.add(rbAjoutCart);
		contenu.add(rbListerCart);
		btnAnnuler = new JButton("Annuler");
		btnValider = new JButton("Valider");
		btnAnnuler.setBounds(100,200,100,20);
		btnValider.setBounds(300,200,100,20);
		btnAnnuler.addActionListener(this);
		btnValider.addActionListener(this);
		contenu.add(btnAnnuler);
		contenu.add(btnValider);
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		if (evt.getSource() == btnAnnuler)
			System.exit(0);
		if (evt.getSource() == btnValider)
		{
			if (rbListerCart.isSelected())
			{
				FenetreChoixImprimante fci;
				fci = new FenetreChoixImprimante();
				fci.setBounds(200,200,600,200);
				fci.setTitle("Choix de l'imprimante");
				fci.setVisible(true);
			}
			if (rbAjoutCart.isSelected())
			{
				FenetreAjoutCartouche fac;
				fac = new FenetreAjoutCartouche();
				fac.setBounds(200,200,600,200);
				fac.setTitle("Ajout d'une cartouche");
				fac.setVisible(true);
			}
		}
	}
}
