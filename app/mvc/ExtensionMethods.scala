/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package mvc

import ixias.play.api.mvc.BaseExtensionMethods

import play.api.mvc._

trait ExtensionMethods extends BaseExtensionMethods {
  self: BaseController =>

  implicit lazy val parser = parse.default

  /**
   * Action Helper
   */
  object action {

    /**
     * For authentication
     */
    object auth {
      val AttrKey       = ActionAttrKey.auth
      def Login         = mvc.action.Login()
      def Authenticated = mvc.action.Authenticated()
    }
  }
}
