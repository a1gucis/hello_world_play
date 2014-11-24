package controllers

import models.Product
import org.specs2.execute.{AsResult, Result}
import org.specs2.matcher.JsonMatchers
import org.specs2.mutable.Specification
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test.{FakeRequest, WithApplication}

abstract class WithModelSetup extends WithApplication {
  override def around[T: AsResult](t: => T): Result = super.around {
    setupData()
    t
  }

  def setupData() {
    Product.products = Set(
      Product("1", "Product1", "Description of Product1"),
      Product("2", "Product2", "Description of Product2"),
      Product("3", "Product3", "Description of Product3")
    )
  }
}


class ProductsControllerSpec extends Specification with JsonMatchers {

  "ProductsController" should {

    "retrieve products on /get" in new WithModelSetup {
      val Some(result) = route(FakeRequest(GET, "/products"))

      status(result) must equalTo(OK)

      contentAsString(result) must */("id" -> "1")
      contentAsString(result) must */("name" -> "Product1")
      contentAsString(result) must */("description" -> "Description of Product1")
    }

    "retrieve the product by its id on /get/:id" in new WithModelSetup {
      val Some(result) = route(FakeRequest(GET, "/products/1"))

      status(result) must equalTo(OK)
      contentAsString(result) must /("id" -> "1")
      contentAsString(result) must /("name" -> "Product1")
      contentAsString(result) must /("description" -> "Description of Product1")
    }

    "update the product by its id on /put/:id" in new WithModelSetup {
      val body = Json.parse(
        """{
          "name": "foo1",
          "description": "foo2"
         }"""
      )
      val Some(result) = route(FakeRequest(PUT, "/products/1").withJsonBody(body))
      status(result) must equalTo(OK)
      contentAsString(result) must /("id" -> "1")
      contentAsString(result) must /("name" -> "foo1")
      contentAsString(result) must /("description" -> "foo2")
    }

    "create the product with /post" in new WithModelSetup {
      val body = Json.parse(
        """{
          "name": "foo4",
          "description": "foo4"
         }"""
      )
      val Some(result) = route(FakeRequest(POST, "/products").withJsonBody(body))
      status(result) must equalTo(OK)
      contentAsString(result) must /("name" -> "foo4")
      contentAsString(result) must /("description" -> "foo4")


      val Some(product) = Product.findById((contentAsJson(result) \ "id").as[String])
      product.name must equalTo ("foo4")
      product.description must equalTo ("foo4")
    }
  }
}
