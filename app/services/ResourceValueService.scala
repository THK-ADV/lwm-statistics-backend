package services

import javax.inject.Inject

import dao.{ResourceValueDao, ResourceVariableDao}
import models.{ResourceValue, ResourceVariable}

class ResourceValueService @Inject()(resourceValueDao: ResourceValueDao, resourceVariableDao: ResourceVariableDao){


  def byVariable(id: Long): List[ResourceValue] = {
    resourceValueDao.byVariableId(id)
  }


  def createByVaribale(id: Long, resourceValue: ResourceValue): ResourceValue = {
    val resourceVariable: ResourceVariable = resourceVariableDao.byId(id)
    resourceValue.setResourceVariable(resourceVariable)
    resourceValueDao.save(resourceValue)
    resourceValueDao.byId(resourceValue.getId)
  }



  def delete(id: Long): Boolean = {
    resourceValueDao.delete(id)
    true
  }

}
