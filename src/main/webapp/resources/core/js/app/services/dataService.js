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

    function getAllBooks(){
         var promise = $http.get('/getAllBooks').
                	success(function (response, status, headers, config) {
                       return response.data;
                	}).
                	error(function (data, status, headers, config) {
                		alert("Error");
                	});

          return promise;
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
		addUserPreferredCategories:addUserPreferredCategories,
		getAllBooks : getAllBooks,
		getDetailsForUser : getDetailsForUser
	};

}]);
