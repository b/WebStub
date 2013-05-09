package com.thoughtworks.webstub

import dsl.builders.ResponseBuilder._
import dsl.HttpDsl._
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import StubServerFacade._

@RunWith(classOf[JUnitRunner])
class RequestLineStubbingSpec extends StubFunctionalSpec {
  val contextUrl = "http://localhost:9099/context"

  val server = newServer(9099)
  val dslServer = server.withContext("/context")

  override protected def beforeEach() {
    dslServer.reset
  }

  it("should support GET") {
    dslServer.get("/person").returns(response(200))
    httpClient.get(s"$contextUrl/person").status should be(200)
    httpClient.get(s"$contextUrl/person/1").status should be(404)
  }

  it("should support DELETE") {
    dslServer.delete("/person").returns(response(200))
    httpClient.delete(s"$contextUrl/person").status should be(200)
  }

  it("should support POST") {
    dslServer.post("/person").returns(response(200))
    httpClient.post(s"$contextUrl/person").status should be(200)
  }

  it("should support PUT") {
    // note that no expectation is set on stub for request content, yet it matches response
    dslServer.put("/person").returns(response(201))
    httpClient.put(s"$contextUrl/person", "some content").status should be(201)
  }

  it("should support OPTIONS") {
    dslServer.options("/person").returns(response(401))
    httpClient.options(s"$contextUrl/person").status should be(401)
  }

  it("should support HEAD") {
    dslServer.head("/person").returns(response(401))
    httpClient.head(s"$contextUrl/person").status should be(401)
  }

  it("should support TRACE") {
    dslServer.trace("/person").returns(response(200).withContent("All Ok"))
    httpClient.trace(s"$contextUrl/person").status should be(200)
  }

  it ("should support query params") {
    dslServer.get("/dogs?color=black&status=new born").returns(response(200))

    httpClient.getWithParams(s"$contextUrl/dogs?color=black&status=new born").status should be(200)
    httpClient.get(s"$contextUrl/dogs").status should be(400)
  }

  it("should support multiple expectations") {
    dslServer.get("/person/1").returns(response(404))
    dslServer.delete("/person/2").returns(response(200))

    httpClient.get(s"$contextUrl/person/1").status should be(404)
    httpClient.delete(s"$contextUrl/person/2").status should be(200)
  }

  it("should support multiple expectations on the same uri") {
    dslServer.get("/person/1").returns(response(200))
    dslServer.delete("/person/1").returns(response(405))

    httpClient.get(s"$contextUrl/person/1").status should be(200)
    httpClient.delete(s"$contextUrl/person/1").status should be(405)
  }

  ignore("should support overriding expectations") {
    dslServer.get("/person/1").returns(response(200))
    dslServer.get("/person/1").returns(response(405))

    httpClient.get(s"$contextUrl/person/1").status should be(405)
  }
}
