<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
</head>
<body class="awesome">
<div id="wrapper">

	<header class="center cf">
		<a href="/feedjam" class="logo"><img src="resources/images/feedjam.png" alt="feedjam" /></a>
		<a href="/feedjam" class="logo-white"><img src="resources/images/feedjam-white.png" alt="feedjam" /></a>
		<form method="GET" action="search" class="search" id="search_form">
			<input type="text" id="q" name="q" placeholder="search terms" <c:if test="${not empty query}">value="<c:out value="${query}" />"</c:if> />
			<input type="hidden" id="resultsPerPage" name="resultsPerPage" value="20" />
			<button type="submit">Search</button>
		</form>
		<div id="information_toggle" class="btn togglebutton">i</div>
		<div id="info" class="infobox information hidden">
			<strong>Feedjam</strong> is an experiment which tries to implement a PageRank-esque algorithm to rate and sort Tweets. Developed by Snorre Davøen, Lisa Halvorsen and Torstein Thune @ UiB
			Read more in the report or check out the source.
		</div>
	</header>
	