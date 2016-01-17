var bookApp = angular.module('myApp', ['ngRoute']);

bookApp.config(function ($routeProvider) {
    $routeProvider

    // route for the home page
        .when('/', {
        templateUrl: '/exchange',
        controller: 'mainController'
    })

    // route for the about page
    .when('/offers', {
        templateUrl: '/offers',
        controller: 'offersController'
    })

    // route for the contact page
    .when('/requests', {
        templateUrl: '/requests',
        controller: 'offersController'
    })

    // route for the contact page
    .when('/history', {
        templateUrl: '/history',
        controller: 'exchangeHistoryController'
    })

    // route for the contact page
    .when('/account', {
        templateUrl: '/account',
        controller: 'contactController'
    })

      .when('/offerExchange', {
            templateUrl: '/offerExchange',
            controller: 'exchangeController'
        });



});