package api

import com.typesafe.config.ConfigFactory

object API {
  private val api = ConfigFactory.load().getConfig("api")

  val version = api.getString("version")
  val baseUrl = api.getString("baseUrl") + version
}
