package controllers

import javax.inject.Inject

import models.{ResourcePatternProtocol, ResourceValueProtocol}
import play.api.libs.json.JsValue
import play.api.mvc._
import services.ResourceValueService

import scala.concurrent.{ExecutionContext, Future}

class ResourceValueCRUDController @Inject()(cc: ControllerComponents, resourceValueService: ResourceValueService)(implicit ec: ExecutionContext)
  extends AbstractCRUDController(cc) {

  def createByVariable(id: String): Action[JsValue] = Action(parse.json).async { request =>
    val result = for {
      protocol <- Future.fromTry(parseRequest[ResourceValueProtocol](request))
      resource = protocol.toDBModel()
    } yield {
      resourceValueService.createByVaribale(id.toLong, resource)
    }

    jsonFutureResult(result)
  }

  def byVariable(id: String): Action[AnyContent] = Action {
    jsonResult(resourceValueService.byVariable(id.toLong))
  }

  def delete(id: String): Action[AnyContent] = Action {
    jsonResult(resourceValueService.delete(id.toLong))
  }
}
