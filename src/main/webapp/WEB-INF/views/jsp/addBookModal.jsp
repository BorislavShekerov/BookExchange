<!-- Add Book Modal -->
<div class="row" id="bookAddModal">
	<div class="modal-header">
		<button type="button" class="close" ng-click="closeModal()"><span aria-hidden="true">&times;</span></button>
		<h4 class="modal-title" id="myModalLabel">Adding a new Book</h4>
	</div>
	<div class="modal-body">
		<div class="row">
			<div class="col-md-10 col-md-offset-1">
				<form name="addBookForm" class="form-horizontal" role="form" ng-submit="submitForm(addBookForm.$valid)" novalidate>
					<fieldset>

						<!-- Form Name -->
						<legend>Book Details</legend>

						<!-- Text input-->

						<div class="col-sm-10 col-sm-offset-1">
							<label class="col-sm-2 control-label" for="bookTitle">Title</label>
							<div class="col-sm-8">
								<input id="bookTitle" name="bookTitle" type="text" placeholder="Book Title" class="form-control" ng-model="bookTitle" required>
							</div>
							<p ng-show="(formSubmitted && addBookForm.bookTitle.$invalid) || (addBookForm.bookTitle.$invalid && !addBookForm.bookTitle.$pristine)" class="help-block">Book title is required.</p>
						</div>


						<!-- Text input-->

						<div class="col-sm-10 col-sm-offset-1">
							<label class="col-sm-2 control-label" for="bookAuthor">Author</label>
							<div class="col-sm-8">
								<input type="text" name="bookAuthor" placeholder="Book Author" class="form-control" ng-model="bookAuthor" required>
							</div>
							<p ng-show="(formSubmitted && addBookForm.bookAuthor.$invalid) || (addBookForm.bookAuthor.$invalid && !addBookForm.bookAuthor.$pristine)" class="help-block">Book Author is required.</p>
						</div>


						<!-- Text input-->
						<div class="col-sm-10 col-sm-offset-1">
							<h4>Pick Book Category</h4>
							<div>
								<a class="btn btn-primary" ng-click="addCategory(category)" ng-repeat="category in allCategories">{{category.categoryName}}</a>
							</div>
							<p ng-show="formSubmitted && !categorySelected" class="help-block">Book Author is required.</p>
						</div>


						<!-- Text input-->
						<div class="col-sm-10 col-sm-offset-1">
							<label class="col-sm-3 control-label" for="textinput">Cover Image</label>

							<div class="col-sm-9" ng-class="{'col-sm-offset-2':showBookCoverImage}">
								<input ng-model="imgUrl" id="avatarUploadInput" name="picture" role="uploadcare-uploader" data-image-shrink="1024x1024" />
								<p ng-show="formSubmitted && !bookCoverImgSelected" class="help-block">Please upload a book cover image</p>
							</div>
							<div class="row" ng-show="bookCoverImgSelected">
								<img src="{{bookCoverImg}}" alt="Book Cover" id="bookCoverImage">
							</div>
						</div>

						<button type="submit" class="btn btn-primary">Add Book</button>
				</form>
			</div>
			<!-- /.col-lg-12 -->
		</div>
		<!-- /.row -->
	</div>
</div>