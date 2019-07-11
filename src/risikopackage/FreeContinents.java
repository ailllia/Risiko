package risikopackage;

import java.util.List;

public class FreeContinents extends Mission {
	List<Continent> continents;
	
	public FreeContinents(String name, String description, List<Continent> continents) {
		missionName = name;
		missionDescription = description;
		this.continents = continents;
	}
	
	public boolean missionAccomplished(Player player) {
		int counter = 0;
		for (Continent continent : continents) {
			if (player.continentComplete(continent))
				counter++;
		}
		if (counter == continents.size())
			return true;
		else
			return false;
	}
}
