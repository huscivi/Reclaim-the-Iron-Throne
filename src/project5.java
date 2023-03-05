import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class project5 {

	public static void main(String[] args) throws FileNotFoundException, IOException  {
		
		String input= args[0];	
		String output = args[1];
			
		FordFulkerson ff= new FordFulkerson();		

		File Obj = new File(input); 
		Scanner Reader = new Scanner(Obj); 
		
	    int numOfCities = Integer.parseInt(Reader.nextLine());
	    
	    for(int i=0; i<numOfCities; i++) {   // I added as many c0 c1 nodes as the number in the first line
			String node= "c" + Integer.toString(i);
			ff.addNode(node);
	    }
		
		
	    String data = Reader.nextLine();   // I read the 2nd line and calculated the capacities of the sources
        String [] sources = data.split(" ");
        
        for(int i=0;i<6;i++) {  // I created quotas for cities
			String node= "rest" + Integer.toString(i);
			ff.addNode(node);
			ff.addEdge(node,"r" + Integer.toString(i), Integer.parseInt(sources[i]));
		}		
		
		
	    while (Reader.hasNextLine()) {  // I read the distance between the nodes in the input and placed it on the graph.
			String row = Reader.nextLine(); 
            String [] rowElements = row.split(" ");            

            int numOfEdges= (rowElements.length - 1) / 2 ;
            
            for(int i=0; i<numOfEdges;i++) {          	
            	ff.addEdge(rowElements[0] ,rowElements[1+2*i] , Integer.parseInt(rowElements[2+2*i]));
            }          

	    }	
		
      
      int maxFlow = ff.getMaxFlow();

        FileWriter myWriter = new FileWriter(output, false);  // I printed the results to the file.
	    myWriter.write(Integer.toString(maxFlow));	 

		myWriter.close();        
		Reader.close();
	}
}
