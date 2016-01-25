bookApp.controller('exchangeOfferController', ['$scope', '$http', '$location', 'dataService', 'exchangeService', function ($scope, $http, $location, dataService, exchangeService) {
	$scope.userDetails = dataService.getUserData();
	$scope.bookToExchangeFor = exchangeService.getBookToExchangeFor();
	$scope.selectedBook = "Select a book";
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
		exchangeOrder.bookUnderOfferOwner = $scope.bookToExchangeFor.ownedBy;
		exchangeOrder.bookOfferedInExchange = $scope.selectedBook;
		exchangeOrder.bookOfferedInExchangeOwner = $scope.userDetails.username;

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
			alert(response);
		}, function (response) {
			alert("error");
		});
	}
}]);