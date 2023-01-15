/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers.site.auth

import play.api.mvc._
import play.api.i18n.I18nSupport

/**
 * Login page
 */
class LoginController @javax.inject.Inject()(implicit
  cc: MessagesControllerComponents
) extends AbstractController(cc) with mvc.ExtensionMethods with I18nSupport {

  /**
   * Display page.
   */
  def viewLogin = Action { implicit request =>
    Ok(views.html.site.auth.login.Main(
      model.site.auth.SiteViewValueLogin.build,
      model.form.auth.FormValueLogin.form
    ))
  }
}
