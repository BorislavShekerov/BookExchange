bookApp.controller('ExploreMoreOptionsModalController', ['$scope', 'exchangeService', '$uibModalInstance','exchangeChain', 'bookRequested', function ($scope, exchangeService, $uibModalInstance,exchangeChain, bookRequested ) {
	$scope.loading = true;
    $scope.pathFound = false;
    $scope.bookToExchangeFor = bookRequested;
	function init(){
	    exchangeService.exploreOtherOptions(exchangeChain.id).then(function(response){

	        if (response.chain.length > 0){
	           $scope.exchangeOptionPath = response.chain;
            	        $scope.closedComponent = response.closedComponent;
            	        $scope.pathFound = true;
	        }

	        $scope.loading = false;

	    },function(err){
	    console.log(err);
	    })
	}

	init();

	    $scope.initiateExchangeChain = function(){
                var exchangeOrder = {};

                    var userEmails = [];

                angular.forEach($scope.exchangeOptionPath,function(value,index){
                    userEmails.push(value.email);
                });

                exchangeOrder.userChain = userEmails;
                exchangeOrder.bookRequestedTitle = $scope.bookToExchangeFor.title;
                exchangeOrder.closedComponent = $scope.closedComponent;

            		 exchangeService.initiateExchangeChain(exchangeOrder).then(function (response) {

            		    $uibModalInstance.close(response);

            		}, function (response) {
            		 $uibModalInstance.close();
            			alert("error");
            		});

        }
}]);