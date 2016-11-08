package resources

import java.util.concurrent.Executors

import api.SwaggerHttpDocument
import api.v1.{CompanyGet, CompanyList, CompanyPostWrapper, CompanyWrapper}
import com.github.xiaodongw.swagger.finatra.SwaggerSupport
import com.google.inject.Inject
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.util.FuturePool
import core.CompanyService
import domain.Company
import io.swagger.models.Swagger

class CompanyResource @Inject() (companyService: CompanyService) extends Controller with
  SwaggerSupport {

  import api.API._

  override protected implicit val swagger: Swagger = SwaggerHttpDocument
  val futurePool = FuturePool(Executors.newCachedThreadPool())

  getWithDoc(baseUrl + "/company") { o =>
    o.tags(List("Company"))
      .summary("List with all companies")
      .responseWith[ CompanyList ](200, "List with companies")

  } { request: Request =>
    futurePool(companyService.all).flatten map { comps =>
      val mapped = comps.map(comp => CompanyGet(comp.key, comp.companyName))
      response.ok.body(CompanyList(mapped))
    }

  }

  getWithDoc(baseUrl + "/company/:key") { o =>
    o.tag("Company")
      .summary("Provide company with given key")
      .routeParam[Long]("key", "company key")
      .responseWith[CompanyWrapper](200, "Found company")
      .responseWith[Unit](404, "Company not found with this key")
  } { request : Request =>
    val key = request.params.getLongOrElse("key", 0L)

    futurePool(companyService.find(key)).flatten map {
      case Some(comp) => response.ok.body(CompanyWrapper(CompanyGet(comp.key, comp.companyName)))
      case None => response.notFound
    }
  }

  putWithDoc(baseUrl + "/company") { o =>
    o.tag("Company")
      .summary("Store given company")
      .responseWith[Unit](200, "Company stored")
      .responseWith[Unit](500, "Could not store company")
      .consumes("application/json")
  } { request : CompanyPostWrapper =>
    val post = request.company
    val mapped = Company(None, post.companyName)
    futurePool(companyService.save(mapped)).flatten map {
      case true => response.ok
      case false => response.internalServerError()
    }
  }

  deleteWithDoc(baseUrl + "/company/:companyKey") { o =>
    o.tags(List("Company"))
      .summary("Delete a given company")
      .routeParam[ Long ]("companyKey", "Key from commpany to delete")
      .responseWith[ Unit ](200, "Company deleted")
      .responseWith[ Unit ](404, "Company not found")
      .consumes("application/json")
  } { request : Request =>
    val key = request.params.getLongOrElse("companyKey", 0)
    futurePool(companyService.delete(key)).flatten map {
      case 200 => response.ok
      case 404 => response.notFound
      case _ => response.internalServerError
    }
  }

}
