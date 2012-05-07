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
			<div class="two_cols center hidden spinner"></div>
			<div class="two_cols block btn center">
				Load more
			</button>
		</div>
		
		<script type="text/javascript" src="resources/js/jquery-1.7.2.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				var pageIterator = 2;
				var next_url = '<c:out value="${nextPageUrl}" />'.split("&amp;"); 
				var max_id = next_url[1];
				
				$('#more').click(function() {
					console.log("start ajax request for '" + "search/ajax?q=test&page="+pageIterator+"&"+max_id+"&"+next_url[3]+"'");
					
					$('#more').find('.spinner').toggle().parent().find('.btn').toggle();
					
					
					$.ajax({
					  url: "search/ajax?q=test&page="+pageIterator+"&"+max_id+"&"+next_url[3],
					  context: '#more'
					}).done(function(data) { 
						$('#more').find('.spinner').toggle().parent().find('.btn').toggle();
					 	pageIterator += 1;
					 	$('#tweets').append(data);
						console.log("ajax returned");
					});
					
				});
			});
		</script>
	</c:if>
<jsp:include page="footer.jsp"></jsp:include>

