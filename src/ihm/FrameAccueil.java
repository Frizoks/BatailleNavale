package ihm;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;

import controleur.*;

public class FrameAccueil extends JFrame implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/** Un Controleur pour pouvoir accéder au controleur
	 * 
	 */
	private Controleur  ctrl;

	/**
	 * Bouton pour 
	 */
	private JButton     btnSolo;

	/**
	 * Bouton pour quitter
	 */
	private JButton     btnQuitter;

	/**
	 * Bouton pour accéder au jeu en mode deux joueurs
	 */
	private JButton     btnRejoindre;

	/**
	 * Bouton pour accéder au jeu en mode 1 joueur
	 */
	private JButton     btnHeberger;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructuer de FrameAcceuil qui crée un panelGraphe et panelAction
	 * @param ctrl le controleur
	 * 
	 */
	public FrameAccueil ( Controleur ctrl )
	{
		this.ctrl = ctrl;

		Image img       = new ImageIcon ( this.getToolkit ( ).getImage ( "Donnees/images/FondAccueil.png" ) ).getImage ( );
		Image scaledImg = img.getScaledInstance ( 1000, 700, Image.SCALE_SMOOTH );
		
		Image     icon        = Toolkit.getDefaultToolkit ( ).getImage ( "Donnees/images/BateauIcon.png" );
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit ( ).getScreenSize ( );

		int l = ( tailleEcran.width  - 612 ) / 2;
		int h = ( tailleEcran.height -  401 ) / 2;

		this.setSize     ( 700, 450   );
		this.setLocation (    l,   h   );
		this.setTitle    ( "Bataille Navale" );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		JPanel panel         = new JPanel ( new BorderLayout (      )         );
		JPanel panelTest     = new JPanel ( new GridLayout   ( 1, 3 )         );
		JPanel panelButton   = new JPanel ( new GridLayout   ( 6, 1, 15, 15 ) );
		JPanel panelInutile  = new JPanel (                                   );
		JPanel panelInutile2 = new JPanel (                                   );
		
		JLabel j = new JLabel ( new ImageIcon ( scaledImg ) );
		j.setLayout ( new BorderLayout ( ) );

		panel        .setOpaque ( false );
		panelTest    .setOpaque ( false );
		panelButton  .setOpaque ( false );
		panelInutile .setOpaque ( false );
		panelInutile2.setOpaque ( false );

		this.btnSolo           = new JButton ( "Solo" );
		this.btnHeberger       = new JButton ( "Héberger" );
		this.btnRejoindre       = new JButton ( "Rejoindre" );
		this.btnQuitter  = new JButton ( "Quitter"  );

		panelButton.add ( this.btnSolo );
		panelButton.add ( this.btnHeberger       );
		panelButton.add ( this.btnRejoindre       );
		panelButton.add ( this.btnQuitter  );

		panelTest.add ( panelInutile  );
		panelTest.add ( panelButton   );
		panelTest.add ( panelInutile2 );

		panel.add ( panelTest, BorderLayout.SOUTH );
		
		j.add ( panel, BorderLayout.CENTER );

		this.getContentPane ( ).add ( j );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btnSolo.addActionListener ( this );
		this.btnHeberger.addActionListener ( this );
		this.btnRejoindre.addActionListener ( this );
		this.btnQuitter .addActionListener ( this );

		this.setIconImage             ( icon          );
		this.setDefaultCloseOperation ( EXIT_ON_CLOSE );
		this.setVisible               ( true          );
	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource() == this.btnHeberger )
		{
			this.ctrl.partieMulti();
			new FrameHeberger(ctrl);
		}
		if ( e.getSource() == this.btnRejoindre )
		{
			this.ctrl.partieMulti();
			new FrameRejoindre(ctrl);
		}
		if ( e.getSource() == this.btnSolo )
		{
			this.ctrl.partieSolo();
			FrameJeu frmTemp = new FrameJeu(ctrl);
			frmTemp.afficher();
		}
		
		this.cacher();
	}

	/**
	 * Suprrimer la frame quand on clique sur un bouton
	 */
	public void cacher ( ) { this.dispose ( ); }
}