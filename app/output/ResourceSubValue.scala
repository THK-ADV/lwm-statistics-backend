package output

import models.Statistic
import play.api.libs.json.{Json, Writes}

case class ResourceSubValue (label: String, values: List[Statistic])

object ResourceSubValue{
  implicit def writes: Writes[ResourceSubValue] = Json.writes[ResourceSubValue]
}
