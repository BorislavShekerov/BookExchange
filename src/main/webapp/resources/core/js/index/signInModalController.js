bookApp.controller('SignInModalController', ['$scope','$uibModalInstance', function ( $scope, $uibModalInstance) {
 	$scope.closeModal = function () {
 		$uibModalInstance.dismiss('cancel');
 	};

}]);