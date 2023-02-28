package pl.nowak.githubgateway.gitrepository.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient


@ConfigurationProperties(prefix = "app.adapter.github")
class GitRepositoryProperties(
    var url: String = "",
    var token: String = "",
)

@Configuration
open class GitRepositoryConfiguration {

    @Bean("githubWebClient")
    fun githubWebClient(
        gitRepositoryProperties: GitRepositoryProperties
    ): WebClient {
        return WebClient.builder()
            .baseUrl(gitRepositoryProperties.url)
            .defaultHeader("Accept", "application/vnd.github+json")
            .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
            .defaultHeader("Authorization", "Bearer ${gitRepositoryProperties.token}")
            .build()
    }
}
