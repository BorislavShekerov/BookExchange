bookApp.controller('signUpController', ['$scope', '$http', function ($scope, $http) {
    $scope.user = {
        firstName : "",
        lastName : "",
        email : "",
        password : "",
        avatarUrl:"",
        categoriesInterestedIn:[]
    }

    $scope.avatarImages = ['resources/core/img/avatar5.jpg',
                            'resources/core/img/avatar1.jpg',
                            'resources/core/img/avatar2.jpg',
                            'resources/core/img/avatar3.jpg',
                            'resources/core/img/avatar4.jpg',
                            'resources/core/img/avatar6.jpg',
                            'resources/core/img/avatar7.jpg'];
    $scope.passwordRepeat = "";
    $scope.emailRepeat = "";
    $scope.categoriesInterestedIn = [];
    $scope.emailAlreadyInUse = false;
     var invalidInputCount = 0;
    var previouslySelectedAvatarImg;
    var allCategories = [];
    $scope.submitted = false;

    $scope.emailsDifferent = false;
    $scope.passwordsDifferent = false;
    $scope.conditionsAgreement = false;
    $scope.userAvatarSet = false;

    var checkPasswords = function() {
       if($scope.passwordRepeat != $scope.user.password){
           $scope.passwordsDifferent = true;
           invalidInputCount ++;
       }
    }

    var checkEmails = function() {
       if($scope.emailRepeat != $scope.user.email){
           $scope.emailsDifferent = true;
           invalidInputCount ++;
       }
    }

    var checkConditions = function() {
       if(!$scope.conditionsAgreement){
           invalidInputCount ++;
       }
    }

    $scope.submitForm = function (isFormValid){
        $scope.submitted = true;
        invalidInputCount = 0;
        $scope.emailsDifferent = false;
        $scope.passwordsDifferent = false;

        checkPasswords();
        checkEmails();
        checkConditions();


        if(invalidInputCount == 0 && isFormValid && $scope.userAvatarSet &&  $scope.categoriesInterestedIn.length>0){
            $('#avatarUrl').val($scope.user.avatarUrl);

            $('#userRegisterForm').submit();
        }
    };


    $http.get('/getAllCategories').
    success(function (data, status, headers, config) {
        constructCategoriesList(data);
    }).
    error(function (data, status, headers, config) {
        alert("Error");
    });

    var updateUserAvatar = function(newVal){
        $scope.userAvatarSet = newVal;
    }
    $scope.setAvatarPicture = function(){
        $scope.user.avatarUrl = $('#avatarUploadForm input').val();
         $("#uploadAvatarPicker img").attr("src",$scope.user.avatarUrl);
         updateUserAvatar(true);
         $('#myUploadAvatarModal').modal('hide');
    }

    $scope.selectDefaultAvatarImg = function(avatarImgSelected){
        console.log(avatarImgSelected);
        if ( previouslySelectedAvatarImg && avatarImgSelected != previouslySelectedAvatarImg){
             $("img[src='"+previouslySelectedAvatarImg+"'").removeClass('imgSelected');
            }

           $("img[src='"+avatarImgSelected+"'").addClass('imgSelected');
            previouslySelectedAvatarImg = avatarImgSelected;
            $scope.user.avatarUrl = avatarImgSelected;
             updateUserAvatar(true);

    }


    }]);

