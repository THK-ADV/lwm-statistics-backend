package models

import java.util.UUID
import javax.persistence.{Column, Entity, ManyToMany, OneToMany}

import com.fasterxml.jackson.annotation.{JsonInclude, JsonProperty}
import dao_.AbstractDao
import org.joda.time.{LocalDate, LocalDateTime}
import play.api.libs.json.{JsValue, Json, Reads, Writes}

import scala.beans.BeanProperty

case class ResourceProtocol(label: String, method: String, filter: String, detail: String) extends AbstractProtocol[Resource]{
  override def toDBModel(): Resource = Resource(label = label, method = method, filter = filter, detail = detail)
}

@Entity
class Resource extends AbstractModel{

  @Column
  @BeanProperty
  var label:String = _

  @Column
  @BeanProperty
  var method: String = _

  @Column
  @BeanProperty
  var filter: String = _

  @Column
  @BeanProperty
  var detail: String = _

  @OneToMany
  @BeanProperty
  var resourceEntries: List[ResourceEntry] = List()

  @OneToMany
  @BeanProperty
  var detailEntries: List[DetailEntry] = List()

}


object Resource extends AbstractDao(classOf[Resource]){
  def apply(id:Long = 0, label: String, method: String, filter: String, detail: String, created: LocalDateTime = LocalDateTime.now): Resource = {
    val resource = new Resource()
    resource.setId(id)
    resource.setLabel(label)
    resource.setMethod(method)
    resource.setFilter(filter)
    resource.setDetail(detail)
    resource.setCreated(created)
    resource
  }

  def unapply(resource: Resource): Option[(Long, String, String, String, String, LocalDateTime)] = Some((resource.getId, resource.getLabel, resource.getMethod, resource.getFilter, resource.getDetail, resource.created))

  implicit def reads: Reads[ResourceProtocol] = Json.reads[ResourceProtocol]

  implicit def writes: Writes[Resource] = Json.writes[Resource]
}