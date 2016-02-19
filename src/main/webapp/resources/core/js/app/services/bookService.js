bookApp.service("bookService", ['$http', function ($http) {

     function removeBookForUser(bookTitle){
        var bookToRemove = {title:bookTitle};
             var csrfToken = $("meta[name='_csrf']").attr("content");

                    var req = {
                    				method: 'POST',
                    				url: '/app/book/remove',
                    				headers: {
                    					'X-CSRF-TOKEN': csrfToken
                    				},
                    				data: bookToRemove
                    			}

                    	var postPromise	= $http(req).then(function (response) {
                    				return response.data;
                    			}, function (response) {
                    				return response.status;
                    			});
                    	return postPromise;


     }

      function addBook(bookToAdd){

                  var csrfToken = $("meta[name='_csrf']").attr("content");

                         var req = {
                         				method: 'POST',
                         				url: '/app/book/add',
                         				headers: {
                         					'X-CSRF-TOKEN': csrfToken
                         				},
                         				data: bookToAdd
                         			}

                         	var postPromise	= $http(req).then(function (response) {
                         				return response.data;
                         			}, function (response) {
                         				return response.status;
                         			});
                         	return postPromise;


          }

      function getAllBooks(){
             var promise = $http.get('/getAllBooks').
                    	success(function (response, status, headers, config) {
                           return response.data;
                    	}).
                    	error(function (data, status, headers, config) {
                    		alert("Error");
                    	});

              return promise;
        }

         function getBooksForCriteria(title,categoriesToFilter){
         var csrfToken = $("meta[name='_csrf']").attr("content");

           var searchCriteria = {
            bookTitle:title,
            categoriesFiltered : categoriesToFilter
           };
                                  var req = {
                                  				method: 'POST',
                                  				url: '/searchForBooks',
                                  				headers: {
                                  					'X-CSRF-TOKEN': csrfToken
                                  				},
                                  				data: searchCriteria
                                  			}

                     var promise = $http(req).then(function (response) {
                                                            				return response.data;
                                                            			}, function (response) {
                                                            				return response.status;
                                                            			});
                      return promise;
                }



    return {
    		getAllBooks : getAllBooks,
    		removeBookForUser : removeBookForUser,
    		addBook : addBook,
    		getBooksForCriteria: getBooksForCriteria
    	};

}]);