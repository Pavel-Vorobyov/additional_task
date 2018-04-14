package test.by.vorobyov.skillmatrix;

import by.vorobyov.skillmatrix.domain.Node;
import by.vorobyov.skillmatrix.util.DataParserUtil;
import by.vorobyov.skillmatrix.util.IndexProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

public class DataParserUtilTest {

    private File file;
    private ArrayList<Node> nodes;
    private DataParserUtil util;
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        file = new File("src/test/resources/test.xls");
        nodes = new ArrayList<>();
        util = new DataParserUtil(new IndexProvider());
        mapper = new ObjectMapper();
    }

    @Test
    public void parseXLS() throws Exception {
        Node node = util.parseXLS(nodes,file);
//        System.out.println(nodes);
        System.out.println(mapper.writeValueAsString(node));

    }
}