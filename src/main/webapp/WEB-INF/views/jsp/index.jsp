<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

		<title>B | e | X</title>

		<!-- Bootstrap core CSS -->
		<link href="resources/core/css/bootstrap.min.css" rel="stylesheet">
		<link href="resources/core/css/bootstrap-multiselect.css" rel="stylesheet">
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">


		<!-- Custom styles for index page-->
		<link href="resources/core/css/app/index.css" rel="stylesheet">
		<link href="resources/core/css/app/buttons.css" rel="stylesheet">
		<link href="resources/core/css/app/nav_bar.css" rel="stylesheet">
		<link href="resources/core/css/app/footer.css" rel="stylesheet">
		<link href="resources/core/css/app/book_card.css" rel="stylesheet">
		<link href="../resources/core/css/animate.css" rel="stylesheet">
	</head>

	<body ng-app="myApp" ng-controller="indexController" ng-class="{'hideVerticalScroll':hideVerticalScroll}" ng-cloak>

		<!-- Fixed navbar -->

		<nav class="navbar navbar-default navbar-fixed-top">

				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#"><img src="resources/core/img/logo.png"></a>
				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<ul ng-if="!isUserMobile" class="nav navbar-nav">
						<li class="active"><a href="#"><span class="glyphicon glyphicon-home margin-right-small"></span>Home</a></li>
					</ul>
					<form class="navbar-form navbar-left" role="search" class="ng-cloak" ng-show="shouldFixCategoriesFilter">
						<div class="form-group">
							<input type="text" class="form-control" placeholder="Search" ng-model="searchFor">
						</div>
					</form>
					<ul class="nav navbar-nav navbar-right">

						<!-- Login Drop Down -->
						<li class="dropdown">
							<a ng-class="{'text-center': isUserMobile}" href="#" class="dropdown-toggle" data-toggle="dropdown"><b>Login</b> <span class="caret"></span></a>
							<ul id="login-dp" class="dropdown-menu">
								<li>
									<div class="row">
										<div class="col-md-12">
											Login via
											<div class="social-buttons">
												<a ng-click="connectToFacebook()" class="btn btn-fb"><i class="fa fa-facebook"></i> Facebook</a>
												<a href="/connect/twitter" class="btn btn-tw"><i class="fa fa-twitter"></i> Twitter</a>
											</div>
											<p class="text-center">OR</p>
											<c:url value="/login" var="loginUrl" />
											<form action="${loginUrl}" method="post">
												<div class="form-group">
													<label class="sr-only" for="exampleInputEmail2">Username</label>
													<input type="text" id="username" name="username" class="form-control" id="exampleInputEmail2" placeholder="Username" />
												</div>
												<div class="form-group">
													<label class="sr-only" for="passwordInputField">Password</label>
													<input name="password" type="password" class="form-control" id="passwordInputField" placeholder="Password" />
													<div class="help-block text-right"><a href="">Forget the password ?</a></div>
												</div>
												<div class="form-group text-center">
													<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
													<button type="submit" class="ghost-button sign-up-button">Sign in</button>
												</div>

											</form>
										</div>
										<div class="bottom text-center">
											<span>New here ?</span> <a href="/signup"><b>Join Us</b></a>
										</div>
									</div>
								</li>
							</ul>
						</li>
					</ul>
				</div>
				<!--/.nav-collapse -->

		</nav>

		<div id="home" class="home">
			<div id="scrollButtonHolder" ng-click="scrollToSearch()" ng-class="{'bounce infinite':showBouncingScrollArrow }" class="animated "><span class="glyphicon glyphicon-chevron-down" du-smooth-scroll></span></div>
			<div class="text-vcenter text-center">
				<blockquote>
					<p>"Nothing is more expensive than a missed opportunity."</p>
					<p><small>H. Jackson Brown, Jr</small></p>
				</blockquote>
				<a href="/signup" class="ghost-button ghost-button-semi-transparent">Sign Up</a>
			</div>
		</div>



		<!-- Exchange -->
		<div class="row" id="main">
			<!-- Search -->
			<div class="row">
				<div class="col-md-10">
					<form id="custom-search-form">
						<div id="custom-search-input">
							<div class="input-group" ng-class="col-md-12">
								<input type="text" class="search-query form-control" placeholder="Search" ng-model="searchFor" />

								<span class="input-group-btn">
                                                          <button class="btn btn-danger" type="button">
                                                              <span class=" glyphicon glyphicon-search"></span>
								</button>
								</span>
							</div>
						</div>
					</form>
				</div>
				<div class="col-md-2" ng-if="!isUserMobile">
					<a id="filterButton" class="ghost-button filter-button" ng-click="filterButtonClicked()" ng-class="{'filter-button-clicked': filterExpanded}"><span class="glyphicon" ng-class="{'glyphicon-chevron-down':!filterExpanded,'glyphicon-chevron-up':filterExpanded}"></span>Filter</a>
				</div>
			</div>

			<div class="row space-out-top-md">
			    <div class="col-md-9"  ng-if="searching">
			    	<div class="row" >
                				<h4 class="pagination-centered text-center">Searching</h4>
                				<div class="pagination-centered text-center"><img src="resources/core/img/ajax-loader.gif" id="loading-indicator" /> </div>
                			</div>
                			<h3 class="pagination-centered text-center" ng-if="noBooksToDisplay()">No Results Found</h3>
                </div>
				<div ng-cloak ng-if="!searching" ng-class="{'col-sm-6 col-md-9': filterExpanded, 'col-sm-12': !filterExpanded}" class="ng-cloak">
					<div ng-cloak id="exchange" ng-class="{'col-md-4': filterExpanded, 'col-sm-6 col-md-3': !filterExpanded}" ng-repeat="bookOnExchange in booksDisplayed" ng-mouseenter="bookOnExchange.hoveredOver = true" ng-mouseleave="bookOnExchange.hoveredOver = false">
						<div ng-if="bookOnExchange.hoveredOver" ng-class="{'animated': bookOnExchange.hoveredOver ,'fadeIn': bookOnExchange.hoveredOver}" class="offer-exchange-button" ng-click="initiateExchange(bookOnExchange.title,bookOnExchange.ownerEmail)">
							<span class="glyphicon glyphicon-refresh margin-right-small"></span>
						</div>
						<div ng-if="bookOnExchange.hoveredOver" ng-class="{'animated': bookOnExchange.hoveredOver ,'fadeIn': bookOnExchange.hoveredOver}" class="triangle-topright"></div>
						<div id="outer-box">
							<img class="img-responsive" ng-src="{{bookOnExchange.imgUrl}}">
						</div>
						<div ng-cloak id="book-details-wrap" ng-if="bookOnExchange.hoveredOver" ng-class="{'animated': bookOnExchange.mouseEntered ,'fadeIn': bookOnExchange.hoveredOver}" class="book-owner-details-wrap">
						</div>
						<div id="userPostedDetails" ng-if="bookOnExchange.hoveredOver" ng-class="{'animated': bookOnExchange.hoveredOver ,'fadeIn': bookOnExchange.hoveredOver}" class="row book-owner-details">
							<div class="col-md-4" id="avatarHolder">
								<img class="img-responsive" ng-src="{{bookOnExchange.ownerAvatar}}" /> </div>
							<div class="col-md-7 margin-top-sm" id="details">
								<a>{{bookOnExchange.ownerFirstname + " " + bookOnExchange.ownerLastname}}</a>
							</div>
						</div>
					</div>
				</div>

				<div id="categoriesFilter" ng-if="filterExpanded" class="col-sm-6 col-md-3" ng-class="{'position-fixed':shouldFixCategoriesFilter,'filter-reached-bottom':filterReachedBottom}">

					<div class="row">
						<a class="ghost-button filter-button col-sm-6" ng-class="{'filterChosen':category.selected}" ng-repeat="category in allCategories" ng-click="adjustCategoryFilter(category)">{{category.categoryName}}</a>
					</div>
				</div>


			</div>

			<div class="col-md-9"  ng-if="loadingMoreResults">
            			    	<div class="row" >
                            				<h4 class="pagination-centered text-center">Loading Books</h4>
                            				<div class="pagination-centered text-center"><img src="resources/core/img/ajax-loader.gif" id="loading-indicator" /> </div>
                            			</div>
                            </div>



		</div>
		<footer class="footer-distributed">
		    <div ng-if="!isUserMobile">
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

	</html>
	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js "></script>
	<script src="resources/core/js/bootstrap.js "></script>
	<script src="resources/core/js/bootstrap-multiselect.js"></script>
	<script src="resources/core/js/angular.js "></script>
	<script src="resources/core/js/angular-scroll.js "></script>
	<script src="resources/core/js/ng-device-detector.js "></script>
	<script src="resources/core/js/re-tree.js "></script>
	<script src="resources/core/js/jquery.bootpag.js"></script>
			<script src="../resources/core/js/ui-bootstrap-tpls-1.1.0.min.js"></script>

	<!-- Custon JS -->
	<script src="resources/core/js/index/index.js "></script>
	<script src="resources/core/js/index/app-directives.js "></script>
	<script src="resources/core/js/index/indexController.js "></script>
	<script src="resources/core/js/index/signInModalController.js"></script>
	<script src="resources/core/js/app/services/bookService.js "></script>
	<script src="resources/core/js/app/services/categoryService.js "></script>

	<script type="text/javascript">
		var jumboHeight = $('.jumbotron').outerHeight();

		function parallax() {
			var scrolled = $(window).scrollTop();
			$('.bg').css('height', (jumboHeight - scrolled) + 'px');
		}

		$(window).scroll(function (e) {
			parallax();
		});
	</script>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	</body>

	</html>