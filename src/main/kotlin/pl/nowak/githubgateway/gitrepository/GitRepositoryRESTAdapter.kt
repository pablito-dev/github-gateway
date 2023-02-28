package pl.nowak.githubgateway.gitrepository

import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import pl.nowak.githubgateway.domain.exception.GitRepositoryException
import pl.nowak.githubgateway.domain.exception.UserNotFoundException
import pl.nowak.githubgateway.domain.model.GitRepositoryDetails
import pl.nowak.githubgateway.domain.port.GitRepositoryPort
import pl.nowak.githubgateway.gitrepository.mapper.GitRepositoryMapper
import pl.nowak.githubgateway.gitrepository.model.BranchDetailsDto
import pl.nowak.githubgateway.gitrepository.model.UserRepositoriesDto
import reactor.core.publisher.Flux

@Component
@Profile("!test")
class GitRepositoryRESTAdapter(
    private val webClient: WebClient,
    private val mapper: GitRepositoryMapper
) : GitRepositoryPort {

    override fun getRepositoriesForUserName(userName: String): Flux<GitRepositoryDetails> {
        return getUserRepositories(userName)
            .filter { repository ->
                !repository.fork
            }
            .flatMap { repositoryDto ->
                getBranchesForRepository(userName, repositoryDto.name).collectList().map { branches ->
                    repositoryDto to branches
                }
            }.map { (repository, branches) ->
                mapper.fromDto(repository, branches)
            }
    }

    private fun getUserRepositories(userName: String): Flux<UserRepositoriesDto> {
        return webClient.get()
            .uri("users/${userName}/repos?type=all")
            .exchangeToFlux { response ->
                when (response.statusCode()) {
                    HttpStatus.OK -> response.bodyToFlux<UserRepositoriesDto>()
                    HttpStatus.NOT_FOUND -> throw UserNotFoundException("Username $userName cannot be found")
                    else -> throw GitRepositoryException("Error while fetching git repository code: " +
                            "${response.statusCode()}")
                }
            }
    }

    private fun getBranchesForRepository(userName: String, repositoryName: String): Flux<BranchDetailsDto> {
        return webClient.get()
            .uri("repos/$userName/$repositoryName/branches")
            .exchangeToFlux { response ->
                when (response.statusCode()) {
                    HttpStatus.OK -> response.bodyToFlux<BranchDetailsDto>()
                    else -> throw GitRepositoryException("Error while fetching git repository code: " +
                            "${response.statusCode()}")
                }
            }
    }
}
