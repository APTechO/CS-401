// The Stock program is following the MVC design template and this is our controller object.
// The main functionality for buying and selling the stocks are in this controller object.
// This is the ONLY file you may edit

import java.util.LinkedList;
import java.util.Scanner;

public class Controller {
	
	static LinkedList<Stock> mStockList = new LinkedList<Stock>();
	static Scanner input = new Scanner(System.in);
		
	public Controller() {
		
		
		do {
			System.out.print("Enter 1 to buy stock, 2 to sell, 3 to exit. ");
			int controlNum = input.nextInt();

			if(controlNum == 3)
				break;
			if(controlNum == 1) {
					System.out.print("Enter stock name: ");
					String stockName = input.next();
					System.out.print("How many stocks: ");
					int quantity = input.nextInt();
					System.out.print("At what price: ");
					double price = input.nextDouble();
					Controller.buyStock(mStockList, stockName, quantity, price);
					
			}
			else {
				System.out.print("Press 1 for LIFO accounting, 2 for FIFO accounting: ");
				controlNum = input.nextInt();
				if(controlNum == 1) {
					System.out.print("How many stocks to sell: ");
					int quantity = input.nextInt();
					Controller.sellLIFO(mStockList, quantity);
				}
				if(controlNum == 2) {
						System.out.print("How many stocks to sell: ");
						int quantity = input.nextInt();
						Controller.sellFIFO(mStockList, quantity);
				}else
					break;
			}
			
		} while(true);
		input.close();
	}
			
	public static void buyStock(LinkedList<Stock> list, String name, int quantity, double price) {
		Stock temp = new Stock(name,quantity,price);
		
		// push everything onto a stack
		list.push(temp);
		System.out.printf("You bought %d shares of %s stock at $%.2f per share %n", quantity, name, price);
	}
	
	public static void sellLIFO(LinkedList<Stock> list, int numToSell) {
	    // You need to write the code to sell the stock using the LIFO method (Stack)
	    // You also need to calculate the profit/loss on the sale
	    double total = 0; // this variable will store the total after the sale
	    double profit = 0; // the price paid minus the sale price, negative # means a loss
	    System.out.print("Selling price: ");
	    double price = input.nextDouble();
	    // get top element of stack (last in first out)
	    Stock lastIn = (Stock) list.getFirst();
	    double sPrice = lastIn.getPrice();
	    total = (double) numToSell * price;
	    profit = (double) numToSell * (price - sPrice);
	    
		System.out.printf("You sold %d shares of %s stock at %.2f per share %n", numToSell, lastIn.getName(), total/numToSell);
	    System.out.printf("You made $%.2f on the sale %n", profit);
	}
	
	public static void sellFIFO(LinkedList<Stock> list, int numToSell) {
	    // You need to write the code to sell the stock using the FIFO method (Queue)
	    // You also need to calculate the profit/loss on the sale
	    double total = 0; // this variable will store the total after the sale
	    double profit = 0; // the price paid minus the sale price, negative # means a loss
	    // ask for selling price
	    System.out.print("Selling price: ");
	    double price = input.nextDouble();
	    // get last item in stack (first in first out)
	    Stock firstIn = list.getLast();
	    // get price of stock when bought
	    double sPrice = firstIn.getPrice();
	    total = (double) numToSell * price;
	    profit = (double) numToSell * (price - sPrice);
		System.out.printf("You sold %d shares of %s stock at %.2f per share %n", numToSell, firstIn.getName(), total/numToSell);
	    System.out.printf("You made $%.2f on the sale %n", profit);
	}
	
	
}
