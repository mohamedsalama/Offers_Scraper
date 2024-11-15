package com.cib.scraper;

import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ScrapCIBOffers {

    private final OffersRepository offersRepo;

    private static final String BASE_URL = "https://www.cibeg.com/en/personal/offers-and-loyalty/offers?";
    private static final String IMAGE_URL = "https://www.cibeg.com";

    //    @Scheduled(cron = "0 * * * * *") // 0 * * * * * every min
    public void scrap() {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
//        System.setProperty("webdriver.chrome.driver", "/usr/bin/google-chrome");
        System.out.println("--- Start Scrapping ---");

        // change driver to chrome or any other browser you have on your pc
        FirefoxOptions options = new FirefoxOptions();
        // change the browser binary or exe location on your pc
        options.setBinary("/usr/bin/firefox-esr");

        // change FirefoxDriver here to your browser above
        WebDriver driver = new FirefoxDriver(options);

        // launch Fire fox and direct it to the Base URL
        driver.get(BASE_URL);

        WebElement pageInfo = driver.findElement(By.className("page-info"));
        String pagesText = pageInfo.getText();
        String[] pageTextSplit = pagesText.split(" ");
        double totalPages = Double.parseDouble(pageTextSplit[3]);
        int totalPagesInt = (int) Math.ceil(totalPages / 12);

        System.out.println("Result: " + totalPages + " ,No. of pages: " + totalPagesInt);

        for (int i = 1; i <= totalPagesInt; i++) {
            List<Offers> offers = new ArrayList<>();
            driver.get(BASE_URL + "pageno=" + i);
            System.out.println(BASE_URL + "pageno=" + i);

            List<WebElement> listOfOffersCards = driver.findElements(By.className("offer-cards"));

            for (WebElement element : listOfOffersCards) {
                Offers offer = new Offers();

                try {
                    WebElement pictureDiv = element.findElement(By.className("offer-img"));
                    WebElement picture = pictureDiv.findElement(By.tagName("picture"));
                    String imageSrc = picture.getAttribute("data-iesrc");
                    System.out.println("Image Src: " + IMAGE_URL + imageSrc);
                    offer.setImageURL(IMAGE_URL + imageSrc);
                } catch (NoSuchElementException ex) {
                    offer.setImageURL(null);
                }

                List<WebElement> cardDetails = element.findElements(By.className("card-info"));
                cardDetails.forEach(card -> {
                    try {
                        String category = card.findElement(By.tagName("span")).getText();
                        System.out.println("Category: " + category);
                        offer.setCategory(category);
                    } catch (NoSuchElementException ex) {
                        System.out.println("Category not exist, make it others");
                        offer.setCategory("Other");
                    }

                    String installmentAndClientName = card.findElement(By.tagName("h3")).getText();
                    String[] split = installmentAndClientName.split("\n");
                    if (split.length > 1) {
                        System.out.println("1:" + split[0] + " 2:" + split[1]);
                        offer.setInstallment(split[0]);
                        offer.setName(split[1]);
                    } else {
                        System.out.println("1:" + split[0]);
                        offer.setInstallment(split[0]);
                    }

                    String installmentDesc = card.findElement(By.tagName("p")).getText();
                    System.out.println("Installment Desc: " + installmentDesc);
                    offer.setDetails(installmentDesc);
                });

                WebElement cardDateUntil = element.findElement(By.className("card-date"));
                String[] splitDate = cardDateUntil.getText().split(" ");
                String dateValidUntil = splitDate[splitDate.length - 1];
                System.out.println("Date: " + dateValidUntil);
                System.out.println("Date Until: " + cardDateUntil.getText());
                offer.setValidUntilStr(dateValidUntil);
                offer.setValidUntil(this.getDateOnlyByStr(dateValidUntil));

                offer.setOfferId(UUID.randomUUID().toString());
                offer.setCreationDate(new Date());
                offers.add(offer);
                System.out.println(" -- End loop --");
            }

            offersRepo.saveAll(offers);
            System.out.println("Page " + i + " Ended");
        }
        // close Browser
        driver.close();
        System.out.println("--- End Scrapping ---");
    }

    private Date getDateOnlyByStr(String date) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        TimeZone timeZone = TimeZone.getTimeZone("GMT+02:00");
        DateTimeZone dtZone = DateTimeZone.forTimeZone(timeZone);
        DateTime dtus = formatter.parseDateTime(date).withZone(dtZone);
        return dtus.toLocalDateTime().toDate();
    }

}
