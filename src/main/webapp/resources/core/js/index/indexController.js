bookApp.controller('indexController', ['$scope', 'categoryService', '$document', '$interval', 'bookService', '$window', '$anchorScroll', '$location', '$uibModal','deviceDetector','$http', function ($scope, categoryService, $document, $interval, bookService, $window, $anchorScroll, $location, $uibModal,deviceDetector, $http) {
	$scope.showBouncingScrollArrow = true;
	$scope.searchFor = '';
	$scope.filterExpanded = false;
	$scope.isCategoryFilterOpen = true;
	$scope.allCategories = [];
	$scope.categoryFilter = [];

	$scope.allBooks = [];
	$scope.booksSplitInBatches = [];
	$scope.booksDisplayed = [];

	$scope.searching = true;
	$scope.loadingMoreResults = false;

	$scope.shouldFixCategoriesFilter = false;
	$scope.hideVerticalScroll = true;
	$scope.filterReachedBottom = false;

	$scope.currentBatch = 1;
	$scope.isUserMobile = deviceDetector.isMobile();

    $scope.connectToFacebook = function(){
         var csrfToken = $("meta[name='_csrf']").attr("content");

                            var req = {
                            				method: 'POST',
                            				url: '/connect/facebook/',
                            				headers: {
                            					'X-CSRF-TOKEN': csrfToken
                            				}
                            			}

                            	 $http(req).then(function (response) {
                            				return response.data;
                            			}, function (response) {
                            				return response.status;
                            			});
    }

	angular.element($document).bind("scroll", function () {
		loadMoreResultsIfNeeded();
		var footer = document.getElementsByTagName('footer')[0].offsetTop;

		if (($document[0].body.scrollTop + $window.innerHeight) < footer) {
			$scope.filterReachedBottom = false;
		} else {
			$scope.filterReachedBottom = true;
		}

		if (($document[0].body.scrollTop - 20) > $window.innerHeight) {
			$scope.showBouncingScrollArrow = false;
			$scope.shouldFixCategoriesFilter = true;
		} else {
			$scope.shouldFixCategoriesFilter = false;
		}

	});

	function init() {
		var allBooksPromise = bookService.getAllBooks();
		allBooksPromise.then(function (response) {
			sortBooksInPages(response.data);
			$scope.searching = false;
		}, function (error) {
			console.log(error);
		});
	}

	init();

	function sortBooksInPages(books) {
		var currentPage = [];
		$scope.booksSplitInBatches = [];

		angular.forEach(books, function (book, index) {
			if (index > 0 && index % 40 == 0) {
				$scope.booksSplitInBatches.push(currentPage);
				currentPage = [];
				currentPage.push(book);
			} else {
				currentPage.push(book);
			}
		});
		$scope.booksSplitInBatches.push(currentPage);
		$scope.booksDisplayed = $scope.booksSplitInBatches[0];

		if ($scope.allBooks.length == 0) {
			$scope.allBooks = $scope.booksSplitInBatches.slice();
		}

	}

	categoryService.getAllCategories().then(function (response) {
		$scope.allCategories = response.data;
	}, function (err) {
		console.log("err");
	});

	$scope.initiateExchange = function (title, ownedBy) {
		var promptWindow = $uibModal.open({
			animation: true,
			templateUrl: '/signInModal',
			controller: 'SignInModalController'
		});
	}

	function openExchangeModal() {

	}

	$scope.mouseEntered = function (bookHoveredOver) {
		bookHoveredOver.mouseEntered = true;
		bookHoveredOver.mouseLeft = false;
	}

	$scope.mouseLeft = function (bookHoveredOutOf) {
		bookHoveredOutOf.mouseEntered = false;
		bookHoveredOutOf.mouseLeft = true;
	}

    $scope.noBooksToDisplay = function(){
        return  !($scope.loadingMoreResults || $scope.searching) && $scope.booksDisplayed.length == 0;
    }

    function fetchBooksForCurrentSearchCriteria(){
        bookService.getBooksForCriteria($scope.searchFor, $scope.categoryFilter).then(function (result) {

        				sortBooksInPages(result);
        				$scope.searching = false;
        			}, function (error) {

        				$scope.searching = false;
        				console.log(error);
        			});
    }
	$scope.adjustCategoryFilter = function (category) {
		$scope.searching = true;

		if (wasCategoryRemoved(category.categoryName)) {
			category.selected = false;
			removeCategoryFromFilter(category.categoryName);
			if ($scope.categoryFilter.length == 0) {
				if ($scope.searchFor == "") {
					$scope.searching = false;
					$scope.booksSplitInBatches = $scope.allBooks;
					$scope.booksDisplayed = $scope.allBooks[0];
				} else {
					 fetchBooksForCurrentSearchCriteria();
				}

			} else {

				 fetchBooksForCurrentSearchCriteria();
			}
		} else {
			category.selected = true;
			$scope.categoryFilter.push(category.categoryName);

            fetchBooksForCurrentSearchCriteria();
		}
	}

	function wasCategoryRemoved(categoryName) {
		if ($scope.categoryFilter.indexOf(categoryName) > -1) {
			return true;
		}
		return false;
	}

	function removeCategoryFromFilter(categoryName) {
		$scope.categoryFilter.splice($.inArray(categoryName, $scope.categoryFilter), 1);
	}

	function addBooksFromCategoryToPagination(categoryName) {
		if ($scope.categoryFilter.length == 0) {
			$scope.booksSplitInBatches = [];
		}
		angular.forEach($scope.allBooks, function (booksArray, index) {
			angular.forEach(booksArray, function (book, index) {
				if (book.category == categoryName) {
					addBookToPagination(book);
				}
			});
		});
		if ($scope.booksSplitInBatches[0]) {
			$scope.booksDisplayed = $scope.booksSplitInBatches[0];
		} else {
			$scope.booksDisplayed = [];
		}

	}

	function addBookToPagination(bookToAdd) {
		if ($scope.booksSplitInBatches.length == 0) {
			var newLastPage = [bookToAdd];
			$scope.booksSplitInBatches.push(newLastPage);
		} else {
			var lastPage = $scope.booksSplitInBatches[$scope.booksSplitInBatches.length - 1];
			if (lastPage.length > 0 && lastPage.length % 40 == 0) {
				var newLastPage = [bookToAdd];
				$scope.booksSplitInBatches.push(newLastPage);
			} else {
				lastPage.push(bookToAdd);
			}
		}

	}

	$scope.$watch('searchFor', function (newValue, oldValue) {
		if (newValue.length >= 1) {
			initiateSearch(newValue);
		}
		if (newValue < oldValue && newValue == 0) {
			removeSearchFilter();
		}
	});

	function initiateSearch(newValue) {
		$scope.searching = true;

		bookService.getBooksForCriteria(newValue, $scope.categoryFilter).then(function (result) {
			sortBooksInPages(result);
			$scope.searching = false;
		}, function (error) {
			$scope.searching = false;
			console.log(error);
		});


	}

	function removeSearchFilter() {
		if ($scope.categoryFilter.length == 0) {
			$scope.booksSplitInBatches = $scope.allBooks;
			$scope.booksDisplayed = $scope.booksSplitInBatches[0];
		} else {
			getAllBooksForFiltersNoSearch();
		}
	}

	function getAllBooksForFiltersNoSearch() {
		bookService.getBooksForCriteria($scope.searchFor, $scope.categoryFilter).then(function (result) {
			sortBooksInPages(result);
			$scope.searching = false;
		}, function (error) {
			$scope.searching = false;
			console.log(error);
		});
	}

	function loadNextBatch() {
		setTimeout(function () {
			$scope.booksDisplayed = $scope.booksDisplayed.concat($scope.booksSplitInBatches[$scope.currentBatch - 1]);
			$scope.loadingMoreResults = false;
			$scope.$apply();
		}, 1500);
	}

	function loadMoreResultsIfNeeded() {
		angular.element($document).bind("scroll", function () {
			$scope.$apply(function () {
				if (!$scope.searching) {
					loadMoreResults();
				}
			});
		});
	}


	function loadMoreResults() {

		var footer = document.getElementsByTagName('footer')[0].getBoundingClientRect();
		if (footer.top - $window.innerHeight < $window.innerHeight / 3 && moreResultsToLoad() && !$scope.searching) {

			$scope.loadingMoreResults = true;
			$scope.currentBatch++;
			loadNextBatch();
		}
	}

	function moreResultsToLoad() {
		var currentPageEntries = $scope.booksSplitInBatches[$scope.currentBatch];
		if (currentPageEntries && currentPageEntries.length > 0) {
			return true;
		}
		return false;
	}

	$scope.scrollToSearch = function () {
		//Scroll to the exact position
		var someElement = angular.element(document.getElementById('custom-search-input'));
		$scope.hideVerticalScroll = false;
		$scope.showBouncingScrollArrow = false;
		$document.scrollToElementAnimated(someElement, 60).then(function () {
			console && console.log('You just scrolled to the top!');
		});
	}

}]);