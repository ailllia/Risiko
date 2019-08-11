package risikopackage;

/**
 * FreeCountries handles data concerning the structure of missions of the type 'free countries' in the game Risc.
 *
 * @author Wiebke Assenmacher
 * @author Swantje Wiechmann
 */
public class FreeCountries extends Mission {

    /**
     * Creates a new mission in this category.
     *
     * @param name        the category of a mission
     * @param title       the title of one particular mission
     * @param description the description of that mission
     */
    public FreeCountries(String name, String title, String description) {
        setMissionName(name);
        setMissionTitle(title);
        setMissionDescription(description);
    }
}
