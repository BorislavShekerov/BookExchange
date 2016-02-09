bookApp.service("exchangeService",['$http', function ($http) {
	var bookToExchangeFor = {};

	var setBookToExchangeFor = function (book) {
		bookToExchangeFor = book;
	};

	var getBookToExchangeFor = function () {
		return bookToExchangeFor;
	};

    function getExchangeRequestsInitiatedByUser() {
    		var getPromise = $http.get('/app/exchangeChainRequests/initiated').
    		then(function (response) {
    			return response.data;
    		}, function (response) {
    			console.log(response)
    		});

    		return getPromise;
    	}

    	function rejectExchangeChainRequest(exchangeChainID){
    	    var csrfToken = $("meta[name='_csrf']").attr("content");
                         var exchangeChain= {
                         id: exchangeChainID};

                                    var req = {
                                    				method: 'POST',
                                    				url: '/app/exchangeChain/reject',
                                    				headers: {
                                    					'X-CSRF-TOKEN': csrfToken
                                    				},
                                    				data: exchangeChain
                                    			}

                                    	 $http(req).then(function (response) {
                                    				return response.data;
                                    			}, function (response) {
                                    				return response.status;
                                    			});


    	}

    function getChainDetailsForUser(chainId){
             var csrfToken = $("meta[name='_csrf']").attr("content");
             var chainDetials = {
             id: chainId};
                        var req = {
                        				method: 'POST',
                        				url: '/app/exchangeChain/userRequest',
                        				headers: {
                        					'X-CSRF-TOKEN': csrfToken
                        				},
                        				data: chainDetials
                        			}

                        	var postPromise	= $http(req).then(function (response) {
                        				return response.data;
                        			}, function (response) {
                        				return response.status;
                        			});

                        			return postPromise;
        }

        function getExchangeRequestsReceivedByUser(){
        var csrfToken = $("meta[name='_csrf']").attr("content");
                   var chainDetials = {
                   id: chainId};
                              var req = {
                              				method: 'GET',
                              				url: '/app/exchangeChainRequests/received',
                              				headers: {
                              					'X-CSRF-TOKEN': csrfToken
                              				},
                              				data: chainDetials
                              			}

                              	var postPromise	= $http(req).then(function (response) {
                              				return response.data;
                              			}, function (response) {
                              				return response.status;
                              			});

                              			return postPromise;
        }

        function acceptExchangeChainRequest(bookId,exchangeChainId){
                  var csrfToken = $("meta[name='_csrf']").attr("content");
                     var chainAcceptanceData = {
                     exchangeChainId: exchangeChainId,
                     bookId: bookId};

                                var req = {
                                				method: 'POST',
                                				url: '/app/exchangeChain/accept',
                                				headers: {
                                					'X-CSRF-TOKEN': csrfToken
                                				},
                                				data: chainAcceptanceData
                                			}

                                	var postPromise	= $http(req).then(function (response) {
                                				return response.data;
                                			}, function (response) {
                                				return response.status;
                                			});
        }

	return {
		setBookToExchangeFor: setBookToExchangeFor,
		getBookToExchangeFor: getBookToExchangeFor,
		getExchangeRequestsInitiatedByUser: getExchangeRequestsInitiatedByUser,
		getChainDetailsForUser: getChainDetailsForUser,
		rejectExchangeChainRequest: rejectExchangeChainRequest,
		acceptExchangeChainRequest : acceptExchangeChainRequest,
		getExchangeRequestsReceivedByUser:getExchangeRequestsReceivedByUser
	};

}]);