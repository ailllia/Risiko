package risikopackage;

import java.util.List;

public class Player {
    private String color;
    private String playerMission;
    private int playerArmys;
    private int playernew;
    private List<String> countriesName;

    public Player(String pcolor, String mission, int armys, int newarmys) {
        color = pcolor;
        playerMission = mission;
        playerArmys = armys;
        playernew = newarmys;
    }
    
	public boolean continentComplete(Continent continent) {
		if (continent.completeContinent(countriesName)) {
			return true;
		} else {
			return false;
		}
	}
    
	@Override
	public String toString() {
		return color + playerMission + playerArmys + playernew;
	}
}
