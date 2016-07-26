//C1365495
//Program matches testbed output for all three data files.

import java.io.*;
import java.util.*;

public class SMMHeap{
	public String[] data;
	public int[] ints;
	public int[] hData;
	public static void main(String[] args){
		SMMHeap heap1 = new SMMHeap();
		heap1.file(args[0]);
	}

	public void file(String fileName){
		try {
			Scanner dataFile = new Scanner(new File(fileName));
			while ( dataFile.hasNextLine() ) {
				String line = dataFile.nextLine(); // Get a line of the text file
				data = line.split(" "); // Separate the line into separate numbers and add to the data array
				System.out.print("input data: ");
				for (int i = 0; i<data.length; i++){//print array
					System.out.print(data[i] + " ");
				}
			}
			ints = new int[data.length];
				for (int i=0; i < data.length; i++) { //changes string array into int array
					ints[i] = Integer.parseInt(data[i]);
			}
			dataFile.close();//close data file
		}
		catch ( Exception e ) {
			System.out.println( e );
		}

		System.out.println("");
		heap(ints); //calls the method to add the base tier elements to the heap
		swapBase(hData); //calls the base tier sibling swap method

		int n = (((ints.length + 2)/2)/2); //parameters for adding the next tier of the heap
		int x = (ints.length + 2)/2;
		nextLevel(hData, ints, n, x); //calls the method to add an additional tier of elements to the heap
		swap2(hData); //calls the second tier sibling swap method

		if (hData.length > 7){ //for data sets with more than 2 layers
			n = ((((ints.length + 2)/2)/2)/2); //parameters for adding the next tier of the heap
			x = (((ints.length)/2) + ((((ints.length))/2)/2)+2);
			nextLevel(hData, ints, n, x); //calls the method to add an additional tier of elements to the heap
			swap3(hData); //calls the third tier sibling swap method

			if (hData.length > 15){ //for data sets with more than 3 layers
				n = (((((ints.length + 2)/2)/2)/2)/2); //parameters for adding the next tier of the heap
				x = ((ints.length) - ((((ints.length)/3)/5)));
				nextLevel(hData, ints, n, x); //calls the method to add an additional tier of elements to the heap
				swap4(hData); //calls the fourth tier sibling swap method
			}
		}

		minDel(hData);//calls the delete minimum method
		maxDel(hData);//calls the delete maximum method
		swapBase(hData);//calls the lower tier sibling swap method
	}


	public int[] heap(int[] ints){
		hData = new int[ints.length +1]; //creates the heap with an additional space for the root
		for (int i=0; i < hData.length; i++){ //sets the heap to -1
			hData[i] = -1;
		}
		for (int i = 0; i < (ints.length + 2)/2; i++){ //adds the base tier elements to the heap
			hData[i + ((ints.length + 2)/2)-1] = ints[i];
		}
		System.out.print("heap array: ");
		for (int i = 0; i<hData.length; i++){
			System.out.print(hData[i] + " ");
		}
		System.out.println("");
		return(hData);
	}

	public int[] swapSib(int[] hData, int i){//sibling swap method
		int temp;
		if (hData[i] > hData[i+1] & hData[i] != -1 & hData[(i+1)] != -1){ // checks if sibilings need to be swapped and ignores any -1 values
			temp = hData[i];
			hData[i]=hData[i+1];
			hData[i+1] = temp;
			System.out.println("swap sibling");
			System.out.print("heap array: ");
			for (int j = 0; j<hData.length; j++){
				System.out.print(hData[j] + " ");
			}
			System.out.println("");
		}
		return(hData);
	}

	public int[] swapBase(int[] hData){
		for (int i = ((hData.length/2)-1); i < hData.length-1; i++){ //loops through base tier elements
			if ((i) % 2 != 0){
				swapSib(hData, i);
			}
		}
		return(hData);
	}

