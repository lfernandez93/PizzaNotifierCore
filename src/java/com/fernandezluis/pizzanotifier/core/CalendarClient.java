/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fernandezluis.pizzanotifier.core;

/**
 *
 * @author Luis Fernandez
 */
public interface CalendarClient<T> {
    public T getCalendar();
}