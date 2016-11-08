package resources

import api.SwaggerHttpDocument
import api.v1.PreflightOptions
import com.github.xiaodongw.swagger.finatra.SwaggerSupport
import com.twitter.finatra.http.Controller
import io.swagger.models.Swagger

class PreflightResource extends Controller with SwaggerSupport {
  import api.API._

  override protected implicit val swagger: Swagger = SwaggerHttpDocument

  optionsWithDoc(baseUrl + "/:*") { o =>
    o.tag("Preflight")
      .summary("This is the pre-flight option configuration")
      .responseWith[Unit](200, "Successfull preflight")
      .headerParam[String]("Access-Control-Allow-Origin", "cors server")
      .headerParam[String]("Access-Control-Allow-Methods", "allowed methods")
      .headerParam[String]("Access-Control-Allow-Headers", "allowed headers")
      .produces("application/json")
  } { request : PreflightOptions =>
    response.ok
      .header("Access-Control-Allow-Origin", corsServer)
      .header("Access-Control-Allow-Methods", "HEAD, GET, PUT, POST, DELETE, OPTIONS")
      .header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization")
      .contentType("application/json")
  }
}
