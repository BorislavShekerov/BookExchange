bookApp.controller('welcomeController', ['$scope', 'dummyData', '$http', function ($scope, dummyData, $http) {
    $scope.searchIconClicked = false;
    $scope.searchFor = '';
    $scope.bookPages = [];
    var allBooks = [];
    $scope.booksDisplayed = [];
    $scope.bookToSearchFor = '';
    $scope.selectedCategories = [];
    $scope.categoriesToDisplay = [];
    $scope.allCategories = [];
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
            $scope.categoriesToDisplay = $scope.allCategories;
        } else {
            $scope.categoriesToDisplay.splice($.inArray(category, $scope.categoriesToDisplay), 1);
        }

        $('option[value=' + category + ']', $('#categories-select')).prop('selected', false);

        $('#categories-select').multiselect('refresh');
    };

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

            $scope.$apply();
        }
    }

    $http.get('/getAllBooks').
    success(function (data, status, headers, config) {
        allBooks = data;
        splitDataIntoPagesForPagination(data);
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
                bookData[i].categoriesInterestedInList = bookData[i].categoriesInterestedIn.split(",");
                pageData.push(bookData[i]);
                $scope.bookPages.push(pageData);
                pageData = [];
            } else {
                bookData[i].categoriesInterestedInList = bookData[i].categoriesInterestedIn.split(",");
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
                $scope.addCategoryFilter($(option).val());
            }
        });

        var options = [];
        $.each(data, function (index, value) {
            $scope.allCategories.push(value.categoryName);

            var option = {};
            option.label = value.categoryName;
            option.title = value.categoryName;
            option.value = value.categoryName;
            option.selected = false;

            options.push(option);
        });

        $scope.categoriesToDisplay = $scope.allCategories;
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
}]);