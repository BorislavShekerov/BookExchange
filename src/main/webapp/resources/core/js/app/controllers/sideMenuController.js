bookApp.controller('sideMenuController', ['$scope', function ($scope) {
		$scope.sideMenuOptions = [{
			url: '#/',
			glyphicon: 'glyphicon-globe',
			label: "Exchange",
			selected: true
    }, {
			url: '#offers',
			glyphicon: 'glyphicon-export',
			label: "Your Offers",
			selected: false
          }, {
			url: '#requests',
			glyphicon: 'glyphicon-import',
			label: "Requests Received",
			selected: false
                     }, {
			url: '#history',
			glyphicon: 'glyphicon-retweet',
			label: "Exchange History",
			selected: false
                                           }, {
			url: '#account',
			glyphicon: 'glyphicon-user',
			label: "Account",
			selected: false
           }];
        $scope.selectedMenuItem = "Exchange";

           $scope.menuItemSelected = function(itemSelected){
                $scope.selectedMenuItem = itemSelected;
           }
}
]);