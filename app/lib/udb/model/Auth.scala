/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package lib.udb.model

import ixias.model._
import ixias.security.PBKDF2
import java.time.LocalDateTime

/**
 * Auth data
 */
import Auth._
case class Auth(
  id:        Option[Id]    = None,              // Id
  uid:       User.Id,                           // Id of user
  token:     Token,                             // Token hashed by PBKDF2
  updatedAt: LocalDateTime = NOW,               // DateTime of updated
  createdAt: LocalDateTime = NOW                // DateTime of created
) extends EntityModel[Id]

/**
 * Companion object
 */
object Auth {

  // --[ New Types ]------------------------------------------------------------
  val  Id    = the[Identity[Id]]
  type Id    = Long   @@ Auth
  type Token = String @@ Token.type

  // --[ New Types ]------------------------------------------------------------
  /**
   * The token of Auth
   */
  object Token {

    /**
     * Create a token object by string
     */
    def build(str: String): Token =
      the[Identity[Token]].apply(
        PBKDF2.hash(str)
      )
  }
}
