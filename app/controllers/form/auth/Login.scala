/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers.form.auth

import ixias.security.PBKDF2

import play.api.mvc._
import play.api.i18n.I18nSupport

import lib.udb.model.Auth
import model.form.auth.FormValueLogin

/**
 * Login form
 */
class LoginController @javax.inject.Inject()(implicit
  cc: MessagesControllerComponents
) extends AbstractController(cc) with mvc.ExtensionMethods with I18nSupport {

  /**
   * Submit login
   */
  def submitLogin = (
    Action andThen action.auth.Login
  ) { implicit request =>
    FormValueLogin.form.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.site.auth.login.Main(
          model.site.auth.SiteViewValueLogin.build,
          formWithErrors
        ))
      },
      login => {
        println("--- submit login")
        println(login)
        println(request.attrs)
        println(request.attrs.get(action.auth.AttrKey.Token))
        println(login.password)
        println(Auth.Token(login.password))
        println(Auth.Token(login.password).length)
        println(PBKDF2.compare(login.password, Auth.Token(login.password)))
        request.addAttr(action.auth.AttrKey.Token, Auth.Token(login.password))
        val attrs = request.attrs
        val token = request.attrs.get(action.auth.AttrKey.Token)
        println(request.addAttr(action.auth.AttrKey.Token, Auth.Token(login.password)))
        println(attrs)
        println(token)
        // TODO: redirect to my page
        Ok(views.html.site.top.Main(
          model.site.SiteViewValueTop.build
        ))
      }
    )
  }
}
