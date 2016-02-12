<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Fixed Top Navbar Example for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="resources/core/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/core/css/bootstrap-multiselect.css" rel="stylesheet">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">


    <!-- Custom styles for index page-->
    <link href="resources/core/css/app/index.css" rel="stylesheet">
    <link href="resources/core/css/app/nav_bar.css" rel="stylesheet">
    <link href="resources/core/css/app/footer.css" rel="stylesheet">
    <link href="resources/core/css/app/sign_in_popup.css" rel="stylesheet">
</head>

<body ng-app="myApp" id="welcomeController" ng-controller="welcomeController" ng-cloak>

    <!-- Fixed navbar -->

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
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#"><span class="glyphicon glyphicon-home margin-right-small"></span>Home</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <!-- Books Filter Drop Down -->
                    <li class="dropdown mega-dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Books <span class="caret"></span></a>

                        <ul class="dropdown-menu mega-dropdown-menu row">
                            <li class="col-sm-3">
                                <ul>
                                    <li class="dropdown-header">New on the Exchange</li>
                                    <div id="myCarousel" class="carousel slide" data-ride="carousel">
                                        <div class="carousel-inner">
                                            <div class="item active">
                                                <a href="#"><img src="resources/core/img/spring_mvc.jpg" class="img-responsive" alt="product 1"></a>
                                                <h4><small>Summer dress floral prints</small></h4>
                                                <button href="#" class="btn btn-primary" type="button"><span class="glyphicon glyphicon-refresh margin-right-small"></span>Offer Exchange</button>
                                            </div>
                                            <!-- End Item -->
                                            <div class="item">
                                                <a href="#"><img src="resources/core/img/java_patterns.jpg" class="img-responsive" alt="product 2"></a>
                                                <h4><small>Gold sandals with shiny touch</small></h4>
                                                <button href="#" class="btn btn-primary" type="button"><span class="glyphicon glyphicon-refresh margin-right-small"></span>Offer Exchange</button>
                                            </div>
                                            <!-- End Item -->
                                            <div class="item">
                                                <a href="#"><img src="resources/core/img/hibernate_in_action.jpg" class="img-responsive" alt="product 3"></a>
                                                <h4><small>Denin jacket stamped</small></h4>
                                                <button href="#" class="btn btn-primary" type="button"><span class="glyphicon glyphicon-refresh margin-right-small"></span>Offer Exchange</button>
                                            </div>
                                            <!-- End Item -->
                                        </div>
                                        <!-- End Carousel Inner -->
                                    </div>
                                </ul>
                            </li>
                            <li class="col-sm-3">
                                <ul>
                                    <li class="dropdown-header">Books Offered</li>
                                    <li ng-repeat="category in categoriesToDisplayInNav" ng-click="navCategoryFilter(category.categoryName)"><a href="#">{{category.categoryName}} <span class="badge pull-right">{{category.booksForCategory.length}}</span></a></li>
                                    <li class="btn-group" role="group" aria-label="...">
                                        <button type="button" class="btn btn-default" disabled="disabled" id="showPrevCategoriesButton" ng-click="showPrevCategories()"><span class="glyphicon glyphicon-menu-left"></span></button>
                                        <button type="button" class="btn btn-default" ng-click="showNextCategories()" id="showNextCategoriesButton"><span class="glyphicon glyphicon-menu-right"></span></button>
                                    </li>
                                </ul>
                            </li>
                            <li class="col-sm-3">
                                <ul>
                                    <li class="dropdown-header">Books wanted</li>
                                    <li><a href="#">Easy to customize</a></li>
                                    <li><a href="#">Glyphicons</a></li>
                                    <li><a href="#">Pull Right Elements</a></li>
                                </ul>
                            </li>
                            <li class="col-sm-3">
                                <ul>
                                    <li class="dropdown-header">Newsletter</li>
                                    <form class="form" role="form">
                                        <div class="form-group">
                                            <label class="sr-only" for="email">Email address</label>
                                            <input type="email" class="form-control" id="email" placeholder="Enter email">
                                        </div>
                                        <button type="submit" class="btn btn-primary btn-block">Sign up</button>
                                    </form>
                                </ul>
                            </li>
                        </ul>

                    </li>
                    <!-- Login Drop Down -->
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><b>Login</b> <span class="caret"></span></a>
                        <ul id="login-dp" class="dropdown-menu">
                            <li>
                                <div class="row">
                                    <div class="col-md-12">
                                        Login via
                                        <div class="social-buttons">
                                            <a href="#" class="btn btn-fb"><i class="fa fa-facebook"></i> Facebook</a>
                                            <a href="#" class="btn btn-tw"><i class="fa fa-twitter"></i> Twitter</a>
                                        </div>
                                        or
                                        <c:url value="/login" var="loginUrl"/>
                                        <form action="${loginUrl}" method="post">
                                            <div class="form-group">
                                                <label class="sr-only" for="exampleInputEmail2">Username</label>
                                                <input type="text" id="username" name="username" class="form-control" id="exampleInputEmail2" placeholder="Username" />
                                            </div>
                                            <div class="form-group">
                                                <label class="sr-only" for="passwordInputField">Password</label>
                                                <input name="password" type="password" class="form-control"  id="passwordInputField" placeholder="Password" />
                                                <div class="help-block text-right"><a href="">Forget the password ?</a></div>
                                            </div>
                                            <div class="form-group">
                                                <input type="hidden" name="${_csrf.parameterName}"value="${_csrf.token}"/>
                                                <button type="submit" class="btn btn-primary btn-block">Sign in</button>
                                            </div>
                                            <div class="checkbox">
                                                <label>
                                                    <input type="checkbox"> keep me logged-in
                                                </label>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="bottom text-center">
                                        New here ? <a href="#"><b>Join Us</b></a>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
            <!--/.nav-collapse -->
        </div>
    </nav>

    <div id="home" class="home">
        <div class="text-vcenter">
            <blockquote>
                <p>"Nothing is more expensive than a missed opportunity."</p>
                <p><small>H. Jackson Brown, Jr</small></p>
            </blockquote>
            <a href="/signup" class="btn btn-primary btn-lg">Sign Up</a>
        </div>
    </div>

    <!-- /Parallax -->
    <!-- Main Screen Content -->

    <div class="container" id="main">
        <div class="row space-out-top-md">
            <div class="col-xs-12 col-sm-6 col-md-3 sidebar-offcanvas">
                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                    <div class="panel panel-default" id="menu-item1">
                        <div class="panel-heading" role="tab" id="headingDanny">
                            <h3 class="panel-title">
                                <a role="button" data-toggle="collapse"
                                     data-parent="#accordion" href="#search"
                                     aria-expanded="true" aria-controls="search"
                                   ng-click="changeFilterPanelIcon('search',!isSearchFilterExpanded)">
                                    <span class="glyphicon glyphicon-search space-out-right-sm"></span>Search<span class="pull-right" ng-class="{'glyphicon glyphicon-menu-down': isSearchFilterExpanded,'glyphicon glyphicon-menu-right': !isSearchFilterExpanded}"></span></a>
                            </h3>
                        </div>
                        <div role="tabpanel" class="panel-collapse in" id="search" aria-labelledby="headingDanny">
                            <div class="panel-body">
                                <div id="custom-search-input">
                                                          <div class="input-group col-md-12">
                                                              <input type="text" class="  search-query form-control" placeholder="Search" />
                                                              <span class="input-group-btn">
                                                                  <button class="btn btn-primary" type="button">
                                                                      <span class="glyphicon glyphicon-search"></span>
                                                                  </button>
                                                              </span>
                                                          </div>
                                                      </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default" id="menu-item2">
                        <div class="panel-heading" role="tab" id="headingAgumbe">
                            <h3 class="panel-title">
                                <a role="button" data-toggle="collapse"
                                     data-parent="#accordion" href="#categories"
                                     aria-expanded="true" aria-controls="categories"
                                   ng-click="changeFilterPanelIcon('category',!isCategoriesFilterExpanded)">
                                   Categories Filter<span class="pull-right" ng-class="{'glyphicon glyphicon-menu-down': isCategoriesFilterExpanded,'glyphicon glyphicon-menu-right': !isCategoriesFilterExpanded}"></span></a>
                            </h3>
                        </div>
                        <div role="tabpanel" class="panel-collapse collapse" id="categories" aria-labelledby="headingAgumbe">
                            <div class="panel-body">
                                <select id="categories-select" multiple="multiple" class="space-out-top-sm">
                                </select>
                                <div>
                                    <div ng-repeat="category in selectedCategories " class="row space-out-top-sm space-out-left">
                                        <span class="tag label label-info">
                                        <span>{{category}}</span>
                                        <a ng-click="removeCategory(category)"><i class="remove glyphicon glyphicon-remove-sign glyphicon-white"></i></a>
                                        </span>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default" id="menu-item3">
                        <div class="panel-heading" role="tab" id="headingAlberto">
                            <h3 class="panel-title">
                                <a role="button" data-toggle="collapse"
                                     data-parent="#accordion" href="#alberto"
                                     aria-expanded="true" aria-controls="alberto" ng-click="changeFilterPanelIcon('category-wanted',!isCategoriesWantedFilterExpanded)">
                                   Categories Wanted<span class="pull-right" ng-class="{'glyphicon glyphicon-menu-down': isCategoriesWantedFilterExpanded,'glyphicon glyphicon-menu-right': !isCategoriesWantedFilterExpanded}"></span></a>
                            </h3>
                        </div>
                        <div role="tabpanel" class="panel-collapse collapse" id="alberto" aria-labelledby="headingAlberto">
                            <div class="panel-body">
                                <select id="categories-wanted-select" multiple="multiple" class="space-out-top-sm">
                                </select>

                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-12 col-sm-6 col-md-8 sidebar-offcanvas " id="sidebar ">
                <div class="pagination-centered text-center" ng-show="shouldShowLoadingIndicator()"><img src="/resources/core/img/ajax-loader.gif" id="loading-indicator" /> </div>
                <div ng-repeat="bookOnExchange in booksDisplayed " class="col-xs-12 col-sm-12 col-md-4 " ng-cloak>
                    <div class="panel panel-default col-xs-12" ng-show="shouldDipslayCategory(bookOnExchange.category)" ng-class="{cardHover: hover}" ng-mouseenter="hover = true" ng-mouseleave="hover = false" ng-cloak>
                        <img src="{{bookOnExchange.imgUrl}}" class="img-responsive full-width" id="bookCoverImg">
                        <div class="panel-body">
                            <div class="media">
                                <div class="media-left ">
                                    <a href="#">
                                        <img class="media-object profile-picture" src="{{bookOnExchange.ownerAvatar}}" alt="...">
                                    </a>
                                </div>
                                <div class="media-body">
                                    <h4 class="media-heading">{{bookOnExchange.ownerFirstname + " " + bookOnExchange.ownerLastname}}</h4>
                                    <h4 class="media-heading">Exchanges for:</h4>
                                </div>
                            </div>
                            <ul class="list-inline">
                                <li ng-repeat="category in bookOnExchange.ownerCategoriesOfInterest">
                                    <span class="label label-default">{{category}}</span></li>
                            </ul>
                            <div class="space-out-top">
                                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"><span class="glyphicon glyphicon-refresh margin-right-small"></span>Offer Exchange</button>
                            </div>

                        </div>
                    </div>
                </div>
            </div>


        </div>
        <div class="space-out-top-sm pull-right" ng-show="bookPages.length > 0">
            <div id="page-pagination"></div>
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

    <!-- Sign In Modal -->
 <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
 	<div class="modal-dialog" role="document">
 		<div class="modal-content panel-login">
 			<div class="modal-header panel-header">
 				<div class="row">
 					<div class="col-xs-6">
 						<a href="#" class="active" id="login-form-link">Login</a>
 					</div>
 					<div class="col-xs-6">
 						<a href="#" id="register-form-link">Register</a>
 					</div>
 				</div>
 				<hr>
 			</div>
 			<div class="modal-body panel-body">
 				<div class="row">
 					<div class="col-lg-12">
 						<form action="${loginUrl}" method="post" style="display: block;">
                        	<div class="form-group">
                        		<input type="text" id="username" name="username" class="form-control" id="exampleInputEmail2" placeholder="Username" />
                        	</div>
                        	<div class="form-group">
                        		<input name="password" type="password" class="form-control" id="passwordInputField" placeholder="Password" />
                        	</div>
                        	<div class="form-group text-center">
                        		<input type="checkbox" tabindex="3" class="" name="remember" id="remember">
                        		<label for="remember"> Remember Me</label>
                        	</div>
                        	<div class="form-group">
                        	    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        		<div class="row">
                        			<div class="col-sm-6 col-sm-offset-3">
                        	    		<input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="Log In">
                        			</div>
                        		</div>
                        	/div>
                        	<div class="form-group">
                        	    <div class="row">
                        			<div class="col-lg-12">
                        				<div class="text-center">
                        		            <a href="http://phpoll.com/recover" tabindex="5" class="forgot-password">Forgot Password?</a>
                        				</div>
                        		    </div>
                        		</div>
                        	</div>
                        </form>
 						<form id="register-form" action="http://phpoll.com/register/process" method="post" role="form" style="display: none;">
 							<div class="form-group">
 								<input type="text" name="username" id="username" tabindex="1" class="form-control" placeholder="Username" value="">
 							</div>
 							<div class="form-group">
 								<input type="email" name="email" id="email" tabindex="1" class="form-control" placeholder="Email Address" value="">
 							</div>
 							<div class="form-group">
 								<input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password">
 							</div>
 							<div class="form-group">
 								<input type="password" name="confirm-password" id="confirm-password" tabindex="2" class="form-control" placeholder="Confirm Password">
 							</div>
 							<div class="form-group">
 								<div class="row">
 									<div class="col-sm-6 col-sm-offset-3">
 										<input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="Register Now">
 									</div>
 								</div>
 							</div>
 						</form>
 					</div>
 				</div>
 			</div>
 		</div>
 	</div>
 </div>
