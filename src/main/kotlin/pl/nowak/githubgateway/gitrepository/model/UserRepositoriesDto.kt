package pl.nowak.githubgateway.gitrepository.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserRepositoriesDto(
    val id: Long,
    val name: String,
    @JsonProperty("full_name")
    val fullName: String,
    val owner: RepositoryOwnerDto,
    val fork: Boolean
)

data class RepositoryOwnerDto(
    val login: String
)
