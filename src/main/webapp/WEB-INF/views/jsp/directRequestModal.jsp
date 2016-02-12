<div class="row chainRequestModal">
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
					<div class="col-md-4" ng-repeat="book in userToChooseFrom.booksPostedOnExchange">

						<div class="effects" id="effect-1">

							<div class="box" style="background:url({{book.imgUrl}}) no-repeat 100% 100%;" ng-mouseenter="imageHoveredOver = true" ng-mouseleave="imageHoveredOver = false" ng-class="{'boxHover':imageHoveredOver}">

								<a href="#">
									<div class="overlay" ng-class="{'overlay-visible':imageHoveredOver}">
										<span class="glyphicon glyphicon-plus search" ng-click="chooseBook(book)"></span>
									</div>
								</a>
								<div id="bookChosenLabel" ng-if="bookChosen">
                                    <h2>{{book.title}} chosen<span class="glyphicon glyphicon-ok-circle pull-right"></span></h2>
								</div>
							</div>


						</div>

					</div>
				</div>

			</div>
			<hr/>
			<div class="row">
				<h3>User choosing from your library :{{userChoosingFromYou.firstName + " "+ userChoosingFromYou.lastName}}</h3>
			</div>
			<div class="row">
				<button class="btn btn-primary pull-right" type="button" ng-click="acceptExchangeRequest()">Accept</button>
				<button class="btn btn-danger pull-left" type="button" ng-click="rejectExchangeRequest()">Reject</button>
			</div>
		</div>




	</div>