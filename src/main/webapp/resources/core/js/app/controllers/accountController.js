bookApp.controller('AccountController', ['$scope', 'dataService','bookService','$uibModal','ngToast','categoryService','eventRecordService','$routeParams', function ($scope, dataService, bookService, $uibModal, ngToast, categoryService,eventRecordService,$routeParams) {

    $scope.loading = true;
    $scope.isEditable = false;
    var currentUser =  dataService.getUserData();
    if($routeParams.userEmail){
        if(currentUser.email != $routeParams.userEmail){
         dataService.getDetailsForUser($routeParams.userEmail).then(function(userDetails){
                    $scope.loading = false;
                    $scope.userAccount =userDetails;
                    $scope.isEditable = false;
             eventRecordService.setSelectedItem("");
                },function (error){
                });
        }else{
              $scope.loading = false;
                    $scope.isEditable = true;
                    $scope.userAccount = currentUser;
                      eventRecordService.setSelectedItem("Account");
        }
    }else{
        $scope.loading = false;
        $scope.isEditable = true;
        $scope.userAccount = currentUser;
        eventRecordService.setSelectedItem("Account");
    }

    $scope.librarySelected = true;
    $scope.commentsSelected = false;


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

    $scope.openExchangeModal = function(book){
        var promptWindow = $uibModal.open({
                				animation: true,
                				templateUrl: '/offerExchange',
                				controller: 'exchangeOfferController',
                				resolve: {
                                           						bookToExchangeFor: bookInterestedIn
                                           					}
                			});

                		          promptWindow.result.then(function (result) {
                		                if(result.exchangeCreated){
                		                     ngToast.create({
                                                                                                                       							className: 'success',
                                                                                                                       							content: '<a href="#" class="">Exchange created successfully!</a>',
                                                                                                                       							timeout: 2000,
                                                                                                                       							animation: 'slide'
                                                                                                                       						});

                                                                            			}}, function () {
                                                                            				$log.info('Modal dismissed at: ' + new Date());
                                                                            			});
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