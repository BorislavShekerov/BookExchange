bookApp.controller('exchangeController', ['$scope', '$location', 'dataService','bookService', 'exchangeService', '$http', '$uibModal', 'ngToast', '$document', '$window','searchService', '$interval', function ($scope, $location, dataService, bookService, exchangeService, $http, $uibModal, ngToast, $document, $window, searchService, $interval) {
	dataService.setEmail(email);

	$scope.searchFor = '';
	$scope.filterExpanded = false;
	$scope.isCategoryFilterOpen = true;
	$scope.allCategories = [];
	$scope.categoryFilter = [];

	$scope.allBooks = [];
	$scope.booksSplitInBatches = [];
	$scope.booksDisplayed = [];

	$scope.loadingResults = true;
    $scope.filteringResults = false;
    $scope.shouldFixCategoriesFilter = false;
    	$scope.currentBatch = 1;

    angular.element($document).bind("scroll", function() {
                if($document[0].body.scrollTop > document.getElementById("custom-search-input").scrollHeight +  document.getElementById("custom-search-input").clientHeight) {
                    searchService.setSearchNavBarVisible(true);
                    searchService.setSearchPhrase($scope.searchFor);
                    $scope.shouldFixCategoriesFilter = true;
                }else{
                searchService.setSearchNavBarVisible(false);
                $scope.shouldFixCategoriesFilter = false;
                }

        	});

    $interval(function(){
                if(searchService.getSearchNavBarVisible()){
                    $scope.searchFor = searchService.getSearchPhrase();
                }
        },100);


	function init() {
		var allBooksPromise = bookService.getAllBooks();
		allBooksPromise.then(function (response) {
			sortBooksInPages(response.data);
			$scope.loadingResults = false;
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

	$http.get('/getAllCategories').
	success(function (data, status, headers, config) {
		dataService.setCategories(data);

		$scope.allCategories = data;
	}).
	error(function (data, status, headers, config) {
		alert("Error");
	});

	$scope.initiateExchange = function (title, ownedBy) {
		angular.forEach($scope.booksDisplayed, function (value, index) {
			if (value.title == title && value.ownerEmail == ownedBy) {
				exchangeService.setBookToExchangeFor(value);
				openExchangeModal();
			}
		});
	}

	function openExchangeModal(){
	    	var promptWindow = $uibModal.open({
        				animation: true,
        				templateUrl: '/offerExchange',
        				controller: 'exchangeOfferController'
        			});
	}

	$scope.adjustCategoryFilter = function (categoryName) {
	$scope.filteringResults = true;

    $scope.mouseEntered = function(bookHoveredOver){
        bookHoveredOver.mouseEntered = true;
         bookHoveredOver.mouseLeft = false;
    }

     $scope.mouseLeft = function(bookHoveredOutOf){
            bookHoveredOutOf.mouseEntered = false;
             bookHoveredOutOf.mouseLeft = true;
        }


	setTimeout(function () {
    			$scope.currentBatch = 1;
                		if (wasCategoryRemoved(categoryName)) {
                			removeCategoryFromFilter(categoryName);
                			if ($scope.categoryFilter.length == 0) {
                				$scope.booksSplitInBatches = $scope.allBooks;
                				$scope.booksDisplayed = $scope.allBooks[0];
                			} else {
                				removeBooksFromCategoryFromView(categoryName);
                			}
                		} else {
                			addBooksFromCategoryToPagination(categoryName);

                			$scope.categoryFilter.push(categoryName);
                		}
                		$scope.filteringResults = false;
                		$scope.$apply();
    		}, 1000);



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

	function removeBooksFromCategoryFromView(categoryName) {
		var areBooksFiltered = false;
		angular.forEach($scope.booksSplitInBatches, function (booksArray, index) {
			var filteredBooksArray = [];
			angular.forEach(booksArray, function (book, index) {
				if (book.category != categoryName) {
					filteredBooksArray.push(book);
				} else {
					areBooksFiltered = true;
				}
			});

			$scope.booksSplitInBatches[index] = filteredBooksArray;
		});

		if (areBooksFiltered) {
			rearrangeBooksInView();
		}
	}

	function rearrangeBooksInView() {
		var allFilteredBooks = [];
		angular.forEach($scope.booksSplitInBatches, function (booksArray, index) {
			angular.forEach(booksArray, function (book, index) {
				allFilteredBooks.push(book);
			});
		});

		sortBooksInPages(allFilteredBooks);
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
        if($scope.booksSplitInBatches[0]){
        $scope.booksDisplayed = $scope.booksSplitInBatches[0];
        } else {
        $scope.booksDisplayed = [] ;
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
		if (newValue.length > 2) {
			initiateSearch(newValue);
		}
		if (newValue < oldValue && newValue.length < 3) {
			removeSearchFilter();
		}
	});

	function initiateSearch(newValue) {
	    $scope.filteringResults = true;

		setTimeout(function () {
    			angular.forEach($scope.booksSplitInBatches, function (booksArray, index) {
                			var filteredBooksArray = [];
                			angular.forEach(booksArray, function (book, index) {
                				if (book.title.toLowerCase().indexOf(newValue.toLowerCase()) > -1 && isBookCategoryInCategoryFilter(book.category)) {
                					filteredBooksArray.push(book);
                				}
                			});

                			$scope.booksSplitInBatches[index] = filteredBooksArray;
                		});

                		rearrangeBooksInView();

                		$scope.filteringResults = false;
                		$scope.$apply();
    		}, 1000);

	}

	function isBookCategoryInCategoryFilter(category) {
		if ($scope.categoryFilter.length == 0) {
			return true;
		} else {
			return $scope.categoryFilter.indexOf(category) > -1;
		}
	}

	function removeSearchFilter() {
		if ($scope.categoryFilter.length == 0) {
			$scope.booksSplitInBatches = $scope.allBooks;
			$scope.booksDisplayed = $scope.booksSplitInBatches[0];
		} else {
			addCategoryFilteredBooksToView();
		}
	}

	function addCategoryFilteredBooksToView() {
		angular.forEach($scope.allBooks, function (booksArray, index) {
			var filteredBooksArray = [];
			angular.forEach(booksArray, function (book, index) {
				if (isBookCategoryInCategoryFilter(book.category)) {
					filteredBooksArray.push(book);
				}
			});

			$scope.booksSplitInBatches[index] = filteredBooksArray;
		});

		rearrangeBooksInView();
	}

	function loadNextBatch() {
		setTimeout(function () {
			$scope.booksDisplayed = $scope.booksDisplayed.concat($scope.booksSplitInBatches[$scope.currentBatch - 1]);
			$scope.loadingResults = false;
			$scope.$apply();
		}, 1500);
	}

	angular.element($document).bind("scroll", function () {
		$scope.$apply(function () {
			if (!$scope.loadingResults) {
				loadMoreResults();
			}
		});
	});

	function loadMoreResults() {

		var footer = document.getElementsByTagName('footer')[0].getBoundingClientRect();
		if (footer.top - $window.innerHeight < $window.innerHeight / 3 && moreResultsToLoad() && !$scope.loadingResults) {

			$scope.loadingResults = true;
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
}]);