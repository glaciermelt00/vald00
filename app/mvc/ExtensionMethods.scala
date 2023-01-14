/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package mvc

import play.api.mvc._
import ixias.play.api.auth.mvc.{ AuthProfile, AuthExtensionMethods }

import lib.udb.model.User

trait ExtensionMethods extends AuthExtensionMethods {
  self: BaseController =>

  implicit lazy val parser = parse.default

  /**
   * Action Helper
   */
  object action {
    /*V
     * For authentication
     */
    object auth {
      val AttrKey = ActionAttrKey.auth
      def Authenticatedd(auth: AuthProfile[User.Id, User, String]) = Authenticated(auth)
    }
  }
}
