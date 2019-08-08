package risikopackage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Gameplay extends JFrame {
    // hier werden die spielzuege in einzelnen funktionen aufgerufen
    boolean continuing = false; // wird duch klicken des buttons "weiter" auf true gesetzt sodass
    // die n�chste funktion aufgerufen wird

    public Gameplay() {
    }

    public static void initialising() {
        Random chance = new Random();
        spreadCountries(Main.playerOne, Main.playerTwo, Main.countries, chance);
        paintCountries(Main.playerOne, Main.playerTwo, Main.countries);
        chooseMission(Main.playerOne, Main.playerTwo, Main.missions, chance);
    }

    public static void welcome() { // Erste Textausgaben
        FieldGUI.textfield.append("Willkommen bei einer Runde Risiko!\nDie 14 Laender wurden bereits unter " +
                "den Spielern aufgeteilt. Klickt auf 'Weiter' um fortzufahren.\n");
    }

    public void deployArmies1(Player player) {
        FieldGUI.next.setEnabled(false);
        FieldGUI.textfield.append("\n" + player.getColor() + ": Verteile jetzt " + player.getNewArmies() + " neue Armeen auf deine " +
                "Laender.\nLinksklicke dazu auf das Land, deren Einheitenzahl du erhoehen willst. Achtung: Du kannst " +
                "deine Wahl nicht rueckgaengig machen!\n");

    }

    public void attackphase1(Player player) {
        FieldGUI.textfield.append("\n" + player.getColor() + ": Du kannst nun von deinem Gegner besetzte Laender befreien.\nWaehle dazu " +
                "ein Land in deinem Besitz, das mindestens 2 Armeen besitzt, mit Linksklick aus und anschließend" +
                "ein Land deines Gegners\nauf die gleiche Weise. Klicke auf 'Wuerfeln' um deinen Zug zu machen." +
                "Wenn du kein weiteres Land befreien moechtest, klicke auf 'Weiter'.\n");
    }

    public void redistribution1(Player player) {
        FieldGUI.textfield.append("\n" + player.getColor() + ": Nun kannst du noch deine Einheiten neuverteilen, wenn du das willst." + " Klicke dafür auf 'Neuverteilen'." +
                "\nKlicke auf 'Weiter' um deinen Zug zu beenden.\n");
    }

    public void redistributionNext(Player player) {
        FieldGUI.textfield.append("Verteile jetzt " + player.getArmiesAvailableToMove() + " Armeen auf deine Länder."
                + "\nMit Linksklick fuegst du eine Einheit hinzu; mit Rechtsklick ziehst du eine Einheit ab."
                + "\nUm deine Wahl rueckgaenig zu machen und neu zu verteilen, klicke auf 'Rueckgaengig'. Achtung: Dies setzt auch deine bereits"
                + " neuverteilten Armeen zurueck.\n");
    }

    // verteilt Laender an die beiden Spieler
    private static void spreadCountries(Player playerOne, Player playerTwo, List<Country> countries, Random chance) {
        List<Country> countriesCopy = new ArrayList<>(countries);
        int j = 13;

        for (int i = 0; i < 7; i++) {
            int k = chance.nextInt(j);
            playerOne.addCountryToList(countriesCopy.get(k).getCountryName(k));
            countriesCopy.remove(k);
            j -= 1;
        }

        for (int i = 0; i < 7; i++) {
            playerTwo.addCountryToList(countriesCopy.get(i).getCountryName(i));
        }
    }

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
            //Ausgabe, dass keine Armeen bewegt werden k�nnen
        }
    }

    public static void loseArmyInCountry(Country country, Player player) {
        if (player.armiesAvailableToWithdraw(country)) {
            country.loseArmy();
        } else {
            //Ausgabe, dass aus diesem Land keine Armee abgezogen werden kann
        }
    }
}
