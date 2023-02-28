package pl.nowak.githubgateway.gitrepository.model

data class BranchDetailsDto(
    val name: String,
    val commit: CommitDetailsDto
)

data class CommitDetailsDto(
    val sha: String
)
