package pl.nowak.githubgateway.girepository.mapper

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import pl.nowak.githubgateway.gitrepository.mapper.GitRepositoryMapper
import pl.nowak.githubgateway.gitrepository.model.BranchDetailsDto
import pl.nowak.githubgateway.gitrepository.model.CommitDetailsDto
import pl.nowak.githubgateway.gitrepository.model.RepositoryOwnerDto
import pl.nowak.githubgateway.gitrepository.model.UserRepositoriesDto

class GitRepositoryMapperTests : StringSpec({
    "fromDto should return RepositoryDetails object with a single branch" {
        val repository = UserRepositoriesDto(
            1,
            "repo",
            "test/repo",
            RepositoryOwnerDto("test"),
            false
        )
        val branches = listOf(
            BranchDetailsDto("master", CommitDetailsDto("sha"))
        )
        val mapper = GitRepositoryMapper()

        val result = mapper.fromDto(repository, branches)

        assertSoftly {
            result.name shouldBe "repo"
            result.owner shouldBe "test"
            result.branches shouldHaveSize 1
            result.branches[0].name shouldBe "master"
            result.branches[0].lastCommitSHA shouldBe "sha"
        }
    }
})
