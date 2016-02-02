bookApp.service("eventRecordService", function () {
    var newOfferEvents = 0;

    function recordNewOfferEvents(){
        newOfferEvents ++;
    }

    function resetOfferEvents(){
        resetOfferEvents = 0;
    }

    function getNewOfferEvents(){
        return newOfferEvents;
    }

    return {
    		recordNewOfferEvents: recordNewOfferEvents,
    		getNewOfferEvents: getNewOfferEvents,
    		resetOfferEvents:resetOfferEvents
    	};

});