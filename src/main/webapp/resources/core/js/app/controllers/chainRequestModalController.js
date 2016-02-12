bookApp.controller('ChainRequestModalController', ['exchangeService','notificationsService','$scope', 'userToChooseFrom', 'userChoosingFromYou','$uibModalInstance','exchangeChain', function (exchangeService, notificationsService, $scope, userToChooseFrom, userChoosingFromYou, $uibModalInstance, exchangeChain) {
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
        exchangeService.rejectExchangeChainRequest(exchangeChain.id);
        $uibModalInstance.close();
    }

     $scope.acceptExchangeChainRequest = function(){
            exchangeService.acceptExchangeChainRequest( $scope.chosenBook.id,exchangeChain.id);
                    $uibModalInstance.close($scope.chosenBook);
        }
           }]);