bookApp.service("notificationsService",['$http', function ($http) {
    function getNewNotificationsForUser() {
    		var getPromise = $http.get('/app/notifications/newNotificationsCheck').
    		then(function (response) {
    			return response.data;
    		}, function (response) {
    			console.log(response)
    		});

    		return getPromise;
    	}

    function setNotificationSeen(notification){
      var csrfToken = $("meta[name='_csrf']").attr("content");
        var notificationSeen = { id: notification.id
        };
            var req = {
            				method: 'POST',
            				url: '/app/notifications/marAsSeen',
            				headers: {
            					'X-CSRF-TOKEN': csrfToken
            				},
            				data: notificationSeen
            			}

            	var postPromise	= $http(req).then(function (response) {
            				return response.data;
            			}, function (response) {
            				return response.status;
            			});

            			return postPromise;
    }


	return {
		getNewNotificationsForUser: getNewNotificationsForUser,
		setNotificationSeen: setNotificationSeen
	};

}]);