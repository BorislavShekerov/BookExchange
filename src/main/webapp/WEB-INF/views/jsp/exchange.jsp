<div class="side-body" id="main-content">
	<div class="row">
		<div class="col-md-9">
			<form id="custom-search-form">
				<div id="custom-search-input">
					<div class="input-group" ng-class="{'col-md-12':!filterExpanded, 'col-md-10':filterExpanded}">
						<input type="text" class="  search-query form-control" placeholder="Search" ng-model="searchFor" />

						<span class="input-group-btn">
                                          <button class="btn btn-danger" type="button">
                                              <span class=" glyphicon glyphicon-search"></span>
						</button>
						</span>
					</div>
				</div>
			</form>
		</div>
		<div class="col-md-3">
			<a id="filterButton" class="filter-exchange-ghost-button" ng-click="filterExpanded =! filterExpanded" ng-class="{'filter-button-clicked': filterExpanded}"><span class="glyphicon" ng-class="{'glyphicon-chevron-down':!filterExpanded,'glyphicon-chevron-up':filterExpanded}"></span>Filter</a>
		</div>
	</div>
	<div class="row">
		<div ng-class="{'col-sm-6 col-md-8': filterExpanded, 'col-sm-12': !filterExpanded}">
			<div id="library" ng-class="{'col-md-4': filterExpanded, 'col-sm-6 col-md-3': !filterExpanded}" ng-if="!filteringResults" ng-repeat="bookOnExchange in booksDisplayed" ng-mouseenter="bookOnExchange.hoveredOver = true" ng-mouseleave="bookOnExchange.hoveredOver = false">
				<div ng-if="bookOnExchange.hoveredOver" ng-class="{'animated': bookOnExchange.hoveredOver ,'fadeIn': bookOnExchange.hoveredOver}" class="offer-exchange-button" ng-click="initiateExchange(bookOnExchange.title,bookOnExchange.ownerEmail)">
					<span class="glyphicon glyphicon-refresh margin-right-small"></span>
				</div>
				<div ng-if="bookOnExchange.hoveredOver" ng-class="{'animated': bookOnExchange.hoveredOver ,'fadeIn': bookOnExchange.hoveredOver}" class="triangle-topright"></div>
				<div id="outer-box">
					<img class="img-responsive" src="{{bookOnExchange.imgUrl}}">
				</div>
				<div id="book-details-wrap" ng-if="bookOnExchange.hoveredOver" ng-class="{'animated': bookOnExchange.mouseEntered ,'fadeIn': bookOnExchange.hoveredOver}" class="book-owner-details-wrap">
				</div>
				<div id="userPostedDetails" ng-if="bookOnExchange.hoveredOver" ng-class="{'animated': bookOnExchange.hoveredOver ,'fadeIn': bookOnExchange.hoveredOver}" class="row book-owner-details">
					<div class="col-md-4">
						<img class="img-responsive" src="../{{bookOnExchange.ownerAvatar}}" /> </div>
					<div class="col-md-7">
						<a>{{bookOnExchange.ownerFirstname + " " + bookOnExchange.ownerLastname}}</a>
					</div>
				</div>
			</div>

		</div>

		<div id="categoriesFilter" ng-if="filterExpanded" class="col-sm-6 col-md-4" ng-class="{'position-fixed':shouldFixCategoriesFilter}">

			<div class="row">
				<a class="filter-exchange-ghost-button col-sm-5" ng-repeat="category in allCategories" ng-click="adjustCategoryFilter(category.categoryName)">{{category.categoryName}}</a>
			</div>
		</div>
	</div>
	<div class="row" ng-if="filteringResults || loadingResults">
		<h4 class="pagination-centered text-center" ng-if="loadingResults">Fetching More Books</h4>
		<h4 class="pagination-centered text-center" ng-if="filteringResults">Filtering Data</h4>
		<div class="pagination-centered text-center"><img src="../resources/core/img/ajax-loader.gif" id="loading-indicator" /> </div>
	</div>
	<h3 class="pagination-centered text-center" ng-if="!(filteringResults || loadingResults) && booksDisplayed.length == 0">No Results Found</h3>



	<footer></footer>
</div>