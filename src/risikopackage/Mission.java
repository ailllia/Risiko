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
}
