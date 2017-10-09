package dao

import dao_.AbstractDao
import models.Resource

class ResourceDao extends AbstractDao[Resource](classOf[Resource]){

}
