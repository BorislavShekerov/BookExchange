<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <sec:csrfMetaTags />
     <meta name="_csrf_parameter" content="${_csrf_parameter}"/>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
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
    <link href="../resources/core/css/app/notifications.css" rel="stylesheet">
    <!-- Include Bootstrap-select CSS, JS -->
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.2/css/bootstrap-select.min.css" />

</head>

<body ng-app="myApp">

            <c:url value="/j_spring_security_logout" var="logoutUrl" />

                        	<!-- csrt for log out-->
                        	<form action="${logoutUrl}" method="post" id="logoutForm">
                        	  <input type="hidden"
                        		name="${_csrf.parameterName}"
                        		value="${_csrf.token}" />
                        	</form>

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

                           <li>
                                                            <a role="button" data-toggle="modal" data-target="#addBookModal">
                                                                                                       <span class="glyphicon glyphicon-plus"></span>Add Book
                                                            </a>
                                                           </li>
                     <li class="dropdown">
                                        <a class="dropdown-toggle" data-toggle="dropdown" role="button">
                                            <span class="glyphicon glyphicon-user"></span>
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
                                                            <p class="col-sm-10 col-sm-10-offset-1">
                                                                <a href="javascript:formSubmit()" class="btn btn-danger btn-block">Log Out</a>
                                                            </p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </li>
                                        </ul>
                                    </li>

                                    <li class="dropdown">
                                      <a id="dLabel" role="button" data-toggle="dropdown" class="dropdown-toggle"  data-target="#">
                                        <span class="glyphicon glyphicon-bell"></span>
                                      </a>

                                      <ul class="dropdown-menu notifications" role="menu" aria-labelledby="dLabel">

                                        <div class="notification-heading"><h4 class="menu-title">Notifications</h4><h4 class="menu-title pull-right">View all<i class="glyphicon glyphicon-circle-arrow-right"></i></h4>
                                        </div>
                                        <li class="divider"></li>
                                       <div class="notifications-wrapper">
                                          <c:forEach var="notification" items="${userDetails.userNotifications}">
                                               <a class="content" href="#">

                                                <div class="notification-item">
                                                 <h4 class="item-title">${notification.message}</h4>
                                                  <p class="item-info">Marketing 101, Video Assignment</p>
                                                  </div>

                                                  </a>
                                          </c:forEach>

                                       </div>
                                        <li class="divider"></li>
                                        <div class="notification-footer"><h4 class="menu-title">View all<i class="glyphicon glyphicon-circle-arrow-right"></i></h4></div>
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

    <!-- Add Book Modal -->
<div class="modal fade" id="addBookModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" ng-controller = "moduleController">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Adding a new Book</h4>
      </div>
      <div class="modal-body">
        <div class="row">
          <div class="col-md-10 col-md-offset-1">
            <form class="form-horizontal" role="form">
              <fieldset>

                <!-- Form Name -->
                <legend>Book Details</legend>

                <!-- Text input-->
                <div class="form-group">
                 <label class="col-sm-2 control-label" for="textinput">Title</label>
                  <div class="col-sm-10">
                    <input type="text" placeholder="Book Title" class="form-control" ng-model="bookTitle">
                  </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                  <label class="col-sm-2 control-label" for="textinput">Author</label>
                  <div class="col-sm-10">
                    <input type="text" placeholder="Book Author" class="form-control" ng-model="bookAuthor">
                  </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                 <label class="col-sm-2 control-label" for="textinput">Genre</label>
                  <div class="col-sm-10">
                    <input type="text" placeholder="Book Genre" class="form-control" ng-model="bookCategory">
                  </div>
                </div>


                <!-- Text input-->
                                <div class="form-group">
                                 <label class="col-sm-2 control-label" for="textinput">Cover</label>
                                  <div class="row" ng-show="showBookCoverImage">
                                                         <img src="{{imgUrl}}" alt="Book Cover" id="bookCoverImage">
                                                                             </div>
                                  <div class="col-sm-10" ng-class="{'col-sm-offset-2':showBookCoverImage}">
                                    <input type="text" placeholder="Book Genre" class="form-control" ng-model="imgUrl" ng-change="textChanged()">
                                  </div>
                                </div>



              </fieldset>
            </form>
          </div><!-- /.col-lg-12 -->
      </div><!-- /.row -->
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" ng-click="addBook()">Add Book</button>
      </div>
    </div>
  </div>
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
      var  username="${userDetails.username}";

      function formSubmit() {
      			document.getElementById("logoutForm").submit();
      		}

        $(document).ready(function () {
            var  username="${userDetails.username}";
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
    <script src="../resources/core/js/app/app-services.js "></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js "></script>
      <script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.2/js/bootstrap-select.min.js"></script>
</body>

</html>