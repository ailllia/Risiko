package risikopackage;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

public class Gameplay extends JFrame {
    // hier werden die spielzuege in einzelnen funktionen aufgerufen
    private static Gameplay instance;
    private Player playerOne = new Player(null, null);
    private Player playerTwo = new Player(null, null);
    private List<Country> countries = new ArrayList<>();
    private List<Continent> continents = new ArrayList<>();
    private List<Mission> missions = new ArrayList<>();

    /**
     * Creates a new Gameplay.
     */
    private Gameplay() {
    }

    /**
     * Gets the Gameplay. Initialises a new Gameplay if value of instance is null
     *
     * @return instance
     */
    public static Gameplay getInstance() {
        if (instance == null) {
            instance = new Gameplay();
        }
        return instance;
    }

    /**
     * Initialises the game.
     */
    public void initialising() {
        Random chance = new Random();
        spreadCountries(chance);
        paintCountries(playerOne);
        paintCountries(playerTwo);
        chooseMission(chance);
    }

    /**
     * Welcomes the players to a round of Risk.
     */
    public void welcomeText() {
        FieldGUI.textfield.append("Willkommen bei einer Runde Risiko!\nJedem Spieler wurden 7 Laender zugeteilt." +
                " Die Anzahl der Armeen steht in der Farbe des Spielers auf dem Land.\nIn den Spielregeln werden" +
                " die Phasen eines Spielzuges erklaert.\nKlicke auf 'Weiter' um fortzufahren.\n");
    }

    /**
     * Informs the player that they have to deploy armies.
     *
     * @param player the player whose turn it is
     */
    public void deployArmiesText(Player player) {
        FieldGUI.next.setEnabled(false);
        FieldGUI.textfield.append("Du bist am Zug, " + player.getName() + ".\n\n   Verteile " + player.getNewArmies() +
                " neue Armeen auf deine Laender.\nKlicke dafuer auf die Zahlen in den Laendern.\nMit Linksklick" +
                " fuegst du einem Land eine Armee hinzu.\nMit Rechtsklick ziehst du eine verteilte Armee wieder ab.\n\n");
    }

    /**
     * Informs the player that they can attack other countries if they want.
     */
    public void attackphaseText() {
        FieldGUI.textfield.append("\n   Befreie Laender, die von deinem Gegner besetzt sind.\nWaehle dazu ein Land aus," +
                " das in deinem Besitz ist und in dem mindestens 2 Armeen stationiert sind. Waehle danach\nein gegnerisches" +
                " Nachbarland aus.\nMit Rechtsklick kannst du deine jeweilige Auswahl aufheben.\nKlicke auf 'Wuerfeln' um" +
                " den Befreiungsversuch zu starten.\nWenn du nicht weisst, ob du einen Befreiungsversuch starten kannst," +
                " klicke 'Pruefen'.\nKlicke auf 'Weiter', um zur naechsten Spielphase zu gelangen.\n");
    }

    /**
     * Informs the player that an attack is possible.
     */
    public void attackPossibleText() {
        FieldGUI.textfield.append("\nEs ist ein Angriff moeglich. Starte einen Befreiungsversuch, oder klicke auf" +
                " 'Weiter', um zur naechsten Spielphase zu gelangen.\n");
    }

    /**
     * Informs the player that an attack is impossible.
     */
    public void attackNotPossibleText() {
        FieldGUI.textfield.append("\nEs ist kein Angriff moeglich. Klicke auf 'Weiter', um zur naechsten Spielphase zu" +
                " gelangen.\n");
    }

    /**
     * Informs the player that they can redistribute armies if they want.
     */
    public void redistributionText() {
        FieldGUI.textfield.append("\n   Versetze deine Armeen.\nZiehe dafuer" +
                " zuerst mit Rechtsklick mindestens eine Einheit aus einem deiner Laender ab. Mit Linksklick fuegst" +
                " du\ndie abgezogenen Einheiten einem Land hinzu.\nWenn du nicht weisst, ob du Einheiten verteilen" +
                " kannst, klicke 'Pruefen'.\nKlicke auf 'Weiter', um deinen Zug zu beenden.\n\n");
    }

    /**
     * Informs the player that they have no armies they can redistribute.
     */
    public void redistributionAbortText() {
        FieldGUI.textfield.append("\nDu kannst keine Einheiten neuverteilen. Beende deinen Zug, indem du auf 'Weiter' klickst.\n");
    }

    /**
     * Informs the player that they have armies they can redistribute.
     *
     * @param player the player whose turn it is
     */
    public void redistributionContText(Player player) {
        FieldGUI.textfield.append("\nDu kannst " + player.getArmiesAvailableToMove() + " Einheit/en neu verteilen.\n");
    }

    /**
     * Informs the player that they completed the mission and signifies the end of the game.
     */
    public void finishedGameText() {
        FieldGUI.textfield.append("\nHERZLICHEN GLUECKWUNSCH! Du hast deine Mission vor deinem Gegner \n" +
                "erfuellt und damit dieses Spiel gewonnen!");
    }

    /**
     * Randomly distributes seven countries to the players.
     *
     * @param chance the random generator to determine the positions of the seven first countries to be distributed in countries
     */
    private void spreadCountries(Random chance) {
        List<Country> countriesCopy = new ArrayList<>(countries);
        int j = 13;

        for (int i = 0; i < 7; i++) {
            int k = chance.nextInt(j);
            playerOne.addCountryToList(countriesCopy.get(k).getCountryName(), countriesCopy.get(k));
            countriesCopy.remove(k);
            j -= 1;
        }

        for (int i = 0; i < 7; i++) {
            playerTwo.addCountryToList(countriesCopy.get(i).getCountryName(), countriesCopy.get(i));
        }
    }

    /**
     * Sets armies and the owner of countries.
     *
     * @param player the player whose turn it is
     */
    private void paintCountries(Player player) {
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 7; j++) {
                if (player.getCountryName(j).equals(countries.get(i).getCountryName())) {
                    countries.get(i).setColorOfOwnerCode(Player.PlayerColorCode(player));
                    countries.get(i).setColorOfOwnerString(player.getColor());
                    countries.get(i).setArmies();
                }
            }
        }
    }

    /**
     * Randomly distributes a mission to the two players.
     *
     * @param chance the random generator to determine the position of the chosen mission in missions
     */
    private void chooseMission(Random chance) {
        List<Mission> missionsCopy = new ArrayList<>(missions);
        int j = 5;
        int k = chance.nextInt(j);
        playerOne.setPlayerMission(missionsCopy.get(k).getMissionTitle());
        missionsCopy.remove(k);
        j -= 1;
        k = chance.nextInt(j);
        playerTwo.setPlayerMission(missionsCopy.get(k).getMissionTitle());
    }

    /**
     * Gets the first player.
     *
     * @return playerOne
     */
    public Player getPlayerOne() {
        return playerOne;
    }

    /**
     * Gets the second player.
     *
     * @return playerTwo
     */
    public Player getPlayerTwo() {
        return playerTwo;
    }

    /**
     * Gets the list containing countries.
     *
     * @return countries
     */
    public List<Country> getCountries() {
        return countries;
    }

    /**
     * Gets the list containing continents.
     *
     * @return continents
     */
    public List<Continent> getContinents() {
        return continents;
    }

    /**
     * Gets the list containing missions.
     *
     * @return missions
     */
    public List<Mission> getMissions() {
        return missions;
    }
}
