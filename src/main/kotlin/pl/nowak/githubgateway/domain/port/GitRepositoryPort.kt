package pl.nowak.githubgateway.domain.port

import pl.nowak.githubgateway.domain.model.GitRepositoryDetails
import reactor.core.publisher.Flux

interface GitRepositoryPort {
    fun getRepositoriesForUserName(userName: String): Flux<GitRepositoryDetails>
}
