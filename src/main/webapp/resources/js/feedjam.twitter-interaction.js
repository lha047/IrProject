// hvis returnerer error, skriv ut og terminer


/*
	User functions
	--------------
*/

// control user ajax requests
function usrRequests(searchRequest, rpp, searchQuery, usrs) {
	
	if(usrs) {
		// we have users to find
		var usrLst = usrs.split(",");
	
		// get user data
		getUsersFromTwitter(usrs, searchQuery, searchRequest, rpp);
		
		// get followers and following for all users
		for(var i=0;i<usrLst.length-1;i++) {
			getFollowingFromTwitter(usrLst[i]);
			getFollowersFromTwitter(usrLst[i]);
		}
		
	} else {
		// get the view
		console.log("No users to fetch, getting view");
		usersToServer('', searchQuery, searchRequest, rpp);
	}
}

// get users from twitter
function getUsersFromTwitter(usrs, searchQuery, searchRequest, rpp) {
	console.log('REQUEST: Twitter API: requesting user data for: ' + usrs);
	$.getJSON('https://api.twitter.com/1/users/lookup.json?user_id=' + usrs + '&include_entities=false&callback=?', function(usrJSONData) {
		console.log('RESPONSE: Twitter API: received user data for ' + usrs);
		usersToServer(usrJSONData, searchQuery, searchRequest, rpp);
	});
}

// sends returned user data to controller (../ajaj/processUsers)
function usersToServer(usrJSONData, searchQuery, searchRequest, rpp) {
	console.log('SERVER POST: sending users to server');
	console.log(searchRequest + ' \n ################# \n' + usrJSONData);
	var out = "";
	if(usrJSONData != "") {
		out = '{"users":' + JSON.stringify(usrJSONData) + '}';
	}
	
	console.log(out);
		
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
   });
}

// sends following to controller (ajaj/processFollowing)
function followingToServer(followingData, userId) {
	$.post("ajaj/processFollowing", { 
		userId: userId, 
		following: followingData
	},
   function(data) {
     console.log("response");
   });
}

// sends followers to controller (ajaj/processFollowers)
function followersToServer(followersData, userId) {
	$.post("ajaj/processFollowers", { 
		userId: userId, 
		followers: followersData
	},
   function(data) {
     console.log("SERVER RESPONSE: added followers for " + userId);
   });
}

// sends requests for following for users to twitter
function getFollowingFromTwitter(usr) {
	console.log('REQUEST: Twitter API: requesting following for ' + usr);
	$.getJSON('https://api.twitter.com/1/friends/ids.json?cursor=-1&user_id=' + usr + '&callback=?', function(usrFollowingJSON) {
		console.log('RESPONSE: Twitter API: received response for following user: ' + usr);
		followingToServer(usr, usrFollowingJSON);
	});
}

// sends requests for followers for users to twitter
function getFollowersFromTwitter(usr) {
	console.log('REQUEST: Twitter API: requesting followers for ' + usr);
	$.getJSON('https://api.twitter.com/1/followers/ids.json?cursor=-1&user_id=' + usr + '&callback=?', function(usrFollowersJSON) {
		console.log('RESPONSE: Twitter API: received response for followers for user: ' + usr);
		followersToServer(usr, usrFollowersJSON);
	});
}

/*
	Search functions
	--------------
*/

// sends the JSON response from twitter to controller (ajaj/processSearch)
function tweetsToServer(searchResponse, rpp, searchQuery) {

	console.log("searchQuery: " + searchQuery + " and rpp: " + rpp + " returned: " + JSON.stringify(searchResponse));
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
	
	$.getJSON('http://search.twitter.com/search.json?q=' + searchQuery + '&rpp=' + rpp + '&include_entities=true&result_type=mixed&callback=?', function(searchResponse) {
		console.log('received response for search: ' + searchResponse);
	  
		tweetsToServer(searchResponse, rpp, searchQuery);
	});
}

/*
	Init stuff
	---------- */

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
				
usrClick();


var searchQuery = "";
var searchRpp = "";
// AJAX request as result of search submit
$('#search_form').submit( function() {
	
	// get query params from search form
	searchQuery = $('#search_form').find('input').val();
	searchRpp = $('#search_form').find('#resultsPerPage').val();
	
	// if an actual query is entered
	if(searchQuery != '') {
	
		// start AJAX calls
		searchTweets(searchQuery, searchRpp);
	
		// remove trendinglist and add container for tweets and more button
		var tweetContainer = '<section class="ten_cols no_padding cf tweet_wrapper" id="tweets"></section><div id="more" class="ten_cols hidden"><div class="two_cols block btn center">Load more</div></div>';
		
		$("#trendingList").fadeOut("slow", "linear").delay(2000).after(tweetContainer).fadeIn("slow", "linear");
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
			
			searchTweets(searchQuery, searchRpp);
		});
	}
	
});


// displays returned view, rebinds events, reloads masonry
function doTheFunkyBusiness(view) {
	// add new tweets to #tweets and reload masonry
	$('#tweets').append(view).masonry('reload');
	
	// stop more spinning
	$('#more').find('.btn').removeClass('disabled no_text spinner');
	
	// rebind usrClick
	usrClick();
}

	// ajax logic
	var pageIterator = 2;
	var next_url = nextUrl.split("&amp;"); 
	var max_id = next_url[1];
	
	console.log(next_url);
	
	