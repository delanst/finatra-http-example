package core

import com.twitter.util.{Future => TwitterFuture}
import domain.Account

trait AccountService {
  def all: TwitterFuture[ Seq[ Account ] ]

  def find (userName: String): TwitterFuture[ Option[ Account ] ]

  def login (userName: String, password: String): TwitterFuture[ Option[ Account ] ]
}

class AccountServiceImpl extends AccountService {
  val accounts = Seq(
    Account("user1", "pass1"),
    Account("user2", "pass2"),
    Account("user3", "pass3")
  )

  override def all: TwitterFuture[ Seq[ Account ] ] = {
    TwitterFuture {
      accounts
    }
  }

  override def find (userName: String): TwitterFuture[ Option[ Account ] ] = {
    TwitterFuture {
      accounts.find(_.username == userName)
    }
  }

  override def login (userName: String, password: String): TwitterFuture[ Option[ Account ] ] = {
    TwitterFuture {
      accounts.find(u => u.username == userName & u.password == password)
    }
  }
}
