bookApp.controller('offersReceivedController', ['$scope', 'dataService', 'exchangeService','$uibModal','ratingService','eventRecordService', function ($scope, dataService, exchangeService,$uibModal,ratingService,eventRecordService) {
	$scope.userRequestsReceived = [];
	var userEmail = dataService.getEmail();
      $scope.userRatings = [];
      $scope.loadingRequests = true;
      eventRecordService.setSelectedItem("Requests Received");

	function findBookRequestedFromUser(exchangeChain) {
		var bookRequestedFromUser = {};

		angular.forEach(exchangeChain.booksRequestedInChain, function (bookRequest, index) {
			if (bookRequest.requestedBook.ownerEmail == userEmail) {
				bookRequestedFromUser = bookRequest.requestedBook;

			}
		});
		if (!bookRequestedFromUser.imgUrl) {
			bookRequestedFromUser.imgUrl = "../resources/core/img/unknown-book.jpg";
		}
		return bookRequestedFromUser;
	}

	function findBookRequestedByUser(exchangeChain) {
		var bookRequestedFromUser = {};

		angular.forEach(exchangeChain.booksRequestedInChain, function (bookRequest, index) {
			if (bookRequest.requestedBy.email == userEmail) {
				bookRequestedFromUser = bookRequest.requestedBook;

			}
		});
		if (!bookRequestedFromUser.imgUrl) {
			bookRequestedFromUser.imgUrl = "../resources/core/img/unknown-book.jpg";
		}
		return bookRequestedFromUser;
	}

	function findUserToChooseFrom(exchangeChain) {
		var userToChooseFrom = {};

		angular.forEach(exchangeChain.exchangeChainRequests, function (exchangeChainRequest, index) {
			if (exchangeChainRequest.userOfferingTo.email == userEmail) {
				userToChooseFrom = exchangeChainRequest.requestFor;

			}
		});
		return userToChooseFrom;
	}

	function findUserChoosingFromYou(exchangeChain) {
		var userChoosingFromYou = {};

		angular.forEach(exchangeChain.exchangeChainRequests, function (exchangeChainRequest, index) {
			if (exchangeChainRequest.userChoosingFrom.email == userEmail) {
				userChoosingFromYou = exchangeChainRequest.requestFor;
			}
		});

		if(!userChoosingFromYou.email){
            userChoosingFromYou = exchangeChain.exchangeInitiator;
		}

		return userChoosingFromYou;
	}
	$scope.openDetailsModal = function (exchangeChain) {
		var userToChooseFrom = findUserToChooseFrom(exchangeChain);
		var userChoosingFromYou = findUserChoosingFromYou(exchangeChain);

		var promptWindow = $uibModal.open({
			animation: true,
			templateUrl: '/app/openChainRequestModal',
			controller: 'ChainRequestModalController',
			size: 'lg',
			resolve: {
				userToChooseFrom: userToChooseFrom,
				userChoosingFromYou: userChoosingFromYou,
				exchangeChain: exchangeChain
			}
		});

		promptWindow.result.then(function (bookChosen) {
			exchangeChain.userChoice = bookChosen;
		}, function () {
			$log.info('Modal dismissed at: ' + new Date());
		});

	}

	function computeChainProgress(exchangeChain) {
		var acceptedRequests = 0;
		angular.forEach(exchangeChain.exchangeChainRequests, function (chainRequest, index) {
			if (chainRequest.accepted) {
				acceptedRequests++;
			}
		});

		return (acceptedRequests / exchangeChain.exchangeChainRequests.length) * 100;
	}

	function addExchangeChainsToAllExchanges(exchangeChains) {
		angular.forEach(exchangeChains, function (exchangeChain, index) {
			exchangeChain.bookRequested = findBookRequestedFromUser(exchangeChain);
			exchangeChain.isChain = true;
			exchangeChain.isCollapsed = true;
			exchangeChain.userChoice = findBookRequestedByUser(exchangeChain);
			exchangeChain.progress = computeChainProgress(exchangeChain);

            setRatingInfo(exchangeChain, exchangeChain.bookRequested.ownerEmail);

			$scope.userRequestsReceived.push(exchangeChain);
		});
	}

	function setRatingInfo(exchange,userExchangingWith){
                     exchange.rated = false;
                      exchange.showRateButton = false;
                     exchange.rating = 0;
                                angular.forEach($scope.userRatings,function(rating,index){
                                    if(rating.commentForEmail == userExchangingWith){
                                        exchange.rated = true;
                                         exchange.rating = rating.rating;
                                    }
                                });

    }

	function init() {
	    requestRatingsForUser();
		exchangeService.getExchangeRequestsReceivedByUser().then(function (currentExchanges) {
			addExchangeChainsToAllExchanges(currentExchanges.bookExchangeChains);
			addDirectBookExchanges(currentExchanges.directExchanges);

			   $scope.loadingRequests = false;
		});

	}

    function addDirectBookExchanges(directExchanges){
        angular.forEach(directExchanges,function(directExchange,index){
            directExchange.isChain = false;
           setRatingInfo(directExchange,directExchange.exchangeInitiator.email);

           $scope.userRequestsReceived.push(directExchange);
        });
    }
             $scope.userRatingSet = function(exchangeCreated){
                        exchangeCreated.showRateButton = true;
                        console.log(exchangeCreated.rating);
                    }

                    $scope.rateUser = function (exchangeCreated){
                        exchangeCreated.rated =  true;
                        var userRated = getUserRatedEmail(exchangeCreated);

                        ratingService.addRating(userRated,exchangeCreated.ratingComment,exchangeCreated.rating);
                        exchangeCreated.showRateButton = false;
                    }

                      function getUserRatedEmail(exchangeCreated){
                                if(exchangeCreated.isChain){
                                    return findBookRequestedByUser(exchangeCreated).ownerEmail;
                                }else{
                                    return exchangeCreated.exchangeInitiator.email;
                                }
                            }

	        $scope.cancelRequest = function(exchangeToCancel){
                if(exchangeToCancel.isChain){
                    exchangeService.rejectExchangeChainRequest(exchangeToCancel.id);
                }else{
                    exchangeService.rejectDirectExchange(exchangeToCancel.id);
                }

                exchangeToCancel.over = true;
                exchangeToCancel.successful = false;
            }

             function requestRatingsForUser(){
                        ratingService.getRatingsForUser().then(function(ratings){
                        		                angular.forEach(ratings,function(rating,index){
                        		                    if(rating.commentatorEmail == userEmail){
                        		                        $scope.userRatings.push(rating);
                        		                    }
                        		                });
                        		                 $scope.ratingLoading = false;

                                                },function(err){
                                                    console.log(err);
                                                });

                    }

            $scope.viewDetails = function(exchangeRequest){
                dataService.getDetailsForUser(exchangeRequest.exchangeInitiator.email).then(function(userData){
                 var promptWindow = $uibModal.open({
                                			animation: true,
                                			templateUrl: '/app/openDirectRequestModal',
                                			controller: 'DirectRequestModalController',
                                			size: 'lg',
                                			resolve: {
                                				userToChooseFrom: userData,
                                				directExchange:exchangeRequest
                                			}
                                		});

                                		promptWindow.result.then(function (result) {
                                		    if(result.accepted){
                                		        exchangeRequest.successful =true;

                                		        exchangeRequest.bookOfferedInExchange = result.bookChosen;
                                		    }else{
                                		      exchangeRequest.successful =false;
                                		    }
                                		    exchangeRequest.over = true;

                                		}, function () {
                                			$log.info('Modal dismissed at: ' + new Date());
                                		});
                },function(err){
                    console.log(err);
                });

            }

	init();
}]);