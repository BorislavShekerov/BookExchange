<div id="exchange-offer-modal">
	<div class="modal-header">
		<h3 class="modal-title">Make An Exchange Offer</h3>
	</div>
	<div class="modal-body">
		<div class="row" ng-if="exchangeOptionExists">

			<div class="row">
				<button type="button" class="btn btn-primary text-center" ng-click="exchangeOptionExists = false"><span class="glyphicon glyphicon-arrow-left margin-right-small"></span>Back</button>
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
                         				<button type="button" class="btn btn-primary text-center" ng-click="initiateExchangeChain()"><span class="glyphicon glyphicon-flash margin-right-small"></span> Initiate Exchange Chain</button>
                    </div>
			</div>
		</div>


		<div class="row" ng-if="!exchangeOptionExists">

			<div id="offerExchangeForm" class="form-group col-md-4">
				<div class="alert alert-warning" role="alert" ng-hide="userHasBooksOfInterest()"><strong>Warning!</strong> You have no books that match {{bookToExchangeFor.ownedBy}}''s interests</div>
				<label class="control-label">Pick A Book</label>
				<div id="selectContainer">
				    <!-- Single button -->
                    <div class="btn-group" uib-dropdown is-open="status.isopen">
                      <button id="single-button" type="button" class="btn" uib-dropdown-toggle ng-disabled="disabled">
                        {{selectedBook}}<span class="caret"></span>
                      </button>
                      <ul uib-dropdown-menu role="menu" aria-labelledby="single-button">
                        <li ng-repeat="book in userDetails.booksPostedOnExchange" role="menuitem" ng-click="bookChosen(book.title)">{{book.title}}</li>
                      </ul>
                    </div>

				</div>
			</div>

			<div class="col-xs-12 col-sm-2 col-md-4 " id="middle-button-container">
				<div id="arrow-right-container" class="text-center">
					<i class="fa fa-arrow-right fa-4x"></i>
				</div>
			</div>



			<div class="col-md-4">
				<div class="panel panel-default col-xs-12" ng-class="{cardHover: hover}" ng-mouseenter="hover = true" ng-mouseleave="hover = false">
					<img src="{{bookToExchangeFor.imgUrl}}" class="img-responsive full-width">
					<div class="panel-body">
						<div class="media">
							<div class="media-left ">
								<a href="#">
									<img class="media-object profile-picture" src="../{{bookToExchangeFor.ownerAvatar}}" alt="...">
								</a>
							</div>
							<div class="media-body">
								<h4 class="media-heading">{{bookToExchangeFor.ownedBy}}</h4>
								<h4 class="media-heading">Exchanges for:</h4>
							</div>
						</div>
						<ul class="list-inline space-out-top-sm">
							<li ng-repeat="category in bookToExchangeFor.ownerCategoriesOfInterest">
								<span class="label label-default">{{category}}</span></li>
						</ul>
					</div>
				</div>
			</div>

		</div>
		<div class="row" ng-if="!exchangeOptionExists">
			<div class="row text-center">
				<button type="button" class="btn btn-warning text-center" ng-click="initiateOffer()"><span class="glyphicon glyphicon-refresh margin-right-small"></span>Make Offer</button>
				<div class="row omb_loginOr">
					<div class="">
						<hr class="omb_hrOr">
						<span class="omb_spanOr">or</span>
					</div>
				</div>

				<button type="button" class="btn btn-primary text-center" ng-click="exploreOtherOptions()" data-toggle="modal" data-target="#processing-modal"><span class="glyphicon glyphicon-flash margin-right-small"></span> Explore Other Options</button>
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