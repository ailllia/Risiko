package risikopackage;

import java.util.List;

public class Player {
    private String color;
    private String playerMission;
    private int playerArmies;
    //private int playernew;
    private List<String> occupiedCountriesName;
    private List<Country> occupiedCountries;

    public Player(String pcolor) {
    	
        color = pcolor;
    }

    public boolean continentComplete(Continent continent) {
        return (continent.completeContinent(occupiedCountriesName));
    }
    
    public int getNewArmies() {
		int newArmies = 0;
		for (Continent continent : Main.continents) {
			if (continentComplete(continent))
				newArmies += continent.getBonusArmies();
		}
		if (occupiedCountries.size() >= 9)
			newArmies += (occupiedCountries.size()/3);
		else
			newArmies += 2;
		return newArmies;
	}
    
    public void moveArmies() {
		int newArmies = playerArmies - occupiedCountries.size();
		if (newArmies == 0) {
			// Ausgabe, dass keine Bewegung moeglich ist
		} else {
			for (Country country : occupiedCountries) {
				country.setArmies();
			}
			// newArmies koennen jetzt verteilt werden
		}
	}

    @Override
    public String toString() {
        return "Farbe: " + color + ", Mission: " + playerMission + ", Anzahl der Armeen: " + playerArmies; // + playernew
    }
}
