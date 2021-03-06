package com.wavesplatform.api.http.assets

import akka.http.scaladsl.server.Route
import com.wavesplatform.api.http._
import com.wavesplatform.http.BroadcastRoute
import com.wavesplatform.network._
import com.wavesplatform.settings.RestAPISettings
import com.wavesplatform.state.diffs.TransactionDiffer.TransactionValidationError
import com.wavesplatform.transaction.{Transaction, ValidationError}
import com.wavesplatform.utx.UtxPool
import io.netty.channel.group.ChannelGroup

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Left, Right}

<<<<<<< HEAD
@Path("/tokens/broadcast")
@Api(value = "tokens")
=======
>>>>>>> 3ed6509985baf3fbb9364f1e34f19a473ab95339
case class AssetsBroadcastApiRoute(settings: RestAPISettings, utx: UtxPool, allChannels: ChannelGroup) extends ApiRoute with BroadcastRoute {

  override val route: Route = pathPrefix("tokens" / "broadcast") {
    issue ~ reissue ~ transfer ~ burnRoute ~ batchTransfer ~ exchange
  }

<<<<<<< HEAD
  @Path("/issue")
  @ApiOperation(
    value = "Broadcast signed token issue",
    notes = "Publish signed token issue transaction to the Blockchain",
    httpMethod = "POST",
    consumes = "application/json",
    produces = "application/json"
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        name = "body",
        value = "Json with signed Issue transaction",
        required = true,
        paramType = "body",
        dataType = "com.wavesplatform.api.http.assets.SignedIssueV1Request"
      )))
  @ApiResponses(
    Array(
      new ApiResponse(code = 200, message = "Json with signed token issue transaction contained token ID"),
      new ApiResponse(code = 400, message = "Json with error description", response = classOf[ApiErrorResponse])
    ))
=======
>>>>>>> 3ed6509985baf3fbb9364f1e34f19a473ab95339
  def issue: Route = (path("issue") & post) {
    json[SignedIssueV1Request] { issueReq =>
      doBroadcast(issueReq.toTx)
    }
  }

<<<<<<< HEAD
  @Path("/reissue")
  @ApiOperation(
    value = "Broadcast signed token reissue",
    notes = "Publish signed token reissue transaction to the Blockchain",
    httpMethod = "POST",
    consumes = "application/json",
    produces = "application/json"
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        name = "body",
        value = "Json with signed Reissue transaction",
        required = true,
        paramType = "body",
        dataType = "com.wavesplatform.api.http.assets.SignedReissueV1Request"
      )))
  @ApiResponses(
    Array(
      new ApiResponse(code = 200, message = "Json with signed token reissue transaction"),
      new ApiResponse(code = 400, message = "Json with error description", response = classOf[ApiErrorResponse])
    ))
=======
>>>>>>> 3ed6509985baf3fbb9364f1e34f19a473ab95339
  def reissue: Route = (path("reissue") & post) {
    json[SignedReissueV1Request] { reissueReq =>
      doBroadcast(reissueReq.toTx)
    }
  }

<<<<<<< HEAD
  @Path("/burn")
  @ApiOperation(
    value = "Broadcast signed token burn transaction",
    notes = "Publish signed token burn transaction to the Blockchain",
    httpMethod = "POST",
    consumes = "application/json",
    produces = "application/json"
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(name = "body",
                           value = "Json with signed Burn transaction",
                           required = true,
                           paramType = "body",
                           dataType = "com.wavesplatform.api.http.assets.SignedBurnV1Request")))
  @ApiResponses(
    Array(
      new ApiResponse(code = 200, message = "Json with signed token burn transaction"),
      new ApiResponse(code = 400, message = "Json with error description", response = classOf[ApiErrorResponse])
    ))
=======
>>>>>>> 3ed6509985baf3fbb9364f1e34f19a473ab95339
  def burnRoute: Route = (path("burn") & post) {
    json[SignedBurnV1Request] { burnReq =>
      doBroadcast(burnReq.toTx)
    }
  }

