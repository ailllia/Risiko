package risikopackage;

import java.util.List;

public abstract class Mission {
    protected String missionName;
    protected String missionTitle;
    protected String missionDescription;

    /**
     * creates new missions either in subclass "FreeContinents" or in "FreeCountries"
     *
     * @param name        is the category of a mission
     * @param title       is the title of one particular mission
     * @param description is the description of that mission
     * @param continents  is the lists of possible continents to free
     * @return returns a complete mission
     */
    public Mission create(String name, String title, String description, List<Continent> continents) {
        switch (name) {
            case "Laender befreien":
                return new FreeCountries(name, title, description);
            case "Kontinente befreien":
                return new FreeContinents(name, title, description, continents);
            default:
                return null;
        }
    }

    /**
     * returns the category of a mission
     *
     * @return returns the category of a mission
     */
    public String getMissionName() {
        return missionName;
    }

    /**
     * returns the title of a mission
     *
     * @return returns the title of a mission
     */
    public String getMissionTitle() {
        return this.missionTitle;
    }

    /**
     * searches for and returns the wanted description of a mission
     *
     * @param title is the mission's name
     * @return returns the description of that mission
     */
    public static String getDescription(String title) {
        String thatMissionDescription = "blubb";
        for (int i = 0; i < 6; i++) {
            if (title.equals(Gameplay.getInstance().getMissions().get(i).missionTitle)) {
                thatMissionDescription = Gameplay.getInstance().getMissions().get(i).missionDescription;
            }
        }
        return thatMissionDescription;
    }

    /**
     * tests whether a mission is complete
     *
     * @param currentPlayer is the current player
     * @return true if a mission is complete and false if it is not
     */
    public static boolean testMission(Player currentPlayer) {
        switch (currentPlayer.getPlayerMission()) {
            case "Iwein":
                return (Continent.complete("Otea", currentPlayer) && Continent.complete("Solva", currentPlayer));

            case "Gawein":
                return (Continent.complete("Priya", currentPlayer) && Continent.complete("Solva", currentPlayer));

            case "Parzival":
                return (Continent.complete("Priya", currentPlayer) && Continent.complete("Otea", currentPlayer));

            case "Tristan":
            case "Keie":
            case "Erec":
                return (currentPlayer.numberOfCountries() >= 11);
        }
        return false;
    }
}

