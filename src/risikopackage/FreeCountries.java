package risikopackage;

public class FreeCountries extends Mission {

    /**
     * creates a new mission in this category
     *
     * @param name        is the category of a mission
     * @param title       is the title of one particular mission
     * @param description is the description of that mission
     */
    public FreeCountries(String name, String title, String description) {
        setMissionName(name);
        setMissionTitle(title);
        setMissionDescription(description);
    }
}
