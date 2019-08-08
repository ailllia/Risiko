package risikopackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static Player playerOne;
    public static Player playerTwo;
    public static List<Country> countries = new ArrayList<>();
    public static List<Continent> continents = new ArrayList<>();
    public static List<Mission> missions = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        playerOne = new Player(null);
        playerTwo = new Player(null);

        //Das Einlesen
        String separator = ".";
        readContinents(separator);
        readCountries(separator);
        readMissions(separator);

        //Erste GUI, Startfenster
        new PlayersGUI();
    }

    private static void readCountries(String separator) throws FileNotFoundException {
        File f = new File("material/Countries.txt");
        InputStream istream = new FileInputStream(f); //Anwendung wie in den Seminaren, keine Ahnung warum es nicht funktioniert
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
                countries.add(country);
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
                continents.add(continent);
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
                        for (int i = 0; i < continents.size(); i++) {
                            if (continents.get(i).getName().equals(missionContinent)) {
                                index = i;
                                break;
                            }
                        }
                        continentsInMission.add(continents.get(index));
                    }
                    mission = new FreeContinents(missionName, missionTitle, missionDescription, continentsInMission);
                } else
                    mission = new FreeCountries(missionName, missionTitle, missionDescription);
                missions.add(mission);
            }
        }
        reader.close();
    }
}