/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package lib.shop.model

import ixias.model._
import ixias.util.EnumStatus
import java.util.UUID
import java.time.{ Duration, LocalDateTime }

/**
 * Cart data and session associated with the browser.
 */
import Cart._
case class Cart(
  id:        Option[Id]    = None,              // Id
  token:     Token         = Token.build,       // Token of session
  state:     Status        = Status.IS_ACTIVE,  // Status of cart
  expiry:    LocalDateTime = Expiry.next,       // Expiry
  updatedAt: LocalDateTime = NOW,               // DateTime of updated
  createdAt: LocalDateTime = NOW                // DateTime of created
) extends EntityModel[Id] {

  /**
   * Check if your cart is valid
   */
  def valid: Boolean =
    this.state == Status.IS_ACTIVE && Expiry.valid(this.expiry)
}

/**
 * Companion object
 */
object Cart {

  // --[ New Types ]------------------------------------------------------------
  val  Id    = the[Identity[Id]]
  type Id    = Long   @@ Cart
  type Token = String @@ Cart.Token.type

  // --[ Methods ]--------------------------------------------------------------
  /**
   * Create a new model object
   */
  def build: Cart#WithNoId = Cart().toWithNoId

  // --[ Util: Expiry ]---------------------------------------------------------
  /**
   * Define the usuful function of expi
   */
  object Expiry {

    /**
     * Calculate the next expiry time from the current time
     */
    def next: LocalDateTime =
      LocalDateTime.now.plus(Duration.ofDays(14L))

    /**
     * Check that the expiry date is within the valid range
     */
    def valid(expiry: LocalDateTime): Boolean =
      expiry.isAfter(LocalDateTime.now)
  }

  // --[ New Types: Token ]-----------------------------------------------------
  /**
   * Generate a new session token
   */
  object Token {

    /**
     * Create a new token
     */
    def build: Token =
      the[Identity[Token]].apply(UUID.randomUUID.toString)

    /**
     * Create a token object by string
     */
    def apply(str: String): Token =
      str.length match {
        case len if len > 30 => the[Identity[Token]].apply(str)
        case _ => throw new IllegalArgumentException("Token as string is too short.")
      }
  }

  // --[ Enum: Status ]---------------------------------------------------------
  sealed abstract class Status(val code: Short) extends EnumStatus
  object Status extends EnumStatus.Of[Status] {
    case object IS_ACTIVE extends Status(code =   1)
    case object IS_DONE   extends Status(code = 100)
  }
}
