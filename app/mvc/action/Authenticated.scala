/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

//package mvc.action

//import cats.data.EitherT
//import cats.implicits._
//import scala.concurrent.{ Future, ExecutionContext }
//
//import play.api.Environment
//import play.api.mvc.{ Request, Result, ActionRefiner }
//
//import lib.udb.persistence.default.UserRepository

/**
 * Authenticate as admin Via session-token
 */
//case class Authenticated()(implicit
//  val env:              Environment,
//  val executionContext: ExecutionContext
//) extends ActionRefiner[Request, Request] {
//
//  /**
//   * Determine how to process a request
//   */
//  def refine[A](request: Request[A]): Future[Either[Result, Request[A]]] =
//    (EitherT.fromEither[Future] {
//      UserRepository.findBy
//    })
//}
//
//
//
//
//
//
//case class AuthenticatedAsAdmin()(implicit
//  val env:              Environment,
//  val executionContext: ExecutionContext
//) extends ActionRefiner[Request, Request] with AuthProfileViaAuth0[Admin.OpenId] {
//
//  /**
//   * The Auth0 application name
//   */
//  val appName = "admin"
//
//  /**
//   * Determine how to process a request
//   */
//  def refine[A](request: Request[A]): Future[Either[Result, Request[A]]] =
//    (EitherT.fromEither[Future] {
//      for {
//        v1 <- getAuthorizationToken(request)
//        v2 <- decodeJwtToken(v1, Admin.OpenId(_))
//      } yield v2
//    } flatMapF {
//      case (token, openId, _) => {
//        AdminRepository.findByOpenId(openId).map({
//          case Some(admin) => Right(admin)
//          case None        => Left(Unauthorized("Could not reference user from Access Token"))
//        })
//      }
//    } map {
//      case admin =>
//        request.addAttr(ActionAttrKey.auth.Admin, admin)
//    }).value
//}
