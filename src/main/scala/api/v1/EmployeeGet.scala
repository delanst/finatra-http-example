package api.v1

import com.fasterxml.jackson.annotation.JsonProperty

case class EmployeeGet (
  @JsonProperty("key") key : Option[Long],
  @JsonProperty("firstName")firstName : String,
  @JsonProperty("lastName")lastName : String,
  @JsonProperty("commpany")company : CompanyGet
)

case class EmployeeList(
  @JsonProperty("employees") employees : Seq[EmployeeGet]
)

case class EmployeePost(
  @JsonProperty("firstName")firstName : String,
  @JsonProperty("lastName")lastName : String,
  @JsonProperty("companyId")companyId : Long
)

case class EmployeeWrapper(
  @JsonProperty("employee") employee : EmployeeGet
)

case class EmployeePostWrapper(
  @JsonProperty("employee") employee : EmployeePost
)