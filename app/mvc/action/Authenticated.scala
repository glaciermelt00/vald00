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

import play.api.mvc.Results._
import play.api.mvc.{ Request, Result, ActionRefiner }

import lib.udb.model.Auth
import lib.udb.persistence.default.{ AuthRepository, UserRepository }

/**
 * Authenticate via session-token
 */
case class Authenticated()(implicit
  val ex: ExecutionContext
) extends ActionRefiner[Request, Request] {

  override def executionContext: ExecutionContext = ex

  /**
   * Determine how to process a request
   */
  def refine[A](request: Request[A]): Future[Either[Result, Request[A]]] = {
    println("--- authenticated refine")
    println(request)
    println(request.attrs)
    println(request.attrs.get(mvc.ActionAttrKey.auth.Token))
    println(request.session.get(mvc.ActionAttrKey.auth.COOKIES_NAME))
    (EitherT.fromEither[Future] {
      request.session.get(mvc.ActionAttrKey.auth.COOKIES_NAME) match {
        case Some(v) => Right(v)
        case None    => Left(Unauthorized("Not found Authorization token"))
      }
    } flatMapF {
      case token => {
        println("--- after token")
        println(token)
        println(Auth.Token(token))
        for {
          v1 <- AuthRepository.findByToken(Auth.Token(token))
          _ = {
            println(v1)
          }
        } yield v1
        AuthRepository.findByToken(Auth.Token(token)).map({
          case Some(auth) => Right(auth)
          case None       => Left(Unauthorized("Could not reference data from Access Token #1"))
        })
      }
    } flatMapF {
      case auth =>
        UserRepository.get(auth.v.uid).map({
          case Some(user) => Right(user)
          case None       => Left(Unauthorized("Could not reference user from Access Token #2"))
        })
    } map {
      case user =>
        request.addAttr(mvc.ActionAttrKey.auth.User, user)
    }).value
  }
}
