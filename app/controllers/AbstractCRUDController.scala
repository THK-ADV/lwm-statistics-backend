package controllers

import dao_.AbstractDao
import models.Statistic
import play.api.libs.json._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}
import scala.util.control.NonFatal

class AbstractCRUDController(cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc){


  implicit def listReads[R](implicit r: Reads[R]): Reads[List[R]] = Reads.list[R]


  final protected def jsonResult[A](result: A)(implicit writes: Writes[A]): Result = {
    Ok(Json.obj("status" -> "OK", "result" -> Json.toJson(result)))
  }

  final protected def parseRequest[A](request: Request[JsValue])(implicit reads: Reads[A]): Try[A] = {
    request.body.validate[A].fold[Try[A]](
      errors => Failure(new Throwable(JsError.toJson(errors).toString())),
      success => Success(success)
    )
  }

  final protected def jsonFutureResult[A](result: Future[A])(implicit writes: Writes[A]): Future[Result] = {
    result.map(r =>
      jsonResult(r)
    ).recover {
      case NonFatal(exception) =>
        InternalServerError(Json.obj("status" -> "KO", "message" -> exception.getLocalizedMessage))
    }
  }
}
