/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.site.shop

import model.component.ViewValuePageLayout
import lib.shop.model.Product

/**
 * Product detail page
 */
case class SiteViewValueProductDetail(
  product: Product#EmbeddedId,
  layout:  ViewValuePageLayout
)
