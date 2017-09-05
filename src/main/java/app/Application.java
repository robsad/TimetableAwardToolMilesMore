package app;

import java.util.List;
import java.util.Map;

import app.data.*;
import app.data.rulesModule.IRulesModule;
import app.data.rulesModule.RulesModuleFactory;
import app.logic.*;

public class Application 
{

    public static void main( String[] args )
    {
    	DataLoader dataLoader = new DataLoader();
    	List<AirportsData> airportsData = dataLoader.getAirports();
    	Map<String, String> countryByCode = dataLoader.getCountryCodes();
    	Airports airports = new Airports(airportsData,countryByCode);
    	
    	Map<String, List<Connection>> connectionsByOrigin = dataLoader.getConnections();
    	
    	//SaveToCSV saveToCSV = new SaveToCSV(airportsData,connectionsByOrigin);
    	//saveToCSV.toFile();
    	
    	RulesModuleFactory rulesFactory = new RulesModuleFactory();
    	IRulesModule milesMoreModule = rulesFactory.getModule("MilesMore", airports, connectionsByOrigin);
    	
    	ContainerManager containerManager = new ContainerManager(milesMoreModule);
    	containerManager.containerInterface();
    }
}
