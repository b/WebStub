package com.thoughtworks.webstub.stub.matcher

import com.thoughtworks.webstub.SmartSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.thoughtworks.webstub.config.{Request, HttpConfiguration}
import scala.collection.JavaConversions._
import com.thoughtworks.webstub.dsl.Header
import javax.servlet.http.HttpServletRequest
import org.mockito.Mockito._

@RunWith(classOf[JUnitRunner])
class HeadersMatcherSpec extends SmartSpec{
  val request = requestWithHeader("x", "y")

  it ("should match when expected headers is present") {
    val configuration = configurationWithRequestHeaders("x" -> "y");
    new HeadersMatcher(configuration).matches(request) should be(true)
  }

  it ("should not match when expected header is absent") {
    val configuration = configurationWithRequestHeaders("x" -> "y", "a" -> "b");
    new HeadersMatcher(configuration).matches(request) should be(false)
  }

  def requestWithHeader(name: String, value: String) = {
    val request = mock[HttpServletRequest]
    when(request.getHeader(name)).thenReturn(value)
    request
  }

  def configurationWithRequestHeaders(headers: (String, String)*) = {
    val h = headers.map { case (k, v) => new Header(k, v) }
    new HttpConfiguration(new Request(null, null, null, h), null)
  }
}