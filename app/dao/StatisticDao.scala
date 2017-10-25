package dao

import dao_.AbstractDao
import io.ebean.ExpressionList
import models.{Resource, Statistic}
import org.joda.time.{DateTime, LocalDateTime}
import output.DateValue

import scala.collection.JavaConverters._

class StatisticDao extends AbstractDao[Statistic](classOf[Statistic]) {



  def getData(label: String, method: String, pattern: String, start: LocalDateTime, end: LocalDateTime, view: String): List[DateValue] = {
    //val query: String = "select count(date_part('month',created))" +
    //  " from statistic" +
    //  " where created BETWEEN " + new Timestamp(start.getMillis) + " AND " + new Timestamp(end.getMillis) +
    //  " and method = " + method +
    //  " group by date_part('month',created);"


    var dbResult = find().where().between("created", start, end.plusDays(1))
      .and().startsWith("description", pattern)
      .and().eq("method", method)
      .findList().asScala.toList
      .groupBy(stat => forView(stat.created, view))

    var iDate = forView(start, view)

    do {
      if (!dbResult.exists(s => s._1 == iDate))
        dbResult += (iDate -> List.empty)
      iDate = increaseForView(iDate, view)
    } while (iDate.isBefore(end.plusDays(1)))

    val result = dbResult.toList.sortBy(dt => dt._1.toDate.getTime)

    result.map(v => DateValue(label, v._1, v._2))
  }

  def forView(date: LocalDateTime, view: String): LocalDateTime = {
    view match {
      case "day" => date.withTime(0, 0, 0, 0)
      case "week" => date.withDayOfWeek(1).withTime(0, 0, 0, 0)
      case "month" => date.withDayOfMonth(1).withTime(0, 0, 0, 0)
    }
  }

  def increaseForView(iDate: LocalDateTime, view: String): LocalDateTime = {
    view match {
      case "day" => iDate.plusDays(1)
      case "week" => iDate.plusWeeks(1)
      case "month" => iDate.plusMonths(1)
    }
  }

  def byResource(resource: Resource, start: Long, end: Long): List[Statistic] = find().where()
    .between("created", new DateTime(start), new DateTime(end)).and()
    .startsWith("description", resource.filter)
    .and().eq("method", resource.method)
    .findList().asScala.toList

  def filtered(filter: Map[String, Object]): List[Statistic] = {
    val query: ExpressionList[Statistic] = find().where()
    filter.foreach(f => query.eq(f._1, f._2))
    query.findList().asScala.toList
  }


}
