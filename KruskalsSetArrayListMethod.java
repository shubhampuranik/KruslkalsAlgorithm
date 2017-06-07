
package kruskals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;

public class KruskalsSetArrayListMethod {
    class Edge implements Comparable<Edge>{
        int u,v,cost;
        public Edge(int u, int v, int cost) {
            this.u = u;
            this.v = v;
            this.cost = cost;
        }
        void display(){
            System.out.println("("+u+","+v+")\t"+cost);
        }
        @Override
        public int compareTo(Edge o) {
            if(this.cost<o.cost)
                return -1;
            else if(this.cost>o.cost)
                return 1;
            else
                return 0;
        }
    }
    Edge[] getSortedEdgeList(int cost[][],int v){
        int e=0;
        for(int i=0;i<v;i++)
            for(int j=i;j<v;j++)
                if(cost[i][j]!=0)
                    e++;
        Edge edge[]=new Edge[e];
        int k=0;
        for(int i=0;i<v;i++)
            for(int j=i;j<v;j++)
                if(cost[i][j]!=0)
                    edge[k++]=new Edge(i,j,cost[i][j]);
        Arrays.sort(edge);
        System.out.println("Sorted Edge List:");
        for(int i=0;i<e;i++)
            edge[i].display();
        return edge;
    }
    public KruskalsSetArrayListMethod() {
        System.out.println("Example 1:");
        int cost[][]={
            {0,7,0,5,0,0,0},
            {7,0,8,9,7,0,0},
            {0,8,0,0,5,0,0},
            {5,9,0,0,15,6,0},
            {0,7,5,15,0,8,9},
            {0,0,0,6,8,0,11},
            {0,0,0,0,9,11,0}},v=7;
        kruskals(cost,v);
        System.out.println("Example 2:");
        int cost1[][]={
            {0,10,0,30,45,0},
            {10,0,50,0,40,25},
            {0,50,0,0,35,15},
            {30,0,0,0,0,20},
            {45,40,35,0,0,55},
            {0,25,15,20,55,0}};
        v=6;
        kruskals(cost1, v);
    }
    //FIND FUNCTION
    Set<Integer> find(ArrayList<Set> sets,Integer node){
        Set<Integer> returnValue=new HashSet<>();
        ListIterator setsIterator=sets.listIterator();
        while(setsIterator.hasNext()){
            Set set=(Set)setsIterator.next();
            if(set.contains(node)){
                returnValue.addAll(set);
                break;
                
            }
        }
        return returnValue;
    }
    //UNION FUNCTION
    void union(ArrayList<Set> sets,Set<Integer> x,Set<Integer> y){
        sets.remove(x);
        sets.remove(y);
        x.addAll(y);
        sets.add(x);
    }
    boolean isEqual(Set x,Set y){
        x.removeAll(y);
        return x.isEmpty();
    }
    int[][] kruskals(int cost[][],int v){
        ArrayList<Set> sets=new ArrayList<>();
        for(int i=0;i<v;i++){
            Set<Integer> s=new HashSet<>();
            s.add(i);
            sets.add(s);
        }
        Edge e[]=getSortedEdgeList(cost,v);
        int i=0,j=0,t[][]=new int[v-1][3],mincost=0;
        System.out.println("EDGE\tCOST\tA/R\tSETS");
        System.out.println("\t\t\t"+sets);
        while(i<v-1){
            Edge edge=e[j++];
            Set<Integer> x=find(sets,edge.u),y=find(sets,edge.v);
            if(!isEqual(x,y)){
                t[i][0]=edge.u;
                t[i][1]=edge.v;
                t[i][2]=edge.cost;
                union(sets,x,y);
                mincost=mincost+edge.cost;
                i++;
                System.out.println("("+edge.u+","+edge.v+")\t"+edge.cost+"\tA\t"+sets);
            }else{
                System.out.println("("+edge.u+","+edge.v+")\t"+edge.cost+"\tR\t"+sets);
            }
        }
        System.out.println("Total edges="+e.length+"\nEdges processed="+j);
        System.out.println("3-Tuple Output:");
        for(int k=0;k<v-1;k++)
            System.out.println(t[k][0]+" "+t[k][1]+" "+t[k][2]);
        System.out.println("Mincost="+mincost);
        return t;
    }
    public static void main(String[] args) {
        new KruskalsSetArrayListMethod();
    }
}

