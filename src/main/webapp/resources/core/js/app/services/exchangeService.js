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
    function getChainDetailsForUser(exchangeInitiator){
             var csrfToken = $("meta[name='_csrf']").attr("content");
             var exchangeInitiator = {
             email: exchangeInitiator};
                        var req = {
                        				method: 'POST',
                        				url: '/app/exchangeChain/userRequest',
                        				headers: {
                        					'X-CSRF-TOKEN': csrfToken
                        				},
                        				data: exchangeInitiator
                        			}

                        	var postPromise	= $http(req).then(function (response) {
                        				return response.data;
                        			}, function (response) {
                        				return response.status;
                        			});

                        			return postPromise;
        }

	return {
		setBookToExchangeFor: setBookToExchangeFor,
		getBookToExchangeFor: getBookToExchangeFor,
		getUserCurrentExchanges: getUserCurrentExchanges,
		getChainDetailsForUser: getChainDetailsForUser
	};

}]);