	public int[] nextLevel(int[] hData, int[] ints, int n, int x){
		System.out.println("now process next level");
		System.out.print("heap array: ");
		for (int i = 0; i < n; i++){
			hData[i + (n-1)] = ints [i + x];
		}
		for (int i = 0; i<hData.length; i++){
			System.out.print(hData[i] + " ");
		}
		System.out.println("");
		return(hData);
	}

	public int[] swap2(int[] hData){

		int x = (((hData.length/2)/2));
		int y = (hData.length - (hData.length/2)-1);
		for (int i = x; i < y; i++){
			int temp;
			int node;
			int lowNode;
			int highNode;
			int temp1 = hData[(i*2)+1]; //[(i*2)+1] and [(i*2)+2] are the locations of the (i's) children
			int temp2 = hData[(i*2)+2];
			if ((i % 2) != 0){ // if node is odd
				swapSib(hData, i);
				if ( temp1 < temp2){// if child 1 is smaller than child 2
					lowNeph(hData, i);
					if (hData[i] > hData[((i*2)+1)]& hData[i] != -1 & hData[((i*2)+1)] != -1 & hData[((i*2)+2)] != -1){// checks if parents/children/nephews need to be swapped and ignores any -1 values
						temp = hData[i];			// swap child/parent
						hData[i] = hData[((i*2)+1)];
						hData[((i*2)+1)] = temp;
						temp1 = hData[(i*2)+1];
						temp2 = hData[(i*2)+2];
						System.out.println("swap with children/nephews");
						System.out.print("heap array: ");
						for (int j = 0; j<hData.length; j++){
							System.out.print(hData[j] + " ");
						}
						System.out.println("");
					}
				}
				swapBase(hData);
				lowNeph(hData, i);
			}

			if ((i % 2) == 0){// if node is even
				temp1 = hData[(i*2)+1];
				temp2 = hData[(i*2)+2];
				if ( temp1 < temp2){// if child 1 is smaller than child 2
					highNeph(hData, i);
					if (hData[i] < hData[((i*2)+2)]& hData[i] != -1 & hData[((i*2)+1)] != -1 & hData[((i*2)+2)] != -1){// checks if parents/children/nephews need to be swapped and ignores any -1 values
						temp = hData[i];			// swap child/parent
						hData[i] = hData[((i*2)+2)];
						hData[((i*2)+2)] = temp;
						temp1 = hData[(i*2)+1];
						temp2 = hData[(i*2)+2];
						System.out.println("swap with children/nephews");
						System.out.print("heap array: ");
						for (int j = 0; j<hData.length; j++){
							System.out.print(hData[j] + " ");
						}
						System.out.println("");
					}
				}
				swapBase(hData);
				highNeph(hData, i);
			}
		}
		return(hData);
	}

	public int[] swap3(int[] hData){
		int x = (((hData.length/2)/2)/2);
		int y = (((hData.length/2)/2));
		for (int i = x; i < y; i++){
			int temp;
			int node;
			int lowNode;
			int highNode;
			int temp1 = hData[(i*2)+1];
			int temp2 = hData[(i*2)+2];
			if ((i % 2) != 0){ // if node is odd
				swapSib(hData, i);

				if ( temp1 < temp2){// if child 1 is smaller than child 2
					lowNeph(hData, i);
					if (hData[i] > hData[((i*2)+1)]& hData[i] != -1 & hData[((i*2)+1)] != -1 & hData[((i*2)+2)] != -1){// checks if parents/children/nephews need to be swapped and ignores any -1 values
						temp = hData[i];			// swap child/parent
						hData[i] = hData[((i*2)+1)];
						hData[((i*2)+1)] = temp;
						temp1 = hData[(i*2)+1];
						temp2 = hData[(i*2)+2];
						System.out.println("swap with children/nephews");
						System.out.print("heap array: ");
						for (int j = 0; j<hData.length; j++){
							System.out.print(hData[j] + " ");
						}
						System.out.println("");
					}
					swap2(hData);
				}
				swapBase(hData);
				lowNeph(hData, i);
				swap2(hData);
			}
			if ((i % 2) == 0){// if node is even
				temp1 = hData[(i*2)+1];
				temp2 = hData[(i*2)+2];

				if ( temp1 < temp2){// if child 1 is smaller than child 2
					highNeph(hData, i);
					if (hData[i] < hData[((i*2)+2)]& hData[i] != -1 & hData[((i*2)+1)] != -1 & hData[((i*2)+2)] != -1){// checks if parents/children/nephews need to be swapped and ignores any -1 values
						temp = hData[i];			// swap child/parent
						hData[i] = hData[((i*2)+2)];
						hData[((i*2)+2)] = temp;
						temp1 = hData[(i*2)+1];
						temp2 = hData[(i*2)+2];
						System.out.println("swap with children/nephews");
						System.out.print("heap array: ");
						for (int j = 0; j<hData.length; j++){
							System.out.print(hData[j] + " ");
						}

						System.out.println("");
					}
					swap2(hData);
				}
				swapBase(hData);
				highNeph(hData, i);
				swap2(hData);
			}
		}
		return(hData);
	}

