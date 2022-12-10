/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package lib.shop.model

import ixias.model._
import java.time.LocalDateTime

/**
 * Manage the products in cart
 */
import CartItem._
case class CartItem(
  id:         Option[Id],             // Id
  cartId:     Cart.Id,                // Id of cart
  productId:  Product.Id,             // Id of product
  productNum: Int           = 1,      // Quantity of products
  updatedAt:  LocalDateTime = NOW,    // DateTime of updated
  createdAt:  LocalDateTime = NOW     // DateTime of created
) extends EntityModel[Id]

/**
 * Companion object
 */
object CartItem {

  // --[ New Types ]------------------------------------------------------------
  val  Id = the[Identity[Id]]
  type Id = Long @@ CartItem
}
