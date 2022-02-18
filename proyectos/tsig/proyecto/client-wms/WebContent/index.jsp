<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Cliente WMS</title>
	<script type="text/javascript" src="../includes/OpenLayers-2.10/OpenLayers.js"></script>
	
	<style type="text/css">
		#infoDiv { 
            float: right;
        }
        #mapDiv { 
            width: 470px; 
            height: 522px; 
            border: solid 2px #808080;
            float: left;
        }
    </style>
	<script>
		var map;
		var layer;
		var bounds;
		var options;
		var info;
						 
		function showMap(pLayer){
			if (pLayer == "null"){
				layer = 'tsig:departamento'; 
			}else {
				layer = pLayer;
			}
			
			bounds = new OpenLayers.Bounds(
                366582.29, 6127927.1,
                858252.015, 6671738.212
            );
            options = {
                maxExtent: bounds,
                maxResolution: 2124.2621562500026,
                projection: "EPSG:32721",
                units: 'm'
            };

			map = new OpenLayers.Map("mapDiv", options);
			var servicio_esb = "<%= request.getParameter("s") %>";
			if(servicio_esb == "null" )
				servicio_esb = "rest";
			layer = new OpenLayers.Layer.WMS( 
											layer,
										    "http://localhost:8180/wms-rest/http/"+servicio_esb+"?", 
										    {layers: layer,
											 format: 'image/gif'}
										);
			map.addLayer(layer);

			info = new OpenLayers.Control.WMSGetFeatureInfo({
	            url: "http://localhost:8180/wms-rest/http/"+servicio_esb+"?", 
	            queryVisible: true,
	            eventListeners: {
	                getfeatureinfo: function(event) {
	                	 document.getElementById('infoDiv').innerHTML = event.text;
		            }
	            }
	        });
	        map.addControl(info);
	        info.activate();

	        map.addControl(new OpenLayers.Control.LayerSwitcher());
	        map.zoomToMaxExtent();
	    }
	</script>
</head>
<body  onload="showMap('<%=request.getParameter("layer")%>')">
	<div id="containerDiv">
		<div id="mapDiv"></div>
	    <div id="infoDiv"></div>
    </div>  
</body>
</html>