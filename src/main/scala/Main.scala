package main

import java.util.concurrent.Executors

import cats.effect.{ContextShift, IO}
import io.circe.JsonObject
import io.circe.parser._
import io.circe.syntax._
import org.http4s.Method._
import org.http4s.client.blaze._
import org.http4s.client.dsl.io._
import org.http4s.headers._
import org.http4s.{Header, MediaType, Method, Request, Uri}

import scala.concurrent.ExecutionContext

object Main {

    val PROTOCOL: String = "https"
    val CONSEIl_HOST: String = "conseil-prod.cryptonomic-infra.tech"
    val CONSEIL_PORT: Int = 443
    val CONSEIl_API_KEY: String = "f86ab59d-d2ea-443b-98e2-6c0785e3de8c"
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
        testAttributes(httpClient)

        testAttributeData(httpClient)

        validateAttributeData(httpClient)



        //        TESTING TEZOS CHAIN DATA ENDPOINTS

        println("\n\n\nTesting Block Head\n")
        println(sendConseilRequest(httpClient, CONSEIL.withPath(Requests.TezosChainRequests.TEZOS_BLOCK_HEAD)))

        println("\n\n\nTesting Operation Kinds\n")
        println(sendConseilRequest(httpClient, CONSEIL.withPath(Requests.TezosChainRequests.TEZOS_OPERATION_KINDS)))



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
     * Send a Conseil query
     * @param client The HTTP client used to send the request
     * @param queryUrl The API endpoint to send the query to
     * @param queryBody The body of the query
     * @return The result of the query
     */
    def sendConseilQuery(client: BlazeClientBuilder[IO], queryUrl: Uri, queryBody: String): String = {

        val request = Request[IO](method = Method.POST, uri = queryUrl)
          .withEntity[String](queryBody)
          .withHeaders(
              Header("apiKey", CONSEIl_API_KEY),
              `Content-Type`(MediaType.application.json),
              Accept(MediaType.application.json)
          )

        client.resource.use {client =>
            client.expect[String](request)
        }.unsafeRunSync()
    }

    /**
     * Test retrieval of all tezos attributes from the entities given
     * @param httpClient The BlazeClientBuilder http client to make the conseil request
     * @param entities The Array of entities grab attributes for
     */
    def testAttributes(httpClient: BlazeClientBuilder[IO], entities: Array[String] = Requests.TEZOS_ENTITIES): Unit = {
        entities.foreach(entity => {
            println("\n\n\nTesting Tezos " + entity + " Attributes\n")
            println(sendConseilRequest(httpClient, CONSEIL.withPath(Requests.getTezosAttributePath(entity))))
        })
    }

    def testAttributeData(httpClient: BlazeClientBuilder[IO], entityAttributes: Map[String, Array[String]] = Requests.TEZOS_ENTITY_ATTRIBUTES): Unit = {

        entityAttributes.foreach(entity => {

            val queryString: String =
                """
                  |{
                  |     "fields": %FIELDS%,
                  |     "predicates": [],
                  |     "orderBy": [],
                  |     "aggregation": [],
                  |     "limit": 10
                  |}
                  |""".stripMargin.replace("%FIELDS%", entity._2.asJson.toString)

            println("\n\n\nTesting Tezos " + entity._1 + " Query\n")
            println(sendConseilQuery(httpClient, CONSEIL.withPath(Requests.getTezosQueryPath(entity._1)), queryString))
        })

    }

    def validateAttributeData(httpClient: BlazeClientBuilder[IO], entityAttributes: Map[String, Array[String]] = Requests.TEZOS_ENTITY_ATTRIBUTES): Unit = {

        entityAttributes.foreach(entity => {

            entity._2.foreach(attribute => {

                val queryString: String =
                    """
                      |{
                      |     "field": ["%FIELD%"],
                      |     "predicates": [
                      |         {
                      |             "field": "%FIELD%",
                      |             "operation": "isnull",
                      |             "set": [""],
                      |             "inverse": true
                      |         }
                      |     ],
                      |     "orderBy": [],
                      |     "aggregation": [],
                      |     "limit": 1
                      |}
                      |""".stripMargin.replace("%FIELD%", attribute)

                println("\n\n\nValidating Tezos " + entity._1 + " " + attribute + " Data\n")
                var result: String = ""
                try {
                    result = sendConseilQuery(httpClient, CONSEIL.withPath(Requests.getTezosQueryPath(entity._1)), queryString)
                } catch {
                    case x: org.http4s.client.UnexpectedStatus => {

                        val queryString: String =
                            """
                              |{
                              |     "field": ["%FIELD%"],
                              |     "predicates": [],
                              |     "orderBy": [],
                              |     "aggregation": [],
                              |     "limit": 1
                              |}
                              |""".stripMargin.replace("%FIELD%", attribute)

                        result = sendConseilQuery(httpClient, CONSEIL.withPath(Requests.getTezosQueryPath(entity._1)), queryString)

                    }
                }

                println(result)

                if(result.compareTo("") == 0) throw new NullPointerException("Query Returned an Empty String")

                parse(result) match {
                    case Left(failure) => throw new IllegalArgumentException("Query did not return valid JSON")
                    case Right(json) => {

                        if(json.findAllByKey(attribute).isEmpty) throw new IllegalArgumentException("Non-Null Attribute was Null")

                    }
                }
            })
        })
    }

}