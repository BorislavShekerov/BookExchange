package com.bookexchange.web;

import com.bookexchange.dto.DirectBookExchange;
import com.bookexchange.dto.BookExchangeChain;
import com.bookexchange.dto.frontend.ExchangeAccumulator;
import com.bookexchange.dto.frontend.ExchangeChainAcceptance;
import com.bookexchange.dto.frontend.ExchangeOrder;
import com.bookexchange.dto.User;
import com.bookexchange.exception.BookExchangeInternalException;
import com.bookexchange.service.ExchangeChainService;
import com.bookexchange.service.ExchangePathSuggestionService;
import com.bookexchange.service.ExchangeService;
import com.bookexchange.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by sheke on 11/15/2015.
 */
@Controller
public class ExchangeController {
    private static final Logger LOGGER = Logger.getLogger(ExchangeController.class);

    @Autowired
    ExchangeService exchangeService;
    @Autowired
    ExchangePathSuggestionService exchangePathSuggestionService;
    @Autowired
    ExchangeChainService exchangeChainService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/exchange", method = RequestMethod.GET)
    public String getExchangePage(ModelMap model) {
        return "exchange";
    }

    @RequestMapping(value = "/offers", method = RequestMethod.GET)
    public String getUserOffersPage(ModelMap model) {
        return "offers";

    }

    @RequestMapping(value = "/app/openChainRequestModal", method = RequestMethod.GET)
    public String openChainRequestModal(ModelMap model) {
        return "chainRequestModal";

    }

    @RequestMapping(value = "/app/exchangeChainRequests/initiated", method = RequestMethod.GET)
    public
    @ResponseBody
    ExchangeAccumulator getUserCurrentExchanges(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();

        return exchangeService.getUserCurrentExchanges(userEmail);
    }

    @RequestMapping(value = "/offerExchange", method = RequestMethod.GET)
    public String getOfferExchange(ModelMap model) {
        return "offerExchangeModal";
    }

    @RequestMapping(value = "/app/exchangeOrder", method = RequestMethod.POST)
    public @ResponseBody String recordExchange(@RequestBody ExchangeOrder exchangeOrder, Model model)  {
        try {
            exchangeService.recordExchange(exchangeOrder);
        } catch (BookExchangeInternalException e) {
            return "Failure";
        }

        return "Succes";
    }

    @RequestMapping(value = "/app/exchangeChainOrder", method = RequestMethod.POST)
    public @ResponseBody String recordExchangeChain(@RequestBody List<String> exchangeChain, Model model){
        List<User> usersOnChain = userService.fetchUsersForEmail(exchangeChain);
        exchangeChainService.registerExchangeChain(usersOnChain);
        return "Success";
    }

    @RequestMapping(value = "/app/exploreOptions", method = RequestMethod.POST)
    public
    @ResponseBody
    List<User> exploreExchangeOptions(@RequestBody ExchangeOrder exchangeOrder, Model model) throws BookExchangeInternalException, InterruptedException {
        Thread.sleep(1000);
        return exchangePathSuggestionService.exploreOptions(exchangeOrder);
    }


    @RequestMapping(value = "/app/exchangeChain/userRequest", method = RequestMethod.POST)
    public
    @ResponseBody
    List<User> exploreExchangeOptions(@RequestBody BookExchangeChain exchangeChain, Model model) throws BookExchangeInternalException, InterruptedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();

        return exchangeChainService.getChainDetailsForUser(exchangeChain.getId(), userEmail);
    }

    @RequestMapping(value = "/app/exchangeChain/reject", method = RequestMethod.POST)
    public
    @ResponseBody
    String rejectExchangeChain(@RequestBody BookExchangeChain bookExchangeChain,Model model) throws BookExchangeInternalException, InterruptedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();

        exchangeChainService.rejectExchangeChain(userEmail,bookExchangeChain.getId());
        return "Success";
    }

    @RequestMapping(value = "/app/exchangeChain/accept", method = RequestMethod.POST)
    public
    @ResponseBody
    String acceptExchangeChain(@RequestBody ExchangeChainAcceptance exchangeChainAcceptance, Model model) throws BookExchangeInternalException, InterruptedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();

        exchangeChainService.acceptExchangeChain(userEmail,exchangeChainAcceptance);
        return "Success";
    }



    @RequestMapping(value = "/requests", method = RequestMethod.GET)
    public String getExchangeRequestsPage(ModelMap model) {
        return "requests";
    }
}
