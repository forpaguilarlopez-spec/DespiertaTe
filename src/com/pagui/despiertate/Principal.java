package com.pagui.despiertate;

import com.pagui.despiertate.base.Cafe;
import com.pagui.despiertate.base.Te;

import java.time.LocalDate;

public class Principal {
    public static void main(String[] args) {

        Cafe c1 = new Cafe("2165165", "Arabic", "África", 10.5, LocalDate.of(2026, 03, 18), 10 );

        Te t1 = new Te("2165215", "Zubrow", "Polonia", 23.4, LocalDate.of(2027,05,23), 25.5, 7);

        System.out.println(c1);
        System.out.println(t1);

    }
}
