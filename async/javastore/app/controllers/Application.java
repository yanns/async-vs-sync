package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render());
    }

    public static Result search(String query) {
        return TODO;
    }

    public static Result payments() {
        return TODO;
    }
  
}
