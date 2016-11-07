package api.v1

import com.fasterxml.jackson.annotation.JsonProperty

case class AccountGet (
  @JsonProperty("username") username : String,
  @JsonProperty("password") password : String
)

case class AccountList(
  @JsonProperty("accounts") accounts : Seq[AccountGet]
)

case class AccountPost(
  @JsonProperty("account") account : AccountGet
)
