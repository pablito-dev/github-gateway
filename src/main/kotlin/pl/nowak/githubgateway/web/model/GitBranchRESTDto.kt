package pl.nowak.githubgateway.web.model

data class GitBranchRESTDto(
    val name: String,
    val lastCommitSHA: String
)
