package risikopackage;

import java.util.List;

public class Player {
    private String color;
    private String playerMission;
    private int playerArmys;
    private int playernew;
    private List<String> countriesName;

    public Player(String pcolor) {
        color = pcolor;
    }

    public boolean continentComplete(Continent continent) {
        return (continent.completeContinent(countriesName));
    }

    @Override
    public String toString() {
        return color + playerMission + playerArmys + playernew;
    }
}
