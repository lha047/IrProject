$(document).ready(function() {

	
	
	// toggle user info
	var usrClickVar = $('.user');
	function usrClick() {
		usrClickVar.unbind();
		usrClickVar = $('.user');
		usrClickVar.click(function () {
			active = true;
			console.log("click");
			$(this).toggleClass('active').parent().find('.user_info').fadeToggle("fast", "linear").parent().toggleClass("full_opacity");
		});
	}
	
	// bind click on user name to opening user info
	usrClickVar.click(usrClick());

				
	// run masonry
	var $container = $('#tweets');
	$container.imagesLoaded(function(){
	  $container.masonry({
		itemSelector : '.tweet_container',
	  });
	});
				
	// ajax logic
	var pageIterator = 2;
	var next_url = nextUrl.split("&amp;"); 
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
			$('#tweets').append(data).masonry('reload');
			console.log("ajax returned");
			usrClick();
		});
		
	});
});	