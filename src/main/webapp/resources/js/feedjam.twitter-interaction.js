// hvis returnerer error, skriv ut og terminer

// field variables used to build search queries to twitter
var page = 1;
var max_id = 0;
var query = "";
var rpp = 20;

// catches all AJAX errors not caused by jsnonp-requests (namely interaction with the feedjam server)
jQuery(document).ajaxError(function(event, request, settings){
   console.log("**********ERROR:***********\nevent: " + event + "\nrequest: " + request + "nsettings: " + settings);
});

// pushes loadingdata to the loading scroller
function loadingMessage(message) {
	$loading = $('#loading');
	if(!$('#loading').length) {
		$('#more').append('<div id="loading" class="two_cols center">' + message + '</div>');
	} else {
		$('#loading').html(message);
	}
	// todo : sette inn meldinger om hva som skjer i loading knappen (hvis den fortsatt er loading)
	
}

// pushes error messages to the frontend
function displayError(message) {
	$('#more').before('<div class="error">' + message + '<div class="close"></div></div>');
	
	// bind remove to close Button
	$('.error').find('.close').click(function () {
		$(this).parent().remove();
	});
	
	// stop more spinning
	$('#more').find('.btn').removeClass('disabled no_text spinner');
	
	// remove loading message
	$('#loading').remove();
}

/*
	User functions
	--------------
*/

// control user ajax requests
function usrRequests(searchRequest, rpp, searchQuery, usrs) {
	loadingMessage('Getting user data from Twitter');
	if(usrs) {
		// get user data
		getUsersFromTwitter(usrs, searchQuery, searchRequest, rpp);
		
	} else {
		// get the view
		console.log("No users to fetch, getting view");
		usersToServer('', searchQuery, searchRequest, rpp, false);
	}
}

// get users from twitter
function getUsersFromTwitter(usrs, searchQuery, searchRequest, rpp) {
	console.log('REQUEST: Twitter API: requesting user data for: ' + usrs);
	
	$.ajax({
		url:'https://api.twitter.com/1/users/lookup.json?user_id=' + usrs + '&include_entities=false&callback=?',
		dataType:'jsonp',
		timeout: 5000,
		success:function(usrJSONData){
			console.log('RESPONSE: Twitter API: received user data for ' + usrs);
			usersToServer(usrJSONData, searchQuery, searchRequest, rpp, usrs);
		},
		error:function(){
			console.log('****** ERROR: getUsersFromTwitter() failed *******'); 
			displayError('Could not get userdata: out of Twitter API requests');
		},
		complete:function(){}
	});
	
	/*$.getJSON('https://api.twitter.com/1/users/lookup.json?user_id=' + usrs + '&include_entities=false&callback=?', function(usrJSONData) {
		console.log('RESPONSE: Twitter API: received user data for ' + usrs);
		usersToServer(usrJSONData, searchQuery, searchRequest, rpp);
	});*/
}

// sends returned user data to controller (../ajaj/processUsers)
function usersToServer(usrJSONData, searchQuery, searchRequest, rpp, usrs) {
	console.log('SERVER POST: sending users to server');
	loadingMessage('Crunching data and generating View');
	// console.log(searchRequest + ' \n ################# \n' + usrJSONData);
	var out = "";
	if(usrJSONData != "") {
		out = '{"users":' + JSON.stringify(usrJSONData) + '}';
	}
	
	// console.log(out);
		
	if(!rpp) {
		rpp = 20;
	}
	$.post("ajaj/processUsers", { 
		users: out, 
		searchQuery: searchQuery, 
		searchRequest: JSON.stringify(searchRequest), 
		rpp: rpp
	},
   function(view) {
		console.log('RETURNED VIEW');
		doTheFunkyBusiness(view);
		// followingFollowers for users
		if(usrs) {
			console.log('Starting getting following and followers');
			var usrLst = usrs.split(",");
		
			// get followers and following for all users
			for(var i=0;i<usrLst.length-1;i++) {
				getFollowingFromTwitter(usrLst[i]);
				getFollowersFromTwitter(usrLst[i]);
			}
		}
   });
}

