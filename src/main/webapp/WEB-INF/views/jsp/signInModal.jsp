<div class="row" id="signUpSignInModal">
	<div class="modal-header">
	    	<button type="button" class="close" ng-click="closeModal()"><span aria-hidden="true">&times;</span></button>
		<h3 class="modal-title text-center">Please Sign In</h3>
	</div>
	<div class="modal-body">
		<div class="row">
			<div class="col-md-10 col-md-offset-1">
				<form action="${loginUrl}" method="post">
					<div class="form-group">
						<input type="text" id="username" name="username" class="form-control" id="exampleInputEmail2" placeholder="Username" />
					</div>
					<div class="form-group">
						<input name="password" type="password" class="form-control" id="passwordInputField" placeholder="Password" />
					</div>
					<div class="row">

					<div class="form-group col-md-6 text-center">
						<input type="checkbox" tabindex="3" class="" name="remember" id="remember">
						<label for="remember"> Remember Me</label>
					</div>
					<div class="form-group col-md-6">
                    							<div class="row">
                    								<div class="col-lg-12">
                    									<div class="text-center">
                    										<a href="http://phpoll.com/recover" tabindex="5" class="forgot-password">Forgot Password?</a>
                    									</div>
                    								</div>
                    							</div>
                    						</div>
					</div>
					<div class="form-group">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<div class="row">
							<div class="text-center">
								<input type="submit" name="login-submit" id="login-submit" tabindex="4" class="ghost-button sign-up-button" value="Sign In">
							</div>
						</div>
						</div>

                    <div class="row">
                                        								<div class="col-lg-12">
                                        									<div class="text-center">
                                        										<a href="/signup" class="forgot-password">Not a user?</a>
                                        									</div>
                                        								</div>
                                        							</div>
				</form>


				</div>
			</div>
		</div>
	</div>
