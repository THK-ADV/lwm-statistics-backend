package models

import javax.persistence._

import dao_.AbstractDao
import org.joda.time.LocalDateTime
import play.api.libs.json.{Json, Reads, Writes}

import scala.beans.BeanProperty

case class ResourceValueProtocol(name: String, uuid: String) extends AbstractProtocol[ResourceValue]{
  override def toDBModel(): ResourceValue = ResourceValue(name = name, uuid = uuid)
}

@Entity
class ResourceValue extends AbstractModel{

  @Column
  @BeanProperty
  var name:String = _

  @Column
  @BeanProperty
  var uuid: String = _

  @ManyToOne
  @BeanProperty
  var resourceVariable: ResourceVariable = _


}


object ResourceValue extends AbstractDao(classOf[ResourceValue]){
  def apply(id:Long = 0, name: String, uuid: String, created: LocalDateTime = LocalDateTime.now): ResourceValue = {
    val resourceValue = new ResourceValue()
    resourceValue.setId(id)
    resourceValue.setName(name)
    resourceValue.setUuid(uuid)
    resourceValue.setCreated(created)
    resourceValue
  }

  def unapply(resourceValue: ResourceValue): Some[(Long, String, String, LocalDateTime)] = Some((resourceValue.getId, resourceValue.getName, resourceValue.getUuid, resourceValue.created))

  implicit def reads: Reads[ResourceValueProtocol] = Json.reads[ResourceValueProtocol]

  implicit def writes: Writes[ResourceValue] = Json.writes[ResourceValue]
}