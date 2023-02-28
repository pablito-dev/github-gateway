package pl.nowak.githubgateway

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.core.spec.style.StringSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import pl.nowak.githubgateway.config.GitRepositoryInMemoryAdapter
import pl.nowak.githubgateway.web.model.GitRepositoryDetailsRESTDto

@TestConfiguration
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class GithubGatewayApplicationTests : StringSpec() {
    override fun extensions() = listOf(SpringExtension)

    private val testClient = WebTestClient
        .bindToServer()
        .baseUrl("http://localhost:8080")
        .build()

    private val objectMapper = jacksonObjectMapper()

    init {
        "application should return 406 when application/xml Accept header is sent" {
            val userName = "pablo"

            testClient.get()
                .uri("/git/users/$userName")
                .accept(MediaType.APPLICATION_XML)
                .exchange()
                .expectStatus().is4xxClientError
                .expectBody().json(
                    """
                    {
                        "code": 406,
                        "message": "Content Type ${MediaType.APPLICATION_XML_VALUE} is not accepted."
                    }
                    """.trimIndent()
                )
        }

        "application should return 404 when userName does not exist" {
            val userName = "pablo1"

            testClient.get()
                .uri("/git/users/$userName")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is4xxClientError
                .expectBody().json(
                    """
                    {
                        "code": 404,
                        "message": "GitHub user: $userName cannot be found."
                    }
                    """.trimIndent()
                )
        }

        "application should return UserRepositories when user exists" {
            val userName = "pablo"

            testClient.get()
                .uri("/git/users/$userName")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .consumeWith { byteArrayBody ->
                    val body = objectMapper.readValue<List<GitRepositoryDetailsRESTDto>>(byteArrayBody.responseBody!!)

                    body[0].name shouldBe "repo"
                    body[0].owner shouldBe userName
                    body[0].branches[0].name shouldBe "develop"
                    body[0].branches[0].lastCommitSHA shouldBe "sha"
                }
        }
    }

    @Bean
    fun testGitRepositoryAdapter(): GitRepositoryInMemoryAdapter {
        return GitRepositoryInMemoryAdapter()
    }
}
