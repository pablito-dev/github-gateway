package pl.nowak.githubgateway.web.mapper

import org.springframework.stereotype.Component
import pl.nowak.githubgateway.domain.model.GitRepositoryDetails
import pl.nowak.githubgateway.web.model.GitBranchRESTDto
import pl.nowak.githubgateway.web.model.GitRepositoryDetailsRESTDto

@Component
class GitRepositoryRESTMapper {
    fun fromDomain(domain: GitRepositoryDetails): GitRepositoryDetailsRESTDto {
        return GitRepositoryDetailsRESTDto(
            domain.name,
            domain.owner,
            domain.branches.map { branch ->
                GitBranchRESTDto(
                    branch.name,
                    branch.lastCommitSHA
                )
            }
        )
    }
}
