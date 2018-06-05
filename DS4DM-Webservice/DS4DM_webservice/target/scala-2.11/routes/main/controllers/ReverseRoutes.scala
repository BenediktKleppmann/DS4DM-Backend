
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/UNI-Mannheim/Documents/DS4DM_backend3/DS4DM-Webservice/DS4DM_webservice/conf/routes
// @DATE:Sun May 20 19:48:13 CEST 2018

import play.api.mvc.{ QueryStringBindable, PathBindable, Call, JavascriptLiteral }
import play.core.routing.{ HandlerDef, ReverseRouteContext, queryString, dynamicString }


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers {

  // @LINE:7
  class ReverseExtendTable(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:33
    def fetchTablePOST(repositoryName:String): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "fetchTablePOST" + queryString(List(Some(implicitly[QueryStringBindable[String]].unbind("repositoryName", repositoryName)))))
    }
  
    // @LINE:45
    def Test1(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "test1")
    }
  
    // @LINE:7
    def ind(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "ind")
    }
  
    // @LINE:14
    def suggestAttributes(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "suggestAttributes")
    }
  
    // @LINE:29
    def fetchTable_T2DGoldstandard(name:String): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "fetchTable" + queryString(List(Some(implicitly[QueryStringBindable[String]].unbind("name", name)))))
    }
  
    // @LINE:23
    def extendedSearch_T2DGoldstandard(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "search")
    }
  
    // @LINE:40
    def generateCorrespondences(repository:String): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "generateCorrespondences" + queryString(List(Some(implicitly[QueryStringBindable[String]].unbind("repository", repository)))))
    }
  
    // @LINE:46
    def Test2(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "test2")
    }
  
    // @LINE:41
    def deleteRepository(repository:String): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "deleteRepository" + queryString(List(Some(implicitly[QueryStringBindable[String]].unbind("repository", repository)))))
    }
  
    // @LINE:39
    def moderateBulkUploadTables(repository:String): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "bulkUploadTables" + queryString(List(Some(implicitly[QueryStringBindable[String]].unbind("repository", repository)))))
    }
  
    // @LINE:22
    def unconstrainedSearch(repository:String): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "unconstrainedSearch" + queryString(List(Some(implicitly[QueryStringBindable[String]].unbind("repository", repository)))))
    }
  
    // @LINE:26
    def correlationBasedSearch(repository:String): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "correlationBasedSearch" + queryString(List(Some(implicitly[QueryStringBindable[String]].unbind("repository", repository)))))
    }
  
    // @LINE:36
    def createRepository(repository:String): Call = {
    
      (repository: @unchecked) match {
      
        // @LINE:36
        case (repository)  =>
          import ReverseRouteContext.empty
          Call("POST", _prefix + { _defaultPrefix } + "createRepository" + queryString(List(Some(implicitly[QueryStringBindable[String]].unbind("repository", repository)))))
      
      }
    
    }
  
    // @LINE:13
    def getRepositoryStatistics(repository:String): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "getRepositoryStatistics" + queryString(List(Some(implicitly[QueryStringBindable[String]].unbind("repository", repository)))))
    }
  
    // @LINE:43
    def getUploadStatus(repository:String, uploadID:String): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "getUploadStatus" + queryString(List(Some(implicitly[QueryStringBindable[String]].unbind("repository", repository)), Some(implicitly[QueryStringBindable[String]].unbind("uploadID", uploadID)))))
    }
  
    // @LINE:18
    def search(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "old_search")
    }
  
    // @LINE:38
    def uploadTable(repository:String): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "uploadTable" + queryString(List(Some(implicitly[QueryStringBindable[String]].unbind("repository", repository)))))
    }
  
    // @LINE:12
    def getRepositoryNames(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "getRepositoryNames")
    }
  
  }

  // @LINE:9
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:9
    def versioned(file:Asset): Call = {
      implicit val _rrc = new ReverseRouteContext(Map(("path", "/public")))
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[Asset]].unbind("file", file))
    }
  
  }

  // @LINE:6
  class ReverseApplication(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:6
    def index(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix)
    }
  
  }


}