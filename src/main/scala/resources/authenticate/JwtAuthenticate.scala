package resources.authenticate

import authentikat.jwt.{JsonWebToken, JwtClaimsSet, JwtHeader}
import com.twitter.finagle.http.Request
import com.twitter.inject.Logging
import resources.filter.UnauthorizedException

object JwtAuthenticate extends Logging {
  // in this case we use one secret key for all the generated tokens.
  // you can also apply a different secret key by user.
  private val secretKey = "@3zTp!2ORéA&4#Z39"

  def generateToken (user: String): String = {
    val header = JwtHeader("HS256")
    val now = System.currentTimeMillis()

    // provide your own json web token structure. A timestamp is a good way of generating
    // different looking tokens. You can also invalid token based  on the time.
    val claimsSet = JwtClaimsSet(
      Map("user" -> user, "now" -> now)
    )

    JsonWebToken(header, claimsSet, secretKey)
  }

  def validateToken (jwt: String): Boolean = {
    val validKey = JsonWebToken.validate(jwt, secretKey)

    val timed = claims(jwt) match {
      case Some(value) => value.find(_._1 == "now") match {
        case Some(time) =>
          // invalidate a generated tokens where time is larger then 2 hours.
          // This is not the best way but you get the idea. Implement your own idea about this.
          val difference = System.currentTimeMillis() - time._2.toLong
          if(difference > 7200000) false else true
        case None => false
      }
      case None => false
    }

    validKey && timed
  }

  def claims (jwt: String): Option[ Map[ String, String ] ] = {
    jwt match {
      case JsonWebToken(header, claimsSet, signature) =>
        claimsSet.asSimpleMap.toOption
      case x =>
        None
    }

  }

  def validate (request: Request): Unit = {
    val authorization = request.headerMap.getOrElse("Authorization", "")

    if (!authorization.startsWith("Bearer")) {
      throw new UnauthorizedException("Invalid token start.")
    }

    val token = Some(authorization.split(" ")(1))

    JwtAuthenticate.validateToken(token.getOrElse("")) match {
      case false => throw new UnauthorizedException("Access denied. Invalid token or expired.")
      case _ =>
    }
  }


}
