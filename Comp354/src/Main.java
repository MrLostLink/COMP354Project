/**
 * Created by Kendy on 2016-10-16.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //Creating array containing the symbol tickers from the DOW 30
        String[] Dow30Ticker = {"AAPL", "AXP", "BA", "CAT", "CSCO", "CVX", "KO", "DD", "XOM", "GE",
                "GS", "HD", "IBM", "INTC", "JNJ", "JPM", "MCD", "MMM", "MRK", "MSFT",
                "NKE", "PFE", "PG", "TRV", "UNH", "UTX", "V", "VZ", "WMT", "DIS"};


        YahooQuoteFetcher fetcher = new YahooQuoteFetcher(5.0);
        StockData stockName;

        System.out.println("Please select from the stock list below by inputing its corresponding # (1 - 30) \n\n");
        try {
            for (int i = 0; i < Dow30Ticker.length; i++) {
                stockName = fetcher.getData(Dow30Ticker[i]);
                System.out.println(i + 1 + " - " + Dow30Ticker[i] + " - " + stockName.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scanner reader = new Scanner(System.in);  // Reading from System.in
        int input = reader.nextInt();

//        BufferedReader sampleData = null;
//
//        try {
//            //reading from the CSV file provided
//            String line;
//            sampleData = new BufferedReader(new FileReader("F:\\Comp354\\Comp354project\\Sampledata.csv"));
//
//            // How to read file in java line by line?
//            while ((line = sampleData.readLine()) != null) {
//                System.out.println("Raw CSV data: " + line);
//                System.out.println("Converted ArrayList data: " + LineCSVtoArrayList(line) + "\n");
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//         } finally {
//            try {
//                if (sampleData != null) sampleData.close();
//           } catch (IOException crunchifyException) {
//                crunchifyException.printStackTrace();
//            }
//
//       }

        //Read for the source itself from URL
        try {
            StockData aStock = fetcher.getData(Dow30Ticker[input - 1]);
            System.out.println(aStock.getQuotes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        // Utility which converts CSV to ArrayList using Split Operation
        public static ArrayList<String> LineCSVtoArrayList (String LineCSV){
            ArrayList<String> crunchifyResult = new ArrayList<String>();

            if (LineCSV != null) {
                String[] splitData = LineCSV.split("\\s*,\\s*");
                for (int i = 0; i < splitData.length; i++) {
                    if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
                        crunchifyResult.add(splitData[i].trim());
                    }
                }
            }
            return crunchifyResult;
        }


}
