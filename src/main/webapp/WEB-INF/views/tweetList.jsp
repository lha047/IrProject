<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

			<c:forEach var="tweet" items="${results.tweets}">
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