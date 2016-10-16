import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;


public class YahooQuoteFetcher {

    private static HashMap<String, StockData> stocks = new HashMap<String, StockData>();
    private long updateInterval;

    /**
     * @param updateIntervalInSeconds
     */
    public YahooQuoteFetcher(double updateIntervalInSeconds) {
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

		/*
		 * Fetch CSV data from Yahoo. The format codes (f=) are:
		 * s=symbol, l1 = last, c1 = change, d1 = trade day, t1 = trade time, o = open, h = high, g = low, v = volume
		 */
        URL ulr = new URL("http://finance.yahoo.com/d/quotes.csv?s=" + symbol + "&f=sl1c1vd1t1ohg&e=.csv");
        URLConnection urlConnection = ulr.openConnection();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                String[] yahooStockInfo = inputLine.split(",");
                StockData stockInfo = new StockData();
                stockInfo.setSymbol(yahooStockInfo[0].replaceAll("\"", ""));
                stockInfo.setPrice(Double.valueOf(yahooStockInfo[1]));
                stockInfo.setChange(Double.valueOf(yahooStockInfo[2]));
                stockInfo.setVolume(Double.valueOf(yahooStockInfo[3]));
                stockInfo.setLastUpdated(System.currentTimeMillis());
                stocks.put(symbol, stockInfo);
                break;
            }
        }
        finally {
            if(reader != null) reader.close();
        }
    }
}