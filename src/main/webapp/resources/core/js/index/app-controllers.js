bookApp.controller('welcomeController', ['$scope', '$http', function ($scope, $http) {
    $scope.searchIconClicked = false;
    $scope.searchFor = '';

    $scope.bookPages = [];
    $scope.booksDisplayed = [];
    $scope.bookToSearchFor = '';
    var allBooks = [];

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

        $('option[value=' + category + ']', $('#categories-select')).prop('selected', false);

        $('#categories-select').multiselect('refresh');
    };

    $scope.filterByCategoryFilter = function (category) {
        addCategoryFilter(category);
        $scope.$apply();
    }

    $scope.navCategoryFilter = function (category) {
        $scope.addCategoryFilter(category);
        $('option[value=' + category + ']', $('#categories-select')).prop('selected', true);

        $('#categories-select').multiselect('refresh');
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

    $http.get('getAllBooks').
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


    $http.get('getAllCategories').
    success(function (data, status, headers, config) {
        constructCategoriesList(data);
    }).
    error(function (data, status, headers, config) {
        alert("Error");
    });

    function constructCategoriesList(data) {
        $('#categories-select,#categories-wanted-select').multiselect({
            buttonText: function (options, select) {
                return 'Pick Categories';
            },
            buttonTitle: function (options, select) {
                var labels = [];
                options.each(function () {
                    labels.push($(this).text());
                });
                return labels.join(' - ');
            },
            onChange: function (option, checked, select) {
                $scope.filterByCategoryFilter($(option).val());
            }
        });

        var options = [];
        $.each(data, function (index, value) {
            $scope.allCategories.push(value);

            var option = {};
            option.label = value.categoryName;
            option.title = value.categoryName;
            option.value = value.categoryName;
            option.selected = false;

            options.push(option);
        });

        $.each($scope.allCategories, function (index, value) {
            $scope.categoriesToDisplay.push(value.categoryName);
            allCategoriesStrings.push(value.categoryName);
        });

        $scope.categoriesToDisplayInNav = $scope.allCategories.slice(0, 10);
        $('#categories-select,#categories-wanted-select').multiselect('dataprovider', options);
    }

    $scope.shouldDipslayCategory = function (category) {
        if (jQuery.inArray(category, $scope.categoriesToDisplay) > -1) {
            return true;
        }
        return false;
    }

    $scope.changeFilterPanelIcon = function (filterName, value) {
        switch (filterName) {
        case 'category':
            {
                if (value) {
                    $scope.isCategoriesFilterExpanded = true;
                    $scope.isSearchFilterExpanded = false;
                    $scope.isCategoriesWantedFilterExpanded = false;
                } else {
                    $scope.isCategoriesFilterExpanded = false;
                }
            }
            break;

        case 'search':
            {
                if (value) {
                    $scope.isSearchFilterExpanded = true;
                    $scope.isCategoriesFilterExpanded = false;
                    $scope.isCategoriesWantedFilterExpanded = false;
                } else {
                    $scope.isSearchFilterExpanded = false;
                }
            }
            break;
        case 'category-wanted':
            {
                if (value) {
                    $scope.isCategoriesWantedFilterExpanded = true;
                    $scope.isCategoriesFilterExpanded = false;
                    $scope.isSearchFilterExpanded = false;
                } else {
                    $scope.isCategoriesWantedFilterExpanded = false;
                }
            }
            break;
        }
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
}]);