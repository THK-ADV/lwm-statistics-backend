package output


import models.Statistic
import org.joda.time.{LocalDate, LocalDateTime}
import play.api.libs.json._

case class DateValue (dataSetLabel: String, date: LocalDateTime, statistics: List[Statistic])
case class DataSet(data: List[DateValue], label: String)
case class DataSetPack(dataSets: List[DataSet], label: String)
case class DataPack(dataSetPacks: List[DataSetPack], label: String)


object DateValue{
  implicit def writesLocalDateTime: Writes[LocalDateTime] = (o: LocalDateTime) => Json.toJson(o.toString())
  implicit def writes: Writes[DateValue] = Json.writes[DateValue]
}
object DataSet{
  implicit def writes: Writes[DataSet] = Json.writes[DataSet]
}
object DataSetPack{
  implicit def writes: Writes[DataSetPack] = Json.writes[DataSetPack]
}
object DataPack{
  implicit def writes: Writes[DataPack] = Json.writes[DataPack]
}