package com.yundai.playscala.playconcurrency

import akka.actor.ActorSystem
import com.typesafe.config._
/**
 * Created by yundai on 11/14/15.
 */
package object playactor {

  lazy val ourSystem = ActorSystem("OurExampleSystem")

  def remotingConfig(port: Int) = ConfigFactory.parseString(s"""
akka {
  actor.provider = "akka.remote.RemoteActorRefProvider"
  remote {
    enabled-transports = ["akka.remote.netty.tcp"]
    netty.tcp {
      hostname = "127.0.0.1"
      port = $port
    }
  }
}
  """)

  def remotingSystem(name: String, port: Int) = ActorSystem(name, remotingConfig(port))

}
