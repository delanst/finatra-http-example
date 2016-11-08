package core

import com.google.inject.Inject
import com.twitter.util.{Future => TwitterFuture}
import domain.{Company, Employee, EmployseeSave}

trait EmployeeService {
  def all: TwitterFuture[ Seq[ Employee ] ]

  def find (key: Long): TwitterFuture[ Option[ Employee ] ]

  def save(employee : EmployseeSave) : TwitterFuture[Boolean]

  def delete(employeeKey : Long) : TwitterFuture[Int]
}

class EmployeeServiceImpl @Inject() (companyService: CompanyService) extends EmployeeService {
  val employees: Seq[ Employee ] = Seq(
    Employee(Some(123), "John", "Doe", Company(Some(1), "Google")),
    Employee(Some(123), "Jane", "Doe", Company(Some(1), "Apple"))
  )

  override def all: TwitterFuture[ Seq[ Employee ] ] = {
    TwitterFuture {
      employees
    }
  }

  override def find (key: Long): TwitterFuture[ Option[ Employee ] ] = {
    TwitterFuture {
      employees.find(_.key.getOrElse(0) == key)
    }
  }

  override def save (employee: EmployseeSave): TwitterFuture[ Boolean ] = {
    TwitterFuture {
      // save your employee
      true
    }
  }

  override def delete (employeeKey: Long): TwitterFuture[ Int ] = {
    TwitterFuture {
      // delete your employee
      1
    }
  }
}