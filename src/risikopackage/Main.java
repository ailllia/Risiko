package risikopackage;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static Player playerOne;
	public static Player playerTwo;
	public static List<Country> countries = new ArrayList<Country>();
	public static List<Continent> continents = new ArrayList<Continent>();
	public static List<Mission> missions = new ArrayList<Mission>();
	
    public static void main(String[] args) {
        //Das Einlesen
        String seperator = ".";
        readCountries(seperator);
        readContinents(seperator);
        readMissions(seperator);
    	
        //Erste GUI, Startfenster
        PlayersGUI g = new PlayersGUI();
                
    }
    
    private static void readCountries (String seperator) {
    	File f = new File("material\\Countries.txt");
        InputStream istream = new FileInputStream(f); //Anwendung wie in den Seminaren, keine Ahnung warum es nicht funktioniert
        Scanner reader = new Scanner(istream);
        while (reader.hasNext()) {
        	if (reader.hasNext(seperator))
        		continue;
        	else {
        		String countryName = reader.nextLine();
        		List<String> neighboringCountries = new LinkedList<String>();
        		while (!reader.hasNext(seperator)) {
        			neighboringCountries.add(reader.nextLine());
        		}
        		Country country = new Country(countryName, neighboringCountries);
        		countries.add(country);
        	}
        }
        reader.close();
    }
    
    private static void readContinents (String seperator) {
    	File f = new File("material\\Continents.txt");
    	InputStream istream = new FileInputStream(f);
    	Scanner reader = new Scanner(istream);
    	while (reader.hasNext()) {
    		if (reader.hasNext(seperator))
    			continue;
    		else {
    			String continentName = reader.nextLine();
    			List<String> belongingCountries = new LinkedList<String>();
    			while (!reader.hasNext(seperator)) {
    				belongingCountries.add(reader.nextLine());
    			}
    			Continent continent = new Continent(continentName, belongingCountries);
    			continents.add(continent);
    		}
    	}
    	reader.close();
    }
    
    private static void readMissions (String seperator) {
    	File f = new File("material\\Missions.txt");
    	InputStream istream = new FileInputStream(f);
    	Scanner reader = new Scanner(istream);
    	while (reader.hasNext()) {
    		if (reader.hasNext(seperator))
    			continue;
    		else {
    			String missionName = reader.nextLine();
    			String missionDescription = reader.nextLine();
       			Mission mission;
    			if (missionName == "Kontinente befreien") {	
    				List<Continent> continentsInMission = new LinkedList<Continent>();
    				while (!reader.hasNext(seperator)) {
    					int index = 0;
    					String missionContinent = reader.nextLine();
    					for (int i = 0; i < continents.size(); i++) {
    						if (continents.get(i).getName() == missionContinent) {
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
    		}
    	}
    	reader.close();
    }
}