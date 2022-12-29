/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.component

import play.api.mvc.RequestHeader

/**
 * Page Layout Management
 */
case class ViewValuePageLayout(
  isMobile:      Boolean,            // Device detection (true: sp, false: pc)
  remoteAssets:  Seq[String] = Nil,  // Assets resource
) {

  /**
   * Add remote assets
   */
  def addRemoteAsset(f: ViewValuePageLayout => String): ViewValuePageLayout =
    this.copy(remoteAssets = remoteAssets :+ f(this))
}

/**
 * Companion Object
 */
object ViewValuePageLayout {

  // --[ Methods ]--------------------------------------------------------------
  def build(implicit rh: RequestHeader) = new ViewValuePageLayout(
    isMobile     = rh.attrs.get(ixias.play.api.mvc.DeviceDetectionAttrKey.IsMobile).getOrElse(false),
    remoteAssets = Seq("common")
  )
}
