package pl.nowak.githubgateway.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse
import pl.nowak.githubgateway.domain.service.GitRepositoryService

@Configuration
class RouterFunctions(
    private val handler: GitRepositoryWebHandler
) {
    @Bean
    @Suppress("UnusedPrivateMember")
    fun router(gitRepositoryService: GitRepositoryService): RouterFunction<ServerResponse> {
        return RouterFunctions.route(
            RequestPredicates.GET("/git/users/{userName}")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
            handler::handleGetUserRepositoriesJSON
        ).and(RouterFunctions.route(
            RequestPredicates.GET("/git/users/{userName}")
                .and(RequestPredicates.accept(MediaType.APPLICATION_XML)),
            handler::handleGetUserRepositoriesXML
        ))
    }
}