</html>
<!-- Bootstrap core JavaScript
    ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js "></script>
<script src="resources/core/js/bootstrap.js "></script>
<script src="resources/core/js/bootstrap-multiselect.js"></script>
<script src="resources/core/js/angular.js "></script>
<script src="resources/core/js/jquery.bootpag.js"></script>
<script src="resources/core/js/index/index.js "></script>
<script src="resources/core/js/index/app-directives.js "></script>
<script src="resources/core/js/index/app-controllers.js "></script>
<script src="resources/core/js/index/dummyData.js "></script>
<script type="text/javascript">
    var jumboHeight = $('.jumbotron').outerHeight();

    function parallax() {
        var scrolled = $(window).scrollTop();
        $('.bg').css('height', (jumboHeight - scrolled) + 'px');
    }

    $(window).scroll(function (e) {
        parallax();
    });

    $(document).ready(function () {
        $('#showNextCategoriesButton,#showPrevCategoriesButton').click(function (e) {
            e.stopPropagation();
        });


    $('#login-form-link').click(function(e) {
		$("#login-form").delay(100).fadeIn(100);
 		$("#register-form").fadeOut(100);
		$('#register-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});
	$('#register-form-link').click(function(e) {
		$("#register-form").delay(100).fadeIn(100);
 		$("#login-form").fadeOut(100);
		$('#login-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});
    });
</script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js "></script>
</body>

</html>