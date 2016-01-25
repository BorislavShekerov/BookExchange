bookApp.controller('offersController', ['$scope', 'dataService', 'exchangeService', function ($scope, dataService, exchangeService) {
		var userCurrentExchanges;
		$scope.userCurrentOffersMade = [];
		$scope.userCurrentOffersReceived = [];
		var userEmail = dataService.getEmail();

		function init() {
			exchangeService.getUserCurrentExchanges().then(function (currentExchanges) {
				userCurrentExchanges = currentExchanges;

				$.each(userCurrentExchanges, function (index, value) {
					if (value.bookOfferedInExchange.ownerEmail == userEmail) {
						value.bookOfferedInExchange.ownerAvatar = "../" + value.bookOfferedInExchange.ownerAvatar;
						value.bookPostedOnExchange.ownerAvatar = "../" + value.bookPostedOnExchange.ownerAvatar;

						$scope.userCurrentOffersMade.push(value);
					} else if (value.bookPostedOnExchange.ownerEmail == userEmail) {

						value.bookOfferedInExchange.ownerAvatar = "../" + value.bookOfferedInExchange.ownerAvatar;
						value.bookPostedOnExchange.ownerAvatar = "../" + value.bookPostedOnExchange.ownerAvatar;

						$scope.userCurrentOffersReceived.push(value);
					}
				});
			});

		}

		init();
}
]);