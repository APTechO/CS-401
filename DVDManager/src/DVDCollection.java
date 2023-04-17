/***
 * Anthony Perez
 * Barbara Hecker
 * CS 401-03
 * Spring 2023
 * 
 **/
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class DVDCollection {

	// Data fields
	/** The current number of DVDs in the array */
	private int numdvds;
	/** The array to contain the DVDs */
	private DVD[] dvdArray;
	/** The name of the data file that contains dvd data */
	private String sourceName;
	/** Boolean flag to indicate whether the DVD collection was
	    modified since it was last saved. */
	private boolean modified;
	/**
	 *  Constructs an empty directory as an array
	 *  with an initial capacity of 7. When we try to
	 *  insert into a full array, we will double the size of
	 *  the array first.
	 */
	public DVDCollection() {
		numdvds = 0;
		dvdArray = new DVD[7];
	}
	
	public String toString() {
		String dvds = "";
		for (int i=0; i<numdvds; i++) {
			dvds += "dvdArray["+i+"] = " + dvdArray[i].toString()+"\n";
		}
		
		String text = "Number of DVDs: " + numdvds +"\n"
					+	"Array length: " + dvdArray.length +"\n"
					+	dvds;

		return text;
	}

	public void addOrModifyDVD(String title, String rating, String runningTime) {
		
		// make title upper case
		title = title.toUpperCase();
		
		int time;
		try {	// Change string runningTime to int time 
			time = Integer.parseInt(runningTime);
		} catch (NumberFormatException e) {
			System.out.println("Invalid runtime. Enter a number.");
			e.printStackTrace();
			return;
		}
		
		// First check to see if title already exists
		for (int i=0; i<numdvds; i++) {
			// get current title at index i
			String currTitle = dvdArray[i].getTitle();
			if (currTitle.compareTo(title) == 0) {
				// if title exists, update rating and runTime
				 System.out.append("Rating and Runtime updated.\n");
				 dvdArray[i].setRating(rating);
				 dvdArray[i].setRunningTime(time);
				 return;
			}
		}
		// Create new DVD with passed in title, rating, and time 
		DVD newDVD = new DVD(title, rating, time);
				
		// Case: Empty Collection
		if (numdvds == 0) {
			dvdArray[numdvds] = newDVD;
			numdvds+=1;
			return;
		}
		// Case: if array is full, copy itself with double the array length
		if (numdvds == dvdArray.length) {
			dvdArray = Arrays.copyOf(dvdArray, dvdArray.length*2);
		}
		// Case: Adding in the middle
		// and in lexicographical order
		for (int i=0; i<numdvds; i++) {
			String currTitle = dvdArray[i].getTitle();
			if (currTitle.compareTo(title)>0) { 
				for (int j=numdvds; j>i; j--) { // starting from the end
					// move elements to the right one spot
					dvdArray[j] = dvdArray[j-1]; 
				}
				// set newDVD at index i
				dvdArray[i] = newDVD;
				numdvds+=1;
				return;
			}
		}
		
		// Case: Adding at the end of collection
		dvdArray[numdvds] = newDVD;
		numdvds+=1;
		return;

	
	}
	
	public void removeDVD(String title) {
		// upper case the title passed in 
		title = title.toUpperCase();
		// helperArray to help removing dvd from original array
		DVD[] helperArray = new DVD[dvdArray.length]; 
		
		// if empty collection
		if (numdvds == 0) {
			System.out.println("Empty collection");
			return;
		}
		
		// loop through collection 
		for (int i=0; i<numdvds; i++) {
			// get current title 
			String currTitle = dvdArray[i].getTitle();
			if (currTitle.compareTo(title)==0) {
//				System.out.println("delete at index: "+i);
				// copy dvdArray to helperArray from the beginning to the index we want to delete
				System.arraycopy(dvdArray, 0, helperArray, 0, i);
				// copy dvdArray to helperArray starting at the dvd after the index we want to delete
				System.arraycopy(dvdArray, i+1, helperArray, i, dvdArray.length - i - 1);
				// set original array to the new copy
				dvdArray = helperArray;
				// decrease the number of dvds
				numdvds-=1;
				return;
			}
		}
		// return if title not found
		return;
	}
	
	public String getDVDsByRating(String rating) {
		// string dvds to build
		String dvds = "";
		for (int i=0; i<numdvds; i++) {
			String currRating = dvdArray[i].getRating();
			if (currRating.compareTo(rating)==0) {
				// if movie found with correct rating 
				// add title to the dvds string
				dvds += dvdArray[i].getTitle() + "\n"; 
			}
		}
		// return the dvds found by rating
		return dvds;

	}

	public int getTotalRunningTime() {
		int collectionRuntime = 0;
		for (int i=0; i<numdvds; i++) {	// summation of all dvds running time
			collectionRuntime += dvdArray[i].getRunningTime();
		}
		return collectionRuntime;
	}

	
	public void loadData(String filename) {
		
		sourceName = filename;	// save filename to global sourceName to be used in save method
		
		try {	// try to load filename into my inputfile
			File inputfile = new File(filename);
			Scanner myScanner = new Scanner(inputfile);	
			while (myScanner.hasNextLine()) {	// scan file line by line
				String line = myScanner.nextLine();
				System.out.println(line); // print dvds as they load in
				String[] partition = new String[3]; // split line into three parts, title/rating/runtime
				partition = line.split("[,/]");	
				addOrModifyDVD(partition[0], partition[1], partition[2]);
			}
			myScanner.close();
		} catch (FileNotFoundException e) {	// file failed to load
			System.out.println("An error occured.");
			e.printStackTrace();
		}
		
	}
	
	public void save() {	
		// write to sourceName file 
		// flag modified
		String dvds = "";
		for (int i=0; i<numdvds; i++) {
			dvds += dvdArray[i].toString()+"\n";
		}
		try {	// try to write to source file
			FileWriter myWriter = new FileWriter(sourceName);
			myWriter.write(dvds); // write dvds string
			myWriter.close();
//			System.out.println("Written to file.");
			
		} catch (IOException e) {
			System.out.println("Error occurred.");
			e.printStackTrace();
		}

	}
	
}
