package risikopackage;

import java.util.List;

public abstract class Mission {
    protected String missionName;
    protected String missionTitle;
    protected String missionDescription;

    public static Mission create(String name, String title, String description, List<Continent> continents) {
        switch (name) {
            case "Laender befreien":
                return new FreeCountries(name, title, description);
            case "Kontinente befreien":
                return new FreeContinents(name, title, description, continents);
            default:
                return null;
        }
    }

    public String getMissionName() {
        return missionName;
    }

    public String getMissionTitle() {
        return this.missionTitle;
    }

    public static String getDescription(String title) {
        String thatMissionDescription = "blubb";
        for (int i = 0; i < 6; i++) {
            if (title.equals(Gameplay.getInstance().getMissions().get(i).missionTitle)) {
                thatMissionDescription = Gameplay.getInstance().getMissions().get(i).missionDescription;
            }
        }
        return thatMissionDescription;
    }

    // testet, ob Mission komplett ist
    public static boolean testMission(Player playerNow) {

        switch (playerNow.getPlayerMission()) {
            case "Iwein":       // Befreie die Kontinente Otea und Solva.
                return (Continent.complete("Otea", playerNow) && Continent.complete("Solva", playerNow));

            case "Gawein":      // Befreie die Kontinente Solva und Priya.
                return (Continent.complete("Priya", playerNow) && Continent.complete("Solva", playerNow));

            case "Parzival":    // Befreie die Kontinente Priya und Otea.
                return (Continent.complete("Priya", playerNow) && Continent.complete("Otea", playerNow));

            case "Tristan":     // Befreie und besetze insgesamt 11 Laender.
            case "Keie":
            case "Erec":
                return (playerNow.numberOfCountries() >= 11);
        }
        return false;
    }
}

