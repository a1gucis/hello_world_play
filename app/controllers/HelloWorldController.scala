package controllers


import play.api.libs.json.{JsNumber, JsString, Json}
import play.api.mvc.{Action, Controller}


object HelloWorldController extends Controller {

  def get = Action {
    val result = Json.obj(
      "message" -> JsString("Hello World"),
      "owner" -> Json.obj(
        "name" -> JsString("Algirdas"),
        "surname" -> JsString("Beinaravicius"),
        "age" -> JsNumber(26)
      ),
      "tags" -> Json.arr(
        JsString("tag1"),
        JsString("tag2")
      )
    )

    Ok(Json.toJson(result))
  }
}