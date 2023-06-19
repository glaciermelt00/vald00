/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.site.student

import play.api.mvc.RequestHeader
import model.component.ViewValuePageLayout

/**
 * Home Site
 */
case class SiteViewValueHome(
  layout: ViewValuePageLayout
)

/**
 * Companion Object
 */
object SiteViewValueHome {

  /**
   * Create a view model object.
   */
  def build()(implicit request: RequestHeader): SiteViewValueHome =
    SiteViewValueHome(
      layout = ViewValuePageLayout.build
                .addRemoteAsset(_ => "home")
    )
}
