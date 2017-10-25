package dao

import dao_.AbstractDao
import models.ResourceValue
import scala.collection.JavaConverters._

class ResourceValueDao extends AbstractDao[ResourceValue](classOf[ResourceValue]){

  def byVariableId(id: Long): List[ResourceValue] = {
    find().where().eq("resource_variable_id", id).findList().asScala.toList
  }

}
