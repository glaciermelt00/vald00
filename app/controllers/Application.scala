/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers

import play.api.mvc._

/**
 * Application basic process
 */
class ApplicationController @javax.inject.Inject()() extends ControllerHelpers {

  /**
   * Simple Action
   */
  val Action =
    new ActionBuilder.IgnoringBody()(controllers.Execution.trampoline)

  // --[ Methods ]--------------------------------------------------------------
  /**
   * For Application healthcheck
   */
  def ping = Action {
    Ok("Ok")
  }

  /**
   * Returns a 303 SeeOther response.
   */
  def redirect(to: String, from: String): Action[AnyContent] = Action {
    Redirect(to)
  }
}
