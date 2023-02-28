package pl.nowak.githubgateway.gitrepository.mapper

import org.springframework.stereotype.Component
import pl.nowak.githubgateway.domain.model.GitBranch
import pl.nowak.githubgateway.domain.model.GitRepositoryDetails
import pl.nowak.githubgateway.gitrepository.model.BranchDetailsDto
import pl.nowak.githubgateway.gitrepository.model.UserRepositoriesDto

@Component
class GitRepositoryMapper {
    fun fromDto(repository: UserRepositoriesDto, branches: List<BranchDetailsDto>): GitRepositoryDetails {
        return GitRepositoryDetails(
            repository.name,
            repository.owner.login,
            branches.map { branch ->
                GitBranch(
                    branch.name,
                    branch.commit.sha
                )
            }
        )
    }
}
