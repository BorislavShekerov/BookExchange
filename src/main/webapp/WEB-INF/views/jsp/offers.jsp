<div class="side-body" id="main-content">

    <div ng-repeat="bookExchange in userCurrentOffersMade " class="col-xs-12">

           <div class="col-xs-12 col-sm-4 col-md-4 col-md-offset-1">
                <div class="panel panel-default col-xs-12" ng-class="{cardHover: hover}" ng-mouseenter="hover = true" ng-mouseleave="hover = false">
                    <img src="{{bookExchange.bookOfferedInExchange.imgUrl}}" class="img-responsive full-width">
                    <div class="panel-body">
                        <div class="media">
                            <div class="media-left ">
                                <a href="#">
                                    <img class="media-object profile-picture" src="{{bookExchange.bookOfferedInExchange.ownerAvatar}}" alt="...">
                                </a>
                            </div>
                            <div class="media-body">
                                <h4 class="media-heading">{{bookExchange.bookOfferedInExchange.ownedBy}}</h4>
                                <h4 class="media-heading">Exchanges for:</h4>
                            </div>
                        </div>
                        <ul class="list-inline space-out-top-sm">
                            <li ng-repeat="category in bookExchange.bookOfferedInExchange.ownerCategoriesOfInterest">
                                <span class="label label-default">{{category}}</span></li>
                        </ul>
                        <div class="space-out-top-sm">
                            <button type="button" class="btn btn-primary"><span class="glyphicon glyphicon-refresh margin-right-small"></span>Offer Exchange</button>
                        </div>

                    </div>
                </div>
            </div>



              <div class="col-xs-12 col-sm-2 col-md-2">
              <div id="arrow-right-container" class="text-center">
                        <i class="fa fa-arrow-right fa-4x"></i>
                        </div>
               </div>



        <div class="col-xs-12 col-sm-4 col-sm-offset-1 col-md-4 col-md-offset-0">
            <div class="panel panel-default col-xs-12" ng-class="{cardHover: hover}" ng-mouseenter="hover = true" ng-mouseleave="hover = false">
                <img src="{{bookExchange.bookPostedOnExchange.imgUrl}}" class="img-responsive full-width">
                <div class="panel-body">
                    <div class="media">
                        <div class="media-left ">
                            <a href="#">
                                <img class="media-object profile-picture" src="{{bookExchange.bookPostedOnExchange.ownerAvatar}}" alt="...">
                            </a>
                        </div>
                        <div class="media-body">
                            <h4 class="media-heading">{{bookExchange.bookPostedOnExchange.ownedBy}}</h4>
                            <h4 class="media-heading">Exchanges for:</h4>
                        </div>
                    </div>
                    <ul class="list-inline space-out-top-sm">
                        <li ng-repeat="category in bookExchange.bookPostedOnExchange.ownerCategoriesOfInterest">
                            <span class="label label-default">{{category}}</span></li>
                    </ul>
                    <div class="space-out-top-sm">
                        <button type="button" class="btn btn-primary"><span class="glyphicon glyphicon-refresh margin-right-small"></span>Offer Exchange</button>
                    </div>

                </div>
            </div>
        </div>

    </div>
</div>