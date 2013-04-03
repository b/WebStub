package com.thoughtworks.webstub

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import StubServerFactory.stubServer
import com.thoughtworks.webstub.dsl.builders.ResponseBuilder
import ResponseBuilder.response
import com.thoughtworks.webstub.dsl.HttpDsl.dslWrapped
import scala.collection.JavaConversions._

@RunWith(classOf[JUnitRunner])
class HeaderStubbingSpec extends StubFunctionalSpec {
  val contextUrl = "http://localhost:9099/context"
  val stub = stubServer(9099, "/context")
  val dslServer = dslWrapped(stub)

  ignore("should support request headers") {
    /*
       TODO: for some weird reason, this doesn't compile in scala (the withHeader() part);
       this scenario is covered in HttpStubTest for now
     */
    // dslServer.get("/users/1").withHeader("name", "value").returns(response(200))
  }

  it("should support response headers") {
    List(
      response(200).withHeader("Content-Length", "13"),
      response(200).withHeaders(Map("Content-Length" -> "13"))
    ).foreach {
      response =>
        dslServer.get("/users/1").returns(response)

        val resp = httpClient.get(s"$contextUrl/users/1")
        resp.header("Content-Length") should contain("13")
    }
  }
}
