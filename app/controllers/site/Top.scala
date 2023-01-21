/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers.site

import ixias.security.PBKDF2

import play.api.mvc._
import play.api.i18n.I18nSupport

/**
 * Top page
 */
class TopController @javax.inject.Inject()(implicit
  cc: MessagesControllerComponents
) extends AbstractController(cc) with mvc.ExtensionMethods with I18nSupport {

  /**
   * Display page.
   */
  def view = Action { implicit request => {
        println("--- top")
        println(request.attrs)
        println(request.attrs.get(action.auth.AttrKey.User))
        println("hash")
        println(PBKDF2.hash("hash"))
        println(PBKDF2.hash("hash").length)
        println(PBKDF2.compare("hash", PBKDF2.hash("hash")))
    Ok(views.html.site.top.Main(
      model.site.SiteViewValueTop.build
    ))
  }}
}
