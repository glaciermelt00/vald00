/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers.site.student

import play.api.mvc._
import play.api.i18n.I18nSupport

/**
 * My page
 */
class MyPageController @javax.inject.Inject()(implicit
  cc: MessagesControllerComponents
) extends AbstractController(cc) with mvc.ExtensionMethods with I18nSupport {

  /**
   * Display page.
   */
  def viewMyPage = (
    Action andThen action.auth.Authenticated
  ) { implicit request =>
    Ok(views.html.site.student.mypage.Main(
      model.site.student.SiteViewValueMyPage.build
    ))
  }
}
