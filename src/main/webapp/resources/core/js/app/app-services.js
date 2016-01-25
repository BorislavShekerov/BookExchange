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
    function addUserPreferredCategories(categoriesInterestedIn){
    var csrfToken = $("meta[name='_csrf']").attr("content");
        var req = {
        				method: 'POST',
        				url: '/app/addPrefferedCategeories',
        				headers: {
        					'X-CSRF-TOKEN': csrfToken
        				},
        				data: categoriesInterestedIn
        			}

        	var postPromise	= $http(req).then(function (response) {
        				return response.data;
        			}, function (response) {
        				return response.status;
        			});
        	return postPromise;
    }
	var setEmail = function (userEmail) {
		if (email == "") {
			email = userEmail;
			fetchUserDetails();
		}
	};

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
		addUserPreferredCategories:addUserPreferredCategories
	};

}]);

bookApp.service("exchangeService",['$http', function ($http) {
	var bookToExchangeFor = {};

	var setBookToExchangeFor = function (book) {
		bookToExchangeFor = book;
	};

	var getBookToExchangeFor = function () {
		return bookToExchangeFor;
	};

    function getUserCurrentExchanges() {
    		var getPromise = $http.get('/app/getUserCurrentExchanges').
    		then(function (response) {
    			return response.data;
    		}, function (response) {
    			console.log(response)
    		});

    		return getPromise;
    	}

	return {
		setBookToExchangeFor: setBookToExchangeFor,
		getBookToExchangeFor: getBookToExchangeFor,
		getUserCurrentExchanges: getUserCurrentExchanges
	};

}]);