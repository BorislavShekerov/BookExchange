bookApp.controller('offersReceivedController', ['$scope', 'dataService', 'exchangeService', function ($scope, dataService, exchangeService) {
		var userCurrentExchanges;

		function init() {
			exchangeService.getExchangeRequestsReceivedByUser().then(function (currentExchanges) {
				userCurrentExchanges = currentExchanges;


			});

		}

		init();
}
]);