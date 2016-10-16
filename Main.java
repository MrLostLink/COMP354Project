/**
 * Created by Kendy on 2016-10-16.
 */
public class Main {

    public static void main(String[] args)
    {
        try {
            YahooQuoteFetcher fetcher = new YahooQuoteFetcher(5.0);
            StockData apple = fetcher.getData("AAPL");
            System.out.println("Symbol of Corporation: "+ apple.getSymbol());
            System.out.println("Price of Stock: $"+ apple.getPrice());
            System.out.println("Change : $"+ apple.getChange());
            System.out.println("Volume : " + apple.getVolume());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
