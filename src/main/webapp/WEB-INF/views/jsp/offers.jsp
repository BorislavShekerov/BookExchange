<div class="side-body" id="offers-made">
	<div id="loading-container" class="pagination-centered text-center" ng-if="loadingRequests"><img src="../resources/core/img/ajax-loader.gif" id="loading-indicator" /> </div>
	<div class="row text-center well well-lg" ng-if="!loadingRequests && userExchangesCreated.length == 0">
		<h2 class="animated fadeIn text-center"><i class="fa fa-frown-o"></i>No offers made</h2>
	</div>
	<div ng-repeat="exchangeCreated in userExchangesCreated" class="media">
	    <div ng-class="{'text-center' : isUserMobile ,'pull-left': !isUserMobile ,'pagination-centered':isUserMobile}">
		<a>
			<img ng-class="{'text-center' : isUserMobile ,'pull-left': !isUserMobile ,'align-center': isUserMobile}" class="media-object" src="{{exchangeCreated.bookRequested.imgUrl}}" alt="profile">
		</a>
		</div>
		<div class="media-body">

			<div class="well well-lg">
				<div ng-if="exchangeCreated.over">
					<h4 ng-show="exchangeCreated.successful" class="successful-exchange">Exchange Successful <span class="glyphicon glyphicon-ok"></span></h4>
					<h4 ng-show="exchangeCreated.bookOfferedInExchange && !exchangeCreated.successful" class="rejected-exchange">Exchange Rejected <span class="glyphicon glyphicon-remove"></span></h4>
					<h4 ng-show="exchangeCreated.canceled || !exchangeCreated.bookOfferedInExchange" class="rejected-exchange">Exchange Canceled <span class="glyphicon glyphicon-remove"></span></h4>
				</div>
				<div class="body-content">
					<h4 class="media-heading text-uppercase reviews">{{"Requested from : "+ exchangeCreated.bookRequested.ownerFirstname + " " + exchangeCreated.bookRequested.ownerLastname}}</h4>
					<div class="pagination-centered text-center" ng-if="ratingLoading"><img src="../resources/core/img/ajax-loader.gif" id="loading-indicator" /> </div>
					<div class="rating-holder"  ng-if="!ratingLoading && (exchangeCreated.over && exchangeCreated.successful)">
						<p class="media-heading text-uppercase reviews" ng-if="!exchangeCreated.rated">{{"Rate "+ exchangeCreated.bookRequested.ownerFirstname}}
							<ng-rate-it id="ratingMechanism" ng-model="exchangeCreated.rating" ng-click="userRatingSet(exchangeCreated)"></ng-rate-it>
						</p>
						<p class="media-heading text-uppercase reviews" ng-if="exchangeCreated.rated">Rated
							<ng-rate-it id="ratingMechanism" ng-model="exchangeCreated.rating" read-only="true"></ng-rate-it>
						</p>
						<textarea class="form-control" type="textarea" ng-model="exchangeCreated.ratingComment" ng-if="exchangeCreated.showRateButton && !exchangeCreated.rated" placeholder="Write Comment Here" maxlength="140" rows="7"></textarea>
					</div>
					<ul class="media-date text-uppercase reviews list-inline">
						<li class="dd">{{exchangeCreated.dateCreated.dayOfMonth}}</li>
						<li class="mm">{{exchangeCreated.dateCreated.monthValue}}</li>
						<li class="aaaa">{{exchangeCreated.dateCreated.year}}</li>
					</ul>
					<div ng-if="!exchangeCreated.successful && !isUserMobile" class="media-comment">

					</div>
					<div class="text-center" ng-class="{'display-block':isUserMobile,'display-inline':!isUserMobile}">
					<a class="ghost-button  reject-offer-button text-uppercase"  ng-if="!exchangeCreated.over" ng-click="cancelRequest(exchangeCreated)" id="reply"><span class="glyphicon glyphicon-remove"></span> Cancel Request</a>
					</div>
					<div class="text-center"  ng-class="{'display-block':isUserMobile,'display-inline':!isUserMobile}">
					<a class="ghost-button  offers-received-button text-uppercase" ng-if="exchangeCreated.isChain" ng-click="exchangeCreated.isCollapsed = !exchangeCreated.isCollapsed" id="reply"><span class="glyphicon glyphicon-share-alt"></span> Check Progress</a>
					</div>
					<div class="text-center"  ng-class="{'display-block':isUserMobile,'display-inline':!isUserMobile}">
					<button class="ghost-button  offers-received-button text-uppercase" ng-if="exchangeCreated.over && exchangeCreated.successful" ng-click="" id="reply"><span class="glyphicon glyphicon glyphicon-random"></span> Initiate Dispatching</button>
					</div>
					<div class="text-center"  ng-class="{'display-block':isUserMobile,'display-inline':!isUserMobile}">
					<a class="ghost-button  offers-received-button text-uppercase" ng-if="exchangeCreated.isSuccessful"><span class="glyphicon glyphicon-comment"></span> Mark Dispatched</a>
					</div>
					<div class="text-center"  ng-class="{'display-block':isUserMobile,'display-inline':!isUserMobile}">
					<a class="ghost-button  offers-received-button text-uppercase" ng-if="exchangeCreated.showRateButton" ng-click="rateUser(exchangeCreated)"><span class="glyphicon glyphicon-comment"></span> Rate</a>
					</div>
					<div class="text-center"  ng-class="{'display-block':isUserMobile,'display-inline':!isUserMobile}">
					<a class="ghost-button   offers-received-button button-wider text-uppercase" ng-if="exchangeCreated.isChain && exchangeCreated.over && !exchangeCreated.successful" ng-click="findOtherExchangePaths(exchangeCreated)"><span class="glyphicon glyphicon-comment"></span> Explore Other Options</a>
                    </div>
					<div id="collapsedChainDetails" uib-collapse="exchangeCreated.isCollapsed">
						<div class="well well-lg">
							<div class="row">
								<div class="col-md-6 col-md-offset-3">
									<uib-progressbar max="100" value="0"><span style="color:black; white-space:nowrap;">Chain Progress {{exchangeCreated.progress}}%</span></uib-progressbar>
								</div>
							</div>
							<ul id="usersInChainList" class="nav nav-justified" id="nav-tabs" role="tablist">
								<li ng-repeat="exchangeChainRequest in exchangeCreated.exchangeChainRequests" role="presentation" ng-class="{'active':exchangeChainRequest.accepted}">
									<h3 class="text-center">{{exchangeChainRequest.requestFor.fullName}}</h3>
									<a href="#dustin" aria-controls="dustin" role="tab" data-toggle="tab" ng-class="{'opacity-max':exchangeChainRequest.answered}">
										<img class="img-circle" src="{{exchangeChainRequest.requestFor.avatarUrl}}" />
									</a>
									<h3 class="text-center" ng-if="!exchangeChainRequest.answered">Not Answered <span class="glyphicon glyphicon-eye-close"></span></h3>
									<h3 class="text-center" ng-if="exchangeChainRequest.answered && !exchangeChainRequest.accepted">Rejected <span class="glyphicon glyphicon-remove"></span></h3>
									<h3 class="text-center" ng-if="exchangeChainRequest.answered && exchangeChainRequest.accepted">Accepted <span class="glyphicon glyphicon-ok"></span></h3>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
		<hr/>
	</div>
</div>