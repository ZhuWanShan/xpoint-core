/*var mysqlPoolConf = Java.type("xpoint.dataaccess.datasource.MySqlPoolConf").apply(
  "jdbc:mysql://localhost:3306/test?useSSL=false",
  "root",
  "",
  true,
  250,
  2048,
  30000
);*/

var prestoConf = Java.type("xpoint.dataaccess.datasource.PrestoConf").apply(
  "SparkTest",
  "jdbc:presto://localhost:8084/mb/view"
);
/*
var contextConf = Java.type("xpoint.engine.script.ctx.ContextConf").apply(
  ""
)*/

var xpoint_script_location = "http://localhost:40001/xpoint";

