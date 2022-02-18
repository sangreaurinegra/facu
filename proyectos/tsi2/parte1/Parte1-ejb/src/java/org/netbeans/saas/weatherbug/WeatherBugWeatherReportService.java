/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.saas.weatherbug;

import java.io.IOException;
import org.netbeans.saas.RestConnection;
import org.netbeans.saas.RestResponse;

/**
 * WeatherBugWeatherReportService Service
 *
 * @author dell
 */

public class WeatherBugWeatherReportService {

    /** Creates a new instance of WeatherBugWeatherReportService */
    public WeatherBugWeatherReportService() {
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch(Throwable th) {}
    }

    /**
     *
     * @param zipCode
     * @param cityCode
     * @param latitude
     * @param longitude
     * @param unitType
     * @param cityType
     * @return an instance of RestResponse
     */
    public static RestResponse getForecastRSS(String zipCode, String cityCode, String latitude, String longitude, String unitType, String cityType) throws IOException {
        String apiKey = WeatherBugWeatherReportServiceAuthenticator.getApiKey();
        String[][] pathParams = new String[][]{};
        String[][] queryParams = new String[][]{{"ACode", "" + apiKey + ""}, {"ZipCode", zipCode}, {"CityCode", cityCode}, {"Latitude", latitude}, {"Longitude", longitude}, {"UnitType", unitType}, {"CityType", cityType}};
        RestConnection conn = new RestConnection("http://api.wxbug.net/getForecastRSS.aspx", pathParams, queryParams);
        sleep(1000);
        return conn.get(null);
    }
}
