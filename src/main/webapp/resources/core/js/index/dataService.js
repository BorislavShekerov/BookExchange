bookApp.service("dataService", ['$http', function ($http) {
    var dataService = this;

    $http.get('/getAllBooks').
    success(function (data, status, headers, config) {
        dataService.booksDisplayed = data;
    }).
    error(function (data, status, headers, config) {
        alert("Error");
    });
}]);