# cardano-authentication

<p align="left">
<img alt="Tests" src="https://github.com/cardano-foundation/cf-ledger-consumer/actions/workflows/tests.yaml/badge.svg" />
<img alt="Release" src="https://github.com/cardano-foundation/cf-ledger-consumer/actions/workflows/release.yaml/badge.svg?branch=main" />
<img alt="Publish" src="https://github.com/cardano-foundation/cf-ledger-consumer/actions/workflows/publish.yaml/badge.svg?branch=main" />
</p>

## Getting Started

### Prerequisites

- Docker && Docker Compose
- 
### Installing

- Clone the repository
- Copy `./.m2/settings.default.xml` to `./.m2/settings.xml`
- Fill `{username_github}` and `{token_github}` in `./.m2/settings.xml` with your github username and token. Guide to generate a token with `read:packages` scope [here](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token#creating-a-personal-access-token-classic)
- Copy `.env.example`  to `.env`
- Fill the `.env` file with your values (explain below)
- Create if not exists external network `infrastructure-net-local` with `docker network create infrastructure-net-local`
- Run `docker-compose -f docker-compose-local.yml up -d` to start the containers
