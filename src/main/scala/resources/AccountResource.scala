package resources

import api.SwaggerHttpDocument
import api.v1.AccountPost
import com.github.xiaodongw.swagger.finatra.SwaggerSupport
import com.google.inject.Inject
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import core.AccountService
import io.swagger.models.Swagger
import resources.authenticate.JwtAuthenticate

class AccountResource @Inject()(accountService : AccountService) extends Controller with
  SwaggerSupport {
  import api.API._

  override protected implicit val swagger: Swagger = SwaggerHttpDocument

  postWithDoc(baseUrl + "/account/authenticate") { o =>
    o.tags(List("Account"))
      .summary("Authenticate for the application")
      .bodyParam[AccountPost]("accountPost", "username and password to authenticate with")
      .responseWith[Unit](200, "Bearer token in the response header")
      .responseWith[Unit](404, "Account does not exist")
  } { request : AccountPost =>
    val accountPost = request.account

   accountService.login(accountPost.username, accountPost.password) map {
      case Some(user) => response.ok
        .header("Access-Control-Expose-Headers", "Authorization")
        .header("Authorization", "Bearer " + JwtAuthenticate.generateToken(user.username))
      case None => response.notFound
    }
  }


}
