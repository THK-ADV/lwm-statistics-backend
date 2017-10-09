package dao

import dao_.AbstractDao
import models.{Resource, ResourceEntry}

import scala.collection.JavaConverters._

class ResourceEntryDao extends AbstractDao[ResourceEntry](classOf[ResourceEntry]){

  def byResource(resource: Resource): List[ResourceEntry] = find().where().eq("resource",resource).findList().asScala.toList

}
