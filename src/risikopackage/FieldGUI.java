package risikopackage;

import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.net.*;

public class FieldGUI {
   public FieldGUI() {
      Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
      JFrame frame = new JFrame("Risikospielfeld");
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
}