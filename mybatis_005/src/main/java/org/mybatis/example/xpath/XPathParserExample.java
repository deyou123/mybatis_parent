package org.mybatis.example.xpath;

import javafx.util.converter.DateStringConverter;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean2;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author DeYou
 * @date 2022/9/23
 */
public class XPathParserExample {
    @Test
    public void testXPathParser() throws IOException, InvocationTargetException, IllegalAccessException {
        Reader resourceAsReader = Resources.getResourceAsReader( "users.xml" );
        XPathParser parser = new XPathParser( resourceAsReader );

        //注册日期转换器
        DateConverter dateConverter = new DateConverter(null);
        dateConverter.setPattern( "yyyy-MM-dd HH:mm:ss");

        ConvertUtils.register( dateConverter, Date.class);


        ArrayList<User> users = new ArrayList<>();

        List<XNode> nodes =  parser.evalNodes( "/users/*" );

        for (XNode node : nodes) {
            User user = new User();
            Long id = node.getLongAttribute( "id" );
            BeanUtils.setProperty( user, "id", id );


            List<XNode> childrenNodes = node.getChildren();
            for (XNode childNode : childrenNodes) {
                BeanUtils.setProperty( user,
                        childNode.getName(),childNode.getStringBody() );
            }
            users.add( user );
        }

        users.forEach( System.out::println);
    }
}