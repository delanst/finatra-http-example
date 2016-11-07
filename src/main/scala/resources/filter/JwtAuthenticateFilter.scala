package resources.filter

import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Future
import resources.authenticate.JwtAuthenticate

class JwtAuthenticateFilter extends SimpleFilter[Request, Response] {
  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    if(request.method.toString() != "OPTIONS")  {
      JwtAuthenticate.validate(request)
    }
    service(request)
  }
}