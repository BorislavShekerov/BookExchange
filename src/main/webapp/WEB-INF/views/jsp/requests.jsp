<div class="side-body" id="main-content">
	<div class="pagination-centered text-center" ng-if="loadingRequests"><img src="../resources/core/img/ajax-loader.gif" id="loading-indicator" /> </div>
	<div class="row text-center well well-lg" ng-if="!loadingRequests && userRequestsReceived.length == 0">
		<h2 class="animated fadeIn text-center"><i class="fa fa-frown-o"></i>No offers received</h2>
	</div>
	<div class="row">
		<div ng-repeat="exchangeRequest in userRequestsReceived" class="media">
			<div class="row" ng-if="exchangeRequest.isChain">
			    <div class="col-xs-10 col-xs-offset-1 col-md-3 col-md-offset-0 text-center">
			    <div class="chain-creator-wrapper " href="#">
                						<h3 class="text-center"> Chain Creator </h3>
                						<img class="media-object img-responsive" ng-src="{{exchangeRequest.exchangeInitiator.avatarUrl}}" alt="profile">
                						<h4 class="text-center"> {{exchangeRequest.exchangeInitiator.fullName}} </h4>
                					</div>
			    </div>
				<div ng-class="{'col-md-6': exchangeRequest.chainRequest.answered && exchangeRequest.chainRequest.accepted ,'space-out-top':exchangeRequest.answered && exchangeRequest.accepted, 'col-md-8': !exchangeRequest.chainRequest.answered || !exchangeRequest.chainRequest.accepted}">

					<div class="media-body">

						<div class="well well-lg">
							<div ng-if="exchangeRequest.chainRequest.answered">
								<h4 ng-show="exchangeRequest.chainRequest.accepted" class="successful-exchange">Exchange Accepted <span class="glyphicon glyphicon-ok"></span></h4>
								<h4 ng-show="!exchangeRequest.chainRequest.accepted" class="rejected-exchange">Exchange Rejected <span class="glyphicon glyphicon-remove"></span></h4>
							</div>
							<div class="body-content">
								<h4 class="media-heading text-uppercase reviews">Book Requested From You</h4>
								<ul class="media-date text-uppercase reviews list-inline">
									<li class="dd">{{exchangeRequest.dateCreated.dayOfMonth}}</li>
									<li class="mm">{{exchangeRequest.dateCreated.monthValue}}</li>
									<li class="aaaa">{{exchangeRequest.dateCreated.year}}</li>
								</ul>
								<div ng-class="{'text-center':isUserMobile}">
									<img src="{{exchangeRequest.bookRequested.imgUrl}}">
								</div>
								<div class="pagination-centered text-center" ng-if="ratingLoading"><img src="../resources/core/img/ajax-loader.gif" id="loading-indicator" /> </div>
								<div class="rating-holder" ng-if="exchangeRequest.over && exchangeRequest.successful">
									<p class="media-heading text-uppercase reviews" ng-if="!exchangeRequest.rated">{{"Rate "+ exchangeRequest.exchangeInitiator.firstName}}
										<ng-rate-it id="ratingMechanism" ng-model="exchangeRequest.rating" ng-click="userRatingSet(exchangeRequest)"></ng-rate-it>
									</p>
									<p class="media-heading text-uppercase reviews" ng-if="exchangeRequest.rated">Rated
										<ng-rate-it id="ratingMechanism" ng-model="exchangeRequest.rating" read-only="true"></ng-rate-it>
									</p>
									<textarea type="textarea" ng-model="exchangeRequest.ratingComment" ng-if="exchangeRequest.showRateButton && !exchangeRequest.rated" placeholder="Write Comment Here" maxlength="140" rows="7"></textarea>
								</div>
								<div class="row">
								<div class="col-sm-12 col-md-2 text-center">
								<a class="ghost-button  offers-received-button text-uppercase" ng-if="!exchangeRequest.chainRequest.answered" ng-click="openDetailsModal(exchangeRequest)" id="reply"><span class="glyphicon glyphicon-share-alt"></span> View Details</a>
								</div>
								<div class="col-sm-12 col-md-2 text-center">
								<a class="ghost-button  offers-received-button text-uppercase" ng-if="exchangeRequest.showRateButton" ng-click="rateUser(exchangeRequest)"><span class="glyphicon glyphicon-comment"></span> Rate</a>
                                </div>
                                </div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-4" ng-if="exchangeRequest.chainRequest.answered && exchangeRequest.chainRequest.accepted">
					<div class="col-md-3">
						<span class="glyphicon glyphicon-resize-horizontal"></span>
					</div>
					<div class="col-md-9">
						<h4 class="text-center user-choice-header"> YOUR CHOICE </h4>
						<img src="{{exchangeRequest.userChoice.imgUrl}}" />
					</div>
				</div>

			</div>
			<div class="row" ng-if="!exchangeRequest.isChain">
				<div cass="col-xs-12" ng-class="{'col-md-8': exchangeRequest.over && exchangeRequest.successful ,'space-out-top':exchangeRequest.over && exchangeRequest.successful, 'col-md-12': !exchangeRequest.over || !exchangeRequest.successful}">
					<div ng-class="{'text-center' : isUserMobile ,'pull-left': !isUserMobile ,'pagination-centered':isUserMobile}">
					<a>
						<img ng-class="{'text-center' : isUserMobile ,'pull-left': !isUserMobile ,'align-center': isUserMobile,'display-inline': isUserMobile}" class="media-object" src="{{exchangeRequest.bookRequested.imgUrl}}" alt="profile">
					</a>
					</div>
					<div class="media-body">
						<div class="well well-lg">
							<div ng-if="exchangeRequest.over">
								<h4 ng-show="exchangeRequest.successful" class="successful-exchange">Exchange Accepted <span class="glyphicon glyphicon-ok"></span></h4>
								<h4 ng-show="!exchangeRequest.successful" class="rejected-exchange">Exchange Rejected <span class="glyphicon glyphicon-remove"></span></h4>
							</div>
							<div class="body-content">
								<h4 class="media-heading text-uppercase reviews">Request Initiator :<span ng-if="isUserMobile"><br  /></span>{{" " + exchangeRequest.exchangeInitiator.fullName}}</h4>
								<div class="pagination-centered text-center" ng-if="ratingLoading"><img src="../resources/core/img/ajax-loader.gif" id="loading-indicator" /> </div>
								<div class="rating-holder" ng-if="exchangeRequest.over && exchangeRequest.successful">
									<p class="media-heading text-uppercase reviews" ng-if="!exchangeRequest.rated">{{"Rate "+ exchangeRequest.exchangeInitiator.firstName}}
										<ng-rate-it id="ratingMechanism" ng-model="exchangeRequest.rating" ng-click="userRatingSet(exchangeRequest)"></ng-rate-it>
									</p>
									<p class="media-heading text-uppercase reviews" ng-if="exchangeRequest.rated">Rated
										<ng-rate-it id="ratingMechanism" ng-model="exchangeRequest.rating" read-only="true"></ng-rate-it>
									</p>
									<textarea class="form-control" type="textarea" ng-model="exchangeRequest.ratingComment" ng-if="exchangeRequest.showRateButton && !exchangeRequest.rated" placeholder="Write Comment Here" maxlength="140" rows="7"></textarea>
								</div>
								<ul class="media-date text-uppercase reviews list-inline">
									<li class="dd">{{exchangeRequest.dateCreated.dayOfMonth}}</li>
									<li class="mm">{{exchangeRequest.dateCreated.monthValue}}</li>
									<li class="aaaa">{{exchangeRequest.dateCreated.year}}</li>
								</ul>
								<div ng-if="!exchangeRequest.over && !isUserMobile" class="media-comment">

								</div>
                                <div class="row">
                                								<div class="col-sm-12 col-md-2 text-center">
								<a class="ghost-button  offers-received-button text-uppercase" ng-if="!exchangeRequest.over" ng-click="viewDetails(exchangeRequest)" id="reply"><span class="glyphicon glyphicon-share-alt"></span> View Details</a>
								</div>
								<div class="col-sm-12 col-md-2 text-center">
								<a class="ghost-button  offers-received-button text-uppercase" ng-if="exchangeRequest.showRateButton" ng-click="rateUser(exchangeRequest)"><span class="glyphicon glyphicon-comment"></span> Rate</a>
                                </div>
							</div>

						</div>

					</div>
				</div>
				<div class="col-xs-12 col-md-4" ng-if="exchangeRequest.over && exchangeRequest.successful">
					<div class="col-md-3" ng-if="!isUserMobile">

						<span class="glyphicon glyphicon-resize-horizontal"></span>
					</div>
					<div class="col-md-9">
						<h4 class="text-center user-choice-header"> YOUR CHOICE </h4>
						<img src="{{exchangeRequest.bookOfferedInExchange.imgUrl}}" />
					</div>
				</div>

			</div>
			<hr/>
		</div>
	</div>
</div>