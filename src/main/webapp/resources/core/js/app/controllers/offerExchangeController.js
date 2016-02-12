bookApp.controller('exchangeOfferController', ['$scope', '$http', '$location', 'dataService', 'exchangeService', '$uibModalInstance', 'eventRecordService', function ($scope, $http, $location, dataService, exchangeService, $uibModalInstance, eventRecordService) {
	$scope.userDetails = dataService.getUserData();
	$scope.bookToExchangeFor = exchangeService.getBookToExchangeFor();
	$scope.selectedBook = 'Select Book';
	$scope.exchangeOptionExists = false;
	$scope.exchangeOptionPath = [];

	var csrfToken = $("meta[name='_csrf']").attr("content");

	$scope.userHasBooksOfInterest = function () {
		$.each($scope.userDetails.booksPostedOnExchange, function (index, value) {
			if ($scope.bookToExchangeFor.ownerCategoriesOfInterest.indexOf(value.category.categoryName) > -1) {
				return true;
			}
		});
		return false;
	}

    $scope.initiateExchangeChain = function(){
            var dataToPost = {};

                var userEmails = [];

            angular.forEach($scope.exchangeOptionPath,function(value,index){
                userEmails.push(value.email);
            });

            dataToPost.userChain = userEmails;
            dataToPost.bookRequestedTitle = $scope.bookToExchangeFor.title;

        	var req = {
        			method: 'POST',
        			url: '/app/exchangeChainOrder',
        			headers: {
        				'X-CSRF-TOKEN': csrfToken
        			},
        			data: dataToPost
        		}

        		$http(req).then(function (response) {
        		   if(response.data == "Success"){
        		    $uibModalInstance.close();
        		   }
        		}, function (response) {
        			alert("error");
        		});

    }
	$scope.exploreOtherOptions = function () {
		var exchangeOrder = constructExchangeOrder();

		var req = {
			method: 'POST',
			url: '/app/exploreOptions',
			headers: {
				'X-CSRF-TOKEN': csrfToken
			},
			data: exchangeOrder
		}


		$http(req).then(function (response) {
			$('#processing-modal').modal('hide');
			$scope.exchangeOptionPath = response.data;
			$scope.exchangeOptionExists = true;
		}, function () {
			alert("error");
			$scope.exchangeOptionExists = false;
		});

	}

	var constructExchangeOrder = function () {
		var exchangeOrder = {};
		exchangeOrder.bookUnderOffer = $scope.bookToExchangeFor.title;
		exchangeOrder.bookUnderOfferOwner = $scope.bookToExchangeFor.ownerEmail;

		return exchangeOrder;

	}
	$scope.initiateOffer = function () {

		var exchangeOrder = constructExchangeOrder();

		var req = {
			method: 'POST',
			url: '/app/exchangeOrder',
			headers: {
				'X-CSRF-TOKEN': csrfToken
			},
			data: exchangeOrder
		}

		$http(req).then(function (response) {
		    eventRecordService.recordNewOfferEvents();
			$uibModalInstance.close();
		}, function (response) {
			alert("error");
		});
	}

	$scope.bookChosen = function(selectedBook){
	    $scope.selectedBook = selectedBook;
	}
}]);