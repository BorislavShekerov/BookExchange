bookApp.service("exchangeService",['$http', function ($http) {
	var bookToExchangeFor = {};

	var setBookToExchangeFor = function (book) {
		bookToExchangeFor = book;
	};

	var getBookToExchangeFor = function () {
		return bookToExchangeFor;
	};

    function getExchangeRequestsInitiatedByUser() {
    		var getPromise = $http.get('/app/exchangeRequests/initiated').
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

    	function rejectDirectExchange(requestId){
    	      var csrfToken = $("meta[name='_csrf']").attr("content");
                                     var exchangeChain= {
                                     id: requestId};

                                                var req = {
                                                				method: 'POST',
                                                				url: '/app/directExchange/reject',
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

    	function acceptDirectExchange(bookRequested,exchangeId){
    	      var csrfToken = $("meta[name='_csrf']").attr("content");
                var dataToSend = {bookId:bookRequested,exchangeId:exchangeId};
                                    var req = {
                                    				method: 'POST',
                                    				url: '/app/directExchange/accept',
                                    				headers: {
                                    					'X-CSRF-TOKEN': csrfToken
                                    				},
                                    				data: dataToSend
                                    			}

                                    	var postPromise	= $http(req).then(function (response) {
                                    				return response.data;
                                    			}, function (response) {
                                    				return response.status;
                                    			});

                                    			return postPromise;
    	}

    function getChainDetailsForUser(){
             var csrfToken = $("meta[name='_csrf']").attr("content");

                        var req = {
                        				method: 'POST',
                        				url: '/app/exchangeChain/userRequest',
                        				headers: {
                        					'X-CSRF-TOKEN': csrfToken
                        				}
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

                              var req = {
                              				method: 'GET',
                              				url: '/app/exchangeRequests/received',
                              				headers: {
                              					'X-CSRF-TOKEN': csrfToken
                              				}
                              			}

                              	var postPromise	= $http(req).then(function (response) {
                              				return response.data;
                              			}, function (response) {
                              				return response.status;
                              			});

                              			return postPromise;
        }

     function exploreOtherOptions(exchangeChainId){
                      var csrfToken = $("meta[name='_csrf']").attr("content");

                                    var req = {
                                    				method: 'GET',
                                    				url: '/app/exploreOtherOptions/' + exchangeChainId,
                                    				headers: {
                                    					'X-CSRF-TOKEN': csrfToken
                                    				}
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
                     exchangeId: exchangeChainId,
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


     function initiateExchangeChain(exchangeOrder){
                 var csrfToken = $("meta[name='_csrf']").attr("content");

                          var req = {
                                          			method: 'POST',
                                          			url: '/app/exchangeChainOrder',
                                          			headers: {
                                          				'X-CSRF-TOKEN': csrfToken
                                          			},
                                          			data: exchangeOrder
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
		getExchangeRequestsInitiatedByUser: getExchangeRequestsInitiatedByUser,
		getChainDetailsForUser: getChainDetailsForUser,
		rejectExchangeChainRequest: rejectExchangeChainRequest,
		rejectDirectExchange : rejectDirectExchange,
		acceptExchangeChainRequest : acceptExchangeChainRequest,
		acceptDirectExchange: acceptDirectExchange,
		getExchangeRequestsReceivedByUser:getExchangeRequestsReceivedByUser,
		exploreOtherOptions : exploreOtherOptions,
		initiateExchangeChain: initiateExchangeChain
	};

}]);