package risikopackage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Gameplay extends JFrame {
    // hier werden die spielzuege in einzelnen funktionen aufgerufen
    private static Gameplay instance;
    private Player playerOne = new Player(null);
    private Player playerTwo = new Player(null);
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
        spreadCountries(playerOne, playerTwo, countries, chance);
        paintCountries(playerOne, playerTwo, countries);
        chooseMission(playerOne, playerTwo, missions, chance);
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
     * Informs the player that he has to deploy armies.
     * @param player the player whose turn it is
     */
    public void deployArmiesText(Player player) {
        FieldGUI.next.setEnabled(false);
        FieldGUI.textfield.append("Du bist am Zug, " + player.getColor() + ".\n\n\tVerteile " + player.getNewArmies() +
                " neue Armeen auf deine Laender.\nKlicke dafuer auf die Zahlen in den Laendern.\nMit Linksklick" +
                " fuegst du einem Land eine Armee hinzu.\nMit Rechtsklick ziehst du eine verteilte Armee wieder ab.\n");
    }

    /**
     * Informs the player that he can attack other countries if he wants.
     */
    public void attackphaseText() {
        FieldGUI.textfield.append("\n\tBefreie Laender, die von deinem Gegner besetzt sind.\nWaehle dazu ein Land aus," +
                " das in deinem Besitz ist und in dem mindestens 2 Armeen stationiert sind. Waehle danach\n ein gegnerisches" +
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
     * Informs the player that he can redistribute armies if he wants.
     */
    public void redistributionText() {
        FieldGUI.textfield.append("\n\tVersetze deine Armeen.\nZiehe dafuer" +
                " zuerst mit Rechtsklick mindestens eine Einheit aus einem deiner Laender ab. Mit Linksklick fuegst" +
                " du\ndie abgezogenen Einheiten einem Land hinzu.\nWenn du nicht weisst, ob du Einheiten verteilen" +
                " kannst, klicke 'Pruefen'.\nKlicke auf 'Weiter', um deinen Zug zu beenden.\n");
    }

    /**
     * Appends a message to the textfield that no armies can be redistributed.
     * Informs the player that he has no armies to be redistributed.
     */
    public void redistributionAbortText() {
        FieldGUI.textfield.append("\nDu kannst keine Einheiten neuverteilen. Beende deinen Zug, indem du auf 'Weiter' klickst.\n");
    }

    /**
     * Appends a message to the textfield that the player can redistribute armies.
     * Informs the player that he has armies to be redistributed.
     * @param player the player whose turn it is
     */
    public void redistributionContText(Player player) {
        FieldGUI.textfield.append("\nDu kannst " + player.getArmiesAvailableToMove() + " Einheit/en neu verteilen.\n");
    }

    /**
     * Appends a congratulatory message to the textfield and signifies the end of the game.
     */
    public void finishedGameText() {
        FieldGUI.textfield.append("HERZLICHEN GLUECKWUNSCH! Du hast deine Mission vor deinem Gegner \n" +
                "erfuellt und damit dieses Spiel gewonnen!");
    }

    /**
     * Randomly distributes seven countries to the players
     * @param playerOne the first player to get seven countries
     * @param playerTwo the second player to get seven countries
     * @param countries the list with countries to be distributed to the players
     * @param chance the random generator to determine the positions of the seven first countries to be distributed in countries
     */
    private static void spreadCountries(Player playerOne, Player playerTwo, List<Country> countries, Random chance) {
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
    
    /* Testfall: Spieler 1 hat zu Beginn bereits Kontinent Otea
    private static void spreadCountries(Player playerOne, Player playerTwo, List<Country> countries, Random chance) {
        List<Country> countriesCopy = new ArrayList<>(countries);
        int j = 13;
        for (int i = 1; i < 8; i++) {
            int k = i;
            playerOne.addCountryToList(countries.get(k).getCountryName(), countries.get(k));
            countriesCopy.remove(k);
            j -= 1;
        }
        for (int i = 0; i < 14; i++) {
            playerTwo.addCountryToList(countries.get(i).getCountryName(), countries.get(i));
            if(i == 0) i = 7;
        }
    }
    */

    /**
     * Sets armies and the owner of countries.
     * @param playerOne
     * @param playerTwo
     * @param countries the list of countries which contains the countries of the player
     */
    private static void paintCountries(Player playerOne, Player playerTwo, List<Country> countries) {
        Color rgbCodesOne = Player.PlayerColorCode(playerOne);
        Color rgbCodesTwo = Player.PlayerColorCode(playerTwo);
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 7; j++) {
                if (playerOne.getCountryName(j).equals(countries.get(i).getCountryName())) {
                    countries.get(i).setColorOfOwnerCode(rgbCodesOne);
                    countries.get(i).setColorOfOwnerString(playerOne.getColor());
                    countries.get(i).setArmies();
                }
            }
        }
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 7; j++) {
                if (playerTwo.getCountryName(j).equals(countries.get(i).getCountryName())) {
                    countries.get(i).setColorOfOwnerCode(rgbCodesTwo);
                    countries.get(i).setColorOfOwnerString(playerTwo.getColor());
                    countries.get(i).setArmies();
                }
            }
        }
    }

    /**
     * Randomly distributes a mission to the two players.
     * @param playerOne the first player to get a mission
     * @param playerTwo the second player to get a mission
     * @param missions the list with missions from which the chosen missions will come from
     * @param chance the random generator to determine the position of the chosen mission in missions
     */
    private static void chooseMission(Player playerOne, Player playerTwo, List<Mission> missions, Random chance) {
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
     * @return playerOne
     */
    public Player getPlayerOne() {
        return playerOne;
    }

    /**
     * Gets the second player.
     * @return playerTwo
     */
    public Player getPlayerTwo() {
        return playerTwo;
    }

    /**
     * Gets the list containing countries
     * @return countries
     */
    public List<Country> getCountries() {
        return countries;
    }

    /**
     * Gets the list containing continents
     * @return continents
     */
    public List<Continent> getContinents() {
        return continents;
    }

    /**
     * Gets the list containing missions
     * @return missions
     */
    public List<Mission> getMissions() {
        return missions;
    }
}
