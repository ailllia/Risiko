package risikopackage;

public abstract class Mission {
    private String missionName, missionTitle, missionDescription;

    /**
     * Sets the name of the mission.
     *
     * @param missionName the name the mission will get
     */
    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    /**
     * Sets the title of a mission.
     *
     * @param missionTitle the title the mission will get
     * @see Mission#getMissionTitle()
     */
    public void setMissionTitle(String missionTitle) {
        this.missionTitle = missionTitle;
    }

    /**
     * Returns the title of a mission.
     *
     * @return the title of a mission
     * @see Mission#setMissionTitle(String missionTitle)
     */
    public String getMissionTitle() {
        return this.missionTitle;
    }

    /**
     * Sets the description of a mission.
     *
     * @param missionDescription the description the mission will get
     * @see Mission#getMissionDescription(String title)
     */
    public void setMissionDescription(String missionDescription) {
        this.missionDescription = missionDescription;
    }

    /**
     * Searches for the wanted description of a mission.
     *
     * @param title the mission's name
     * @return the description of that mission
     * @see Mission#setMissionDescription(String missionDescription)
     */
    public static String getMissionDescription(String title) {
        String thatMissionDescription = "";
        for (int i = 0; i < 6; i++) {
            if (title.equals(Gameplay.getInstance().getMissions().get(i).missionTitle)) {
                thatMissionDescription = Gameplay.getInstance().getMissions().get(i).missionDescription;
            }
        }
        return thatMissionDescription;
    }

    /**
     * Tests whether a mission is complete.
     *
     * @param currentPlayer the player whose turn it is
     * @return true if a mission is complete; false otherwise
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

