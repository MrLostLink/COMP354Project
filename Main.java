/**
 * Created by Kendy on 2016-10-16.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        BufferedReader sampleData = null;

        try {
            //reading from the CSV file provided
            String line;
            sampleData = new BufferedReader(new FileReader("E:\\Comp354\\Comp354project\\Sampledata.csv"));

            // How to read file in java line by line?
            while ((line = sampleData.readLine()) != null) {
                System.out.println("Raw CSV data: " + line);
                System.out.println("Converted ArrayList data: " + LineCSVtoArrayList(line) + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
         } finally {
            try {
                if (sampleData != null) sampleData.close();
           } catch (IOException crunchifyException) {
                crunchifyException.printStackTrace();
            }

       }

        //Read for the source itself from URL
        try {
            YahooQuoteFetcher fetcher = new YahooQuoteFetcher(5.0);
            StockData apple = fetcher.getData("GOOG");
            System.out.println("Symbol of Corporation: " + apple.getSymbol());
            System.out.println("Price of Stock: $" + apple.getPrice());
            System.out.println("Change : $" + apple.getChange());
            System.out.println("Volume : " + apple.getVolume());

            System.out.println("Quote : " + apple.getQuotes());
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

