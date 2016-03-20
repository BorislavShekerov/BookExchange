<div class="side-body" id="main-content">
	<div class="row panel" id="accountSection">
		<div class="col-md-4 bg_blur ">
		</div>
		<div class="col-md-8  col-xs-12">
			<img src="{{userAccount.avatarUrl}}" class="img-thumbnail picture hidden-xs" />
			<img src="{{userAccount.avatarUrl}}" class="img-thumbnail visible-xs picture_mob" />
			<div class="header">
				<h1 ng-bind="userAccount.fullName"></h1>
				<h4>Genres Interested In:</h4>
				<span ng-repeat="category in userAccount.categoriesInterestedIn " class="space-out-top-sm space-out-right-sm">
                                                        <span class="tag label label-info">
                                                        <span ng-bind="category.categoryName"></span>
				<a ng-if="isEditable" ng-click="removeCategoryInterestedIn(category)"><i class="remove glyphicon glyphicon-remove-sign glyphicon-white"></i></a>
				</span>

				</span>
				<span><button ng-if="isEditable" type="button" ng-click="openAddPreferredCategoriesModal()" class="btn btn-default btn-circle btn-lg"><i class="glyphicon glyphicon-plus"></i></button></span>

			</div>
		</div>
	</div>

	<div class="row" id="accountSectionSelectors">
		<div class="col-md-4"></div>
		<div class="col-md-8 col-xs-12" style="margin: 0px;padding: 0px;">
			<div ng-click="libraryTabSelected()"class="col-md-5 col-md-offset-1 col-xs-5 well" ng-class="{'active':librarySelected}">Library<i class="fa fa-book fa-lg"></i><span ng-bind="userAccount.booksPostedOnExchange.length"></span></div>
			<div ng-click="commentsTabSelected()" class="col-md-5 col-xs-5 well" ng-class="{'active':commentsSelected}">Peer Comments <i class="fa fa-star-o fa-lg"></i><span ng-bind="userAccount.ratingsForUser.length"></span></div>
		</div>

	</div>

	<div class="row" ng-if="librarySelected">
	<div class="row">
    		<hr>
    	</div>
		<div class="row">

			<div id="library" class="col-md-3 col-xs-12 " ng-repeat="book in userAccount.booksPostedOnExchange">
				<div id="outer-box">
					<img class="img-responsive" src="{{book.imgUrl}}">
					<div id="inner-box" class="text-center">
						<button type="button" ng-if="isEditable" class="btn btn-default btn-circle btn-lg" ng-click="removeBook(book)"><i class="glyphicon glyphicon-trash"></i></button>
						<button type="button" ng-if="!isEditable" class="btn btn-default btn-circle btn-lg" ng-click="openExchangeModal(book)"><i class="glyphicon glyphicon-refresh"></i></button>
					</div>
				</div>
			</div>
		</div>
		<div class="row" ng-if="isEditable">
			<span><button type="button" class="btn btn-default btn-circle btn-lg pull-right" ng-click="openPostBookModal()"><i class="glyphicon glyphicon-plus"></i></button></span>
		</div>

	</div>

	<div class="row" ng-if="commentsSelected" id="userCommentsSection">
    	<div class="row">
        		<hr>
        	</div>
    		<div class="row">

                <div ng-repeat="userRating in userAccount.ratingsForUser" class="media">
                		<a class="pull-left" href="#">
                			<img class="media-object" src="{{userRating.commentatorAvatar}}" alt="profile">
                		</a>

                		<div class="media-body">
                                 <h3 ng-bind="userRating.commentatorName"></h3>
                                                            <h4 class="media-heading">Rating   <ng-rate-it ng-model="userRating.rating" read-only="true"></ng-rate-it></h4>

                                                            <h4>Comment:</h4>
                                                            <p ng-bind="userRating.comment"></p>

                         </div>
                 </div>

    		</div>
    	</div>

	<div class="row">
		<hr>
	</div>
</div>