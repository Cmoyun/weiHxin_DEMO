var ioc = {

    //读取配置文件 DataSource.properties
    config: {
        type: "org.nutz.ioc.impl.PropertiesProxy",
        fields: {
            paths: ["conf/DataSource.properties"]
        }
    },
    // 数据源
    dataSource : {
        type : "com.alibaba.druid.pool.DruidDataSource",
        events : {
            create : "init",
            depose : 'close'
        },
        fields : {
            driverClassName : {java :"$config.get('db-driver')"},
            url             : {java :"$config.get('db-url')"},
            username        : {java :"$config.get('db-username')"},
            password        : {java :"$config.get('db-password')"},
            testWhileIdle : true,
            validationQuery : "select 1" ,
            maxActive : 100
        }
    },
    // dao
    dao : {
        type : "org.nutz.dao.impl.NutDao",
        args : [{refer:"dataSource"}]
    }
};