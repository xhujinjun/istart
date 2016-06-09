import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the Products entity.
 */
class ProductsGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://127.0.0.1:8080"""

    val httpConf = http
        .baseURL(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connection("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "X-CSRF-TOKEN" -> "${csrf_token}"
    )

    val scn = scenario("Test the Products entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))
        .check(headerRegex("Set-Cookie", "CSRF-TOKEN=(.*); [P,p]ath=/").saveAs("csrf_token"))).exitHereIfFailed
        .pause(10)
        .exec(http("Authentication")
        .post("/api/authentication")
        .headers(headers_http_authenticated)
        .formParam("j_username", "admin")
        .formParam("j_password", "admin")
        .formParam("remember-me", "true")
        .formParam("submit", "Login")).exitHereIfFailed
        .pause(1)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200))
        .check(headerRegex("Set-Cookie", "CSRF-TOKEN=(.*); [P,p]ath=/").saveAs("csrf_token")))
        .pause(10)
        .repeat(2) {
            exec(http("Get all products")
            .get("/api/products")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new products")
            .post("/api/products")
            .headers(headers_http_authenticated)
            .body(StringBody("""{"id":null, "pno":"SAMPLE_TEXT", "travelAgentId":null, "phone":"SAMPLE_TEXT", "title":"SAMPLE_TEXT", "price":null, "pricedesc":"SAMPLE_TEXT", "preferential":"SAMPLE_TEXT", "startdate":"2020-01-01T00:00:00.000Z", "startadderss":"SAMPLE_TEXT", "endadderss":"SAMPLE_TEXT", "days":"0", "costdesc":"SAMPLE_TEXT", "route":"SAMPLE_TEXT", "detaildesc":"SAMPLE_TEXT", "bookNotice":"SAMPLE_TEXT", "rate":"SAMPLE_TEXT", "tourismTypesId":null, "detailTypeId":null, "dataCreator":"SAMPLE_TEXT", "dataUpdater":"SAMPLE_TEXT", "dataCreateDatetime":"2020-01-01T00:00:00.000Z", "dataUpdateDatetime":"2020-01-01T00:00:00.000Z", "dataStatus":"0"}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_products_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created products")
                .get("${new_products_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created products")
            .delete("${new_products_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(100) over (1 minutes))
    ).protocols(httpConf)
}