// sends following to controller (ajaj/processFollowing)
function followingToServer(userId, followingData) {
	$.post("ajaj/processFollowing", { 
		userId: userId.toString(), 
		following: JSON.stringify(followingData)
	},
   function(data) {
     console.log("SERVER RESPONSE: added following for " + userId);
   });
}

// sends followers to controller (ajaj/processFollowers)
function followersToServer(userId, followersData) {
	$.post("ajaj/processFollowers", { 
		userId: userId.toString(), 
		followers: JSON.stringify(followersData)
	},
   function(data) {
     console.log("SERVER RESPONSE: added followers for " + userId);
   });
}

// sends requests for following for users to twitter
function getFollowingFromTwitter(usr) {
	console.log('REQUEST: Twitter API: requesting following for ' + usr);
	
	$.ajax({
		url:'https://api.twitter.com/1/friends/ids.json?cursor=-1&user_id=' + usr + '&callback=?',
		dataType:'jsonp',
		timeout: 5000,
		success:function(usrFollowingJSON){
			console.log('RESPONSE: Twitter API: received response for following user: ' + usr);
			followingToServer(usr, usrFollowingJSON);
		},
		error:function(){
			console.log('****** ERROR: getFollowingFromTwitter() failed *******');                   
		},
		complete:function(){                    
		}
	});
	
	/*$.getJSON('https://api.twitter.com/1/friends/ids.json?cursor=-1&user_id=' + usr + '&callback=?', function(usrFollowingJSON) {
		console.log('RESPONSE: Twitter API: received response for following user: ' + usr);
		followingToServer(usr, usrFollowingJSON);
	});*/
}

// sends requests for followers for users to twitter
function getFollowersFromTwitter(usr) {
	console.log('REQUEST: Twitter API: requesting followers for ' + usr);
	
	$.ajax({
		url:'https://api.twitter.com/1/followers/ids.json?cursor=-1&user_id=' + usr + '&callback=?',
		dataType:'jsonp',
		timeout: 5000,
		success:function(usrFollowersJSON){
			console.log('RESPONSE: Twitter API: received response for followers for user: ' + usr);
			followersToServer(usr, usrFollowersJSON);
		},
		error:function(){
			console.log('****** ERROR: getFollowersFromTwitter() failed *******');
		},
		complete:function(){                    
		}
	});
	
	/*$.getJSON('https://api.twitter.com/1/followers/ids.json?cursor=-1&user_id=' + usr + '&callback=?', function(usrFollowersJSON) {
		console.log('RESPONSE: Twitter API: received response for followers for user: ' + usr);
		followersToServer(usr, usrFollowersJSON);
	});*/
}

/*
	Search functions
	--------------
*/

// sends the JSON response from twitter to controller (ajaj/processSearch)
function tweetsToServer(searchResponse, rpp, searchQuery) {
	loadingMessage('crunching tweets');
	// console.log("searchQuery: " + searchQuery + " and rpp: " + rpp + " returned: " + JSON.stringify(searchResponse));
	$.post("ajaj/processSearch", { 
		searchResponse: JSON.stringify(searchResponse) 
	},
   function(usersToCheck) {
	 usrRequests(searchResponse, rpp, searchQuery, usersToCheck);
   });
}

// sends query to Twitter
function searchTweets(searchQuery, rpp) {
	console.log('searching: ' + searchQuery);
	
	loadingMessage('querying Twitter for tweets');
	
	var query_id = "";
	if(max_id != 0) {
		query_id = "&max_id=" + max_id;
	}
	console.log('Running query: http://search.twitter.com/search.json?q=' + searchQuery + '&rpp=' + rpp + '&page=' + page + query_id + '&include_entities=true&result_type=mixed&callback=?');
	
	$.ajax({
		url:'http://search.twitter.com/search.json?q=' + searchQuery + '&rpp=' + rpp + '&page=' + page + query_id + '&include_entities=true&result_type=mixed&callback=?',
		dataType:'jsonp',
		timeout: 5000,
		success:function(searchResponse){
			console.log('received response for search: ' + searchResponse);
	  
			page = searchResponse.page + 1;
			max_id = searchResponse.max_id;
			query = searchResponse.query;
			rpp = rpp;
			
			console.log("page: " + page + "\nmax_id: " + max_id + "\nquery: " + query + "\nrpp: " + rpp);
			
			tweetsToServer(searchResponse, rpp, searchQuery);
		},
		error:function(){
			console.log('****** ERROR: getFollowersFromTwitter() failed *******');
			displayError('Could not search for tweets. Probably out of Twitter API requests.');
		},
		complete:function(){                    
		}
	});
	
	/*$.getJSON('http://search.twitter.com/search.json?q=' + searchQuery + '&rpp=' + rpp + '&page=' + page + query_id + '&include_entities=true&result_type=mixed&callback=?', function(searchResponse) {
		console.log('received response for search: ' + searchResponse);
	  
		page = searchResponse.page + 1;
		max_id = searchResponse.max_id;
		query = searchResponse.query;
		rpp = rpp;
		
		console.log("page: " + page + "\nmax_id: " + max_id + "\nquery: " + query + "\nrpp: " + rpp);
		
		tweetsToServer(searchResponse, rpp, searchQuery);
	});*/
}

