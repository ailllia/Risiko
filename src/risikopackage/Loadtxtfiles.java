package risikopackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Loadtxtfiles reads files which are formatted in a specific way and saves the data.
 *
 * @author Swantje Wiechmann
 */
public class Loadtxtfiles {

    /**
     * Creates a new Loadtxtfiles.
     */
    public Loadtxtfiles() {
    }

    /**
     * Reads the files for continents, countries and missions of the game into the program.
     *
     * @param separator the sign that separates the entries in the files
     * @throws FileNotFoundException if the file has a different name, or isn't saved at the named path
     * @see Loadtxtfiles#readContinents(String seperator)
     * @see Loadtxtfiles#readCountries(String seperator)
     * @see Loadtxtfiles#readMissions(String seperator)
     */
    public static void readFiles(String separator) throws FileNotFoundException {
        readContinents(separator);
        readCountries(separator);
        readMissions(separator);
    }

    /**
     * Reads the file with countries into the program.
     *
     * @param separator the sign that separates the entries in the files
     * @throws FileNotFoundException if the file has a different name, or isn't saved at the named path
     * @see Loadtxtfiles#readFiles(String seperator)
     * @see Loadtxtfiles#readMissions(String seperator)
     * @see Loadtxtfiles#readContinents(String seperator)
     */
    private static void readCountries(String separator) throws FileNotFoundException {
        File f = new File("material/Countries.txt");
        InputStream istream = new FileInputStream(f);
        Scanner reader = new Scanner(istream);
        while (reader.hasNext()) {
            if (reader.hasNext(separator))
                reader.nextLine();
            else {
                String countryName = reader.nextLine();
                List<String> neighboringCountries = new LinkedList<>();
                while (reader.hasNext() && !reader.hasNext(separator)) {
                    neighboringCountries.add(reader.nextLine());
                }
                Country country = new Country(countryName, neighboringCountries);
                Gameplay.getInstance().getCountries().add(country);
            }
        }
        reader.close();
    }

    /**
     * Reads the file with the continents into the program.
     *
     * @param separator the sign that separates the entries in the files
     * @throws FileNotFoundException if the file has a different name, or isn't saved at the named path
     * @see Loadtxtfiles#readFiles(String seperator)
     * @see Loadtxtfiles#readCountries(String seperator)
     * @see Loadtxtfiles#readMissions(String seperator)
     */
    private static void readContinents(String separator) throws FileNotFoundException {
        File f = new File("material/Continents.txt");
        InputStream istream = new FileInputStream(f);
        Scanner reader = new Scanner(istream);
        while (reader.hasNext()) {
            if (reader.hasNext(separator))
                reader.nextLine();
            else {
                String continentName = reader.nextLine();
                List<String> belongingCountries = new LinkedList<>();
                while (reader.hasNext() && !reader.hasNext(separator)) {
                    belongingCountries.add(reader.nextLine());
                }
                Continent continent = new Continent(continentName, belongingCountries);
                Gameplay.getInstance().getContinents().add(continent);
            }
        }
        reader.close();
    }

    /**
     * Reads the file with the missions into the program.
     *
     * @param separator the sign that separates the entries in the files
     * @throws FileNotFoundException if the file has a different name, or isn't saved at the named path
     * @see Loadtxtfiles#readFiles(String seperator)
     * @see Loadtxtfiles#readCountries(String seperator)
     * @see Loadtxtfiles#readContinents(String seperator)
     */
    private static void readMissions(String separator) throws FileNotFoundException {
        Gameplay gameplayInstance = Gameplay.getInstance();
        File f = new File("material/Missions.txt");
        InputStream istream = new FileInputStream(f);
        Scanner reader = new Scanner(istream);
        while (reader.hasNext()) {
            if (reader.hasNext(separator))
                reader.nextLine();
            else {
                String missionName = reader.nextLine();
                String missionTitle = reader.nextLine();
                String missionDescription = reader.nextLine();
                Mission mission;
                if (missionName.equals("Kontinente befreien")) {
                    List<Continent> continentsInMission = new LinkedList<>();
                    while (reader.hasNext() && !reader.hasNext(separator)) {
                        int index = 0;
                        String missionContinent = reader.nextLine();
                        for (int i = 0; i < gameplayInstance.getContinents().size(); i++) {
                            if (gameplayInstance.getContinents().get(i).getName().equals(missionContinent)) {
                                index = i;
                                break;
                            }
                        }
                        continentsInMission.add(gameplayInstance.getContinents().get(index));
                    }
                    mission = new FreeContinents(missionName, missionTitle, missionDescription, continentsInMission);
                } else
                    mission = new FreeCountries(missionName, missionTitle, missionDescription);
                gameplayInstance.getMissions().add(mission);
            }
        }
        reader.close();
    }
}
