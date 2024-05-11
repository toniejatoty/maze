package jimp2.jimp2_maze;

public class solver {
 private Vertex [] mazegraph ; 
private int start; 
private boolean[]visited; // is vertex visited?
private Integer [] distance_from_start; // like distance_from_start[50] will have value beetween start and vertex represented by 50 number    
private Integer [] previous_vertex; // to know connetions  
 public solver(Vertex[] mazegraph, int start) {
    this.mazegraph = mazegraph;
    this.start=start;
 this.distance_from_start= new Integer[mazegraph.length];
 previous_vertex = new Integer[mazegraph.length];
for(int i=0; i<distance_from_start.length; i++)
{
    distance_from_start[i]=Integer.MAX_VALUE;
}
visited = new boolean[mazegraph.length];
}


public void solve ()
    {

    int number_min_vertex; 
    previous_vertex[start]=-1;
    distance_from_start[start]=0;
    for(int i=0; i<mazegraph.length-1; i++)
    {
number_min_vertex = find_min_vertex();
visited[number_min_vertex]=true;    
for(int j=0; j<mazegraph[number_min_vertex].getsize(); j++)
{
    if (!visited[mazegraph[number_min_vertex].getEdge(j).getDestination()] &&  distance_from_start[number_min_vertex] != Integer.MAX_VALUE 
    && distance_from_start [number_min_vertex] + mazegraph[number_min_vertex].getEdge(j).getWeight() < distance_from_start [mazegraph[number_min_vertex].getEdge(j).getDestination()]) 
    {
    distance_from_start [mazegraph[number_min_vertex].getEdge(j).getDestination()] = distance_from_start [number_min_vertex] + mazegraph[number_min_vertex].getEdge(j).getWeight();
previous_vertex[mazegraph[number_min_vertex].getEdge(j).getDestination()] = number_min_vertex;
}
}
    }
    Integer [] directions= new Integer[mazegraph.length]; // it will containts direction 1-up 2-down 3-left 4-right i should head in maze from finish to start
    
    int vertex_number_iterator=1;
    int i =0; // to chronology assign values to way
    
    while(previous_vertex[vertex_number_iterator]!=-1)
    {
        directions[i]=mazegraph[vertex_number_iterator].getEdge(mazegraph[vertex_number_iterator].find_vertex_connection(previous_vertex[vertex_number_iterator])).getDirection();
          i++;
        vertex_number_iterator = previous_vertex[vertex_number_iterator];
    } 
    for(int p=0; p<20; p++)
    System.out.println(directions[p]);
    printSolution(distance_from_start, previous_vertex);
}

    private static void printSolution(Integer[] distance, Integer[] previous_vertex) {
        System.out.println("Vertex \t Distance from Source");
        for (int i = 0; i < distance.length-1; i++) {
            System.out.println(i + " \t " + distance[i]+ " \t" + previous_vertex[i] );
        }
    }

    private int find_min_vertex()
    {
        int minvalue = Integer.MAX_VALUE;
        int vertex_with_minvalue = -1;
        for(int i=0; i<mazegraph.length; i++)
        { 
            if(distance_from_start[i]<minvalue && visited[i]==false){
           minvalue = distance_from_start[i];
           vertex_with_minvalue = i;
        }
        
        }
        return vertex_with_minvalue;
    }
}
