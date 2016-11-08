package resources

import java.util.concurrent.Executors

import api.SwaggerHttpDocument
import api.v1._
import com.github.xiaodongw.swagger.finatra.SwaggerSupport
import com.google.inject.Inject
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.util.FuturePool
import core.EmployeeService
import domain.EmployseeSave
import io.swagger.models.Swagger


class EmployeeResource @Inject() (employeeService: EmployeeService) extends Controller with
  SwaggerSupport {

  import api.API._

  override protected implicit val swagger: Swagger = SwaggerHttpDocument

  val futurePool = FuturePool(Executors.newCachedThreadPool())

  getWithDoc(baseUrl + "/employee") { o =>
    o.tags(List("Employee"))
      .summary("List of all employees")
      .responseWith[ EmployeeList ](200, "All employees")
      .produces("application/json")
  } { request: Request =>
    futurePool(employeeService.all).flatten map { employees =>
      val mapped = employees.map(emp =>
        EmployeeGet(emp.key, emp.firstName, emp.lastName, CompanyGet(emp.company.key,
          emp.company.companyName))
      )
      CreateResponse.ok(response).body(EmployeeList(mapped))
    }
  }

  getWithDoc(baseUrl + "/employee/:employeeKey") { o =>
    o.tags(List("Employee"))
      .summary("Provide employee with given identification")
      .routeParam[ String ]("employeeKey", "Employee identification")
      .responseWith[ EmployeeWrapper ](200, "Found employee")
      .responseWith[ Unit ](404, "Employee not found")
      .consumes("application/json")
  } { request: Request =>
    val findWithKey = request.params.getLong("employeeKey")
    futurePool(employeeService.find(findWithKey.getOrElse(-1))).flatten map {
      case Some(emp) => CreateResponse.ok(response).body(emp)
      case None => CreateResponse.notFound(response)
    }
  }

  putWithDoc(baseUrl + "/employee") { o =>
    o.tags(List("Employee"))
      .summary("Save a given employee")
      .bodyParam[ EmployeePostWrapper ]("employee", "Employee to store")
      .responseWith[ Unit ](200, "Employee stored")
      .consumes("application/json")
  } { request: EmployeePostWrapper =>
    val post = request.employee
    val mapped = EmployseeSave(post.firstName, post.lastName, post.companyId)
    futurePool(employeeService.save(mapped)).flatten map {
      case true => CreateResponse.ok(response)
      case false => CreateResponse.error(response)
    }
  }

  deleteWithDoc(baseUrl + "/employee/:employeeKey") { o =>
    o.tags(List("Employee"))
      .summary("Delete a given employee")
      .routeParam[ Long ]("employeeKey", "Key from employee to delete")
      .responseWith[ Unit ](200, "Employee deleted")
      .responseWith[ Unit ](404, "Employee not found")
      .consumes("application/json")
  } { request: Request =>
    val key = request.params.getLongOrElse("employeeKey", 0)
    futurePool(employeeService.delete(key)).flatten map {
      case 200 => CreateResponse.ok(response)
      case 404 => CreateResponse.notFound(response)
      case _ => CreateResponse.error(response)
    }
  }

}
