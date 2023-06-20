/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers.form.student

import cats.data.EitherT
import cats.implicits._
import scala.concurrent.Future
import play.api.mvc._
import play.api.i18n.I18nSupport

import lib.udb.model.Auth
import lib.udb.persistence.default.AuthRepository
import lib.student.persistence.default.ReadAnswerBRepository
import model.form.student.FormValueAnswerB

/**
 * Answer B form
 */
class AnswerBController @javax.inject.Inject()(implicit
  cc: MessagesControllerComponents
) extends AbstractController(cc) with mvc.ExtensionMethods with I18nSupport {

  /**
   * Submit
   */
  def submit = Action.async { implicit request =>
    EitherT.fromEither[Future] {
      FormHelper.bindFromRequest(FormValueAnswerB.form.mapping)
    } semiflatMap {
      case post => {
        val Some(loginCookie) = request.cookies.get(mvc.ActionAttrKey.auth.COOKIES_NAME)
        val token             = Auth.Token(loginCookie.value)
        for {
          Some(auth) <- AuthRepository.findByToken(token)
          _          <- ReadAnswerBRepository.add(post.create(auth.v.uid))
        } yield Redirect(controllers.site.routes.TopController.view)
      }
    }
  }
}
