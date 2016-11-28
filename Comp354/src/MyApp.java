import java.awt.EventQueue;

import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JPanel;
import com.toedter.calendar.JDateChooser;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MyApp {

	private String[] Dow30Ticker = {"AAPL", "AXP", "BA", "CAT", "CSCO", "CVX", "KO", "DD", "XOM", "GE",
            "GS", "HD", "IBM", "INTC", "JNJ", "JPM", "MCD", "MMM", "MRK", "MSFT",
            "NKE", "PFE", "PG", "TRV", "UNH", "UTX", "V", "VZ", "WMT", "DIS"};
	private Stock stock;
	private List<HistoricalQuote> dataList;
	private JFrame frame;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyApp window = new MyApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MyApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("deprecation")
	private void initialize() {
		/*
		 * Overall Frame of Application
		 */
		frame = new JFrame();
		frame.setBounds(100, 100, 780, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		Date todaysDate = new Date();
		todaysDate.getDate();
		Date minDate = new Date(todaysDate.getYear()-5, todaysDate.getMonth(), todaysDate.getDay());
		
		/*
		 * A Label
		 */
		JLabel lblNewLabel = new JLabel("Company Name:");
		lblNewLabel.setBounds(138, 8, 128, 14);
		frame.getContentPane().add(lblNewLabel);
		
		/*
		 * GUI Button to select starting Date
		 */
		JDateChooser dateFrom = new JDateChooser();
		dateFrom.setMinSelectableDate(minDate);
		dateFrom.setBounds(276, 33, 128, 20);
		dateFrom.setMaxSelectableDate(todaysDate);
		frame.getContentPane().add(dateFrom);
		
		/*
		 * GUI Button to select ending Date
		 *  > Action Event: Verifies starting date for any discrepancies 
		 */
		JDateChooser dateUntil = new JDateChooser();
		dateUntil.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dateUntil.setMinSelectableDate(dateFrom.getDate());
			}
		});
		dateUntil.setBounds(276, 64, 128, 20);
		dateUntil.setMaxSelectableDate(todaysDate);
		frame.getContentPane().add(dateUntil);
		
		/*
		 *  A Label
		 */
		JLabel lblFrom = new JLabel("From:");
		lblFrom.setBounds(138, 33, 128, 14);
		frame.getContentPane().add(lblFrom);
		
		/*
		 *  A Label
		 */
		JLabel lblUntil = new JLabel("Until:");
		lblUntil.setBounds(138, 64, 128, 14);
		frame.getContentPane().add(lblUntil);
		
		/*
		 *  List of Combo items corresponding to available companies 
		 */
		JComboBox<ComboItem> companyBox = new JComboBox<ComboItem>();
		companyBox.setEditable(true);
		companyBox.setBounds(276, 5, 128, 20);
		for(String str: Dow30Ticker){
			ComboItem item = new ComboItem(str,str);
			companyBox.addItem(item);
		}
		frame.getContentPane().add(companyBox);
		
		
		JPanel graphPanel = new JPanel();
		graphPanel.setBounds(10, 105, 744, 449);
		frame.getContentPane().add(graphPanel);
		
		JLabel PR = new JLabel("Purchase Recommendation :");
		PR.setBounds(138, 91, 175, 14);
		frame.getContentPane().add(PR);
		
		JLabel Recommendation = new JLabel("");
		Recommendation.setBounds(315, 91, 175, 14);
		frame.getContentPane().add(Recommendation);
		
		/*
		 * Action performed will create Graph based on dataset
		 */
		JButton btnNewButton = new JButton("Calculate");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					int choice = 0;
					Interval selectedInterval = null;
					/*
					 * Everything Date Related; 
					 * 		- Setting variables for start date and end date
					 * 		- Calculating the difference in days to establish whether or not 
					 * 		  the data will be calculated in days or weeks or months (Interval)
					 */
					
					Calendar startDate = new GregorianCalendar();
					startDate.setTime(dateFrom.getDate());
					Calendar endDate = new GregorianCalendar();
					endDate.setTime(dateUntil.getDate());
					
					long diffDays = TimeUnit.MILLISECONDS.toDays(Math.abs(endDate.getTimeInMillis() - startDate.getTimeInMillis()));
						
						//Testing - Console Print
						System.out.println("Start Date: " + startDate.getTime() + "\nEnd Date: " + endDate.getTime() + "\nDifference in Days: " + diffDays);
						
					
					/*
					 * 	Fetching the data using the YahooFinance API
					 */
					
					try {
						stock = YahooFinance.get(companyBox.getSelectedItem().toString());
						if(diffDays<60){
							choice = 1;
							selectedInterval = Interval.DAILY;
							System.out.println("X Axis: Day");
							dataList = stock.getHistory(startDate, endDate, selectedInterval);
						}
						else if(diffDays>60 && diffDays<365){
							choice = 2;
							selectedInterval = Interval.WEEKLY;
							System.out.println("X Axis: Week");
							dataList = stock.getHistory(startDate, endDate, selectedInterval);
						}
						else{
							choice = 3;
							selectedInterval = Interval.MONTHLY;
							System.out.println("X Axis: Month");
							dataList = stock.getHistory(startDate, endDate, selectedInterval);
						}
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(frame,
							    "Make sure you are connected to the Internet.\n" +
							    "Contact your system admin if error continues.", 
							    "An Error has occured.",
							    JOptionPane.ERROR_MESSAGE);
						System.exit(0);
						e1.printStackTrace();
					} 

					StockList currentSL = new StockList(companyBox.getSelectedItem().toString(), todaysDate, startDate.getTime(), endDate.getTime(), selectedInterval);
					DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
					Collections.reverse(dataList);
					
					/*
					 * Insert Data Set Here
					 */
					long totalAmountPre = 0;
					int count = 1;
					for(HistoricalQuote item: dataList){
						System.out.println(item.getDate() + ": " + item.getHigh());
						dataSet.addValue(item.getClose(), "data", Integer.toString(count));
						totalAmountPre += item.getClose().longValue();
						dataSet.addValue(totalAmountPre/count, "Simple Moving Average", Integer.toString(count++));
						currentSL.addToStockList(item);
					}
					
					History history = new History();
					try {
						history.addToHistory(currentSL);
						System.out.println(history.getStackSize());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
					String recommendation = StockRecommendation(stock);
					Recommendation.setText(recommendation);
					
					JFreeChart graph = null;
					/*
					 * Customize Graph Here
					 */
					if(choice == 1){
						graph = ChartFactory.createLineChart("Close Market Price of " + companyBox.getSelectedItem().toString(), "No of Days from Selected Date", "Price ($)", dataSet);
					}
					else if(choice == 2){
						graph = ChartFactory.createLineChart("Close Market Price of " + companyBox.getSelectedItem().toString(), "No of Weeks from Selected Date", "Price ($)", dataSet);
					}
					else if(choice == 3){
						graph = ChartFactory.createLineChart("Close Market Price of " + companyBox.getSelectedItem().toString(), "No of Months from Selected Date", "Price ($)", dataSet);
					}	
					CategoryPlot graphPlot = graph.getCategoryPlot();
					graphPlot.setRangeGridlinePaint(Color.CYAN);
					graphPlot.configureDomainAxes();
					
					ChartPanel graphingPanel = new ChartPanel(graph);
					graphPanel.removeAll();
					graphPanel.add(graphingPanel, BorderLayout.CENTER);
					graphPanel.validate();
					
			}
		});
		btnNewButton.setBounds(406, 8, 89, 76);
		frame.getContentPane().add(btnNewButton);
		
		/*
		 * Implementing the Menu Bar
		 */
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		/*
		 * Corresponds to the File Tab 
		 * 	Current options available: Exit
		 */
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		mnFile.add(mntmExit);
		
		/*
		 * Corresponds to the History Tab
		 * 	Iterates through the last 5 searches and displays them for offline purposes
		 */
		JMenu mnHistory = new JMenu("History");
				History history = new History();
				Iterator<StockList> ite = history.getStackIterator();
				while(ite.hasNext()){
					StockList item = ite.next();
					JMenuItem itemM = new JMenuItem(item.getSymbol() +" - " + item.getCreatedOn());
					itemM.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							System.exit(0);
						}
					});
					mnHistory.add(itemM);
				}
		menuBar.add(mnHistory);
		
		
	}
	
	@SuppressWarnings("unused")
	public static void getData(String input, DefaultCategoryDataset dataSet){
		YahooQuoteFetcher fetcher = new YahooQuoteFetcher(5.0);
        StockData stockName;
        
        try {
			stockName = fetcher.getData(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
    
	}
	
	//recommendation module
	public String StockRecommendation(Stock astock) {
		double priceOfStock = stock.getQuote().getPrice().doubleValue();
		System.out.println("Price of the stock : " + priceOfStock);
		double fiftyDayMA = stock.getQuote().getPriceAvg50().doubleValue();
		System.out.println("50 days moving average : " + fiftyDayMA);
		
		//Formulae from recommendation
		if(priceOfStock < fiftyDayMA - 5.00)
			return "High";
		else if (priceOfStock > fiftyDayMA)
			return "Low";
		else
			return "Medium";
	}
}
