<div id="shortlist-creation-modal">
	<div class="modal-header">
	<button type="button" class="close" ng-click="closeModal()"><span aria-hidden="true">&times;</span></button>
		<h3 class="modal-title">Add Book Genre Preferences</h3>
	</div>
	<div class="modal-body">
		<div id="categoriesInterestedPicker" class="row separate-bottom">
        	<div class="pagination-centered text-center" ng-if="loading"><img src="/resources/core/img/ajax-loader.gif" id="loading-indicator" /> </div>
            <div class="col-xs-12 col-md-10 col-md-offset-1">
            	<a ng-repeat="category in allCategories" ng-class="{'category-button-activated':category.selected}" class="ghost-button offers-received-button" ng-click="addCategoryInterestedIn(category)">
                 {{category.categoryName}}
            	</a>
            </div>
            <div class="col-xs-12">
            	<p ng-show="submitted && categoriesInterestedIn.length == 0" class="help-block warning-text">You must pick at least one book genre.</p>
            </div>
        </div>
		<div class="row">
	    	<button class="ghost-button submit-button pull-right" type="button" ng-click="addPreferredGenres()">Submit</button>
		</div>
	</div>
</div>