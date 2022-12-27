/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package lib.udb.model

import ixias.model._
import ixias.util.EnumStatus
import java.time.LocalDateTime

/**
 * User data
 */
import User._
case class User(
  id:        Option[Id]    = None,              // Id
  no:        Int,                               // No of user
  state:     Status        = Status.IS_ACTIVE,  // State of user
  updatedAt: LocalDateTime = NOW,               // DateTime of updated
  createdAt: LocalDateTime = NOW                // DateTime of created
) extends EntityModel[Id]

/**
 * Companion object
 */
object User {

  // --[ New Types ]------------------------------------------------------------
  val  Id = the[Identity[Id]]
  type Id = Long @@ User

  // --[ Enum: Activation status ]----------------------------------------------
  sealed abstract class Status(val code: Short) extends EnumStatus
  object Status extends EnumStatus.Of[Status] {
    case object IS_INVITED  extends Status(code = 0)
    case object IS_ACTIVE   extends Status(code = 1)
  }
}
