<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>Hello world!</h1>
	<section>
		<form action="login" method="POST">
			<p>
				Username: <input type="text" name="username" size="25"
					required="required">
			</p>

		</form>
	</section>
	<P>User: ${user.name} Followers: ${user.followersCount} Follows:
		${user.friendsCount}.</P>
	<div>
		<c:if test="${not empty results}">
			<div id="dailies">
				<c:forEach var="result" items="${results}">
					<div class="tweetRow">
						<p>${result.fromUser} ${result.text}</p>

					</div>
				</c:forEach>
			</div>
		</c:if>
	</div>
	<div id="main-content">

		<c:if test="${not empty publicTweets}">
			<div id="dailies">
				<c:forEach var="tweet" items="${publicTweets}">
					<div class="tweetRow">
						<img class="tweetPic" src="${tweet.profileImageUrl}"> <span
							class="tweetUser"><c:out value="${tweet.fromUser}" /></span> <span
							class="tweetContent"> <span class="tweetText"><c:out
									value="${tweet.text}" /></span><br> <span class="tweetDate"><c:out
									value="${tweet.createdAt}" /></span>
						</span>
					</div>
				</c:forEach>
			</div>
		</c:if>


	</div>

</body>
</html>
