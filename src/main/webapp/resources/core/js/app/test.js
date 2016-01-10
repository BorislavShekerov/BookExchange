bookApp.controller('mainController', ['$scope', '$location', 'dataService', 'exchangeService', '$http', function ($scope, $location, dataService, exchangeService, $http) {
    dataService.setUsername(username);
    $scope.searchIconClicked = false;
    $scope.searchFor = '';

    $scope.bookPages = [];
    $scope.booksDisplayed = [];
    $scope.bookToSearchFor = '';
    var allBooks = [];
    var categoryIdToNameMap = {};

    $scope.selectedCategories = [];
    $scope.categoriesToDisplay = [];
    $scope.allCategories = [];
    $scope.categoriesToDisplayInNav = [];
    var categoriesSetShownInNav = 1;
    var allCategoriesStrings = [];

    $scope.showLoadingIndicator = true;
    $scope.isCategoriesFilterExpanded = false;
    $scope.isSearchFilterExpanded = true;
    $scope.isCategoriesWantedFilterExpanded = false;

    $scope.changePageNumber = function (pageNum) {
        $scope.booksDisplayed = $scope.bookPages[pageNum - 1];
        $scope.$apply();
    };

    $scope.removeCategory = function (category) {
        $scope.selectedCategories.splice($.inArray(category, $scope.selectedCategories), 1);
        if ($scope.categoriesToDisplay.length == 1) {
            $scope.categoriesToDisplay = allCategoriesStrings;
        } else {
            $scope.categoriesToDisplay.splice($.inArray(category, $scope.categoriesToDisplay), 1);
        }
    };

    $scope.filterByCategoryFilter = function (category) {
        addCategoryFilter(category);
        $scope.$apply();
    }

    $scope.navCategoryFilter = function (category) {
        $scope.addCategoryFilter(category);
    }
    $scope.addCategoryFilter = function (category) {
        if (jQuery.inArray(category, $scope.selectedCategories) > -1) { //Removing catgory from filter
            $scope.selectedCategories.splice($.inArray(category, $scope.selectedCategories), 1);
            if ($scope.categoriesToDisplay.length == 1) {
                $scope.categoriesToDisplay = $scope.allCategories;

                reconfigurePagination();
            } else {
                $scope.categoriesToDisplay.splice($.inArray(category, $scope.categoriesToDisplay), 1);

                reconfigurePagination();
            }
            $scope.$apply();
        } else { //Adding a new category to filter
            if ($scope.allCategories.length == $scope.categoriesToDisplay.length) { //First time choosing category
                $scope.categoriesToDisplay = [];
                $scope.categoriesToDisplay.push(category);
                $scope.selectedCategories.push(category);

                reconfigurePagination();
            } else {
                $scope.categoriesToDisplay.push(category);
                $scope.selectedCategories.push(category);

                reconfigurePagination();
            }

        }
    }

    $http.get('/getAllBooks').
    success(function (data, status, headers, config) {
        allBooks = data;
        splitDataIntoPagesForPagination(data);
        $scope.showLoadingIndicator = false;
    }).
    error(function (data, status, headers, config) {
        alert("Error");
    });

    function reconfigurePagination() {
        var booksForCategories = [];
        $.each(allBooks, function (index, value) {
            if (jQuery.inArray(value.category.categoryName, $scope.categoriesToDisplay) > -1) {
                booksForCategories.push(value);
            }
        });

        splitDataIntoPagesForPagination(booksForCategories);
    }

    function splitDataIntoPagesForPagination(bookData) {
        var pageData = [];
        $scope.bookPages = [];
        for (i = 0; i < bookData.length; i++) {
            bookData[i].ownerAvatar = "../" + bookData[i].ownerAvatar;
            if (i > 0 && i % 29 == 0) {
                pageData.push(bookData[i]);
                $scope.bookPages.push(pageData);
                pageData = [];
            } else {
                pageData.push(bookData[i]);
            }
        }
        if (pageData.length > 0) {
            $scope.bookPages.push(pageData);
            $scope.booksDisplayed = pageData;
        }
        if ($scope.bookPages.length > 0) {
            $scope.booksDisplayed = $scope.bookPages[0];
        }

        addPagination();
    }

    function addPagination() {
        $("#page-pagination").bootpag({
            total: $scope.bookPages.length
        }).on("page", function (event, num) {
            $scope.changePageNumber(num);
        });
    }


    $http.get('/getAllCategories').
    success(function (data, status, headers, config) {
        dataService.setCategories(data);
        constructCategoriesList(data);
    }).
    error(function (data, status, headers, config) {
        alert("Error");
    });

    function constructCategoriesList(data) {
        $.each(data, function (index, value) {
            $scope.allCategories.push(value);
            $scope.categoriesToDisplay.push(value.categoryName);
            allCategoriesStrings.push(value.categoryName);

        });

        $scope.categoriesToDisplayInNav = $scope.allCategories.slice(0, 10);
    }

    $scope.shouldDipslayCategory = function (category) {
        if (jQuery.inArray(category, $scope.categoriesToDisplay) > -1) {
            return true;
        }
        return false;
    }

    $scope.shouldShowLoadingIndicator = function () {
        return $scope.showLoadingIndicator;
    }

    $scope.showNextCategories = function () {
        $('#showPrevCategoriesButton').prop('disabled', false);
        var spliceStart = categoriesSetShownInNav * 10;
        var spliceEnd = spliceStart + 10;
        if (spliceEnd > $scope.allCategories.length) {
            $scope.categoriesToDisplayInNav = $scope.allCategories.slice(spliceStart, $scope.allCategories.length);
        } else {
            $scope.categoriesToDisplayInNav = $scope.allCategories.slice(spliceStart, spliceEnd);
        }
        categoriesSetShownInNav++;
        if (categoriesSetShownInNav == 3) {
            $scope.nextCategoriesButtonDispl = true;
            $('#showNextCategoriesButton').prop('disabled', true);
        }
    }

    $scope.showPrevCategories = function () {
        $('#showNextCategoriesButton').prop('disabled', false);
        var spliceEnd = categoriesSetShownInNav * 10 - 10;
        var spliceStart = spliceEnd - 10;

        $scope.categoriesToDisplayInNav = $scope.allCategories.slice(spliceStart, spliceEnd);

        categoriesSetShownInNav--;
        if (categoriesSetShownInNav == 1) {
            $scope.nextCategoriesButtonDispl = true;
            $('#showPrevCategoriesButton').prop('disabled', true);
        }

    }

    $scope.initiateExchange = function (title, ownedBy) {
        $.each($scope.booksDisplayed, function (index, value) {
            if (value.title == title && value.ownedBy == ownedBy) {
                exchangeService.setBookToExchangeFor(value);
                $location.path('offerExchange');
            }
        });
    }
}]);



