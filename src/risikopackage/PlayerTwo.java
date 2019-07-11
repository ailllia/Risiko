package risikopackage;

import java.util.List;

public class Player {
	private String colour;
	private String missionName;
	private List<Country> countries;
	private List<String> countriesName;
	private int armiesOnMap;
	//int newArmies;
	
	public Player(String color) {
		this.colour = color;
	}
	
	public int getNewArmies(List<Continent> continents) {
		int newArmies = 2;
		for (Continent continent : continents) {
			if (continentComplete(continent))
				newArmies += continent.getBonusArmies();
		}
		if (countries.size() >= 9)
			newArmies += (countries.size()/3);
		return newArmies;
	}
	
	public boolean continentComplete(Continent continent) {
		if (continent.completeContinent(countriesName)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void moveArmies() {
		int newArmies = armiesOnMap - countries.size();
		for (Country country : countries) {
			country.setArmies();
		}
		// newArmies koennen jetzt verteilt werden
	}
}
