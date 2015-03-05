/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fernandezluis.pizzanotifier.core;

import com.fernandezluis.pizzanotifier.domain.Subscriber;
import com.fernandezluis.pizzanotifier.facade.SubscriberFacade;
import com.fernandezluis.pizzanotifier.utils.MailSender;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Luis
 */
@Stateless
public class PizzaMomentNotifier implements Notifier {
    @EJB
    MailSender mailSender;
    @EJB
    SubscriberFacade subsFacade;

    @Override
    public void notifySubscribers(Food pizza, NotificationType notifyType) {
        System.out.println(subsFacade!=null);
        List<Subscriber> subscribers = subsFacade.findAll();
        String body = "";
        String time = "";
        switch (notifyType) {
            case DAILY:
                body = "Dear subscriber today Annapurna is having Pizza at " + pizza.getSummary() + " this \n"
                        + "event is " + pizza.getStatus() + " by the MUM food calendar the menu for this meal will be \n"
                        + "" + pizza.getDescription() + "\n you can check directly at " + pizza.getHtmlLink() + ""
                        + " happy eating! \n\n"
                        + "Pizza Notifier. 2015";
                time="NOW";
                break;
            case MOMENT:
                body = "Pizza now at Annapurna RUN RUN happy eating! ";
                time="TODAY";
                break;
        }
        for (Subscriber subscriber : subscribers) {
            if (subscriber.isSend()) {
                mailSender.sendMail("PizzaNotifier@mum.edu", subscriber.getEmail(), body, "Pizza "+time+" at " + pizza.getSummary());
            }
        }
    }

}
