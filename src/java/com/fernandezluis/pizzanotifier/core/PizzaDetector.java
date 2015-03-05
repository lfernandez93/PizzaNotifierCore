/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fernandezluis.pizzanotifier.core;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luis Fernandez
 */
public class PizzaDetector implements FoodDetector {

    private List<Food> allFood;

    public PizzaDetector(List<Food> food) {
        allFood = food;
    }

    @Override
    public List<Food> desiredFood() {
        List<Food> pizza = new ArrayList<>();
        for (Food food : allFood) {
            if (food.getDescription().contains("Greek Pizza")) {
            } else {
                if (food.getDescription().contains("Pizza")) {
                    pizza.add(food);
                }
            }
        }
        return pizza;
    }

}
