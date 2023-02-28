package pl.nowak.githubgateway.web.model

class GitRepositoryDetailsRESTDto(
    val name: String,
    val owner: String,
    val branches: List<GitBranchRESTDto>
)

