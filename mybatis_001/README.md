# 完成基本的增删改查
引入依赖
    ```groovy
        implementation 'org.mybatis:mybatis:3.5.10'
        implementation 'mysql:mysql-connector-java:8.0.30'
        compileOnly 'org.projectlombok:lombok:1.18.18'
        annotationProcessor 'org.projectlombok:lombok:1.18.18'
    ```
User类完成基本的增删改查，并测试

Student 和 Class  多表查询


完成一对一

```xml

    <resultMap id="studentMap" type="org.example.entity.Student">
        <id property="id" column="sid"></id>
        <result property="name" column="sname"></result>
        <association property="clazz" javaType="org.example.entity.Class">
            <id property="id" column="cid"></id>
            <result property="name" column="cname"></result>
        </association>
    
    </resultMap>

    <select id="findById" resultMap="studentMap">
    select s.id sid, s.name sname, c.id cid, c.name cname
    from student s ,class c
    where s.cid = c.id
    and s.id = #{id}
    </select>
```







一对多的查询