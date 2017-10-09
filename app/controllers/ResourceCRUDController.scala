package controllers

import javax.inject._

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import dao.{DetailEntryDao, ResourceDao, ResourceEntryDao}
import models._
import play.api.libs.json._
import play.api.mvc._

import scala.collection.JavaConverters._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ResourceCRUDController @Inject()(cc: ControllerComponents, resourceDao: ResourceDao, detailEntryDao: DetailEntryDao, resourceEntryDao: ResourceEntryDao)(implicit ec: ExecutionContext)
  extends AbstractCRUDController(cc) {

  def add(): Action[JsValue] = Action(parse.json).async { request =>
    val result = for {
      protocol <- Future.fromTry(parseRequest[ResourceProtocol](request))
      resource = protocol.toDBModel()
    } yield {
      resourceDao.save(resource)
      resource
    }

    jsonFutureResult(result)
  }

  def byId(id: String): Action[AnyContent] = Action {
    val resource = resourceDao.byId(id)
    jsonResult(resource)
  }

  def details(id: String): Action[AnyContent] = Action {
    val resource = resourceDao.byId(id)
    val detailEntries = detailEntryDao.byResource(resource)
    val entries = resourceEntryDao.byResource(resource)


    Ok(Json.obj("status" -> "OK", "result" -> Json.obj("detailEntries" -> Json.toJson(detailEntries), "entries" -> Json.toJson(entries))))
  }

  def addResourceEntry(resourceId: String): Action[JsValue] = Action(parse.json).async { request =>
    val result = for {
      protocol <- Future.fromTry(parseRequest[ResourceEntryProtocol](request))
      resourceEntry = protocol.toDBModel()
    } yield {
      resourceEntry.setResource(Resource.byId(resourceId))
      resourceEntryDao.save(resourceEntry)
      resourceEntry
    }
    jsonFutureResult(result)
  }

  def addDetailEntry(resourceId: String): Action[JsValue] = Action(parse.json).async { request =>
    val result = for {
      protocol <- Future.fromTry(parseRequest[DetailEntryProtocol](request))
      detailEntry = protocol.toDBModel()
    } yield {
      detailEntry.setResource(Resource.byId(resourceId))
      detailEntryDao.save(detailEntry)
      detailEntry
    }
    jsonFutureResult(result)
  }

  def all(): Action[AnyContent] = Action {
    jsonResult(resourceDao.all())
  }
}
