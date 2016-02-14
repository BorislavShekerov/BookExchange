bookApp.service("dataService", ['$http', function ($http) {
	var categories = [];
	var email = "";

	function fetchUserDetails() {
		$http.get('/app/udetails/').
		success(function (data, status, headers, config) {
			userDetails = data;
		}).
		error(function (data, status, headers, config) {
			alert("Error");
		});

	}

	function getAllCategories() {
    		var postPromise = $http.get('/getAllCategories').
    		success(function (data, status, headers, config) {
    			userDetails = data;
    		}).
    		error(function (data, status, headers, config) {
    			alert("Error");
    		});

        return postPromise;
    	}


   function getDetailsForUser(userEmail){
     var csrfToken = $("meta[name='_csrf']").attr("content");
     var userData= {email:userEmail};
            var req = {
            				method: 'POST',
            				url: '/app/getDetailsForUser',
            				headers: {
            					'X-CSRF-TOKEN': csrfToken
            				},
            				data: userData
            			}

            	var postPromise	= $http(req).then(function (response) {
            				return response.data;
            			}, function (response) {
            				return response.status;
            			});
            	return postPromise;
   }

	function setEmail(userEmail) {
		if (email == "") {
			email = userEmail;
			fetchUserDetails();
		}
	}

	var setCategories = function (categs) {
		if (categories.length == 0) {
			categories = categs;
		}
	};

	var getCategories = function () {
		return categories;
	};

    var getEmail = function () {
    		return email;
    	};

	var getUserData = function () {
		return userDetails;
	};

	return {
		setEmail: setEmail,
		getEmail: getEmail,
		getUserData: getUserData,
		setCategories: setCategories,
		getCategories: getCategories,
		getAllCategories: getAllCategories,
		getDetailsForUser : getDetailsForUser
	};

}]);
