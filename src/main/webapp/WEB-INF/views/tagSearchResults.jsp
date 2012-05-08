<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<jsp:include page="htmlheader.jsp"></jsp:include>
	<title>FeedJam: A New Twitter Experience</title>

	
<jsp:include page="header.jsp"></jsp:include>
	
	<c:if test="${not empty results}">
			<jsp:include page="tweetList.jsp"></jsp:include>
	</c:if>
	
	<c:if test="${not empty nextPageUrl}">
		<div id="more" class="ten_cols">
			
			<div class="two_cols block btn center">
				Load more
			</div>
		</div>
		
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
		<script type="text/javascript" src="resources/js/jquery.masonry.min.js"></script>
		
		<script type="text/javascript">
			$(document).ready(function() {
				
				// run masonry
				var $container = $('#tweets');
				$container.imagesLoaded(function(){
				  $container.masonry({
				    itemSelector : '.tweet_container',
				  });
				});
				
				// ajax logic
				var pageIterator = 2;
				var next_url = '<c:out value="${nextPageUrl}" />'.split("&amp;"); 
				var max_id = next_url[1];
				
				console.log(next_url);
				
				$('#more').click(function() {
					console.log("start ajax request for '" + "search/ajax?q=test&page="+pageIterator+"&"+max_id+"&"+next_url[3]+"'");
					
					$('#more').find('.btn').addClass('disabled no_text spinner');
					
					
					$.ajax({
					  url: "search/ajax?q=test&page="+pageIterator+"&"+max_id+"&"+next_url[3],
					  context: '#more'
					}).done(function(data) { 
						$('#more').find('.btn').removeClass('disabled no_text spinner');
					 	pageIterator += 1;
					 	$('#tweets').append(data).masonry('appended', data);
						console.log("ajax returned");
					});
					
				});
			});
		</script>
	</c:if>
<jsp:include page="footer.jsp"></jsp:include>

