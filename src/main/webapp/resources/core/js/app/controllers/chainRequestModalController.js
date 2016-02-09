bookApp.controller('ChainRequestModalController', ['exchangeService','notificationsService','$scope', 'userToChooseFrom', 'userChoosingFromYou','$uibModalInstance', 'notification', function (exchangeService, notificationsService, $scope, userToChooseFrom, userChoosingFromYou, $uibModalInstance, notification) {
    $scope.userToChooseFrom = userToChooseFrom;
    $scope.userChoosingFromYou = userChoosingFromYou;
    $scope.chosenBook = {};
    $scope.bookChosen = false;

    $scope.imageHoveredOver = false;

    $scope.chooseBook = function(chosenBook){
        $scope.chosenBook = chosenBook;
        $scope.bookChosen = true;
    }

    $scope.rejectExchangeChainRequest = function(){
        exchangeService.rejectExchangeChainRequest(notification.exchangeChainID);
        notificationsService.setNotificationSeen(notification);
        $uibModalInstance.close();
    }

     $scope.acceptExchangeChainRequest = function(){
            exchangeService.acceptExchangeChainRequest( $scope.chosenBook.id,notification.exchangeChainID);
                    notificationsService.setNotificationSeen(notification);
                    $uibModalInstance.close();
        }
           }]);