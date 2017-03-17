//var mysqlPool = {
//    url: "jdbc:mysql://localhost:3306/test?useSSL=false",
//    user:  "root",
//    passwd: "",
//    cachePrepStmts: true,
//    prepStmtCacheSize: 250,
//    prepStmtCacheSqlLimit: 2048,
//    connectionTimeout:  30000
//}

var mysqlPoolConf = Java.type("vip.xpoint.dataaccess.datasource.MySqlPoolConf").apply(
    "jdbc:mysql://localhost:3306/test?useSSL=false",
    "root",
    "",
    true,
    250,
    2048,
    30000
);

var xpoint_script_root_dir = "/ws/dev/xpoint-script";
