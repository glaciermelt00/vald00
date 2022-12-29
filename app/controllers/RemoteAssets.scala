/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers

/**
 * Definition: Remote, Resource
 */
object RemoteAssets {
  val conf = ixias.util.Configuration()

  def getUrl(name: String, file: String) = {
    // Project
    val path    = conf.get[Option[String]]("""play.assets.paths.%s"""   .format(name)).getOrElse(name.replace(".", "/"))
    val version = conf.get[Option[String]]("""play.assets.version.%s""" .format(name))
    val prefix  = conf.get[Option[String]]("""play.assets.prefix.%s"""  .format(name)).getOrElse("")

    // CDN
    val https  = conf.get[Option[Boolean]]("play.assets.https")  getOrElse(false)
    val cdn    = conf.get[Option[Boolean]]("play.assets.cdn")    getOrElse(false)
    val region = conf.get[Option[String]] ("play.assets.region")
    val bucket = conf.get[Option[String]] ("play.assets.bucket")

    // File URL
    (region, bucket, version) match {
      case (Some(region), Some(bucket), Some(version)) => {
        // Create URL of CDN
        ((https, cdn) match {
          case (false, true)  => s"http://cdn-${bucket}/"
          case (true,  true)  => s"https://cdn-${bucket}/"
          case (false, false) => s"http://s3-${region}.amazonaws.com/${bucket}/"
          case (true,  false) => s"https://s3-${region}.amazonaws.com/${bucket}/"
        }) + s"${path}/${version}/${prefix}/${file}".replace("//", "/")
      }
      case _ => {
        ixias.play.api.controllers.routes.
          UIAssets.versioned(s"${path}/${prefix}/${file}".replace("//", "/"))
      }
    }
  }
}
