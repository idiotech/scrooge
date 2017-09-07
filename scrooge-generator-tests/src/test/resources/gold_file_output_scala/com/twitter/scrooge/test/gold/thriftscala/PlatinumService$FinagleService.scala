/**
 * Generated by Scrooge
 *   version: ?
 *   rev: ?
 *   built at: ?
 */
package com.twitter.scrooge.test.gold.thriftscala

import com.twitter.finagle.{RichServerParam, SimpleFilter, Thrift, Filter => finagle$Filter, Service => finagle$Service}
import com.twitter.finagle.stats.{Counter, NullStatsReceiver, StatsReceiver}
import com.twitter.scrooge.{TReusableBuffer, ThriftMethod, ThriftStruct}
import com.twitter.util.{Future, Return, Throw, Throwables}
import java.nio.ByteBuffer
import java.util.Arrays
import org.apache.thrift.protocol._
import org.apache.thrift.TApplicationException
import org.apache.thrift.transport.TMemoryInputTransport
import scala.collection.mutable.{
  ArrayBuffer => mutable$ArrayBuffer, HashMap => mutable$HashMap}
import scala.collection.{Map, Set}

import scala.language.higherKinds


@javax.annotation.Generated(value = Array("com.twitter.scrooge.Compiler"))
class PlatinumService$FinagleService(
  iface: PlatinumService[Future],
  serverParam: RichServerParam
) extends GoldService$FinagleService(iface, serverParam) {
  import PlatinumService._

  @deprecated("Use com.twitter.finagle.RichServerParam", "2017-08-16")
  def this(
    iface: PlatinumService[Future],
    protocolFactory: TProtocolFactory,
    stats: StatsReceiver = NullStatsReceiver,
    maxThriftBufferSize: Int = Thrift.param.maxThriftBufferSize,
    serviceName: String = "PlatinumService"
  ) = this(iface, RichServerParam(protocolFactory, serviceName, maxThriftBufferSize, stats))

  @deprecated("Use com.twitter.finagle.RichServerParam", "2017-08-16")
  def this(
    iface: PlatinumService[Future],
    protocolFactory: TProtocolFactory,
    stats: StatsReceiver,
    maxThriftBufferSize: Int
  ) = this(iface, protocolFactory, stats, maxThriftBufferSize, "PlatinumService")

  @deprecated("Use com.twitter.finagle.RichServerParam", "2017-08-16")
  def this(
    iface: PlatinumService[Future],
    protocolFactory: TProtocolFactory
  ) = this(iface, protocolFactory, NullStatsReceiver, Thrift.param.maxThriftBufferSize)

  override def serviceName: String = serverParam.serviceName
  addService("moreCoolThings", {
    val statsFilter = perMethodStatsFilter(MoreCoolThings)

    val methodService = new finagle$Service[MoreCoolThings.Args, MoreCoolThings.SuccessType] {
      def apply(args: MoreCoolThings.Args): Future[MoreCoolThings.SuccessType] = {
        iface.moreCoolThings(args.request)
      }
    }

    val protocolExnFilter = new SimpleFilter[(TProtocol, Int), Array[Byte]] {
      def apply(
        request: (TProtocol, Int),
        service: finagle$Service[(TProtocol, Int), Array[Byte]]
      ): Future[Array[Byte]] = {
        val (iprot, seqid) = request
        service(request).rescue {
          case e: TProtocolException => {
            iprot.readMessageEnd()
            exception("moreCoolThings", seqid, TApplicationException.PROTOCOL_ERROR, e.getMessage)
          }
          case e: Exception => Future.exception(e)
        }
      }
    }

    val serdeFilter = new finagle$Filter[(TProtocol, Int), Array[Byte], MoreCoolThings.Args, MoreCoolThings.SuccessType] {
      override def apply(
        request: (TProtocol, Int),
        service: finagle$Service[MoreCoolThings.Args, MoreCoolThings.SuccessType]
      ): Future[Array[Byte]] = {
        val (iprot, seqid) = request
        val args = MoreCoolThings.Args.decode(iprot)
        iprot.readMessageEnd()
        val res = service(args)
        res.flatMap { value =>
          reply("moreCoolThings", seqid, MoreCoolThings.Result(success = Some(value)))
        }.rescue {
          case e: com.twitter.scrooge.test.gold.thriftscala.AnotherException => {
            reply("moreCoolThings", seqid, MoreCoolThings.Result(ax = Some(e)))
          }
          case e: com.twitter.scrooge.test.gold.thriftscala.OverCapacityException => {
            reply("moreCoolThings", seqid, MoreCoolThings.Result(oce = Some(e)))
          }
          case e => Future.exception(e)
        }
      }
    }

    protocolExnFilter.andThen(serdeFilter).andThen(statsFilter).andThen(methodService)
  })
}
