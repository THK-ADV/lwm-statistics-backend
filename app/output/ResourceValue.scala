package output

import play.api.libs.json.{Json, Writes}

case class ResourceValue(label: String, subs: List[ResourceSubValue])

object ResourceValue{
  implicit def writes: Writes[ResourceValue] = Json.writes[ResourceValue]
}