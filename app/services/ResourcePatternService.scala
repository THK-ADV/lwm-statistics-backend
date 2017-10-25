package services

import javax.inject.{Inject, Singleton}

import dao.{ResourcePatternDao, ResourceVariableDao}
import models.{ResourcePattern, ResourceVariable}

@Singleton
class ResourcePatternService @Inject()(resourcePatternDao: ResourcePatternDao, resourceVariableDao: ResourceVariableDao, resourceVariableService: ResourceVariableService) {


  def createWithVariables(resourcePattern: ResourcePattern): ResourcePattern = {
    val variables = resourcePattern.getPattern.split("/").filter(subPath => subPath.contains(":")).map(varString => ResourceVariable(name = varString.substring(1,varString.length))).toList
    resourcePatternDao.save(resourcePattern)

    variables.foreach(println)
    variables.foreach(variable => {
      variable.setResourcePattern(resourcePattern)
      resourceVariableDao.save(variable)
    })

    resourcePatternDao.byId(resourcePattern.getId)
  }

  def all(): List[ResourcePattern] = {
    resourcePatternDao.all()
  }

  def byId(id: Long): ResourcePattern = {
    resourcePatternDao.byId(id)
  }

  def delete(id: Long): Boolean = {
    resourceVariableService.byPatternId(id).foreach(variable => resourceVariableService.delete(variable.id))
    resourcePatternDao.delete(id)
    true
  }

}
