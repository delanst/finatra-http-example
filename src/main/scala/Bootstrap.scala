import api.SwaggerHttpDocument
import com.github.xiaodongw.swagger.finatra.{SwaggerController, WebjarsController}
import com.google.inject.Module
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import io.swagger.models.Info
import resources.{AccountResource, CompanyResource, EmployeeResource, PreflightResource}
import resources.filter.{JwtAuthenticateFilter, UnauthorizedExcetpionMapper}
import resources.module.{AccountModule, CompanyModule, EmployeeModule}

object Bootstrap extends BootstrapRestServer

class BootstrapRestServer extends HttpServer {

  override protected def disableAdminHttpServer: Boolean = false
  override val defaultFinatraHttpPort: String = ":8888"

  override protected def modules: Seq[ Module ] = Seq(EmployeeModule, CompanyModule, AccountModule)

  val info = new Info()
    .description("Data access layer API only for the dashboard.")
    .version("1.0.0")
    .title("Dashboard API")
  SwaggerHttpDocument.info(info)

  override def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[WebjarsController]
      .add(new SwaggerController(swagger = SwaggerHttpDocument))
      .add[PreflightResource] // add preflight options with CORS
      .add[AccountResource] // do NOT add authentication filter here
      .add[JwtAuthenticateFilter, EmployeeResource]
      .add[JwtAuthenticateFilter, CompanyResource]
      .exceptionMapper[UnauthorizedExcetpionMapper]
  }

}
