/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * Use of this source code is governed by an MIT-style license
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package mvc

import ixias.util.Logging
import scala.concurrent.Future

import play.api.mvc.Results._
import play.api.mvc.{ RequestHeader, Result }
import play.api.http.HttpErrorHandler
import play.api.http.Status._


/**
 * Application Error Handler
 */
class ErrorHandler extends HttpErrorHandler with Logging {

  /**
   * When client errors has occurred
   */
  def onClientError(rh: RequestHeader, stateCode: Int, message: String): Future[Result] =
    Future.successful {
      logger.info("state = %d, message = %s".format(stateCode, message))
      Status(stateCode)(message)
    }

  /**
   * When internal server errors has occurred
   */
  def onServerError(rh: RequestHeader, ex: Throwable): Future[Result] =
    Future.successful {
      val stateCode = (ex match {
        case _: NoSuchElementException   => NOT_FOUND
        case _: IllegalArgumentException => BAD_REQUEST
        case _                           => INTERNAL_SERVER_ERROR
      })
      val message = "state = %d, message = %s".format(stateCode, ex.getMessage)
      stateCode match {
        case INTERNAL_SERVER_ERROR => logger.error(message, ex)
        case _                     => logger.info(message)
      }
      Status(stateCode)(ex.getMessage)
    }
}
