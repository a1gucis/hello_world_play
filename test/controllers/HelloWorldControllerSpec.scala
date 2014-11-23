package controllers

import org.specs2.matcher.JsonMatchers
import org.specs2.mutable.Specification
import play.api.test.Helpers._
import play.api.test.{FakeRequest, WithApplication}

class HelloWorldControllerSpec extends Specification with JsonMatchers {

  "HelloWorldController" should {

    "send 404 on a bad request" in new WithApplication {
      route(FakeRequest(GET, "/boum")) must beNone
    }

    "respond with Hello World message" in new WithApplication {
      val Some(result) = route(FakeRequest(GET, "/hw"))

      status(result) must equalTo(OK)
      contentAsString(result) must /("message" -> "Hello World")
      contentAsString(result) must /("owner") /("age" -> 26)
    }
  }
}
