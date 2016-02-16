bookApp.controller('NavBarSearchController', ['$scope','searchService','$interval', function ($scope,searchService, $interval) {
    $scope.searchBarVisible = false;
    $scope.searchPhrase = "";
    $interval(function(){
            $scope.searchBarVisible = searchService.getSearchNavBarVisible();
             $scope.searchPhrase = searchService.getSearchPhrase();
    },100);

     $scope.$watch('searchPhrase', function() {
            searchService.setSearchPhrase($scope.searchPhrase);
        });

}]);