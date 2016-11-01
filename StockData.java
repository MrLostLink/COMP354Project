/**
 * Created by Kendy on 2016-10-16.
 */
public class StockData {

        String	symbol;
        double	price;
        double	change;
        double	volume;
        long	lastUpdate;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }


    /**
     * @return the volume
     */
    public double getVolume() {

        return volume;
    }

    /**
     * @param volume the volume to set
     */
    public void setVolume(double volume) {

        this.volume = volume;
    }

    public long getLastUpdated() {
        return lastUpdate;
    }

    public void setLastUpdated(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
        
       public String getQuotes(){
        return   this.getSymbol() + "\n" +
        "price : " + this.getPrice() + "\n" +
        "Change : " + this.getChange() + "\n" +
        "Last Updated : " + this.getLastUpdated() + "\n\n";
    }

}
