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

    private Gameplay() {
    }

    public static Gameplay getInstance() {
        if (instance == null) {
            instance = new Gameplay();
        }
        return instance;
    }

    public void initialising() {
        Random chance = new Random();
        spreadCountries(playerOne, playerTwo, countries, chance);
        paintCountries(playerOne, playerTwo, countries);
        chooseMission(playerOne, playerTwo, missions, chance);
    }

    public void welcomeText() { // Erste Textausgaben
        FieldGUI.textfield.append("Willkommen bei einer Runde Risiko!\nJedem Spieler wurden 7 Laender zugeteilt." +
                " Die Anzahl der Armeen steht in der Farbe des Spielers auf dem Land.\nIn den Spielregeln werden" +
                " die Phasen eines Spielzuges erklärt.\nKlicke auf 'Weiter' um fortzufahren.\n");
    }

    public void deployArmiesText(Player player) {
        FieldGUI.next.setEnabled(false);
        FieldGUI.textfield.append("Du bist am Zug, " + player.getColor() + ".\n\nVerteile " + player.getNewArmies() +
                " neue Armeen auf deine Laender.\nKlicke dafür auf die Zahlen in den Laendern.\nMit Linksklick" +
                " fuegst du einem Land eine Armee hinzu.\nMit Rechtsklick ziehst du eine verteilte Armee wieder ab.\n");
    }

    public void attackphaseText() {
        FieldGUI.textfield.append("\nBefreie Laender, die von deinem Gegner besetzt sind.\nWaehle dazu ein Land aus," +
                " das in deinem Besitz ist und in dem mindestens 2 Armeen stationiert sind. Waehle danach\n ein gegnerisches" +
                " Nachbarland aus.\nMit Rechtsklick kannst du deine jeweilige Auswahl aufheben.\nKlicke auf 'Wuerfeln' um" +
                " den Befreiungsversuch zu starten.\nWenn du nicht weisst, ob du einen Befreiungsversuch starten kannst," +
                " klicke 'Pruefen'.\nKlicke auf 'Weiter', um zur nächsten Spielphase zu gelangen.\n");
    }

    public void attackPossibleText() {
        FieldGUI.textfield.append("\nEs ist ein Angriff möglich. Starte einen Befreiungsversuch, oder klicke auf" +
                " 'Weiter', um zur nächsten Spielphase zu gelangen.\n");
    }

    public void attackNotPossibleText() {
        FieldGUI.textfield.append("\nEs ist kein Angriff möglich. Klicke auf 'Weiter', um zur nächsten Spielphase zu" +
                " gelangen.\n");
    }

    public void redistributionText() {
        FieldGUI.textfield.append("\nVersetze deine Armeen.\nZiehe dafür" +
                " zuerst mit Rechtsklick mindestens eine Einheit aus einem deiner Laender ab. Mit Linksklick fuegst" +
                " du\ndie abgezogenen Einheiten einem Land hinzu.\nWenn du nicht weisst, ob du Einheiten verteilen" +
                " kannst, klicke 'Pruefen'.\nKlicke auf 'Weiter', um deinen Zug zu beenden.\n");
    }

    public void redistributionAbortText() {
        FieldGUI.textfield.append("\nDu kannst keine Einheiten neuverteilen. Beende deinen Zug, indem du auf 'Weiter' klickst.\n");
    }

    public void redistributionContText(Player player) {
        FieldGUI.textfield.append("\nDu kannst " + player.getArmiesAvailableToMove() + " Einheit/en neu verteilen.\n");
    }

    public void finishedGameText() {
        FieldGUI.textfield.append("HERZLICHEN GLUECKWUNSCH! Du hast deine Mission vor deinem Gegner \n" +
                "erfuellt und damit dieses Spiel gewonnen!\nStarte eine neue Runde ueber 'Spiel abbrechen' ->" +
                " 'neues Spiel starten'.");
    }

    // verteilt Laender an die beiden Spieler
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

    // faerbt beim Spielaufbau die Laender ein und setzt in jedes eine Armee
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

    //teilt Missionen zu
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
/*
    public static void addArmiesInCountry(Country country, Player player) {
        if (player.armiesAvailableToMove()) {
            country.addArmy();
        } else {
            //Ausgabe, dass keine Armeen bewegt werden koennen
        }
    }
*/
/*
    public static void loseArmyInCountry(Country country, Player player) {
        if (player.armiesAvailableToWithdraw(country)) {
            country.loseArmy();
        } else {
            //Ausgabe, dass aus diesem Land keine Armee abgezogen werden kann
        }
    }
*/
    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public void setContinents(List<Continent> continents) {
        this.continents = continents;
    }

    public void setMissions(List<Mission> missions) {
        this.missions = missions;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public List<Continent> getContinents() {
        return continents;
    }

    public List<Mission> getMissions() {
        return missions;
    }
}
