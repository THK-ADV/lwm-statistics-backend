package services

import javax.inject.Inject

import dao.ResourceVariableDao
import models.ResourceVariable

class ResourceVariableService @Inject()(resourceVariableDao: ResourceVariableDao){

  def byPatternId(id: Long): List[ResourceVariable] = {
    resourceVariableDao.byPatternId(id)
  }

}
