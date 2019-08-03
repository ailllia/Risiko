package risikopackage;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static java.io.File.separator;

public class Main {

    public static Player playerOne;
    public static Player playerTwo;
    public static List<Country> countries = new ArrayList<>();
    public static List<Continent> continents = new ArrayList<>();
    public static List<Mission> missions = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        //Das Einlesen
        String separator = ".";
        readMissions(separator);
        readContinents(separator);
        readCountries(separator);


        //Erste GUI, Startfenster
        PlayersGUI g = new PlayersGUI();

    }

    private static void readCountries(String separator) throws FileNotFoundException {
        File f = new File("material/Countries.txt");
        InputStream istream = new FileInputStream(f); //Anwendung wie in den Seminaren, keine Ahnung warum es nicht funktioniert
        Scanner reader = new Scanner(istream);
        while (reader.hasNext()) {
            if (!reader.hasNext(separator)) {
                String countryName = reader.nextLine();
                List<String> neighboringCountries = new LinkedList<>();
                while (!reader.hasNext(separator)) {
                    neighboringCountries.add(reader.nextLine());
                }
                Country country = new Country(countryName, neighboringCountries);
                countries.add(country);
            } else break;
        }
        reader.close();
    }

    private static void readContinents(String separator) throws FileNotFoundException {
        File f = new File("material/Continents.txt");
        InputStream istream = new FileInputStream(f);
        Scanner reader = new Scanner(istream);
        while (reader.hasNext()) {
            if (!reader.hasNext(separator)) {
                String continentName = reader.nextLine();
                List<String> belongingCountries = new LinkedList<>();
                while (!reader.hasNext(separator)) {
                    belongingCountries.add(reader.nextLine());
                }
                Continent continent = new Continent(continentName, belongingCountries);
                continents.add(continent);
            } else break;
        }
        reader.close();
    }

    private static void readMissions(String separator) throws FileNotFoundException {
        File f = new File("material/Missions.txt");
        InputStream istream = new FileInputStream(f);
        Scanner reader = new Scanner(istream);
        while (reader.hasNext()) {
            if (!reader.hasNext(separator)) {
                String missionName = reader.nextLine();
                String missionDescription = reader.nextLine();
                Mission mission;
                if (missionName.equals("Kontinente befreien")) {
                    List<Continent> continentsInMission = new LinkedList<>();
                    while (!reader.hasNext(separator)) {
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
                    mission = new FreeContinents(missionName, missionDescription, continentsInMission);
                } else
                    mission = new FreeCountries(missionName, missionDescription);
                missions.add(mission);
            } else break;
        }
        reader.close();
    }
}