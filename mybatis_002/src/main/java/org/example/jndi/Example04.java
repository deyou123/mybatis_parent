package org.example.jndi;

import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
import org.junit.jupiter.api.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author DeYou
 * @date 2022/9/21
 */
public class Example04 {
    /**
     * 提供命名服务
     * @throws IOException
     */
    @BeforeEach
    public void before() throws IOException {
        DataSourceFactory dsf = new UnpooledDataSourceFactory();
        Properties properties = new Properties();
        InputStream configStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("database1.properties");
        properties.load(configStream);
        dsf.setProperties(properties);
        DataSource dataSource = dsf.getDataSource();
        try {
            Properties jndiProps = new Properties();
            jndiProps.put( Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
            jndiProps.put(Context.URL_PKG_PREFIXES, "org.apache.naming");
            Context ctx = new InitialContext(jndiProps);
            // javaLTestDC 和配置文件里的信息绑定
            ctx.bind("java:TestDC", dataSource);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName( "JNDI查找命名服务测试")
    public void testJndi() {
        try {
            Properties jndiProps = new Properties();
            jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
            jndiProps.put(Context.URL_PKG_PREFIXES, "org.apache.naming");
            Context ctx = new InitialContext(jndiProps);
            //根据命名获取数据源
            DataSource dataSource = (DataSource) ctx.lookup("java:TestDC");
            Connection conn = dataSource.getConnection();
            //测试连接对象是否为空
            Assertions.assertNotNull( conn );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}