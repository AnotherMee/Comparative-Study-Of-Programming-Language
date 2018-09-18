import java.io.BufferedReader;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Master extends Thread {
	private volatile boolean done = false;
	private Map<String, List<String>> mymap = null;
	public Master(Map<String, List<String>> mymap){
		this.mymap = mymap;
	}
	
	public void shutdown() {
	    done = true;
	  }
		
		public void run(){
			
			//print **calls to be made**
			System.out.println("***Calls to be made***");
			for(Map.Entry<String,List<String>> entry : mymap.entrySet())
			{
				System.out.println(entry.getKey()+":"+entry.getValue());
			}
			System.out.println("\n");
			//end of print
			
			//create caller thread
			for(Map.Entry<String,List<String>> entry : mymap.entrySet()) {
			calling c = new calling(entry.getValue(),this);
			c.setName(entry.getKey());
			c.start();
			}
			//end of create caller thread
			
		}
		
		
		public void printMsg(String msg, long time) {
			System.out.println(msg + " [" + time + "]");
		}
			
		int counter=0;
		int count=0;
		public void terminate(String call) throws InterruptedException{

		synchronized (mymap) {
			this.logic();

			this.shutdown();
			System.out.println("\nProcess "+call+" has received no calls for 1 second, ending...\n");
			count++;
			if(count==counter+1) {
				System.out.println("Master has received no replies for 1.5 second, ending...");
			}
			
			}
		
		}

		private void logic() {
			// TODO Auto-generated method stub
			if(counter < mymap.size()-1) {
				counter++;
				try{
					mymap.wait();}catch(Exception e){}
				}
			
			else {
				
				mymap.notifyAll();
			}}
		}
		
	

public class exchange  {

	private static BufferedReader breader;

	public static void main(String args[]) throws IOException{
		String path = "calls.txt"; 
		LinkedHashMap<String,List<String>> mymap = new LinkedHashMap<String,List<String>>();
		FileInputStream fileStream = new FileInputStream(path);
		breader = new BufferedReader(new InputStreamReader(fileStream));
		String line;
		while((line = breader.readLine()) != null) {
			String[] tokens = line.split(",");
			String key = tokens[0].replace("{", "");
			List<String> list = new ArrayList<>();
			for(int i = 1;i<tokens.length;i++) {
			list.add(tokens[i].replaceAll("]}.", "").replace("[", "").trim());
			}
			mymap.put(key,list);
			
			}
			Master m = new Master(mymap);
			m.start();
			
	}
	
}
