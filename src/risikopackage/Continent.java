package risikopackage;

import java.util.ArrayList;
import java.util.List;

public class Continent {
    private String continentName;
    private int bonusArmies;
    private List<String> belongingCountries;

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
                if (country.equals(land)) {
                    matchingCountries++;
                    break;
                }
            }
        }
        return (matchingCountries == belongingCountries.size());
    }

    public int getBonusArmies() {
        return bonusArmies;
    }
    
    // testet, ob bestimmter Kontinent komplett ist
    public static boolean complete(String cName, Player PlayerNow)
    {
		switch (cName) {
		case "Otea" : 
			int oteas = 0;
	        for (int i = 0; i < PlayerNow.numberOfCountries(); i++) {
	            for (int j = 0; j < Main.continents.get(0).belongingCountries.size(); j++) {
	            		if (PlayerNow.getCountryName(i).equals(Main.continents.get(0).belongingCountries.get(j)))
	            		{
	            			oteas += 1;
	            		}
	                }
	            }
			if (oteas == 4)
				return true;
			else
				return false;
		case "Priya" : 
			int pryas = 0;
	        for (int i = 0; i < PlayerNow.numberOfCountries(); i++) {
	            for (int j = 0; j < Main.continents.get(2).belongingCountries.size(); j++) {
	            		if (PlayerNow.getCountryName(i).equals(Main.continents.get(2).belongingCountries.get(j)))
	            		{
	            			pryas += 1;
	            		}
	                }
	            }
			if (pryas == 5)
				return true;
			else
				return false;
		case "Solva" : 
			int solvas = 0;
	        for (int i = 0; i < PlayerNow.numberOfCountries(); i++) {
	            for (int j = 0; j < Main.continents.get(2).belongingCountries.size(); j++) {
	            		if (PlayerNow.getCountryName(i).equals(Main.continents.get(2).belongingCountries.get(j)))
	            		{
	            			solvas += 1;
	            		}
	                }
	            }
			if (solvas == 5)
				return true;
			else
				return false;
		default : return false;
		}
    }
}
