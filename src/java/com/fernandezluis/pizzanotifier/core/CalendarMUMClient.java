/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fernandezluis.pizzanotifier.core;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Luis Fernandez
 */
public class CalendarMUMClient implements CalendarClient<Calendar> {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "https://clients6.google.com/calendar/v3/calendars/casey739@gmail.com/events?calendarId=casey739%40gmail.com&singleEvents=true&timeZone=America%2FChicago&maxAttendees=1&maxResults=250&sanitizeHtml=true&timeMin=2015-03-01T00%3A00%3A00-06%3A00&timeMax=2015-04-05T00%3A00%3A00-06%3A00&key=AIzaSyBNlYH01_9Hc5S1J9vuFmu2nUqBZJNAXxs";

    public CalendarMUMClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI);
    }

    @Override
    public Calendar getCalendar() {
        return webTarget.request(MediaType.APPLICATION_JSON).get(Calendar.class);
    }

}
