bookApp.controller('AddBookController', ['$scope','$uibModal','ngToast', function ($scope,$uibModal,ngToast) {

    $scope.openAddBookModal = function(){
        var promptWindow = $uibModal.open({
                					animation: true,
                					templateUrl: '/app/openAddBookModal',
                					controller: 'AddBookModalController',
                					size: 'lg'
                				});

        	promptWindow.result.then(function (result) {
                    ngToast.create({
                    							className: 'success',
                    							content: '<a href="#" class="">Book added successfully!</a>',
                    							timeout: 2000,
                    							animation: 'slide'
                    						});

                        			}, function () {
                        			});



    }
}]);