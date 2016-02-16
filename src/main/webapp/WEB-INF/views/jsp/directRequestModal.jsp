<div class="row" id="directRequestModal">
	<div class="modal-header">
		<h3 class="modal-title text-center">Answer Direct Request</h3>
	</div>
	<div class="modal-body">
		<div>
			<div class="row">
				<h2 class="text-center"><strong>{{userToChooseFrom.fullName + "'s library:"}}</strong></h2>
			</div>
			<div class="row">

				<div class="col-md-12">

					<div id="library" class="col-md-4 col-xs-12 " ng-repeat="book in userToChooseFrom.booksPostedOnExchange">
						<div id="outer-box">
							<img class="img-responsive" src="{{book.imgUrl}}">
							<div id="inner-box" ng-if="!book.selected" class="text-center">
								<button type="button" class="btn btn-default btn-circle btn-lg" ng-click="chooseBook(book)"><i class="glyphicon glyphicon-ok"></i></button>
							</div>

							<div id="inner-box-book-selected" ng-if="book.selected" class="text-center">

                            </div>

                            <div id="inner-box-book-selected-content" ng-if="book.selected" class="text-center">
                            <p><span class="glyphicon glyphicon-remove" ng-click="deselectBook(book)"></span></p>
                            							<h4> Selected <span class="glyphicon glyphicon-ok"></h4>
                                                        </div>
						</div>
					</div>

				</div>
			</div>

		</div>
		<hr/>

		<div class="row">
			<button ng-class="{'disabled-button':!bookChosen}" class="ghost-button  offers-received-button pull-right" type="button" ng-disabled="!bookChosen" ng-click="acceptExchangeRequest()">Accept</button>
			<a class="ghost-button  reject-offer-button  pull-left" type="button" ng-click="rejectExchangeRequest()">Reject</a>
		</div>
	</div>

</div>