package resources.filter

import com.google.inject.Inject
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.exceptions.ExceptionMapper
import com.twitter.finatra.http.response.ResponseBuilder

/**
  * Throw an unauthorized exception in order to respond with a 401 unauthorized response.
  */

class UnauthorizedExcetpionMapper @Inject()(response: ResponseBuilder)
  extends ExceptionMapper[UnauthorizedException] {

  override def toResponse(request: Request, exception: UnauthorizedException): Response = {
    response.unauthorized(s"Unauthorized access - ${exception.getMessage}")
  }
}

