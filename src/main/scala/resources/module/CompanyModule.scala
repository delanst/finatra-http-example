package resources.module


import com.google.inject.{Provides, Singleton}
import com.twitter.inject.{Injector, TwitterModule}
import core.{CompanyService, CompanyServiceImpl}

object CompanyModule extends TwitterModule {

  @Singleton
  @Provides
  def provideService (injector: Injector): CompanyService = {
    new CompanyServiceImpl
  }
}

