bookApp.controller('AddPreferredCategoriesModalController', ['$scope', 'categoryService', '$http', '$uibModalInstance', function ($scope, categoryService, $http, $uibModalInstance) {
	$scope.loading = true;
	var allCategories = [];
	$scope.categoriesInterestedIn = [];

	$http.get('/getAllCategories').
	success(function (data, status, headers, config) {
		$scope.loading = false;
		constructCategoriesList(data);
	}).
	error(function (data, status, headers, config) {
		alert("Error");
	});

	function constructCategoriesList(data) {
	$scope.allCategories = data;

	}

	$scope.addCategoryInterestedIn = function (category) {
		$scope.categoriesInterestedIn.push(category);
	}

	$scope.closeModal = function () {
		$uibModalInstance.dismiss('cancel');
	};

	$scope.addPreferredGenres = function () {
	    $scope.loading =true;
		categoryService.addCategoryInterestedIn($scope.categoriesInterestedIn).then(function(result){
		    $scope.categoriesInterestedIn = result;
		    $uibModalInstance.close($scope.categoriesInterestedIn);
		},function(err){
		   $uibModalInstance.dismiss();
		});


	}
}]);