<%@page import="java.util.Random"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

			<c:forEach var="tweet" items="${results.tweets}">
				<article class="left two_half_cols tweet_container five_stars" style="opacity: 0.<c:out value="${tweet.userInfo.fitnessScore}" />;"><!-- <c:out value="${tweet.tweetRank}" /> -->
					
					<section class="padding tweet">
						<section class="cf user">
							<img class="avatar" src="${tweet.profileImageUrl}">
							<strong class="username">
								<c:out value="${tweet.userInfo.name}" />
							</strong><br />
							<small style="color:#666;">@<c:out value="${tweet.fromUser}" /></small>
							
						</section>
						<section class="user_info">
								<span class="user_score">score<br /><strong><c:out value="${tweet.userInfo.fitnessScore}" /></strong></span>
								<ul class="five_cols scores">
									<li>following: <c:out value="${tweet.userInfo.favoritesCount}" /></li>
									<li>followers: <c:out value="${tweet.userInfo.followersCount}" /></li>
									<li>friends: <c:out value="${tweet.userInfo.friendsCount}" /></li>
								</ul>
								<p class="description">
									<c:out value="${tweet.userInfo.description}" />
								</p>
								<a class="left dark btn" href="<c:out value="${tweet.userInfo.url}" />">Homepage</a>
								<a class="right dark btn" href="<c:out value="${tweet.userInfo.profileUrl}" />">Profile</a>
							</section>
						
						 
						<p class="clear tweet_content">
							<c:out value="${tweet.text}" />
						</p>
						
					</section>

				</article>
			</c:forEach>