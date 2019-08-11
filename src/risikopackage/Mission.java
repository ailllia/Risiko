package risikopackage;

import java.util.List;

public abstract class Mission {
    protected String missionName;
    protected String missionTitle;
    protected String missionDescription;

    /**
    * Returns the title of a mission.
     *
    * @return the title of a mission
    */
    public String getMissionTitle() {
        return this.missionTitle;
    }

    /**
    * Searches for the wanted description of a mission.
     *
    * @param title the mission's name
    * @return the description of that mission
    */
    public static String getDescription(String title) {
        String thatMissionDescription = "";
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

