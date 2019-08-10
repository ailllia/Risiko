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
import java.util.*;

public class FieldGUI extends JFrame implements ActionListener {

    private JFrame frame;
    private JLabel armiesattacking, armiesdefending, playertwop3, playertwop2, playeronep2, playeronep3;
    public static JTextArea textfield;
    public static JButton next;
    private JButton rollDice, check; //undo
    private MouseListener hitBoxListener;
    private int counterNext = 0;
    private int counterHitbox = 0;
    private int counter = 0;
    private int remaining;
    private Country selectedCountry1;
    private Country selectedCountry2;
    private Player player;
    private int counterPlayer = 0;
    private String dice_source1, dice_source2;
    private JLabel dicePlayerOne, dicePlayerTwo;

    private Player getPlayer() {
        if ((counterPlayer % 2) != 0)
            player = Gameplay.getInstance().getPlayerOne();
        else
            player = Gameplay.getInstance().getPlayerTwo();
        return player;
    }

    private void setRemaining() {
        if (counterNext == 1) {
            remaining = this.getPlayer().getNewArmies();
        } /*else if (counterNext == 3) {
            remaining = this.getPlayer().getArmiesAvailableToMove();
        }*/ else {
            remaining = 0;
        }
    }

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
                    return;
                }
                //findet angeklicktes land, durchsucht komponenten des panels nach "armyLabel", gibt geaenderte zahl auf der gui aus
                JPanel panel = (JPanel) mouseEvent.getSource();
                Country country = findCountry(panel.getName());
                JLabel armyLabel = null;
                for (Component c : panel.getComponents()) { //findet angeklicktes armyLabel
                    if (c.getName().equals("armyLabel")) {
                        armyLabel = (JLabel) c;
                    }
                }

                if (armyLabel == null) {
                    System.out.println("Army Label not found!");
                    return;
                }

                if (country != null) {  // wenn geklicktes land gefunden
                    //setRemaining();     //wie viele Armeen duerfen verteilt werden
                    if (counterNext == 1 && counterHitbox == 0) {       //spieler 1 kann einheiten neu verteilen
                        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                            deployArmiesLeftClick(armyLabel, country);
                        }
                    }
                    if (counterNext == 1 && country.getArmiesInCountry() > 1
                            && mouseEvent.getButton() == MouseEvent.BUTTON3) {
                        deployArmiesRightClick(armyLabel, country);
                    }

                    if (counterNext == 2 && counterHitbox == 1) {       //angriffsrunde, auswahl des eigenen landes
                        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                            chooseOwnCountryLeftClick(armyLabel, country);
                        }
                    }
                    if (counterNext == 2 && mouseEvent.getButton() == MouseEvent.BUTTON3
                            && country.equals(selectedCountry1)) {
                        chooseOwnCountryRightClick();
                    }
                    if (counterNext == 2 && counterHitbox == 2) {   //angriffsphase, auswahl des gegnerlandes
                        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                            chooseEnemyCountryLeftClick(armyLabel, country);
                        }
                    }
                    if (counterNext == 2 && mouseEvent.getButton() == MouseEvent.BUTTON3
                            && country.equals(selectedCountry2)) {
                        chooseEnemyCountryRightClick();
                    }
                    if (counterNext == 0 && counterHitbox == 0) {
                        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {         //linksklick
                            redistributionLeftClick(armyLabel, country);
                        }
                    }
                    if (counterNext == 0 && mouseEvent.getButton() == MouseEvent.BUTTON3) {
                        redistributionRightClick(armyLabel, country);
                    }
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

        JMenuBar bar = new JMenuBar();

        JMenu rules = new JMenu("Spielregeln");
        JMenuItem winning = new JMenuItem("Ende des Spiels");
        winning.addActionListener(e -> openEndingRules());
        rules.add(winning);
        JMenuItem attack = new JMenuItem("Land befreien");
        attack.addActionListener(e -> openPlayingRules());
        rules.add(attack);
        bar.add(rules);

        JMenu quitGame = new JMenu("Spiel abbrechen");
        JMenuItem endGame = new JMenuItem("Programm beenden");
        endGame.addActionListener(e -> endProgram());
        quitGame.add(endGame);
        JMenuItem newGame = new JMenuItem("neues Spiel starten");
        newGame.addActionListener(e -> openSelection());
        quitGame.add(newGame);
        bar.add(quitGame);

        frame.setJMenuBar(bar);

        //Angaben Spieler Eins
        JLabel playeroneh1 = new JLabel("Spieler Eins", SwingConstants.LEFT);
        playeroneh1.setFont(new Font("Sans-Serif", Font.BOLD, 13));
        playeroneh1.setBounds(20, 58, 170, 20);
        frame.add(playeroneh1);

        JLabel playeroneh2 = new JLabel("Farbe:", SwingConstants.LEFT);
        playeroneh2.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeroneh2.setBounds(25, 80, 70, 20);
        frame.add(playeroneh2);

        JLabel playeronep1 = new JLabel(Gameplay.getInstance().getPlayerOne().getColor(), SwingConstants.RIGHT);
        playeronep1.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeronep1.setForeground(Player.PlayerColorCode(Gameplay.getInstance().getPlayerOne()));
        playeronep1.setBounds(95, 80, 90, 20);
        frame.add(playeronep1);

        JLabel playeroneh3 = new JLabel("Laender:", SwingConstants.LEFT);
        playeroneh3.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeroneh3.setBounds(25, 101, 70, 20);
        frame.add(playeroneh3);

        playeronep2 = new JLabel(Integer.toString(Gameplay.getInstance().getPlayerOne().numberOfCountries()), SwingConstants.RIGHT);
        playeronep2.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeronep2.setBounds(95, 101, 90, 20);
        frame.add(playeronep2);

        JLabel playeroneh4 = new JLabel("Armeen im Feld:", SwingConstants.LEFT);
        playeroneh4.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeroneh4.setBounds(25, 122, 90, 20);
        frame.add(playeroneh4);

        playeronep3 = new JLabel(Integer.toString(Gameplay.getInstance().getPlayerOne().numberOfArmies(Gameplay.getInstance().getPlayerOne())), SwingConstants.RIGHT);
        playeronep3.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeronep3.setBounds(115, 122, 70, 20);
        frame.add(playeronep3);

        JLabel playeroneh5 = new JLabel("Mission:", SwingConstants.LEFT);
        playeroneh5.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeroneh5.setBounds(25, 143, 70, 20);
        frame.add(playeroneh5);

        JLabel playeronep4 = new JLabel(Gameplay.getInstance().getPlayerOne().getPlayerMission(), SwingConstants.RIGHT);
        playeronep4.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeronep4.setBounds(95, 143, 90, 20);
        frame.add(playeronep4);

        JLabel playeronep5 = new JLabel(breakDescription(Gameplay.getInstance().getPlayerOne()));
        playeronep5.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeronep5.setBounds(25, 163, 165, 40);
        frame.add(playeronep5);

        JLabel playeronehr1 = new JLabel("");
        playeronehr1.setOpaque(true);
        playeronehr1.setBackground(new java.awt.Color(204, 204, 204));
        playeronehr1.setBounds(20, 78, 170, 2);
        frame.add(playeronehr1);

        JLabel playeronehr2 = new JLabel("");
        playeronehr2.setOpaque(true);
        playeronehr2.setBackground(new java.awt.Color(204, 204, 204));
        playeronehr2.setBounds(20, 100, 170, 1);
        frame.add(playeronehr2);

        JLabel playeronehr3 = new JLabel("");
        playeronehr3.setOpaque(true);
        playeronehr3.setBackground(new java.awt.Color(204, 204, 204));
        playeronehr3.setBounds(20, 121, 170, 1);
        frame.add(playeronehr3);

        JLabel playeronehr4 = new JLabel("");
        playeronehr4.setOpaque(true);
        playeronehr4.setBackground(new java.awt.Color(204, 204, 204));
        playeronehr4.setBounds(20, 142, 170, 1);
        frame.add(playeronehr4);


        //Angaben Spieler2
        JLabel playertwoh1 = new JLabel("Spieler Zwei", SwingConstants.RIGHT);
        playertwoh1.setFont(new Font("Sans-Serif", Font.BOLD, 13));
        playertwoh1.setBounds(795, 58, 170, 20);
        frame.add(playertwoh1);

        JLabel playertwoh2 = new JLabel("Farbe:", SwingConstants.LEFT);
        playertwoh2.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwoh2.setBounds(800, 80, 70, 20);
        frame.add(playertwoh2);

        JLabel playertwop1 = new JLabel(Gameplay.getInstance().getPlayerTwo().getColor(), SwingConstants.RIGHT);
        playertwop1.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwop1.setForeground(Player.PlayerColorCode(Gameplay.getInstance().getPlayerTwo()));
        playertwop1.setBounds(865, 80, 90, 20);
        frame.add(playertwop1);

        JLabel playertwoh3 = new JLabel("Laender:", SwingConstants.LEFT);
        playertwoh3.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwoh3.setBounds(800, 101, 70, 20);
        frame.add(playertwoh3);

        playertwop2 = new JLabel(Integer.toString(Gameplay.getInstance().getPlayerTwo().numberOfCountries()), SwingConstants.RIGHT);
        playertwop2.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwop2.setBounds(865, 101, 90, 20);
        frame.add(playertwop2);

        JLabel playertwoh4 = new JLabel("Armeen im Feld:", SwingConstants.LEFT);
        playertwoh4.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwoh4.setBounds(800, 122, 90, 20);
        frame.add(playertwoh4);

        playertwop3 = new JLabel(Integer.toString(Gameplay.getInstance().getPlayerTwo().numberOfArmies(Gameplay.getInstance().getPlayerTwo())), SwingConstants.RIGHT);
        playertwop3.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwop3.setBounds(885, 122, 70, 20);
        frame.add(playertwop3);

        JLabel playertwoh5 = new JLabel("Mission:", SwingConstants.LEFT);
        playertwoh5.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwoh5.setBounds(800, 143, 70, 20);
        frame.add(playertwoh5);

        JLabel playertwop4 = new JLabel(Gameplay.getInstance().getPlayerTwo().getPlayerMission(), SwingConstants.RIGHT);
        playertwop4.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwop4.setBounds(865, 143, 90, 20);
        frame.add(playertwop4);

        JLabel playertwop5 = new JLabel(breakDescription(Gameplay.getInstance().getPlayerTwo()));
        playertwop5.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwop5.setBounds(800, 163, 165, 40);
        frame.add(playertwop5);

        JLabel playertwohr1 = new JLabel("");
        playertwohr1.setOpaque(true);
        playertwohr1.setBackground(new java.awt.Color(204, 204, 204));
        playertwohr1.setBounds(795, 78, 170, 2);
        frame.add(playertwohr1);

        JLabel playertwohr2 = new JLabel("");
        playertwohr2.setOpaque(true);
        playertwohr2.setBackground(new java.awt.Color(204, 204, 204));
        playertwohr2.setBounds(795, 100, 170, 1);
        frame.add(playertwohr2);

        JLabel playertwohr3 = new JLabel("");
        playertwohr3.setOpaque(true);
        playertwohr3.setBackground(new java.awt.Color(204, 204, 204));
        playertwohr3.setBounds(795, 121, 170, 1);
        frame.add(playertwohr3);

        JLabel playertwohr4 = new JLabel("");
        playertwohr4.setOpaque(true);
        playertwohr4.setBackground(new java.awt.Color(204, 204, 204));
        playertwohr4.setBounds(795, 142, 170, 1);
        frame.add(playertwohr4);

        //Angaben Kontinente
        JLabel otea = new JLabel("OTEA", SwingConstants.RIGHT);
        otea.setFont(new Font("Sans-Serif", Font.BOLD, 11));
        otea.setForeground(Color.white);
        otea.setBounds(580, 421, 170, 12);
        frame.add(otea);

        JLabel prya = new JLabel("PRYA", SwingConstants.RIGHT);
        prya.setFont(new Font("Sans-Serif", Font.BOLD, 11));
        prya.setForeground(Color.white);
        prya.setBounds(580, 434, 170, 12);
        frame.add(prya);

        JLabel solva = new JLabel("SOLVA", SwingConstants.RIGHT);
        solva.setFont(new Font("Sans-Serif", Font.BOLD, 11));
        solva.setForeground(Color.white);
        solva.setBounds(580, 447, 170, 12);
        frame.add(solva);


        //Angaben Laender
        //Laender werden in Maps geladen die die zugehoerigen Koordinaten enthalten
        //for-Schleife ruft dann f�r jedes land die funktion auf die die elemente dem frame / panel hinzuf�gt

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

        for (Country c : Gameplay.getInstance().getCountries()) {
            String n = c.getCountryName().toUpperCase();
            createHitBoxAndLabels(c, countryNameLabels.get(n), hitBoxPanels.get(n), armyCountLabels.get(n));
        }

        textfield = new JTextArea();
        JScrollPane scrollbar = new JScrollPane(textfield);
        scrollbar.setBounds(100, 500, 800, 150);
        textfield.setEditable(false);
        frame.add(scrollbar);

        next = new JButton("Weiter");
        next.setBounds(810, 465, 105, 25);
        next.addActionListener(e -> next());
        frame.add(next);

        rollDice = new JButton("Wuerfeln");
        rollDice.setBounds(380, 470, 105, 20);
        rollDice.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
        rollDice.setEnabled(false);
        rollDice.addActionListener(e -> attack());
        frame.add(rollDice);

        check = new JButton("Pruefen");
        check.setBounds(650, 470, 105, 20);
        check.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
        check.setEnabled(false);
        check.addActionListener(e -> checking());
        frame.add(check);

        dicePlayerOne = new JLabel(new ImageIcon(dice_source1));
        dicePlayerOne.setBounds(60, 340, 80, 80);
        dicePlayerOne.setVisible(false);
        frame.add(dicePlayerOne);

        dicePlayerTwo = new JLabel(new ImageIcon(dice_source2));
        dicePlayerTwo.setBounds(840, 340, 80, 80);
        dicePlayerTwo.setVisible(false);
        frame.add(dicePlayerTwo);

        frame.add(createMainPanel());
        frame.setSize(1000, 750);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void deployArmiesLeftClick(JLabel armyLabel, Country country) {
        if (country.getColorOfOwnerString().equals(getPlayer().getColor()) //land gehoert dem spieler
                && remaining >= 0) { //es sind noch einheiten ueber
            country.addArmy();
            //counter++;
            remaining--;
            armyLabel.setText(Integer.toString(country.getArmiesInCountry()));
            setArmyText(getPlayer());
            if (remaining > 0) {
                textfield.append("Noch " + remaining + " Einheit/en zu verteilen.\n");
            } else {
                textfield.append("Alle Einheiten verteilt, klicke 'Weiter' um forzufahren.\n");
                next.setEnabled(true);
                counterHitbox++;
            }
        } else {
            textfield.append("Verteile die Einheiten in den Laender, die deiner Farbe entsprechen.\n");
        }
    }

    private void deployArmiesRightClick(JLabel armyLabel, Country country) {
        if (country.getColorOfOwnerString().equals(getPlayer().getColor()) //land gehoert dem spieler
                && remaining > 1) {                                       //es sind noch einheiten ueber
            country.loseArmy();
            counter--;
            remaining -= counter;
            armyLabel.setText(Integer.toString(country.getArmiesInCountry()));
            setArmyText(getPlayer());
            if (counterHitbox != 0) {
                counterHitbox--;
                next.setEnabled(false);
            }
        }
        textfield.append("Noch " + remaining + " Einheit/en zu verteilen.\n");
    }

    private void chooseOwnCountryLeftClick(JLabel armyLabel, Country country) {
        if (country.getColorOfOwnerString().equals(getPlayer().getColor())
                && country.getArmiesInCountry() > 1 && hasEnemyNeighbours(country)) {
            selectedCountry1 = country;
            armiesattacking = armyLabel;
            textfield.append("Du hast " + country.getCountryName() + " ausgewaehlt, klicke jetzt" +
                    " auf ein benachbartes Land (mit schwarzem Strich verbunden) deines Gegners.\n");
            counterHitbox++;
            check.setEnabled(false);
        } else {
            textfield.append("Waehle zuerst ein Land von dir mit mehr als einer Einheit aus." +
                    " Es muss mindestens ein gegnerisches Land als Nachbar haben.\n");
        }
    }

    private void chooseOwnCountryRightClick() {
        counterHitbox--;
        selectedCountry1 = null;
        textfield.append("Auswahl aufgehoben.\n");
        check.setEnabled(true);
    }

    private void chooseEnemyCountryLeftClick(JLabel armyLabel, Country country) {
        if (!country.getColorOfOwnerString().equals(getPlayer().getColor()) //land gehoert gegner
                && country.isNeighbor(selectedCountry1)) {      //land ist nachbar
            selectedCountry2 = country;
            armiesdefending = armyLabel;
            rollDice.setEnabled(true);
            counterHitbox++;
            textfield.append("Du hast " + selectedCountry2.getCountryName() + " ausgewaehlt." +
                    " Klicke 'Wuerfeln' um eine Befreiungsaktion zu starten.\n");
        } else {
            textfield.append("Waehle ein Nachbarland deines Gegners aus.\n");
        }
    }

    private void chooseEnemyCountryRightClick() {
        if (counterHitbox == 4) {
            rollDice.setEnabled(false);
        }
        counterHitbox--;
        selectedCountry2 = null;
        textfield.append("Auswahl aufgehoben.\n");
    }

    private void redistributionLeftClick(JLabel armyLabel, Country country) {
        if (country.getColorOfOwnerString().equals(getPlayer().getColor())
                && remaining >= 0) {
            country.addArmy();
            remaining--;
            armyLabel.setText(Integer.toString(country.getArmiesInCountry()));
            setArmyText(getPlayer());
            if (remaining > 0) {
                textfield.append("Noch " + remaining + " Einheit/en zu verteilen.\n");
            } else {
                textfield.append("Alle Einheiten verteilt, klicke 'Weiter' um forzufahren.\n");
                next.setEnabled(true);
            }
        }
    }

    private void redistributionRightClick(JLabel armyLabel, Country country) {
        if (country.getColorOfOwnerString().equals(getPlayer().getColor())
                && country.getArmiesInCountry() > 1) {
            if (next.isEnabled())
                next.setEnabled(false);
            country.loseArmy();
            remaining++;
            setArmyText(getPlayer());
            armyLabel.setText(Integer.toString(country.getArmiesInCountry()));
            textfield.append("Noch " + remaining + "Einheit/en zu verteilen.\n");
        } else if (country.getColorOfOwnerString().equals(getPlayer().getColor())) {
            armyLabel.setText(Integer.toString(country.getArmiesInCountry()));
            textfield.append("Dein Land muss mindestens zwei Armeen beinhalten.\n");
        }
    }

    private boolean hasEnemyNeighbours(Country country) {
        /*
        -> make Country.neighboringCountries a List<Country> instead of List<String>
            therefore:
            -> read countries from static file like .xml or .java instead of (mutable) .txt file
            <countries>
                <country name=HELA>
                    <neighbors>
                        <country name=TIRA>
                        <country name=TIRA>
                        <country name=TIRA>
                    </neighbors>
                </country>
            </countries>
            -> change creation
                -> first: create all countries with empty neighbors
                -> second: fill neighbors
        (-> introduce a class variable Country.owner of type Player)


        c = color of country
        for country in neighbors:
            //search countryName in countries (loop)
                if color of owner of country != c
                    return true
        return false
         */
        for (String s : country.getNeighboringCountries()) {
            Country neighbor = Gameplay.getInstance().getCountries().stream()
                    .filter(country1 -> country1.getCountryName().equals(s))
                    .findFirst()
                    .orElse(null);
            if (neighbor != null && !country.getColorOfOwnerString().equals(neighbor.getColorOfOwnerString())) {
                return true;
            }
        }
        return false;
    }

    private void next() {       //ruft je nach spielzug die naechste spielfunktion auf
        counterNext++;
        switch (counterNext) {
            case 1:
                counterPlayer++;
                check.setEnabled(false);
                this.setRemaining();
                Gameplay.getInstance().deployArmiesText(this.getPlayer());
                break;
            case 2:
                counter = 0;
                Gameplay.getInstance().attackphaseText(this.getPlayer());
                check.setEnabled(true);
                break;
            case 3:
                Gameplay.getInstance().redistributionText(this.getPlayer());
                check.setEnabled(true);
                counterHitbox = 0;
                counterNext = 0;
                rollDice.setEnabled(false);
                dicePlayerOne.setVisible(false);
                dicePlayerTwo.setVisible(false);
                break;
        }
    }

    private void attack() {
        check.setEnabled(true);
        ImageIcon dicePlayerOne_img, dicePlayerTwo_img;
        Random random = new Random();
        //int diceAttacker = 6;
        //int diceDefender = 1;
        int diceAttacker = random.nextInt(6) + 1;
        int diceDefender = random.nextInt(6) + 1;
        if (player == Gameplay.getInstance().getPlayerOne()) {
            dicePlayerOne_img = getImageForDiceRoll(diceAttacker);
            dicePlayerTwo_img = getImageForDiceRoll(diceDefender);
        } else {
            dicePlayerOne_img = getImageForDiceRoll(diceDefender);
            dicePlayerTwo_img = getImageForDiceRoll(diceAttacker);
        }
        dicePlayerOne.setIcon(dicePlayerOne_img);
        dicePlayerTwo.setIcon(dicePlayerTwo_img);
        dicePlayerOne.setVisible(true);
        dicePlayerTwo.setVisible(true);
        textfield.append("Du wuerfelst eine " + diceAttacker + "!\nDer Besetzer wuerfelt eine " + diceDefender + "!\n");
        if (diceAttacker > diceDefender) {
            if (selectedCountry2.getArmiesInCountry() > 1) {
                textfield.append("Der Besetzer verliert eine Einheit!\n");
                selectedCountry2.loseArmy();
                armiesdefending.setText(Integer.toString(selectedCountry2.getArmiesInCountry()));
            } else {
                selectedCountry2.changeOwner(selectedCountry1.getColorOfOwnerString());
                selectedCountry2.setColorOfOwnerCode(selectedCountry1.getColorOfOwnerCode());
                selectedCountry2.setArmies();
                selectedCountry1.loseArmy();
                armiesdefending.setForeground(selectedCountry2.getColorOfOwnerCode());
                armiesattacking.setText(Integer.toString(selectedCountry1.getArmiesInCountry()));
                armiesdefending.setText(Integer.toString(selectedCountry2.getArmiesInCountry()));

                if (selectedCountry1.getColorOfOwnerString().equals(Gameplay.getInstance().getPlayerOne().getColor())) {
                    int j = 0;
                    for (int i = 0; i < Gameplay.getInstance().getPlayerTwo().numberOfCountries(); i++) {
                        if (selectedCountry2.getCountryName().equals(Gameplay.getInstance().getPlayerTwo().getCountryName(i))) {
                            j = i;
                        }
                    }
                    Gameplay.getInstance().getPlayerOne().addCountryToList(Gameplay.getInstance().getPlayerTwo().getCountryName(j), Gameplay.getInstance().getPlayerTwo().getCountry(j));
                    Gameplay.getInstance().getPlayerTwo().deleteCountryToList(j);
                    playeronep2.setText(Integer.toString(Gameplay.getInstance().getPlayerOne().numberOfCountries()));
                    playertwop2.setText(Integer.toString(Gameplay.getInstance().getPlayerTwo().numberOfCountries()));
                    playeronep3.setText(Integer.toString(Gameplay.getInstance().getPlayerOne().numberOfArmies(Gameplay.getInstance().getPlayerOne())));
                    playertwop3.setText(Integer.toString(Gameplay.getInstance().getPlayerTwo().numberOfArmies(Gameplay.getInstance().getPlayerTwo())));
                } else {
                    int j = 0;
                    for (int i = 0; i < Gameplay.getInstance().getPlayerOne().numberOfCountries(); i++) {
                        if (selectedCountry2.getCountryName().equals(Gameplay.getInstance().getPlayerOne().getCountryName(i))) {
                            j = i;
                        }
                    }
                    Gameplay.getInstance().getPlayerTwo().addCountryToList(Gameplay.getInstance().getPlayerOne().getCountryName(j), Gameplay.getInstance().getPlayerOne().getCountry(j));
                    Gameplay.getInstance().getPlayerOne().deleteCountryToList(j);
                    playeronep2.setText(Integer.toString(Gameplay.getInstance().getPlayerOne().numberOfCountries()));
                    playertwop2.setText(Integer.toString(Gameplay.getInstance().getPlayerTwo().numberOfCountries()));
                }
                playeronep3.setText(Integer.toString(Gameplay.getInstance().getPlayerOne().numberOfArmies(Gameplay.getInstance().getPlayerOne())));
                playertwop3.setText(Integer.toString(Gameplay.getInstance().getPlayerTwo().numberOfArmies(Gameplay.getInstance().getPlayerTwo())));


                textfield.append("Du hast " + selectedCountry2.getCountryName() + " erfolgreich befreit!\n");
                if (Mission.testMission(player)) {
                    Gameplay.getInstance().finishedGameText();      //pruefe ob spiel gewonnen
                    return;
                }
            }
        } else {
            textfield.append("Du verlierst eine Einheit.\n");
            selectedCountry1.loseArmy();
            armiesattacking.setText(Integer.toString(selectedCountry1.getArmiesInCountry()));
            playeronep3.setText(Integer.toString(Gameplay.getInstance().getPlayerOne().numberOfArmies(Gameplay.getInstance().getPlayerOne())));
            playertwop3.setText(Integer.toString(Gameplay.getInstance().getPlayerTwo().numberOfArmies(Gameplay.getInstance().getPlayerTwo())));
        }
        rollDice.setEnabled(false);
        setArmyText(Gameplay.getInstance().getPlayerOne());
        setArmyText(Gameplay.getInstance().getPlayerTwo());
        counterHitbox = 1;      // damit wieder ein neues land gew�hlt werden kann
        textfield.append("Waehle erneut zwei Laender oder beene die Befreiungsphase durch einen Klick auf 'Weiter'.\n");
    }

    private ImageIcon getImageForDiceRoll(int roll) {
        ImageIcon img;
        switch (roll) {
            case 1:
                img = new ImageIcon("material/diceone.png");
                break;
            case 2:
                img = new ImageIcon("material/dicetwo.png");
                break;
            case 3:
                img = new ImageIcon("material/dicethree.png");
                break;
            case 4:
                img = new ImageIcon("material/dicefour.png");
                break;
            case 5:
                img = new ImageIcon("material/dicefive.png");
                break;
            case 6:
                img = new ImageIcon("material/dicesix.png");
                break;
            default:
                img = new ImageIcon();
        }
        return img;
    }


    private void checking() {
        switch (counterNext) {
            case 0:
                if (this.getPlayer().getPlayerArmies() == this.getPlayer().numberOfCountries()) {
                    Gameplay.getInstance().redistributionAbortText(this.getPlayer());
                    check.setEnabled(false);
                } else {
                    Gameplay.getInstance().redistributionContText(this.getPlayer());
                    check.setEnabled(false);
                } break;
            case 2:
                if (this.getPlayer().attackPossible()) {
                    Gameplay.getInstance().attackPossibleText(this.getPlayer());
                    check.setEnabled(false);
                } else {
                    Gameplay.getInstance().attackNotPossibleText(this.getPlayer());
                    check.setEnabled(false);
                }
                break;
            default:
                check.setEnabled(false);
        }
    }

    private JPanel createMainPanel() {
        Image image = null;
        try {
            image = ImageIO.read(new File("material/backgroundmap.png"));
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
        JLabel nameLabel = new JLabel(country.getCountryName().toUpperCase());
        nameLabel.setFont(new Font("Sans-Serif", Font.BOLD, 13));
        nameLabel.setBounds(nameCoord.get(0), nameCoord.get(1), nameCoord.get(2), nameCoord.get(3));
        this.frame.add(nameLabel);

        //JPanel Hit box
        JPanel hitboxPanel = new JPanel();
        hitboxPanel.setName(country.getCountryName().toUpperCase());
        hitboxPanel.setBounds(hitBoxCoord.get(0), hitBoxCoord.get(1), hitBoxCoord.get(2), hitBoxCoord.get(3));
        //hitboxPanel.setVisible(false);
        hitboxPanel.addMouseListener(this.hitBoxListener);

        //JLabel Army Count
        JLabel armyLabel = new JLabel(Integer.toString(country.getArmiesInCountry()));
        armyLabel.setName("armyLabel");
        armyLabel.setFont(new Font("Sans-Serif", Font.BOLD, 17));
        armyLabel.setForeground(country.getColorOfOwnerCode());
        armyLabel.setBounds(armyCoord.get(0), armyCoord.get(1), armyCoord.get(2), armyCoord.get(3));
        hitboxPanel.add(armyLabel);
        this.frame.add(hitboxPanel);
    }


    // erklaert Spielende
    private void openEndingRules() {
        JOptionPane.showMessageDialog(frame,
                "<html>Das Spiel Risiko endet, wenn ein Spieler seine Mission erfuellt hat.<html>",
                "Ende des Spiels",
                JOptionPane.PLAIN_MESSAGE);
    }

    //erklaert die Spielregeln
    private void openPlayingRules() {
        JOptionPane.showMessageDialog(frame,
                "<html>Laender werden befreit, indem...<html>",
                "Land befreien",
                JOptionPane.PLAIN_MESSAGE);
    }

    // oeffnet Abfrage bzgl. neuem Spiel
    private void openSelection() {
        Object[] options = {"Ja, wirklich", "Ne, doch nicht"};
        int n = JOptionPane.showOptionDialog(frame,
                "Willst du wirklich ein neues Spiel anfangen?",
                "Spiel neu starten",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        switch (n) {
            case 0:
                // alle Werte auf null
                Gameplay.getInstance().getPlayerOne().emptyAll();
                Gameplay.getInstance().getPlayerTwo().emptyAll();
                // die Schleife danach ist eigentlich ueber - Bei der Initialisierung wird alles auf 1 gesetzt
                for (Country i : Gameplay.getInstance().getCountries()) {
                    i.setArmies();
                }
                frame.dispose();
                new PlayersGUI();
            case 1:
                setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        }
    }

    // beendet das Programm
    private void endProgram() {
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
    }

    private Country findCountry(String name) {
        for (Country c : Gameplay.getInstance().getCountries()) {
            if (c.getCountryName().toUpperCase().equals(name.toUpperCase())) {
                return c;
            }
        }
        return null;
    }

    // sorgt dafuer, dass die Missionsbeschreibung mehrzeilig angezeigt wird
    private static String breakDescription(Player playerNow) {
        return "<html>" + Mission.getDescription(playerNow.getPlayerMission()) + "<html>";
    }

    // sorgt fuer eine Aktualisierung der Armeeanzeige
    private void setArmyText(Player player) {
        if (player.getColor().equals(Gameplay.getInstance().getPlayerOne().getColor())) {
            playeronep3.setText(Integer.toString(Gameplay.getInstance().getPlayerOne().numberOfArmies(Gameplay.getInstance().getPlayerOne())));
        } else {
            playertwop3.setText(Integer.toString(Gameplay.getInstance().getPlayerTwo().numberOfArmies(Gameplay.getInstance().getPlayerTwo())));
        }

    }
}