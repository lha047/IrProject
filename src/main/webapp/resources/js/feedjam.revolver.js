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

		