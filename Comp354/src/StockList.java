import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class StockList implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6741173894122902476L;
	private Date createdOn;
	private Date startDate;
	private Date endDate;
	private Interval intervalType;
	private ArrayList<StockData> stockList;
	private String symbol;
	
	public StockList(String symbol, Date createdOn, Date startDate, Date endDate, Interval intervalType) {
		stockList = new ArrayList<StockData>();
		this.createdOn =createdOn;
		this.startDate =startDate;
		this.endDate =endDate;
		this.intervalType = intervalType;
		this.symbol = symbol;
	}
	
	public void addToStockList(HistoricalQuote obj){
		StockData data = new StockData(obj.getSymbol(), obj.getHigh().doubleValue(), obj.getLow().doubleValue(), obj.getClose().doubleValue(), obj.getOpen().doubleValue(), obj.getVolume().longValue(), obj.getDate().getTime());
		stockList.add(data);
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public Interval getIntervalType() {
		return intervalType;
	}

	public ArrayList<StockData> getStockList() {
		return stockList;
	}

	public String getSymbol() {
		return symbol;
	}
	
	
}
