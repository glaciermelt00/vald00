/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package mvc

import play.api.libs.typedmap.TypedKey

/**
 * Attrkey of action
 */
object ActionAttrKey {

  /**
   * For authentication
   */
  object auth {
    val COOKIES_NAME  = "Login-info"
    val Token         = TypedKey[lib.udb.model.Auth.Token]
    val User          = TypedKey[lib.udb.model.User#EmbeddedId]
  }
}
