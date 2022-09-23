JDBC 获取数据库连接的几种方法

* 方法一

```java
//1. 加载驱动
Class.forName( "org.hsqldb.jdbcDriver" );
//获取连接
Connection connection = DriverManager.getConnection( "jdbc:hsqldb:mem:mybatis", "sa", "" );

```

* 方法二

```java
DataSource dataSource = new UnpooledDataSource("org.hsqldb.jdbcDriver",
                                               "jdbc:hsqldb:mem:mybatis", "sa", "");

Connection connection = dataSource.getConnection( );
```

* 方法三使用外部配置文件

```properties
jdbc.jdbcUrl=jdbc:mysql://localhost:3306/mybatis?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
jdbc.driverClass=com.mysql.cj.jdbc.Driver
jdbc.user=root
jdbc.password=666666
```

```java
UnpooledDataSourceFactory unpooledDataSourceFactory = new UnpooledDataSourceFactory();
Properties properties = new Properties();
properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream( "jdbc.properties" ));
unpooledDataSourceFactory.setProperties( properties );
DataSource dataSource = unpooledDataSourceFactory.getDataSource();
```



