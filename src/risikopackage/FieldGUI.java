package risikopackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.imageio.*;
import java.io.*;
import java.net.*;

public class FieldGUI extends JFrame implements ActionListener {
	
	private JFrame frame;
	private JMenuBar bar;
	private JLabel playeroneh1, playeronehr1, playeronehr2, playeronecolorh, playeronecolor, playertwoh1, playertwohr1, playertwohr2, playertwocolorh, playertwocolor;
	private JLabel amraarmy, amracolor;
	
   public FieldGUI() {
      Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
      JFrame frame = new JFrame("Risikospielfeld");
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      
      bar = new JMenuBar();
      JMenu rules = new JMenu("Spielregeln");
      JMenuItem winning = new JMenuItem("Ende des Spiels");
      rules.add(winning);
      JMenuItem attack = new JMenuItem("Land befreien");
      rules.add(attack);
      bar.add(rules);
      
      JMenu showMission = new JMenu("Mission anzeigen");
      JMenuItem missionPlayerOne = new JMenuItem("Spieler 1");
      showMission.add(missionPlayerOne);
      JMenuItem missionPlayerTwo = new JMenuItem("Spieler 2");
      showMission.add(missionPlayerTwo);
      bar.add(showMission);
      
      JMenu endGame = new JMenu("Spiel beenden");
      JMenuItem newGame = new JMenuItem("neues Spiel starten");
      endGame.add(newGame);
      JMenuItem endProg = new JMenuItem("Programm beenden");
      endGame.add(endProg);
      bar.add(endGame);
      
      newGame.addActionListener(e -> openAuswahl());
      endProg.addActionListener(e -> endProgram());
      
      frame.setJMenuBar(bar);
      
      //Angaben Spieler Eins
      playeroneh1 = new JLabel("Spieler Eins", SwingConstants.LEFT);
      playeroneh1.setFont(new Font("Sans-Serif", Font.BOLD, 13));
      frame.add(playeroneh1);
      playeroneh1.setBounds(20, 58, 170, 20);
      
      playeronecolorh = new JLabel("Farbe:", SwingConstants.LEFT);
      playeronecolorh.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
      frame.add(playeronecolorh);
      playeronecolorh.setBounds(25, 80, 70, 20);

      playeronecolor = new JLabel("farbe", SwingConstants.RIGHT);
      playeronecolor.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
      frame.add(playeronecolor);
      playeronecolor.setBounds(95, 80, 90, 20);
      
      playeronecolorh = new JLabel("Länder:", SwingConstants.LEFT);
      playeronecolorh.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
      frame.add(playeronecolorh);
      playeronecolorh.setBounds(25, 101, 70, 20);
      
      playeronecolor = new JLabel("zahl", SwingConstants.RIGHT);
      playeronecolor.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
      frame.add(playeronecolor);
      playeronecolor.setBounds(95, 101, 90, 20);
      
      playeronecolorh = new JLabel("Armeen:", SwingConstants.LEFT);
      playeronecolorh.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
      frame.add(playeronecolorh);
      playeronecolorh.setBounds(25, 122, 70, 20);
      
      playeronecolor = new JLabel("zahl", SwingConstants.RIGHT);
      playeronecolor.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
      frame.add(playeronecolor);
      playeronecolor.setBounds(95, 122, 90, 20);
      
      playeronecolorh = new JLabel("Mission:", SwingConstants.LEFT);
      playeronecolorh.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
      frame.add(playeronecolorh);
      playeronecolorh.setBounds(25, 143, 70, 20);
      
      playeronehr1 = new JLabel("");
      playeronehr1.setOpaque(true);
      playeronehr1.setBackground(new java.awt.Color(204, 204, 204));
      frame.add(playeronehr1);
      playeronehr1.setBounds(20, 78, 170, 2);
      
      playeronehr2 = new JLabel("");
      playeronehr2.setOpaque(true);
      playeronehr2.setBackground(new java.awt.Color(204, 204, 204));
      frame.add(playeronehr2);
      playeronehr2.setBounds(20, 100, 170, 1);
      
      playeronehr2 = new JLabel("");
      playeronehr2.setOpaque(true);
      playeronehr2.setBackground(new java.awt.Color(204, 204, 204));
      frame.add(playeronehr2);
      playeronehr2.setBounds(20, 121, 170, 1);

      playeronehr2 = new JLabel("");
      playeronehr2.setOpaque(true);
      playeronehr2.setBackground(new java.awt.Color(204, 204, 204));
      frame.add(playeronehr2);
      playeronehr2.setBounds(20, 142, 170, 1);
      
      //Angaben Spieler2
      playertwoh1 = new JLabel("Spieler Zwei", SwingConstants.RIGHT);
      playertwoh1.setFont(new Font("Sans-Serif", Font.BOLD, 13));
      frame.add(playertwoh1);
      playertwoh1.setBounds(795, 58, 170, 20);
      
      playertwocolorh = new JLabel("Farbe:", SwingConstants.LEFT);
      playertwocolorh.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
      frame.add(playertwocolorh);
      playertwocolorh.setBounds(800, 80, 70, 20);

      playertwocolor = new JLabel("farbe", SwingConstants.RIGHT);
      playertwocolor.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
      frame.add(playertwocolor);
      playertwocolor.setBounds(865, 80, 90, 20);
      
      playertwocolorh = new JLabel("Länder:", SwingConstants.LEFT);
      playertwocolorh.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
      frame.add(playertwocolorh);
      playertwocolorh.setBounds(800, 101, 70, 20);
      
      playertwocolor = new JLabel("zahl", SwingConstants.RIGHT);
      playertwocolor.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
      frame.add(playertwocolor);
      playertwocolor.setBounds(865, 101, 90, 20);
      
      playertwocolorh = new JLabel("Armeen:", SwingConstants.LEFT);
      playertwocolorh.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
      frame.add(playertwocolorh);
      playertwocolorh.setBounds(800, 122, 70, 20);
      
      playertwocolor = new JLabel("zahl", SwingConstants.RIGHT);
      playertwocolor.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
      frame.add(playertwocolor);
      playertwocolor.setBounds(865, 122, 90, 20);
      
      playertwocolorh = new JLabel("Mission:", SwingConstants.LEFT);
      playertwocolorh.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
      frame.add(playertwocolorh);
      playertwocolorh.setBounds(800, 143, 70, 20);
      
      playertwohr1 = new JLabel("");
      playertwohr1.setOpaque(true);
      playertwohr1.setBackground(new java.awt.Color(204, 204, 204));
      frame.add(playertwohr1);
      playertwohr1.setBounds(795, 78, 170, 2);
      
      playertwohr2 = new JLabel("");
      playertwohr2.setOpaque(true);
      playertwohr2.setBackground(new java.awt.Color(204, 204, 204));
      frame.add(playertwohr2);
      playertwohr2.setBounds(795, 100, 170, 1);
      
      playertwohr2 = new JLabel("");
      playertwohr2.setOpaque(true);
      playertwohr2.setBackground(new java.awt.Color(204, 204, 204));
      frame.add(playertwohr2);
      playertwohr2.setBounds(795, 121, 170, 1);

      playertwohr2 = new JLabel("");
      playertwohr2.setOpaque(true);
      playertwohr2.setBackground(new java.awt.Color(204, 204, 204));
      frame.add(playertwohr2);
      playertwohr2.setBounds(795, 142, 170, 1);   
      
      //Angaben Laender
      amracolor = new JLabel("farbe");
      amracolor.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amracolor);
      amracolor.setBounds(487, 83, 70, 12);      
      amraarmy = new JLabel("zahl");
      amraarmy.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amraarmy);
      amraarmy.setBounds(487, 95, 70, 12);
      
      amracolor = new JLabel("farbe");
      amracolor.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amracolor);
      amracolor.setBounds(643, 103, 70, 12);      
      amraarmy = new JLabel("zahl");
      amraarmy.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amraarmy);
      amraarmy.setBounds(643, 115, 70, 12);
      
      amracolor = new JLabel("farbe");
      amracolor.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amracolor);
      amracolor.setBounds(272, 132, 70, 12);      
      amraarmy = new JLabel("zahl");
      amraarmy.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amraarmy);
      amraarmy.setBounds(272, 144, 70, 12);
      
      amracolor = new JLabel("farbe");
      amracolor.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amracolor);
      amracolor.setBounds(390, 141, 70, 12);      
      amraarmy = new JLabel("zahl");
      amraarmy.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amraarmy);
      amraarmy.setBounds(390, 153, 70, 12);
      
      amracolor = new JLabel("farbe");
      amracolor.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amracolor);
      amracolor.setBounds(555, 224, 70, 12);      
      amraarmy = new JLabel("zahl");
      amraarmy.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amraarmy);
      amraarmy.setBounds(555, 236, 70, 12);
      
      amracolor = new JLabel("farbe");
      amracolor.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amracolor);
      amracolor.setBounds(390, 229, 70, 12);      
      amraarmy = new JLabel("zahl");
      amraarmy.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amraarmy);
      amraarmy.setBounds(390, 241, 70, 12);
      
      amracolor = new JLabel("farbe");
      amracolor.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amracolor);
      amracolor.setBounds(292, 246, 70, 12);      
      amraarmy = new JLabel("zahl");
      amraarmy.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amraarmy);
      amraarmy.setBounds(292, 258, 70, 12);
      
      amracolor = new JLabel("farbe");
      amracolor.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amracolor);
      amracolor.setBounds(680, 266, 70, 12);      
      amraarmy = new JLabel("zahl");
      amraarmy.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amraarmy);
      amraarmy.setBounds(680, 278, 70, 12);
      
      amracolor = new JLabel("farbe");
      amracolor.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amracolor);
      amracolor.setBounds(455, 261, 70, 12);      
      amraarmy = new JLabel("zahl");
      amraarmy.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amraarmy);
      amraarmy.setBounds(455, 273, 70, 12);
      
      amracolor = new JLabel("farbe");
      amracolor.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amracolor);
      amracolor.setBounds(575, 288, 70, 12);      
      amraarmy = new JLabel("zahl");
      amraarmy.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amraarmy);
      amraarmy.setBounds(575, 300, 70, 12);
      
      amracolor = new JLabel("farbe");
      amracolor.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amracolor);
      amracolor.setBounds(545, 393, 70, 12);      
      amraarmy = new JLabel("zahl");
      amraarmy.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amraarmy);
      amraarmy.setBounds(545, 405, 70, 12);
      
      amracolor = new JLabel("farbe");
      amracolor.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amracolor);
      amracolor.setBounds(250, 305, 70, 12);      
      amraarmy = new JLabel("zahl");
      amraarmy.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amraarmy);
      amraarmy.setBounds(250, 317, 70, 12);
      
      amracolor = new JLabel("farbe");
      amracolor.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amracolor);
      amracolor.setBounds(415, 393, 70, 12);      
      amraarmy = new JLabel("zahl");
      amraarmy.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amraarmy);
      amraarmy.setBounds(415, 405, 70, 12);
      
      amracolor = new JLabel("farbe");
      amracolor.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amracolor);
      amracolor.setBounds(315, 393, 70, 12);      
      amraarmy = new JLabel("zahl");
      amraarmy.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
      frame.add(amraarmy);
      amraarmy.setBounds(315, 405, 70, 12);
      
      frame.add(createMainPanel());
      frame.setSize(1000, 800);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
      frame.setResizable(false);
            
   }

   private JPanel createMainPanel() {
      Image image = null;
      try {
         image = ImageIO.read(new File("material\\backgroundmap.png"));
      } catch(IOException ioe) {
         JOptionPane.showMessageDialog(null,
            "Das Hintergrundbild konnte nicht geladen werden!\n" + ioe.getLocalizedMessage(),
            ioe.getClass().getName(),
            JOptionPane.WARNING_MESSAGE);
      }

      BackgroundImagePanel mainPanel = new BackgroundImagePanel(new BorderLayout());
      mainPanel.setImage(image); //hier kann man einstellen, ob das Bild im Original oder eingepasst ausgegeben werden soll (true/false)

      JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      southPanel.setOpaque(false); //entscheidet, ob das Button-Panel durchsichtig sein soll
      southPanel.add(createButtonPanel(southPanel.isOpaque()));

      mainPanel.add(southPanel, BorderLayout.SOUTH);
      return mainPanel;
   }

   //erzeugt ein Panel mit Buttons, bei Übergabe von true wird das Panel undurchsichtig
   private JPanel createButtonPanel(boolean opaque) {
      JPanel panel = new JPanel(new GridLayout(1, 0, 5, 5));
      panel.setOpaque(opaque);
      panel.add(new JButton("Abbrechen"));
      panel.add(new JButton("OK"));
      //Ereignisverarbeitung fehlt!
      return panel;
   }

   private void openAuswahl() {
	   setVisible(false);
	   PlayersGUI startNewGame = new PlayersGUI();
   }
   
   private void endProgram() {
	   System.exit(0);
   }   
   
/*   public static void main(String[] args) {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch(Exception e) {
         System.err.println(e);
      }

      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            new FieldGUI();
         }
      });
   }
   */
   
   @Override
   public void actionPerformed(ActionEvent arg0) {
   	// TODO Auto-generated method stub
   	
   }
}