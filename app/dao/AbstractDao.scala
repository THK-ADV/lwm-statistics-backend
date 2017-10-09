package dao_

import io.ebean.{Ebean, Query}
import org.joda.time.{LocalDate, LocalDateTime}
import play.api.libs.json.{JsValue, Json, Writes}
import scala.collection.JavaConverters._

/**
  * Dao for a given Entity bean type.
  */
abstract class AbstractDao[T](cls: Class[T]) {

  def all(): List[T] = find().findList().asScala.toList

  /**
    * Find by Id.
    */
  def byId(id: Any): T = Ebean.find(cls, id)

  /**
    * Find with expressions and joins etc.
    */
  def find(): Query[T] = Ebean.find(cls)

  /**
    * Return a reference.
    */
  def ref(id: Any): T = Ebean.getReference(cls, id)

  /**
    * Save (insert or update).
    */
  def save(o: Any): Unit = Ebean.save(o)

  /**
    * Delete.
    */
  def delete(o: Any): Unit = Ebean.delete(o)

  implicit def writesLocalDateTime: Writes[LocalDateTime] = (o: LocalDateTime) => Json.toJson(o.toString())
}
