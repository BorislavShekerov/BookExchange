var bookApp = angular.module('myApp', ['ngRoute','ui.bootstrap','ngToast','infinite-scroll','ngRateIt','ng.deviceDetector']);

bookApp.config(function ($routeProvider) {
    $routeProvider

    // route for the home page
        .when('/', {
        templateUrl: '/exchange',
        controller: 'exchangeController'
    })

    // route for the about page
    .when('/offers', {
        templateUrl: '/offers',
        controller: 'offersMadeController'
    })

    // route for the contact page
    .when('/requests', {
        templateUrl: '/requests',
        controller: 'offersReceivedController'
    })

    // route for the contact page
    .when('/history', {
        templateUrl: '/history',
        controller: 'exchangeHistoryController'
    })

    // route for the contact page
    .when('/account/:userEmail', {
        templateUrl: '/account',
        controller: 'AccountController'
    })
        .when('/account', {
              templateUrl: '/account',
              controller: 'AccountController'
          })
      .when('/offerExchange', {
            templateUrl: '/offerExchange',
            controller: 'exchangeOfferController'
        });



});

bookApp.run(['dataService','$uibModal', 'ngToast' ,'$document', function(dataService, $uibModal, ngToast, $document) {
    dataService.setEmail(email);

    if (userLoginCount == 1) {
    			var promptWindow = $uibModal.open({
    				animation: true,
    				templateUrl: '/app/category/openAddPreferredCategoryModal',
    				controller: 'AddPreferredCategoriesModalController'
    			});

    				promptWindow.result.then(function (preferredCategoriesList) {

                                    						ngToast.create({
                                    							className: 'success',
                                    							content: '<a href="#" class="">Categories added successfully!</a>',
                                    							timeout: 2000,
                                    							animation: 'slide'
                                    						});


                                    			                    			}, function () {
                                    				$log.info('Modal dismissed at: ' + new Date());
                                    			});
    		}

}]);