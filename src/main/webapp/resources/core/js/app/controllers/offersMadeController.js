bookApp.controller('offersMadeController', ['$scope', 'dataService', 'exchangeService','ratingService','eventRecordService','$rootScope','$uibModal', function ($scope, dataService, exchangeService, ratingService,eventRecordService,$rootScope, $uibModal) {
		$rootScope.shouldSlideMenuIn = false;
		var userEmail = dataService.getEmail();
		$scope.rating = 0;
		$scope.userExchangesCreated = [];
		$scope.userRatings = [];
		$scope.ratingLoading = true;
        $scope.loadingRequests = true;
        $scope.isUserMobile = $rootScope.isMobile;
        eventRecordService.setSelectedItem("Your Offers");

        function findBookRequestedByUser(exchangeChain){
            var bookRequestedByUser = {};

             angular.forEach(exchangeChain.booksRequestedInChain, function (bookRequest, index) {
                if(bookRequest.requestedBy.email == userEmail){
                    bookRequestedByUser = bookRequest.requestedBook;
                }
             });

             return bookRequestedByUser;
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
                return exchangeCreated.bookRequested.ownerEmail;
            }
        }
        $scope.cancelRequest = function(exchangeToCancel){
            if(exchangeToCancel.isChain){
                exchangeService.rejectExchangeChainRequest(exchangeToCancel.id);
            }else{
                exchangeService.rejectDirectExchange(exchangeToCancel.id);
            }
            exchangeToCancel.canceled = true;
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

                setRatingInfo(exchangeChain, exchangeChain.bookRequested.ownerEmail);

                $scope.userExchangesCreated.push( exchangeChain );
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

            requestExchangesInitiatedByUser();


		}

		init();

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
		function requestExchangesInitiatedByUser(){
		exchangeService.getExchangeRequestsInitiatedByUser().then(function (currentExchanges) {
        			   addExchangesCreatedByUser(currentExchanges);
        			},function (err){
        			console.log(err);
        			});
		}

        function addExchangesCreatedByUser(exchanges){
          addExchangeChainsToAllExchanges(exchanges.bookExchangeChains);


                                            				 angular.forEach(exchanges.directExchanges, function (directExchange, index) {
                                            				    directExchange.isCollapsed = true;

                                                                setRatingInfo(directExchange, directExchange.bookRequested.ownerEmail);
                                            				 });



                                            				 	$scope.userExchangesCreated = $scope.userExchangesCreated.concat(exchanges.directExchanges);
                                                            $scope.loadingRequests = false;
        }

		$scope.findOtherExchangePaths = function(exchangeChainCreated){

		        var bookRequested = findBookRequestedByUser(exchangeChainCreated);
            	var promptWindow = $uibModal.open({
            					animation: true,
            					templateUrl: '/app/openExploreMoreOptionsModal',
            					controller: 'ExploreMoreOptionsModalController',
            					resolve: {
                                						 exchangeChain: exchangeChainCreated,
                                						 bookRequested:bookRequested

                                					}
            				});


				promptWindow.result.then(function (result) {
				$scope.loadingRequests = true;
				$scope.userExchangesCreated =[];

                           addExchangesCreatedByUser();
                			}, function () {
                				$log.info('Modal dismissed at: ' + new Date());
                			});



            		}
}
]);