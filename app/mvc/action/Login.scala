/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package mvc.action

import cats.data.EitherT
import cats.implicits._
import ixias.security.PBKDF2
import scala.concurrent.{ Future, ExecutionContext }

import play.api.data.FormBinding
import play.api.mvc.Results._
import play.api.mvc.{ Request, Result, ActionRefiner }

import lib.udb.persistence.default.{ AuthRepository, UserRepository }
import model.auth.SendLogin

/**
 * Login and add token to session
 */
case class Login()(implicit
  val ex: ExecutionContext
) extends ActionRefiner[Request, Request] {

  override def executionContext: ExecutionContext = ex

  /**
   * Determine how to process a request
   */
  def refine[A](request: Request[A]): Future[Either[Result, Request[A]]] = {
    println("--- login refine")
    println(request)
    println(lib.udb.model.Auth.Token.build("1101"))

    (EitherT.fromEither[Future] {
      request.cookies.get(mvc.ActionAttrKey.auth.COOKIES_NAME).map(_.value) match {
        case Some(v) => Right(v)
        case None    => Left(Unauthorized("Not found login info"))
      }
    } flatMapF {
      case value =>
        UserRepository.findByNo(value.userNumber).map({
          case Some(user) => Right((user, value.password))
          case None       => Left(Unauthorized("Could not match user number and password #1"))
        })
    } flatMapF {
      case (user, inputPass) =>
        for {
          Some(auth) <- AuthRepository.findByUserId(user.id)
          token       = auth.v.token
        } yield PBKDF2.compare(inputPass, token) match {
          case true  => Right(auth)
          case false => Left(Unauthorized("Could not match user number and password #2"))
        }
    } map {
      case auth =>
        request.addAttr(mvc.ActionAttrKey.auth.Token, auth.v.token)
    }).value
  }
}
