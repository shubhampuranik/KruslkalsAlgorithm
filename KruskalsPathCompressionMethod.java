
package kruskals;

import java.util.*;

class Node{
  //data : Name of the vertex
  int data;
  //rank : used because set with lower no of vertices gets merged with highee no of vertices
  int rank;
  //parent : the node it is pointing to
  Node parent;

  //Constructor
  public Node(int data){
    this.data = data;
    this.rank = 0;
  }
}

class Edge implements Comparable{
	int u,v,cst;
	
	public Edge(int u,int v,int cst){
		this.u=u;
		this.v=v;
		this.cst=cst;
	}
	
	@Override
	public int compareTo(Object edge) {
		int cst = ((Edge)edge).cst;
		return this.cst-cst;
	}
}

public class KruskalsPathCompressionMethod {

  static int n;
  static Map<Integer,Node> map = new HashMap<Integer,Node>();
  static ArrayList<Edge> edges = new ArrayList<Edge>();
  static int cost[][] = {
		  {999,7,999,5,999,999,999},
		  {7,999,8,9,7,999,999},
		  {999,8,999,999,5,999,999},
		  {5,9,999,999,15,6,999},
		  {999,7,5,15,999,8,9},
		  {999,999,999,6,8,999,11},
		  {999,7,999,5,9,11,999}
  };
  
  
  //Functn 1 : makes a set with one element in it (with rank 0)
  public static void makeSet(int data){
    Node node = new Node(data);
    node.parent = node;
    map.put(data, node);
  }
  
  //Functn 2 : finds the representative node (last parent) by traversing through their parent nodes
  public static Node findset(Node na){

    if(na==na.parent)
      return na;
    
    /*             PATH COMPRESSION               */

    //the below statement is used for path compression
    //i.e. it makes the representative node as its parent node

    na.parent = findset(na.parent);
    return na.parent;
  }

  
  /*             UNION BY RANK                */

  //Functn 3 : merges two sets if vertices a and b are in two distinct sets 
  //the representative of the set with higher no of vrtices is the representative of the mergred set
  
  public static boolean union(int a, int b){
    
    int r;

    Node ra = findset(map.get(a));
    Node rb = findset(map.get(b));

    if( ra.data!=rb.data ){

      if(ra.rank>=rb.rank){

        rb.parent=ra;
        
        if(ra.rank==rb.rank)
          ra.rank++;

      }
      else
        ra.parent=rb;

      return true;
    }
    return false;
  }

  public static void main(String args[]) { 
	  
      /*
      Dynamic Initialisation
      Scanner s = new Scanner(System.in);
      
      System.out.println("Enter No. of a edges:");
      n = s.nextInt();

      for(int i=0;i<n-1;i++){
    	  for(int j=i+1;j<n;j++){
    		  System.out.println("Enter cost for edge "+(i+1)+"-->"+(j+1)+":");
    		  cost[i][j] = s.nextInt();
    		  if(cost[i][j]!=999){
    			  edges.add(new Edge(i+1,j+1,cost[i][j]));
    		  }
    	  }
      }
      */
	  
      //Static Initialisation
	  
      n = 7;
      int mincost=0;
      //Scanning upper triangular of cost matrix for adding relevent edges
      
      for(int i=0;i<n-1;i++){
    	  for(int j=i+1;j<n;j++){
    		  if(cost[i][j]!=999){
    			  edges.add(new Edge(i+1,j+1,cost[i][j]));
    		  }
    	  }
      }
      
      //Sorting the Edges....
      
      Collections.sort(edges);
      System.out.println("V1 \t V2 \t Cost");
      for(Edge edge : edges){
          System.out.println((char)(64+edge.u)+"\t"+(char)(64+edge.v)+"\t"+edge.cst);
      }
      
      //Creating a set of size 1 for each vertex
      
      for(int i=1;i<=n;i++){
          makeSet(i);
      }
      
      //Calling union function for each edge in the ascending order of its cost
      
      for(Edge e:edges){
    	  
    	  boolean b = union(e.u,e.v);
    	  
    	  if (b) {
    		  
    		  //b is true meaning : edge is accepted
    		  
    		  System.out.println("Edge " + (char)(64+e.u) + " --> " + (char)(64+e.v) + " Accepted.");
    		  mincost+=e.cst;
    		  /*             PATH COMPRESSION               */
    		  for (int i = 1; i <= n; i++) {
    			  findset(map.get(i));
    		  }
    		  System.out.println("After union(" + (char)(64+e.u) + "," + (char)(64+e.v) + "):");
    		  
    		  System.out.println("Vertex    Parent Vertex");
    		  for (int i = 1; i <= n; i++) {
    			  System.out.println((char)(64+i) + "         " + (char)(64+map.get(i).parent.data));
    		  }
    		  
    	  }
    	  else{
    		  //b is false meaning : edge is rejected
			    System.out.println("Edge " + (char)(64+e.u) + " --> " + (char)(64+e.v) + " Rejected.");
    	  }
      }
      System.out.println("Minimum Cost: "+mincost);
  }
}