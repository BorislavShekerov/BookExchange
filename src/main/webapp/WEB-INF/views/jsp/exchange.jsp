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
		<div class="col-md-3" ng-if="!isMobile">
			<a id="filterButton" class="filter-exchange-ghost-button"  ng-click="changeFilterState()" ng-class="{'filter-button-clicked': filterExpanded}"><span class="glyphicon" ng-class="{'glyphicon-chevron-down':!filterExpanded,'glyphicon-chevron-up':filterExpanded}"></span>Filter</a>
		</div>
	</div>
	<div class="row">
		<div ng-class="{'col-sm-6 col-md-8': filterExpanded, 'col-sm-12': !filterExpanded}">
			<div id="library" ng-class="{'col-md-4': filterExpanded, 'col-sm-6 col-md-3': !filterExpanded}" ng-mouseenter="hoveredOverBook(bookOnExchange)" ng-mouseleave="bookOnExchange.hoveredOver = false" ng-if="!filteringResults" ng-repeat="bookOnExchange in booksDisplayed" >
				<div ng-if="bookOnExchange.hoveredOver" ng-class="{'animated': bookOnExchange.hoveredOver ,'fadeIn': bookOnExchange.hoveredOver}" class="offer-exchange-button" ng-click="openExchangeModal(bookOnExchange)">
					<span class="glyphicon glyphicon-refresh margin-right-small"></span>
				</div>
				<div ng-if="bookOnExchange.hoveredOver" ng-class="{'animated': bookOnExchange.hoveredOver ,'fadeIn': bookOnExchange.hoveredOver}" class="triangle-topright"></div>
				<div id="outer-box">
					<img class="img-responsive" ng-src="{{bookOnExchange.imgUrl}}">
				</div>
				<div id="book-details-wrap" ng-if="bookOnExchange.hoveredOver" ng-class="{'animated': bookOnExchange.mouseEntered ,'fadeIn': bookOnExchange.hoveredOver}" class="book-owner-details-wrap">
				</div>
				<div id="userPostedDetails" ng-if="bookOnExchange.hoveredOver" ng-class="{'animated': bookOnExchange.hoveredOver ,'fadeIn': bookOnExchange.hoveredOver}" class="row book-owner-details">
					<div class="col-md-3" id="avatarHolder">
						<img class="img-responsive" ng-src="{{bookOnExchange.ownerAvatar}}" /> </div>
					<div class="col-md-9" id="details">
						<a href="#/account/{{bookOnExchange.ownerEmail}}">{{bookOnExchange.ownerFirstname + " " + bookOnExchange.ownerLastname}}</a>
                           <ng-rate-it ng-model="bookOnExchange.ownerRating" read-only="true"></ng-rate-it>
					</div>
				</div>
			</div>

		</div>

		<div id="categoriesFilter" ng-if="filterExpanded" class="col-sm-6 col-md-4" ng-class="{'position-fixed':shouldFixCategoriesFilter}">

			<div class="row">
				<a class="filter-exchange-ghost-button col-sm-5" ng-class="{'filterChosen':category.selected}" ng-repeat="category in allCategories" ng-click="adjustCategoryFilter(category)">{{category.categoryName}}</a>
			</div>
		</div>
	</div>
	<div class="row" ng-if="searching || loadingMoreResults">
		<h4 class="pagination-centered text-center" ng-if="searching">Searching</h4>
		<h4 class="pagination-centered text-center" ng-if="loadingMoreResults">Loading More Books</h4>
		<div class="pagination-centered text-center"><img src="../resources/core/img/ajax-loader.gif" id="loading-indicator" /> </div>
	</div>
	<h3 class="pagination-centered text-center" ng-if="!(searching || loadingMoreResults) && booksDisplayed.length == 0">No Results Found</h3>



	<footer></footer>
</div>