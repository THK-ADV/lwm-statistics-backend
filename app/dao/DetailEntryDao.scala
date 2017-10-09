package dao

import dao_.AbstractDao
import models.{DetailEntry, Resource}
import scala.collection.JavaConverters._

class DetailEntryDao extends AbstractDao[DetailEntry](classOf[DetailEntry]){

  def byResource(resource: Resource): List[DetailEntry] = find().where().eq("resource",resource).findList().asScala.toList
}
