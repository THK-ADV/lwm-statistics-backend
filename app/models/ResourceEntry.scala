package models

import java.util.UUID
import javax.persistence.{Column, Entity, ManyToMany, ManyToOne}

import dao_.AbstractDao
import org.joda.time.{LocalDate, LocalDateTime}
import play.api.libs.json.{Json, Reads, Writes}

import scala.beans.BeanProperty

case class ResourceEntryProtocol(label: String, entryId: String) extends AbstractProtocol[ResourceEntry]{
  override def toDBModel(): ResourceEntry = ResourceEntry(label = label, entryId = entryId)
}

@Entity
class ResourceEntry extends AbstractModel{

  @Column
  @BeanProperty
  var label:String = _

  @Column
  @BeanProperty
  var entryId:String = _

  @ManyToOne
  @BeanProperty
  var resource: Resource = _
}


object ResourceEntry extends AbstractDao(classOf[ResourceEntry]){
  def apply(id:Long = 0, label: String, entryId: String, created: LocalDateTime = LocalDateTime.now): ResourceEntry = {
    val resourceEntry = new ResourceEntry()
    resourceEntry.setId(id)
    resourceEntry.setLabel(label)
    resourceEntry.setEntryId(entryId)
    resourceEntry.setCreated(created)
    resourceEntry
  }

  def unapply(resourceEntry: ResourceEntry): Option[(Long, String, String, LocalDateTime)] = Some((resourceEntry.getId, resourceEntry.getLabel, resourceEntry.getEntryId, resourceEntry.getCreated))

  implicit def reads: Reads[ResourceEntryProtocol] = Json.reads[ResourceEntryProtocol]

  implicit def writes: Writes[ResourceEntry] = Json.writes[ResourceEntry]
}