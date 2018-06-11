package com.abc;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Nov11 on 2015/8/1.
 */
public class bat {
    public static void main(String[] args) {
        String resource = "config.xml";//"org/mybatis/example/mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        try {
            Blog blog = (Blog) session.selectOne("org.mybatis.example.BlogMapper.selectBlog", 101);
        } finally {
            session.close();
        }
        /*SqlSession session = sqlSessionFactory.openSession();
        try {
            BlogMapper mapper = session.getMapper(BlogMapper.class);
            Blog blog = mapper.selectBlog(101);
        } finally {
            session.close();
        }*/
    }
}
