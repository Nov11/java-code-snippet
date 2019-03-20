package mybatis.spring_mymatis_config.standalone_mybatis;

import mybatis.spring_mymatis_config.DTO.Employee;
import org.apache.ibatis.cursor.Cursor;


public interface MapperInterface {
    Cursor<Employee> selectFirst10();
}
