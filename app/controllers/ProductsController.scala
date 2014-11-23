package controllers

import models.Product
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc.{Action, Controller}


object ProductsController extends Controller {
  implicit val productWrites: Writes[Product] = (
    (JsPath \ "ean").write[Long] and
      (JsPath \ "name").write[String] and
      (JsPath \ "description").write[String]
    )(unlift(Product.unapply))

  implicit val productReads: Reads[Product] = (
    (JsPath \ "ean").read[Long] and
      (JsPath \ "name").read[String] and
      (JsPath \ "description").read[String]
    )(Product.apply _)

  def get = Action {
    Ok(Json.toJson(Product.findAll))
  }

  def post = Action(parse.json) { request =>
    val productJson = request.body.asInstanceOf[JsObject] ++ Json.obj("ean" -> JsNumber(Product.maxId + 1))
    val product = productJson.as[Product]
    try {
      Product.insert(product)
      Ok(Json.toJson(product))
    }
    catch {
      case e: IllegalArgumentException => BadRequest("Failed to create new product")
    }
  }

  def getOne(ean: Long) = Action {
    Product.findByEan(ean) match {
      case Some(product) => Ok(Json.toJson(product))
      case None => NotFound
    }
  }

  def putOne(ean: Long) = Action(parse.json) { request =>
    val productJson = request.body.asInstanceOf[JsObject] ++ Json.obj("ean" -> JsNumber(ean))
    val product = productJson.as[Product]
    try {
      Product.save(product)
      Ok(Json.toJson(product))
    }
    catch {
      case e: IllegalArgumentException => BadRequest("Product not found")
    }
  }
}