	public int[] swap4(int[] hData){
		int x = ((hData.length/10)-2);
		int y = (hData.length/10);
		for (int i = x; i < y; i++){
			int temp;
			int node;
			int lowNode;
			int highNode;
			int temp1 = hData[(i*2)+1];
			int temp2 = hData[(i*2)+2];
			if ((i % 2) != 0){ // if node is odd
				swapSib(hData, i);
				if ( temp1 < temp2){// if child 1 is smaller than child 2
					lowNeph(hData, i);
					if (hData[i] > hData[((i*2)+1)]& hData[i] != -1 & hData[((i*2)+1)] != -1 & hData[((i*2)+2)] != -1){// checks if parents/children/nephews need to be swapped and ignores any -1 values
						temp = hData[i];			// swap child/parent
						hData[i] = hData[((i*2)+1)];
						hData[((i*2)+1)] = temp;
						temp1 = hData[(i*2)+1];
						temp2 = hData[(i*2)+2];
						System.out.println("swap with children/nephews");
						System.out.print("heap array: ");
						for (int j = 0; j<hData.length; j++){
							System.out.print(hData[j] + " ");
						}
						System.out.println("");
					}
					swap3(hData);
				}
				swapBase(hData);
				lowNeph(hData, i);
				swap3(hData);
			}
			if ((i % 2) == 0){// if node is even
				temp1 = hData[(i*2)+1];
				temp2 = hData[(i*2)+2];

				if ( temp1 < temp2){// if child 1 is smaller than child 2
					highNeph(hData, i);
					if (hData[i] < hData[((i*2)+2)]& hData[i] != -1 & hData[((i*2)+1)] != -1 & hData[((i*2)+2)] != -1){// checks if parents/children/nephews need to be swapped and ignores any -1 values
						temp = hData[i];			// swap child/parent
						hData[i] = hData[((i*2)+2)];
						hData[((i*2)+2)] = temp;
						temp1 = hData[(i*2)+1];
						temp2 = hData[(i*2)+2];
						System.out.println("swap with children/nephews");
						System.out.print("heap array: ");
						for (int j = 0; j<hData.length; j++){
							System.out.print(hData[j] + " ");
						}

						System.out.println("");
					}
					swap3(hData);
				}
				swapBase(hData);
				highNeph(hData, i);
				swap3(hData);
			}
		}
		return(hData);
	}

