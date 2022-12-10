/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

/*
 * Copyright IxiaS,Inc. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package lib.shop

import lib.shop.model.Cart

package object persistence {
  val default = onMySQL

  object onMySQL {
    implicit val driver = slick.jdbc.MySQLProfile

    object Productrepository extends persistence.ProductRepository
    object CartRepository    extends persistence.CartRepository

    def CartItemRepository = persistence.CartItemRepository(_: Cart.Id)
  }
}
