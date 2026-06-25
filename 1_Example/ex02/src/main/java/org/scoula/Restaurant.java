package org.scoula;

import jdk.jfr.DataAmount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@DataAmount
public class Restaurant {

    @Autowired
    private Chef chef;

    public Chef getChef() {
        return chef;
    }

}
