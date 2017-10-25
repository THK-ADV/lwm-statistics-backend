package controllers

import java.util.concurrent.ThreadLocalRandom
import javax.inject._

import dao.{DetailEntryDao, ResourceDao, ResourceEntryDao, StatisticDao}
import models._
import org.joda.time.{DateTime, LocalDateTime}
import play.api.libs.json.JsValue
import play.api.mvc._
import services.{ResourcePatternService, ResourceValueService, ResourceVariableService, StatisticService}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Random

@Singleton
class TestCRUDController  @Inject()(cc: ControllerComponents, resourceVariableService: ResourceVariableService, resourceValueService: ResourceValueService, resourcePatternService: ResourcePatternService, statisticService: StatisticService, statisticDao: StatisticDao, resourceDao: ResourceDao, detailEntryDao: DetailEntryDao, resourceEntryDao: ResourceEntryDao)(implicit ec: ExecutionContext)
  extends AbstractCRUDController(cc){



  def createTestEntities(): Action[AnyContent] = Action {

    val patternString: String = "/api/courses/:courseId/labworks/:labworkId"

    val patternProtocol1: ResourcePatternProtocol = ResourcePatternProtocol("Abnahmen", patternString, "GET")

    val pattern1 = resourcePatternService.createWithVariables(patternProtocol1.toDBModel())

    val var1 = resourceVariableService.byName(pattern1.getId, "courseId")
    val var2 = resourceVariableService.byName(pattern1.getId, "labworkId")

    val var1Values = List(
      resourceValueService.createByVaribale(var1.getId, ResourceValueProtocol("AP1", "1").toDBModel()),
      resourceValueService.createByVaribale(var1.getId, ResourceValueProtocol("AP2", "2").toDBModel())
    )
    val var2Values = List(
      resourceValueService.createByVaribale(var2.getId, ResourceValueProtocol("MI", "3").toDBModel()),
      resourceValueService.createByVaribale(var2.getId, ResourceValueProtocol("AI", "4").toDBModel()),
      resourceValueService.createByVaribale(var2.getId, ResourceValueProtocol("TI", "5").toDBModel()),
      resourceValueService.createByVaribale(var2.getId, ResourceValueProtocol("WI", "6").toDBModel()),
    )

    val namePool = List("mmustermann", "umuesse", "fherborn", "mmitarbe")
    val payloadPool = List("Test Payload 1", "Test Payload 2", "Payload Extendend", "Payload 2.0")

    (0 to 1000).map(_ => {
      val stat = StatisticProtocol(patternProtocol1.method, "/api/courses/"+ Random.shuffle(var1Values).head.uuid+"/labworks/"+Random.shuffle(var2Values).head.uuid, Random.shuffle(payloadPool).head, Random.shuffle(namePool).head).toDBModel()
      stat.created = randomLocalDateTime(LocalDateTime.now().getYear)
      statisticService.create(stat)
    })

    Ok("")
  }

  def randomLocalDateTime(year: Int): LocalDateTime = {
    val startOfYear = new LocalDateTime(year, 1, 1, 0, 0)
    startOfYear.plusDays(Random.nextInt(if (startOfYear.year.isLeap) 355 else 356))
  }
}
