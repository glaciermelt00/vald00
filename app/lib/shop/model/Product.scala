/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package lib.shop.model

import ixias.model._
import ixias.util.EnumStatus
import java.time.LocalDateTime

/**
 * Shop product
 */
import Product._
case class Product(
  id:        Option[Id],                       // Id
  name:      String,                           // Product name
  price:     Int,                              // The price per unit: tax exclusive
  taxRate:   BigDecimal    = BigDecimal(0.1),  // Tax rate
  state:     Status        = Status.IS_DRAFT,  // State of saleing
  updatedAt: LocalDateTime = NOW,              // DateTime of updated
  createdAt: LocalDateTime = NOW               // DateTime of created
) extends EntityModel[Id]

/**
 * Companion object
 */
object Product {

  // --[ New Types ]------------------------------------------------------------
  val  Id = the[Identity[Id]]
  type Id = Long @@ Product

  // --[ Enum: Status ]---------------------------------------------------------
  sealed abstract class Status(val code: Short) extends EnumStatus
  object Status extends EnumStatus.Of[Status] {
    case object IS_ARCHIVED extends Status(code = -1)
    case object IS_DRAFT    extends Status(code =  0)
    case object IS_ON_SALES extends Status(code =  1)
    case object IS_SOLD_OUT extends Status(code =  2)

    val ENABLED_FOR_SALE    = IS_ON_SALES :: Nil
    val ENABLED_FOR_DISPLAY = IS_ON_SALES :: IS_SOLD_OUT :: Nil
  }
}
