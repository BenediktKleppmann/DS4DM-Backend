
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/UNI-Mannheim/Documents/DS4DM_backend3/DS4DM-Webservice/DS4DM_webservice/conf/routes
// @DATE:Sun May 20 19:48:13 CEST 2018

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:6
  Application_1: controllers.Application,
  // @LINE:7
  ExtendTable_2: controllers.ExtendTable,
  // @LINE:9
  Assets_0: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    Application_1: controllers.Application,
    // @LINE:7
    ExtendTable_2: controllers.ExtendTable,
    // @LINE:9
    Assets_0: controllers.Assets
  ) = this(errorHandler, Application_1, ExtendTable_2, Assets_0, "/")

  import ReverseRouteContext.empty

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, Application_1, ExtendTable_2, Assets_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.Application.index()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """ind""", """controllers.ExtendTable.ind()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """getRepositoryNames""", """controllers.ExtendTable.getRepositoryNames()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """getRepositoryStatistics""", """controllers.ExtendTable.getRepositoryStatistics(repository:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """suggestAttributes""", """controllers.ExtendTable.suggestAttributes()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """old_search""", """controllers.ExtendTable.search()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """unconstrainedSearch""", """controllers.ExtendTable.unconstrainedSearch(repository:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """search""", """controllers.ExtendTable.extendedSearch_T2DGoldstandard()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """correlationBasedSearch""", """controllers.ExtendTable.correlationBasedSearch(repository:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """fetchTable""", """controllers.ExtendTable.fetchTable_T2DGoldstandard(name:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """fetchTablePOST""", """controllers.ExtendTable.fetchTablePOST(repositoryName:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """createRepository""", """controllers.ExtendTable.createRepository(repository:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """createRepository""", """controllers.ExtendTable.createRepository(repository:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """uploadTable""", """controllers.ExtendTable.uploadTable(repository:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """bulkUploadTables""", """controllers.ExtendTable.moderateBulkUploadTables(repository:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """generateCorrespondences""", """controllers.ExtendTable.generateCorrespondences(repository:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """deleteRepository""", """controllers.ExtendTable.deleteRepository(repository:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """getUploadStatus""", """controllers.ExtendTable.getUploadStatus(repository:String, uploadID:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """test1""", """controllers.ExtendTable.Test1()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """test2""", """controllers.ExtendTable.Test2()"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:6
  private[this] lazy val controllers_Application_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_Application_index0_invoker = createInvoker(
    Application_1.index(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "index",
      Nil,
      "GET",
      """ functions defined by Java Play""",
      this.prefix + """"""
    )
  )

  // @LINE:7
  private[this] lazy val controllers_ExtendTable_ind1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("ind")))
  )
  private[this] lazy val controllers_ExtendTable_ind1_invoker = createInvoker(
    ExtendTable_2.ind(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExtendTable",
      "ind",
      Nil,
      "GET",
      """""",
      this.prefix + """ind"""
    )
  )

  // @LINE:9
  private[this] lazy val controllers_Assets_versioned2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned2_invoker = createInvoker(
    Assets_0.versioned(fakeValue[String], fakeValue[Asset]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      """ Map static resources from the /public folder to the /assets URL path""",
      this.prefix + """assets/$file<.+>"""
    )
  )

  // @LINE:12
  private[this] lazy val controllers_ExtendTable_getRepositoryNames3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("getRepositoryNames")))
  )
  private[this] lazy val controllers_ExtendTable_getRepositoryNames3_invoker = createInvoker(
    ExtendTable_2.getRepositoryNames(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExtendTable",
      "getRepositoryNames",
      Nil,
      "GET",
      """ Get info functions""",
      this.prefix + """getRepositoryNames"""
    )
  )

  // @LINE:13
  private[this] lazy val controllers_ExtendTable_getRepositoryStatistics4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("getRepositoryStatistics")))
  )
  private[this] lazy val controllers_ExtendTable_getRepositoryStatistics4_invoker = createInvoker(
    ExtendTable_2.getRepositoryStatistics(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExtendTable",
      "getRepositoryStatistics",
      Seq(classOf[String]),
      "GET",
      """""",
      this.prefix + """getRepositoryStatistics"""
    )
  )

  // @LINE:14
  private[this] lazy val controllers_ExtendTable_suggestAttributes5_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("suggestAttributes")))
  )
  private[this] lazy val controllers_ExtendTable_suggestAttributes5_invoker = createInvoker(
    ExtendTable_2.suggestAttributes(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExtendTable",
      "suggestAttributes",
      Nil,
      "POST",
      """""",
      this.prefix + """suggestAttributes"""
    )
  )

  // @LINE:18
  private[this] lazy val controllers_ExtendTable_search6_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("old_search")))
  )
  private[this] lazy val controllers_ExtendTable_search6_invoker = createInvoker(
    ExtendTable_2.search(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExtendTable",
      "search",
      Nil,
      "POST",
      """ table search related functions""",
      this.prefix + """old_search"""
    )
  )

  // @LINE:22
  private[this] lazy val controllers_ExtendTable_unconstrainedSearch7_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("unconstrainedSearch")))
  )
  private[this] lazy val controllers_ExtendTable_unconstrainedSearch7_invoker = createInvoker(
    ExtendTable_2.unconstrainedSearch(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExtendTable",
      "unconstrainedSearch",
      Seq(classOf[String]),
      "POST",
      """ POST /search 						controllers.ExtendTable.extendedSearch(repository)
 POST /search 						controllers.ExtendTable.extendedSearch_Produktdata()
 POST /search 						controllers.ExtendTable.PreCalculatedSearch()""",
      this.prefix + """unconstrainedSearch"""
    )
  )

  // @LINE:23
  private[this] lazy val controllers_ExtendTable_extendedSearch_T2DGoldstandard8_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("search")))
  )
  private[this] lazy val controllers_ExtendTable_extendedSearch_T2DGoldstandard8_invoker = createInvoker(
    ExtendTable_2.extendedSearch_T2DGoldstandard(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExtendTable",
      "extendedSearch_T2DGoldstandard",
      Nil,
      "POST",
      """""",
      this.prefix + """search"""
    )
  )

  // @LINE:26
  private[this] lazy val controllers_ExtendTable_correlationBasedSearch9_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("correlationBasedSearch")))
  )
  private[this] lazy val controllers_ExtendTable_correlationBasedSearch9_invoker = createInvoker(
    ExtendTable_2.correlationBasedSearch(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExtendTable",
      "correlationBasedSearch",
      Seq(classOf[String]),
      "POST",
      """""",
      this.prefix + """correlationBasedSearch"""
    )
  )

  // @LINE:29
  private[this] lazy val controllers_ExtendTable_fetchTable_T2DGoldstandard10_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("fetchTable")))
  )
  private[this] lazy val controllers_ExtendTable_fetchTable_T2DGoldstandard10_invoker = createInvoker(
    ExtendTable_2.fetchTable_T2DGoldstandard(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExtendTable",
      "fetchTable_T2DGoldstandard",
      Seq(classOf[String]),
      "GET",
      """""",
      this.prefix + """fetchTable"""
    )
  )

  // @LINE:33
  private[this] lazy val controllers_ExtendTable_fetchTablePOST11_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("fetchTablePOST")))
  )
  private[this] lazy val controllers_ExtendTable_fetchTablePOST11_invoker = createInvoker(
    ExtendTable_2.fetchTablePOST(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExtendTable",
      "fetchTablePOST",
      Seq(classOf[String]),
      "POST",
      """""",
      this.prefix + """fetchTablePOST"""
    )
  )

  // @LINE:36
  private[this] lazy val controllers_ExtendTable_createRepository12_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("createRepository")))
  )
  private[this] lazy val controllers_ExtendTable_createRepository12_invoker = createInvoker(
    ExtendTable_2.createRepository(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExtendTable",
      "createRepository",
      Seq(classOf[String]),
      "POST",
      """ Build and maintain repositories""",
      this.prefix + """createRepository"""
    )
  )

  // @LINE:37
  private[this] lazy val controllers_ExtendTable_createRepository13_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("createRepository")))
  )
  private[this] lazy val controllers_ExtendTable_createRepository13_invoker = createInvoker(
    ExtendTable_2.createRepository(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExtendTable",
      "createRepository",
      Seq(classOf[String]),
      "GET",
      """""",
      this.prefix + """createRepository"""
    )
  )

  // @LINE:38
  private[this] lazy val controllers_ExtendTable_uploadTable14_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("uploadTable")))
  )
  private[this] lazy val controllers_ExtendTable_uploadTable14_invoker = createInvoker(
    ExtendTable_2.uploadTable(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExtendTable",
      "uploadTable",
      Seq(classOf[String]),
      "POST",
      """""",
      this.prefix + """uploadTable"""
    )
  )

  // @LINE:39
  private[this] lazy val controllers_ExtendTable_moderateBulkUploadTables15_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("bulkUploadTables")))
  )
  private[this] lazy val controllers_ExtendTable_moderateBulkUploadTables15_invoker = createInvoker(
    ExtendTable_2.moderateBulkUploadTables(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExtendTable",
      "moderateBulkUploadTables",
      Seq(classOf[String]),
      "POST",
      """""",
      this.prefix + """bulkUploadTables"""
    )
  )

  // @LINE:40
  private[this] lazy val controllers_ExtendTable_generateCorrespondences16_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("generateCorrespondences")))
  )
  private[this] lazy val controllers_ExtendTable_generateCorrespondences16_invoker = createInvoker(
    ExtendTable_2.generateCorrespondences(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExtendTable",
      "generateCorrespondences",
      Seq(classOf[String]),
      "POST",
      """""",
      this.prefix + """generateCorrespondences"""
    )
  )

  // @LINE:41
  private[this] lazy val controllers_ExtendTable_deleteRepository17_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("deleteRepository")))
  )
  private[this] lazy val controllers_ExtendTable_deleteRepository17_invoker = createInvoker(
    ExtendTable_2.deleteRepository(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExtendTable",
      "deleteRepository",
      Seq(classOf[String]),
      "POST",
      """""",
      this.prefix + """deleteRepository"""
    )
  )

  // @LINE:43
  private[this] lazy val controllers_ExtendTable_getUploadStatus18_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("getUploadStatus")))
  )
  private[this] lazy val controllers_ExtendTable_getUploadStatus18_invoker = createInvoker(
    ExtendTable_2.getUploadStatus(fakeValue[String], fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExtendTable",
      "getUploadStatus",
      Seq(classOf[String], classOf[String]),
      "POST",
      """""",
      this.prefix + """getUploadStatus"""
    )
  )

  // @LINE:45
  private[this] lazy val controllers_ExtendTable_Test119_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("test1")))
  )
  private[this] lazy val controllers_ExtendTable_Test119_invoker = createInvoker(
    ExtendTable_2.Test1(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExtendTable",
      "Test1",
      Nil,
      "POST",
      """""",
      this.prefix + """test1"""
    )
  )

  // @LINE:46
  private[this] lazy val controllers_ExtendTable_Test220_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("test2")))
  )
  private[this] lazy val controllers_ExtendTable_Test220_invoker = createInvoker(
    ExtendTable_2.Test2(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExtendTable",
      "Test2",
      Nil,
      "POST",
      """""",
      this.prefix + """test2"""
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:6
    case controllers_Application_index0_route(params) =>
      call { 
        controllers_Application_index0_invoker.call(Application_1.index())
      }
  
    // @LINE:7
    case controllers_ExtendTable_ind1_route(params) =>
      call { 
        controllers_ExtendTable_ind1_invoker.call(ExtendTable_2.ind())
      }
  
    // @LINE:9
    case controllers_Assets_versioned2_route(params) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned2_invoker.call(Assets_0.versioned(path, file))
      }
  
    // @LINE:12
    case controllers_ExtendTable_getRepositoryNames3_route(params) =>
      call { 
        controllers_ExtendTable_getRepositoryNames3_invoker.call(ExtendTable_2.getRepositoryNames())
      }
  
    // @LINE:13
    case controllers_ExtendTable_getRepositoryStatistics4_route(params) =>
      call(params.fromQuery[String]("repository", None)) { (repository) =>
        controllers_ExtendTable_getRepositoryStatistics4_invoker.call(ExtendTable_2.getRepositoryStatistics(repository))
      }
  
    // @LINE:14
    case controllers_ExtendTable_suggestAttributes5_route(params) =>
      call { 
        controllers_ExtendTable_suggestAttributes5_invoker.call(ExtendTable_2.suggestAttributes())
      }
  
    // @LINE:18
    case controllers_ExtendTable_search6_route(params) =>
      call { 
        controllers_ExtendTable_search6_invoker.call(ExtendTable_2.search())
      }
  
    // @LINE:22
    case controllers_ExtendTable_unconstrainedSearch7_route(params) =>
      call(params.fromQuery[String]("repository", None)) { (repository) =>
        controllers_ExtendTable_unconstrainedSearch7_invoker.call(ExtendTable_2.unconstrainedSearch(repository))
      }
  
    // @LINE:23
    case controllers_ExtendTable_extendedSearch_T2DGoldstandard8_route(params) =>
      call { 
        controllers_ExtendTable_extendedSearch_T2DGoldstandard8_invoker.call(ExtendTable_2.extendedSearch_T2DGoldstandard())
      }
  
    // @LINE:26
    case controllers_ExtendTable_correlationBasedSearch9_route(params) =>
      call(params.fromQuery[String]("repository", None)) { (repository) =>
        controllers_ExtendTable_correlationBasedSearch9_invoker.call(ExtendTable_2.correlationBasedSearch(repository))
      }
  
    // @LINE:29
    case controllers_ExtendTable_fetchTable_T2DGoldstandard10_route(params) =>
      call(params.fromQuery[String]("name", None)) { (name) =>
        controllers_ExtendTable_fetchTable_T2DGoldstandard10_invoker.call(ExtendTable_2.fetchTable_T2DGoldstandard(name))
      }
  
    // @LINE:33
    case controllers_ExtendTable_fetchTablePOST11_route(params) =>
      call(params.fromQuery[String]("repositoryName", None)) { (repositoryName) =>
        controllers_ExtendTable_fetchTablePOST11_invoker.call(ExtendTable_2.fetchTablePOST(repositoryName))
      }
  
    // @LINE:36
    case controllers_ExtendTable_createRepository12_route(params) =>
      call(params.fromQuery[String]("repository", None)) { (repository) =>
        controllers_ExtendTable_createRepository12_invoker.call(ExtendTable_2.createRepository(repository))
      }
  
    // @LINE:37
    case controllers_ExtendTable_createRepository13_route(params) =>
      call(params.fromQuery[String]("repository", None)) { (repository) =>
        controllers_ExtendTable_createRepository13_invoker.call(ExtendTable_2.createRepository(repository))
      }
  
    // @LINE:38
    case controllers_ExtendTable_uploadTable14_route(params) =>
      call(params.fromQuery[String]("repository", None)) { (repository) =>
        controllers_ExtendTable_uploadTable14_invoker.call(ExtendTable_2.uploadTable(repository))
      }
  
    // @LINE:39
    case controllers_ExtendTable_moderateBulkUploadTables15_route(params) =>
      call(params.fromQuery[String]("repository", None)) { (repository) =>
        controllers_ExtendTable_moderateBulkUploadTables15_invoker.call(ExtendTable_2.moderateBulkUploadTables(repository))
      }
  
    // @LINE:40
    case controllers_ExtendTable_generateCorrespondences16_route(params) =>
      call(params.fromQuery[String]("repository", None)) { (repository) =>
        controllers_ExtendTable_generateCorrespondences16_invoker.call(ExtendTable_2.generateCorrespondences(repository))
      }
  
    // @LINE:41
    case controllers_ExtendTable_deleteRepository17_route(params) =>
      call(params.fromQuery[String]("repository", None)) { (repository) =>
        controllers_ExtendTable_deleteRepository17_invoker.call(ExtendTable_2.deleteRepository(repository))
      }
  
    // @LINE:43
    case controllers_ExtendTable_getUploadStatus18_route(params) =>
      call(params.fromQuery[String]("repository", None), params.fromQuery[String]("uploadID", None)) { (repository, uploadID) =>
        controllers_ExtendTable_getUploadStatus18_invoker.call(ExtendTable_2.getUploadStatus(repository, uploadID))
      }
  
    // @LINE:45
    case controllers_ExtendTable_Test119_route(params) =>
      call { 
        controllers_ExtendTable_Test119_invoker.call(ExtendTable_2.Test1())
      }
  
    // @LINE:46
    case controllers_ExtendTable_Test220_route(params) =>
      call { 
        controllers_ExtendTable_Test220_invoker.call(ExtendTable_2.Test2())
      }
  }
}