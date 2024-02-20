package controllers

import javax.inject._

import shared.SharedMessages
import play.api.mvc._
import models.NBATeams

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    println("Getting index.")
    Ok(views.html.index(SharedMessages.itWorks))
  }

  def getNBATable(col: String) = Action {
    val colNum = NBATeams.tableHeader.indexOf(col) max 0
    Ok(views.html.nbaTable(NBATeams.tableHeader, NBATeams.tableData.sortBy(row => row(colNum))))
  }

}
