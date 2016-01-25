<div id="shortlist-creation-modal">
	<div class="modal-header">
		<h3 class="modal-title">Add Book Genre Preferences</h3>
	</div>
	<div class="modal-body">
		<div id="categoriesInterestedPicker" class="row">
        	<div class="pagination-centered text-center" ng-if="loading"><img src="/resources/core/img/ajax-loader.gif" id="loading-indicator" /> </div>
            <div class="col-xs-12 col-md-6">
            	<select id="categories-wanted-select" multiple="multiple" class="space-out-top-sm">
            	</select>
            </div>
            <div class="col-xs-12 col-md-6">
            	<div ng-repeat="category in categoriesInterestedIn " class="row space-out-top-sm space-out-left">
            		<span class="tag label label-info">
                    <span>{{category}}</span>
            		<a ng-click="removeCategory(category)"><i class="remove glyphicon glyphicon-remove-sign glyphicon-white"></i></a>
            		</span>
            	</div>
            </div>
            <div class="col-xs-12">
            	<p ng-show="submitted && categoriesInterestedIn.length == 0" class="help-block warning-text">You must pick at least one book genre.</p>
            </div>
        </div>
		<div class="row">
	    	<button class="btn btn-primary pull-right" type="button" ng-click="addPreferedGenres()">OK</button>
		</div>
	</div>
</div>