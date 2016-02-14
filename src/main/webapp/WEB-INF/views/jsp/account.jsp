<div class="side-body" id="main-content">
	<div class="row panel" id="accountSection">
		<div class="col-md-4 bg_blur ">
		</div>
		<div class="col-md-8  col-xs-12">
			<img src="../{{userAccount.avatarUrl}}" class="img-thumbnail picture hidden-xs" />
			<img src="../{{userAccount.avatarUrl}}" class="img-thumbnail visible-xs picture_mob" />
			<div class="header">
				<h1 ng-bind="userAccount.fullName"></h1>
				<h4>Genres Interested In:</h4>
				<span ng-repeat="category in userAccount.categoriesInterestedIn " class="space-out-top-sm space-out-right-sm">
                                                        <span class="tag label label-info">
                                                        <span ng-bind="category.categoryName"></span>
				<a ng-click="removeCategoryInterestedIn(category)"><i class="remove glyphicon glyphicon-remove-sign glyphicon-white"></i></a>
				</span>

				</span>
				<span><button type="button" ng-click="openAddPreferredCategoriesModal()" class="btn btn-default btn-circle btn-lg"><i class="glyphicon glyphicon-plus"></i></button></span>

			</div>
		</div>
	</div>

	<div class="row" id="accountSectionSelectors">
		<div class="col-md-4"></div>
		<div class="col-md-8 col-xs-12" style="margin: 0px;padding: 0px;">
			<div class="col-md-5 col-md-offset-1 col-xs-5 well" ng-class="{'active':selectorClicked == 'library'}">Library<i class="fa fa-book fa-lg"></i><span ng-bind="userAccount.booksPostedOnExchange.length"></span></div>
			<div class="col-md-5 col-xs-5 well">Books Favoured <i class="fa fa-heart-o fa-lg"></i>5</div>
		</div>

	</div>
	<div class="row">
		<hr>
	</div>
	<div class="row">
		<div class="row">

			<div id="library" class="col-md-3 col-xs-12 " ng-repeat="book in userAccount.booksPostedOnExchange">
				<div id="outer-box">
					<img class="img-responsive" src="{{book.imgUrl}}">
					<div id="inner-box" class="text-center">
						<button type="button" class="btn btn-default btn-circle btn-lg" ng-click="removeBook(book)"><i class="glyphicon glyphicon-trash"></i></button>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<span><button type="button" class="btn btn-default btn-circle btn-lg pull-right" ng-click="openPostBookModal()"><i class="glyphicon glyphicon-plus"></i></button></span>
		</div>

	</div>

	<div class="row">
		<hr>
	</div>
</div>