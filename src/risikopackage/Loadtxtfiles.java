package risikopackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Loadtxtfiles {

    public Loadtxtfiles() {
    }

    public static void readFiles(String separator) throws FileNotFoundException {
        readContinents(separator);
        readCountries(separator);
        readMissions(separator);
    }

    private static void readCountries(String separator) throws FileNotFoundException {
        File f = new File("material/Countries.txt");
        InputStream istream = new FileInputStream(f);
        Scanner reader = new Scanner(istream);
        while (reader.hasNext()) {
            if (reader.hasNext(separator))
                reader.nextLine();
            else {
                String countryName = reader.nextLine();
                //test if country with countryName in countries
                //dont add again
                List<String> neighboringCountries = new LinkedList<>(); //list<country>
                while (reader.hasNext() && !reader.hasNext(separator)) {
                    //test if neighbor in countries
                    // if not: create new country and add to countries (with empty neighbors)
                    //add neighbor to country.neighbors
                    neighboringCountries.add(reader.nextLine());
                }
                Country country = new Country(countryName, neighboringCountries);
                Gameplay.getInstance().getCountries().add(country);
            }
        }
        reader.close();
    }

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

    private static void readMissions(String separator) throws FileNotFoundException {
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
                        for (int i = 0; i < Gameplay.getInstance().getContinents().size(); i++) {
                            if (Gameplay.getInstance().getContinents().get(i).getName().equals(missionContinent)) {
                                index = i;
                                break;
                            }
                        }
                        continentsInMission.add(Gameplay.getInstance().getContinents().get(index));
                    }
                    mission = new FreeContinents(missionName, missionTitle, missionDescription, continentsInMission);
                } else
                    mission = new FreeCountries(missionName, missionTitle, missionDescription);
                Gameplay.getInstance().getMissions().add(mission);
            }
        }
        reader.close();
    }
}
