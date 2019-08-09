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
    private JMenuBar bar;
    private JLabel playeroneh1, playeroneh2, playeroneh3, playeroneh4, playeroneh5, playeronep1, playeronep2,
            playeronep3, playeronep4, playeronep5, playeronehr1, playeronehr2, playeronehr3, playeronehr4;
    private JLabel playertwoh1, playertwoh2, playertwoh3, playertwoh4, playertwoh5, playertwop1, playertwop2,
            playertwop3, playertwop4, playertwop5, playertwohr1, playertwohr2, playertwohr3, playertwohr4;
    private JLabel otea, prya, solva;
    private JLabel armiesattacking, armiesdefending;
    private JPanel hitboxPanel;
    public static JTextArea textfield;
    private JScrollPane scrollbar;
    public static JButton next;
    private JButton undo, rollDice, suspendCoice, spreadNew;
    private MouseListener hitBoxListener, buttonListener;
    private int counterNext = 0;
    private int counterHitbox = 0;
    private Gameplay gameplay = new Gameplay();
    private int counter = 0;
    private int remaining;
    private Country selectedCountry1;
    private Country selectedCountry2;
    private Player player;
    private int counterPlayer = 0;
    private String dice_source1, dice_source2;
    private JLabel dicePlayerOne, dicePlayerTwo;

    private Player getPlayer() {
        if ((counterPlayer % 2) == 0)
            player = Main.playerOne;
        else
            player = Main.playerTwo;
        return player;
    }

    private void setRemaining() {
        if (counterNext == 1) {
            remaining = this.getPlayer().getNewArmies();
        } else if (counterNext == 3) {
            remaining = this.getPlayer().getArmiesAvailableToMove();
        } else {
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
                //testfall, eigentlich irrelevant
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

                if (armyLabel == null) {    //test
                    System.out.println("Army Label not found!");
                    return;
                }

                if (country != null) {  // wenn geklicktes land gefunden
                    setRemaining();     //wie viele Armeen duerfen verteilt werden
                    if (counterNext == 1 && counterHitbox == 0) {       //spieler 1 kann einheiten neu verteilen
                        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                            if (country.getColorOfOwnerString().equals(getPlayer().getColor()) //land gehoert dem spieler
                                    && remaining >= 0) { //es sind noch einheiten ueber
                                country.addArmy();
                                counter++;
                                remaining -= counter;
                                armyLabel.setText(Integer.toString(country.getArmiesInCountry()));
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
                    }
                    if (counterNext == 1 && country.getArmiesInCountry() > 1
                            && mouseEvent.getButton() == MouseEvent.BUTTON3) {
                        if (country.getColorOfOwnerString().equals(getPlayer().getColor()) //land gehoert dem spieler
                                && remaining > 1) {                                       //es sind noch einheiten ueber
                            country.loseArmy();
                            counter--;
                            remaining -= counter;
                            armyLabel.setText(Integer.toString(country.getArmiesInCountry()));
                            if (counterHitbox != 0) {
                                counterHitbox--;
                                next.setEnabled(false);
                            }
                        }
                        textfield.append("Noch " + remaining + " Einheit/en zu verteilen.\n");
                    }

                    if (counterNext == 2 && counterHitbox == 1) {       //angriffsrunde, auswahl des eigenen landes
                        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                            if (country.getColorOfOwnerString().equals(getPlayer().getColor())
                                    && country.getArmiesInCountry() > 1 && hasEnemyNeighbours(country)) {
                                selectedCountry1 = country;
                                armiesattacking = armyLabel;
                                textfield.append("Du hast " + country.getCountryName() + " ausgewaehlt, klicke jetzt" +
                                        " auf ein benachbartes Land (mit schwarzem Strich verbunden) deines Gegners.\n");
                                counterHitbox++;
                            } else {
                                textfield.append("Waehle zuerst ein Land von dir mit mehr als einer Einheit aus." +
                                        " Es muss mindestens ein gegnerisches Land als Nachbar haben.\n");
                            }
                        }
                    }
                    if (counterNext == 2 && mouseEvent.getButton() == MouseEvent.BUTTON3
                            && country.equals(selectedCountry1)) {
                        counterHitbox--;
                        selectedCountry1 = null;
                        textfield.append("Auswahl aufgehoben.\n");
                    }
                    if (counterNext == 2 && counterHitbox == 2) {   //angriffsphase, auswahl des gegnerlandes
                        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
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
                    }
                    if (counterNext == 2 && mouseEvent.getButton() == MouseEvent.BUTTON3
                            && country.equals(selectedCountry2)) {
                        if (counterHitbox == 4) {
                            rollDice.setEnabled(false);
                        }
                        counterHitbox--;
                        selectedCountry2 = null;
                        textfield.append("Auswahl aufgehoben.\n");
                    }
                    if (counterNext == 3 && counterHitbox == 3) {
                        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {         //linksklick
                            if (country.getColorOfOwnerString().equals(getPlayer().getColor())
                                    && remaining >= 0) {
                                country.addArmy();
                                armyLabel.setText(Integer.toString(country.getArmiesInCountry()));
                                if (remaining > 0) {
                                    textfield.append("Noch " + remaining + " Einheit/en zu verteilen.\n");
                                } else {
                                    textfield.append("Alle Einheiten verteilt, klicke 'Weiter' um forzufahren.\n");
                                    next.setEnabled(true);
                                    counterPlayer++;
                                }
                            } else {
                                textfield.append("Verteile die Einheiten in den Laendern, die deiner Farbe entsprechen.\n");
                            }
                        }
                    }
                    if (counterNext == 3 && mouseEvent.getButton() == MouseEvent.BUTTON3) {
                        if (country.getColorOfOwnerString().equals(getPlayer().getColor())
                                && country.getArmiesInCountry() > 1) {
                            country.loseArmy();
                            remaining++;
                            armyLabel.setText(Integer.toString(country.getArmiesInCountry()));
                            textfield.append("Noch " + remaining + "Einheit/en zu verteilen.\n");
                        } else if (country.getColorOfOwnerString().equals(getPlayer().getColor())) {
                            textfield.append("Dein Land muss mindestens eine Armee beinhalten.\n");
                        } else {
                            textfield.append("Einheiten koennen nur in den Laendern abgezogen werden, die deiner Farbe entsprechen.\n");
                        }
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
/*
        buttonListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                if (mouseEvent.getSource().getClass() != JButton.class)
                    return;

                JLabel armyLabel = null;

                for (Component c : hitboxPanel.getComponents()) {
                    if (c.getName().equals("armyLabel")) {

                        armyLabel = (JLabel) c;
                        //armyLabel.setText(Integer.toString(country.getArmiesInCountry()));
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
*/


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
        playeronep1.setForeground(Player.PlayerColorCode(Main.playerOne));
        playeronep1.setBounds(95, 80, 90, 20);
        frame.add(playeronep1);

        playeroneh3 = new JLabel("Laender:", SwingConstants.LEFT);
        playeroneh3.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeroneh3.setBounds(25, 101, 70, 20);
        frame.add(playeroneh3);

        playeronep2 = new JLabel(Integer.toString(Main.playerOne.numberOfCountries()), SwingConstants.RIGHT);
        playeronep2.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeronep2.setBounds(95, 101, 90, 20);
        frame.add(playeronep2);

        playeroneh4 = new JLabel("Armeen:", SwingConstants.LEFT);
        playeroneh4.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeroneh4.setBounds(25, 122, 70, 20);
        frame.add(playeroneh4);

        playeronep3 = new JLabel(Integer.toString(Main.playerOne.numberOfArmies()), SwingConstants.RIGHT);
        playeronep3.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeronep3.setBounds(95, 122, 90, 20);
        frame.add(playeronep3);

        playeroneh5 = new JLabel("Mission:", SwingConstants.LEFT);
        playeroneh5.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeroneh5.setBounds(25, 143, 70, 20);
        frame.add(playeroneh5);

        playeronep4 = new JLabel(Main.playerOne.getPlayerMission(), SwingConstants.RIGHT);
        playeronep4.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeronep4.setBounds(95, 143, 90, 20);
        frame.add(playeronep4);

        playeronep5 = new JLabel(breakDescription(Main.playerOne));
        playeronep5.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playeronep5.setBounds(25, 163, 165, 40);
        frame.add(playeronep5);

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
        playertwop1.setForeground(Player.PlayerColorCode(Main.playerTwo));
        playertwop1.setBounds(865, 80, 90, 20);
        frame.add(playertwop1);

        playertwoh3 = new JLabel("Laender:", SwingConstants.LEFT);
        playertwoh3.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwoh3.setBounds(800, 101, 70, 20);
        frame.add(playertwoh3);

        playertwop2 = new JLabel(Integer.toString(Main.playerTwo.numberOfCountries()), SwingConstants.RIGHT);
        playertwop2.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwop2.setBounds(865, 101, 90, 20);
        frame.add(playertwop2);

        playertwoh4 = new JLabel("Armeen:", SwingConstants.LEFT);
        playertwoh4.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwoh4.setBounds(800, 122, 70, 20);
        frame.add(playertwoh4);

        playertwop3 = new JLabel(Integer.toString(Main.playerTwo.numberOfArmies()), SwingConstants.RIGHT);
        playertwop3.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwop3.setBounds(865, 122, 90, 20);
        frame.add(playertwop3);

        playertwoh5 = new JLabel("Mission:", SwingConstants.LEFT);
        playertwoh5.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwoh5.setBounds(800, 143, 70, 20);
        frame.add(playertwoh5);

        playertwop4 = new JLabel(Main.playerTwo.getPlayerMission(), SwingConstants.RIGHT);
        playertwop4.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwop4.setBounds(865, 143, 90, 20);
        frame.add(playertwop4);

        playertwop5 = new JLabel(breakDescription(Main.playerTwo));
        playertwop5.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        playertwop5.setBounds(800, 163, 165, 40);
        frame.add(playertwop5);

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

        //Angaben Kontinente
        otea = new JLabel("OTEA", SwingConstants.RIGHT);
        otea.setFont(new Font("Sans-Serif", Font.BOLD, 11));
        otea.setForeground(Color.white);
        otea.setBounds(580, 421, 170, 12);
        frame.add(otea);

        prya = new JLabel("PRYA", SwingConstants.RIGHT);
        prya.setFont(new Font("Sans-Serif", Font.BOLD, 11));
        prya.setForeground(Color.white);
        prya.setBounds(580, 434, 170, 12);
        frame.add(prya);

        solva = new JLabel("SOLVA", SwingConstants.RIGHT);
        solva.setFont(new Font("Sans-Serif", Font.BOLD, 11));
        solva.setForeground(Color.white);
        solva.setBounds(580, 447, 170, 12);
        frame.add(solva);


        //Angaben Laender
        //Laender werden in Maps geladen die die zugeh�rigen Koordinaten enthalten
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

        for (Country c : Main.countries) {
            String n = c.getCountryName().toUpperCase();
            createHitBoxAndLabels(c, countryNameLabels.get(n), hitBoxPanels.get(n), armyCountLabels.get(n));
        }

        textfield = new JTextArea();
        scrollbar = new JScrollPane(textfield);
        scrollbar.setBounds(100, 500, 800, 150);
        textfield.setEditable(false);
        frame.add(scrollbar);

        next = new JButton("Weiter");
        next.setBounds(810, 465, 105, 25);
        next.addActionListener(e -> next());
        frame.add(next);

        undo = new JButton("Rueckgaengig");
        undo.setBounds(200, 470, 105, 20);
        undo.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
        undo.setEnabled(false);
        undo.addActionListener(e -> reduceArmy());
        frame.add(undo);

        suspendCoice = new JButton("Zurueck");
        suspendCoice.setBounds(350, 470, 105, 20);
        suspendCoice.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
        suspendCoice.setEnabled(false);
        suspendCoice.addActionListener(e -> undoCountryChoice());
        frame.add(suspendCoice);

        rollDice = new JButton("Wuerfeln");
        rollDice.setBounds(500, 470, 105, 20);
        rollDice.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
        rollDice.setEnabled(false);
        rollDice.addActionListener(e -> attack());
        frame.add(rollDice);

        spreadNew = new JButton("Neu verteilen");
        spreadNew.setBounds(650, 470, 105, 20);
        spreadNew.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
        spreadNew.setEnabled(false);
        spreadNew.addActionListener(e -> spreading());
        frame.add(spreadNew);

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

    private boolean hasEnemyNeighbours(Country country) {
        boolean bool = false;
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
            Country neighbor = Main.countries.stream()
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
                this.setRemaining();
                gameplay.deployArmies(this.getPlayer());
                break;
            case 2:
                counter = 0;
                gameplay.attackphase(this.getPlayer());
                break;
            case 3:
                gameplay.redistribution(this.getPlayer());
                spreadNew.setEnabled(true);
                counterNext = 0;
                counterHitbox = 0;
                rollDice.setEnabled(false);
                dicePlayerOne.setVisible(false);
                dicePlayerTwo.setVisible(false);
                break;
        }
    }

    private void reduceArmy() {
        this.setArmiesOnMap();
        gameplay.redistributionDel(this.getPlayer());
    }

    private void undoCountryChoice() {
    }

    private void attack() {
        Random random = new Random();
        int diceAttacker = random.nextInt(6) + 1;
        int diceDefender = random.nextInt(6) + 1;
        ImageIcon dicePlayerOne_img;
        ImageIcon dicePlayerTwo_img;
        if (player == Main.playerOne) {
            switch (diceAttacker) {
            case 1:
                dicePlayerOne_img = new ImageIcon("material/diceone.png");
                break;
            case 2:
                dicePlayerOne_img = new ImageIcon("material/dicetwo.png");
                break;
            case 3:
                dicePlayerOne_img = new ImageIcon("material/dicethree.png");
                break;
            case 4:
                dicePlayerOne_img = new ImageIcon("material/dicefour.png");
                break;
            case 5:
                dicePlayerOne_img = new ImageIcon("material/dicefive.png");
                break;
            case 6:
                dicePlayerOne_img = new ImageIcon("material/dicesix.png");
                break;
            default:
                dicePlayerOne_img = new ImageIcon();
        }
            switch (diceDefender) {
                case 1:
                    dicePlayerTwo_img = new ImageIcon("material/diceone.png");
                    break;
                case 2:
                    dicePlayerTwo_img = new ImageIcon("material/dicetwo.png");
                    break;
                case 3:
                    dicePlayerTwo_img = new ImageIcon("material/dicethree.png");
                    break;
                case 4:
                    dicePlayerTwo_img = new ImageIcon("material/dicefour.png");
                    break;
                case 5:
                    dicePlayerTwo_img = new ImageIcon("material/dicefive.png");
                    break;
                case 6:
                    dicePlayerTwo_img = new ImageIcon("material/dicesix.png");
                    break;
                default:
                    dicePlayerTwo_img = new ImageIcon();
            }
        } else {
            switch (diceDefender) {
                case 1:
                    dicePlayerOne_img = new ImageIcon("material/diceone.png");
                    break;
                case 2:
                    dicePlayerOne_img = new ImageIcon("material/dicetwo.png");
                    break;
                case 3:
                    dicePlayerOne_img = new ImageIcon("material/dicethree.png");
                    break;
                case 4:
                    dicePlayerOne_img = new ImageIcon("material/dicefour.png");
                    break;
                case 5:
                    dicePlayerOne_img = new ImageIcon("material/dicefive.png");
                    break;
                case 6:
                    dicePlayerOne_img = new ImageIcon("material/dicesix.png");
                    break;
                default:
                    dicePlayerOne_img = new ImageIcon();
            }
            switch (diceAttacker) {
                case 1:
                    dicePlayerTwo_img = new ImageIcon("material/diceone.png");
                    break;
                case 2:
                    dicePlayerTwo_img = new ImageIcon("material/dicetwo.png");
                    break;
                case 3:
                    dicePlayerTwo_img = new ImageIcon("material/dicethree.png");
                    break;
                case 4:
                    dicePlayerTwo_img = new ImageIcon("material/dicefour.png");
                    break;
                case 5:
                    dicePlayerTwo_img = new ImageIcon("material/dicefive.png");
                    break;
                case 6:
                    dicePlayerTwo_img = new ImageIcon("material/dicesix.png");
                    break;
                default:
                    dicePlayerTwo_img = new ImageIcon();
            }
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
                textfield.append("Du hast " + selectedCountry2.getCountryName() + " erfolgreich befreit!\n");
            }
        } else {
            textfield.append("Du verlierst eine Einheit.\n");
            selectedCountry1.loseArmy();
            armiesattacking.setText(Integer.toString(selectedCountry1.getArmiesInCountry()));
        }
        rollDice.setEnabled(false);
        counterHitbox = 1;      // damit wieder ein neues land gew�hlt werden kann
        textfield.append("Waehle erneut zwei Laender oder beene die Befreiungsphase durch einen Klick auf 'Weiter'.\n");
    }

    private void spreading() {
        if (this.getPlayer().getPlayerArmies() == this.getPlayer().numberOfCountries()) {
            gameplay.redistributionAbort(this.getPlayer());
            undo.setEnabled(false);
            spreadNew.setEnabled(false);
            counterPlayer++;
        }
        else{
            this.setArmiesOnMap();
            gameplay.redistributionNext(this.getPlayer());
            undo.setEnabled(true);
            spreadNew.setEnabled(false);
        }
    }

    private void setArmiesOnMap() {
        this.setRemaining();
        this.getPlayer().readyArmiesToMove();
        //Anzeige Armeen in Laendern aktualisieren
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

    private void openSelection() {
        int input = JOptionPane.showConfirmDialog(null, "Willst du wirklich ein neues Spiel anfangen?");
        switch (input) {
            case 0:
                // alle Werte auf null
                Main.playerOne.emptyAll();
                Main.playerTwo.emptyAll();
                // die Schleife danach ist eigentlich ueber - Bei der Initialisierung wird alles auf 1 gesetzt
                for (Country i : Main.countries) {
                    i.setArmies();
                }
                frame.dispose();
                new PlayersGUI();
            case 1:
                setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            case 2:
                setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        }
    }

    private void endProgram() {
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
    }

    private Country findCountry(String name) {
        for (Country c : Main.countries) {
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
}