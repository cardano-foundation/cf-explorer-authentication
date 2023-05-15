# cf-explorer-authentication

<p align="left">
<img alt="Tests" src="https://github.com/cardano-foundation/cf-explorer-authentication/actions/workflows/tests.yaml/badge.svg" />
<img alt="Release" src="https://github.com/cardano-foundation/cf-explorer-authentication/actions/workflows/release.yaml/badge.svg?branch=main" />
<img alt="Publish" src="https://github.com/cardano-foundation/cf-explorer-authentication/actions/workflows/publish.yaml/badge.svg?branch=main" />
</p>

## Getting Started

### Prerequisites

- Docker && Docker Compose

### Installing

- Clone the repository
- Copy `./.m2/settings.default.xml.tpl` to `./.m2/settings.xml`
- Fill `{PRIVATE_MVN_REGISTRY_URL}`, `{PRIVATE_MVN_REGISTRY_USER}` and `{PRIVATE_MVN_REGISTRY_PASS}` in `./.m2/settings.xml`
- Copy `.env.example`  to `.env`
- Fill the `.env` file with your values (explain below)
- Create if not exists external network `infrastructure-net-local` with `docker network create infrastructure-net-local`
- Run `docker-compose -f docker-compose-local.yml up -d` to start the containers

## Environment variables

- `SPRING_PROFILES_ACTIVE` : Spring profile [local, dev, test, prod]. Default is local.
- `DB_HOST` : Postgres host. Default is postgres
- `DB_PORT` : Postgres port. Default is 5432
- `DB_USERNAME` : Postgres user. Default is cardano-master
- `DB_PASSWORD` : Postgres password. Default is postgres
- `DB_NAME`: Database name
- `DB_SCHEMA`: Database schema
