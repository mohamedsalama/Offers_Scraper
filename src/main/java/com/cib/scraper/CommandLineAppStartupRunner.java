package com.cib.scraper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    private ScrapCIBOffers scrapCIBOffers;

    @Override
    public void run(String... args) throws Exception {
        scrapCIBOffers.scrap();
    }
}
