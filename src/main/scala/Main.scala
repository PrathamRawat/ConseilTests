package main

import main.Requests
import org.http4s.client.blaze._
import org.http4s.client._
import org.http4s.client.dsl.io._
import org.http4s.headers._
import org.http4s.{Header, MediaType, ParseResult, Uri}
import org.http4s.Method._
import org.http4s.UriTemplate._
import java.util.concurrent.Executors

import scala.concurrent.ExecutionContext
import cats.effect.{Blocker, ContextShift, IO}

import scala.concurrent.ExecutionContext.Implicits.global

object Main {

    val PROTOCOL: String = "https"
    val CONSEIl_HOST: String = "conseil-prod.cryptonomic-infra.tech"
    val CONSEIL_PORT: Int = 443
    val CONSEIl_API_KEY: String = ""

    def main(args: Array[String]): Unit = {

        val httpClient: BlazeClientBuilder[IO] = createHttpClient()
        val conseilHost: org.http4s.Uri = Uri.unsafeFromString(PROTOCOL + "://" + CONSEIl_HOST + ":" + CONSEIL_PORT)

        //        TESTING CONSEIL BUILD REQUESTS

        println("\n\n\nTesting Info Endpoint:\n")
        println(sendConseilRequest(httpClient, conseilHost.withPath(Requests.ConseilRequests.CONSEIL_BUILD_INFO)))

        println("\n\n\nTesting Platforms Endpoint\n")
        println(sendConseilRequest(httpClient, conseilHost.withPath(Requests.ConseilRequests.CONSEIL_PLATFORMS)))

        //        TESTING CONSEIL TEZOS BUILD CONFIG

        println("\n\n\nTesting Tezos Networks\n")
        println(sendConseilRequest(httpClient, conseilHost.withPath(Requests.TezosConfigRequests.TEZOS_NETWORKS)))

        println("\n\n\nTesting Tezos Entitites\n")
        println(sendConseilRequest(httpClient, conseilHost.withPath(Requests.TezosConfigRequests.TEZOS_ENTITIES)))

        //        TESTING TEZOS ENTITY ATTRIBUTES

        println("\n\n\nTesting Tezos Accounts Attributes\n")
        println(sendConseilRequest(httpClient, conseilHost.withPath(Requests.TezosEntities.TEZOS_ACCOUNTS_ATTRIBUTES)))

        println("\n\n\nTesting Tezos Account History Attributes\n")
        println(sendConseilRequest(httpClient, conseilHost.withPath(Requests.TezosEntities.TEZOS_ACCOUNTS_HISTORY_ATTRIBUTES)))

        println("\n\n\nTesting Tezos Baker Registry Attributes\n")
        println(sendConseilRequest(httpClient, conseilHost.withPath(Requests.TezosEntities.TEZOS_BAKER_REGISTRY_ATTRIBUTES)))

        println("\n\n\nTesting Tezos Bakers Attributes\n")
        println(sendConseilRequest(httpClient, conseilHost.withPath(Requests.TezosEntities.TEZOS_BAKER_ATTRIBUTES)))

        println("\n\n\nTesting Tezos Baker History Attributes\n")
        println(sendConseilRequest(httpClient, conseilHost.withPath(Requests.TezosEntities.TEZOS_BAKERS_HISTORY_ATTRIBUTES)))

        println("\n\n\nTesting Tezos Balance Updates Attributes\n")
        println(sendConseilRequest(httpClient, conseilHost.withPath(Requests.TezosEntities.TEZOS_BALANCE_UPDATES_ATTRIBUTES)))

        println("\n\n\nTesting Tezos Big Map Contents Attributes\n")
        println(sendConseilRequest(httpClient, conseilHost.withPath(Requests.TezosEntities.TEZOS_BIG_MAP_CONTENTS_ATTRIBUTES)))

        println("\n\n\nTesting Tezos Big Map Attributes\n")
        println(sendConseilRequest(httpClient, conseilHost.withPath(Requests.TezosEntities.TEZOS_BIG_MAPS_ATTRIBUTES)))

        println("\n\n\nTesting Tezos Block Attributes\n")
        println(sendConseilRequest(httpClient, conseilHost.withPath(Requests.TezosEntities.TEZOS_BLOCK_ATTRIBUTES)))

        println("\n\n\nTesting Tezos Fees Attributes\n")
        println(sendConseilRequest(httpClient, conseilHost.withPath(Requests.TezosEntities.TEZOS_FEES_ATTRIBUTES)))

        println("\n\n\nTesting Tezos Governance Attributes\n")
        println(sendConseilRequest(httpClient, conseilHost.withPath(Requests.TezosEntities.TEZOS_GOVERNANCE_ATTRIBUTES)))

        println("\n\n\nTesting Tezos Known Addresses Attributes\n")
        println(sendConseilRequest(httpClient, conseilHost.withPath(Requests.TezosEntities.TEZOS_KNOWN_ADDRESSES_ATTRIBUTES)))

        println("\n\n\nTesting Tezos Operation Groups Attributes\n")
        println(sendConseilRequest(httpClient, conseilHost.withPath(Requests.TezosEntities.TEZOS_OPERATION_GROUPS_ATTRIBUTES)))

        println("\n\n\nTesting Tezos Operations Attributes\n")
        println(sendConseilRequest(httpClient, conseilHost.withPath(Requests.TezosEntities.TEZOS_OPERATIONS_ATTRIBUTES)))

        println("\n\n\nTesting Tezos Originated Big Maps Attributes\n")
        println(sendConseilRequest(httpClient, conseilHost.withPath(Requests.TezosEntities.TEZOS_ORIGINATED_ACCOUNT_MAPS_ATTRIBUTES)))


        //        TESTING TEZOS CHAIN DATA ENDPOINTS

        println("\n\n\nTesting Block Head\n")
        println(sendConseilRequest(httpClient, conseilHost.withPath(Requests.TezosChainRequests.TEZOS_BLOCK_HEAD)))



    }

    def createHttpClient(): BlazeClientBuilder[IO] = {

        val pool = Executors.newCachedThreadPool()
        val clientExecution: ExecutionContext = ExecutionContext.fromExecutor(pool)
        implicit val shift: ContextShift[IO] = IO.contextShift(clientExecution)

        val clientBuild = BlazeClientBuilder[IO](clientExecution)

        clientBuild
    }

    def sendConseilRequest(client: BlazeClientBuilder[IO], queryUrl: Uri): String = {

        val conseilRequest = GET(
            queryUrl,
            `Content-Type`(MediaType.application.json),
            Accept(MediaType.application.json),
            Header("apiKey", CONSEIl_API_KEY)
        )

        client.resource.use { client =>
            client.expect[String](conseilRequest)
        }.unsafeRunSync()
    }


    def test(name: String, path: String): Unit = {
        println()
    }

}