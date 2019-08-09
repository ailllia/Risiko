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

    public void welcome() { // Erste Textausgaben
        FieldGUI.textfield.append("Willkommen bei einer Runde Risiko!\nDie 14 Laender wurden bereits unter " +
                "den Spielern aufgeteilt. Klickt auf 'Weiter' um fortzufahren.\n");
    }

    public void deployArmies(Player player) {
        FieldGUI.next.setEnabled(false);
        FieldGUI.textfield.append("\n" + player.getColor() + ": Verteile jetzt " + player.getNewArmies() +
                " neue Armeen auf deine Laender.\nLinksklicke dazu auf das Land, deren Einheitenzahl du " +
                "erhoehen willst.\nMit Rechtsklick kannst du eine verteilte Armee wieder abziehen und neu setzen.\n");
    }

    public void attackphase(Player player) {
        FieldGUI.textfield.append("\n" + player.getColor() + ": Du kannst nun von deinem Gegner besetzte Laender " +
                "befreien.\nWaehle dazu ein Land in deinem Besitz, das mindestens 2 Armeen besitzt, mit Linksklick " +
                "aus und anschliessend ein Land deines Gegners\nauf die gleiche Weise. Klicke auf 'Wuerfeln' um " +
                "deinen Zug zu machen. Wenn du kein weiteres Land befreien moechtest, klicke auf 'Weiter'.\n" +
                "Mit Rechtsklick kannst du deine Laenderwahl aufheben.\n");
    }

    public void redistribution(Player player) {
        FieldGUI.textfield.append("\n" + player.getColor() + ": Nun kannst du noch deine Einheiten neuverteilen, " +
                "wenn du das willst." + " Klicke dafuer auf 'Neuverteilen'." +
                "\nKlicke auf 'Weiter' um deinen Zug zu beenden.\n");
    }

    public void redistributionAbort(Player player) {
        FieldGUI.textfield.append("\n" + player.getColor() + ": Du kannst keine Einheiten neuverteilen. Beende deinen Zug.\n");
    }

    public void missionstate1(Player player) {
        if (Mission.testMission(player)) {
            FieldGUI.textfield.append("\n" + player.getColor() + ": Du hast deine Mission erfuellt. Herzlichen Glückwunsch zum Sieg.\n Das Spiel ist damit beendet\n.");
        } else {
            FieldGUI.textfield.append("\n" + player.getColor() + ": Du hast deine Mission noch nicht erfuellt.\n");
        }
    }

    public void redistributionNext(int armies) {
        FieldGUI.next.setEnabled(false);
        FieldGUI.textfield.append("Verteile jetzt " + armies + " Armeen auf deinen Laendern um."
                + "\nMit Linksklick fuegst du eine Einheit hinzu; mit Rechtsklick ziehst du eine Einheit ab."
                + "Um deine Wahl rueckgaenig zu machen \nund neu zu verteilen, klicke auf 'Rueckgaengig'. \nAchtung: " +
                "Dies setzt auch deine bereits neuverteilten Armeen zurueck.\n");
    }

    public void redistributionDel(int armies) {
        FieldGUI.textfield.append("Du hast deine Umverteilung rueckgaengig gemacht. Verteile " + armies + " Armeen auf deine Laender.\n");
    }

    public void endGameRound() {
        FieldGUI.textfield.append("HERZLICHEN GLUECKWUNSCH! Du hast deine Mission vor deinem Gegner \n" +
                "erfuellt und damit dieses Spiel gewonnen!\nUeber 'Spiel abbrechen' im Menue oben links kann eine" +
                " neue Runde gestartet werden.");
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

    public static void addArmiesInCountry(Country country, Player player) {
        if (player.armiesAvailableToMove()) {
            country.addArmy();
        } else {
            //Ausgabe, dass keine Armeen bewegt werden koennen
        }
    }

    public static void loseArmyInCountry(Country country, Player player) {
        if (player.armiesAvailableToWithdraw(country)) {
            country.loseArmy();
        } else {
            //Ausgabe, dass aus diesem Land keine Armee abgezogen werden kann
        }
    }

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
