bookApp.controller('DirectRequestModalController', ['exchangeService','$scope', 'userToChooseFrom','$uibModalInstance','directExchange', function (exchangeService, $scope, userToChooseFrom, $uibModalInstance, directExchange) {
    $scope.userToChooseFrom = userToChooseFrom;
    $scope.chosenBook = {};
    $scope.bookChosen = false;

    $scope.imageHoveredOver = false;

    $scope.chooseBook = function(chosenBook){
        $scope.chosenBook = chosenBook;
        $scope.bookChosen = true;
    }

    $scope.rejectExchangeRequest = function(){
        var result = {accepted:false}
        exchangeService.rejectDirectExchange(exchangeChain.id);
        $uibModalInstance.close(result);
    }

     $scope.acceptExchangeRequest = function(){
        var result = {accepted:true,bookChosen : $scope.chosenBook };
            exchangeService.acceptDirectExchange( $scope.chosenBook.id,directExchange.id);
                    $uibModalInstance.close(result);
        }
           }]);