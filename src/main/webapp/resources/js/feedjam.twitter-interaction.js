// hvis returnerer error, skriv ut og terminer


/*
	User functions
	--------------
*/

var followers = "";
var following = "";

// control users
function usrRequests(searchRequest, rpp, searchQuery, usrs) {
	
	if(usrs) {
		var usrLst = usrs.split(",");
	
		// get user data
		getUsersFromTwitter(usrs, searchQuery, searchRequest, rpp);
		
		// get followers and following for all users
		for(var i=0;i<usrLst.length-1;i++) {
			following += getFollowingFromTwitter(usrLst[i]);
			followers += getFollowersFromTwitter(usrLst[i]);
		}
		
	} else {
		// get the view
		console.log("No users to fetch, getting view");
		usersToServer("", searchQuery, searchRequest, rpp);
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
	if(!rpp) {
		rpp = 20
	}
	$.post("../ajaj/processUsers", { 
		users: JSON.stringify(usrJSONData), 
		searchQuery: searchQuery, 
		searchRequest: JSON.stringify(searchRequest), 
		rpp: rpp
	},
   function(view) {
		console.log('RETURNED VIEW');
		doTheFunkyBusiness(view);
   });
}

function doTheFunkyBusiness(view) {
	var tweetContainer = '<section class="ten_cols no_padding cf tweet_wrapper" id="tweets"></section><div id="more" class="ten_cols"><div class="two_cols block btn center">Load more</div></div>';
	if(!$("#more")) {
		$('header').after(tweetContainer);
	}
	$("#trendingList").fadeOut("fast", "linear").delay(10).remove();
	$('#tweets').append(view).masonry('reload');
}

	// ajax logic
	var pageIterator = 2;
	var next_url = nextUrl.split("&amp;"); 
	var max_id = next_url[1];
	
	console.log(next_url);
	
	$('#more').click(function() {
		
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

// sends following to controller (ajaj/processFollowing)
function followingToServer(followingData, userId) {
	$.post("../ajaj/processFollowing", { 
		userId: userId, 
		following: followingData
	},
   function(data) {
     console.log("response");
   });
}

// sends followers to controller (ajaj/processFollowers)
function followersToServer(followersData, userId) {
	$.post("../ajaj/processFollowers", { 
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
	$.post("../ajaj/processSearch", { 
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