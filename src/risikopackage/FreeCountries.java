package risikopackage;

public class FreeCountries extends Mission {
	private int numberOfCountries = 11;
	
	public FreeCountries(String name, String description) {
		missionName = name;
		missionDescription = description;
	}

	public boolean missionAccomplished(int countries) {
		if (numberOfCountries <= countries)
			return true;
		else
			return false;
	}
}
