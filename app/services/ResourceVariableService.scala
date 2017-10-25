package services

import javax.inject.Inject

import dao.ResourceVariableDao
import models.ResourceVariable

class ResourceVariableService @Inject()(resourceVariableDao: ResourceVariableDao, resourceValueService: ResourceValueService){


  def delete(id: Long): Boolean = {
    resourceValueService.byVariable(id).foreach(value => resourceValueService.delete(value.id))
    resourceVariableDao.delete(id)
    true
  }


  def byPatternId(id: Long): List[ResourceVariable] = {
    resourceVariableDao.byPatternId(id)
  }

  def byName(patternId: Long, name: String): ResourceVariable = {
    resourceVariableDao.find().where().eq("name", name).and().eq("resource_pattern_id", patternId).findUnique()
  }

}
