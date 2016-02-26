bookApp.service("ratingService",['$http', function ($http) {
  function getRatingsForUser() {
        		var getPromise = $http.get('/app/ratings/').
        		then(function (response) {
        			return response.data;
        		}, function (response) {
        			console.log(response)
        		});

        		return getPromise;
        	}

        	 function addRating(commentFor,comment,rating) {
        	        var rating = { rating:rating,commentMessage:comment,commentFor:commentFor};
                    	var csrfToken = $("meta[name='_csrf']").attr("content");

                                    var req = {
                                    				method: 'POST',
                                    				url: '/app/ratings/add',
                                    				headers: {
                                    					'X-CSRF-TOKEN': csrfToken
                                    				},
                                    				data: rating
                                    			}

                                    	var postPromise	= $http(req).then(function (response) {
                                    				return response.data;
                                    			}, function (response) {
                                    				return response.status;
                                    			});

                                    			return postPromise;
                    	}

    return {
    		getRatingsForUser: getRatingsForUser,
    		addRating: addRating
    	};
}]);