bookApp.controller('AccountController', ['$scope', 'dataService','bookService','$uibModal','ngToast','categoryService','eventRecordService', function ($scope, dataService, bookService, $uibModal, ngToast, categoryService,eventRecordService) {
	$scope.userAccount = dataService.getUserData();
    $scope.librarySelected = true;
    $scope.commentsSelected = false;
    eventRecordService.setSelectedItem("Account");

    $scope.libraryTabSelected = function(){
        if(!$scope.librarySelected){
         $scope.librarySelected = true;
                    $scope.commentsSelected = false;
        }else{
            $scope.librarySelected = false;
        }
        }



  $scope.commentsTabSelected = function(){
        if(!$scope.commentsSelected){
         $scope.commentsSelected = true;
                    $scope.librarySelected = false;
        }else{
            $scope.commentsSelected = false;
        }

    }

    $scope.removeBook = function(bookToRemove){
        bookService.removeBookForUser(bookToRemove.title).then(function(booksPosted){
            		angular.forEach(booksPosted, function (bookPosted, index) {
            		    if(bookPosted.title == bookToRemove.title){
            		        raiseBookRequestedByUserWarning();
            		    }
                    });

            $scope.userAccount.booksPostedOnExchange = result;
        },function(err){
        console.log(err);});
    }


     function  raiseBookRequestedByUserWarning(){
      ngToast.create({
                                         							className: 'danger',
                                         							content: '<a href="#" class="">Book requested in an exchange!</a>',
                                         							timeout: 2500,
                                         							animation: 'slide'
                                         						});W
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