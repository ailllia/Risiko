package risikopackage;

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
}
