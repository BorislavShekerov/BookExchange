bookApp.controller('sideMenuController', ['$scope', 'eventRecordService','$interval','$rootScope','deviceDetector', function ($scope, eventRecordService, $interval, $rootScope, deviceDetector) {
		  $rootScope.isMobile =  deviceDetector.isMobile();
            $scope.isMobile = $rootScope.isMobile ;

		$scope.sideMenuOptions = [{
			url: '#/',
			glyphicon: 'glyphicon-globe',
			label: "Exchange",
			selected: true,
			eventHappened: false,
			eventSum:0
    }, {
			url: '#offers',
			glyphicon: 'glyphicon-export',
			label: "Your Offers",
			selected: false,
			eventHappened: false,
			eventSum:0
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
			eventHappened : false,
			eventSum: 0
           }];
        $scope.selectedMenuItem = "Exchange";

           $scope.menuItemSelected = function(itemSelected){
                $scope.selectedMenuItem = itemSelected;

                updateMenuItemEventProperties(itemSelected,false,0);
                eventRecordService.setSelectedItem(itemSelected);
           }

           function updateMenuItemEventProperties(menuItemLabel, newEventsHappened, newEvents ){
                 angular.forEach($scope.sideMenuOptions, function (menuOption, index) {

                                if (menuOption.label === menuItemLabel) {
                                    menuOption.eventHappened = newEventsHappened;
                                    menuOption.eventSum = newEvents;
                                    }

                                    if(!newEventsHappened){
                                        eventRecordService.resetOfferEvents();
                                    }
                  });
           }

           function updateNewOfferEvents(){
                var newOffers = eventRecordService.getNewOfferEvents();
                                   if (newOffers > 0){
                                   updateMenuItemEventProperties("Your Offers",true,newOffers);
                                   }
           }

           function updateSelectedItem(){
                if ($scope.selectedMenuItem != eventRecordService.getSelectedItem()){
                    $scope.selectedMenuItem = eventRecordService.getSelectedItem();

                    updateMenuItemEventProperties( $scope.selectedMenuItem ,false,0);
                }
           }
               $interval(function(){
                   updateNewOfferEvents();
               },5000);

                $interval(function(){
                                  updateSelectedItem();
                              },50);
}
]);