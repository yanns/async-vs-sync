package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import scala.concurrent.ExecutionContext.Implicits.global
import models.{Payment, StockQuery}
import scala.util.{Success, Failure}

object Application extends Controller with StockQuery with Payment {
  
  def index = Action {
    Ok(views.html.index())
  }

  def search(query: String) = Action {
    Async {
      searchStock(query).map {
        case Success(results) => Ok(results.map(_ \ "description").mkString("Found results: ", ", ", ".\n"))
        case Failure(e)       => InternalServerError(e.getMessage)
      }
    }
  }

  val paymentForm = Form("amount" -> number)

  def payments = Action { implicit request =>
    val amount = paymentForm.bindFromRequest.get
    Async {
      proceedPayments(amount).map {
        case Success(msg) => Ok(msg)
        case Failure(e)   => InternalServerError(e.getMessage)
      }
    }
  }
}
