package risikopackage;

import java.util.List;

public class FreeContinents extends Mission {
    private List<Continent> continents;

    /**
     * creates a new mission in this category
     *
     * @param name        is the category of a mission
     * @param title       is the title of one particular mission
     * @param description is the description of that mission
     * @param continents  is the lists of possible continents to free
     */
    public FreeContinents(String name, String title, String description, List<Continent> continents) {
        missionName = name;
        missionTitle = title;
        missionDescription = description;
        this.continents = continents;
    }

    /**
     * tests whether a player owns a whole continent
     *
     * @param player is the current player
     * @return true if a continent is owned by the current player
     */
    public boolean missionAccomplished(Player player) {
        int counter = 0;
        for (Continent continent : continents) {
            if (player.continentComplete(continent))
                counter++;
        }
        return (counter == continents.size());
    }
}
