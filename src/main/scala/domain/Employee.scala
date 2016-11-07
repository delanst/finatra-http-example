package domain

case class Employee(key : Option[Long], firstName : String, lastName : String, company : Company)

case class EmployseeSave(firstName : String, lastName : String, companyId : Long)