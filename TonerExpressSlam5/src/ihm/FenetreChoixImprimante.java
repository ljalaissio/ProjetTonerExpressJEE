package ihm;

import java.awt.Container;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import dao.DAO_TonerExpress;
import metier.*;

public class FenetreChoixImprimante extends JDialog implements ActionListener
{
	private Container contenu;
	private JComboBox cboListeImp;
	private JButton btnAnnuler;
	private JButton btnValider;
	
	public FenetreChoixImprimante()
	{
		contenu = this.getContentPane();
		contenu.setLayout(null);

		cboListeImp = new JComboBox(DAO_TonerExpress.getLesImprimantes());
		cboListeImp.setBounds(50,50, 250, 20);
		contenu.add(cboListeImp);

		btnAnnuler = new JButton("Annuler");
		btnValider = new JButton("Valider");
		btnAnnuler.setBounds(350,50,100,20);
		btnValider.setBounds(470,50,100,20);
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
			Imprimante imp = (Imprimante) cboListeImp.getSelectedItem();
			String mes = "Pour l'imprimante " + imp.getDesImprimante() + ", il existe " + imp.getNbCartouchesCompatibles() + " cartouches compatibles";
			JOptionPane.showMessageDialog(null,mes);		
		}
	}
}
