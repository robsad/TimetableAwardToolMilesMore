package app.logic;

import java.util.List;
import java.util.Map;

import app.*;
import app.data.Airports;
import app.data.rulesModule.IRulesModule;
import app.gateaway.FormChoosen;
import app.gateaway.FormPossibles;

public class ContainerManager {
	
	private IRulesModule rulesModule;
	private Container container;
	
	public ContainerManager(IRulesModule rulesModule){
		this.rulesModule = rulesModule;
		System.out.println("ContainerManager created");
	}
	
	public void containerInterface() {
		Airports airports = rulesModule.getAirports();
		container = new Container(4, rulesModule);
		FormChoosen formChoosen = new FormChoosen(4);
		//formChoosen.setAirport(0,airports.getAirportNameByCode("HNL"));
		formChoosen.setAirport(0,"Warsaw");
		formChoosen.setAirport(2,"Rio de Janeiro");
    	//formChoosen.setCountry(2,"NZ");
    	FormPossibles formPossibles = container.setRouteLines(formChoosen);
    	System.out.println(formPossibles.toString());
    	
	}
	
}
