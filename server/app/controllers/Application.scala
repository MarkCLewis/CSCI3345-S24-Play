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

  def getNBATable = Action {
    Ok(views.html.nbaTable(NBATeams.tableHeader, NBATeams.tableData))
  }

}
