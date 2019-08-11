package risikopackage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.Random;

/**
 * FieldGUI displays the status of the game and lets the players interact with the program.
 *
 * @author Swantje Wiechmann
 */
public class FieldGUI extends JFrame implements ActionListener {
    private JFrame frame;
    private JLabel armiesattacking, armiesdefending, playertwop3, playertwop2, playeronep2, playeronep3;
    private JLabel dicePlayerOne, dicePlayerTwo;
    public static JTextArea textfield;
    private JButton next, rollDice, check;
    private MouseListener hitBoxListener;
    private int remaining;
    private int counterNext = 0;
    private int counterHitbox = 0;
    private int counter = 0;
    private int counterPlayer = 0;
    private Country selectedCountry1, selectedCountry2;
    private Player player;
    private String dice_source1, dice_source2;
    private ArrayList<Country> increasedCountries = new ArrayList<>();
    private LinkedList<Country> clickedCountries = new LinkedList<>();

    /**
     * Gets the player whose turn it is.
     *
     * @return the player whose turn it is
     */
    private Player getPlayer() {
        if ((counterPlayer % 2) != 0) {
            player = Gameplay.getInstance().getPlayerOne();
            textfield.setForeground(Player.PlayerColorCode(player));
        } else {
            player = Gameplay.getInstance().getPlayerTwo();
            textfield.setForeground(Player.PlayerColorCode(player));
        }
        return player;
    }

    /**
     * Sets the value of remaining.
     */
    private void setRemaining() {
        if (counterNext == 1)
            remaining = this.getPlayer().getNewArmies();
        else
            remaining = 0;
    }

