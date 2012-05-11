<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<jsp:include page="htmlheader.jsp"></jsp:include>
	<title>FeedJam: A New Twitter Experience</title>

	
<jsp:include page="header.jsp"></jsp:include>

<!-- Add some JSP to print todays trends somewhere here... Or maybe in header. -->
	<h1 class="block ten_cols center trending_topics text_center">Wordwide trends last 24 hours</h1>
	<div class="slider ten_cols">
		<div class="prev"></div>
		<div class="next"></div>
		<ul id="trending_topics" class="trending_topics ten_cols slides">
			<jsp:include page="trendingList.jsp"></jsp:include>
		</ul>
	</div>
	<script type="text/javascript">
		$('#trending_topics .slide:first-child').removeClass('hidden');

		var slider =  $('.slider');

        var revolver = slider.find('.slides').revolver({transition: {type: 'slide', easing: 'swing'}, autoplay: true}).data('revolver');

		slider.find('.goto').eq(0).addClass('active');

		

		slider.find('.next').click(function() { 

			if(!revolver.isAnimating) {

				slider.find('.goto').removeClass('active');

				slider.find('.goto').eq(revolver.nextSlide).addClass('active');

				revolver.next();

			}

				

		});

		slider.find('.prev').click(function() { 

			if(!revolver.isAnimating) {

				slider.find('.goto').removeClass('active');

				slider.find('.goto').eq(revolver.currentSlide-1).addClass('active');

				revolver.previous({direction:'right'}); 

			}

		});

		

		slider.find('.goto').click(function(e){

            e.preventDefault();

			var goingTo = $(this).data('goto');

			if(goingTo < revolver.currentSlide) {

				revolver.goTo(goingTo, {direction:'right'});

			} else {

				revolver.goTo(goingTo);

			}

			slider.find('.goto').removeClass('active');

			slider.find('.goto').eq(goingTo).addClass('active');

        });

		
	</script>
<jsp:include page="footer.jsp"></jsp:include>
