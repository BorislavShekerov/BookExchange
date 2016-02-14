bookApp.controller('mainController', ['dataService', '$uibModal', 'ngToast' ,'$document', function (dataService, $uibModal, ngToast, $document) {
	dataService.setEmail(email);


	function init() {
		if (userLoginCount == 1) {
			var promptWindow = $uibModal.open({
				animation: true,
				templateUrl: '/app/category/openAddPreferredCategoryModal',
				controller: 'AddPreferredCategoriesModalController'
			});

				promptWindow.result.then(function (preferredCategoriesList) {

                                						ngToast.create({
                                							className: 'success',
                                							content: '<a href="#" class="">Categories added successfully!</a>',
                                							timeout: 2000,
                                							animation: 'slide'
                                						});


                                			                    			}, function () {
                                				$log.info('Modal dismissed at: ' + new Date());
                                			});
		}
	}

	init();




}]);

