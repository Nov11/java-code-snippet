package mybatis.spring_mymatis_config.standalone_mybatis;

import mybatis.spring_mymatis_config.DTO.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class StandAloneMyBatis {
    private static final Logger logger = LoggerFactory.getLogger(StandAloneMyBatis.class);
    private final SqlSessionFactory sqlSessionFactory;

    public StandAloneMyBatis() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    public List<Employee> get10() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            return sqlSession.selectList("EmployeeMapper.selectFirst10");
        }
    }

    public static void main(String[] args) throws IOException {
        StandAloneMyBatis s = new StandAloneMyBatis();
        List<Employee> employeeList = s.get10();
        employeeList.forEach(e -> logger.info("{}", e));
    }
}
