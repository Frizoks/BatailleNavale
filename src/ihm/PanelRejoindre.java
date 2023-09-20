package ihm;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class PanelRejoindre extends JPanel implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/**
	 * Un Controleur pour pouvoir accéder au controleur
	 */
	private FrameRejoindre  frmRejoindre;

	/**
	 * Un Panel pour afficher des composants
	 */
	private JTextField  txtfPort;

	/**
	 * Un Panel pour afficher des composants
	 */
	private JTextField  txtfAdresse;


	/**
	 * Un Panel pour afficher des composants
	 */
	private JButton  btnValider;

	/**
	 * Un Panel pour afficher des composants
	 */
	private JLabel  lblErreur;



	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructuer de FrameAcceuil qui crée un panelGraphe et panelAction
	 * @param ctrl le controleur
	 * 
	 */
	public PanelRejoindre ( FrameRejoindre frmRejoindre )
	{
		this.frmRejoindre = frmRejoindre;

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.setLayout( new BorderLayout());
		this.setBackground(Color.LIGHT_GRAY);

		ImageIcon img = new ImageIcon(this.frmRejoindre.getIcon().getScaledInstance(350, 225, Image.SCALE_DEFAULT));

		JLabel lblIcon = new JLabel( img);

		JPanel pnlTemp = new JPanel( new GridLayout(3,1));
		JPanel pnlTxtf = new JPanel();
		this.txtfPort = new JTextField("9696",25);
		this.txtfAdresse = new JTextField("localhost",25);
		this.btnValider = new JButton("Valider");
		this.lblErreur = new JLabel("");

		this.btnValider.addActionListener(this);

		pnlTxtf.add(this.txtfPort);
		pnlTxtf.add(this.txtfAdresse);
		pnlTxtf.add(this.btnValider);

		pnlTemp.add(new JLabel(String.format("%6s","") + String.format("%-67s","Port :") + "Adresse IP :"));
		pnlTemp.add(pnlTxtf);
		pnlTemp.add(this.lblErreur);

		this.add(lblIcon,BorderLayout.CENTER);
		this.add(pnlTemp,BorderLayout.SOUTH);
	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource() == this.btnValider )
		{
			if ( !FrameJeu.connection(this.txtfPort.getText(),this.txtfAdresse.getText()))
			{
				this.lblErreur.setText("Port ou adresse introuvable");
			}
			else
			{
				this.frmRejoindre.connection();
			}
		}
	}
}