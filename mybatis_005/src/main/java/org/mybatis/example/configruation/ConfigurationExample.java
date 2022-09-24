package org.mybatis.example.configruation;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

/**
 * @author DeYou
 * @date 2022/9/23
 */
public class ConfigurationExample {
    @Test
    public void testConfiguration() throws Exception {
        //获取配置文件输入流
        InputStream resourceAsStream = Resources.getResourceAsStream( "mybatis-config.xml" );
        //创建XmLConfigBuilder 实例
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder( resourceAsStream );
        //调用XmLConfigBuilder的parse()方法获取Configuration对象
        Configuration parse = xmlConfigBuilder.parse();
    }
}