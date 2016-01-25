bookApp.controller('exchangeHistoryController', ['$scope', 'dataService', function ($scope, dataService) {
		var userData = dataService.getUserData();
		$scope.userExchangeHistory = userData.exchangeHistory;

		$.each($scope.userExchangeHistory, function (index, value) {
			if (value.bookOfferedInExchange.ownedBy == userData.username) {

				value.bookOfferedInExchange.ownerAvatar = "../" + value.bookOfferedInExchange.ownerAvatar;
				value.bookPostedOnExchange.ownerAvatar = "../" + value.bookPostedOnExchange.ownerAvatar;

				value.offered = true;
			} else if (value.bookPostedOnExchange.ownedBy == userData.username) {

				value.bookOfferedInExchange.ownerAvatar = "../" + value.bookOfferedInExchange.ownerAvatar;
				value.bookPostedOnExchange.ownerAvatar = "../" + value.bookPostedOnExchange.ownerAvatar;

				value.offered = false;
			}
		});
}
]);