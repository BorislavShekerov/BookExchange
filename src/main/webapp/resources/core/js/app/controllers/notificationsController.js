bookApp.controller('notificationsController', ['notificationsService', 'exchangeService', '$scope', '$interval', 'dataService', '$uibModal', function (notificationsService, exchangeService, $scope, $interval, dataService, $uibModal) {
	$scope.notifications = [];
	$scope.notificationsDropdownOpen = false;
	$scope.unseenNotifications = 0;

	$scope.notificationsDropdownOpened = function () {

		$scope.notificationsDropdownOpen = !$scope.notificationsDropdownOpen;
		if ($scope.notificationsDropdownOpen) {

			angular.forEach($scope.notifications, function (notification, index) {
				if (notification.notificationType == "NEW_USER" && !notification.isSeen) {

					notificationsService.setNotificationSeen(notification).then(function (response) {
						$scope.unseenNotifications--;
					}, function (error) {
						console.log(error);
					});;
				}
			});
		}

	}



	$scope.notificationClicked = function (notification) {
		if (notification.notificationType == 'EXCHANGE_CHAIN_INVITATION') {
			exchangeService.getChainDetailsForUser(notification.exchangeChainID).then(function (usersToExchangeWith) {
                var exchangeChain = {id:notification.exchangeChainID};
				var promptWindow = $uibModal.open({
					animation: true,
					templateUrl: '/app/openChainRequestModal',
					controller: 'ChainRequestModalController',
					size: 'lg',
					resolve: {
						 userToChooseFrom: usersToExchangeWith[0],
							userChoosingFromYou: usersToExchangeWith[1],
						exchangeChain : exchangeChain
					}
				});

				promptWindow.result.then(function (result) {
                        $scope.notifications.push(notification);
                        $scope.unseenNotifications --;
                			}, function () {
                				$log.info('Modal dismissed at: ' + new Date());
                			});
			}, function (error) {
				console.log(error);
			});


		}
	}

	$scope.toggled = function (open) {
		$log.log('Dropdown is now: ', open);
	};

	function hideEmailFromMessage(notification) {
		var message = notification.message;
		notification.exchangeChainID = message.substring(message.indexOf('(') + 1, message.indexOf(')'));
		notification.message = message.substring(0, message.indexOf('(')) + message.substring(message.indexOf(')') + 1, message.length);
	}

	function isNotificationInList(notSeenNotification) {
		var isNotificationInList = false;
		angular.forEach($scope.notifications, function (notification, index) {
			if (notification.id == notSeenNotification.id) {
				isNotificationInList = true;
			}
		});

		return isNotificationInList;
	}
	$interval(function () {
		notificationsService.getNewNotificationsForUser().then(function (response) {
			if (response.length > 0) {

				angular.forEach(response, function (newNotification, index) {
					if (!isNotificationInList(newNotification)) {

						if (newNotification.notificationType != 'NEW_USER') {
							hideEmailFromMessage(newNotification);
						}

						$scope.unseenNotifications++;
						$scope.notifications.unshift(newNotification);
					}
				});
			}
		}, function (error) {
			console.log(error);
		});
	}, 30000);

}]);