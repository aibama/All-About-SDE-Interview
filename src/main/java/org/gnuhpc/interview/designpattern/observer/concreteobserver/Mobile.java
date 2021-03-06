package org.gnuhpc.interview.designpattern.observer.concreteobserver;

import org.gnuhpc.interview.designpattern.observer.observer.Observer;
import org.gnuhpc.interview.designpattern.observer.concreteobserverable.Stock;

public class Mobile implements Observer {

    @Override
    public void update(Stock stock) {
        System.out.println("MOBILE - The Price of " + stock.getName() + " has changed:" + stock.getPrice());
    }

}
