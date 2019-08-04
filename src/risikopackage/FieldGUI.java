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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FieldGUI extends JFrame implements ActionListener {

    private JFrame frame;
    private JMenuBar bar;
    private JLabel playeroneh1, playeroneh2, playeroneh3, playeroneh4, playeroneh5, playeronep1, playeronep2, playeronep3, playeronep4, playeronehr1, playeronehr2, playeronehr3, playeronehr4;
    private JLabel playertwoh1, playertwoh2, playertwoh3, playertwoh4, playertwoh5, playertwop1, playertwop2, playertwop3, playertwop4, playertwohr1, playertwohr2, playertwohr3, playertwohr4;
    private JLabel armyLabel, nameLabel, neaharmy, neahname, menaarmy, menaname, liyaarmy, liyaname, kilaarmy, kilaname, jariarmy, jariname, immaarmy, immaname, helaarmy, helaname, gydaarmy, gydaname, friaarmy, frianame, essaarmy, essaname, demiarmy, deminame, caiaarmy, caianame, bitaarmy, bitaname;
    private JPanel hitboxPanel, bitahitbox;
    public static JTextArea textfield;
    private MouseListener hitBoxListener;

    public FieldGUI() {
        hitBoxListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                if (mouseEvent.getSource().getClass() != JPanel.class) {
                    System.out.println("Invalid Type clicked");
                    return;
                }
                JPanel panel = (JPanel) mouseEvent.getSource();

                Country country = findCountry(panel.getName());
                if (country != null) {
                    //do attack things -> call a function
                    System.out.println(country.getCountryName() + " " + country.getArmiesInCountry());
                } else {
                    System.out.println("County not found");
                }
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        };

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame = new JFrame("Risikospielfeld");
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
        playeroneh1.setBounds(20, 58, 170, 20);
        frame.add(playeroneh1);

        playeroneh2 = new JLabel("Farbe:", SwingConstants.LEFT);
        playeroneh2.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeroneh2.setBounds(25, 80, 70, 20);
        frame.add(playeroneh2);

        playeronep1 = new JLabel(Main.playerOne.getColor(), SwingConstants.RIGHT);
        playeronep1.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeronep1.setForeground(setColor(Main.playerOne));
        playeronep1.setBounds(95, 80, 90, 20);
        frame.add(playeronep1);

        playeroneh3 = new JLabel("Länder:", SwingConstants.LEFT);
        playeroneh3.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeroneh3.setBounds(25, 101, 70, 20);
        frame.add(playeroneh3);

        playeronep2 = new JLabel("zahl", SwingConstants.RIGHT);
        playeronep2.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeronep2.setBounds(95, 101, 90, 20);
        frame.add(playeronep2);

        playeroneh4 = new JLabel("Armeen:", SwingConstants.LEFT);
        playeroneh4.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeroneh4.setBounds(25, 122, 70, 20);
        frame.add(playeroneh4);

        playeronep3 = new JLabel("zahl", SwingConstants.RIGHT);
        playeronep3.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeronep3.setBounds(95, 122, 90, 20);
        frame.add(playeronep3);

        playeroneh5 = new JLabel("Mission:", SwingConstants.LEFT);
        playeroneh5.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeroneh5.setBounds(25, 143, 70, 20);
        frame.add(playeroneh5);

        playeronep4 = new JLabel("missionsinhalt", SwingConstants.LEFT);
        playeronep4.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeronep4.setBounds(25, 163, 170, 100);
        frame.add(playeronep4);

        playeronehr1 = new JLabel("");
        playeronehr1.setOpaque(true);
        playeronehr1.setBackground(new java.awt.Color(204, 204, 204));
        playeronehr1.setBounds(20, 78, 170, 2);
        frame.add(playeronehr1);

        playeronehr2 = new JLabel("");
        playeronehr2.setOpaque(true);
        playeronehr2.setBackground(new java.awt.Color(204, 204, 204));
        playeronehr2.setBounds(20, 100, 170, 1);
        frame.add(playeronehr2);

        playeronehr3 = new JLabel("");
        playeronehr3.setOpaque(true);
        playeronehr3.setBackground(new java.awt.Color(204, 204, 204));
        playeronehr3.setBounds(20, 121, 170, 1);
        frame.add(playeronehr3);

        playeronehr4 = new JLabel("");
        playeronehr4.setOpaque(true);
        playeronehr4.setBackground(new java.awt.Color(204, 204, 204));
        playeronehr4.setBounds(20, 142, 170, 1);
        frame.add(playeronehr4);

        //Angaben Spieler2
        playertwoh1 = new JLabel("Spieler Zwei", SwingConstants.RIGHT);
        playertwoh1.setFont(new Font("Sans-Serif", Font.BOLD, 13));
        playertwoh1.setBounds(795, 58, 170, 20);
        frame.add(playertwoh1);

        playertwoh2 = new JLabel("Farbe:", SwingConstants.LEFT);
        playertwoh2.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwoh2.setBounds(800, 80, 70, 20);
        frame.add(playertwoh2);

        playertwop1 = new JLabel(Main.playerTwo.getColor(), SwingConstants.RIGHT);
        playertwop1.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwop1.setForeground(setColor(Main.playerTwo));
        playertwop1.setBounds(865, 80, 90, 20);
        frame.add(playertwop1);

        playertwoh3 = new JLabel("Länder:", SwingConstants.LEFT);
        playertwoh3.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwoh3.setBounds(800, 101, 70, 20);
        frame.add(playertwoh3);

        playertwop2 = new JLabel("zahl", SwingConstants.RIGHT);
        playertwop2.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwop2.setBounds(865, 101, 90, 20);
        frame.add(playertwop2);

        playertwoh4 = new JLabel("Armeen:", SwingConstants.LEFT);
        playertwoh4.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwoh4.setBounds(800, 122, 70, 20);
        frame.add(playertwoh4);

        playertwop3 = new JLabel("zahl", SwingConstants.RIGHT);
        playertwop3.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwop3.setBounds(865, 122, 90, 20);
        frame.add(playertwop3);

        playertwoh5 = new JLabel("Mission:", SwingConstants.LEFT);
        playertwoh5.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwoh5.setBounds(800, 143, 70, 20);
        frame.add(playertwoh5);

        playertwop4 = new JLabel("missionsinhalt", SwingConstants.LEFT);
        playertwop4.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwop4.setBounds(800, 163, 170, 100);
        frame.add(playertwop4);

        playertwohr1 = new JLabel("");
        playertwohr1.setOpaque(true);
        playertwohr1.setBackground(new java.awt.Color(204, 204, 204));
        playertwohr1.setBounds(795, 78, 170, 2);
        frame.add(playertwohr1);

        playertwohr2 = new JLabel("");
        playertwohr2.setOpaque(true);
        playertwohr2.setBackground(new java.awt.Color(204, 204, 204));
        playertwohr2.setBounds(795, 100, 170, 1);
        frame.add(playertwohr2);

        playertwohr3 = new JLabel("");
        playertwohr3.setOpaque(true);
        playertwohr3.setBackground(new java.awt.Color(204, 204, 204));
        playertwohr3.setBounds(795, 121, 170, 1);
        frame.add(playertwohr3);

        playertwohr4 = new JLabel("");
        playertwohr4.setOpaque(true);
        playertwohr4.setBackground(new java.awt.Color(204, 204, 204));
        playertwohr4.setBounds(795, 142, 170, 1);
        frame.add(playertwohr4);


        //Angaben Laender
        //Länder werden in Maps geladen die die zugehörigen Koordinaten enthalten
        //for-Schleife ruft dann für jedes land die funktion auf die die elemente dem frame / panel hinzufügt

        /*
        List of countries
        Map<String,List<int>>  maps country names to coordinates of JLabel country name
        Map<String,List<int>>  maps country names to coordinates of JPanel hit box
        Map<String,List<int>>  maps country names to coordinates of JLabel army count
        example:
        country names: AMRA :  487, 70, 70, 15
        */

        Map<String, ArrayList<Integer>> countryNameLabels = new HashMap<>();
        Map<String, ArrayList<Integer>> hitBoxPanels = new HashMap<>();
        Map<String, ArrayList<Integer>> armyCountLabels = new HashMap<>();

        countryNameLabels.put("AMRA", new ArrayList<>(Arrays.asList(487, 70, 70, 15)));
        hitBoxPanels.put("AMRA", new ArrayList<>(Arrays.asList(482, 80, 30, 30)));
        armyCountLabels.put("AMRA", new ArrayList<>(Arrays.asList(487, 85, 70, 20)));

        countryNameLabels.put("BITA", new ArrayList<>(Arrays.asList(643, 83, 70, 15)));
        hitBoxPanels.put("BITA", new ArrayList<>(Arrays.asList(638, 95, 30, 30)));
        armyCountLabels.put("BITA", new ArrayList<>(Arrays.asList(643, 98, 70, 20)));

        countryNameLabels.put("CAIA", new ArrayList<>(Arrays.asList(272, 92, 70, 15)));
        hitBoxPanels.put("CAIA", new ArrayList<>(Arrays.asList(267, 95, 30, 30)));
        armyCountLabels.put("CAIA", new ArrayList<>(Arrays.asList(272, 107, 70, 20)));

        countryNameLabels.put("DEMI", new ArrayList<>(Arrays.asList(390, 101, 70, 15)));
        hitBoxPanels.put("DEMI", new ArrayList<>(Arrays.asList(385, 110, 30, 30)));
        armyCountLabels.put("DEMI", new ArrayList<>(Arrays.asList(390, 116, 70, 20)));

        countryNameLabels.put("ESSA", new ArrayList<>(Arrays.asList(555, 184, 70, 15)));
        hitBoxPanels.put("ESSA", new ArrayList<>(Arrays.asList(550, 193, 30, 30)));
        armyCountLabels.put("ESSA", new ArrayList<>(Arrays.asList(555, 199, 70, 20)));

        countryNameLabels.put("FRIA", new ArrayList<>(Arrays.asList(390, 189, 70, 15)));
        hitBoxPanels.put("FRIA", new ArrayList<>(Arrays.asList(385, 193, 30, 30)));
        armyCountLabels.put("FRIA", new ArrayList<>(Arrays.asList(390, 204, 70, 20)));

        countryNameLabels.put("GYDA", new ArrayList<>(Arrays.asList(292, 206, 70, 15)));
        hitBoxPanels.put("GYDA", new ArrayList<>(Arrays.asList(287, 210, 30, 30)));
        armyCountLabels.put("GYDA", new ArrayList<>(Arrays.asList(292, 221, 70, 20)));

        countryNameLabels.put("HELA", new ArrayList<>(Arrays.asList(680, 231, 70, 15)));
        hitBoxPanels.put("HELA", new ArrayList<>(Arrays.asList(675, 236, 30, 30)));
        armyCountLabels.put("HELA", new ArrayList<>(Arrays.asList(680, 243, 70, 20)));

        countryNameLabels.put("IMMA", new ArrayList<>(Arrays.asList(455, 231, 70, 15)));
        hitBoxPanels.put("IMMA", new ArrayList<>(Arrays.asList(450, 239, 30, 30)));
        armyCountLabels.put("IMMA", new ArrayList<>(Arrays.asList(455, 246, 70, 20)));

        countryNameLabels.put("JARI", new ArrayList<>(Arrays.asList(575, 258, 70, 15)));
        hitBoxPanels.put("JARI", new ArrayList<>(Arrays.asList(570, 262, 30, 30)));
        armyCountLabels.put("JARI", new ArrayList<>(Arrays.asList(575, 273, 70, 20)));

        countryNameLabels.put("KILA", new ArrayList<>(Arrays.asList(545, 368, 70, 15)));
        hitBoxPanels.put("KILA", new ArrayList<>(Arrays.asList(540, 374, 30, 30)));
        armyCountLabels.put("KILA", new ArrayList<>(Arrays.asList(545, 383, 70, 20)));

        countryNameLabels.put("LIYA", new ArrayList<>(Arrays.asList(250, 285, 70, 15)));
        hitBoxPanels.put("LIYA", new ArrayList<>(Arrays.asList(245, 294, 30, 30)));
        armyCountLabels.put("LIYA", new ArrayList<>(Arrays.asList(250, 300, 70, 20)));

        countryNameLabels.put("MENA", new ArrayList<>(Arrays.asList(415, 373, 70, 15)));
        hitBoxPanels.put("MENA", new ArrayList<>(Arrays.asList(410, 379, 30, 30)));
        armyCountLabels.put("MENA", new ArrayList<>(Arrays.asList(415, 388, 70, 20)));

        countryNameLabels.put("NEAH", new ArrayList<>(Arrays.asList(315, 373, 70, 15)));
        hitBoxPanels.put("NEAH", new ArrayList<>(Arrays.asList(310, 380, 30, 30)));
        armyCountLabels.put("NEAH", new ArrayList<>(Arrays.asList(315, 388, 70, 20)));

        for (Country c : Main.countries) {
            String n = c.getCountryName().toUpperCase();
            createHitBoxAndLabels(c, countryNameLabels.get(n), hitBoxPanels.get(n), armyCountLabels.get(n));
        }

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

    private void createHitBoxAndLabels(Country country, ArrayList<Integer> nameCoord,
                                       ArrayList<Integer> hitBoxCoord, ArrayList<Integer> armyCoord) {
        // JLabel Country Name
        nameLabel = new JLabel(country.getCountryName().toUpperCase());
        nameLabel.setFont(new Font("Sans-Serif", Font.BOLD, 13));
        // Set Owner Color?
        nameLabel.setBounds(nameCoord.get(0), nameCoord.get(1), nameCoord.get(2), nameCoord.get(3));
        this.frame.add(nameLabel);
        //JPanel Hit box
        hitboxPanel = new JPanel();
        hitboxPanel.setName(country.getCountryName().toUpperCase());
        hitboxPanel.setBounds(hitBoxCoord.get(0), hitBoxCoord.get(1), hitBoxCoord.get(2), hitBoxCoord.get(3));
        this.frame.add(hitboxPanel);
        //amrahitbox.setVisible(true);
        hitboxPanel.addMouseListener(this.hitBoxListener);
        //JLabel Army Count
        armyLabel = new JLabel("00");
        armyLabel.setFont(new Font("Sans-Serif", Font.BOLD, 17));
        //armyLabel.setForeground(new java.awt.Color(country.getColorOfOwner())); // TODO
        armyLabel.setBounds(armyCoord.get(0), armyCoord.get(1), armyCoord.get(2), armyCoord.get(3));
        hitboxPanel.add(armyLabel);
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
    private Color setColor(Player playerNow) {
        Color color;
        if (playerNow.getColor().equals("blau")) {              //switch?
            color = new java.awt.Color(0, 0, 255);
        } else if (playerNow.getColor().equals("rot")) {
            color = new java.awt.Color(255, 0, 0);
        } else if (playerNow.getColor().equals("lila")) {
            color = new java.awt.Color(127, 0, 127);
        } else if (playerNow.getColor().equals("pink")) {
            color = new java.awt.Color(255, 0, 255);
        } else if (playerNow.getColor().equals("grau")) {
            color = new java.awt.Color(76, 76, 76);
        } else {
            color = new java.awt.Color(0, 0, 0);
        }
        return color;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub

    }

    private Country findCountry(String name) {
        for (Country c : Main.countries) {
            if (c.getCountryName().toUpperCase().equals(name.toUpperCase())) {
                return c;
            }
        }
        return null;
    }
}