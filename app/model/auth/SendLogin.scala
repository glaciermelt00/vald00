/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.auth

/**
 * Login Data
 */
case class SendLogin(
  userNumber: Int,
  password:   String
)
