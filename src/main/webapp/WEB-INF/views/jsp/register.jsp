<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<html lang="en">

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
		<meta name="description" content="">
		<meta name="author" content="">
          <sec:csrfMetaTags />
             <meta name="_csrf_parameter" content="${_csrf_parameter}"/>
            <meta name="_csrf" content="${_csrf.token}"/>
            <meta name="_csrf_header" content="${_csrf.headerName}"/>
		<title>Fixed Top Navbar Example for Bootstrap</title>

		<!-- Bootstrap core CSS -->
		<link href="resources/core/css/bootstrap.min.css" rel="stylesheet">
		<link href="resources/core/css/bootstrap-multiselect.css" rel="stylesheet">
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">


		<!-- Custom styles for register page-->
		<link href="resources/core/css/app/sign_up.css" rel="stylesheet">
		<link href="resources/core/css/app/nav_bar.css" rel="stylesheet">
		<link href="resources/core/css/app/footer.css" rel="stylesheet">

	</head>

	<body ng-app="myApp" id="signUpController" ng-controller="signUpController">
		<nav class="navbar navbar-default navbar-fixed-top">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#"><img src="resources/core/img/logo.png"></a>
				</div>
			</div>
		</nav>
		<div class="main container">

			<div class="row">
				<div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
				 <c:url value="/registerUser" var="registerUserUrl"/>
					<form:form id="userRegisterForm" name="userForm" action="/registerUser" modelAttribute="userData"  >
						<h2>Please Sign Up <small>It''s free and always will be.</small></h2>

						<div class="row">
							<div class="col-xs-12 col-sm-6 col-md-6">
								<div class="form-group" ng-class="{ 'has-error' : userForm.first_name.$invalid && !userForm.first_name.$pristine && submitted || (submitted && userForm.first_name.$pristine)}">
									<form:input path="firstName" type="text" name="first_name" id="first_name" class="form-control input-lg" placeholder="First Name" tabindex="1" autocomplete="off" ng-model="user.firstName"/>
									<p ng-show="submitted && (userForm.first_name.$pristine || (userForm.first_name.$invalid && !userForm.first_name.$pristine))" class="help-block">You first name is required.</p>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6 col-md-6">
								<div class="form-group" ng-class="{ 'has-error' : userForm.last_name.$invalid && !userForm.last_name.$pristine && submitted || (submitted && userForm.last_name.$pristine)}">
									<form:input path="lastName" type="text" name="last_name" id="last_name" class="form-control input-lg" placeholder="Last Name" tabindex="2" autocomplete="off" ng-model="user.lastName"/>
									<p ng-show="submitted && (userForm.last_name.$pristine || (userForm.last_name.$invalid && !userForm.last_name.$pristine)) " class="help-block">You last name is required.</p>
								</div>
							</div>
						</div>
						<div class="form-group" ng-class="{ 'has-error' : emailAlreadyInUse || ((userForm.email.$invalid && !userForm.email.$pristine) || emailsDifferent) && submitted || (submitted && userForm.email.$pristine)}">
							<form:input path="email" type="email" name="email" id="email" class="form-control input-lg" placeholder="Email Address" tabindex="4" autocomplete="off" ng-model="user.email" />
							<p ng-show="submitted && (userForm.email.$pristine || (userForm.email.$invalid && !userForm.email.$pristine)) " class="help-block">Enter a valid email.</p>
							<p ng-show="emailAlreadyInUse" class="help-block">Enter a valid email.</p>
						</div>
						<div class="form-group" ng-class="{ 'has-error' : ((userForm.email.$invalid && !userForm.email.$pristine) || emailsDifferent) && submitted || (submitted && userForm.email_confirmation.$pristine)}">
							<input type="email" name="email_confirmation" id="email_confirmation" class="form-control input-lg" placeholder="Confirm Email Address" autocomplete="off" tabindex="5" ng-model="emailRepeat"/>
							<p ng-show="submitted && emailsDifferent" class="help-block">Emails must be the same.</p>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-6 col-md-6">
								<div class="form-group" ng-class="{ 'has-error' : ((userForm.password.$error.minlength && !userForm.email.$pristine)|| passwordsDifferent) && submitted || (submitted && userForm.password.$pristine)}">
									<form:input path="password" type="password" name="password" id="password" class="form-control input-lg" placeholder="Password" tabindex="6" autocomplete="off" ng-minlength="8" ng-model="user.password" />
									<p ng-show="submitted && (userForm.password.$pristine || userForm.password.$error.minlength)" class="help-block">Password must be at least 8 characters long.</p>
									<p ng-show="submitted && passwordsDifferent" class="help-block">Passwords must be the same.</p>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6 col-md-6">
								<div class="form-group" ng-class="{ 'has-error' : ((userForm.email.$invalid && !userForm.email.$pristine)|| passwordsDifferent) && submitted || (submitted && userForm.password_confirmation.$pristine)}">
									<input type="password" name="password_confirmation" id="password_confirmation" class="form-control input-lg" placeholder="Confirm Password" tabindex="7" autocomplete="off" ng-minlength="8" ng-model="passwordRepeat" />
								</div>
							</div>
						</div>
						<div id="categoriesInterestedPicker" class="row">
							<h2> Choose Favourite Genres </h2>
							<div class="col-xs-12 col-md-6">
								<select id="categories-wanted-select" multiple="multiple" class="space-out-top-sm">
								</select>
							</div>
							<div class="col-xs-12 col-md-6">
								<div ng-repeat="category in categoriesInterestedIn " class="row space-out-top-sm space-out-left">
									<span class="tag label label-info">
                            <span>{{category}}</span>
									<a ng-click="removeCategory(category)"><i class="remove glyphicon glyphicon-remove-sign glyphicon-white"></i></a>
									</span>
								</div>
							</div>
							<div class="col-xs-12">
								<p ng-show="submitted && categoriesInterestedIn.length == 0" class="help-block warning-text">You must pick at least one book genre.</p>
							</div>
						</div>
						<div id="avatarSelection" class="row">
							<div class="row">
								<h2> Avatar Selection </h2>

								<h4 class="col-xs-12 col-md-3">Choose avatar</h4>
								<div class="col-xs-12 col-md-6 omb_loginOr">
									<div class="">
										<hr class="omb_hrOr">
										<span class="omb_spanOr">or</span>
									</div>
								</div>
								<h4 class="col-xs-12 col-md-3"> Upload Avatar </h4>
							</div>
							<div class="row">
								<div class="col-xs-12 col-md-6" id="defaultAvatarsPicker">
									<img class="default-avatar-image" ng-repeat="avatarImg in avatarImages" src="{{avatarImg}}" alt="Avatar Image" ng-click="selectDefaultAvatarImg(avatarImg)" />
								</div>

								<div class="col-xs-12 col-md-3 col-md-offset-3" id="uploadAvatarPicker">
									<div class="row">
									    <form:input path="avatarUrl" type="hidden" name="avatarUrl" id="avatarUrl" ng-model="$scope.user.avatarUrl"/>
										<img src="resources/core/img/empty-avatar.png" />
									</div>
									<div class="row">
										<button type="button" class="btn btn-primary col-md-offset-2" data-toggle="modal" data-target="#myUploadAvatarModal"><span class="glyphicon glyphicon-upload"></span>Upload Avatar</button>
									</div>
								</div>
							</div>
							<p ng-show="submitted && !userAvatarSet" class="help-block warning-text">You must select an avatar image.</p>
						</div>
						<div class="space-out-top-sm row">
							<div class="col-xs-4 col-sm-3 col-md-3">
								<label class="checkbox-inline">
									<input type="checkbox" name="favoriteColors" ng-model="conditionsAgreement" ng-class="{ 'has-error' : submitted && !conditionsAgreement}"> I Agree
								</label>
							</div>
							<div class="col-xs-8 col-sm-9 col-md-9">
								<div class="row">
									By clicking <strong class="label label-primary">Register</strong>, you agree to the <a href="#" data-toggle="modal" data-target="#t_and_c_m">Terms and Conditions</a> set out by this site, including our Cookie Use.
								</div>
								<div class="row">
									<p ng-show="submitted && !conditionsAgreement" class="help-block warning-text">You must accept the terms and conditions.</p>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-md-6 col-md-offset-3">
							<input path="tokenName" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<form:button type="button" ng-click="submitForm(true)" value="Sign Up" class="btn btn-primary btn-block btn-lg" tabindex="8" />
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>

		<!--  Footer -->
		<footer class="footer-distributed">
			<div class="footer-left">
				<img src="resources/core/img/logo.png">
				<p class="footer-company-name">WeSwap &copy; 2015</p>
			</div>
			<div class="footer-center">
				<div>
					<i class="fa fa-map-marker"></i>
					<p><span>21 BookExchange Street</span> San Francisco, California</p>
				</div>
				<div>
					<i class="fa fa-phone"></i>
					<p>+1 555 123456</p>
				</div>
				<div>
					<i class="fa fa-envelope"></i>
					<p><a href="mailto:support@company.com">support@bookexchange.com</a></p>
				</div>
			</div>
			<div class="footer-right">
				<p class="footer-company-about">
					<span>About the company</span> With its application servers running on quantum machines BookExchange is the most responsive webapp on the market.
				</p>
				<div class="footer-icons">
					<a href="https://www.facebook.com/bootsnipp"><i id="social-fb" class="fa fa-facebook-square fa-3x social"></i></a>
					<a href="https://plus.google.com/+Bootsnipp-page"><i id="social-gp" class="fa fa-google-plus-square fa-3x social"></i></a>
					<a href="mailto:bootsnipp@gmail.com"><i id="social-em" class="fa fa-envelope-square fa-3x social"></i></a>
				</div>
			</div>
		</footer>

		<!-- Modal -->
		<div class="modal fade" id="t_and_c_m" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">�</button>
						<h4 class="modal-title" id="myModalLabel">Terms & Conditions</h4>
					</div>
					<div class="modal-body">
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Similique, itaque, modi, aliquam nostrum at sapiente consequuntur natus odio reiciendis perferendis rem nisi tempore possimus ipsa porro delectus quidem dolorem ad.</p>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Similique, itaque, modi, aliquam nostrum at sapiente consequuntur natus odio reiciendis perferendis rem nisi tempore possimus ipsa porro delectus quidem dolorem ad.</p>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Similique, itaque, modi, aliquam nostrum at sapiente consequuntur natus odio reiciendis perferendis rem nisi tempore possimus ipsa porro delectus quidem dolorem ad.</p>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Similique, itaque, modi, aliquam nostrum at sapiente consequuntur natus odio reiciendis perferendis rem nisi tempore possimus ipsa porro delectus quidem dolorem ad.</p>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Similique, itaque, modi, aliquam nostrum at sapiente consequuntur natus odio reiciendis perferendis rem nisi tempore possimus ipsa porro delectus quidem dolorem ad.</p>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Similique, itaque, modi, aliquam nostrum at sapiente consequuntur natus odio reiciendis perferendis rem nisi tempore possimus ipsa porro delectus quidem dolorem ad.</p>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Similique, itaque, modi, aliquam nostrum at sapiente consequuntur natus odio reiciendis perferendis rem nisi tempore possimus ipsa porro delectus quidem dolorem ad.</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">I Agree</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->


		<!-- Upload Avatar Modal -->
		<div class="modal fade" id="myUploadAvatarModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="myModalLabel">Upload Avatar Picture</h4>
					</div>
					<div class="modal-body">
						<form id="avatarUploadForm">
							<input ng-model="avatarUrl" id="avatarUploadInput" name="picture" role="uploadcare-uploader" data-image-shrink="1024x1024" />
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary" ng-click="setAvatarPicture()">Save changes</button>
					</div>
				</div>
			</div>
		</div>
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js "></script>
		<script src="https://ucarecdn.com/widget/2.6.0/uploadcare/uploadcare.min.js" charset="utf-8"></script>
		<script src="resources/core/js/bootstrap.js "></script>
		<script src="resources/core/js/bootstrap-multiselect.js"></script>
		<script src="resources/core/js/angular.js "></script>
		<script src="resources/core/js/index/index.js "></script>
		<script src="resources/core/js/index/sign-up-controller.js"></script>
		<script type="text/javascript">
			UPLOADCARE_PUBLIC_KEY = '76aa760fad5093c9dcc0';

			$(document).ready(function () {
				$('.button-checkbox').each(function () {
					// Settings
					var $widget = $(this),
						$button = $widget.find('button'),
						$checkbox = $widget.find('input:checkbox'),
						color = $button.data('color'),
						settings = {
							on: {
								icon: 'glyphicon glyphicon-check'
							},
							off: {
								icon: 'glyphicon glyphicon-unchecked'
							}
						};

					// Event Handlers
					$button.on('click', function () {
						$checkbox.prop('checked', !$checkbox.is(':checked'));
						$checkbox.triggerHandler('change');
						updateDisplay();
					});
					$checkbox.on('change', function () {
						updateDisplay();
					});

					// Actions
					function updateDisplay() {
						var isChecked = $checkbox.is(':checked');

						// Set the button's state
						$button.data('state', (isChecked) ? "on" : "off");

						// Set the button's icon
						$button.find('.state-icon')
							.removeClass()
							.addClass('state-icon ' + settings[$button.data('state')].icon);

						// Update the button's color
						if (isChecked) {
							$button
								.removeClass('btn-default')
								.addClass('btn-' + color + ' active');
						} else {
							$button
								.removeClass('btn-' + color + ' active')
								.addClass('btn-default');
						}
					}

					// Initialization
					function init() {

						updateDisplay();

						// Inject the icon if applicable
						if ($button.find('.state-icon').length == 0) {
							$button.prepend('<i class="state-icon ' + settings[$button.data('state')].icon + '"></i>');
						}
					}
					init();
				});
			});
		</script>
	</body>

	</html>