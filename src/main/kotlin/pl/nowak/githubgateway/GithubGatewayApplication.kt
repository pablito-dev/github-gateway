package pl.nowak.githubgateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import pl.nowak.githubgateway.gitrepository.config.GitRepositoryProperties

@SpringBootApplication
@EnableConfigurationProperties(GitRepositoryProperties::class)
class GithubGatewayApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
	runApplication<GithubGatewayApplication>(*args)
}
