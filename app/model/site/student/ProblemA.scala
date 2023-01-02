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
 * Problem A Site
 */
case class SiteViewValueProblemA(
  layout: ViewValuePageLayout
)

/**
 * Companion Object
 */
object SiteViewValueProblemA {

  /**
   * Create a view model object.
   */
  def build()(implicit request: RequestHeader): SiteViewValueProblemA =
    SiteViewValueProblemA(
      layout = ViewValuePageLayout.build
                 .addRemoteAsset(_ => "problem-a")
    )
}
