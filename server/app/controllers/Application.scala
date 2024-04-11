package controllers

import javax.inject._

import shared.SharedMessages
import play.api.mvc._
import models.NBATeams
import scala.concurrent.Future
import scala.concurrent.ExecutionContext

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.global

  def index = Action {
    println("Getting index.")
    Ok(views.html.index(SharedMessages.itWorks))
  }

  def getNBATable(col: String) = Action {
    val colNum = NBATeams.tableHeader.indexOf(col) max 0
    Ok(views.html.nbaTable(NBATeams.tableHeader, NBATeams.tableData.sortBy(row => row(colNum))))
  }

  def someDBAction() = Action.async {
    val dbResult: Future[Seq[NBATeams.NBATeam]] = NBATeams.getTeams()

    dbResult.map(teams => Ok(views.html.nbaTable(Array("name", "Wins"), Array(Array(teams.toString)))))
  }

}
