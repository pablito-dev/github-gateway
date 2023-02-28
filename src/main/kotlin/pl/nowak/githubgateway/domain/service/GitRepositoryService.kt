package pl.nowak.githubgateway.domain.service

import org.springframework.stereotype.Service
import pl.nowak.githubgateway.domain.model.GitRepositoryDetails
import pl.nowak.githubgateway.domain.port.GitRepositoryPort
import reactor.core.publisher.Flux

@Service
class GitRepositoryService(
    private val gitRepositoryPort: GitRepositoryPort
) {
    fun getRepositoriesForUserName(userName: String): Flux<GitRepositoryDetails> {
        return gitRepositoryPort.getRepositoriesForUserName(userName)
    }
}
