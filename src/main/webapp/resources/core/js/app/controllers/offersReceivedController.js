bookApp.controller('offersReceivedController', ['$scope', 'dataService', 'exchangeService','$uibModal', function ($scope, dataService, exchangeService,$uibModal) {
	$scope.userRequestsReceived = [];
	var userEmail = dataService.getEmail();


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

			$scope.userRequestsReceived.push(exchangeChain);
		});
	}

	function init() {
		exchangeService.getExchangeRequestsReceivedByUser().then(function (currentExchanges) {
			addExchangeChainsToAllExchanges(currentExchanges.bookExchangeChains);
			$scope.userRequestsReceived = $scope.userRequestsReceived.concat(currentExchanges.directExchanges);
		});

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

            $scope.viewDetails = function(exchangeRequest){
                dataService.getDetailsForUser(exchangeRequest.exchangeInitiatorEmail).then(function(userData){
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