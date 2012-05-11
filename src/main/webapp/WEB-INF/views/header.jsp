<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
</head>
<body class="awesome">
<div id="wrapper">

	<header class="center cf">
		<a href="/info323" class="logo"><img src="resources/images/feedjam.png" alt="feedjam" /></a>
		<a href="/info323" class="logo-white"><img src="resources/images/feedjam-white.png" alt="feedjam" /></a>
		<form action="search" type="GET" class="search" id="search_form">
			<input type="text" id="q" name="q" placeholder="search terms" <c:if test="${not empty query}">value="<c:out value="${query}" />"</c:if> />
			<input type="hidden" id="resultsPerPage" name="resultsPerPage" value="20" />
			<button>Search</button>
		</form>
	</header>
	