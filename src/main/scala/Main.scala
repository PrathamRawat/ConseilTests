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
    val CONSEIL: org.http4s.Uri = Uri.unsafeFromString(PROTOCOL + "://" + CONSEIl_HOST + ":" + CONSEIL_PORT)

    def main(args: Array[String]): Unit = {

        val httpClient: BlazeClientBuilder[IO] = createHttpClient()

        //        TESTING CONSEIL BUILD REQUESTS

        println("\n\n\nTesting Info Endpoint:\n")
        println(sendConseilRequest(httpClient, CONSEIL.withPath(Requests.ConseilRequests.CONSEIL_BUILD_INFO)))

        println("\n\n\nTesting Platforms Endpoint\n")
        println(sendConseilRequest(httpClient, CONSEIL.withPath(Requests.ConseilRequests.CONSEIL_PLATFORMS)))

        //        TESTING CONSEIL TEZOS BUILD CONFIG

        println("\n\n\nTesting Tezos Networks\n")
        println(sendConseilRequest(httpClient, CONSEIL.withPath(Requests.TezosConfig.TEZOS_NETWORKS)))

        println("\n\n\nTesting Tezos Entitites\n")
        println(sendConseilRequest(httpClient, CONSEIL.withPath(Requests.TezosConfig.TEZOS_ENTITIES)))

        //        TESTING TEZOS ENTITY ATTRIBUTES
        testAttributes(httpClient, Requests.TEZOS_ENTITIES)



        //        TESTING TEZOS CHAIN DATA ENDPOINTS

        println("\n\n\nTesting Block Head\n")
        println(sendConseilRequest(httpClient, CONSEIL.withPath(Requests.TezosChainRequests.TEZOS_BLOCK_HEAD)))



    }

    /**
     * Create the http client used to make requests
     * @return The client object
     */
    def createHttpClient(): BlazeClientBuilder[IO] = {

        val pool = Executors.newCachedThreadPool()
        val clientExecution: ExecutionContext = ExecutionContext.fromExecutor(pool)
        implicit val shift: ContextShift[IO] = IO.contextShift(clientExecution)

        val clientBuild = BlazeClientBuilder[IO](clientExecution)

        clientBuild
    }

    /**
     * Send a conseil request with an API Key and proper headers
     * @param client The BlazeClientBuilder client with which to make the request
     * @param queryUrl The URI to query
     * @return String results from the query performed
     */
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

    /**
     * Test retrieval of all tezos attributes from the entities given
     * @param httpClient The BlazeClientBuilder http client to make the conseil request
     * @param entities The Array of entities grab attributes for
     */
    def testAttributes(httpClient: BlazeClientBuilder[IO], entities: Array[String]): Unit = {
        entities.foreach(entity => {
            println("\n\n\nTesting Tezos " + entity + " Attributes\n")
            println(sendConseilRequest(httpClient, CONSEIL.withPath(Requests.getTezosAttributePath(entity))))
        })
    }

}