package dao

import dao_.AbstractDao
import models.ResourceVariable

import scala.collection.JavaConverters._

class ResourceVariableDao extends AbstractDao[ResourceVariable](classOf[ResourceVariable]){
  def byPatternId(resourceId: Long): List[ResourceVariable] = {
    find().where().eq("resource_pattern_id", resourceId).findList().asScala.toList
  }
}
