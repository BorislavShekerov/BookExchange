bookApp.service("eventRecordService", function () {
    var newOfferEvents = 0;
    var newRequestEvents = 0;
    var selectedItem = "Exchange";

    function recordNewOfferEvents(){
        newOfferEvents ++;
    }

    function recordNewNotification(notification){
        if(notification.notificationType == 'EXCHANGE_CHAIN_REJECTION' || notification.notificationType == 'EXCHANGE_CHAIN_ACCEPTANCE' || notification.notificationType == 'EXCHANGE_CHAIN_SUCCESS' || notification.notificationType == 'DIREXT_EXHANGE_REJECTED' || notification.notificationType == 'DIRECT_EXCHANGE_ACCEPTED'){
            newOfferEvents++;
        }else if(notification.notificationType == 'EXCHANGE_CHAIN_INVITATION' || notification.notificationType == 'DIRECT_EXCHANGE_INVITATION'){
            newRequestEvents++;
        }
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

    function resetRequestEvents(){
        newRequestEvents = 0;
    }
    function getSelectedItem(){
        return selectedItem;
    }

    function getNewRequestEvents(){
        return newRequestEvents;
    }
    return {
    		recordNewOfferEvents: recordNewOfferEvents,
    		getNewOfferEvents: getNewOfferEvents,
    		resetOfferEvents:resetOfferEvents,
    		setSelectedItem : setSelectedItem,
    		getSelectedItem: getSelectedItem,
            getNewRequestEvents : getNewRequestEvents,
            resetRequestEvents: resetRequestEvents,
            recordNewNotification:recordNewNotification
    	};

});