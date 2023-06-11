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

//    FormValueLogin.form.bindFromRequest.fold(
//      formWithErrors => {
//        BadRequest(views.html.site.auth.login.Main(
//          model.site.auth.SiteViewValueLogin.build,
//          formWithErrors
//        ))
//      },
//      login => {
//        println("--- submit login")
//        println(login)
//        println(request.attrs)
//        println(request.attrs.get(action.auth.AttrKey.Token))
//        println(login.password)
//        println(Auth.Token(login.password))
//        println(Auth.Token(login.password).length)
//        println(PBKDF2.compare(login.password, Auth.Token(login.password)))
//        //request.addAttr(action.auth.AttrKey.Token, Auth.Token(login.password))
//        val attrs = request.attrs
//        val token = request.attrs.get(action.auth.AttrKey.Token)
//        println(request.addAttr(action.auth.AttrKey.Token, Auth.Token(login.password)))
//        println(attrs)
//        println(token)
//        // TODO: redirect to my page
//        Redirect(controllers.site.student.routes.MyPageController.viewMyPage)
//          .withSession(mvc.ActionAttrKey.auth.COOKIES_NAME -> Auth.Token(login.password))
//      }
//    )

  }

}
