/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers.form.student

import play.api.mvc._
import play.api.i18n.I18nSupport

import lib.udb.model.Auth
import lib.udb.persistence.default.AuthRepository
import lib.student.persistence.default.ReadAnswerARepository
import model.form.student.FormValueAnswerA

/**
 * Answer form
 */
class AnswerController @javax.inject.Inject()(implicit
  cc: MessagesControllerComponents
) extends AbstractController(cc) with mvc.ExtensionMethods with I18nSupport {

  /**
   * Submit answer
   */
  def submitAnswer = Action { implicit request =>
    FormValueAnswerA.form.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.site.student.problem.a.Main(
          model.site.student.SiteViewValueProblemA.build,
          formWithErrors
        ))
      },
      answer => {
        val Some(loginCookie) = request.cookies.get("Login-info")
        val token             = Auth.Token(loginCookie.value)
        for {
          Some(auth) <- AuthRepository.findByToken(token)
          _          <- ReadAnswerARepository.add(answer.create(auth.v.uid))
          // TODO: 後で、problem_b に遷移できるようにする
        } yield Ok(views.html.site.top.Main(
          model.site.SiteViewValueTop.build
        ))
      }
    )
  }
}
