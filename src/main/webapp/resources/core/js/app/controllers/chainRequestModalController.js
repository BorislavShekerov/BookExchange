bookApp.controller('ChainRequestModalController', ['exchangeService','notificationsService','$scope', 'userToChooseFrom', 'userChoosingFromYou','$uibModalInstance','exchangeChain','$rootScope', function (exchangeService, notificationsService, $scope, userToChooseFrom, userChoosingFromYou, $uibModalInstance, exchangeChain,$rootScope) {
    $scope.userToChooseFrom = userToChooseFrom;
    $scope.userChoosingFromYou = userChoosingFromYou;
    $scope.chosenBook = {};
    $scope.isUserMobile = $rootScope.isUserMobile;
    $scope.bookChosen = false;

    $scope.imageHoveredOver = false;


        $scope.chooseBook = function(chosenBook){
            $scope.chosenBook = chosenBook;
            chosenBook.selected = true;
            $scope.bookChosen = true;
        }

         $scope.deselectBook = function(chosenBook){
                $scope.chosenBook = {};
                chosenBook.selected = false;
                $scope.bookChosen = false;
            }

    $scope.rejectExchangeChainRequest = function(){
          var result = {accepted:false}
        exchangeService.rejectExchangeChainRequest(exchangeChain.id);
        $uibModalInstance.close(result);
    }

     $scope.acceptExchangeChainRequest = function(){
      var result = {accepted:true,bookChosen : $scope.chosenBook };
            exchangeService.acceptExchangeChainRequest( $scope.chosenBook.id,exchangeChain.id);
                    $uibModalInstance.close(result);
        }
           }]);