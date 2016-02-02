bookApp.controller('notificationsController', ['exchangeService','$scope', 'userToChooseFrom', 'userChoosingFromYou', function (exchangeService, $scope, userToChooseFrom, userChoosingFromYou) {
    $scope.userToChooseFrom = userToChooseFrom;
    $scope.userChoosingFromYou = userChoosingFromYou;
}]);