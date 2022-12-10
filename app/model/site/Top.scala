/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.site

import play.api.mvc.RequestHeader
import model.component.ViewValuePageLayout

/**
 * Top Site
 */
case class SiteViewValueTop(
  layout: ViewValuePageLayout
)

/**
 * Companion Object
 */
object SiteViewValueTop {

  /**
   * Create a view model object.
   */
  def build()(implicit request: RequestHeader): SiteViewValueTop =
    SiteViewValueTop(ViewValuePageLayout.build)
}
