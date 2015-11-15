package com.bookexchange.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by sheke on 11/15/2015.
 */
@Controller
public class ExchangeController {

    @RequestMapping(value = "/exchange", method = RequestMethod.GET)
    public String getExchange(ModelMap model) {
        return "exchange";

    }

    @RequestMapping(value = "/offers", method = RequestMethod.GET)
    public String getOffersForUset(ModelMap model) {
        return "offers";

    }

    @RequestMapping(value = "/requests", method = RequestMethod.GET)
    public String getRequests(ModelMap model) {
        return "requests";
    }
}
