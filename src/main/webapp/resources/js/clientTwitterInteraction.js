// hvis returnerer error, skriv ut og terminer


/*
	User functions
	--------------
*/

var followers = "";
var following = "";

// control users
function usrRequests(usrs, searchQuery) {
	var usrLst = usrs.split(",");
	
	// get user data
	getUsersFromTwitter(usrs);
	
	// get followers and following for all users
	for(var i=0;i<usrLst.length;i++) {
		following += getFollowingFromTwitter(usrLst[i]);
		followers += getFollowersFromTwitter(usrLst[i]);
	}
}

// get users from twitter
function getUsersFromTwitter(usrs, searchQuery) {
	console.log('REQUEST: Twitter API: requesting user data for: ' + usrs);
	$.getJSON('https://api.twitter.com/1/users/lookup.json?screen_name=' + usrs + '&include_entities=false&callback=?', function(usrJSONData) {
		console.log('RESPONSE: Twitter API: received user data for ' + usrs);
		usersToServer(usrJSONData, searchQuery);
	});
}

// sends returned user data to controller (ajaj/insertUsers)
function usersToServer(usrJSONData, rpp, searchQuery, searchRequest) {
	$.post("ajaj/processUsers", { users: usrJSONData, searchQuery: searchQuery, searchRequest: searchRequest, rpp: rpp },
   function(data) {
     alert("Data Loaded: " + data);
	 // todo: run masonry
   });
}

// sends combined list of following to controller (ajaj/following)
function followingToServer(followingData) {
	$.post("ajaj/processFollowing", { following: followingData },
   function(data) {
     alert("Data Loaded: " + data);
	 // todo
   });
}

// sends combined list of followers to controller (ajaj/followers)
function followersToServer(followersData) {
	$.post("ajaj/processFollowers", { followers: followersData },
   function(data) {
     alert("Data Loaded: " + data);
	 // todo
   });
}

// sends requests for following for users to twitter
function getFollowingFromTwitter(usr) {
	console.log('REQUEST: Twitter API: requesting following for ' + usr);
	$.getJSON('https://api.twitter.com/1/friends/ids.json?cursor=-1&screen_name=' + usr + '&callback=?', function(usrFollowingJSON) {
		console.log('RESPONSE: Twitter API: received response for following user: ' + usr);
		return usrFollowingJSON;
	});
}

// sends requests for followers for users to twitter
function getFollowersFromTwitter(usr) {
	console.log('REQUEST: Twitter API: requesting followers for ' + usr);
	$.getJSON('https://api.twitter.com/1/followers/ids.json?cursor=-1&screen_name=' + usr + '&callback=?', function(usrFollowersJSON) {
		console.log('RESPONSE: Twitter API: received response for followers for user: ' + usr);
		return usrFollowersJSON;
	});
}

/*
	Search functions
	--------------
*/

// sends the JSON response from twitter to controller (ajaj/processSearch)
function tweetsToServer(searchResponse, rpp, searchQuery) {

	console.log("searchQuery: " + searchQuery + " and rpp: " + rpp + " returned: " + JSON.stringify(searchResponse));
	$.post("ajaj/processSearch", { searchResponse: searchResponse },
   function(data) {
     alert("Data Loaded: " + data);
	 // todo
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