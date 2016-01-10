<div class="side-body" id="main-content">
    <div ng-repeat="bookOnExchange in booksDisplayed " class="col-xs-12 col-sm-6 col-md-4 ">
        <div class="panel panel-default col-xs-12" ng-class="{cardHover: hover}" ng-mouseenter="hover = true" ng-mouseleave="hover = false">
            <img src="{{bookOnExchange.imgUrl}}" alt="{{bookOnExchange.title}}" class="img-responsive full-width" id="bookCoverImg">
            <div class="panel-body">
                <div class="media">
                    <div class="media-left ">
                        <a href="#">
                            <img class="media-object profile-picture" src="{{bookOnExchange.ownerAvatar}}" alt="..." >
                        </a>
                    </div>
                    <div class="media-body">
                        <h4 class="media-heading">{{bookOnExchange.ownedBy}}</h4>
                        <h4 class="media-heading">Exchanges for:</h4>
                    </div>
                </div>
                <ul class="list-inline space-out-top-sm">
                    <li ng-repeat="category in bookOnExchange.ownerCategoriesOfInterest">
                        <span class="label label-default">{{category}}</span></li>
                </ul>
                <div class="space-out-top-sm">
                    <button type="button" class="btn btn-primary" ng-click="initiateExchange(bookOnExchange.title,bookOnExchange.ownedBy)"><span class="glyphicon glyphicon-refresh margin-right-small"></span>Offer Exchange</button>
                </div>

            </div>
        </div>
    </div>
</div>