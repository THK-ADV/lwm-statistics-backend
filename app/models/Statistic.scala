package models

import java.util.UUID
import javax.persistence.{Column, Entity}

import dao_.AbstractDao
import org.joda.time.{LocalDate, LocalDateTime}
import play.api.libs.json._

import scala.beans.BeanProperty

case class StatisticProtocol(method: String, description: String, payload: String, username: String) extends AbstractProtocol[Statistic]{
  override def toDBModel(): Statistic = Statistic(method = method, description = description, payload = payload, username = username)
}

@Entity
class Statistic extends AbstractModel{

  @Column
  @BeanProperty
  var method:String = _

  @Column
  @BeanProperty
  var description: String = _

  @Column
  @BeanProperty
  var payload: String = _

  @Column
  @BeanProperty
  var username: String = _
}


object Statistic extends AbstractDao(classOf[Statistic]){
  def apply(id:Long = 0, method: String, description: String, payload: String, username: String, created: LocalDateTime = LocalDateTime.now): Statistic = {
    val statistic = new Statistic()
    statistic.setId(id)
    statistic.setMethod(method)
    statistic.setDescription(description)
    statistic.setPayload(payload)
    statistic.setUsername(username)
    statistic.setCreated(created)
    statistic
  }

  def unapply(statistic: Statistic): Option[(Long, String, String, String, String, LocalDateTime)] = Some((statistic.getId, statistic.getMethod, statistic.getDescription, statistic.getPayload, statistic.getUsername, statistic.getCreated))

  implicit def reads: Reads[StatisticProtocol] = Json.reads[StatisticProtocol]

  implicit def writes: Writes[Statistic] = Json.writes[Statistic]
}