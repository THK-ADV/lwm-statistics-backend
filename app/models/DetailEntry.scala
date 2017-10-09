package models

import java.util.UUID
import javax.persistence.{Column, Entity, ManyToMany, ManyToOne}

import dao_.AbstractDao
import org.joda.time.{LocalDate, LocalDateTime}
import play.api.libs.json.{Json, Reads, Writes}

import scala.beans.BeanProperty

case class DetailEntryProtocol(label: String, entryId: String) extends AbstractProtocol[DetailEntry]{
  override def toDBModel(): DetailEntry = DetailEntry(label = label, entryId = entryId)
}

@Entity
class DetailEntry extends AbstractModel{

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


object DetailEntry extends AbstractDao(classOf[DetailEntry]){
  def apply(id:Long = 0, label: String, entryId: String, created: LocalDateTime = LocalDateTime.now()): DetailEntry = {
    val detailEntry = new DetailEntry()
    detailEntry.setId(id)
    detailEntry.setLabel(label)
    detailEntry.setEntryId(entryId)
    detailEntry.setCreated(created)
    detailEntry
  }

  def unapply(detailEntry: DetailEntry): Option[(Long, String, String, LocalDateTime)] = Some((detailEntry.getId, detailEntry.getLabel, detailEntry.getEntryId, detailEntry.getCreated))

  implicit def reads: Reads[DetailEntryProtocol] = Json.reads[DetailEntryProtocol]

  implicit def writes: Writes[DetailEntry] = Json.writes[DetailEntry]
}