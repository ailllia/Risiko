package risikopackage;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayersGUI extends JFrame implements ActionListener {

	private JPanel panel;
	private JButton playing;
	private JLabel playerone;
	private final String[] colorsone = {"blau", "gelb" };
	private final String[] colorstwo = {"rot", "lila" };
	private JComboBox<String> colorone;
	
	public PlayersGUI() {
		
		JPanel cp = (JPanel)getContentPane();
				
		setTitle("Risiko - Spielvorbereitung");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		
		panel = new JPanel(null);
		
		playerone = new JLabel("Wählt zunächst eure Spielfarben:");
		panel.add(playerone);
		playerone.setBounds(10, 80, 480, 20);
		
		playerone = new JLabel("Spieler Eins:");
		panel.add(playerone);
		playerone.setBounds(150, 100, 70, 20);
/*		colorone = new JComboBox<String>(colorsone);
		panel.add(colorone);
		colorone.setBounds(150, 135, 100, 20);
	*/	
		playing = new JButton("Spiel starten");
		playing.setBounds(165, 400, 150, 20);
		panel.add(playing);
		playing.addActionListener(e -> openplay());
		
		cp.add(panel);
	}
	
	private void openplay() {
		setVisible(false);
//		BackgroundImageTest play = new BackgroundImageTest();
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
