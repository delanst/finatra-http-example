package resources

import api.API._
import com.twitter.finatra.http.response.ResponseBuilder

object CreateResponse {

  def ok(response : ResponseBuilder) : response.EnrichedResponse = {
    response.ok
      .header("Access-Control-Allow-Origin", corsServer)
      .header("Access-Control-Allow-Methods", "HEAD, GET, PUT, POST, DELETE, OPTIONS")
      .header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization")
      .contentType("application/json")
  }

  def unAuthorized(response : ResponseBuilder) : response.EnrichedResponse = {
    response.unauthorized
      .header("Access-Control-Allow-Origin", corsServer)
      .header("Access-Control-Allow-Methods", "HEAD, GET, PUT, POST, DELETE, OPTIONS")
      .header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization")
      .contentType("application/json")
  }

  def error(response : ResponseBuilder) : response.EnrichedResponse = {
    response.internalServerError
      .header("Access-Control-Allow-Origin", corsServer)
      .header("Access-Control-Allow-Methods", "HEAD, GET, PUT, POST, DELETE, OPTIONS")
      .header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization")
      .contentType("application/json")
  }

  def notFound(response : ResponseBuilder) : response.EnrichedResponse = {
    response.notFound
      .header("Access-Control-Allow-Origin", corsServer)
      .header("Access-Control-Allow-Methods", "HEAD, GET, PUT, POST, DELETE, OPTIONS")
      .header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization")
      .contentType("application/json")
  }

}
