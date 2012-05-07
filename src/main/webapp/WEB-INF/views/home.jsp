<jsp:include page="htmlheader.jsp"></jsp:include>
	<title>FeedJam: A New Twitter Experience</title>
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

<jsp:include page="footer.jsp"></jsp:include>
