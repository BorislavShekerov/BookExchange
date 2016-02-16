bookApp.controller('DirectRequestModalController', ['exchangeService','$scope', 'userToChooseFrom','$uibModalInstance','directExchange', function (exchangeService, $scope, userToChooseFrom, $uibModalInstance, directExchange) {
    $scope.userToChooseFrom = userToChooseFrom;
    $scope.chosenBook = {};
    $scope.bookChosen = false;

    $scope.imageHoveredOver = false;

    function init(){
        angular.forEach($scope.userToChooseFrom.booksPostedOnExchange,function(book,index){
         book.selected = false;
        });
    }

    init();
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