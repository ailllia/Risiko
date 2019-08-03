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
   
   public static void main(String[] args) {
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
   
   @Override
   public void actionPerformed(ActionEvent arg0) {
   	// TODO Auto-generated method stub
   	
   }
}