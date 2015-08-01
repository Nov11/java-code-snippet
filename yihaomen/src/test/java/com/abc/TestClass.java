package com.abc;

import com.abc.dao.UserMapper;
import com.abc.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Nov11 on 2015/8/1.
 */
public class TestClass {
    @Test
    public void testSelect(){
        String resource = "config.xml";
        InputStream input = null;
        try {
            input = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(input);
        SqlSession sqlSession = sqlSessionFactory.openSession();
/*
        try {
            User user = (User) sqlSession.selectOne("com.abc.dao.UserMapper.selectByPrimaryKey", 1);
            System.out.println(user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
*/
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User i = userMapper.selectByPrimaryKey(1);
            System.out.println("ret: " + i);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }finally {
            sqlSession.close();
        }

    }
}
