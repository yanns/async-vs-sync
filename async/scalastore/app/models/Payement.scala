package models

import scala.util.{Failure, Success, Try}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.ws.WS
import play.api.libs.json.Json

trait Payment {

  def procedePayments(amount: Int): Future[Try[String]] = {
    WS.url("http://localhost:9002/payments").post(Json.toJson(Map("amount" -> amount))).map { response =>
      response.status match {
        case 200 => Success("Payment processed.\n")
        case _   => Failure(new Exception(s"Error calling payment service.\nResponse status ${response.status}\n"))
      }
    }
  }

}
