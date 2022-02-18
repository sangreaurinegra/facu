/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.tsi2.gr5.ejb;

import edu.tsi2.gr5.vo.Cache;
import edu.tsi2.gr5.ws.Gr5WS2Service;
import edu.tsi2.gr5.ws.Gr5WSService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.ws.WebServiceRef;
import org.netbeans.saas.weatherbug.WeatherBugWeatherReportService;
import org.netbeans.saas.RestResponse;

/**
 *
 * @author dell
 */
@Stateless
public class ServicioCompuestoBean implements ServicioCompuestoRemote {

    @WebServiceRef(wsdlLocation = "META-INF/wsdl/localhost_8080/misWS/gr5WS2.wsdl")
    private Gr5WS2Service service_1;
    @WebServiceRef(wsdlLocation = "META-INF/wsdl/localhost_8080/misWS/gr5WS.wsdl")
    private Gr5WSService service;

    public String operacion1() {
        return "hola desde ejb";
    }

    public String estadoDelTiempo(String zipCode) {

        String ret = "";

        String parametros = zipCode;
        String clave = "ServicioCompuesto|estadoDelTiempo|" + parametros;
        String valor = getCache().buscarEnCache(clave);
        if ( valor==null  ) {
            try {
                String cityCode = null;
                String latitude = null;
                String longitude = null;
                String unitType = null;
                String cityType = null;

                RestResponse result = WeatherBugWeatherReportService.getForecastRSS(zipCode, cityCode, latitude, longitude, unitType, cityType);
                if (result.getDataAsObject(weatherbug.weatherreportservice.rss.Rss.class) instanceof weatherbug.weatherreportservice.rss.Rss) {
                    weatherbug.weatherreportservice.rss.Rss resultObj = result.getDataAsObject(weatherbug.weatherreportservice.rss.Rss.class);

                }
                ret = result.getDataAsString();
                //TODO - Uncomment the print Statement below to print result.
                System.out.println("The SaasService returned: " + result.getDataAsString());
                getCache().guardarEnCache(clave, ret);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }else{
            ret =valor;
        }
        return ret;
    }

    public String sumaMultiplicacion(int a, int b) {
        String ret = null;
        String parametros = a + "|" + b;
        String clave = "ServicioCompuesto|sumaMultiplicacion|" + parametros;
        String valor = getCache().buscarEnCache(clave);
         Integer ok = 1;
        Integer enCache=1;
        if (valor == null) {
            enCache=0;
            java.lang.Integer resultSuma = suma(a, b);
            java.lang.Integer resultProducto = producto(a, b);
            ret = "La suma es " + resultSuma + " y el Producto " + resultProducto;
            getCache().guardarEnCache(clave, ret);
        }
        else {
            ret = valor;
        }
        getLogActividades().loguear("ServicioCompuesto","sumaMultiplicacion",parametros ,ok, enCache);
        return ret ;
    }

    public String sumaRestaMultiplicacion(int a, int b) {

        String ret = "";
        String parametros = a + "|" + b;
        String clave = "ServicioCompuesto|sumaRestaMultiplicacion|" + parametros;
        String valor = getCache().buscarEnCache(clave);
         Integer ok = 1;
        Integer enCache=1;
        if (valor == null) {
            enCache=0;
            Integer resultSuma = suma(a, b);
            Integer resultResta = resta(a, b);
            Integer resultProducto = producto(a, b);
            ret = "La resta es " + resultResta + " el producto es " + resultProducto + " la suma es " + resultSuma;
            //guardo en cache
            getCache().guardarEnCache(clave, ret);

        } else {// caso que el cache tiene el resultado compuesto
            ret = valor;
        }

         getLogActividades().loguear("ServicioCompuesto","sumaRestaMultiplicacion",parametros ,ok, enCache);
        return ret;

    }

    private Integer suma(int a, int b) {
        String parametros = a + "|" + b;
        String claveSuma = "ServicioCompuesto|suma|" + parametros;
        String valorSuma = getCache().buscarEnCache(claveSuma);
        java.lang.Integer resultSuma = 0;
         Integer ok = 1;
        Integer enCache=1;
        try { // Call Web Service Operation
            if (valorSuma == null) {
                enCache=0;
                edu.tsi2.gr5.ws.Gr5WS port = service.getGr5WSPort();
                // TODO initialize WS operation arguments here

                // TODO process result here
                resultSuma = port.suma(a, b);
                System.out.println("Result = " + resultSuma);
                getCache().guardarEnCache(claveSuma, resultSuma.toString());
            } else {
                resultSuma = Integer.valueOf(valorSuma);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
             ok = 0;
            }
         getLogActividades().loguear("ServicioCompuesto","suma",parametros ,ok, enCache);
        return resultSuma;
    }

    private Integer resta(int a, int b) {
        String parametros = a + "|" + b;
        String claveResta = "ServicioCompuesto|resta|" + parametros;
        java.lang.Integer resultResta = 0;
        String valorResta = getCache().buscarEnCache(claveResta);
        Integer ok = 1;
        Integer enCache=1;
        try { // Call Web Service

            if (valorResta == null) {
                enCache=0;
                edu.tsi2.gr5.ws.Gr5WS2 port = service_1.getGr5WS2Port();
                // TODO initialize WS operation arguments here

                // TODO process result here
                resultResta = port.resta(a, b);
                System.out.println("Result = " + resultResta);
                getCache().guardarEnCache(claveResta, resultResta.toString());

            } else {
                resultResta = Integer.valueOf(valorResta);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
            ok=0;
            }
        getLogActividades().loguear("ServicioCompuesto","resta",parametros ,ok, enCache);
        return resultResta;
    }

    private Integer producto(int a, int b) {

        String parametros = a + "|" + b;
        String claveMultiplicacion = "ServicioCompuesto|multiplicacion|" + parametros;

        java.lang.Integer resultProducto = 0;
        Integer ok =1;
        Integer enCache=1;
        String valorMultiplicacion = getCache().buscarEnCache(claveMultiplicacion);
        try { // Call Web Service Operation

            if (valorMultiplicacion == null) {
                enCache=0;
                edu.tsi2.gr5.ws.Gr5WS port = service.getGr5WSPort();
                // TODO initialize WS operation arguments here

                // TODO process result here
                resultProducto = port.producto(a, b);
                System.out.println("Result = " + resultProducto);
                getCache().guardarEnCache(claveMultiplicacion, resultProducto.toString());

            } else {
                resultProducto = Integer.valueOf(valorMultiplicacion);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
            ok =0;
         }

        getLogActividades().loguear("ServicioCompuesto","multiplicacion",parametros ,ok, enCache);

        return resultProducto;
    }

    private CacheFacadeRemote getCache() {

        CacheFacadeRemote ret = null;
        InitialContext cntxt = null;
        try {
            cntxt = new InitialContext();
        } catch (NamingException ex) {
            Logger.getLogger(ServicioCompuestoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (cntxt != null) {
            try {
                ret = (CacheFacadeRemote) cntxt.lookup("CacheFacade/remote");
            } catch (NamingException ex) {
                Logger.getLogger(ServicioCompuestoBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }

    private LogActividadesFacadeRemote getLogActividades() {

        LogActividadesFacadeRemote ret = null;
        InitialContext cntxt = null;
        try {
            cntxt = new InitialContext();
        } catch (NamingException ex) {
            Logger.getLogger(ServicioCompuestoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (cntxt != null) {
            try {
                ret = (LogActividadesFacadeRemote) cntxt.lookup("LogActividadesFacade/remote");
            } catch (NamingException ex) {
                Logger.getLogger(ServicioCompuestoBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }
}
