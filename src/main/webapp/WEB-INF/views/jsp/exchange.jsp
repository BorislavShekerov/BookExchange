<div class="side-body" id="main-content">
	<div class="row">
		<div class="col-md-10">
			<form id="custom-search-form">
				<div id="custom-search-input">
					<div class="input-group" ng-class="{'col-md-12':!filterExpanded, 'col-md-10':filterExpanded}">
						<input type="text" class="  search-query form-control" placeholder="Search" />
						<span class="input-group-btn">
                                          <button class="btn btn-danger" type="button">
                                              <span class=" glyphicon glyphicon-search"></span>
						</button>
						</span>
					</div>
				</div>
			</form>
		</div>
		<div>
			<button id="filterButton" type="button" class="btn btn-primary" ng-click="filterExpanded =! filterExpanded"><span class="glyphicon" ng-class="{'glyphicon-chevron-down':!filterExpanded,'glyphicon-chevron-up':filterExpanded}"></span>Filter</button>
		</div>
	</div>
	<div class="row">
		<div ng-class="{'col-sm-6 col-md-8': filterExpanded, 'col-sm-12': !filterExpanded}">
			<div ng-repeat="bookOnExchange in booksDisplayed " ng-class="{'col-md-4': filterExpanded, 'col-sm-6 col-md-3': !filterExpanded}">
				<div class="panel panel-default" ng-class="{cardHover: hover}" ng-mouseenter="hover = true" ng-mouseleave="hover = false">
					<img src="{{bookOnExchange.imgUrl}}" alt="{{bookOnExchange.title}}" class="img-responsive full-width" id="bookCoverImg">
					<div class="panel-body">
						<div class="media">
							<div class="media-left ">
								<a href="#">
									<img class="media-object profile-picture" src="../{{bookOnExchange.ownerAvatar}}" alt="...">
								</a>
							</div>
							<div class="media-body">
								<h4 class="media-heading">{{bookOnExchange.ownedBy}}</h4>
								<h4 class="media-heading">Exchanges for:</h4>
							</div>
						</div>
						<ul class="list-inline space-out-top-sm">
							<li ng-repeat="category in bookOnExchange.ownerCategoriesOfInterest">
								<span class="label label-default">{{category}}</span></li>
						</ul>
						<div class="space-out-top-sm">
							<button type="button" class="btn btn-primary" ng-click="initiateExchange(bookOnExchange.title,bookOnExchange.ownedBy)"><span class="glyphicon glyphicon-refresh margin-right-small"></span>Offer Exchange</button>
						</div>

					</div>
				</div>
			</div>
		</div>
		<div uib-accordion class="col-sm-6 col-md-4 categories" close-others="false" ng-if="filterExpanded">
			<uib-accordion-group is-open="isCategoryFilterOpen">
				<uib-accordion-heading>
					Filter By Categories <i class="pull-right glyphicon" ng-class="{'glyphicon-chevron-down': status.open, 'glyphicon-chevron-right': !status.open}"></i>
				</uib-accordion-heading>
				<div class="row">
				    <div class="btn-group">
                            <label class="btn btn-primary col-sm-6" ng-repeat="category in allCategories" ng-model="category.selected" uib-btn-checkbox>{{category.categoryName}}</label>
                    </div>
				</div>
		</div>
	</div>
</div>