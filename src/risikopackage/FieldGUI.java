package risikopackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class FieldGUI extends JFrame implements ActionListener {

    private JFrame frame;
    private JMenuBar bar;
    private JLabel playeroneh1, playeroneh2, playeroneh3, playeroneh4, playeroneh5, playeronep1, playeronep2, playeronep3, playeronep4, playeronehr1, playeronehr2, playeronehr3, playeronehr4;
    private JLabel playertwoh1, playertwoh2, playertwoh3, playertwoh4, playertwoh5, playertwop1, playertwop2, playertwop3, playertwop4, playertwohr1, playertwohr2, playertwohr3, playertwohr4;
    private JLabel amraarmy, amraname, neaharmy, neahname, menaarmy, menaname, liyaarmy, liyaname, kilaarmy, kilaname, jariarmy, jariname, immaarmy, immaname, helaarmy, helaname, gydaarmy, gydaname, friaarmy, frianame, essaarmy, essaname, demiarmy, deminame, caiaarmy, caianame, bitaarmy, bitaname;
    private JPanel amrahitbox, bitahitbox;
    private JTextArea textfield;

    public FieldGUI() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Risikospielfeld");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        playeroneh2 = new JLabel("Farbe:", SwingConstants.LEFT);
        playeroneh2.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        frame.add(playeroneh2);
        playeroneh2.setBounds(25, 80, 70, 20);

        playeronep1 = new JLabel(Main.playerOne.getColor(), SwingConstants.RIGHT);
        playeronep1.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeronep1.setForeground(setColor(Main.playerOne));
        frame.add(playeronep1);
        playeronep1.setBounds(95, 80, 90, 20);

        playeroneh3 = new JLabel("Länder:", SwingConstants.LEFT);
        playeroneh3.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        frame.add(playeroneh3);
        playeroneh3.setBounds(25, 101, 70, 20);

        playeronep2 = new JLabel("zahl", SwingConstants.RIGHT);
        playeronep2.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        frame.add(playeronep2);
        playeronep2.setBounds(95, 101, 90, 20);

        playeroneh4 = new JLabel("Armeen:", SwingConstants.LEFT);
        playeroneh4.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        frame.add(playeroneh4);
        playeroneh4.setBounds(25, 122, 70, 20);

        playeronep3 = new JLabel("zahl", SwingConstants.RIGHT);
        playeronep3.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        frame.add(playeronep3);
        playeronep3.setBounds(95, 122, 90, 20);

        playeroneh5 = new JLabel("Mission:", SwingConstants.LEFT);
        playeroneh5.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        frame.add(playeroneh5);
        playeroneh5.setBounds(25, 143, 70, 20);

        playeronep4 = new JLabel("missionsinhalt", SwingConstants.LEFT);
        playeronep4.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        frame.add(playeronep4);
        playeronep4.setBounds(25, 163, 170, 100);

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

        playeronehr3 = new JLabel("");
        playeronehr3.setOpaque(true);
        playeronehr3.setBackground(new java.awt.Color(204, 204, 204));
        frame.add(playeronehr3);
        playeronehr3.setBounds(20, 121, 170, 1);

        playeronehr4 = new JLabel("");
        playeronehr4.setOpaque(true);
        playeronehr4.setBackground(new java.awt.Color(204, 204, 204));
        frame.add(playeronehr4);
        playeronehr4.setBounds(20, 142, 170, 1);

        //Angaben Spieler2
        playertwoh1 = new JLabel("Spieler Zwei", SwingConstants.RIGHT);
        playertwoh1.setFont(new Font("Sans-Serif", Font.BOLD, 13));
        frame.add(playertwoh1);
        playertwoh1.setBounds(795, 58, 170, 20);

        playertwoh2 = new JLabel("Farbe:", SwingConstants.LEFT);
        playertwoh2.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        frame.add(playertwoh2);
        playertwoh2.setBounds(800, 80, 70, 20);

        playertwop1 = new JLabel(Main.playerTwo.getColor(), SwingConstants.RIGHT);
        playertwop1.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwop1.setForeground(setColor(Main.playerTwo));
        frame.add(playertwop1);
        playertwop1.setBounds(865, 80, 90, 20);

        playertwoh3 = new JLabel("Länder:", SwingConstants.LEFT);
        playertwoh3.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        frame.add(playertwoh3);
        playertwoh3.setBounds(800, 101, 70, 20);

        playertwop2 = new JLabel("zahl", SwingConstants.RIGHT);
        playertwop2.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        frame.add(playertwop2);
        playertwop2.setBounds(865, 101, 90, 20);

        playertwoh4 = new JLabel("Armeen:", SwingConstants.LEFT);
        playertwoh4.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        frame.add(playertwoh4);
        playertwoh4.setBounds(800, 122, 70, 20);

        playertwop3 = new JLabel("zahl", SwingConstants.RIGHT);
        playertwop3.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        frame.add(playertwop3);
        playertwop3.setBounds(865, 122, 90, 20);

        playertwoh5 = new JLabel("Mission:", SwingConstants.LEFT);
        playertwoh5.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        frame.add(playertwoh5);
        playertwoh5.setBounds(800, 143, 70, 20);

        playertwop4 = new JLabel("missionsinhalt", SwingConstants.LEFT);
        playertwop4.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        frame.add(playertwop4);
        playertwop4.setBounds(800, 163, 170, 100);

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

        playertwohr3 = new JLabel("");
        playertwohr3.setOpaque(true);
        playertwohr3.setBackground(new java.awt.Color(204, 204, 204));
        frame.add(playertwohr3);
        playertwohr3.setBounds(795, 121, 170, 1);

        playertwohr4 = new JLabel("");
        playertwohr4.setOpaque(true);
        playertwohr4.setBackground(new java.awt.Color(204, 204, 204));
        frame.add(playertwohr4);
        playertwohr4.setBounds(795, 142, 170, 1);


        MouseListener testListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                if (mouseEvent.getSource().getClass() != JPanel.class) {
                    System.out.println("Invalid Type");
                    return;
                }
                JPanel panel = (JPanel) mouseEvent.getSource();
                panel.setVisible(false);
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        };

        //Angaben Laender
        amraname = new JLabel("AMRA");
        amraname.setFont(new Font("Sans-Serif", Font.BOLD, 13));
        frame.add(amraname);
        amraname.setBounds(487, 45, 70, 15);
        amraarmy = new JLabel("00");
        amraarmy.setFont(new Font("Sans-Serif", Font.BOLD, 17));
        frame.add(amraarmy);
        amraarmy.setBounds(487, 60, 70, 20);
        amrahitbox = new JPanel();
        frame.add(amrahitbox);
        amrahitbox.setBounds(482, 58, 30, 30);
        //amrahitbox.setVisible(true);
        amrahitbox.addMouseListener(testListener);

        bitaname = new JLabel("BITA");
        bitaname.setFont(new Font("Sans-Serif", Font.BOLD, 13));
        frame.add(bitaname);
        bitaname.setBounds(643, 83, 70, 15);
        bitaarmy = new JLabel("00");
        bitaarmy.setFont(new Font("Sans-Serif", Font.BOLD, 17));
        frame.add(bitaarmy);
        bitaarmy.setBounds(643, 98, 70, 20);
        bitahitbox = new JPanel();
        frame.add(bitahitbox);
        bitahitbox.setBounds(638, 95, 30, 30);
        //bitahitbox.setVisible(true);
        bitahitbox.addMouseListener(testListener);

        caianame = new JLabel("CAIA");
        caianame.setFont(new Font("Sans-Serif", Font.BOLD, 13));
        frame.add(caianame);
        caianame.setBounds(272, 92, 70, 15);
        caiaarmy = new JLabel("00");
        caiaarmy.setFont(new Font("Sans-Serif", Font.BOLD, 17));
        frame.add(caiaarmy);
        caiaarmy.setBounds(272, 107, 70, 20);

        deminame = new JLabel("DEMI");
        deminame.setFont(new Font("Sans-Serif", Font.BOLD, 13));
        frame.add(deminame);
        deminame.setBounds(390, 101, 70, 15);
        demiarmy = new JLabel("00");
        demiarmy.setFont(new Font("Sans-Serif", Font.BOLD, 17));
        frame.add(demiarmy);
        demiarmy.setBounds(390, 116, 70, 20);

        essaname = new JLabel("ESSA");
        essaname.setFont(new Font("Sans-Serif", Font.BOLD, 13));
        frame.add(essaname);
        essaname.setBounds(555, 184, 70, 15);
        essaarmy = new JLabel("00");
        essaarmy.setFont(new Font("Sans-Serif", Font.BOLD, 17));
        frame.add(essaarmy);
        essaarmy.setBounds(555, 199, 70, 20);

        frianame = new JLabel("FRIA");
        frianame.setFont(new Font("Sans-Serif", Font.BOLD, 13));
        frame.add(frianame);
        frianame.setBounds(390, 189, 70, 15);
        friaarmy = new JLabel("00");
        friaarmy.setFont(new Font("Sans-Serif", Font.BOLD, 17));
        frame.add(friaarmy);
        friaarmy.setBounds(390, 204, 70, 20);

        gydaname = new JLabel("GYDA");
        gydaname.setFont(new Font("Sans-Serif", Font.BOLD, 13));
        frame.add(gydaname);
        gydaname.setBounds(292, 206, 70, 15);
        gydaarmy = new JLabel("00");
        gydaarmy.setFont(new Font("Sans-Serif", Font.BOLD, 17));
        frame.add(gydaarmy);
        gydaarmy.setBounds(292, 221, 70, 20);

        helaname = new JLabel("HELA");
        helaname.setFont(new Font("Sans-Serif", Font.BOLD, 13));
        frame.add(helaname);
        helaname.setBounds(680, 231, 70, 15);
        helaarmy = new JLabel("00");
        helaarmy.setFont(new Font("Sans-Serif", Font.BOLD, 17));
        frame.add(helaarmy);
        helaarmy.setBounds(680, 243, 70, 20);

        immaname = new JLabel("IMMA");
        immaname.setFont(new Font("Sans-Serif", Font.BOLD, 13));
        frame.add(immaname);
        immaname.setBounds(455, 231, 70, 15);
        immaarmy = new JLabel("00");
        immaarmy.setFont(new Font("Sans-Serif", Font.BOLD, 17));
        frame.add(immaarmy);
        immaarmy.setBounds(455, 246, 70, 20);

        jariname = new JLabel("JARI");
        jariname.setFont(new Font("Sans-Serif", Font.BOLD, 13));
        frame.add(jariname);
        jariname.setBounds(575, 258, 70, 15);
        jariarmy = new JLabel("00");
        jariarmy.setFont(new Font("Sans-Serif", Font.BOLD, 17));
        frame.add(jariarmy);
        jariarmy.setBounds(575, 273, 70, 20);

        kilaname = new JLabel("KILA");
        kilaname.setFont(new Font("Sans-Serif", Font.BOLD, 13));
        frame.add(kilaname);
        kilaname.setBounds(545, 368, 70, 15);
        kilaarmy = new JLabel("00");
        kilaarmy.setFont(new Font("Sans-Serif", Font.BOLD, 17));
        frame.add(kilaarmy);
        kilaarmy.setBounds(545, 383, 70, 20);

        liyaname = new JLabel("LIYA");
        liyaname.setFont(new Font("Sans-Serif", Font.BOLD, 13));
        frame.add(liyaname);
        liyaname.setBounds(250, 285, 70, 15);
        liyaarmy = new JLabel("00");
        liyaarmy.setFont(new Font("Sans-Serif", Font.BOLD, 17));
        frame.add(liyaarmy);
        liyaarmy.setBounds(250, 300, 70, 20);

        menaname = new JLabel("MENA");
        menaname.setFont(new Font("Sans-Serif", Font.BOLD, 13));
        frame.add(menaname);
        menaname.setBounds(415, 373, 70, 15);
        menaarmy = new JLabel("00");
        menaarmy.setFont(new Font("Sans-Serif", Font.BOLD, 17));
        frame.add(menaarmy);
        menaarmy.setBounds(415, 388, 70, 20);

        neahname = new JLabel("NEAH");
        neahname.setFont(new Font("Sans-Serif", Font.BOLD, 13));
        frame.add(neahname);
        neahname.setBounds(315, 373, 70, 15);
        neaharmy = new JLabel("00");
        neaharmy.setFont(new Font("Sans-Serif", Font.BOLD, 17));
        frame.add(neaharmy);
        neaharmy.setBounds(315, 388, 70, 20);

        textfield = new JTextArea();
        frame.add(textfield);
        textfield.setBounds(100, 500, 800, 150);

        frame.add(createMainPanel());
        frame.setSize(1000, 750);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

    }

    private JPanel createMainPanel() {
        Image image = null;
        try {
            image = ImageIO.read(new File("material\\backgroundmap.png"));
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null,
                    "Das Hintergrundbild konnte nicht geladen werden!\n" + ioe.getLocalizedMessage(),
                    ioe.getClass().getName(),
                    JOptionPane.WARNING_MESSAGE);
        }

        BackgroundImagePanel mainPanel = new BackgroundImagePanel(new BorderLayout());
        mainPanel.setImage(image); //hier kann man einstellen, ob das Bild im Original oder eingepasst ausgegeben werden soll (true/false)

        return mainPanel;
    }


    private void openAuswahl() {
        //setVisible(false);
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
    
    //holt Schriftfarben
    // "blau", "rot", "lila", "pink", "grau"
    public Color setColor(Player playerNow) 
    {
    	Color color = new java.awt.Color(0, 0, 0);
    	if (playerNow.getColor() == "blau")
    	{
    		color = new java.awt.Color(0, 0, 255);
    	}
    	else if (playerNow.getColor() == "rot")
    	{
    		color = new java.awt.Color(255, 0, 0);
    	}
    	else if (playerNow.getColor() == "lila")
    	{
    		color = new java.awt.Color(127, 0, 127);
    	}
    	else if (playerNow.getColor() == "pink")
    	{
    		color = new java.awt.Color(255, 0, 255);
    	}
    	else if (playerNow.getColor() == "grau")
    	{
    		color = new java.awt.Color(76, 76, 76);
    	}
    	else
    	{
    		color = new java.awt.Color(0, 0, 0);    		
    	}
    	return color;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub

    }
}