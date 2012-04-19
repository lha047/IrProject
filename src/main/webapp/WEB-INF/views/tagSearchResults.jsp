<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>Hello world!</h1>
	<p>Your search ${query} returned:</p>
	<p>${results}</p>
	<div id="main-content">
		<div>
			<c:if test="${not empty results}">

					<c:forEach var="result" items="${results}">
						<div class="tweetRow">
							<p>${result.fromUser} ${result.text}</p>

						</div>
					</c:forEach>

			</c:if>
			<c:if test="${empty results}">
				<p>Nothing matched your search...</p>
			</c:if>
		</div>
	</div>


</body>
</html>
