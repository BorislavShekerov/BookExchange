bookApp.controller('FirstTimeLoginModalController', ['$scope', 'dataService', '$http', '$uibModalInstance', function ($scope, dataService, $http, $uibModalInstance) {
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
		$('#categories-wanted-select').multiselect({
			buttonText: function (options, select) {
				return 'Pick Categories Interested In';
			},
			buttonTitle: function (options, select) {
				var labels = [];
				options.each(function () {
					labels.push($(this).text());
				});
				return labels.join(' - ');
			},
			onChange: function (option, checked, select) {
				$scope.addCategoryInterestedIn($(option).val());
			}
		});

		var options = [];
		$.each(data, function (index, value) {

			var option = {};
			option.label = value.categoryName;
			option.title = value.categoryName;
			option.value = value.categoryName;
			option.selected = false;

			options.push(option);

			var category = {
				categoryName: value.categoryName,
				id: value.id
			};
			allCategories.push(category);
		});

		$('#categories-wanted-select').multiselect('dataprovider', options);
	}

	$scope.addCategoryInterestedIn = function (category) {
		$scope.categoriesInterestedIn.push(category);

		$('option[value=' + category + ']', $('#categories-select')).prop('selected', true);

		$('#categories-wanted-select').multiselect('refresh');
		$scope.$apply();
	}
	$scope.removeCategory = function (category) {
		$scope.categoriesInterestedIn.splice($.inArray(category, $scope.categoriesInterestedIn), 1);

		$('option[value=' + category + ']', $('#categories-wanted-select')).prop('selected', false);

		$('#categories-wanted-select').multiselect('refresh');
	};

	$scope.close = function () {
		$uibModalInstance.dismiss('cancel');
	};

	$scope.addPreferedGenres = function () {
		var preferredCategories = [];
		$.each($scope.categoriesInterestedIn, function (index, categoryInterestedIn) {
			$.each(allCategories, function (index, value) {
				if (categoryInterestedIn == value.categoryName) {
					preferredCategories.push(value);
				}
			});

		});
		$uibModalInstance.close(preferredCategories);
	}
}]);