
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/UNI-Mannheim/Documents/DS4DM_backend3/DS4DM-Webservice/DS4DM_webservice/conf/routes
// @DATE:Sun May 20 19:48:13 CEST 2018


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
