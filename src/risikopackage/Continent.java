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
}
