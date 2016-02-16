bookApp.controller('AddBookModalController', ['$scope', '$http', 'dataService','bookService','$uibModalInstance', function ($scope, $http, dataService, bookService, $uibModalInstance) {
		$scope.bookTitle = "";
		$scope.bookAuthor = "";
		$scope.formSubmitted = false;

        $scope.selectedCategoryName = "Choose a Category";
        $scope.selectedCategory = {};
        $scope.categorySelected = false;

        $scope.bookCoverImgSelected = false;
        $scope.bookCoverImg = "";

        $scope.allCategories = [];

		$scope.textChanged = function () {
			$scope.showBookCoverImage = true;
		}

        function addBook(){
            var bookToAdd = {
                title: $scope.bookTitle,
                author: $scope.bookAuthor,
                category:  $scope.selectedCategory,
                imgUrl : $scope.bookCoverImg
            };

            bookService.addBook(bookToAdd).then(function(result){
                $uibModalInstance.close(result);
            },function(err){
                console.log(err);
            });
        }

        $scope.closeModal = function(){
            $uibModalInstance.dismiss();
        }
		$scope.submitForm = function(formValid){
		    $scope.formSubmitted = true;
		    if(formValid){
		        var imgUrl = document.getElementById("avatarUploadInput").value;
		        if(!imgUrl){
		            $scope.bookCoverImgSelected = false;
		        }
		         $scope.bookCoverImg = imgUrl;
                $scope.bookCoverImgSelected = true;

                if($scope.categorySelected && $scope.bookCoverImgSelected ){
                    addBook();
                }
		    }
		}

		$scope.addCategory = function(category){
		if(!category.selected ){
		    disableCategoriesPreviouslyChecked();
		     $scope.selectedCategory =category;
             $scope.selectedCategoryName = category.categoryName;
                        category.selected =true;
                        $scope.categorySelected = true;
		} else{
		     $scope.selectedCategory ={};
                                    $scope.selectedCategoryName = category.categoryName;
                                     category.selected =false;
                                                            $scope.categorySelected = false;
		}


		}

        function disableCategoriesPreviouslyChecked(){
             angular.forEach($scope.allCategories,function(category,index){
             if( category.selected){
             category.selected = false;
             }

             });
        }

        function init(){
            dataService.getAllCategories().then(function(result){
            angular.forEach(result.data,function(category,index){
                category.selected = false;
            });
             $scope.allCategories = result.data;
            },function(err){
                console.log(err);
            });
        }

        init();
}
]);