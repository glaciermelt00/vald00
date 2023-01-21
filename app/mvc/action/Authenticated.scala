/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package mvc.action

import cats.data.EitherT
import cats.implicits._
import scala.concurrent.{ Future, ExecutionContext }

import play.api.Environment
import play.api.mvc.Results._
import play.api.mvc.{ Request, Result, ActionRefiner }

import lib.udb.persistence.default.{ AuthRepository, UserRepository }

/**
 * Authenticate as admin Via session-token
 */
case class Authenticated()(implicit
  val env:              Environment,
  val executionContext: ExecutionContext
) extends ActionRefiner[Request, Request] {

  /**
   * Determine how to process a request
   */
  def refine[A](request: Request[A]): Future[Either[Result, Request[A]]] =
    (EitherT.fromEither[Future] {
      request.attrs.get(mvc.ActionAttrKey.auth.Token) match {
        case Some(v) => Right(v)
        case None    => Left(Unauthorized("Not found Authorization token"))
      }
    } flatMapF {
      case token =>
        AuthRepository.findByToken(token).map({
          case Some(auth) => Right(auth)
          case None       => Left(Unauthorized("Could not reference data from Access Token"))
        })
    } flatMapF {
      case auth =>
        UserRepository.get(auth.v.uid).map({
          case Some(user) => Right(user)
          case None       => Left(Unauthorized("Could not reference user from Access Token"))
        })
    } map {
      case user =>
        request.addAttr(mvc.ActionAttrKey.auth.User, user)
    }).value
}
