package risikopackage;

import java.util.List;

public class FreeContinents extends Mission {
    private List<Continent> continents;

    public FreeContinents(String name, String title, String description, List<Continent> continents) {
        missionName = name;
        missionTitle = title;
        missionDescription = description;
        this.continents = continents;
    }

    public boolean missionAccomplished(Player player) {
        int counter = 0;
        for (Continent continent : continents) {
            if (player.continentComplete(continent))
                counter++;
        }
        return (counter == continents.size());
    }
}
