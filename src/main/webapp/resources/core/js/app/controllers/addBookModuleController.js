bookApp.controller('addBookModuleController', ['$scope', '$http', 'dataService', function ($scope, $http, dataService) {


		$scope.bookTitle = "";
		$scope.bookAuthor = "";
		$scope.bookCategory = "";
		$scope.imgUrl = "";

		$scope.showBookCoverImage = false;

		var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		var csrfToken = $("meta[name='_csrf']").attr("content");

		$scope.addBook = function () {
			var userDetails = dataService.getUserData();
			var categories = dataService.getCategories();

			var bookToAdd = {};
			bookToAdd.title = $scope.bookTitle;
			bookToAdd.author = $scope.bookAuthor;
			bookToAdd.category = $scope.bookCategory;
			bookToAdd.imgUrl = $scope.imgUrl;

			$.each(categories, function (index, value) {
				if (value.categoryName == bookToAdd.category) {
					bookToAdd.category = value;
				}
			});

			var userData = {};
			userData.username = userDetails.username;
			userData.bookToAddToExchange = bookToAdd;

			var req = {
				method: 'POST',
				url: '/app/addBook',
				headers: {
					'X-CSRF-TOKEN': csrfToken
				},
				data: userData
			}

			$http(req).then(function () {
				alert("success");
			}, function () {
				alert("error");
			});


		}
		$scope.textChanged = function () {
			$scope.showBookCoverImage = true;
		}

}
]);