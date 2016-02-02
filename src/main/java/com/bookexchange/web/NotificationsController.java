package com.bookexchange.web;

import com.bookexchange.dto.Notification;
import com.bookexchange.exception.BookExchangeInternalException;
import com.bookexchange.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by sheke on 1/31/2016.
 */
@Controller
public class NotificationsController {
    @Autowired
    NotificationService notificationService;

    @RequestMapping(value = "/app/notifications/newNotificationsCheck", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Notification> checkForNewChainRequests(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();

        return notificationService.getAllNewNotificationsForUser(userEmail);
    }

    @RequestMapping(value = "/app/notifications/marAsSeen", method = RequestMethod.POST)
    public
    @ResponseBody
    String markNotificationAsSeen(@RequestBody Notification notification,Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();


        try {
            notificationService.markNotificationAsSeen(notification);
            return "Success";
        } catch (BookExchangeInternalException e) {
            return "Failure";
        }
    }
}
