package Comp354;

import java.text.DecimalFormat;
import java.io.BufferedReader;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class MovingAverage {

    private static HashMap<String, StockData> stocks = new HashMap<String, StockData>();
    private long updateInterval;
    int day,startMonth,year,endMonth;
    DecimalFormat decimalFormat = new DecimalFormat("#,##0");
    double movingAverage = 0;
    int numberOfLineInFile = 1;

    /**
     * @param updateIntervalInSeconds
     */
    public MovingAverage(double updateIntervalInSeconds) {
        super();
        this.updateInterval = (long)(updateIntervalInSeconds * 1000.0);
    }


    public StockData getData(String symbol) throws IOException
    {
        updateData(symbol);
        return stocks.get(symbol);
    }

    public synchronized void updateData(String symbol) throws IOException {

        // Check if we need to update
        StockData stockData = stocks.get(symbol);
        if(stockData != null && System.currentTimeMillis() - stockData.getLastUpdated() < updateInterval) return;

        Scanner input = new Scanner(System.in);
		/*
		 * Fetch historical Data data from Yahoo. The format codes (f=) are:
		 * s=symbol, n = name, l1 = last trade price, c1 = change, d1 = trade day, t1 = trade time, o = open, h = high, g = low, v = volume
		 */
		//Get the start date from the user.
        System.out.println("Please Enter the Start Date (mm/dd/yyyy)");
        String startDate;
        String[] startDateSplit;
		do {
            startDate = input.nextLine();
            startDateSplit = startDate.split("/",3);
            startMonth = Integer.parseInt(startDateSplit[0]);
            day = Integer.parseInt(startDateSplit[1]);
            year = Integer.parseInt(startDateSplit[2]);
            System.out.println("Start Date : " + startDateSplit[0] + "/" + startDateSplit[1] + "/" + startDateSplit[2] + "\n");
            if(day > 31 || day < 0 || startMonth < 0 || startMonth > 12 || year > 2016 || startDateSplit[0].contains("[a-zA-Z]+.*") == true || startDateSplit[1].contains("[a-zA-Z]+.*") == true || startDateSplit[2].contains("[a-zA-Z]+.*") == true)
                System.out.println("Not a valid date (<13/<32/yyyy), Please Re-enter Starting Date");
            else {
                startMonth = Integer.parseInt(startDateSplit[0]);
                day = Integer.parseInt(startDateSplit[1]);
                year = Integer.parseInt(startDateSplit[2]);
            }
            }while(day > 31 || day < 0 || startMonth < 0 || startMonth > 12 || year > 2016 || startDateSplit[0].contains("[a-zA-Z]+.*") == true || startDateSplit[1].contains("[a-zA-Z]+.*") == true || startDateSplit[2].contains("[a-zA-Z]+.*") == true);

        //get end date from user.
        System.out.println("Please Enter the End Date (mm/dd/yyyy)");
        String endDate;
        String[] endDateSplit;
            do {
                endDate = input.nextLine();
                endDateSplit = endDate.split("/",3);
                endMonth = Integer.parseInt(endDateSplit[0]);
                day = Integer.parseInt(endDateSplit[1]);
                year = Integer.parseInt(endDateSplit[2]);
                System.out.println("End Date : " + endDateSplit[0] + "/" + endDateSplit[1] + "/" + endDateSplit[2] + "\n");
                if(day > 31 || day < 0 || endMonth < 0 || endMonth > 12 ||  year > 2016 || endDateSplit[0].contains("[a-zA-Z]+.*") == true || endDateSplit[1].contains("[a-zA-Z]+.*") == true || endDateSplit[2].contains("[a-zA-Z]+.*") == true )
                    System.out.println("Not a valid date (<13/<32/yyyy), Please Re-enter End Date");
                else {
                    endMonth = Integer.parseInt(endDateSplit[0]);
                    day = Integer.parseInt(endDateSplit[1]);
                    year = Integer.parseInt(endDateSplit[2]);
                }
        }while(day > 31 || day < 0 || endMonth < 0 || endMonth > 12 ||  year > 2016 || endDateSplit[0].contains("[a-zA-Z]+.*") == true || endDateSplit[1].contains("[a-zA-Z]+.*") == true || endDateSplit[2].contains("[a-zA-Z]+.*") == true);


        BufferedReader sampleData = null;
        try {
            //reading historical Data the CSV file provided
            URL url = new URL("http://real-chart.finance.yahoo.com/table.csv?s=" + symbol + "&a=" + String.valueOf(startMonth - 1) + "&b=" + startDateSplit[1] + "&c="+ startDateSplit[2] + "&d="+ String.valueOf(endMonth - 1) +"&e=" + endDateSplit[1] + "&f="+ endDateSplit[2] + "&g=d&ignore=.csv");
            URLConnection urlConnection = url.openConnection();

            String line;
            String firstLine;
            sampleData = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            // read file in java line by line
            firstLine = sampleData.readLine();
            while ((line = sampleData.readLine()) != null) {
                String[] yahooStockInfo = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                movingAverage += Double.valueOf(yahooStockInfo[4]);
                System.out.println("Categories: " + firstLine);
                System.out.println("data: " + LineCSVtoArrayList(line));
                System.out.println("Moving average: " +  CalculateMovingAverage(movingAverage, numberOfLineInFile) + "\n");
                numberOfLineInFile++;
            }

            System.out.println("\n" + "Final Moving average: " +  CalculateMovingAverage(movingAverage, numberOfLineInFile - 1) + "\n");


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (sampleData != null) sampleData.close();
            } catch (IOException crunchifyException) {
                crunchifyException.printStackTrace();
            }

        }

    }
    // Calculate Moving Average.
    public double CalculateMovingAverage (double SMA, double numberOfTradingDays){
        double MA;
        if(numberOfTradingDays == 0)
            return 0;
        else {
            MA = SMA / numberOfTradingDays;
            return MA;
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

