package xpoint.dataaccess.datasource

import javax.sql.DataSource

import com.twitter.inject.Logging
import org.apache.commons.dbcp2.BasicDataSource
import xpoint.conf.Conf

/**
  * Created by Spark Zhu<szhu.spark@gmail.com> on 05/12/2016.
  */
object PrestoDataSource extends Logging{
    def apply(): DataSource = {
        val ds = new BasicDataSource()
        val conf = Conf[PrestoConf].getOrElse(PrestoConf())

        ds.setDriverClassName("com.facebook.presto.jdbc.PrestoDriver")
        ds.setUsername(conf.username)
        ds.setUrl(conf.url)
        //ds.setPassword(passwd)
        ds.setMaxIdle(1)
        ds.setMaxTotal(8)
        ds
    }
}

case class PrestoConf(username: String = "", url: String = "")
