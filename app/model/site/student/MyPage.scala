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
 * My Page Site
 */
case class SiteViewValueMyPage(
  layout: ViewValuePageLayout
)

/**
 * Companion Object
 */
object SiteViewValueMyPage {

  /**
   * Create a view model object.
   */
  def build()(implicit request: RequestHeader): SiteViewValueMyPage =
    SiteViewValueMyPage(
      layout = ViewValuePageLayout.build
                 .addRemoteAsset(_ => "my-page")
    )
}
