# Iris Authentication


<p align="left">
<img alt="Tests" src="https://github.com/cardano-foundation/cf-explorer-authentication/actions/workflows/tests.yaml/badge.svg" />
<img alt="Release" src="https://github.com/cardano-foundation/cf-explorer-authentication/actions/workflows/release.yaml/badge.svg?branch=main" />
<img alt="Publish" src="https://github.com/cardano-foundation/cf-explorer-authentication/actions/workflows/publish.yaml/badge.svg?branch=main" />
</p>

The Iris Authentication API enables users to log in, with or without a wallet, to share personalized data such as bookmarks across devices.

👉 Check the [Iris repository](https://github.com/cardano-foundation/cf-explorer) to understand how the microservices work together

## 🧪 Test Reports

To ensure the stability and reliability of this project, unit and mutation tests have been implemented. By clicking on the links below, you can access the detailed test reports and review the outcomes of the tests performed.

📊 [Coverage Report](https://cardano-foundation.github.io/cf-explorer-authentication/coverage-report/)

📊 [Mutation Report](https://cardano-foundation.github.io/cf-explorer-authentication/mutation-report/)

## Getting Started

### Prerequisites

- Docker && Docker Compose

### Installing

- Clone this repository
- Copy `./.m2/settings.default.xml.tpl` to `./.m2/settings.xml`
- Fill `{PRIVATE_MVN_REGISTRY_URL}`, `{PRIVATE_MVN_REGISTRY_USER}` and `{PRIVATE_MVN_REGISTRY_PASS}` in `./.m2/settings.xml`
- Copy `.env.example`  to `.env`
- Fill the `.env` file with your values (explain below)
- Create if not exists external network `infrastructure-net-local` with `docker network create infrastructure-net-local`
- Run `docker-compose -f docker-compose-local.yml up -d` to start the containers

## Environment variables

- `SPRING_PROFILES_ACTIVE` : Spring profile [local, dev, test, prod], plus Redis Profiles. See Below. Default is local.
- `DB_HOST` : Postgres host. Default is postgres
- `DB_PORT` : Postgres port. Default is 5432
- `DB_USERNAME` : Postgres user. Default is cardano-master
- `DB_PASSWORD` : Postgres password. Default is postgres
- `DB_NAME`: Database name
- `DB_SCHEMA`: Database schema

### We have 3 options for redis cache:
- `redis standalone`
    - `REDIS_STANDALONE_HOST` : Redis hostname eg. `127.0.0.1`.
    - `REDIS_STANDALONE_PORT` : Redis ort, eg. `6379`.
    - `REDIS_STANDALONE_PASSWORD` : Redis password. Default bitnami.
    -
- `redis sentinel`
    - `REDIS_MASTER_NAME` : Redis master name. Default is mymaster.
    - `REDIS_SENTINEL_PASS` : Redis sentinel password. Default is redis_sentinel_pass.
    - `REDIS_SENTINEL_HOST` : Redis sentinel host. Default is  cardano.redis.sentinel.

- `redis-cluster`
    -  `NODE_ADDRESSES`: List of redis cluster nodes host and port.
    -  `REDIS_CLUSTER_PASSWORD`: Password of redis cluster.
