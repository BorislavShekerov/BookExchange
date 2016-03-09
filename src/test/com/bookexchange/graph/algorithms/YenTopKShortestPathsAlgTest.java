package bookexchange.graph.algorithms;

import com.bookexchange.graph.Graph;
import com.bookexchange.graph.Vertex;
import com.bookexchange.graph.Path;
import com.bookexchange.graph.alogirthms.YenTopKShortestPathsAlg;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sheke on 3/1/2016.
 */
public class YenTopKShortestPathsAlgTest {
    YenTopKShortestPathsAlg testObj;

    @Test
    public void next(){

        Vertex vertex1 = new Vertex("vertex1");
        Vertex vertex2 = new Vertex("vertex2");
        Vertex vertex3 = new Vertex("vertex3");
        Vertex vertex4 = new Vertex("vertex4");
        Vertex vertex5 = new Vertex("vertex5");
        Graph graph = new Graph(Arrays.asList(vertex1,vertex2,vertex3,vertex4,vertex5),new ArrayList<>());

        graph.addEdge(vertex1,vertex2,0);
        graph.addEdge(vertex2,vertex3,0);
        graph.addEdge(vertex2,vertex5,0);
        graph.addEdge(vertex3,vertex5,0);
        graph.addEdge(vertex2,vertex4,0);
        graph.addEdge(vertex4,vertex5,0);
        graph.addEdge(vertex5,vertex1,0);

        testObj = new YenTopKShortestPathsAlg(graph,vertex1,vertex5);

        while(testObj.hasNext())
        {
            Path cur_result = testObj.next();
            System.out.println(cur_result);
        }

    }
}
