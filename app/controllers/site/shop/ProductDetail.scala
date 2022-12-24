/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers.site.shop

import play.api.mvc._
import play.api.i18n.I18nSupport

import lib.shop.model.Product
import lib.shop.persistence.default._

/**
 * Product detail page
 */
class ProductDetailController @javax.inject.Inject()(implicit
  cc: MessagesControllerComponents
) extends AbstractController(cc) with mvc.ExtensionMethods with I18nSupport {

  /**
   * Display page.
   */
  def view(id: Long) = Action.async { implicit request =>
    for {
      Some(product) <- ProductRepository.get(Product.Id(id))
    } yield {
      Ok(views.html.site.shop.product.detail.Main(
        model.site.shop.SiteViewValueProductDetail(
          product = product,
          layout  = model.component.ViewValuePageLayout.build
        )
      ))
    }
  }
}
