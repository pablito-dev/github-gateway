package pl.nowak.githubgateway.domain.model

data class GitRepositoryDetails(
    val name: String,
    val owner: String,
    val branches: List<GitBranch>
)
