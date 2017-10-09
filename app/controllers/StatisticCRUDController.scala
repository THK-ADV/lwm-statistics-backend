package controllers

import javax.inject._

import dao.{DetailEntryDao, ResourceDao, ResourceEntryDao, StatisticDao}
import models._
import org.joda.time.DateTime
import output.{ResourceSubValue, ResourceValue}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

import scala.collection.JavaConverters._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class StatisticCRUDController  @Inject()(cc: ControllerComponents, statisticDao: StatisticDao, resourceDao: ResourceDao, detailEntryDao: DetailEntryDao, resourceEntryDao: ResourceEntryDao)(implicit ec: ExecutionContext)
  extends AbstractCRUDController(cc){



  def add(): Action[JsValue] = Action(parse.json).async { request =>
    val result = for {
      protocol <- Future.fromTry(parseRequest[StatisticProtocol](request))
      statistic = protocol.toDBModel()
    } yield {
      statisticDao.save(statistic)
      statistic
    }

    jsonFutureResult(result)
  }

  def all(): Action[AnyContent] = Action {
    jsonResult(statisticDao.all())
  }

  def byResource(id:String, start: String, end: String) = Action {

    val resource = resourceDao.byId(id)
    val statistics = statisticDao.byResource(resource, start.toLong, end.toLong)

    val detailEntries = detailEntryDao.byResource(resource)

    val resourceEntries = resourceEntryDao.byResource(resource).map(entry => {
      ResourceValue(entry.label,
        detailEntries.map(dentry => ResourceSubValue(dentry.label, statistics.filter(stat => isDetail(stat, entry, dentry)))))
    })

    jsonResult(resourceEntries)
  }

  def isDetail(stat: Statistic, entry: ResourceEntry, dentry: DetailEntry): Boolean ={
    stat.description.split("/")(3).equals(entry.entryId) && stat.description.split("/")(5).endsWith(dentry.entryId)
  }
}
