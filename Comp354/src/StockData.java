import java.text.DecimalFormat;
/**
 * Created by Kendy on 2016-10-16.
 */
public class StockData {

    String	symbol;
    String name;
    double	price;
    double	change;
    long	volume;
    long	lastUpdate;
    DecimalFormat decimalFormat = new DecimalFormat("#,##0");

    public StockData(){}
    
    public StockData(String symbol, String name, double price, double change, long volume, long lastUpdate) {
		super();
		this.symbol = symbol;
		this.name = name;
		this.price = price;
		this.change = change;
		this.volume = volume;
		this.lastUpdate = lastUpdate;
	}

	public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public void setVolume(long volume) {

        this.volume = volume;
    }

    public long getLastUpdated() {
        return lastUpdate;
    }

    public void setLastUpdated(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getQuotes(){
        return   "Ticker Symbol: " + this.getSymbol() + "\n" +
                "Company name: " + this.getName() + "\n" +
                "Price of Stock: $"  + this.getPrice() + "\n" +
                "Change : " + this.getChange() + "\n" +
                "Volume : " + decimalFormat.format(this.getVolume()) + "\n\n";
    }

}