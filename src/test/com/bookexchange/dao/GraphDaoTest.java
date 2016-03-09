package bookexchange.dao;

import com.bookexchange.dao.GraphDao;
import com.bookexchange.graph.Edge;
import com.bookexchange.graph.Graph;
import com.bookexchange.graph.Vertex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by sheke on 3/2/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-main.xml")
@Transactional
public class GraphDaoTest {
    @Autowired
    GraphDao testObj;

    @Test
    public void saveGraph(){
        Vertex vertex1 = new Vertex("vertex1");
        Vertex vertex2 = new Vertex("vertex2");

        Graph graph = new Graph(Arrays.asList(vertex1,vertex2),new ArrayList<>());
        graph.addEdge(vertex1,vertex2,0);

        testObj.addGraph(graph);

        List<Graph> allGraphs = testObj.getAllGraphs();
        assertEquals(2,allGraphs.get(0).getVertexList().size());
    }
}
