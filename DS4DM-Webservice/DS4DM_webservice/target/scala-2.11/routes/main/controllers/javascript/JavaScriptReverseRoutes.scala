
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/UNI-Mannheim/Documents/DS4DM_backend3/DS4DM-Webservice/DS4DM_webservice/conf/routes
// @DATE:Sun May 20 19:48:13 CEST 2018

import play.api.routing.JavaScriptReverseRoute
import play.api.mvc.{ QueryStringBindable, PathBindable, Call, JavascriptLiteral }
import play.core.routing.{ HandlerDef, ReverseRouteContext, queryString, dynamicString }


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers.javascript {
  import ReverseRouteContext.empty

  // @LINE:7
  class ReverseExtendTable(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:33
    def fetchTablePOST: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExtendTable.fetchTablePOST",
      """
        function(repositoryName) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "fetchTablePOST" + _qS([(""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("repositoryName", repositoryName)])})
        }
      """
    )
  
    // @LINE:45
    def Test1: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExtendTable.Test1",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "test1"})
        }
      """
    )
  
    // @LINE:7
    def ind: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExtendTable.ind",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "ind"})
        }
      """
    )
  
    // @LINE:14
    def suggestAttributes: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExtendTable.suggestAttributes",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "suggestAttributes"})
        }
      """
    )
  
    // @LINE:29
    def fetchTable_T2DGoldstandard: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExtendTable.fetchTable_T2DGoldstandard",
      """
        function(name) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "fetchTable" + _qS([(""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("name", name)])})
        }
      """
    )
  
    // @LINE:23
    def extendedSearch_T2DGoldstandard: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExtendTable.extendedSearch_T2DGoldstandard",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "search"})
        }
      """
    )
  
    // @LINE:40
    def generateCorrespondences: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExtendTable.generateCorrespondences",
      """
        function(repository) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "generateCorrespondences" + _qS([(""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("repository", repository)])})
        }
      """
    )
  
    // @LINE:46
    def Test2: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExtendTable.Test2",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "test2"})
        }
      """
    )
  
    // @LINE:41
    def deleteRepository: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExtendTable.deleteRepository",
      """
        function(repository) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "deleteRepository" + _qS([(""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("repository", repository)])})
        }
      """
    )
  
    // @LINE:39
    def moderateBulkUploadTables: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExtendTable.moderateBulkUploadTables",
      """
        function(repository) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "bulkUploadTables" + _qS([(""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("repository", repository)])})
        }
      """
    )
  
    // @LINE:22
    def unconstrainedSearch: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExtendTable.unconstrainedSearch",
      """
        function(repository) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "unconstrainedSearch" + _qS([(""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("repository", repository)])})
        }
      """
    )
  
    // @LINE:26
    def correlationBasedSearch: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExtendTable.correlationBasedSearch",
      """
        function(repository) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "correlationBasedSearch" + _qS([(""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("repository", repository)])})
        }
      """
    )
  
    // @LINE:36
    def createRepository: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExtendTable.createRepository",
      """
        function(repository) {
        
          if (true) {
            return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "createRepository" + _qS([(""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("repository", repository)])})
          }
        
        }
      """
    )
  
    // @LINE:13
    def getRepositoryStatistics: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExtendTable.getRepositoryStatistics",
      """
        function(repository) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "getRepositoryStatistics" + _qS([(""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("repository", repository)])})
        }
      """
    )
  
    // @LINE:43
    def getUploadStatus: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExtendTable.getUploadStatus",
      """
        function(repository,uploadID) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "getUploadStatus" + _qS([(""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("repository", repository), (""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("uploadID", uploadID)])})
        }
      """
    )
  
    // @LINE:18
    def search: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExtendTable.search",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "old_search"})
        }
      """
    )
  
    // @LINE:38
    def uploadTable: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExtendTable.uploadTable",
      """
        function(repository) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "uploadTable" + _qS([(""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("repository", repository)])})
        }
      """
    )
  
    // @LINE:12
    def getRepositoryNames: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExtendTable.getRepositoryNames",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "getRepositoryNames"})
        }
      """
    )
  
  }

  // @LINE:9
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:9
    def versioned: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Assets.versioned",
      """
        function(file) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[Asset]].javascriptUnbind + """)("file", file)})
        }
      """
    )
  
  }

  // @LINE:6
  class ReverseApplication(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:6
    def index: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.index",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + """"})
        }
      """
    )
  
  }


}