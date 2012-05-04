<!doctype html />
<!-- 
	Project: Info323/Feedjam
	Frontend: Torstein Thune
	Backend: Snorre Davøen, Lisa Halvorsen
	-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<link rel="icon" type="image/x-icon" href="resources/images/favicon.ico" />
	<link rel="icon" type="image/png" href="resources/images/favicon.png" />
	<title>FeedJam: A New Twitter Experience</title>
	<link rel="stylesheet" type="text/css" href="resources/style.css" />
</head>
<body class="awesome">
<div id="wrapper">

	<jsp:include page="header.jsp"></jsp:include>
	
	<c:if test="${not empty publicTweets}">
		<section class="ten_cols no_padding cf tweet_wrapper">
			<c:forEach var="tweet" items="${publicTweets}">
				<article class="left two_half_cols tweet_container five_stars">
					<section class="rating">
						UsrRnk: 10, TweetRnk: 5
					</section>
					<section class="padding tweet">
						<span class="avatar">
							<img src="${tweet.profileImageUrl}">
						</span>
						<strong class="username"><c:out value="${tweet.fromUser}" /></strong> 
						<c:out value="${tweet.text}" />
						<c:out value="${tweet.createdAt}" />
					</section>
					<div class="inline_absolute_wrapper">
						<section class="retweets">
							<strong>RT</strong>
						</section>
						<section class="replies">
							<strong>Replies</strong>
						</section>
					</div>
				</article>
			</c:forEach>
		</section>
	</c:if>
	
	
		<c:if test="${not empty results}">
			<div id="dailies">
				<c:forEach var="result" items="${results}">
					<div class="tweetRow">
						<p>${result.fromUser} ${result.text}</p>

					</div>
				</c:forEach>
			</div>
		</c:if>
	

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
	<jsp:include page="footer.jsp"></jsp:include>
</div>

</body>
</html>
