bookApp.controller('notificationsController', ['notificationsService', 'exchangeService', '$scope', '$interval', 'dataService', '$uibModal', 'ratingService', '$rootScope', 'deviceDetector','eventRecordService', function (notificationsService, exchangeService, $scope, $interval, dataService, $uibModal, ratingService, $rootScope, deviceDetector,eventRecordService) {
	var userEmail = dataService.getEmail();
	$scope.notifications = [];
	$scope.notificationsDropdownOpen = false;
	$scope.unseenNotifications = 0;
	$scope.userRatings = [];
	$scope.isUserMobile = deviceDetector.isMobile();
	$rootScope.isUserMobile = $scope.isUserMobile;

	$scope.notificationsDropdownOpened = function () {

		$scope.notificationsDropdownOpen = !$scope.notificationsDropdownOpen;
		if ($scope.notificationsDropdownOpen) {

			angular.forEach($scope.notifications, function (notification, index) {

				notificationsService.setNotificationSeen(notification).then(function (response) {
					$scope.unseenNotifications--;
				}, function (error) {
					console.log(error);
				});;
			});
		}

	}

	function init() {
		requestRatingsForUser();

		notificationsService.getAllNotificationsForUser().then(function (response) {

			angular.forEach(response, function (newNotification, index) {
                            setUrlToNotification(newNotification);


				if (newNotification.notificationType == 'RATE_EXCHANGE_RATING') {
					checkIfRated(newNotification);
				}

                if(!newNotification.seen){
                    eventRecordService.recordNewNotification(newNotification);
                    $scope.unseenNotifications++;
                }
				$scope.notifications.unshift(newNotification);
			});
		}, function (error) {
			console.log(error);
		});
	}

	init();

    function findUserToChooseFrom(exchangeChain){
        var userToChooseFrom = {};
        angular.forEach(exchangeChain.exchangeChainRequests,function(exchangeChainRequest,index){
            if(exchangeChainRequest.userOfferingTo.email == userEmail){
                userToChooseFrom = exchangeChainRequest.requestFor;
            }
        });

       return userToChooseFrom;
    }
    function findUserChoosingFromYou(exchangeChain){
      var userOfferingTo = {};
            angular.forEach(exchangeChain.exchangeChainRequests,function(exchangeChainRequest,index){
                if(exchangeChainRequest.userChoosingFrom.email == userEmail){
                    userOfferingTo = exchangeChainRequest.requestFor;
                }
            });

           return userOfferingTo;
    }

	$scope.notificationClicked = function (notification) {
		if (notification.notificationType == 'EXCHANGE_CHAIN_INVITATION') {
                var userToChooseFrom = findUserToChooseFrom(notification.bookExchangeChain);
                var userChoosingFromYou = findUserChoosingFromYou(notification.bookExchangeChain);

				var promptWindow = $uibModal.open({
					animation: true,
					templateUrl: '/app/openChainRequestModal',
					controller: 'ChainRequestModalController',
					size: 'lg',
					resolve: {
						userToChooseFrom: userToChooseFrom,
						userChoosingFromYou: userChoosingFromYou,
						exchangeChain: notification.bookExchangeChain
					}
				});

				promptWindow.result.then(function (result) {
					$scope.notifications.push(notification);
					$scope.unseenNotifications--;
				}, function () {
					$log.info('Modal dismissed at: ' + new Date());
				});


		}else if (notification.notificationType == 'DIRECT_EXCHANGE_INVITATION'){
           				var promptWindow = $uibModal.open({
           					animation: true,
           					templateUrl: '/app/openDirectRequestModal',
           					controller: 'DirectRequestModalController',
           					size: 'lg',
           					resolve: {
           						userToChooseFrom: notification.directBookExchange.exchangeInitiator,
           						directExchange: notification.directBookExchange
           					}
         });

		}
	}

	$scope.toggled = function (open) {
		$log.log('Dropdown is now: ', open);
	};

	function isNotificationInList(notSeenNotification) {
		var isNotificationInList = false;
		angular.forEach($scope.notifications, function (notification, index) {
			if (notification.id == notSeenNotification.id) {
				isNotificationInList = true;
			}
		});

		return isNotificationInList;
	}

	$scope.userRatingSet = function (notification) {
		notification.showRateButton = true;
	}

	$scope.rateUser = function (notification) {
		ratingService.addRating(notification.userRatedEmail, notification.ratingComment, notification.rating);
		notification.rated = true;
		notification.showRateButton = false;
	}


	$interval(function () {
		notificationsService.getNewNotificationsForUser().then(function (response) {
			if (response.length > 0) {

				angular.forEach(response, function (newNotification, index) {
				                            setUrlToNotification(newNotification);
					if (!isNotificationInList(newNotification)) {

						if (newNotification.notificationType != 'NEW_USER') {
							//							hideEmailFromMessage(newNotification);
						} else if (newNotification.notificationType == 'RATE_EXCHANGE_RATING') {
							checkIfRated(newNotification);
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


	function checkIfRated(newNotification) {
		newNotification.rating = 0;
		newNotification.rated = false;
		newNotification.showRateButton = false;

		angular.forEach($scope.userRatings, function (rating, index) {
			if (rating.commentForEmail == newNotification.userRatedEmail) {
				newNotification.rated = true;
			}
		});
	}

	function requestRatingsForUser() {
		ratingService.getRatingsForUser().then(function (ratings) {
			angular.forEach(ratings, function (rating, index) {
				if (rating.commentatorEmail == userEmail) {
					$scope.userRatings.push(rating);
				}


			});
			$scope.ratingLoading = false;


		}, function (err) {
			console.log(err);
		});

	}

	function setUrlToNotification(notification){
	 if(notification.notificationType == 'EXCHANGE_CHAIN_REJECTION' || notification.notificationType == 'EXCHANGE_CHAIN_ACCEPTANCE' || notification.notificationType == 'EXCHANGE_CHAIN_SUCCESS' || notification.notificationType == 'DIREXT_EXHANGE_REJECTED' || notification.notificationType == 'DIRECT_EXCHANGE_ACCEPTED'){
                notification.url = "#offers";
            }else if(notification.notificationType == 'EXCHANGE_CHAIN_INVITATION' || notification.notificationType == 'DIRECT_EXCHANGE_INVITATION'){
                notification.url = "#requests";
            }
	}

}]);