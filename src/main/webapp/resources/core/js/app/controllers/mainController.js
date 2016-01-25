bookApp.controller('mainController', ['$scope', '$location', 'dataService', 'exchangeService', '$http', '$uibModal', 'ngToast', function ($scope, $location, dataService, exchangeService, $http, $uibModal, ngToast) {
	dataService.setEmail(email);

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

}]);

