package risikopackage;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String color;
    private String playerMission;
    private int playerArmys;
    private int playernew;
    private List<String> countryNames;

    public List<String> getCountryNames() {
        return countryNames;
    }
    
    public String getCountryName(int i) {
        return countryNames.get(i);
    }

    public void addCountryToList(String countryName) {
        this.countryNames.add(countryName);
    }

    public Player(String pcolor) {
        color = pcolor;
        this.countryNames = new ArrayList<>();
    }

    public boolean continentComplete(Continent continent) {
        return (continent.completeContinent(countryNames));
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return color + playerMission + playerArmys + playernew;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    public static Color PlayerColorCode(Player player) 
    {
    	Color color;
        if (player.getColor().equals("blau")) {              //switch?
            color = new java.awt.Color(0, 0, 255);
        } else if (player.getColor().equals("rot")) {
            color = new java.awt.Color(255, 0, 0);
        } else if (player.getColor().equals("lila")) {
            color = new java.awt.Color(127, 0, 127);
        } else if (player.getColor().equals("pink")) {
            color = new java.awt.Color(255, 0, 255);
        } else if (player.getColor().equals("grau")) {
            color = new java.awt.Color(76, 76, 76);
        } else {
            color = new java.awt.Color(0, 0, 0);
        }
        return color;
    }
}
