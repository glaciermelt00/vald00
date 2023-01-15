/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers.form.auth

import play.api.mvc._
import play.api.i18n.I18nSupport

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
  def submitLogin = Action { implicit request =>
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
        // TODO: redirect to my page
        Ok(views.html.site.top.Main(
          model.site.SiteViewValueTop.build
        ))
      }
    )
  }
}
