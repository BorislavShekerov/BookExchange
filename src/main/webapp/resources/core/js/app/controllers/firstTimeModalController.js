bookApp.controller('AddPreferredCategoriesModalController', ['$scope', 'categoryService', '$http', '$uibModalInstance', function ($scope, categoryService, $http, $uibModalInstance) {
	$scope.loading = true;
	var allCategories = [];
	$scope.categoriesInterestedIn = [];

	$http.get('/getAllCategories').
	success(function (data, status, headers, config) {
		$scope.loading = false;
		angular.forEach(data,function(category,index){
            category.selected = false;
		});
		$scope.allCategories = data;
	}).
	error(function (data, status, headers, config) {
		alert("Error");
	});


	$scope.addCategoryInterestedIn = function (category) {
	    if (!category.selected){
	        $scope.categoriesInterestedIn.push(category);
	        category.selected = true;
	    }else{
	         $scope.categoriesInterestedIn.splice($scope.categoriesInterestedIn.indexOf(category), 1);
            	        category.selected = false;
	    }

	}

	$scope.closeModal = function () {
		$uibModalInstance.dismiss('cancel');
	};

	$scope.addPreferredGenres = function () {
	    $scope.loading =true;
	    angular.forEach($scope.categoriesInterestedIn,function(categoryInterestedIn, index){
            delete categoryInterestedIn.selected;
	    });
		categoryService.addCategoryInterestedIn($scope.categoriesInterestedIn).then(function(result){
		    $scope.categoriesInterestedIn = result;
		    $uibModalInstance.close($scope.categoriesInterestedIn);
		},function(err){
		   $uibModalInstance.dismiss();
		});


	}
}]);