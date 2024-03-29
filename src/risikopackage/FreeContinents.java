package risikopackage;

import java.util.List;

/**
 * FreeContinents handles data concerning the structure of missions of the type 'free continents' in the game Risc.
 *
 * @author Wiebke Assenmacher
 * @author Swantje Wiechmann
 */
public class FreeContinents extends Mission {
    private List<Continent> continents;

    /**
     * Creates a new mission in this category.
     *
     * @param name        the category of a mission
     * @param title       the title of one particular mission
     * @param description the description of that mission
     * @param continents  the lists of possible continents to free
     */
    public FreeContinents(String name, String title, String description, List<Continent> continents) {
        setMissionName(name);
        setMissionTitle(title);
        setMissionDescription(description);
        this.continents = continents;
    }
}
