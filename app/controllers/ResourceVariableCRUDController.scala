package controllers

import javax.inject.Inject

import play.api.mvc.ControllerComponents
import services.{ResourcePatternService, ResourceVariableService}

import scala.concurrent.ExecutionContext

class ResourceVariableCRUDController @Inject()(cc: ControllerComponents, resourceVariableService: ResourceVariableService)(implicit ec: ExecutionContext)
  extends AbstractCRUDController(cc) {

}
