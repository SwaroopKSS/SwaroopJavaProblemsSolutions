package com.swaroop.personal;

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class AccountDetails {
    private String accountId;
    private List<OfferDetails> offerDetailsList;

    // Constructor, getters, and setters

    public String getAccountId() {
        return accountId;
    }

    public AccountDetails(String accountId) {
        this.accountId = accountId;
    }

    public AccountDetails(String accountId, List<OfferDetails> offerDetailsList) {
        this.accountId = accountId;
        this.offerDetailsList = offerDetailsList;
    }

    public List<OfferDetails> getOfferDetailsList() {
        return offerDetailsList;
    }

    public void setOfferDetailsList(List<OfferDetails> offerDetailsList) {
        this.offerDetailsList = offerDetailsList;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "Account ID: " + accountId + "\nOffer Details: " + offerDetailsList;
    }
}

class OfferDetails {
    private List<Redemption> redemptionList;

    // Constructor, getters, and setters

    @Override
    public String toString() {
        return redemptionList.toString();
    }
}

class Redemption {
    private double amount;
    private String date;

    // Constructor, getters, and setters

    @Override
    public String toString() {
        return "Amount: " + amount + ", Date: " + date;
    }
}

public class Main {
    public static void main(String[] args) {
        // Create sample data
        AccountDetails account1 = new AccountDetails();
        account1.setAccountId("A1");

        OfferDetails offer1 = new OfferDetails();
        offer1.setRedemptionList(Arrays.asList(
                new Redemption(500.0, "2023-05-15T08:30:00"),
                new Redemption(1000.0, "2023-05-10T10:45:00")
        ));

        OfferDetails offer2 = new OfferDetails();
        offer2.setRedemptionList(Arrays.asList(
                new Redemption(200.0, "2023-06-01T14:20:00"),
                new Redemption(800.0, "2023-05-28T11:10:00"),
                new Redemption(1200.0, "2023-05-30T09:00:00")
        ));

        account1.setOfferDetailsList(Arrays.asList(offer1, offer2));

        AccountDetails account2 = new AccountDetails();
        account2.setAccountId("A2");

        OfferDetails offer3 = new OfferDetails();
        offer3.setRedemptionList(Arrays.asList(
                new Redemption(300.0, "2023-05-18T16:45:00"),
                new Redemption(600.0, "2023-05-22T12:30:00"),
                new Redemption(900.0, "2023-05-20T15:15:00")
        ));

        account2.setOfferDetailsList(Arrays.asList(offer3));

        // Create a list of AccountDetails
        List<AccountDetails> accountList = Arrays.asList(account1, account2);

        // Sort the list based on the most recent redemption date within each OfferDetails
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        accountList.forEach(account -> {
            account.getOfferDetailsList().forEach(offer -> {
                offer.getRedemptionList().sort((r1, r2) -> {
                    LocalDateTime date1 = LocalDateTime.parse(r1.getDate(), formatter);
                    LocalDateTime date2 = LocalDateTime.parse(r2.getDate(), formatter);
                    return date2.compareTo(date1); // Compare in reverse order for most recent date
                });
            });
            account.getOfferDetailsList().sort((o1, o2) -> {
                LocalDateTime date1 = LocalDateTime.parse(o1.getRedemptionList().get(0).getDate(), formatter);
                LocalDateTime date2 = LocalDateTime.parse(o2.getRedemptionList().get(0).getDate(), formatter);
                return date2.compareTo(date1); // Compare in reverse
