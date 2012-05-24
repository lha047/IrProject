<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Random"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>

			<c:forEach var="tweet" items="${results.tweets}">
				<article id="<c:out value="${tweet.id}" />" class="left two_half_cols tweet_container five_stars"><!-- <c:out value="${tweet.tweetRank}" /> -->
					<div class="tweet_border">
						<section class="padding tweet" style="opacity: <c:out value="${tweet.userInfo.fitnessScore}" />;">
							<section class="cf user">
								<div class="avatar" style="background:url('${tweet.profileImageUrl}') no-repeat; width: 48px;height: 48px;border-radius:5px;">&nbsp;</div><!-- <img class="avatar" src="${tweet.profileImageUrl}">-->
								<strong class="username">
									<c:out value="${tweet.userInfo.name}" />
								</strong><br />
								<small style="color:#666;">@<c:out value="${tweet.fromUser}" /></small>
								
							</section>
							<section class="tweet_date">
								<fmt:formatDate value="${tweet.createdAt}" pattern="dd.MMM HH:mm" />
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
					</div>
				</article>
			</c:forEach>