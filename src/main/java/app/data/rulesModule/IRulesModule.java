package app.data.rulesModule;

import java.util.List;
import java.util.Map;

import app.Connection;
import app.data.Airports;

public interface IRulesModule {

	Map<String, List<Connection>> getConnectionsByOrigin();	
	List<String> getZoneNameList();
	List<String> getCountryNamesByZone(String zone);
	int getMilesNeeded(String originZone, String destZone);
	String getAirportZone(String airport);
	String getCountryZone(String countryCode);
	Airports getAirports();
	IZoneFilter getZoneFilterInstance();
}
