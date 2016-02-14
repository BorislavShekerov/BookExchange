bookApp.service("searchService", function () {
      var searchInNavBarVisible = false;
      var searchPhrase = "";

      function setSearchNavBarVisible(visibility){
        searchInNavBarVisible = visibility;
      }

      function getSearchNavBarVisible(){
        return searchInNavBarVisible;
      }

        function setSearchPhrase(newSearchPhrase){
            searchPhrase = newSearchPhrase;
        }

        function getSearchPhrase(){

            return searchPhrase;

        }
      return {
        		setSearchNavBarVisible : setSearchNavBarVisible,
        		getSearchNavBarVisible: getSearchNavBarVisible,
        		setSearchPhrase : setSearchPhrase,
        		getSearchPhrase : getSearchPhrase
        	};

});