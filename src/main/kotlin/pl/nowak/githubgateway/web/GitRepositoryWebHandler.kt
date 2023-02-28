package pl.nowak.githubgateway.web

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import pl.nowak.githubgateway.domain.exception.UserNotFoundException
import pl.nowak.githubgateway.domain.service.GitRepositoryService
import pl.nowak.githubgateway.web.mapper.GitRepositoryRESTMapper
import pl.nowak.githubgateway.web.model.ErrorResponse
import reactor.core.publisher.Mono

@Component
class GitRepositoryWebHandler(
    private val gitRepositoryService: GitRepositoryService,
    private val gitRepositoryRESTMapper: GitRepositoryRESTMapper
) {
    fun handleGetUserRepositoriesJSON(serverRequest: ServerRequest): Mono<ServerResponse> {
        val userName = serverRequest.pathVariable("userName")

        return gitRepositoryService.getRepositoriesForUserName(userName).collectList().flatMap { repositoryDetails ->
            val dto = repositoryDetails.map { repository ->
                gitRepositoryRESTMapper.fromDomain(repository)
            }
            ServerResponse.ok().bodyValue(dto)
        }.onErrorResume { error ->
            when (error) {
                is UserNotFoundException -> ServerResponse.status(HttpStatus.NOT_FOUND)
                    .bodyValue(
                        ErrorResponse(
                            HttpStatus.NOT_FOUND.value(),
                            "GitHub user: $userName cannot be found."
                        )
                    )

                else -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .bodyValue(
                        ErrorResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Internal error occurred"
                        )
                    )
            }
        }
    }

    @Suppress("UnusedPrivateMember")
    fun handleGetUserRepositoriesXML(serverRequest: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.status(HttpStatus.NOT_ACCEPTABLE)
            .bodyValue(
                ErrorResponse(
                    HttpStatus.NOT_ACCEPTABLE.value(),
                    "Content Type ${MediaType.APPLICATION_XML_VALUE} is not accepted."
                )
            )
    }
}
