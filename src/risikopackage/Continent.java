package risikopackage;

import java.util.List;

public class Continent {
    private String continentName;
    private int bonusArmies;
    private List<String> belongingCountries;

    /**
     * Creates a new Continent.
     *
     * @param name the name of the continent
     * @param countries the list of countries belonging to the continent
     */
    public Continent(String name, List<String> countries) {
        this.continentName = name;
        this.belongingCountries = countries;
        setBonusArmies();
    }

    /**
     * Sets bonus armies that come with the continent.
     *
     * @see Continent#getBonusArmies()
     */
    private void setBonusArmies() {
        if (belongingCountries.size() < 5)
            bonusArmies = 1;
        else
            bonusArmies = 2;
    }

    /**
     * Gets the name of the continent.
     *
     * @return name of the continent
     */
    public String getName() {
        return continentName;
    }

    /**
     * Returns whether a list contains the complete continent.
     *
     * @param countries the list of countries that might contain the complete continent
     * @return true if countries contains the complete continent; false otherwise
     */
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

    /**
     * Gets the number of bonus armies.
     *
     * @return the number of bonus armies
     * @see Continent#setBonusArmies()
     */
    public int getBonusArmies() {
        return bonusArmies;
    }

    /**
     * Returns true if a continent is owned completely by a player.
     *
     * @param cName the name of the continent
     * @param PlayerNow the player whose turn it is
     * @return false by default
     */
    public static boolean complete(String cName, Player PlayerNow) {
        Gameplay gameplayInstance = Gameplay.getInstance();
        switch (cName) {
            case "Otea":
                int oteas = 0;
                for (int i = 0; i < PlayerNow.numberOfCountries(); i++) {
                    for (int j = 0; j < gameplayInstance.getContinents().get(0).belongingCountries.size(); j++) {
                        if (PlayerNow.getCountryName(i).equals(gameplayInstance.getContinents().get(0).belongingCountries.get(j))) {
                            oteas += 1;
                        }
                    }
                }
                return oteas == 4;
            case "Priya":
                int pryas = 0;
                for (int i = 0; i < PlayerNow.numberOfCountries(); i++) {
                    for (int j = 0; j < gameplayInstance.getContinents().get(1).belongingCountries.size(); j++) {
                        if (PlayerNow.getCountryName(i).equals(gameplayInstance.getContinents().get(2).belongingCountries.get(j))) {
                            pryas += 1;
                        }
                    }
                }
                return pryas == 5;
            case "Solva":
                int solvas = 0;
                for (int i = 0; i < PlayerNow.numberOfCountries(); i++) {
                    for (int j = 0; j < gameplayInstance.getContinents().get(2).belongingCountries.size(); j++) {
                        if (PlayerNow.getCountryName(i).equals(gameplayInstance.getContinents().get(1).belongingCountries.get(j))) {
                            solvas += 1;
                        }
                    }
                }
                return solvas == 5;
            default:
                return false;
        }
    }
}
