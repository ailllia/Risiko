package risikopackage;

import java.util.List;

public abstract class Mission {
    protected String missionName;
    protected String missionDescription;

    public static Mission create(String type, String name, String description, List<Continent> continents) {
        switch (type) {
            case "Laender befreien":
                return new FreeCountries(name, description);
            case "Kontinente befreien":
                return new FreeContinents(name, description, continents);
            default:
                return null;
        }
    }
}
