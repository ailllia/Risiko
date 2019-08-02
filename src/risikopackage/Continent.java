package risikopackage;

import java.util.ArrayList;
import java.util.List;

public class Continent {
	private String continentName;
	private int bonusArmies;
	private List<String> belongingCountries = new ArrayList<String>();
	
	public Continent(String name, List<String> countries) {
		this.continentName = name;
		this.belongingCountries = countries;
		setBonusArmies();
	}
	private void setBonusArmies() {
		if (belongingCountries.size() < 5)
			bonusArmies = 1;
		else
			bonusArmies = 2;
	}
	
	public String getName() {
		return continentName;
	}
	
	public boolean completeContinent(List<String> countries) {
		int matchingCountries = 0;
		for (String country : belongingCountries) {
			for (String land : countries) {
				if (country == land) {
					matchingCountries++;
					break;
				}
			}
		}
		if (matchingCountries == belongingCountries.size())
			return true;
		else
			return false;
	}
	
	public int getBonusArmies() {
		return bonusArmies;
	}
}