    /**
     * Creates a new FieldGUI. It creates a playing field with a map with hitboxes, player statistics, a menu bar, buttons
     * concerning the game play and a textfield which guides the players through the game.
     */
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
                    if (counterNext == 1)
                        setRemaining();
                    if (counterNext == 1 && counterHitbox == 0) {       //einheiten setzen
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
                    if (counterNext == 2 && counterHitbox == 2) {       //angriffsphase, auswahl des gegnerlandes
                        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                            chooseEnemyCountryLeftClick(armyLabel, country);
                        }
                    }
                    if (counterNext == 2 && mouseEvent.getButton() == MouseEvent.BUTTON3
                            && country.equals(selectedCountry2)) {
                        chooseEnemyCountryRightClick();
                    }
                    if (counterNext == 0 && counterHitbox != 0) {       //einheiten neu verteilen
                        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                            redistributionLeftClick(armyLabel, country);
                        }
                    }
                    if (counterNext == 0 && counterHitbox != 0 && mouseEvent.getButton() == MouseEvent.BUTTON3) {
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

        this.frame = new JFrame("Risikospielfeld");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar bar = new JMenuBar();

        JMenu rules = new JMenu("Spielregeln");
        JMenuItem programme = new JMenuItem("Spielplan");
        programme.addActionListener(e -> openPlayingFieldRules());
        rules.add(programme);
        JMenuItem goal = new JMenuItem("Ziel des Spiels");
        goal.addActionListener(e -> openGoalRules());
        rules.add(goal);
        JMenuItem gettingArmies = new JMenuItem("Neue Armeen");
        gettingArmies.addActionListener(e -> openNewArmiesRules());
        rules.add(gettingArmies);
        JMenuItem attack = new JMenuItem("Land befreien");
        attack.addActionListener(e -> openPlayingRules());
        rules.add(attack);
        JMenuItem redistribute = new JMenuItem("Umverteilen");
        redistribute.addActionListener(e -> openRedistributionRules());
        rules.add(redistribute);
        JMenuItem winning = new JMenuItem("Ende des Spiels");
        winning.addActionListener(e -> openEndingRules());
        rules.add(winning);
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
        JLabel playeroneh1 = new JLabel(Gameplay.getInstance().getPlayerOne().getName(), SwingConstants.LEFT);
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
        playeronehr1.setBackground(new java.awt.Color(208, 161, 131));
        playeronehr1.setBounds(20, 78, 170, 2);
        frame.add(playeronehr1);

        JLabel playeronehr2 = new JLabel("");
        playeronehr2.setOpaque(true);
        playeronehr2.setBackground(new java.awt.Color(208, 161, 131));
        playeronehr2.setBounds(20, 100, 170, 1);
        frame.add(playeronehr2);

        JLabel playeronehr3 = new JLabel("");
        playeronehr3.setOpaque(true);
        playeronehr3.setBackground(new java.awt.Color(208, 161, 131));
        playeronehr3.setBounds(20, 121, 170, 1);
        frame.add(playeronehr3);

        JLabel playeronehr4 = new JLabel("");
        playeronehr4.setOpaque(true);
        playeronehr4.setBackground(new java.awt.Color(208, 161, 131));
        playeronehr4.setBounds(20, 142, 170, 1);
        frame.add(playeronehr4);

        //Angaben Spieler2
        JLabel playertwoh1 = new JLabel(Gameplay.getInstance().getPlayerTwo().getName(), SwingConstants.RIGHT);
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
        playertwohr1.setBackground(new java.awt.Color(208, 161, 131));
        playertwohr1.setBounds(795, 78, 170, 2);
        frame.add(playertwohr1);

        JLabel playertwohr2 = new JLabel("");
        playertwohr2.setOpaque(true);
        playertwohr2.setBackground(new java.awt.Color(208, 161, 131));
        playertwohr2.setBounds(795, 100, 170, 1);
        frame.add(playertwohr2);

        JLabel playertwohr3 = new JLabel("");
        playertwohr3.setOpaque(true);
        playertwohr3.setBackground(new java.awt.Color(208, 161, 131));
        playertwohr3.setBounds(795, 121, 170, 1);
        frame.add(playertwohr3);

        JLabel playertwohr4 = new JLabel("");
        playertwohr4.setOpaque(true);
        playertwohr4.setBackground(new java.awt.Color(208, 161, 131));
        playertwohr4.setBounds(795, 142, 170, 1);
        frame.add(playertwohr4);

        //Angaben Kontinente
        JLabel otea = new JLabel("OTEA", SwingConstants.RIGHT);
        otea.setFont(new Font("Sans-Serif", Font.BOLD, 11));
        otea.setForeground(Color.white);
        otea.setBounds(580, 421, 170, 12);
        frame.add(otea);

        JLabel priya = new JLabel("PRIYA", SwingConstants.RIGHT);
        priya.setFont(new Font("Sans-Serif", Font.BOLD, 11));
        priya.setForeground(Color.white);
        priya.setBounds(580, 434, 170, 12);
        frame.add(priya);

        JLabel solva = new JLabel("SOLVA", SwingConstants.RIGHT);
        solva.setFont(new Font("Sans-Serif", Font.BOLD, 11));
        solva.setForeground(Color.white);
        solva.setBounds(580, 447, 170, 12);
        frame.add(solva);

        //Angaben Laender
        Map<String, ArrayList<Integer>> countryNameLabels = new HashMap<>();
        Map<String, ArrayList<Integer>> hitBoxPanels = new HashMap<>();
        Map<String, ArrayList<Integer>> armyCountLabels = new HashMap<>();

        countryNameLabels.put("AMRA", new ArrayList<>(Arrays.asList(475, 35, 70, 15))); // Name
        hitBoxPanels.put("AMRA", new ArrayList<>(Arrays.asList(475, 50, 30, 30))); // Box
        armyCountLabels.put("AMRA", new ArrayList<>(Arrays.asList(487, 85, 70, 20))); // Zahl

        countryNameLabels.put("BITA", new ArrayList<>(Arrays.asList(675, 85, 70, 15)));
        hitBoxPanels.put("BITA", new ArrayList<>(Arrays.asList(675, 100, 30, 30)));
        armyCountLabels.put("BITA", new ArrayList<>(Arrays.asList(643, 98, 70, 20)));

        countryNameLabels.put("CAIA", new ArrayList<>(Arrays.asList(275, 85, 70, 15)));
        hitBoxPanels.put("CAIA", new ArrayList<>(Arrays.asList(275, 100, 30, 30)));
        armyCountLabels.put("CAIA", new ArrayList<>(Arrays.asList(272, 107, 70, 20)));

        countryNameLabels.put("DEMI", new ArrayList<>(Arrays.asList(475, 110, 70, 15)));
        hitBoxPanels.put("DEMI", new ArrayList<>(Arrays.asList(475, 125, 30, 30)));
        armyCountLabels.put("DEMI", new ArrayList<>(Arrays.asList(390, 116, 70, 20)));

        countryNameLabels.put("ESSA", new ArrayList<>(Arrays.asList(550, 185, 70, 15)));
        hitBoxPanels.put("ESSA", new ArrayList<>(Arrays.asList(550, 200, 30, 30)));
        armyCountLabels.put("ESSA", new ArrayList<>(Arrays.asList(555, 199, 70, 20)));

        countryNameLabels.put("FRIA", new ArrayList<>(Arrays.asList(400, 185, 70, 15)));
        hitBoxPanels.put("FRIA", new ArrayList<>(Arrays.asList(400, 200, 30, 30)));
        armyCountLabels.put("FRIA", new ArrayList<>(Arrays.asList(390, 204, 70, 20)));

        countryNameLabels.put("GYDA", new ArrayList<>(Arrays.asList(350, 260, 70, 15)));
        hitBoxPanels.put("GYDA", new ArrayList<>(Arrays.asList(350, 275, 30, 30)));
        armyCountLabels.put("GYDA", new ArrayList<>(Arrays.asList(292, 221, 70, 20)));

        countryNameLabels.put("HELA", new ArrayList<>(Arrays.asList(725, 260, 70, 15)));
        hitBoxPanels.put("HELA", new ArrayList<>(Arrays.asList(725, 275, 30, 30)));
        armyCountLabels.put("HELA", new ArrayList<>(Arrays.asList(680, 243, 70, 20)));

        countryNameLabels.put("IMMA", new ArrayList<>(Arrays.asList(480, 235, 70, 15)));
        hitBoxPanels.put("IMMA", new ArrayList<>(Arrays.asList(480, 250, 30, 30)));
        armyCountLabels.put("IMMA", new ArrayList<>(Arrays.asList(460, 246, 70, 20)));

        countryNameLabels.put("JARI", new ArrayList<>(Arrays.asList(600, 260, 70, 15)));
        hitBoxPanels.put("JARI", new ArrayList<>(Arrays.asList(600, 275, 30, 30)));
        armyCountLabels.put("JARI", new ArrayList<>(Arrays.asList(575, 273, 70, 20)));

        countryNameLabels.put("KILA", new ArrayList<>(Arrays.asList(550, 360, 70, 15)));
        hitBoxPanels.put("KILA", new ArrayList<>(Arrays.asList(550, 375, 30, 30)));
        armyCountLabels.put("KILA", new ArrayList<>(Arrays.asList(545, 383, 70, 20)));

        countryNameLabels.put("LIYA", new ArrayList<>(Arrays.asList(225, 260, 70, 15)));
        hitBoxPanels.put("LIYA", new ArrayList<>(Arrays.asList(225, 275, 30, 30)));
        armyCountLabels.put("LIYA", new ArrayList<>(Arrays.asList(250, 300, 70, 20)));

        countryNameLabels.put("MENA", new ArrayList<>(Arrays.asList(400, 360, 70, 15)));
        hitBoxPanels.put("MENA", new ArrayList<>(Arrays.asList(403, 375, 30, 28)));
        armyCountLabels.put("MENA", new ArrayList<>(Arrays.asList(415, 388, 70, 20)));

        countryNameLabels.put("NEAH", new ArrayList<>(Arrays.asList(275, 360, 70, 15)));
        hitBoxPanels.put("NEAH", new ArrayList<>(Arrays.asList(275, 374, 30, 28)));
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
        next.setBounds(685, 472, 105, 20);
        next.addActionListener(e -> next());
        frame.add(next);

        rollDice = new JButton("Wuerfeln");
        rollDice.setBounds(450, 472, 105, 20);
        rollDice.setFont(new Font("Sans-Serif", Font.PLAIN, 11));
        rollDice.setEnabled(false);
        rollDice.addActionListener(e -> attack());
        frame.add(rollDice);

        check = new JButton("Pruefen");
        check.setBounds(197, 472, 105, 20);
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

    /**
     * Raises the number of armies in a country by 1 and updates the label.
     *
     * @param armyLabel the label that has been left-clicked on
     * @param country   the country that has been left-clicked on
     * @see FieldGUI#deployArmiesRightClick(JLabel armyLabel, Country country)
     */
    private void deployArmiesLeftClick(JLabel armyLabel, Country country) {
        if (country.getColorOfOwnerString().equals(getPlayer().getColor())
                && remaining >= 0) {
            increasedCountries.add(country);
            country.addArmy();
            counter++;
            remaining -= counter;
            armyLabel.setText(Integer.toString(country.getArmiesInCountry()));
            setArmyText(getPlayer());
            if (remaining > 0) {
                textfield.append("Noch " + remaining + " Einheit/en zu verteilen.\n");
            } else {
                textfield.append("\nAlle Einheiten verteilt, klicke 'Weiter' um forzufahren.\n");
                next.setEnabled(true);
                counterHitbox++;
            }
        } else {
            if (!clickedCountries.contains(country)) {
                textfield.append("\nVerteile die Einheiten in den Laendern, die deiner Farbe entsprechen.\n");
                clickedCountries.add(country);
            }
        }
    }

    /**
     * Reduces the number of armies in a country by 1 and updates the label.
     *
     * @param armyLabel the label that has been right-clicked on
     * @param country   the country that has been right-clicked on
     * @see FieldGUI#deployArmiesLeftClick(JLabel armyLabel, Country country)
     */
    private void deployArmiesRightClick(JLabel armyLabel, Country country) {
        if (country.getColorOfOwnerString().equals(getPlayer().getColor())
                && increasedCountries.contains(country)) {
            increasedCountries.remove(country);
            country.loseArmy();
            counter--;
            remaining -= counter;
            armyLabel.setText(Integer.toString(country.getArmiesInCountry()));
            setArmyText(getPlayer());
            if (counterHitbox != 0) {
                counterHitbox--;
                next.setEnabled(false);
            }
            textfield.append("Noch " + remaining + " Einheit/en zu verteilen.\n");
        }
    }

    /**
     * Sets country of the player whose turn it is for an attack.
     *
     * @param armyLabel the label that has been left-clicked in
     * @param country   the country that has been left-clicked on
     * @see FieldGUI#chooseOwnCountryRightClick()
     * @see FieldGUI#chooseEnemyCountryLeftClick(JLabel armyLabel, Country country)
     * @see FieldGUI#chooseEnemyCountryRightClick()
     */
    private void chooseOwnCountryLeftClick(JLabel armyLabel, Country country) {
        if (country.getColorOfOwnerString().equals(getPlayer().getColor())
                && country.getArmiesInCountry() > 1 && hasEnemyNeighbours(country)) {
            selectedCountry1 = country;
            armiesattacking = armyLabel;
            textfield.append("\nDu hast " + country.getCountryName() + " ausgewaehlt, klicke jetzt" +
                    " auf ein benachbartes Land deines Gegners.\n");
            counterHitbox++;
            check.setEnabled(false);
            next.setEnabled(false);
        } else {
            textfield.append("Waehle zuerst ein Land von dir mit mehr als einer Einheit aus." +
                    " Es muss mindestens ein gegnerisches Land als Nachbar haben.\n");
        }
    }

    /**
     * Deselects country that had been chosen for an attack.
     *
     * @see FieldGUI#chooseOwnCountryLeftClick(JLabel armyLabel, Country country)
     * @see FieldGUI#chooseEnemyCountryRightClick()
     * @see FieldGUI#chooseEnemyCountryLeftClick(JLabel armyLabel, Country country)
     */
    private void chooseOwnCountryRightClick() {
        counterHitbox--;
        selectedCountry1 = null;
        textfield.append("Auswahl aufgehoben.\n");
        check.setEnabled(true);
        next.setEnabled(true);
    }

    /**
     * Sets country of the opposing player to attack.
     *
     * @param armyLabel the label that has been left-clicked
     * @param country   the country that has been left-clicked
     * @see FieldGUI#chooseEnemyCountryRightClick()
     * @see FieldGUI#chooseOwnCountryLeftClick(JLabel armyLabel, Country country)
     * @see FieldGUI#chooseOwnCountryRightClick()
     */
    private void chooseEnemyCountryLeftClick(JLabel armyLabel, Country country) {
        if (!country.getColorOfOwnerString().equals(getPlayer().getColor()) //land gehoert gegner
                && country.isNeighbor(selectedCountry1)) {      //land ist nachbar
            selectedCountry2 = country;
            armiesdefending = armyLabel;
            rollDice.setEnabled(true);
            counterHitbox++;
            textfield.append("\nDu hast " + selectedCountry2.getCountryName() + " ausgewaehlt." +
                    " Klicke 'Wuerfeln' um eine Befreiungsaktion zu starten.\n");
        } else {
            textfield.append("Waehle ein Nachbarland deines Gegners aus.\n");
        }
    }

    /**
     * Deselects country that had been chosen to attack.
     *
     * @see FieldGUI#chooseEnemyCountryLeftClick(JLabel armyLabel, Country country)
     * @see FieldGUI#chooseOwnCountryLeftClick(JLabel armyLabel, Country country)
     * @see FieldGUI#chooseOwnCountryRightClick()
     */
    private void chooseEnemyCountryRightClick() {
        if (counterHitbox == 4) {
            rollDice.setEnabled(false);
        }
        counterHitbox--;
        selectedCountry2 = null;
        textfield.append("Auswahl aufgehoben.\n");
    }

    /**
     * Raises the number of armies in a country by 1 and updates the label.
     *
     * @param armyLabel the label that has been left-clicked on
     * @param country   the country that has been left-clicked on
     * @see FieldGUI#redistributionRightClick(JLabel armyLabel, Country country)
     */
    private void redistributionLeftClick(JLabel armyLabel, Country country) {
        if (country.getColorOfOwnerString().equals(getPlayer().getColor())
                && remaining > 0) {
            country.addArmy();
            remaining--;
            armyLabel.setText(Integer.toString(country.getArmiesInCountry()));
            setArmyText(getPlayer());
            if (remaining > 0) {
                textfield.append("Noch " + remaining + " Einheit/en zu verteilen.\n");
            } else {
                textfield.append("\nAlle Einheiten verteilt, klicke 'Weiter' um forzufahren.\n");
                next.setEnabled(true);
            }
        } else if (country.getColorOfOwnerString().equals(getPlayer().getColor())
                && remaining == 0) {
            if (!clickedCountries.contains(country)) {
                textfield.append("Ziehe zuerst mindestens eine Einheit aus einem Land ab.\n");
                clickedCountries.add(country);
            }
        } else {
            if (!clickedCountries.contains(country)) {
                textfield.append("\nVerteile die Einheiten in den Laendern, die deiner Farbe entsprechen.\n");
                clickedCountries.add(country);
            }
        }
    }

    /**
     * Reduces the number of armies in a country by 1 and updates the label.
     *
     * @param armyLabel the label that has been right-clicked on
     * @param country   the country that has been right-clicked on
     * @see FieldGUI#redistributionLeftClick(JLabel armyLabel, Country country)
     */
    private void redistributionRightClick(JLabel armyLabel, Country country) {
        if (country.getColorOfOwnerString().equals(getPlayer().getColor())
                && country.getArmiesInCountry() > 1) {
            if (next.isEnabled())
                next.setEnabled(false);
            country.loseArmy();
            remaining++;
            setArmyText(getPlayer());
            armyLabel.setText(Integer.toString(country.getArmiesInCountry()));
            textfield.append("Noch " + remaining + " Einheit/en zu verteilen.\n");
        } else if (country.getColorOfOwnerString().equals(getPlayer().getColor())
                && country.getArmiesInCountry() == 1) {
            if (!clickedCountries.contains(country)) {
                textfield.append("Dein Land muss mindestens zwei Armeen beinhalten.\n");
                clickedCountries.add(country);
            }
        } else {
            if (!clickedCountries.contains(country)) {
                textfield.append("Du kannst nur Einheiten in den Laendern abziehen, die deiner Farbe entsprechen.\n");
                clickedCountries.add(country);
            }
        }
    }

    /**
     * Checks a country for neighboring countries belonging to the enemy.
     *
     * @param country the country being checked for neighboring countries belonging to the enemy
     * @return true if country has a neighboring country belonging to the enemy; false otherwise
     */
    private boolean hasEnemyNeighbours(Country country) {
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

    /**
     * Sets parameters as needed for the next phase of the game and calls the function explaining the phase up.
     */
    private void next() {
        Gameplay gameplayInstance = Gameplay.getInstance();
        if (counterNext == 0) {
            textfield.setText("");
            counterHitbox = 0;
            clickedCountries.clear();
        }
        counterNext++;
        switch (counterNext) {
            case 1:
                counterPlayer++;
                check.setEnabled(false);
                next.setEnabled(false);
                gameplayInstance.deployArmiesText(this.getPlayer());
                break;
            case 2:
                increasedCountries.clear();
                clickedCountries.clear();
                counter = 0;
                this.setRemaining();
                gameplayInstance.attackphaseText();
                check.setEnabled(true);
                break;
            case 3:
                gameplayInstance.redistributionText();
                check.setEnabled(true);
                counterNext = 0;
                rollDice.setEnabled(false);
                dicePlayerOne.setVisible(false);
                dicePlayerTwo.setVisible(false);
                break;
        }
    }

    /**
     * Simulates an attack and informs the player about the outcome. Depending on which player wins the attack, the
     * information on armies and countries get updated.
     */
    private void attack() {
        check.setEnabled(true);
        Gameplay gameplayInstance = Gameplay.getInstance();
        ImageIcon dicePlayerOne_img, dicePlayerTwo_img;
        Random random = new Random();
        int diceAttacker = random.nextInt(6) + 1;
        int diceDefender = random.nextInt(6) + 1;
        if (player == gameplayInstance.getPlayerOne()) {
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
        textfield.append("\nDu wuerfelst eine " + diceAttacker + "!\nDer Besetzer wuerfelt eine " + diceDefender + "!\n");
        if (diceAttacker > diceDefender) {
            if (selectedCountry2.getArmiesInCountry() > 1) {
                textfield.append("\nDer Besetzer verliert eine Einheit!\n");
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

                if (selectedCountry1.getColorOfOwnerString().equals(gameplayInstance.getPlayerOne().getColor())) {
                    int j = 0;
                    for (int i = 0; i < gameplayInstance.getPlayerTwo().numberOfCountries(); i++) {
                        if (selectedCountry2.getCountryName().equals(gameplayInstance.getPlayerTwo().getCountryName(i))) {
                            j = i;
                        }
                    }
                    gameplayInstance.getPlayerOne().addCountryToList(gameplayInstance.getPlayerTwo().getCountryName(j), gameplayInstance.getPlayerTwo().getCountry(j));
                    gameplayInstance.getPlayerTwo().deleteCountryFromList(j);
                    playeronep2.setText(Integer.toString(gameplayInstance.getPlayerOne().numberOfCountries()));
                    playertwop2.setText(Integer.toString(gameplayInstance.getPlayerTwo().numberOfCountries()));
                    playeronep3.setText(Integer.toString(gameplayInstance.getPlayerOne().numberOfArmies(gameplayInstance.getPlayerOne())));
                    playertwop3.setText(Integer.toString(gameplayInstance.getPlayerTwo().numberOfArmies(gameplayInstance.getPlayerTwo())));
                } else {
                    int j = 0;
                    for (int i = 0; i < gameplayInstance.getPlayerOne().numberOfCountries(); i++) {
                        if (selectedCountry2.getCountryName().equals(gameplayInstance.getPlayerOne().getCountryName(i))) {
                            j = i;
                        }
                    }
                    gameplayInstance.getPlayerTwo().addCountryToList(gameplayInstance.getPlayerOne().getCountryName(j), gameplayInstance.getPlayerOne().getCountry(j));
                    gameplayInstance.getPlayerOne().deleteCountryFromList(j);
                    playeronep2.setText(Integer.toString(gameplayInstance.getPlayerOne().numberOfCountries()));
                    playertwop2.setText(Integer.toString(gameplayInstance.getPlayerTwo().numberOfCountries()));
                }
                playeronep3.setText(Integer.toString(gameplayInstance.getPlayerOne().numberOfArmies(gameplayInstance.getPlayerOne())));
                playertwop3.setText(Integer.toString(gameplayInstance.getPlayerTwo().numberOfArmies(gameplayInstance.getPlayerTwo())));


                textfield.append("\nDu hast " + selectedCountry2.getCountryName() + " erfolgreich befreit!\n");
                if (Mission.testMission(player)) {
                    gameplayInstance.finishedGameText();
                    openWinning(player);
                    return;
                }
            }
        } else {
            textfield.append("\nDu verlierst eine Einheit.\n");
            selectedCountry1.loseArmy();
            armiesattacking.setText(Integer.toString(selectedCountry1.getArmiesInCountry()));
            playeronep3.setText(Integer.toString(gameplayInstance.getPlayerOne().numberOfArmies(gameplayInstance.getPlayerOne())));
            playertwop3.setText(Integer.toString(gameplayInstance.getPlayerTwo().numberOfArmies(gameplayInstance.getPlayerTwo())));
        }
        rollDice.setEnabled(false);
        next.setEnabled(true);
        setArmyText(gameplayInstance.getPlayerOne());
        setArmyText(gameplayInstance.getPlayerTwo());
        counterHitbox = 1;
        textfield.append("\nWaehle erneut zwei Laender oder beende die Befreiungsphase durch einen Klick auf 'Weiter'.\n");
    }

    /**
     * Gets an image of a dice with a certain amount of eyes.
     *
     * @param roll the number of eyes to be displayed on the dice
     * @return the image showing a dice with the right amount of eyes
     */
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

    /**
     * Checks if a play is possible and informs the player of the outcome.
     */
    private void checking() {
        Gameplay gameplayInstance = Gameplay.getInstance();
        switch (counterNext) {
            case 0:
                if (this.getPlayer().getPlayerArmies() == this.getPlayer().numberOfCountries()) {
                    gameplayInstance.redistributionAbortText();
                    check.setEnabled(false);
                } else {
                    gameplayInstance.redistributionContText(this.getPlayer());
                    check.setEnabled(false);
                }
                break;
            case 2:
                if (this.getPlayer().attackPossible()) {
                    gameplayInstance.attackPossibleText();
                    check.setEnabled(false);
                } else {
                    gameplayInstance.attackNotPossibleText();
                    check.setEnabled(false);
                }
                break;
            default:
                check.setEnabled(false);
        }
    }

    /**
     * Creates a panel with a background image.
     *
     * @return the created panel
     */
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

    /**
     * Creates a hit box with a name and number of armies.
     *
     * @param country     the country whose hit box will be created
     * @param nameCoord   the coordinates where the name will be placed
     * @param hitBoxCoord the coordinates where the hit box will be placed
     * @param armyCoord   the coordinates where the number of armies will be placed
     */
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
        hitboxPanel.setBackground(new java.awt.Color(242, 228, 218));
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

    /**
     * Sets UIManager for JOptionPanes.
     */
    private void setUIManager() {
        UIManager.put("OptionPane.background", new java.awt.Color(242, 228, 218));
        UIManager.put("Panel.background", new java.awt.Color(242, 228, 218));
    }

    /**
     * Explains the end of the game.
     */
    private void openEndingRules() {
        this.setUIManager();
        JOptionPane.showMessageDialog(frame,
                "<html>Das Spiel Risiko endet, wenn der erste Spieler seine Mission erfuellt hat.<html>",
                "Ende des Spiels",
                JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Explains the attack phase.
     */
    private void openPlayingRules() {
        this.setUIManager();
        JOptionPane.showMessageDialog(frame,
                "In der zweiten Phase koennen Laender befreit werden. Eine Befreiungsaktion ist moeglich, wenn" +
                        "\n- in dem Land, von dem die Aktion gestartet werden soll, mehr als eine Armee stationiert ist und" +
                        "\n- das zu befreiende Land vom Gegner besetzt ist und ein Nachbarland vom Ausgangsland ist." +
                        "\nZuerst wird das Ausgangsland mit Linksklick auf die Zahl der Armeen ausgewaehlt. Das zu befreiende" +
                        "\nLand wird auf die gleiche Weise ausgewaehlt. Mit Rechtsklick kann die jeweilige Auswahl" +
                        "\nrueckgaengig gemacht werden." +
                        "\nSind die beiden Laender ausgewaehlt, muss mit dem 'Wuerfeln'-Button gewuerfelt werden." +
                        "\nEs gewinnt der Spieler mit dem hoeheren Ergebnis; bei Gleichstand gewinnt der Besetzer." +
                        "\nVon den Armeen des Verlierers wird eine Einheit abgezogen." +
                        "\nVerliert die Besatzungsmacht alle Armeen in einem Land, zieht der Befreier mit einer Armee ein." +
                        "\nUeber den 'Pruefen'-Button kann getestet werden, ob eine Befreiungsaktion moeglich ist." +
                        "\nDiese Phase ist freiwillig und kann uebersprungen werden.",
                "Land befreien",
                JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Explains the goal of the game.
     */
    private void openGoalRules() {
        this.setUIManager();
        JOptionPane.showMessageDialog(frame,
                "<html>Ziel des Spieles ist es, deine Mission vor deinem Gegner zu erfuellen.<html>",
                "Ziel des Spieles",
                JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Explains the redistribution of armies.
     */
    private void openRedistributionRules() {
        this.setUIManager();
        JOptionPane.showMessageDialog(frame,
                "Am Ende jeden Spielzugs hat jeder Spieler die Moeglichkeit, seine Armeen umzusetzen." +
                        "\nMit Rechtsklick wird eine Armee aus einem Land abgezogen, in dem zwei oder mehr Armeen" +
                        "\nstationiert sind. Mit Linksklick wird die Armee in ein anderes Land gesetzt." +
                        "\nUeber den 'Pruefen'-Button kann getestet werden, ob ein Umsetzen der Armeen moeglich ist." +
                        "\nDiese Phase ist freiwillig und kann uebersprungen werden.",
                "Armeen umverteilen",
                JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Explains the distribution of new armies.
     */
    private void openNewArmiesRules() {
        this.setUIManager();
        JOptionPane.showMessageDialog(frame,
                "Am Anfang jeden Spielzugs bekommt jeder Spieler neue Armeen, die verteilt werden muessen." +
                        "\nJeder Spieler bekommt (Anzahl der besetzten Laender / 3) Armeen, mindestens aber 2." +
                        "\nBesitzt ein Spieler einen kompletten Kontinent, bekommt er Bonusarmeen:" +
                        "\nOtea: 1 Bonusarmee\nPriya: 2 Bonusarmeen\nSolva: 2 Bonusarmeen" +
                        "\nDie Verteilung der Armeen bleibt dem Spieler ueberlassen. Mit Linksklick auf die Zahl der Armeen" +
                        "\nin einem Land wird diesem eine Armee hinzugefuegt. Mit Rechtsklick wird eine Armee abgezogen.",
                "Neue Armeen",
                JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Explains the structure of the playing field.
     */
    private void openPlayingFieldRules() {
        this.setUIManager();
        JOptionPane.showMessageDialog(frame,
                "Auf dem Spielplan sind 7 Laender abgebildet. Diese sind in der Farbe eines der" +
                        "\ndrei Kontinente eingefaerbt. Die Legende ist in der rechten unteren Ecke." +
                        "\nBenachbarte Laender werden durch eine braune, gestrichelte Linie ausgewiesen." +
                        "\nDie Namen der Laender stehen auf den Laendern. Unter dem Namen steht die Anzahl" +
                        "\nder Armeen im Land. Die Zahl ist in der Farbe des Spielers, der das Land besitzt." +
                        "\nFuer die Aktionen Armeen setzen und umverteilen sowie Laender auswaehlen muss diese" +
                        "\nZahl angeklickt werden.",
                "Aufbau des Spielplans",
                JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Ends the game and starts a new game if the players want.
     */
    private void openSelection() {
        this.setUIManager();
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
                Gameplay.getInstance().getPlayerOne().emptyAll();
                Gameplay.getInstance().getPlayerTwo().emptyAll();
                frame.dispose();
                new PlayersGUI();
                break;
            case 1:
                setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        }
    }

    /**
     * Ends the program if the players want.
     */
    private void endProgram() {
        this.setUIManager();
        Object[] options = {"Ja, wirklich", "Ne, doch nicht"};
        int n = JOptionPane.showOptionDialog(frame,
                "Willst du wirklich das Programm beenden?",
                "Programm beenden",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        switch (n) {
            case 0:
                System.exit(0);
            case 1:
                setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        }
    }

    /**
     * Informs the player of the end of the game and either starts a new game or ends the program.
     *
     * @param player the player who won the game
     * @see FieldGUI#getWinningMessage(Player player)
     */
    private void openWinning(Player player) {
        ImageIcon icon = new ImageIcon("material/winning.png");
        this.setUIManager();
        Object[] options = {"Ja, wirklich", "Ne, doch nicht"};
        int n = JOptionPane.showOptionDialog(frame,
                getWinningMessage(player),
                "Spiel gewonnen und beendet",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                icon,
                options,
                options[0]);
        switch (n) {
            case 0:
                Gameplay.getInstance().getPlayerOne().emptyAll();
                Gameplay.getInstance().getPlayerTwo().emptyAll();
                frame.dispose();
                new PlayersGUI();
                break;
            case 1:
                System.exit(0);
        }
    }

    /**
     * Gets the message that a player has won.
     *
     * @param player the player who won the game
     * @return the message that the player has won
     * @see FieldGUI#openWinning(Player player)
     */
    private String getWinningMessage(Player player) {
        return "Herzlichen Glueckwunsch, Spieler "
                + player.getName()
                + "!\nDu hast das Spiel gewonnen."
                + "\nWollt ihr noch eine Runde spielen?";
    }

    /**
     * Searches for a country.
     *
     * @param name the name of the country that needs to be found
     * @return the found country; null otherwise
     */
    private Country findCountry(String name) {
        for (Country c : Gameplay.getInstance().getCountries()) {
            if (c.getCountryName().toUpperCase().equals(name.toUpperCase())) {
                return c;
            }
        }
        return null;
    }

    /**
     * Gets the mission description over several lines.
     *
     * @param playerNow the player whose mission is to be displayed
     * @return the multiline mission description of the player
     */
    private static String breakDescription(Player playerNow) {
        return "<html>" + Mission.getMissionDescription(playerNow.getPlayerMission()) + "<html>";
    }

    /**
     * Sets the displayed number of armies of a player.
     *
     * @param player the player whose turn it is
     */
    private void setArmyText(Player player) {
        Gameplay gameplayInstance = Gameplay.getInstance();
        if (player.getColor().equals(gameplayInstance.getPlayerOne().getColor())) {
            playeronep3.setText(Integer.toString(gameplayInstance.getPlayerOne().numberOfArmies(gameplayInstance.getPlayerOne())));
        } else {
            playertwop3.setText(Integer.toString(gameplayInstance.getPlayerTwo().numberOfArmies(gameplayInstance.getPlayerTwo())));
        }

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
    }
}