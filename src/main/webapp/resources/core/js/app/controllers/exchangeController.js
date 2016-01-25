bookApp.controller('exchangeController', ['$scope', '$location', 'dataService', 'exchangeService', '$http', '$uibModal', 'ngToast', function ($scope, $location, dataService, exchangeService, $http, $uibModal, ngToast) {
	dataService.setEmail(email);
	$scope.searchFor = '';
    $scope.filterExpanded = false;
    $scope.isCategoryFilterOpen = true;
    $scope.allCategories = [];
    $scope.filterModel = [];

	$scope.booksDisplayed = [];
	$scope.showLoadingIndicator = true;

	function init() {
		if (userLoginCount == 1) {
			var promptWindow = $uibModal.open({
				animation: true,
				templateUrl: '/app/firstTimeLogin',
				controller: 'FirstTimeLoginModalController'
			});

			promptWindow.result.then(function (preferredCategoriesList) {
				dataService.addUserPreferredCategories(preferredCategoriesList).then(function (response) {
					if (response == "Success") {
						ngToast.create({
							className: 'success',
							content: '<a href="#" class="">Categories added successfully!</a>',
							timeout: 2000,
							animation: 'slide'
						});

					} else {
						ngToast.create({
							className: 'error',
							content: '<a href="#" class="">An error occured, please try using the account section.</a>',
							timeout: 2000
						});
					}
				}, function (error) {
					console.log(error);
				});
			}, function () {
				$log.info('Modal dismissed at: ' + new Date());
			});
		}
	}

	init();

      $scope.$watchCollection('$scope.allCategories', function () {
        angular.forEach($scope.checkModel, function (value, key) {
          if (value.selected) {
            console.log(value.categoryName);
          }
        });
      });

	$http.get('/getAllBooks').
	success(function (data, status, headers, config) {
		$scope.booksDisplayed = data;
		$scope.showLoadingIndicator = false;
	}).
	error(function (data, status, headers, config) {
		alert("Error");
	});

	$http.get('/getAllCategories').
	success(function (data, status, headers, config) {
		dataService.setCategories(data);

		 angular.forEach(data, function(value, index){
            filterModel.value.categoryName
         });

         $scope.allCategories = data;
	}).
	error(function (data, status, headers, config) {
		alert("Error");
	});

	$scope.initiateExchange = function (title, ownedBy) {
		$.each($scope.booksDisplayed, function (index, value) {
			if (value.title == title && value.ownedBy == ownedBy) {
				exchangeService.setBookToExchangeFor(value);
				$location.path('offerExchange');
			}
		});
	}
}]);
