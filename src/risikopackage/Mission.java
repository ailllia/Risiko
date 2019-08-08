package risikopackage;

import java.util.List;

public abstract class Mission {
	protected String missionName;
	protected String missionTitle;
	protected String missionDescription;
	
	public static Mission create(String name, String title, String description, List<Continent> continents) {
		switch (name) {
		case "Laender befreien" : return new FreeCountries(name, title, description);
		case "Kontinente befreien" : return new FreeContinents(name, title, description, continents);
		default : return null;
		}
	}
	
	public String getMissionTitle() {
        return this.missionTitle;
    }
	
	public static String getDescription(String title)
	{
		String thatMissionDescription = "blubb";
		for (int i = 0; i < 6; i++)
		{
			if (title == Main.missions.get(i).missionTitle)
			{
				thatMissionDescription = Main.missions.get(i).missionDescription;
			}
		}
		return thatMissionDescription;
	}
	
	// testet, ob Mission komplett ist
	public static boolean testMission(Player playerNow)
	{
			if (playerNow.getPlayerMission().equals("Iwein"))
				// Befreie die Kontinente Otea und Solva.
			{
				if (Continent.complete("Otea", playerNow) && Continent.complete("Solva", playerNow))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else if (playerNow.getPlayerMission().equals("Gawein"))
				// Befreie die Kontinente Solva und Priya.
			{
				if (Continent.complete("Priya", playerNow) && Continent.complete("Solva", playerNow))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else if (playerNow.getPlayerMission().equals("Parzival"))
				// Befreie die Kontinente Priya und Otea.
			{
				if (Continent.complete("Priya", playerNow) && Continent.complete("Otea", playerNow))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else if (playerNow.getPlayerMission().equals("Tristan") || 
					playerNow.getPlayerMission().equals("Keie") ||
					playerNow.getPlayerMission().equals("Erec"))
				// Befreie und besetze insgesamt 11 Laender.
			{
				if (playerNow.numberOfCountries() >= 11)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
	}
}
