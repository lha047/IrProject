function getUserLocation() {
	if (navigator.geolocation) // check if browser support this feature or not
	{
	    navigator.geolocation.getCurrentPosition(function(position)
	        {
	              var lat = position.coords.latitude;
	              var lng = position.coords.longitude;
	              alert("Latitude:"+lat+" Longitude:"+lng);
	         }
	    );
	}
}