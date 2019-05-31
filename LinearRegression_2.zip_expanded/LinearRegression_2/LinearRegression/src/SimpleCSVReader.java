import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SimpleCSVReader {

	protected ArrayList<String> headers;
	protected ArrayList<ArrayList<String>> data;
	protected static ArrayList<Double> postCodes = new ArrayList<Double>();

	/**
	 * @param arr : an array of elements that will be added to an arrayList.
	 *
	 * @return res : the new arrayList with all the elemtns of the array
	 */
	protected ArrayList<String> arrToArrayList(String[] arr) {
		ArrayList<String> res = new ArrayList<>();
		for (int i = 0; i < arr.length; ++i) {
			res.add(arr[i]);
		}
		return res;
	}

	/**
	 * @param path : a path to the file with the data. 
	 * 
	 * This function opens the file and reads all the data line by line ancd store them in 
	 * the arraylists below (headers, and data). 
	 */
	public SimpleCSVReader(String path) {
		try {
			// Load the data form the file and store them in the data arrayList. 
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String allheaders = reader.readLine();
			headers = arrToArrayList(allheaders.split(","));
			data=new ArrayList<>();
			String temp;
			while ((temp = reader.readLine()) != null) {
				data.add(arrToArrayList(temp.split(",")));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Detecting if an error happended while loading ht edata form the file. 
		int sz=data.get(0).size();
		for (int i=0;i<data.size();++i) {
			if (data.get(i).size()!=sz) {
				System.out.println("Error in row "+i);
			}
		}
	}
	
	/**
	 * @param wantedHeaders :  the headers that the user wants the program to use while predicting. 
	 * @return res : arrayList with data. 
	 * 
	 * This function determines the
	 * indices of the headers the user wants to use. After that, it reads the data line by line. 
	 * and takes the columns with the wanted headers only and store them in the 2D ArrayLis res.  
	 */
	public ArrayList<ArrayList<Double>> getTrainingData(String[] wantedHeaders){
		ArrayList<ArrayList<Double>> res=new ArrayList<>();
		ArrayList<Integer> wantedHeadersIdx=new ArrayList<>();
		int postcodes_idx = 0;
		
		// Getting the indices of the wanted headers columns. 
		for (int i=0;i<wantedHeaders.length;++i) {
			int idx=headers.indexOf(wantedHeaders[i]);
			if (idx==-1) {
				assert(false);
			}
			wantedHeadersIdx.add(idx);
			if(wantedHeaders[i].equals("Postcode")) {
				postcodes_idx = idx;
			}
		}
		// Reading the data and putting them in the arrayList .
		// Storing all the post codes in the data for later use. 
		for (int i=0;i<data.size();++i) {
			ArrayList<Double> single=new ArrayList<>();
			ArrayList<String> dataSingle=data.get(i);
			for (int j=0;j<wantedHeadersIdx.size();++j) {
				try{
					single.add(Double.valueOf(dataSingle.get(wantedHeadersIdx.get(j))));
					if(wantedHeadersIdx.get(j)==postcodes_idx) {
						if(!postCodes.contains(Double.valueOf(dataSingle.get(wantedHeadersIdx.get(j))))){
							postCodes.add(Double.valueOf(dataSingle.get(wantedHeadersIdx.get(j))));
						}
					}
				}catch(Exception e) {
					single.add(0.0);
					assert(false);
				}
			}
			res.add(single);
		}
		int sz=res.get(0).size();
		for (int i=0;i<res.size();++i) {
			if (res.get(i).size()!=sz) {
				//System.out.println("Error in row "+i);
			}
		}

		return res;
	}
}
