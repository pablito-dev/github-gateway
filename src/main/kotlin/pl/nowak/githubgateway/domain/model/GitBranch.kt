package pl.nowak.githubgateway.domain.model

data class GitBranch(
    val name: String,
    val lastCommitSHA: String
)
