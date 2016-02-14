bookApp.service("categoryService", ['$http', function ($http) {

    function addCategoryInterestedIn(categoryList){
           var csrfToken = $("meta[name='_csrf']").attr("content");

                                 var req = {
                                 				method: 'POST',
                                 				url: '/app/category/add',
                                 				headers: {
                                 					'X-CSRF-TOKEN': csrfToken
                                 				},
                                 				data: categoryList
                                 			}

                                 	var postPromise	= $http(req).then(function (response) {
                                 				return response.data;
                                 			}, function (response) {
                                 				return response.status;
                                 			});
                                 	return postPromise;
    }

     function removeCategoryInterestedIn(categoryToRemove){
               var csrfToken = $("meta[name='_csrf']").attr("content");

                                     var req = {
                                     				method: 'POST',
                                     				url: '/app/category/remove',
                                     				headers: {
                                     					'X-CSRF-TOKEN': csrfToken
                                     				},
                                     				data: categoryToRemove
                                     			}

                                     	var postPromise	= $http(req).then(function (response) {
                                     				return response.data;
                                     			}, function (response) {
                                     				return response.status;
                                     			});
                                     	return postPromise;
        }


    return {
        		addCategoryInterestedIn : addCategoryInterestedIn,
        		removeCategoryInterestedIn:removeCategoryInterestedIn
        	};


}]);