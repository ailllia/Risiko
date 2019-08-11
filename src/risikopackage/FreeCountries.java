package risikopackage;

public class FreeCountries extends Mission {
    private int numberOfCountries = 11;

    /**
     * creates a new mission in this category
     * @param name is the category of a mission
     * @param title is the title of one particular mission
     * @param description is the description of that mission
     */
    public FreeCountries(String name, String title, String description) {
        missionName = name;
        missionTitle = title;
        missionDescription = description;
    }

    /**
     * tests whether a player owns enough countries to fulfill a mission
     * @param countries is the number of owned countries
     * @return true if the number of owned countries is higher than the desired number 
     */
    public boolean missionAccomplished(int countries) {
        return (numberOfCountries <= countries);
    }
}
