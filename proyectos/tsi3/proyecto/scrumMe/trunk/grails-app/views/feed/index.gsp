<%@ page import="edu.tsi3.scrumme.util.RSSFeed"%>
<html>
<head>
<title>Feed</title>

</head>
<body>

<g:each in="${feedList}" var="feedInstance">

	<a href="${fieldValue(bean: feedInstance, field: "link")}">
	 ${fieldValue(bean: feedInstance, field: "titulo")}
	</a>
	${fieldValue(bean: feedInstance, field: "desc")}

</g:each>

</body>
</html>