/*
	Init stuff
	---------- */

// toggle user info
function usrClick(usrClickVar) {
	var usrClickVar = $('.user');
	usrClickVar.unbind();
	usrClickVar.click(function () {
		active = true;
		console.log("clicked user");
		$(this).toggleClass('active').parent().find('.user_info').fadeToggle("fast", "linear").parent().toggleClass("full_opacity");
	});
}
				
usrClick();

// close user on click outside user_info
$(document).click(function (e)
{
    var container = $(".user_info");
	var userContainer = $(".user");

    if (container.has(e.target).length === 0 && userContainer.has(e.target).length === 0) {
		container.hide().parent().removeClass("full_opacity").find('.user').removeClass('active');
    }
});

var searchQuery = "";
// AJAX request as result of search submit
$('#search_form').submit( function(e) {
	
	// stop form from submitting
	e.preventDefault();
	
	// get query params from search form
	searchQuery = $('#search_form').find('input').val();
	rpp = $('#search_form').find('#resultsPerPage').val();
	
	// if an actual query is entered
	if(searchQuery != '') {	
		// start AJAX calls
		searchTweets(searchQuery, rpp);
	}
	
	getReadyForView();
		
	});


// bind event to trending topics clicks
$('.trend').click(function(e) {
	
	// stop link from working
	e.preventDefault();
	searchQuery = $(this).attr('data-query');
	if(searchQuery.charAt(0) == "#") {
		searchQuery = "%23" + searchQuery.substr(1);
	}
	
	searchTweets(searchQuery, 20);
	console.log(searchQuery);
	getReadyForView();
	
});

// makes view ready to receive tweets, inits masonry and binds click events
function getReadyForView() {
	// remove trendinglist and add container for tweets and more button
	var tweetContainer = '<section class="ten_cols no_padding cf tweet_wrapper" id="tweets"></section><div id="more" class="ten_cols hidden"><div class="two_cols block btn center">Load more</div></div>';
	$trendingList = $("#trendingList");
	
	$tweetContainer = $('#tweet');
	
	if($trendingList.length) {
		$trendingList.fadeOut("slow", "linear");
	}

	
	if(!$('#more').length) {
		$trendingList.after(tweetContainer).parent().find('#more').fadeIn("fast", "linear");
	} else {
		$('#tweets').html("");
	}
	
	// toggle spinner on more button
	$('#more').find('.btn').addClass('disabled no_text spinner');
	
	// init masonry
	var $container = $('#tweets');
	$container.imagesLoaded(function(){
	  $container.masonry({
		itemSelector : '.tweet_container',
	  });
	});	

	// bind event to #more
	// AJAX request as result of get more click
	$('#more').click(function() {
		console.log('click');
		$(this).find('.btn').addClass('disabled no_text spinner');
		
		searchTweets(searchQuery, rpp);
	});
}


// displays returned view, rebinds events, reloads masonry
function doTheFunkyBusiness(view) {
	// add new tweets to #tweets and reload masonry
	$('#tweets').append(view).masonry('reload');
	
	// stop more spinning
	$('#more').find('.btn').removeClass('disabled no_text spinner');
	
	// remove loading message
	$('#loading').remove();
	
	// rebind usrClick
	usrClick();
}

