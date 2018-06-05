
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/UNI-Mannheim/Documents/DS4DM_backend3/DS4DM-Webservice/DS4DM_webservice/conf/routes
// @DATE:Sun May 20 19:48:13 CEST 2018

package controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final controllers.ReverseExtendTable ExtendTable = new controllers.ReverseExtendTable(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseAssets Assets = new controllers.ReverseAssets(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseApplication Application = new controllers.ReverseApplication(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReverseExtendTable ExtendTable = new controllers.javascript.ReverseExtendTable(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseAssets Assets = new controllers.javascript.ReverseAssets(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseApplication Application = new controllers.javascript.ReverseApplication(RoutesPrefix.byNamePrefix());
  }

}
