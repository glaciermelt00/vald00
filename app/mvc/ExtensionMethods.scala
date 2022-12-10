/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package mvc

import play.api.mvc._
import ixias.play.api.mvc.BaseExtensionMethods

trait ExtensionMethods extends BaseExtensionMethods {
  self: BaseController =>

  implicit lazy val parser = parse.default
}
