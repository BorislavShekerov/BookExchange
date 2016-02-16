<div class="side-body" id="main-content">
	<div class="row">
		<div ng-repeat="exchangeRequest in userRequestsReceived" class="media">
			<div class="row" ng-if="exchangeRequest.isChain">>
				<div class="col-md-8">
					<a class="pull-left" href="#">
						<h3 class="text-center"> Chain Creator </h3>
						<img class="media-object" src="../{{exchangeRequest.exchangeInitiator.avatarUrl}}" alt="profile">
						<h4 class="text-center"> {{exchangeRequest.exchangeInitiator.fullName}} </h4>
					</a>
					<div class="media-body">
						<div class="well well-lg">
							<h4 class="media-heading text-uppercase reviews">Book Requested From You</h4>
							<ul class="media-date text-uppercase reviews list-inline">
								<li class="dd">{{exchangeRequest.dateCreated.dayOfMonth}}</li>
								<li class="mm">{{exchangeRequest.dateCreated.monthValue}}</li>
								<li class="aaaa">{{exchangeRequest.dateCreated.year}}</li>
							</ul>
							<div>
								<img src="{{exchangeRequest.bookRequested.imgUrl}}">
							</div>
							<a class="ghost-button  reject-offer-button text-uppercase" ng-click="cancelRequest(exchangeCreated)" id="reply"><span class="glyphicon glyphicon glyphicon-remove"></span> Reject Request</a>
							<a class="ghost-button  offers-received-button text-uppercase" ng-click="cancelRequest(exchangeCreated)" id="reply"><span class="glyphicon glyphicon-share-alt"></span> View Details</a>

						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="col-md-3">

						<span class="glyphicon glyphicon-resize-horizontal"></span>
					</div>
					<div class="col-md-9">
						<h4 class="text-center"> Your Choice </h4>
						<img ng-click="openDetailsModal(exchangeRequest)" src="{{exchangeRequest.userChoice.imgUrl}}" />
					</div>
				</div>


			</div>
			<div class="row" ng-if="!exchangeRequest.isChain">
				<div ng-class="{'col-md-8': exchangeRequest.over && exchangeRequest.successful , 'col-md-12': !exchangeRequest.over || !exchangeRequest.successful}">
					<a class="pull-left" href="#">
						<img class="media-object" src="{{exchangeRequest.bookRequested.imgUrl}}" alt="profile">
					</a>
					<div class="media-body">
						<div class="well well-lg">
							<h4 class="media-heading text-uppercase reviews">Request Initiator:{{" "+exchangeRequest.bookOfferedInExchange.ownerFirstname + " " + exchangeRequest.bookOfferedInExchange.ownerLastname}}</h4>
							<ul class="media-date text-uppercase reviews list-inline">
								<li class="dd">{{exchangeRequest.dateCreated.dayOfMonth}}</li>
								<li class="mm">{{exchangeRequest.dateCreated.monthValue}}</li>
								<li class="aaaa">{{exchangeRequest.dateCreated.year}}</li>
							</ul>
                        <div ng-if="!exchangeCreated.over"class="media-comment">

                        				</div>

							<a class="ghost-button  reject-offer-button text-uppercase" ng-if="!exchangeRequest.over" ng-click="cancelRequest(exchangeRequest)" id="reply"><span class="glyphicon glyphicon glyphicon-remove"></span> Reject Request</a>
							<a class="ghost-button  offers-received-button text-uppercase" ng-if="!exchangeRequest.over"  ng-click="viewDetails(exchangeRequest)" id="reply"><span class="glyphicon glyphicon-share-alt"></span> View Details</a>

						</div>
					</div>
				</div>
				<div class="col-md-4" ng-if="exchangeRequest.over && exchangeRequest.successful">
					<div class="col-md-3">

						<span class="glyphicon glyphicon-resize-horizontal"></span>
					</div>
					<div class="col-md-9">
						<h4 class="text-center"> Your Choice </h4>
						<img src="{{exchangeRequest.bookOfferedInExchange.imgUrl}}" />
					</div>
				</div>
			</div>
			<hr/>
		</div>
	</div>
</div>