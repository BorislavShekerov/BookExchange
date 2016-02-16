<div id="exchange-offer-modal">
	<div class="modal-header">
		<h3 class="modal-title">Make An Exchange Offer</h3>
	</div>
	<div class="modal-body">
		<div class="row" ng-if="exchangeOptionExists">

			<div class="row">
			                <a ng-click="exchangeOptionExists = false"><span class="glyphicon glyphicon-arrow-left margin-right-small"></span>Back </a>

			</div>
			<div class="row">
				    <div class="timeline">

                        <!-- Line component -->
                        <div class="line text-muted"></div>


                        <!-- Panel -->
                        <article class="panel panel-primary" ng-repeat="userOnExchangePath in exchangeOptionPath">

                            <!-- Icon -->
                            <div class="panel-heading icon">
                                <i class="glyphicon glyphicon-book"></i>
                            </div>
                            <!-- /Icon -->

                            <!-- Heading -->
                            			<div class="panel-heading">
                            				<img src="../{{userOnExchangePath.avatarUrl}}" class="img-responsive"><h2 class="panel-title">{{userOnExchangePath.firstName + " " + userOnExchangePath.lastName}}</h2>
                            			</div>
                             <!-- Heading -->
                            <!-- Body -->
                            <div class="panel-body">
                              <h3 class="text-center">User Library</h3>

                            </div>
                             <ul class="list-group">
                                                           				<li class="list-group-item" ng-repeat="bookPostedByUser in userOnExchangePath.booksPostedOnExchange">{{bookPostedByUser.title}}</li>
                              </ul>
                            <!-- /Body -->

                        </article>
                        <!-- /Panel -->

                        <!-- Panel -->
                        <article class="panel panel-info panel-outline">

                            <!-- Icon -->
                            <div class="panel-heading icon">
                                <i class="glyphicon glyphicon-info-sign"></i>
                            </div>
                            <!-- /Icon -->

                            <!-- Body -->
                            <div class="panel-body">
                                End of exchange.
                            </div>
                            <!-- /Body -->

                        </article>
                        <!-- /Panel -->

                    </div>
                    <div class="row">
                    			                <a ng-click="initiateExchangeChain()"><span class="glyphicon glyphicon-flash margin-right-small"></span> Initiate Exchange Chain </a>
                    </div>
			</div>
		</div>


		<div class="row" ng-if="!exchangeOptionExists">

            <div class="row">
			<div id="offerExchangeForm">
				<div class="alert alert-warning" role="alert" ng-hide="userHasBooksOfInterest()"><strong>Warning!</strong> You have no books that match {{bookToExchangeFor.ownerFirstname + " " + bookToExchangeFor.lastName+ "'"}}s interests</div>
			</div>
            </div>

            <fiv class="row">
			<div class="col-md-10 col-md-offset-1">
				<div class="panel panel-default col-xs-12">
					<img src="{{bookToExchangeFor.imgUrl}}" class="img-responsive full-width">
					<div class="panel-body">
						<div class="row">
						    <h4 class="col-md-4">Posted By</h4>
						    <div class="col-md-3">
						    <img class="media-object profile-picture" src="../{{bookToExchangeFor.ownerAvatar}}" alt="...">
						    </div>
						    <h4 class="col-md-3">{{bookToExchangeFor.ownerFirstname + " " + bookToExchangeFor.lastName}}</h4>
						</div>
					</div>
				</div>
			</div>
			</div>

		</div>
		<div id="actions" class="row" ng-if="!exchangeOptionExists">
			<div class="row text-center">
                <a ng-click="initiateOffer()" ><span class="glyphicon glyphicon-refresh margin-right-small"></span>Make Offer</a>
				<div class="row omb_loginOr">
					<div class="">
						<hr class="omb_hrOr">
						<span class="omb_spanOr">or</span>
					</div>
				</div>
                <a ng-click="exploreOtherOptions()" data-toggle="modal" data-target="#processing-modal"><span class="glyphicon glyphicon-flash margin-right-small"></span> Explore Other Options </a>
			</div>

		</div>
	</div>


	<!-- Static Modal -->
	<div class="modal modal-static fade" id="processing-modal" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="text-center">
						<img src="http://www.travislayne.com/images/loading.gif" class="icon" />
						<h4>Processing...</h4>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>