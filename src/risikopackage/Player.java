package risikopackage;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String color;
    private String playerMission;
    private int playerArmys;
    private int playernew;
    private int availableArmies;
    private List<String> occupiedCountriesNames;

    public List<String> getCountryNames() {
        return occupiedCountriesNames;
    }
    
    public String getCountryName(int i) {
        return occupiedCountriesNames.get(i);
    }

    public void addCountryToList(String countryName) {
        this.occupiedCountriesNames.add(countryName);
    }

    public Player(String pcolor) {
        color = pcolor;
        this.occupiedCountriesNames = new ArrayList<>();
        availableArmies = 0;
        playerArmys = 0;
    }

    public boolean continentComplete(Continent continent) {
        return (continent.completeContinent(occupiedCountriesNames));
    }
    
    public int getNewArmies() {
		int newArmies = 0;
		for (Continent continent : Main.continents) {
			if (continentComplete(continent))
				newArmies += continent.getBonusArmies();
		}
		if (occupiedCountriesNames.size() >= 9)
			newArmies += (occupiedCountriesNames.size()/3);
		else
			newArmies += 2;
		playerArmys += newArmies;
		return newArmies;
	}
    
    public boolean armiesAvailableToMove() {
    	if (availableArmies > 0)
    		return true;
    	else
    		return false;
    }
    
    public boolean armiesAvailableToWithdraw(Country country) {
    	if (country.getArmiesInCountry() > 1) {
    		availableArmies++;
    		return true;
    	} else
    		return false;
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
