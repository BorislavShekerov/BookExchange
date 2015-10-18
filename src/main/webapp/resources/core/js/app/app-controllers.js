bookApp.controller('welcomeController', ['$scope', 'dummyData', function ($scope, dummyData) {
    $scope.searchIconClicked = false;
    $scope.searchFor = '';
    $scope.bookRows = dummyData.bookRows;
    $scope.genresSelected = [];
    $scope.bookToSearchFor = '';
    $scope.selectedCategories = [];
    $scope.bookCategories = dummyData.bookCategories;
    $scope.paginationPages = dummyData.paginationPages;

    $scope.doSomething = function () {
        console.log($scope.bookToSearchFor);
    };

    $scope.removeCategory = function (category) {
        $scope.selectedCategories.splice($.inArray(category, $scope.selectedCategories), 1);
    };

    $scope.addBookCategoryToFilter = function (bookCategory) {
        $scope.selectedCategories.push(bookCategory);
    };

}]);