bookApp.controller('offersController', ['$scope', 'dataService', function ($scope, dataService) {
        var userData = dataService.getUserData();
        $scope.userCurrentOffersMade = [];
        $scope.userCurrentOffersReceived = [];


        $.each(userData.currentExchanges, function (index, value) {
            if (value.bookOfferedInExchange.ownedBy == userData.username) {
                value.bookOfferedInExchange.ownerAvatar = "../" + value.bookOfferedInExchange.ownerAvatar;
                value.bookPostedOnExchange.ownerAvatar = "../" + value.bookPostedOnExchange.ownerAvatar;

                $scope.userCurrentOffersMade.push(value);
            } else if (value.bookPostedOnExchange.ownedBy == userData.username) {

                value.bookOfferedInExchange.ownerAvatar = "../" + value.bookOfferedInExchange.ownerAvatar;
                value.bookPostedOnExchange.ownerAvatar = "../" + value.bookPostedOnExchange.ownerAvatar;

                $scope.userCurrentOffersReceived.push(value);
            }
        });
}
]);

bookApp.controller('exchangeHistoryController', ['$scope', 'dataService', function ($scope, dataService) {
        var userData = dataService.getUserData();
        $scope.userExchangeHistory = userData.exchangeHistory;


        $.each($scope.userExchangeHistory, function (index, value) {
            if (value.bookOfferedInExchange.ownedBy == userData.username) {

                value.bookOfferedInExchange.ownerAvatar = "../" + value.bookOfferedInExchange.ownerAvatar;
                value.bookPostedOnExchange.ownerAvatar = "../" + value.bookPostedOnExchange.ownerAvatar;

                value.offered = true;
            } else if (value.bookPostedOnExchange.ownedBy == userData.username) {

                value.bookOfferedInExchange.ownerAvatar = "../" + value.bookOfferedInExchange.ownerAvatar;
                value.bookPostedOnExchange.ownerAvatar = "../" + value.bookPostedOnExchange.ownerAvatar;

                value.offered = false;
            }
        });
}
]);


bookApp.controller('moduleController', ['$scope', '$http', 'dataService', function ($scope, $http, dataService) {


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

bookApp.controller('exchangeController', ['$scope', '$http', '$location', 'dataService', 'exchangeService', function ($scope, $http, $location, dataService, exchangeService) {
    $scope.userDetails = dataService.getUserData();
    $scope.bookToExchangeFor = exchangeService.getBookToExchangeFor();
    $scope.selectedBook = "Select a book";

    var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");

    $scope.userHasBooksOfInterest = function () {
        $.each($scope.userDetails.booksPostedOnExchange, function (index, value) {
            if ($scope.bookToExchangeFor.ownerCategoriesOfInterest.indexOf(value.category.categoryName) > -1) {
                return true;
            }
        });

        return false;
    }

    $scope.exploreOtherOptions = function () {

    }
    $scope.initiateOffer = function () {

        var exchangeOrder = {};
        exchangeOrder.bookUnderOffer = $scope.bookToExchangeFor.title;
        exchangeOrder.bookUnderOfferOwner = $scope.bookToExchangeFor.ownedBy;
        exchangeOrder.bookOfferedInExchange = $scope.selectedBook;
        exchangeOrder.bookOfferedInExchangeOwner = $scope.userDetails.username;


        var req = {
            method: 'POST',
            url: '/app/exchangeOrder',
            headers: {
                'X-CSRF-TOKEN': csrfToken
            },
            data: exchangeOrder
        }

        $http(req).then(function () {
            $location.path('/');
        }, function () {
            alert("error");
        });
    }
}]);