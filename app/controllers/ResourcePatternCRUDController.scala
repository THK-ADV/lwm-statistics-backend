package controllers

import javax.inject._

import dao.ResourcePatternDao
import models._
import play.api.libs.json._
import play.api.mvc._
import services.{ResourcePatternService, ResourceVariableService}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ResourcePatternCRUDController @Inject()(cc: ControllerComponents, resourcePatternService: ResourcePatternService, resourceVariableService: ResourceVariableService)(implicit ec: ExecutionContext)
  extends AbstractCRUDController(cc) {

  def add(): Action[JsValue] = Action(parse.json).async { request =>
    val result = for {
      protocol <- Future.fromTry(parseRequest[ResourcePatternProtocol](request))
      resource = protocol.toDBModel()
    } yield {
      resourcePatternService.createWithVariables(resource)
      resource
    }

    jsonFutureResult(result)
  }

  def all(): Action[AnyContent] = Action {
     jsonResult(resourcePatternService.all())
  }

  def byId(id: String): Action[AnyContent] = Action {
    jsonResult(resourcePatternService.byId(id.toLong))
  }

  def variables(id: String): Action[AnyContent] = Action {
    jsonResult(resourceVariableService.byPatternId(id.toLong))
  }
}
