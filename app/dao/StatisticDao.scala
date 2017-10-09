package dao

import dao_.AbstractDao
import models.{Resource, Statistic}
import org.joda.time.DateTime
import scala.collection.JavaConverters._

class StatisticDao extends AbstractDao[Statistic](classOf[Statistic]){

  def byResource(resource: Resource, start: Long, end: Long): List[Statistic] = find().where()
    .between("created", new DateTime(start), new DateTime(end)).and()
    .startsWith("description", resource.filter)
    .and().eq("method", resource.method)
    .findList().asScala.toList

}
