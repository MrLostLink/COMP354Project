import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Stack;

public class History{

	
	private String filePath = "C:\\Users\\Mande\\workspace\\COMP354Project\\COMP354Project\\data";
	private String fileName = "datalist.bin";
	private FileOutputStream fileOut = null;
	private ObjectOutputStream objOut = null;
	private FileInputStream fileIn = null;
	private ObjectInputStream objIn = null;
	private Stack<StockList> stack;
	private int stackSize;
	final private int MAX_STACK_SIZE = 5;
	
	
	
	@SuppressWarnings("unchecked")
	public History() {
		setStackSize(0);
		
		try {
			fileIn = new FileInputStream(filePath + "\\" + fileName); //adjust this
			objIn = new ObjectInputStream(fileIn);
			System.out.println("Previous search history loaded");
			stack = (Stack<StockList>) objIn.readObject();
		} catch (FileNotFoundException e) {
			System.out.println("No previous search history data saved yet.");
			stack = new Stack<StockList>();
			try {
				System.out.println("New search history file created in the following directory: \n" + 
										filePath + "\\" + fileName);
				saveToFile();
			} catch (Exception e1) {
					e1.printStackTrace();
			}
		} catch (IOException e2){
			e2.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if(!stack.isEmpty())
			setStackSize(stack.size());
		
	}

	public Iterator<StockList> getStackIterator(){
		Iterator<StockList> ite =  stack.iterator();
		return ite;
	}
	public int getStackSize() {
		return stackSize;
	}

	public void setStackSize(int stackSize) {
		this.stackSize = stackSize;
	}
	
	public void saveToFile() throws Exception{
		fileOut = new FileOutputStream(filePath + "\\" + fileName);
		objOut = new ObjectOutputStream(fileOut);
		objOut.writeObject(stack);
	}
	
	public void addToHistory(StockList obj) throws Exception{
		if(stack.size()==MAX_STACK_SIZE){
			stack.pop();
			stack.push(obj);
		}
		else
			stack.push(obj);
		
		saveToFile();
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		Iterator<StockList> sl = stack.iterator();

		while(sl.hasNext()){
			StockList obj = sl.next();
			sb.append(obj.getSymbol()).append(", ").append(obj.getCreatedOn()).append(", ");
			sb.append(obj.getStartDate()).append(", ").append(obj.getEndDate()).append(", ");
			sb.append(obj.getIntervalType());
			sb.append(System.getProperty("line.separator"));
		}
		
		
		return sb.toString();
	}
}
