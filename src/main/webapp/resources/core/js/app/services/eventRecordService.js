bookApp.service("eventRecordService", function () {
    var newOfferEvents = 0;
    var selectedItem = "Exchange";

    function recordNewOfferEvents(){
        newOfferEvents ++;
    }

    function resetOfferEvents(){
        resetOfferEvents = 0;
    }

    function getNewOfferEvents(){
        return newOfferEvents;
    }

    function setSelectedItem(item){
        selectedItem = item;
    }

    function getSelectedItem(){
        return selectedItem;
    }
    return {
    		recordNewOfferEvents: recordNewOfferEvents,
    		getNewOfferEvents: getNewOfferEvents,
    		resetOfferEvents:resetOfferEvents,
    		setSelectedItem : setSelectedItem,
            getSelectedItem : getSelectedItem
    	};

});