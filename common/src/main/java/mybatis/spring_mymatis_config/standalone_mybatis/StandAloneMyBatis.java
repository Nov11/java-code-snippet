package mybatis.spring_mymatis_config.standalone_mybatis;

import mybatis.spring_mymatis_config.DTO.Employee;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StandAloneMyBatis {
    private static final Logger logger = LoggerFactory.getLogger(StandAloneMyBatis.class);
    private final SqlSessionFactory sqlSessionFactory;

    public StandAloneMyBatis() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    public static void main(String[] args) throws IOException {
        StandAloneMyBatis s = new StandAloneMyBatis();
//        List<Employee> employeeList = s.get10ByCursor2();
//        {
//            Employee employee = s.getById(10001);
//            logger.info("{}", employee);
//        }
        {
            Employee employee = s.getByIdIfc(10001);
            logger.info("{}", employee);
        }
//        employeeList.forEach(e -> logger.info("{}", e));
    }

    public List<Employee> get10() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            return sqlSession.selectList("EmployeeMapper.selectFirst10");
        }
    }

    public Employee getById(int id) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            return sqlSession.selectOne("mybatis.spring_mymatis_config.standalone_mybatis.MapperInterface.selectById", id);
        }
    }

    public List<Employee> get10ByCursor() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            Cursor<Employee> cursor = sqlSession.selectCursor("mybatis.spring_mymatis_config.standalone_mybatis.MapperInterface.selectFirst10");
            List<Employee> result = new ArrayList<>();
            for (Employee employee : cursor) {
                result.add(employee);
            }
            return result;
        }
    }

    public List<Employee> get10ByCursor2() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            MapperInterface mapperInterface = sqlSession.getMapper(MapperInterface.class);
            Cursor<Employee> cursor = mapperInterface.selectFirst10();
            List<Employee> result = new ArrayList<>();
            for (Employee employee : cursor) {
                result.add(employee);
            }
            return result;
        }
    }

    public Employee getByIdIfc(int id) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            MapperInterface mapperInterface = sqlSession.getMapper(MapperInterface.class);
            return mapperInterface.selectById(id);
        }
    }
}
