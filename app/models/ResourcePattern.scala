package models

import javax.persistence._

import dao_.AbstractDao
import org.joda.time.LocalDateTime
import play.api.libs.json.{Json, Reads, Writes}

import scala.beans.BeanProperty

case class ResourcePatternProtocol(label: String, pattern: String, method: String) extends AbstractProtocol[ResourcePattern]{
  override def toDBModel(): ResourcePattern = ResourcePattern(label = label, method = method, pattern = pattern)
}

@Entity
class ResourcePattern extends AbstractModel{

  @Column
  @BeanProperty
  var label:String = _

  @Column
  @BeanProperty
  var pattern: String = _

  @OneToMany
  @BeanProperty
  var variables: List[ResourceVariable] = List()

  @OneToMany
  @BeanProperty
  var method: String = "GET"

}


object ResourcePattern extends AbstractDao(classOf[Resource]){
  def apply(id:Long = 0, label: String, pattern: String, method: String, created: LocalDateTime = LocalDateTime.now): ResourcePattern = {
    val resourcePattern = new ResourcePattern()
    resourcePattern.setId(id)
    resourcePattern.setLabel(label)
    resourcePattern.setPattern(pattern)
    resourcePattern.setCreated(created)
    resourcePattern.setMethod(method)
    resourcePattern
  }

  def unapply(resourcePattern: ResourcePattern): Option[(Long, String, String, String, LocalDateTime)] = Some((resourcePattern.getId, resourcePattern.getLabel, resourcePattern.getPattern, resourcePattern.getMethod, resourcePattern.created))

  implicit def reads: Reads[ResourcePatternProtocol] = Json.reads[ResourcePatternProtocol]

  implicit def writes: Writes[ResourcePattern] = Json.writes[ResourcePattern]
}