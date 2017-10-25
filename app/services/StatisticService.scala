package services

import javax.inject.Inject

import dao.{ResourcePatternDao, ResourceValueDao, ResourceVariableDao, StatisticDao}
import models.{ResourcePattern, ResourceValue, ResourceVariable, Statistic}
import org.joda.time.{DateTime, LocalDateTime}
import output.{DataSet, DataSetPack}

class StatisticService @Inject()(statisticDao: StatisticDao, resourcePatternDao: ResourcePatternDao, resourceVariableDao: ResourceVariableDao, resourceValueDao: ResourceValueDao) {


  def byPattern(patternId: Long, view: String, start: LocalDateTime, end: LocalDateTime): List[DataSetPack] = {

    val pattern: ResourcePattern = resourcePatternDao.byId(patternId)
    val variables: List[ResourceVariable] = resourceVariableDao.byPatternId(pattern.getId)
    val possibleEntries = createChartData(List((pattern.pattern, List.empty)), variables)

    if(possibleEntries.count(s => s._2.size > 1) == possibleEntries.size){
      possibleEntries.groupBy(s => s._2.head).map(data => {
        DataSetPack(data._2.map( v1 =>
          DataSet(statisticDao.getData(v1._2.mkString(" - "), pattern.method, v1._1, start, end, view), v1._2.mkString(" - "))), data._1)
      }).toList
    } else {
      List(DataSetPack(possibleEntries.map(s => new DataSet(statisticDao.getData(s._2.mkString(" - "), pattern.method, s._1, start, end, view), s._2.mkString(" - "))), ""))
    }
  }

  def insertVariables(data: List[(String, List[String])], v: ResourceVariable): List[(String, List[String])] = {
    data.flatMap(data => {
      resourceValueDao.byVariableId(v.getId).map(value => {
        val variation = data._1.split("/").map(part => insertIfVariable(v, value, part)).mkString("/")
        (variation, data._2.+:(value.name))
      })
    })
  }

  def createChartData(data: List[(String, List[String])], variables: List[ResourceVariable]): List[(String, List[String])] = variables match {
    case h::t => createChartData(insertVariables(data, h), t)
    case _ => data
  }

  def insertValues(patternStrings: List[String], variables: List[ResourceVariable]): List[String] = variables match {
    case h :: t => insertValues(generateVariations(patternStrings, h),  t)
    case _ => patternStrings
  }

  private def generateVariations(patternStrings: List[String], variable: ResourceVariable) = {
    patternStrings.flatMap(patternString => {
      resourceValueDao.byVariableId(variable.getId).map(value => {
        patternString.split("/").map(part => insertIfVariable(variable, value, part)).mkString("/")
      })
    })
  }

  private def insertIfVariable(variable: ResourceVariable, value: ResourceValue, part: String) = {
    if (part.contains(":") && variable.name.equals(part.substring(1, part.length))) {
      value.uuid
    } else {
      part
    }
  }

  def create(statistic: Statistic): Statistic = {
    statisticDao.save(statistic)
    statisticDao.byId(statistic.id)
  }
}
