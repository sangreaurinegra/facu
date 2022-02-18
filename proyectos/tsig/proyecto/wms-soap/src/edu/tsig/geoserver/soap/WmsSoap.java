package edu.tsig.geoserver.soap;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;


import edu.tsig.utils.http.ClienteHttp;



@WebService(name = "WmsSoap" )//, targetNamespace = "http://wms-soap/WmsSoap")
public class WmsSoap {
	
//	REQUEST=GetFeatureInfo 	R 	Request name.
//	VERSION=version* 	R 	Request version. Valid values are 1.0.0, 1.1.0, or 1.1.1.
//	SRS=EPSG:id_code* 	R 	Spatial Reference System (SRS) identifier the map is returned in. Identifiers correspond to coordinate system ID codes found in the ArcXML Programmer�s Reference Guide.
//	BBOX=minx,miny,maxx,maxy* 	R 	Bounding box corners (lower left, upper right). Values must be in the units of the specified SRS.
//	WIDTH=output_width* 	R 	Width in pixels of resulting map image.
//	HEIGHT=output_height* 	R 	Height in pixels of resulting map image.
//	QUERY_LAYERS=layer_list 	R 	Comma-separated list of one or more map layers to be queried. Values in the list correspond to the layer <name> values in the Capabilities file. This name corresponds to the layer id in ArcXML.
//	X=pixel_column 	R 	X coordinate in pixels of feature measured from upper left corner of the map.
//	Y=pixel_row 	R 	Y coordinate in pixels of feature measured from upper left corner of the map.

//	INFO_FORMAT=output_format 	O 	Return format of feature information. The default value is application/vnd.ogc.wms_xml. Other options are text/xml, text/html, and text/plain.
//	FEATURE_COUNT=number 	O 	Number of features per layer allowed. The default is 1.
//	EXCEPTIONS=exception_format 	O 	The format in which exceptions are reported. The default and only supported value is application/vnd.ogc.se_xml.
//	SERVICENAME=service_name
//	(vendor specific) 	O 	By default, the capabilities of the default WMS service is queried. If you want to use a non-default service, the service name must be included in the URL.

//	 LAYERS=departamento  R
//	 FORMAT=image/gif	 R
	
	
	@WebMethod
	public String getFeatureInfo(@WebParam(name="VERSION") String version  ,@WebParam(name="SRS") String srs, @WebParam(name="BBOX") String bbox, 
			@WebParam(name="WIDTH")String width, @WebParam(name="HEIGHT") String height, @WebParam(name="QUERY_LAYERS") String queryLayers,  @WebParam(name="X") String x, @WebParam(name="Y") String y,  
			@WebParam(name="LAYERS")String layers, @WebParam(name="FORMAT") String format,
			@WebParam(name="INFO_FORMAT") String infoFormat, @WebParam(name="FEATURE_COUNT") String featureCount, @WebParam(name="EXCEPTIONS") String exceptions, @WebParam(name="SERVICENAME") String servicename
	){
		String ret = "xml";
		
		String url = "http://localhost:8080/geoserver/wms";
		
		HashMap<String,String> map = new HashMap<String,String>();
		
		map.put("REQUEST", "GetFeatureInfo");
		map.put("VERSION", version);
		map.put("SRS",  srs); 
		map.put("BBOX", bbox); 
		map.put("WIDTH", width);
		map.put("HEIGHT", height); 
		map.put("QUERY_LAYERS", queryLayers);
		map.put("X",  x);
		map.put("Y",  y);
		
		map.put("LAYERS",layers);
		map.put("FORMAT",format);
		
		if(infoFormat!=null) map.put("INFO_FORMAT", infoFormat); 
		if(featureCount!=null) map.put("FEATURE_COUNT", featureCount); 
		if(exceptions!=null) map.put("EXCEPTIONS",exceptions); 
		if(servicename!=null) map.put("SERVICENAME", servicename);
		ret = ClienteHttp.executeGet(url, map);
		
		return ret;
	} 
	
	
}
