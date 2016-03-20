bookApp.controller('sideMenuController', ['$scope', 'eventRecordService', '$interval', '$rootScope', 'deviceDetector', function ($scope, eventRecordService, $interval, $rootScope, deviceDetector) {
		$rootScope.isMobile = deviceDetector.isMobile();
		$scope.isMobile = $rootScope.isMobile;


		function init() {
			$scope.sideMenuOptions = [{
				url: '#/',
				glyphicon: 'glyphicon-globe',
				label: "Exchange",
				selected: true,
				eventHappened: false,
				eventSum: 0
             }, {
				url: '#offers',
				glyphicon: 'glyphicon-export',
				label: "Your Offers",
				selected: false,
				eventHappened: false,
				eventSum: 0
                   }, {
				url: '#requests',
				glyphicon: 'glyphicon-import',
				label: "Requests Received",
				selected: false,
				eventHappened: false,
				eventSum: 0
                              }, {
				url: '#account',
				glyphicon: 'glyphicon-user',
				label: "Account",
				selected: false,
				eventHappened: false,
				eventSum: 0
                    }];

			if ($scope.isMobile) {
				var logOutButton = {
					url: '#',
					glyphicon: 'glyphicon-off',
					label: "Log Out",
					selected: false,
					eventHappened: false,
					eventSum: 0
				};
				$scope.sideMenuOptions.push(logOutButton);

			}
		}

		init();
		$scope.selectedMenuItem = "Exchange";

		$scope.menuItemSelected = function (itemSelected) {
			if ($scope.isMobile) {
				if (itemSelected == "Log Out") {
					document.getElementById("logoutForm").submit();
				} else {
					document.getElementById("nav-toggle-button").click();
					$scope.selectedMenuItem = itemSelected;


				}
			}

			updateMenuItemEventProperties(itemSelected, false, 0);
			eventRecordService.setSelectedItem(itemSelected);

		}

		function updateMenuItemEventProperties(menuItemLabel, newEventsHappened, newEvents) {
			angular.forEach($scope.sideMenuOptions, function (menuOption, index) {

				if (menuOption.label === menuItemLabel) {
					menuOption.eventHappened = newEventsHappened;
					menuOption.eventSum = newEvents;
				}


			});

			if (!newEventsHappened) {
				if (menuItemLabel == "Your Offers") {
					eventRecordService.resetOfferEvents();
				} else if (menuItemLabel == "Requests Received") {
					eventRecordService.resetRequestEvents();
				}
			}
		}

		function updateNewOfferEvents() {
			var newOffers = eventRecordService.getNewOfferEvents();
			if (newOffers > 0) {
				updateMenuItemEventProperties("Your Offers", true, newOffers);
			}
		}

		function updateNewRequestEvents() {
			var newRequests = eventRecordService.getNewRequestEvents();
			if (newRequests > 0) {
				updateMenuItemEventProperties("Requests Received", true, newRequests);
			}
		}

		function updateSelectedItem() {
			if ($scope.selectedMenuItem != eventRecordService.getSelectedItem()) {
				$scope.selectedMenuItem = eventRecordService.getSelectedItem();

				updateMenuItemEventProperties($scope.selectedMenuItem, false, 0);
			}
		}
		$interval(function () {
			updateNewOfferEvents();
			updateNewRequestEvents();
		}, 5000);

		$interval(function () {
			updateSelectedItem();
		}, 50);
}
]);