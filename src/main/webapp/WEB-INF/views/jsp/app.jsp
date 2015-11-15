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
    <link href="../resources/core/css/bootstrap.min.css" rel="stylesheet">
    <link href="../resources/core/css/bootstrap-multiselect.css" rel="stylesheet">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">


    <!-- Custom styles for index page-->
    <link href="../resources/core/css/app/nav_bar.css" rel="stylesheet">
      <link href="../resources/core/css/app/app.css" rel="stylesheet">
    <link href="../resources/core/css/app/side_menu.css" rel="stylesheet">
</head>

<body ng-app="myApp" ng-controller="mainController">

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
                <a class="navbar-brand" href="#"><img src="../resources/core/img/logo.png"></a>
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
                                                <a href="#"><img src="../resources/core/img/spring_mvc.jpg" class="img-responsive" alt="product 1"></a>
                                                <h4><small>Summer dress floral prints</small></h4>
                                                <button href="#" class="btn btn-primary" type="button"><span class="glyphicon glyphicon-refresh margin-right-small"></span>Offer Exchange</button>
                                            </div>
                                            <!-- End Item -->
                                            <div class="item">
                                                <a href="#"><img src="../resources/core/img/java_patterns.jpg" class="img-responsive" alt="product 2"></a>
                                                <h4><small>Gold sandals with shiny touch</small></h4>
                                                <button href="#" class="btn btn-primary" type="button"><span class="glyphicon glyphicon-refresh margin-right-small"></span>Offer Exchange</button>
                                            </div>
                                            <!-- End Item -->
                                            <div class="item">
                                                <a href="#"><img src="../resources/core/img/hibernate_in_action.jpg" class="img-responsive" alt="product 3"></a>
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
                     <li class="dropdown">
                                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                            <span class="glyphicon glyphicon-user"></span> 
                                            <strong>Account</strong>
                                             <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li>
                                                <div class="navbar-login">
                                                    <div class="row">
                                                        <div class="col-sm-4 col-lg-4">
                                                            <p class="text-center">
                                                                <img src="../${userDetails.avatarUrl}" class="profile-picture">
                                                            </p>
                                                        </div>
                                                        <div class="col-sm-8 col-lg-8">
                                                            <p class="text-left"><strong>${userDetails.username}</strong></p>
                                                            <p class="text-left small">${userDetails.email}</p>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                     <p class="text-left col-sm-10 col-sm-offset-1">
                                                                                                                    <a href="#" class="btn btn-primary btn-block btn-sm"><span class="glyphicon glyphicon-cog space-out-right-sm" ></span>Preferences</a>
                                                                                                                </p></div>
                                                </div>
                                            </li>
                                            <li class="divider"></li>
                                            <li>
                                                <div class="navbar-login navbar-login-session">
                                                    <div class="row">
                                                        <div class="col-sm-10 col-sm-10-offset-1">
                                                            <p>
                                                                <a href="#" class="btn btn-danger btn-block">Log Out</a>
                                                            </p>
                                                        </div>
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
    <!-- uncomment code for absolute positioning tweek see top comment in css -->
    <!-- <div class="absolute-wrapper"> </div> -->
    <!-- Menu -->
    <div class="side-menu">

        <nav class="navbar navbar-default" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->

            <!-- Main Menu -->
            <div class="side-menu-container">
                <ul class="nav navbar-nav">

                    <li class="list-item-chosen"><a href="#"><span class="glyphicon glyphicon-send"></span> Link</a></li>
                    <li class=""><a href="#/"><span class="glyphicon glyphicon-globe"></span> Exchange</a></li>
                     <li class=""><a href="#offers"><span class="glyphicon glyphicon-export"></span>Your Offers</a></li>
                      <li><a href="#requests"><span class="glyphicon glyphicon-import"></span> Requests Received</a></li>
                    <li><a href="#history"><span class="glyphicon glyphicon-retweet"></span>Exchange History</a></li>
                      <li><a href="#account"><span class="glyphicon glyphicon-user"></span>Account</a></li>





                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </nav>

    </div>

    <!-- Main Content -->
    <div class="container-fluid" ng-view>

    </div>


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js "></script>
    <script src="../resources/core/js/bootstrap.js "></script>
    <script src="../resources/core/js/angular.js "></script>
    <script src="../resources/core/js/angular-route.js "></script>
    <script src="../resources/core/js/jquery.bootpag.js"></script>
    <script src="../resources/core/js/app/app.js "></script>
    <script src="../resources/core/js/app/app-controllers.js "></script>
    <script>
        $(document).ready(function () {
            $('#showNextCategoriesButton,#showPrevCategoriesButton').click(function (e) {
                e.stopPropagation();
            });

            $('.navbar-toggle').click(function () {
                $('.navbar-nav').toggleClass('slide-in');
                $('.side-body').toggleClass('body-slide-in');
                $('#search').removeClass('in').addClass('collapse').slideUp(200);

                /// uncomment code for absolute positioning tweek see top comment in css
                //$('.absolute-wrapper').toggleClass('slide-in');

            });

            // Remove menu for searching
            $('#search-trigger').click(function () {
                $('.navbar-nav').removeClass('slide-in');
                $('.side-body').removeClass('body-slide-in');

                /// uncomment code for absolute positioning tweek see top comment in css
                //$('.absolute-wrapper').removeClass('slide-in');

            });
        });
    </script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js "></script>
</body>

</html>