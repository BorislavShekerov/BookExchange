<div id="exchange-offer-modal">
	<div class="modal-header">
		<h3 class="modal-title">Exchange Chain Proposal</h3>
	</div>
	<div class="modal-body">
		<div class="row pagination-centered" ng-if="loading"><img src="../resources/core/img/ajax-loader.gif" id="loading-indicator" /> </div>
		<div class="row text-center well well-lg" ng-if="!loading && !pathFound">
			<h2 class="animated fadeIn text-center"><i class="fa fa-frown-o"></i>No offers made</h2>
		</div>
		<div class="row" ng-if="!loading && pathFound">
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
						<img src="{{userOnExchangePath.avatarUrl}}" class="img-responsive">
						<h2 class="panel-title">{{userOnExchangePath.firstName + " " + userOnExchangePath.lastName}}</h2>
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
</div>