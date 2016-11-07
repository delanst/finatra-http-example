package api.v1

import com.fasterxml.jackson.annotation.JsonProperty

case class CompanyGet (
  @JsonProperty("key") key : Option[Long],
  @JsonProperty("companyName") companyName : String
)

case class CompanyList(
  @JsonProperty("companies") companies : Seq[CompanyGet]
)

case class CompanyWrapper(
  @JsonProperty("company") company : CompanyGet
)

case class CompanyPost(
  @JsonProperty("companyName") companyName : String
)

case class CompanyPostWrapper(
  @JsonProperty("company") company : CompanyPost
)
