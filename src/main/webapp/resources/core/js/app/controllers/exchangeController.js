bookApp.controller('exchangeController', ['$scope', '$location', 'dataService','bookService', 'exchangeService', '$http', '$uibModal', 'ngToast', '$document', '$window','searchService', '$interval','$rootScope', function ($scope, $location, dataService, bookService, exchangeService, $http, $uibModal, ngToast, $document, $window, searchService, $interval,$rootScope) {
	dataService.setEmail(email);
    $rootScope.shouldSlideMenuIn = false;

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

    $scope.rating= 4;
    $scope.isMobile = $rootScope.isUserMobile;

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

    $scope.hoveredOverBook = function(bookOnExchange){
        bookOnExchange.hoveredOver = true;
    }


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

     $scope.mouseEntered = function(bookHoveredOver){
            bookHoveredOver.mouseEntered = true;
             bookHoveredOver.mouseLeft = false;
        }

         $scope.mouseLeft = function(bookHoveredOutOf){
                bookHoveredOutOf.mouseEntered = false;
                 bookHoveredOutOf.mouseLeft = true;
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

        function fetchBooksForCurrentSearchCriteria(){
            bookService.getBooksForCriteria($scope.searchFor, $scope.categoryFilter).then(function (result) {

            				sortBooksInPages(result);
            				$scope.searching = false;
            			}, function (error) {

            				$scope.searching = false;
            				console.log(error);
            			});
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

	$scope.$watch('searchFor', function (newValue, oldValue) {
		if (newValue.length >= 1) {
			initiateSearch(newValue);
		}
		if (newValue < oldValue && newValue.length < 3) {
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

	angular.element($document).bind("scroll", function () {
		$scope.$apply(function () {
			if (!$scope.searching) {
				loadMoreResults();
			}
		});
	});

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
}]);