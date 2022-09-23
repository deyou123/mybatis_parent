package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Driver;
import java.util.ServiceLoader;

/**
 * @author DeYou
 * @date 2022/9/21
 */
public class SPIExample {
    @Test
    @DisplayName( "获取可用的数据库驱动类")
    public void testSPI() {

        ServiceLoader<Driver> drivers = ServiceLoader.load(java.sql.Driver.class);
        for (Driver driver : drivers ) {
            System.out.println(driver.getClass().getName());
        }
    }
}