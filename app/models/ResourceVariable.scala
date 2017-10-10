package models

import java.util.UUID
import javax.persistence._

import com.fasterxml.jackson.annotation.{JsonInclude, JsonProperty}
import dao_.AbstractDao
import org.joda.time.{LocalDate, LocalDateTime}
import play.api.libs.json.{JsValue, Json, Reads, Writes}

import scala.beans.BeanProperty

case class ResourceVariableProtocol(name: String) extends AbstractProtocol[ResourceVariable]{
  override def toDBModel(): ResourceVariable = ResourceVariable(name = name)
}

@Entity
class ResourceVariable extends AbstractModel{

  @Column
  @BeanProperty
  var name:String = _

  @ManyToOne
  @BeanProperty
  var resourcePattern: ResourcePattern = _

  @OneToMany
  @BeanProperty
  var resourceValues: List[ResourceValue] = List()

}


object ResourceVariable extends AbstractDao(classOf[ResourceVariable]){
  def apply(id:Long = 0, name: String, created: LocalDateTime = LocalDateTime.now): ResourceVariable = {
    val resourceVariable = new ResourceVariable()
    resourceVariable.setId(id)
    resourceVariable.setName(name)
    resourceVariable.setCreated(created)
    resourceVariable
  }

  def unapply(resourceVariable: ResourceVariable): Some[(Long, String, LocalDateTime)] = Some((resourceVariable.getId, resourceVariable.getName, resourceVariable.created))

  implicit def reads: Reads[ResourceVariableProtocol] = Json.reads[ResourceVariableProtocol]

  implicit def writes: Writes[ResourceVariable] = Json.writes[ResourceVariable]
}