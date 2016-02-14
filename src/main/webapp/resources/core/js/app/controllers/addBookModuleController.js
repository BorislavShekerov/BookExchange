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
            $scope.selectedCategory =category;
            $scope.selectedCategoryName = category.categoryName;
            $scope.categorySelected = true;
		}

        function init(){
            dataService.getAllCategories().then(function(result){
             $scope.allCategories = result.data;
            },function(err){
                console.log(err);
            });
        }

        init();
}
]);