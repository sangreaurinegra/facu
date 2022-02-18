<%@ page import="com.infosoftglobal.fusioncharts.FusionChartsCreator"%>
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
</head>
<body>


<div class="body">
<%
	//Create the chart - Column 3D Chart with data from Data/Data.xml
	String chartHTMLCode = FusionChartsCreator.createChartHTML(
			"/scrumMe/swf/Line.swf",
			"http://localhost:8080/scrumMe/chart/burndown/1", "",
			"tsi 3 2010", 600, 300, false);
%> <%=chartHTMLCode%></div>


</body>
</html>