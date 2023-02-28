package pl.nowak.githubgateway.config

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import pl.nowak.githubgateway.domain.exception.UserNotFoundException
import pl.nowak.githubgateway.domain.model.GitBranch
import pl.nowak.githubgateway.domain.model.GitRepositoryDetails
import pl.nowak.githubgateway.domain.port.GitRepositoryPort
import reactor.core.publisher.Flux

@Component
@Profile("test")
class GitRepositoryInMemoryAdapter : GitRepositoryPort {
    override fun getRepositoriesForUserName(userName: String): Flux<GitRepositoryDetails> {
        return when (userName) {
            "pablo" -> Flux.fromIterable(
                listOf(
                    GitRepositoryDetails(
                        "repo",
                        userName,
                        listOf(
                            GitBranch("develop", "sha")
                        )
                    )
                )
            )
            else -> Flux.error(UserNotFoundException("error"))
        }
    }
}
