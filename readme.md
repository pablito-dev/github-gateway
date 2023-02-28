GitHub gateway application

Description:

This is written in Kotlin on Spring Reactive Stack. Kotest is used for testing and Detekt is used for static code
analysis. Application uses Hexagonal architecture instead of standard Layered architecture.

To run application use:

`gradle bootRun`

To test application use:

`gradle check`

Application requires GitHub personal Token to run. Put it under `app.adapter.github.token` in `application.yaml`.

List of possible improvements:

- Add tests for GitRepositoryRESTAdapter using `TestContainers` and `MockServer`.
- Adjust application to use GitHub app's instead of personal token. This however requires a little frontend and a
  redirect to login to GitHub.
- Introduce `.toml` file to manage dependencies and versions.