<div id="shortlist-creation-modal">
	<div class="modal-header">
	<button type="button" class="close" ng-click="closeModal()"><span aria-hidden="true">&times;</span></button>
		<h3 class="modal-title">Add Book Genre Preferences</h3>
	</div>
	<div class="modal-body">
		<div id="categoriesInterestedPicker" class="row">
        	<div class="pagination-centered text-center" ng-if="loading"><img src="/resources/core/img/ajax-loader.gif" id="loading-indicator" /> </div>
            <div class="col-xs-12 col-md-6">
            	<button ng-repeat="category in allCategories" ng-click="addCategoryInterestedIn(category)">
                 {{category.categoryName}}
            	</button>
            </div>
            <div class="col-xs-12">
            	<p ng-show="submitted && categoriesInterestedIn.length == 0" class="help-block warning-text">You must pick at least one book genre.</p>
            </div>
        </div>
		<div class="row">
	    	<button class="btn btn-primary pull-right" type="button" ng-click="addPreferredGenres()">Submit</button>
		</div>
	</div>
</div>