<<<<<<< HEAD
  @Path("/batch-transfer")
  @ApiOperation(value = "Batch transfer operation",
                notes = "Transfer tokens to new addresses",
                httpMethod = "POST",
                produces = "application/json",
                consumes = "application/json")
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        name = "body",
        value = "Array json with data",
        required = true,
        paramType = "body",
        dataType = "com.wavesplatform.api.http.assets.SignedTransferV2Request",
        allowMultiple = true,
        defaultValue =
          "[{\n  \"assetId\": \"E9yZC4cVhCDfbjFJCc9CqkAtkoFy5KaCe64iaxHM2adG\",\n  \"senderPublicKey\": \"CRxqEuxhdZBEHX42MU4FfyJxuHmbDBTaHMhM3Uki7pLw\",\n  \"recipient\": \"3Mx2afTZ2KbRrLNbytyzTtXukZvqEB8SkW7\",\n  \"fee\": 100000,\n  \"amount\": 5500000000,\n  \"attachment\": \"BJa6cfyGUmzBFTj3vvvaew\",\n  \"timestamp\": 1479222433704, \n  \"signature\": \"2TyN8pNS7mS9gfCbX2ktpkWVYckoAmRmDZzKH3K35DKs6sUoXHArzukV5hvveK9t79uzT3cA8CYZ9z3Utj6CnCEo\"\n, {\n  \"assetId\": \"E9yZC4cVhCDfbjFJCc9CqkAtkoFy5KaCe64iaxHM2adG\",\n  \"senderPublicKey\": \"CRxqEuxhdZBEHX42MU4FfyJxuHmbDBTaHMhM3Uki7pLw\",\n  \"recipient\": \"3Mx2afTZ2KbRrLNbytyzTtXukZvqEB8SkW7\",\n  \"fee\": 100000,\n  \"amount\": 5500000000,\n  \"attachment\": \"BJa6cfyGUmzBFTj3vvvaew\",\n  \"timestamp\": 1479222433704, \n  \"signature\": \"2TyN8pNS7mS9gfCbX2ktpkWVYckoAmRmDZzKH3K35DKs6sUoXHArzukV5hvveK9t79uzT3cA8CYZ9z3Utj6CnCEo\"\n}]"
      )
    ))
=======
>>>>>>> 3ed6509985baf3fbb9364f1e34f19a473ab95339
  def batchTransfer: Route = (path("batch-transfer") & post) {
    json[List[SignedTransferRequests]] { reqs =>
      val r = Future
        .traverse(reqs) { req =>
          Future {
            req.eliminate(
              _.toTx,
              _.eliminate(
                _.toTx,
                _ => Left(ValidationError.UnsupportedTransactionType)
              )
            )
          }
        }
        .map { xs: List[Either[ValidationError, Transaction]] =>
          utx.batched { ops =>
            xs.map {
              case Left(e)   => Left(e)
              case Right(tx) => ops.putIfNew(tx).map { case (isNew, _) => (tx, isNew) }
            }
          }
        }
        .map { xs =>
          xs.map {
            case Left(TransactionValidationError(_: ValidationError.AlreadyInTheState, tx)) => Right(tx -> false)
            case Left(e)                                                                    => Left(ApiError.fromValidationError(e))
            case Right(x)                                                                   => Right(x)
          }
        }

      r.foreach { xs =>
        val newTxs = xs.collect { case Right((tx, true)) => tx }
        allChannels.broadcastTx(newTxs)
      }

      r.map { xs =>
        xs.map {
          case Left(e)        => e.json
          case Right((tx, _)) => tx.json()
        }
      }
    }
  }

<<<<<<< HEAD
  @Path("/transfer")
  @ApiOperation(
    value = "Broadcast signed token transfer",
    notes = "Publish signed token transfer transaction to the Blockchain",
    httpMethod = "POST",
    consumes = "application/json",
    produces = "application/json"
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        name = "body",
        value = "Json with signed Transfer transaction",
        required = true,
        paramType = "body",
        dataType = "com.wavesplatform.api.http.assets.SignedTransferV2Request"
      )))
  @ApiResponses(
    Array(
      new ApiResponse(code = 200, message = "Json with signed token transfer transaction"),
      new ApiResponse(code = 400, message = "Json with error description", response = classOf[ApiErrorResponse])
    ))
=======
>>>>>>> 3ed6509985baf3fbb9364f1e34f19a473ab95339
  def transfer: Route = (path("transfer") & post) {
    json[SignedTransferRequests] { transferReq =>
      doBroadcast(
        transferReq.eliminate(
          _.toTx,
          _.eliminate(
            _.toTx,
            _ => Left(ValidationError.UnsupportedTransactionType)
          )
        )
      )
    }
  }

  def exchange: Route = (path("exchange") & post) {
    json[SignedExchangeRequest] { req =>
      doBroadcast(req.toTx)
    }
  }
<<<<<<< HEAD:src/main/scala/scorex/api/http/assets/AssetsBroadcastApiRoute.scala

  @Path("/sponsor")
  @ApiOperation(
    value = "Broadcast signed Sponsor token Fee",
    notes = "Publish signed Sponsor token Fee transaction to the Blockchain",
    httpMethod = "POST",
    consumes = "application/json",
    produces = "application/json"
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(name = "body",
                           value = "Json with signed Reissue transaction",
                           required = true,
                           paramType = "body",
                           dataType = "scorex.api.http.assets.SignedSponsorFeeRequest")))
  @ApiResponses(
    Array(
      new ApiResponse(code = 200, message = "Json with signed Sponsor fee transaction"),
      new ApiResponse(code = 400, message = "Json with error description", response = classOf[ApiErrorResponse])
    ))
  def sponsor: Route = (path("reissue") & post) {
    import scorex.api.http.assets.SponsorFeeRequest._
    json[SignedSponsorFeeRequest] { sponsorReq =>
      doBroadcast(sponsorReq.toTx)
    }
  }
=======
>>>>>>> 4f3106f04982d02459cdc4705ed835b976d02dd9:src/main/scala/com/wavesplatform/api/http/assets/AssetsBroadcastApiRoute.scala
}
