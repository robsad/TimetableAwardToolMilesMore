package app.data.rulesModule;

import java.util.List;
import java.util.Map;
import java.util.Set;

import app.data.Airports;
import app.data.entities.Connection;

public interface IRulesModule {

	Map<String, List<Connection>> getConnectionsByOrigin();	
	List<String> getZoneNameList();
	List<String> getCountryNamesByZone(String zone);
	int getMilesNeeded(String originZone, String destZone);
	String getAirportZone(String airport);
	String getCountryZone(String countryCode);
	String getCountryNameZone(String countryName);
	Airports getAirports();
	IZoneFilter getZoneFilterInstance();
	Set<String> getAirlines(String originAirport, String destAirport);
	
}
