bookApp.controller('AccountController', ['$scope', 'dataService','bookService','$uibModal','ngToast','categoryService', function ($scope, dataService, bookService, $uibModal, ngToast, categoryService) {
	$scope.userAccount = dataService.getUserData();

	$scope.selectorClicked = "library";
	$scope.shouldShowLibrary = function(){
	    return $scope.selectorClicked  == 'library';
	}

    $scope.removeBook = function(book){
        bookService.removeBookForUser(book.title).then(function(result){
            $scope.userAccount.booksPostedOnExchange = result;
        },function(err){
        console.log(err);});
    }

    $scope.openAddPreferredCategoriesModal = function (){
        var promptWindow = $uibModal.open({
        				animation: true,
        				templateUrl: '/app/category/openAddPreferredCategoryModal',
        				controller: 'AddPreferredCategoriesModalController'
        			});

        					promptWindow.result.then(function (preferredCategoriesList) {

                    						ngToast.create({
                    							className: 'success',
                    							content: '<a href="#" class="">Categories added successfully!</a>',
                    							timeout: 2000,
                    							animation: 'slide'
                    						});


                    			                    			}, function () {
                    				$log.info('Modal dismissed at: ' + new Date());
                    			});
    }

    $scope.openPostBookModal = function(){
        	var promptWindow = $uibModal.open({
        					animation: true,
        					templateUrl: '/app/openAddBookModal',
        					controller: 'AddBookModalController',
        					size: 'lg'
        				});

        				promptWindow.result.then(function (result) {
        				$scope.userAccount.booksPostedOnExchange = result;
                               ngToast.create({
                               							className: 'success',
                               							content: '<a href="#" class="">Categories added successfully!</a>',
                               							timeout: 2000,
                               							animation: 'slide'
                               						});
                        			}, function () {
                        				$log.info('Modal dismissed at: ' + new Date());
                        			});

    }

    $scope.removeCategoryInterestedIn = function(category){
        categoryService.removeCategoryInterestedIn(category).then(function(result){
            $scope.userAccount.categoriesInterestedIn = result;
        },function(err){
            console.log(err);
        });
    }
}]);