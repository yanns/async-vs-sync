package loadingtest 
import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import com.excilys.ebi.gatling.jdbc.Predef._
import com.excilys.ebi.gatling.http.Headers.Names._
import akka.util.duration._
import bootstrap._
import assertions._

class RecordedSimulation extends Simulation {

	val httpConf = httpConfig
			.baseURL("http://localhost:9001")
			.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")


	val scn = scenario("Scenario Name")
    .repeat(100) {
      exec(http("search")
            .get("/search")
            .queryParam("""query""", """2""")
        )
    }

	setUp(scn.users(10).ramp(20).protocolConfig(httpConf))
}