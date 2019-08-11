package risikopackage;

public class FreeCountries extends Mission {

    /**
     * Creates a new mission in this category.
     *
     * @param name the category of a mission
     * @param title the title of one particular mission
     * @param description the description of that mission
     */
    public FreeCountries(String name, String title, String description) {
        setMissionName(name);
        setMissionTitle(title);
        setMissionDescription(description);
    }
}
