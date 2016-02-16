bookApp.controller('offersMadeController', ['$scope', 'dataService', 'exchangeService', function ($scope, dataService, exchangeService) {
		var userEmail = dataService.getEmail();
		$scope.userExchangesCreated = [];

        function findBookRequestedByUser(exchangeChain){
            var bookRequestedByUser = {};

             angular.forEach(exchangeChain.booksRequestedInChain, function (bookRequest, index) {
                if(bookRequest.requestedBy.email == userEmail){
                    bookRequestedByUser = bookRequest.requestedBook;
                }
             });

             return bookRequestedByUser;
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

        function computeChainProgress(exchangeChain){
          var acceptedRequests = 0;
          angular.forEach(exchangeChain.exchangeChainRequests, function (chainRequest, index) {
            if(chainRequest.accepted){
                acceptedRequests++;
            }
          });

          return (acceptedRequests/exchangeChain.exchangeChainRequests.length)*100;

        }
        function addExchangeChainsToAllExchanges(exchangeChains){
            angular.forEach(exchangeChains, function (exchangeChain, index) {
                exchangeChain.bookRequested = findBookRequestedByUser(exchangeChain);
                exchangeChain.isChain = true;
                exchangeChain.isCollapsed = true;
                exchangeChain.progress = computeChainProgress(exchangeChain);

                $scope.userExchangesCreated.push( exchangeChain );
            });
        }

		function init() {
			exchangeService.getExchangeRequestsInitiatedByUser().then(function (currentExchanges) {
			    addExchangeChainsToAllExchanges(currentExchanges.bookExchangeChains);


				 angular.forEach(currentExchanges.directExchanges, function (directExchange, index) {
				    directExchange.isCollapsed = true;
				 });

				 	$scope.userExchangesCreated = $scope.userExchangesCreated.concat(currentExchanges.directExchanges);


			},function (err){
			console.log(err);
			});

		}

		init();
}
]);