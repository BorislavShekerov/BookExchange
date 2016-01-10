bookApp.service("dataService", ['$http', function ($http) {
    var userDetails = {};
    var categories = [];
    var username = "";

    function fetchUserDetails() {
        $http.get('/app/udetails/'+username).
        success(function (data, status, headers, config) {
            userDetails = data;
        }).
        error(function (data, status, headers, config) {
            alert("Error");
        });

    }

    var setUsername = function (uName) {
                if (username == "") {
                    username = uName;
                    fetchUserDetails();
                }
            };

     var setCategories = function (categs) {
            if (categories.length == 0) {
                categories = categs;
            }
        };

    var getCategories = function () {
                return categories;
            };


     var getUserData= function () {
           return userDetails;
        };

    return {
        setUsername: setUsername,
        getUserData:getUserData,
        setCategories: setCategories,
        getCategories: getCategories
    };

}]);

bookApp.service("exchangeService",  function () {
    var bookToExchangeFor= {};

    var setBookToExchangeFor = function (book) {
             bookToExchangeFor = book;
            };

    var getBookToExchangeFor = function () {
                return bookToExchangeFor;
            };


    return {
        setBookToExchangeFor: setBookToExchangeFor,
        getBookToExchangeFor: getBookToExchangeFor
    };

});