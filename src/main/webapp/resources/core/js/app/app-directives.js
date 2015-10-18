bookApp.directive('bookCard', function () {
    return {
        restrict: 'E',
        templateUrl: 'bookCard.html',
        replace: true,
        scope: {
            bookOnExchange: "="
        }
    }
});

bookApp.directive('myEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if (event.which === 13) {
                scope.$apply(function () {
                    scope.$eval(attrs.myEnter);
                });

                event.preventDefault();
            }
        });
    };
});

bookApp.directive('selectedCategoryBadge', function () {
    return {
        restrict: 'E',
        templateUrl: 'selectedCategoryBadge.html',
        replace: true,
        scope: {
            selectedCategory: "@",
            removeCategoryFunction: "&"
        }
    }
});