package resources.module

import com.google.inject.{Provides, Singleton}
import com.twitter.inject.{Injector, TwitterModule}
import core.{CompanyServiceImpl, EmployeeService, EmployeeServiceImpl}

object EmployeeModule extends TwitterModule {

  @Singleton
  @Provides
  def provideService(injector: Injector): EmployeeService = {
    new EmployeeServiceImpl(new CompanyServiceImpl)
  }


}
