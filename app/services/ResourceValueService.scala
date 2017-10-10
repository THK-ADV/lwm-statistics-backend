package services

import javax.inject.Inject

import dao.ResourceValueDao
import models.ResourceValue

class ResourceValueService @Inject()(resourceValueDao: ResourceValueDao){

  def createByVaribale(id: Long): ResourceValue = {
    
  }

}
