package pl.nowak.githubgateway.web.mapper

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import pl.nowak.githubgateway.domain.model.GitBranch
import pl.nowak.githubgateway.domain.model.GitRepositoryDetails

class GitRepositoryRESTMapperTests : StringSpec({
    "fromDomain should return GitRepositoryDetailsRESTDto object with a single branch" {
        val repositoryDetails = GitRepositoryDetails(
            "name",
            "owner",
            listOf(
                GitBranch("develop", "sha")
            )
        )

        val mapper = GitRepositoryRESTMapper()

        val result = mapper.fromDomain(repositoryDetails)

        assertSoftly {
            result.name shouldBe "name"
            result.owner shouldBe "owner"
            result.branches shouldHaveSize 1
            result.branches[0].name shouldBe "develop"
            result.branches[0].lastCommitSHA shouldBe "sha"
        }
    }
})
