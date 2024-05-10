package jimp2.jimp2_maze;

public class solver {
 private Vertex [] mazegraph ; 
private int start , finish;
private boolean[]visited;
private Integer [] distance_from_start; // like distance_from_start[50] will have value beetween start and vertex represented by 50 number    
private Integer [] previous_vertex;
 public solver(Vertex[] mazegraph, int start, int finish) {
    this.mazegraph = mazegraph;
    this.start=start;
    this.finish=finish;
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
}
}
    }
    printSolution(distance_from_start);
}

    private static void printSolution(Integer[] distance) {
        System.out.println("Vertex \t Distance from Source");
        for (int i = 0; i < distance.length-1; i++) {
            System.out.println(i + " \t " + distance[i]);
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
