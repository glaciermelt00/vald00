/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers.site.auth

import cats.data.EitherT
import cats.implicits._
import scala.concurrent.Future
import ixias.security.PBKDF2

import play.api.mvc._
import play.api.data.Forms._
import play.api.i18n.I18nSupport

import lib.udb.model.Auth
import lib.udb.persistence.default.AuthRepository
import model.auth.SendLogin

/**
 * Login page
 */
class LoginController @javax.inject.Inject()(implicit
  cc: MessagesControllerComponents
) extends AbstractController(cc) with mvc.ExtensionMethods with I18nSupport {

  /**
   * Display page.
   */
  def view = Action { implicit request => {
    println("--- view")
    Ok(views.html.site.auth.login.Main(
      model.site.auth.SiteViewValueLogin.build,
      SendLogin.form
    ))
  }}

  /**
   * Submit login
   */
  def commit =
    Action.async { implicit request =>
      EitherT.fromEither[Future] {
        //- 定義すべきかも
        FormHelper.bindFromRequest(tuple(
          "userNumber" -> number(min = 0, max = 10000),
          "password"   -> nonEmptyText
        ))
      } semiflatMap {
        case post => {
          println("--- commit login")
          println(post)
          println(post._1)
          println(post._2)
          println(Auth.buildToken(post._2))
          println(PBKDF2.compare(post._2, Auth.buildToken(post._2)))
          val data  = (SendLogin.apply _).tupled(post)
          val token = Auth.buildToken(data.password)
          for {
            Some(auth) <- AuthRepository.findByToken(token)
            result      = Redirect(controllers.site.student.routes.MyPageController.view)
          } yield result.withCookies(Cookie(mvc.ActionAttrKey.auth.COOKIES_NAME, auth.v.token))
        }
      } getOrElse(
        Redirect(controllers.site.auth.routes.LoginController.view)
      )
  }

}
