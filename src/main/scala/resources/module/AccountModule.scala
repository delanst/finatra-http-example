package resources.module

import com.google.inject.{Provides, Singleton}
import com.twitter.inject.{Injector, TwitterModule}
import core.{AccountService, AccountServiceImpl}

object AccountModule extends TwitterModule {

  @Singleton
  @Provides
  def provideService (injector: Injector): AccountService = {
    new AccountServiceImpl
  }

}
