package org.example.mybatis;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DeYou
 * @date 2022/9/21
 */
public class MetaObjectExample {

    @Data
    @AllArgsConstructor
    private static class User {
        List<Order> orders;
        String name;
        Integer age;
    }
    @Data
    @AllArgsConstructor
    private static class Order {
        String orderNo;
        String goodsName;
    }
    //使用MetaObject 可以很优雅的获取对象的属性值
    @Test
    public void testMetaObject() {
        //创建数据，一个 有2个订单的列表
        List<Order> orders = new ArrayList() {
            {
                add(new Order("order20171024010246", "《Mybatis源码深度解析》图书"));
                add(new Order("order20171024010248", "《AngularJS入门与进阶》图书"));
            }
        };
        //订单列表加入用户有2个订单
        User user = new User(orders, "江荣波", 3);
        //创建一个与User对象关联的MetaObject
        MetaObject metaObject = SystemMetaObject.forObject(user);
        // 获取第一笔订单的商品名称
        System.out.println(metaObject.getValue("orders[0].goodsName"));
        // 获取第二笔订单的商品名称
        System.out.println(metaObject.getValue("orders[1].goodsName"));
        // 为属性设置值
        metaObject.setValue("orders[1].orderNo","order20181113010139");
        // 判断User对象是否有orderNo属性
        System.out.println("是否有orderNo属性且orderNo属性有对应的Getter方法：" + metaObject.hasGetter("orderNo"));
        // 判断User对象是否有name属性
        System.out.println("是否有name属性且orderNo属性有对应的name方法：" + metaObject.hasGetter("name"));

    }
}