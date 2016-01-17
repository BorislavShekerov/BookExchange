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

    var invalidInputCount = 0;
    var previouslySelectedAvatarImg;
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

    var addCategoriesInterestedInToUserData = function () {
          $.each($scope.categoriesInterestedIn, function (index, value) {
                var category = { categoryName : value};
                $scope.user.categoriesInterestedIn.push(category);
          });
    }

    var sendSignUpRequest = function () {
        $http.post('/registerUser', $scope.user).then(function(response){
            console.log(response.status);
        }, function(response){
            console.log(response.status);
        });
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
             addCategoriesInterestedInToUserData();

             sendSignUpRequest();
        }
    };


    $http.get('/getAllCategories').
    success(function (data, status, headers, config) {
        constructCategoriesList(data);
    }).
    error(function (data, status, headers, config) {
        alert("Error");
    });

    function constructCategoriesList(data) {
        $('#categories-wanted-select').multiselect({
            buttonText: function (options, select) {
                return 'Pick Categories Interested In';
            },
            buttonTitle: function (options, select) {
                var labels = [];
                options.each(function () {
                    labels.push($(this).text());
                });
                return labels.join(' - ');
            },
            onChange: function (option, checked, select) {
                $scope.addCategoryInterestedIn($(option).val());
            }
        });

        var options = [];
        $.each(data, function (index, value) {

            var option = {};
            option.label = value.categoryName;
            option.title = value.categoryName;
            option.value = value.categoryName;
            option.selected = false;

            options.push(option);
        });

        $('#categories-wanted-select').multiselect('dataprovider', options);
    }

      $scope.addCategoryInterestedIn = function (category) {
            $scope.categoriesInterestedIn.push(category);

            $('option[value=' + category + ']', $('#categories-select')).prop('selected', true);

            $('#categories-wanted-select').multiselect('refresh');
            $scope.$apply();
        }
    $scope.removeCategory = function (category) {
        $scope.categoriesInterestedIn.splice($.inArray(category, $scope.categoriesInterestedIn), 1);

        $('option[value=' + category + ']', $('#categories-wanted-select')).prop('selected', false);

        $('#categories-wanted-select').multiselect('refresh');
    };

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

