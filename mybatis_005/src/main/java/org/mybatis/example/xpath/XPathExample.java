package org.mybatis.example.xpath;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.ibatis.io.Resources;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.awt.geom.RectangularShape;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author DeYou
 * @date 2022/9/24
 * dom解析
 */
public class XPathExample {
    @Test
    public void testXPathParser() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, InvocationTargetException, IllegalAccessException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        InputStream resourceAsStream = Resources.getResourceAsStream( "users.xml" );
        Document document = documentBuilder.parse( resourceAsStream );


        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodeList = (NodeList)xPath.evaluate( "/users/*",document, XPathConstants.NODESET );

        ArrayList<User> users = new ArrayList<>();

        for (int i = 1; i <= nodeList.getLength(); i++) {
            String path ="/users/user["+i+"]";
            String id = (String)xPath.evaluate( path + "/@id", document, XPathConstants.STRING );
            String name = (String)xPath.evaluate( path + "/name", document, XPathConstants.STRING );
            String createTime = (String)xPath.evaluate( path + "/createTime", document, XPathConstants.STRING );
            String password = (String)xPath.evaluate(path + "/password", document, XPathConstants.STRING             );
            String nickName = (String)xPath.evaluate( path + "/nickName", document, XPathConstants.STRING );
            User user = buildUser( id, name,  createTime, createTime, password, nickName );
            users.add( user );
        }
        users.forEach( System.out::println);

    }

    private User buildUser(String id,String name,
                                       String createTime, String password,
                                       String phone, String nickName)
            throws IllegalAccessException, InvocationTargetException {
        User User = new User();
        DateConverter dateConverter = new DateConverter(null);
        dateConverter.setPattern("yyyy-MM-dd HH:mm:ss");
        ConvertUtils.register(dateConverter, Date.class);
        BeanUtils.setProperty(User,"id",id);
        BeanUtils.setProperty(User,"name",name);
        BeanUtils.setProperty(User,"createTime",createTime);
        BeanUtils.setProperty(User,"passward",password);
        BeanUtils.setProperty(User,"phone",phone);
        BeanUtils.setProperty(User,"nickName",nickName);
        return User;
    }
}