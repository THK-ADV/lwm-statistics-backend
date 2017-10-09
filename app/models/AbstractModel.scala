package models

import java.util.UUID
import javax.persistence.{Column, GeneratedValue, Id, MappedSuperclass}

import com.fasterxml.jackson.annotation.JsonFormat
import org.joda.time.{LocalDate, LocalDateTime}

import scala.beans.BeanProperty

@MappedSuperclass
class AbstractModel {

  @Id
  @GeneratedValue
  @BeanProperty
  var id:Long = 0

  @Column
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SS")
  @BeanProperty
  var created: LocalDateTime = LocalDateTime.now()

}

abstract class AbstractProtocol[T] {
  def toDBModel(): T
}
