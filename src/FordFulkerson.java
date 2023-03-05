import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class FordFulkerson {

    public Map<String, Map<String, Integer>> graph= new HashMap<>();     // Graph representation (adjacency list)
    public Map<String, Map<String, Integer>> flowDiagram= new HashMap<>();     // Flow network representation (adjacency list)
    private String[] sources = {"rest0", "rest1", "rest2", "rest3", "rest4", "rest5"};
    Map<String, String> path = new HashMap<>();

    public FordFulkerson() {
		for(int i=0;i<6;i++) {  // 6 şehri oluşturdum
			String node= "r" + Integer.toString(i);
			addNode(node);
		}
		addNode("KL");
	}
    
    
    public void addNode(String node) {       // Method to add a new node to the graph
            graph.put(node, new HashMap<>());
            flowDiagram.put(node, new HashMap<>());
    }

    public void addEdge(String node1, String node2, int maxFlow) {      // Method to add a new edge to the graph
        
        graph.get(node1).put(node2, maxFlow);      	// Add the edge to the graph
        flowDiagram.get(node1).put(node2, 0);          // Initialize the flow on the edge to 0
        flowDiagram.get(node2).put(node1, 0);
    }
    
    private int getMaxFlow(String source) {      // Method to calculate the maximum flow in the flow network
        int totalFlow = 0;

        // Keep finding paths and adding flow until no more paths are found
        while (true) {
            // Perform BFS to find a path from source to KL
            Map<String, String> parent = bfs(source);

            // If the sink was not reached, there is no more flow to send
            if (!parent.containsKey("KL")) {
                break;
            }

            // Calculate the minimum flow along the path from source to KL
            int flow = findFlow(source);

            // Update the flow network with the flow
            updateFlowDiagram(source,  flow);

            // Add the flow to the maximum flow
            totalFlow += flow;
        }

        return totalFlow;
    }

    //  BFS method  to find a path from source to sink
    private Map<String, String> bfs(String source) {

        Queue<String> queue = new LinkedList<>();          // Queue and set for store the visited nodes
        Set<String> visitedNodes = new HashSet<>();    

        path.clear();

        queue.add(source);
        visitedNodes.add(source);


        while (queue.isEmpty() == false) {
            String current = queue.poll();

            // Check all the neighbors of the current node
            for (Map.Entry<String, Integer> neighbor : graph.get(current).entrySet()) {
                String neighborName = neighbor.getKey();
                int capacity = neighbor.getValue();
                int flow = flowDiagram.get(current).get(neighborName);

                // If the neighbor has not been visited and there is residual capacity, add it to the queue
                if ( flow < capacity  && !visitedNodes.contains(neighborName) ) {
                    queue.add(neighborName);
                    visitedNodes.add(neighborName);
                    path.put(neighborName, current);
                }
            }
        }
        return path;
    }

    // find the minimum flow along the path from source to KL
    private int findFlow(String source) {
        int flow = Integer.MAX_VALUE;
        String destination = "KL";
        while (!destination.equals(source)) {
            String src = path.get(destination);
            int capacity = graph.get(src).get(destination);
            int x = flowDiagram.get(src).get(destination);
            
            if(flow>=capacity-x)              	
            	flow=capacity-x;
            	
            destination = src;
        }
        return flow;
    }
    
 // Update the flow network with the flow
    private void updateFlowDiagram(String source, int flow) {
        String destination = "KL";
        while (destination.equals(source)==false) {
            String src = path.get(destination);
            flowDiagram.get(src).put(destination, flowDiagram.get(src).get(destination) + flow);
            flowDiagram.get(destination).put(src, flowDiagram.get(destination).get(src) - flow);
              
            
            destination = src;
        }
    }



    
    // Method to calculate the maximum flow in the flow network with multiple sources
    private int getMaxFlow(String[] sources) {
        int totalFlow = 0;

        for (String source : sources) {       // Loop over all the sources
            int flow = getMaxFlow(source);
            totalFlow += flow;
        }
        
        return totalFlow;
    }
    
    public int getMaxFlow()  {
    	return getMaxFlow(sources);
    }
    
}