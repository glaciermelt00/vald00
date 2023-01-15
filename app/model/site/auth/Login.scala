/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.site.auth

import play.api.mvc.RequestHeader
import model.component.ViewValuePageLayout

/**
 * Login Site
 */
case class SiteViewValueLogin(
  layout: ViewValuePageLayout
)

/**
 * Companion Object
 */
object SiteViewValueLogin {

  /**
   * Create a view model object.
   */
  def build()(implicit request: RequestHeader): SiteViewValueLogin =
    SiteViewValueLogin(
      layout = ViewValuePageLayout.build
                 .addRemoteAsset(_ => "login")
    )
}
