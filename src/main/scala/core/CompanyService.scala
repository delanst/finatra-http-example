package core

import domain.Company
import com.twitter.util.{Future => TwitterFuture}

trait CompanyService {
  def all: TwitterFuture[ Seq[ Company ] ]

  def find (key: Long): TwitterFuture[ Option[ Company ] ]

  def save (employee: Company): TwitterFuture[ Boolean ]

  def delete (companyKey: Long): TwitterFuture[ Int ]
}

class CompanyServiceImpl extends CompanyService {
  val companies = Seq(
    Company(Some(1), "Google"),
    Company(Some(2), "Apple"),
    Company(Some(3), "Samsung")
  )

  override def all: TwitterFuture[ Seq[ Company ] ] = {
    TwitterFuture {
      companies
    }
  }

  override def find (key: Long): TwitterFuture[ Option[ Company ] ] = {
    TwitterFuture {
      companies.find(_.key.get == key)
    }
  }

  override def save (employee: Company): TwitterFuture[ Boolean ] = {
    TwitterFuture {
      // save your company
      true
    }
  }

  override def delete (companyKey: Long): TwitterFuture[ Int ] = {
    TwitterFuture {
      // delete your company
      1
    }
  }
}
