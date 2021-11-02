package edu.tsi1.gr5.crazyfinger.pronosticos.webservices;

import static edu.tsi1.gr5.crazyfinger.pronosticos.PronosticoHelper.convertir;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import net.wxbug.api.ApiForecastData;
import net.wxbug.api.LocationUSWorldCityData;
import net.wxbug.api.UnitType;
import net.wxbug.api.WeatherBugWebServicesLocator;
import net.wxbug.api.WeatherBugWebServicesSoap;
import edu.tsi1.gr5.crazyfinger.pronosticos.PronosticoError;
import edu.tsi1.gr5.crazyfinger.pronosticos.datatypes.PronosticoDia;

public class WeatherbugConnector {
	
	private WeatherBugWebServicesSoap servicesSoap = null;
	private WeatherBugWebServicesLocator locator = null;
	private String apiKey = null;
	
	
	private WeatherBugWebServicesSoap getServicesSoap() throws ServiceException {
		if (servicesSoap == null) {
			locator = getLocator();
			servicesSoap = locator.getWeatherBugWebServicesSoap();
		}
		return servicesSoap;
	}
	
	private WeatherBugWebServicesLocator getLocator() {
		if (locator == null) {
			locator = new WeatherBugWebServicesLocator();
			
		}
		return locator;
	}
	
	private String getApiKey() {
		if (apiKey == null){
			// TODO: sacar de properties . .
			apiKey = "A5664274676";
		}
		return apiKey;
	}
	
	
	public String getCityCode(double latitud, double longitud) throws RemoteException, ServiceException {
		BigDecimal lat = new BigDecimal(latitud);
		BigDecimal lon = new BigDecimal(longitud);
		
		servicesSoap = getServicesSoap();
		LocationUSWorldCityData loc = servicesSoap.getUSWorldCityByLatLong(lat, lon, getApiKey());
		return loc.getCityCode();
	}
	
	private ApiForecastData [] getForecastSemana(double latitud, double longitud) throws RemoteException, ServiceException {
		servicesSoap = getServicesSoap();
		String cityCode = getCityCode(latitud, longitud);
		UnitType unitType = UnitType.fromString(UnitType._Metric);
		Object [] os = servicesSoap.getForecastByCityCode(cityCode, unitType, getApiKey());
//		ApiForecastData [] lst = (ApiForecastData []) os;
		ApiForecastData [] lst = new ApiForecastData [os.length] ;
		for (int i=0;i<os.length;i++) {
			lst[i] = (ApiForecastData) os[i];
		}
		return lst;
	}
	
	public List<PronosticoDia> getPronosticos(double latitud, double longitud) throws PronosticoError {
		try {
			ApiForecastData [] wsres = getForecastSemana(latitud, longitud);
			List<PronosticoDia> res = convertir(wsres,latitud,longitud);
			return res;
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new PronosticoError(e);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new PronosticoError(e);
		}
		
	}
	
	
}
