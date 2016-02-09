bookApp.controller('offersMadeController', ['$scope', 'dataService', 'exchangeService', function ($scope, dataService, exchangeService) {
		var userCurrentExchanges;

		function init() {
			exchangeService.getExchangeRequestsInitiatedByUser().then(function (currentExchanges) {
				userCurrentExchanges = currentExchanges;


			},function (err){
			console.log(err);
			});

		}

		init();
}
]);