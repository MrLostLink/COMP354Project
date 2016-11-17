package Comp354;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //Creating array containing the symbol tickers from the DOW 30
        String[] Dow30Ticker = {"AAPL", "AXP", "BA", "CAT", "CSCO", "CVX", "KO", "DD", "XOM", "GE",
                "GS", "HD", "IBM", "INTC", "JNJ", "JPM", "MCD", "MMM", "MRK", "MSFT",
                "NKE", "PFE", "PG", "TRV", "UNH", "UTX", "V", "VZ", "WMT", "DIS"};

        YahooQuoteFetcher fetcher = new YahooQuoteFetcher(5.0);
        MovingAverage historicalFetcher = new MovingAverage(5.0);
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Please select from the stock list below by inputing its corresponding # (1 - 30) \n\n");
        boolean validEntry = false;
        try {
            for (int i = 0; i < Dow30Ticker.length; i++) {
                StockData stockName = fetcher.getData(Dow30Ticker[i]);
                System.out.println(i + 1 + " - " + stockName.getSymbol() + " - " + stockName.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //user Input
        int input = 0;
        int input2 = 0;

        //Validate user entry
        do {
            try {
                System.out.println("\nPlease choose a number from the list");
                input = reader.nextInt();
                if (input <= 30 && input >= 1)
                    validEntry = true;
                else
                    validEntry = false;
            } catch (InputMismatchException exception) {
                validEntry = false;
                System.out.println("This is not an integer");
            }
            catch (Exception e) {
            e.printStackTrace();
            }
        } while (!validEntry);

        //Read for the source itself from URL
        try {
            StockData aStock = fetcher.getData(Dow30Ticker[input - 1]);
            System.out.println(aStock.getQuotes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Validate user entry
        do {
            try {
                StockData stockName = fetcher.getData(Dow30Ticker[input - 1]);
                System.out.println("Would you like to get the historical Data and Moving average for " + Dow30Ticker[input - 1] + " - " + stockName.getName() + " ?\n" + "1. Yes\n" + "2. No\n");
                input2 = reader.nextInt();
                if (input2 == 2 || input2 == 1)
                    validEntry = true;
                else
                    validEntry = false;
            } catch (InputMismatchException exception) {
                System.out.println("This is not an integer, enter 1 or 2");
                validEntry = false;
            } catch (Exception e) {
                e.printStackTrace();
                validEntry = false;
            }

        } while (!validEntry);

        if (input2 == 1) {
            try {
                historicalFetcher.getData(Dow30Ticker[input - 1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            System.exit(0);
    }
}