	public int[] lowNeph(int[] hData, int i){// finds lowest value and moves it to the correct position
		int temp = 0;						// when [i] is odd, nephews are [(i*2)+3] and [(i*2)+4]
		int node = i;
		int lowNode = hData[i];
		if (lowNode > hData[i+1]& hData[i] != -1 & hData[(i+1)] != -1){
			lowNode = hData[i+1];
			node = i+1;
		}
		if (lowNode > hData[(i*2)+1]& hData[i] != -1 & hData[(i*2)+1] != -1){
			lowNode = hData[(i*2)+1];
			node = (i*2)+1;
		}
		if (lowNode > hData[(i*2)+2]& hData[i] != -1 & hData[(i*2)+2] != -1){
			lowNode = hData[(i*2)+2];
			node = (i*2)+2;
		}
		if (lowNode > hData[(i*2)+3]& hData[i] != -1 & hData[(i*2)+3] != -1){
			lowNode = hData[(i*2)+3];
			node = (i*2)+3;
		}
		if (lowNode > hData[(i*2)+4]& hData[i] != -1 & hData[(i*2)+4] != -1){
			lowNode = hData[(i*2)+4];
			node = (i*2)+4;
		}

		if (hData[i] > lowNode){
			hData[node] = hData[i];
			hData[i] = lowNode;
			System.out.println("swap with children/nephews");
			System.out.print("heap array: ");
			for (int j = 0; j<hData.length; j++){
				System.out.print(hData[j] + " ");
			}
			System.out.println("");
		}
		return(hData);
	}

	public int[] highNeph(int[] hData, int i){// finds highest value and moves it to the correct position
		int temp = 0;						// when [i] is even, nephews are [(i*2)-1] and [(i*2)]
		int node = i;
		int highNode = hData[i];
		if (highNode < hData[i-1]){
			highNode = hData[i-1];
			node = i-1;
		}
		if (highNode < hData[(i*2)-1]& hData[i] != -1 & hData[(i*2)-1] != -1){
			highNode = hData[(i*2)-1];
			node = (i*2)-1;
		}
		if (highNode < hData[(i*2)]& hData[i] != -1 & hData[(i*2)] != -1){
			highNode = hData[(i*2)];
			node = (i*2);
		}
		if (highNode < hData[(i*2)+1]& hData[i] != -1 & hData[(i*2)+1] != -1){
			highNode = hData[(i*2)+1];
			node = (i*2)+1;
		}
		if (highNode < hData[(i*2)+2]& hData[i] != -1 & hData[(i*2)+2] != -1){
			highNode = hData[(i*2)+2];
			node = (i*2)+2;
		}

		if (hData[i] < highNode){
			hData[node] = hData[i];
			hData[i] = highNode;
			System.out.println("swap with children/nephews");
			System.out.print("heap array: ");
			for (int j = 0; j<hData.length; j++){
				System.out.print(hData[j] + " ");
			}
			System.out.println("");
		}
		return (hData);
	}

	public int[] minDel(int[] hData){//replaces the minimum value with -1 and switches it with the end array value
		int temp;					//the heap then resorts to accomodate the value changes, ignoring the new -1
		hData[1] = -1;
		temp = (hData[hData.length-1]);
		hData[hData.length-1] = hData[1];
		hData[1] = temp;
		System.out.println("delete minimum value");
		System.out.print("heap array: ");
		for (int j = 0; j<hData.length; j++){
			System.out.print(hData[j] + " ");
		}
		System.out.println("");

		if (hData.length < 8){ //sorting methods vary depending on array length
			swap2(hData);
		}
		if (hData.length > 8 & hData.length < 16){
			swap3(hData);
		}
		if (hData.length > 16){
			swap4(hData);
		}
		return(hData);
	}

	public int[] maxDel(int[] hData){//replaces the maximum value with -1 and switches it with the end array value
		int temp;					//the heap then resorts to accomodate the value changes, ignoring the new -1
		hData[2] = -1;
		temp = (hData[hData.length-2]);
		hData[hData.length-2] = hData[2];
		hData[2] = temp;
		System.out.println("delete maximum value");
		System.out.print("heap array: ");
		for (int j = 0; j<hData.length; j++){
			System.out.print(hData[j] + " ");
		}
		System.out.println("");
		if (hData.length < 8){	//sorting methods vary depending on array length
			swap2(hData);
		}
		if (hData.length > 8 & hData.length < 16){
			swap3(hData);
		}
		if (hData.length > 16){
			swap4(hData);
		}
		return(hData);
	}
}

