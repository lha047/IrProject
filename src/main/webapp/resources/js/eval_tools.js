

/*
	toggles full opacity for tweets
*/
function toggleOpacity() {
	$('.tweet').toggleClass('full_opacity');
}

/*
	Start ranking
	Appends rating buttons to tweets
	Adds AJAX event which submits ratings
*/
function startRankingEval() {
	$('.tweet').append('<div class="dev_eval" data-user-rank="<c:out value="${tweet.userInfo.fitnessScore}" />" data-tweet-rank="<c:out value="${tweet.tweetRank}" />"><strong>Relevance:</strong><br /><span class="ranking_btn btn" data-user-ranking="0.0">0</span><span class="ranking_btn btn" data-user-ranking="0.1">1</span><span class="ranking_btn btn" data-user-ranking="0.2">2</span><span class="ranking_btn btn" data-user-ranking="0.3">3</span><span class="ranking_btn btn" data-user-ranking="0.4">4</span><span class="ranking_btn btn" data-user-ranking="0.5">5</span><span class="ranking_btn btn" data-user-ranking="0.6">6</span><br /><span class="ranking_btn btn" data-user-ranking="0.7">7</span><span class="ranking_btn btn" data-user-ranking="0.8">8</span><span class="ranking_btn btn" data-user-ranking="0.9">9</span><span class="ranking_btn btn" data-user-ranking="1.0">10</span></div>');
	$('#tweets').masonry('reload');
	
	$('.ranking_btn').click(function () {
		console.log($(this).attr('data-user-ranking'));
		console.log($(this).parent().attr('data-user-rank'));
		
		$(this).parent().html('submitting').addClass('spinner');
		
		console.log('start eval submit');
		
		console.log('eval?searchId=<c:out value="${searchId}" />' + "&ourRank=" + $(this).parent().attr('data-user-rank') + "&theirRank=" + $(this).attr('data-user-ranking'));
		
		$.ajax({
			  url: 'eval?searchId= <c:out value="${searchId}" />' + "&ourRank=" + $(this).parent().attr('data-user-rank') + "&theirRank=" + $(this).attr('data-user-ranking'),
			  context: '#more'
			}).done(function() {
				
				console.log('submitted eval');
				// remove spinner
				$(this).removeClass('spinner');
			});
	});
				
}


// button events
$('#dev_tools_toggle').click(function () {
	console.log('clicked dev tools');
	$(this).parent().find('.tools').toggle();
});

$('.opacity_toggle').click(function () {
	console.log('opacity toggle');
	toggleOpacity();
});

$('.ranking_eval_toggle').click(function () {
	console.log('startEval');
	startRankingEval();
	toggleOpacity();
});