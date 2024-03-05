# Changelog

## [0.9.0](https://github.com/cardano-foundation/cf-explorer-authentication/compare/v0.9.0...v0.9.0) (2024-03-05)


### Features

* adapt code formatter for authen service ([56fa123](https://github.com/cardano-foundation/cf-explorer-authentication/commit/56fa1230554285b0b2f0ee8f56268fb241f5b079))
* add email + stake key for user ([1f58c60](https://github.com/cardano-foundation/cf-explorer-authentication/commit/1f58c607bac00cdd63851005f4932c52349fb247))
* add keycloak event listener ([ef1aaaf](https://github.com/cardano-foundation/cf-explorer-authentication/commit/ef1aaaf6f25bc978f470e29c99112caea4580da7))
* add pipeline and update common packages ([62b7f4b](https://github.com/cardano-foundation/cf-explorer-authentication/commit/62b7f4b02b7638b032ed0d1be2bd87bcbfcbbc30))
* add provider ([5870390](https://github.com/cardano-foundation/cf-explorer-authentication/commit/5870390ad8a4d389731e4ce7498ffcb3f224a6ce))
* adde SES Configuration To send emails ([3c594a9](https://github.com/cardano-foundation/cf-explorer-authentication/commit/3c594a987835414982067e9b18089ca325dc7f04))
* added cluster and standalone redis mode ([32da18b](https://github.com/cardano-foundation/cf-explorer-authentication/commit/32da18b5e7ab4c764e2d7e8c326d0103d9ec8e49))
* coding sign-in, sign-up using wallet and username,password ([9f8c504](https://github.com/cardano-foundation/cf-explorer-authentication/commit/9f8c504236198e23dd08ea2443c0612577536ecc))
* Create licence checker and add to CICD pipeline for Authentication ([860995b](https://github.com/cardano-foundation/cf-explorer-authentication/commit/860995bf5b737311a1fb39ca6065f59c98696217))
* custom multiple language when send email verify ([5ca9414](https://github.com/cardano-foundation/cf-explorer-authentication/commit/5ca94140dff55eb079210b76e1dee4bb37b36b21))
* edit constant language ([8b0210c](https://github.com/cardano-foundation/cf-explorer-authentication/commit/8b0210c8d4754da538adb891f7493bde4d43804e))
* handle event listener from keycloak when edit role ([83e93e1](https://github.com/cardano-foundation/cf-explorer-authentication/commit/83e93e1d22f696ca58fc046b67a0698acac4c29f))
* handle event listeners from keycloak when edit role ([6a74dc6](https://github.com/cardano-foundation/cf-explorer-authentication/commit/6a74dc60a54f582ff58de43620570cd240872e9a))
* handle logout when edit user role on keycloak ([ced99ff](https://github.com/cardano-foundation/cf-explorer-authentication/commit/ced99ff6282d5cd2cdb3a62ef5449cf0d93bb09d))
* handle read RSA key ([3ba0b41](https://github.com/cardano-foundation/cf-explorer-authentication/commit/3ba0b414b76250c0901bbb92bd66e18ad657f9c7))
* handle RSA key with keycloak and paging bookmark api ([9dc7ffc](https://github.com/cardano-foundation/cf-explorer-authentication/commit/9dc7ffc60dc645c6d7b715a5dd537499dd5a1230))
* handle token when edit user role on keycloak ([69e1c4f](https://github.com/cardano-foundation/cf-explorer-authentication/commit/69e1c4f2aeedfb4835584ec5ed55ae43a5a8b6b7))
* refactor code after remove username ([884cb06](https://github.com/cardano-foundation/cf-explorer-authentication/commit/884cb0620f9ab80ee9c3a79477762a209374f38e))
* refactor code, integrate keycloak into authentication ([da2d9d6](https://github.com/cardano-foundation/cf-explorer-authentication/commit/da2d9d6a7a355f9ec24ec7a02e63b6183aca86be))
* remove config rollback transaction ([d291811](https://github.com/cardano-foundation/cf-explorer-authentication/commit/d29181192362d995749ffec82da3b5efc2ca995a))
* remove username when sign up ([25310e4](https://github.com/cardano-foundation/cf-explorer-authentication/commit/25310e4bbb771fd5f5cb977199b12118a28fba37))
* set address for user info ([abed61f](https://github.com/cardano-foundation/cf-explorer-authentication/commit/abed61f98bf8a65c2e8413066e79e1dab79e531a))
* update licenses.txt ([4e6b9ec](https://github.com/cardano-foundation/cf-explorer-authentication/commit/4e6b9ec9c326550648692e0759d56c1af89514db))
* update licenses.txt ([6309a6e](https://github.com/cardano-foundation/cf-explorer-authentication/commit/6309a6ec1b79f38e19d58056b99298ee4c631a89))
* update new migration ([030ba89](https://github.com/cardano-foundation/cf-explorer-authentication/commit/030ba89dcf9c7d802b4deed41ca905daaf5f4d81))
* update redis ([df4fc1c](https://github.com/cardano-foundation/cf-explorer-authentication/commit/df4fc1c9403092c4b3ed48417ab5c44a63c526c0))
* update swagger ([d692ed6](https://github.com/cardano-foundation/cf-explorer-authentication/commit/d692ed668a90e90ff108a4b12b05b27279330274))
* update unit test for authentication ([0e27e13](https://github.com/cardano-foundation/cf-explorer-authentication/commit/0e27e13e17073eb22fdb186cb5a46066ac7ae2f9))
* upgrade java 17 and spring boot 3, re name package ([67e6eb9](https://github.com/cardano-foundation/cf-explorer-authentication/commit/67e6eb912a77f823f7803a0dcdb2fbcca08b5c20))


### Bug Fixes

* add api check expired verify email ([5d523d2](https://github.com/cardano-foundation/cf-explorer-authentication/commit/5d523d2b180fb5d7c625e7faf1d393fa9db7c124))
* add bookmark fail ([cefde95](https://github.com/cardano-foundation/cf-explorer-authentication/commit/cefde95c4cb276c8b81151fd238cb11abe0c7a38))
* add controller test for authentication ([4251da8](https://github.com/cardano-foundation/cf-explorer-authentication/commit/4251da81cfc950baef164fcdb8152ebacf6a607c))
* add first migration ([c2c7c9e](https://github.com/cardano-foundation/cf-explorer-authentication/commit/c2c7c9e7d7df045513712f908bd78294d4890dfe))
* add flyway internal ([4951547](https://github.com/cardano-foundation/cf-explorer-authentication/commit/4951547fd4bd58b65adfcd0f76f898025b3b6637))
* add logic check code ([a9e0406](https://github.com/cardano-foundation/cf-explorer-authentication/commit/a9e04068a2172fedee31ba913e141690c77db664))
* add migration for authentication service ([141fd2e](https://github.com/cardano-foundation/cf-explorer-authentication/commit/141fd2e214760e06fe89559c5705de682bab6dfd))
* add public_key ([47bee0c](https://github.com/cardano-foundation/cf-explorer-authentication/commit/47bee0c947f23d881e406d2e689d3d58488f43be))
* add SANCHONET network enum ([1bb77b6](https://github.com/cardano-foundation/cf-explorer-authentication/commit/1bb77b641862cebefd46ab89da9c2cf3ee4adcf3))
* AQA validation bugs ([e4e2c2d](https://github.com/cardano-foundation/cf-explorer-authentication/commit/e4e2c2d9d03782a048e412b5b614cf575b81e83b))
* attempting to force a release ([dbf4e60](https://github.com/cardano-foundation/cf-explorer-authentication/commit/dbf4e606d784459402a5746e986f5e7feab673cd))
* attempting to force a release ([d976c37](https://github.com/cardano-foundation/cf-explorer-authentication/commit/d976c3722ee0ec00124572d762849f6cd0a4adf5))
* bug sign-up and login using wallet ([b96bb3d](https://github.com/cardano-foundation/cf-explorer-authentication/commit/b96bb3d8631029492d23f2b1d5e54059dfa0c33f))
* bug testing ([c3fec3c](https://github.com/cardano-foundation/cf-explorer-authentication/commit/c3fec3c448abc884148b5261c2f4a5ac7f02c32f))
* change type note column in private_note table ([d238a5c](https://github.com/cardano-foundation/cf-explorer-authentication/commit/d238a5c5a4adeebb2592aa84f4cd8a0f8be5669f))
* check null when get value from redis ([2ae160c](https://github.com/cardano-foundation/cf-explorer-authentication/commit/2ae160ca1522d569caccca5b0ca5b16d17ad7e46))
* check test ([3c21025](https://github.com/cardano-foundation/cf-explorer-authentication/commit/3c21025a48b2e0bdf5739d9fa4131194b25f1046))
* comment code defines url by language ([77323f0](https://github.com/cardano-foundation/cf-explorer-authentication/commit/77323f0e27ab6844748b83ea144c105b642da0e6))
* config keycloak ([9c82446](https://github.com/cardano-foundation/cf-explorer-authentication/commit/9c8244603294ea922d16d97457addb554345611f))
* conflict with sota-testing ([50ec73b](https://github.com/cardano-foundation/cf-explorer-authentication/commit/50ec73be7b6a24b5c5229cb6306bf73ac149d66c))
* edit swagger after review ([4e3a011](https://github.com/cardano-foundation/cf-explorer-authentication/commit/4e3a0110ab434fd6ece18a8b4ba4873200d6901a))
* edit url verify ([1b26bca](https://github.com/cardano-foundation/cf-explorer-authentication/commit/1b26bcaa39d3443e8d912ea715438e1d31eb7228))
* fix conflict and update unit test ([1e1bdbc](https://github.com/cardano-foundation/cf-explorer-authentication/commit/1e1bdbcf977cff3edc6cc978825b711e7661270d))
* fix conflict with sota-testing ([e33acc4](https://github.com/cardano-foundation/cf-explorer-authentication/commit/e33acc49ecf681703c936f6af1e0f5fb6a2214a9))
* fixing redis conf for aws profile ([f57a7ee](https://github.com/cardano-foundation/cf-explorer-authentication/commit/f57a7eee8d8b0cdad38a69b0d2940af3e5058b76))
* fixing redis conf for aws profile ([96a9b4e](https://github.com/cardano-foundation/cf-explorer-authentication/commit/96a9b4e4d9d31f74a06a9c661184060c04559b51))
* fixing redis conf for aws profile ([2d5a5e7](https://github.com/cardano-foundation/cf-explorer-authentication/commit/2d5a5e7739f77bc60d349098d4ae59c1c4fb9d1c))
* **gha:** fix condition for main branch workflow trigger ([2972f6d](https://github.com/cardano-foundation/cf-explorer-authentication/commit/2972f6db8cb77a5e260bfb2e058748543c937f47))
* **gha:** fix condition for main branch workflow trigger ([e5f0f12](https://github.com/cardano-foundation/cf-explorer-authentication/commit/e5f0f12a929e1734b93b3884d8823ce02062d810))
* **gha:** fixed PR builds ([f747e54](https://github.com/cardano-foundation/cf-explorer-authentication/commit/f747e544c61349878549697baef0c2c2ae25a3b0))
* handle bug relate upgrade version ([a3ae556](https://github.com/cardano-foundation/cf-explorer-authentication/commit/a3ae5568ae62163b7ac48780b8768b53953d0026))
* Handle log out when edit user role in keycloak ([2d06e64](https://github.com/cardano-foundation/cf-explorer-authentication/commit/2d06e647c40f4500357b5d4514ad829ff0023ce2))
* insert role ([85828bb](https://github.com/cardano-foundation/cf-explorer-authentication/commit/85828bb1c8dba47995a73154d82d8ec0c9d48291))
* redis key invalid ([d423cd1](https://github.com/cardano-foundation/cf-explorer-authentication/commit/d423cd141135d83084330eee3baa7859c2a71b4e))
* redis key invalid ([fecdaae](https://github.com/cardano-foundation/cf-explorer-authentication/commit/fecdaae07344e4d39468f6d5a86226d8976f043d))
* remove commment [@author](https://github.com/author) ([2e4feae](https://github.com/cardano-foundation/cf-explorer-authentication/commit/2e4feae633025a95edca7a25d4f9dcd731416ba8))
* remove LocaleUtils ([f60e60f](https://github.com/cardano-foundation/cf-explorer-authentication/commit/f60e60f6bf6bab0bb73e9a083ca756b6f1a6d1c0))
* removed references to Iris ([e86a375](https://github.com/cardano-foundation/cf-explorer-authentication/commit/e86a3756f921bafe6e2a68c6144e92879f018e14))
* repair tests upgrade junit ([af1602c](https://github.com/cardano-foundation/cf-explorer-authentication/commit/af1602ce6e2c80fe54ce4cdd83fc13dffbbb77fc))
* run unit test fail ([94bab10](https://github.com/cardano-foundation/cf-explorer-authentication/commit/94bab1001152eefb6348967e91e0bcad342bc091))
* set profile for repository test ([78c0da4](https://github.com/cardano-foundation/cf-explorer-authentication/commit/78c0da433b1763cd2403c2b6b9790d21edff090c))
* testing ([30c10cd](https://github.com/cardano-foundation/cf-explorer-authentication/commit/30c10cd681fe19f1d1f4fd8567d816d5aedb107a))
* trying to fix redis conf for aws profile ([8889d8e](https://github.com/cardano-foundation/cf-explorer-authentication/commit/8889d8ec3df138c14fae9b4f0e5117049ee878d7))
* trying to fix redis conf for aws profile ([a1572ae](https://github.com/cardano-foundation/cf-explorer-authentication/commit/a1572ae056881451f6b986c02f1d627e7217fbc5))
* trying to fix redis conf for aws profile ([509e4d9](https://github.com/cardano-foundation/cf-explorer-authentication/commit/509e4d97a016ae0ba2b790bd4266a4b2697f62f7))
* update public_key ([947b893](https://github.com/cardano-foundation/cf-explorer-authentication/commit/947b893ff6b9300335304cda0f6a9eb7c9e19fc9))
* update read me for authentication ([#103](https://github.com/cardano-foundation/cf-explorer-authentication/issues/103)) ([5619f45](https://github.com/cardano-foundation/cf-explorer-authentication/commit/5619f45cb006e69b2a7247b13d4821114fa07b42))
* Update README.md trigger release ([c0f092e](https://github.com/cardano-foundation/cf-explorer-authentication/commit/c0f092e48d3218e7cc36dfbfdd17e7612003156b))
* update redis config ([bee07a2](https://github.com/cardano-foundation/cf-explorer-authentication/commit/bee07a2561e16b1090d0b84a4192dad6454b0fab))
* update redis config ([fa41614](https://github.com/cardano-foundation/cf-explorer-authentication/commit/fa416149359bd8635fd650e339ca0995541f8cd8))
* update unit test ([3e4331b](https://github.com/cardano-foundation/cf-explorer-authentication/commit/3e4331bb93791f86f6486330d745bb02cac14672))
* update unit test ([73bd81c](https://github.com/cardano-foundation/cf-explorer-authentication/commit/73bd81c421054637fe5bb6313acd254910903d4f))
* update version common api ([e2a832e](https://github.com/cardano-foundation/cf-explorer-authentication/commit/e2a832e2e1f8d9269ad438ad72a6fc590a157e24))
* **version:** set correct snapshot version ([4d51ebe](https://github.com/cardano-foundation/cf-explorer-authentication/commit/4d51ebe6ada1b52cb28f1e02baeae3f10c495bd2))


### Miscellaneous Chores

* release 0.1.6 ([6571a80](https://github.com/cardano-foundation/cf-explorer-authentication/commit/6571a80db3efbda326b27ff983d21bb3acf3e9c1))

## [0.9.0](https://github.com/cardano-foundation/cf-explorer-authentication/compare/v0.1.15...v0.9.0) (2024-03-05)


### Features

* adapt code formatter for authen service ([56fa123](https://github.com/cardano-foundation/cf-explorer-authentication/commit/56fa1230554285b0b2f0ee8f56268fb241f5b079))

## [0.1.15](https://github.com/cardano-foundation/cf-explorer-authentication/compare/v0.1.14...v0.1.15) (2023-11-23)


### Bug Fixes

* comment code defines url by language ([77323f0](https://github.com/cardano-foundation/cf-explorer-authentication/commit/77323f0e27ab6844748b83ea144c105b642da0e6))

## [0.1.14](https://github.com/cardano-foundation/cf-explorer-authentication/compare/v0.1.13...v0.1.14) (2023-10-30)


### Features

* add keycloak event listener ([ef1aaaf](https://github.com/cardano-foundation/cf-explorer-authentication/commit/ef1aaaf6f25bc978f470e29c99112caea4580da7))
* add provider ([5870390](https://github.com/cardano-foundation/cf-explorer-authentication/commit/5870390ad8a4d389731e4ce7498ffcb3f224a6ce))
* Create licence checker and add to CICD pipeline for Authentication ([860995b](https://github.com/cardano-foundation/cf-explorer-authentication/commit/860995bf5b737311a1fb39ca6065f59c98696217))
* custom multiple language when send email verify ([5ca9414](https://github.com/cardano-foundation/cf-explorer-authentication/commit/5ca94140dff55eb079210b76e1dee4bb37b36b21))
* edit constant language ([8b0210c](https://github.com/cardano-foundation/cf-explorer-authentication/commit/8b0210c8d4754da538adb891f7493bde4d43804e))
* handle event listener from keycloak when edit role ([83e93e1](https://github.com/cardano-foundation/cf-explorer-authentication/commit/83e93e1d22f696ca58fc046b67a0698acac4c29f))
* handle event listeners from keycloak when edit role ([6a74dc6](https://github.com/cardano-foundation/cf-explorer-authentication/commit/6a74dc60a54f582ff58de43620570cd240872e9a))
* handle logout when edit user role on keycloak ([ced99ff](https://github.com/cardano-foundation/cf-explorer-authentication/commit/ced99ff6282d5cd2cdb3a62ef5449cf0d93bb09d))
* handle read RSA key ([3ba0b41](https://github.com/cardano-foundation/cf-explorer-authentication/commit/3ba0b414b76250c0901bbb92bd66e18ad657f9c7))
* handle RSA key with keycloak and paging bookmark api ([9dc7ffc](https://github.com/cardano-foundation/cf-explorer-authentication/commit/9dc7ffc60dc645c6d7b715a5dd537499dd5a1230))
* handle token when edit user role on keycloak ([69e1c4f](https://github.com/cardano-foundation/cf-explorer-authentication/commit/69e1c4f2aeedfb4835584ec5ed55ae43a5a8b6b7))
* refactor code, integrate keycloak into authentication ([da2d9d6](https://github.com/cardano-foundation/cf-explorer-authentication/commit/da2d9d6a7a355f9ec24ec7a02e63b6183aca86be))
* remove config rollback transaction ([d291811](https://github.com/cardano-foundation/cf-explorer-authentication/commit/d29181192362d995749ffec82da3b5efc2ca995a))
* update licenses.txt ([4e6b9ec](https://github.com/cardano-foundation/cf-explorer-authentication/commit/4e6b9ec9c326550648692e0759d56c1af89514db))
* update licenses.txt ([6309a6e](https://github.com/cardano-foundation/cf-explorer-authentication/commit/6309a6ec1b79f38e19d58056b99298ee4c631a89))
* update redis ([df4fc1c](https://github.com/cardano-foundation/cf-explorer-authentication/commit/df4fc1c9403092c4b3ed48417ab5c44a63c526c0))


### Bug Fixes

* add public_key ([47bee0c](https://github.com/cardano-foundation/cf-explorer-authentication/commit/47bee0c947f23d881e406d2e689d3d58488f43be))
* bug testing ([c3fec3c](https://github.com/cardano-foundation/cf-explorer-authentication/commit/c3fec3c448abc884148b5261c2f4a5ac7f02c32f))
* check null when get value from redis ([2ae160c](https://github.com/cardano-foundation/cf-explorer-authentication/commit/2ae160ca1522d569caccca5b0ca5b16d17ad7e46))
* check test ([3c21025](https://github.com/cardano-foundation/cf-explorer-authentication/commit/3c21025a48b2e0bdf5739d9fa4131194b25f1046))
* config keycloak ([9c82446](https://github.com/cardano-foundation/cf-explorer-authentication/commit/9c8244603294ea922d16d97457addb554345611f))
* conflict with sota-testing ([50ec73b](https://github.com/cardano-foundation/cf-explorer-authentication/commit/50ec73be7b6a24b5c5229cb6306bf73ac149d66c))
* fix conflict with sota-testing ([e33acc4](https://github.com/cardano-foundation/cf-explorer-authentication/commit/e33acc49ecf681703c936f6af1e0f5fb6a2214a9))
* Handle log out when edit user role in keycloak ([2d06e64](https://github.com/cardano-foundation/cf-explorer-authentication/commit/2d06e647c40f4500357b5d4514ad829ff0023ce2))
* redis key invalid ([d423cd1](https://github.com/cardano-foundation/cf-explorer-authentication/commit/d423cd141135d83084330eee3baa7859c2a71b4e))
* redis key invalid ([fecdaae](https://github.com/cardano-foundation/cf-explorer-authentication/commit/fecdaae07344e4d39468f6d5a86226d8976f043d))
* remove LocaleUtils ([f60e60f](https://github.com/cardano-foundation/cf-explorer-authentication/commit/f60e60f6bf6bab0bb73e9a083ca756b6f1a6d1c0))
* testing ([30c10cd](https://github.com/cardano-foundation/cf-explorer-authentication/commit/30c10cd681fe19f1d1f4fd8567d816d5aedb107a))
* update public_key ([947b893](https://github.com/cardano-foundation/cf-explorer-authentication/commit/947b893ff6b9300335304cda0f6a9eb7c9e19fc9))
* update read me for authentication ([#103](https://github.com/cardano-foundation/cf-explorer-authentication/issues/103)) ([5619f45](https://github.com/cardano-foundation/cf-explorer-authentication/commit/5619f45cb006e69b2a7247b13d4821114fa07b42))
* update redis config ([bee07a2](https://github.com/cardano-foundation/cf-explorer-authentication/commit/bee07a2561e16b1090d0b84a4192dad6454b0fab))
* update redis config ([fa41614](https://github.com/cardano-foundation/cf-explorer-authentication/commit/fa416149359bd8635fd650e339ca0995541f8cd8))
* update unit test ([3e4331b](https://github.com/cardano-foundation/cf-explorer-authentication/commit/3e4331bb93791f86f6486330d745bb02cac14672))
* update version common api ([e2a832e](https://github.com/cardano-foundation/cf-explorer-authentication/commit/e2a832e2e1f8d9269ad438ad72a6fc590a157e24))

## [0.1.13](https://github.com/cardano-foundation/cf-explorer-authentication/compare/v0.1.12...v0.1.13) (2023-08-11)


### Bug Fixes

* removed references to Iris ([e86a375](https://github.com/cardano-foundation/cf-explorer-authentication/commit/e86a3756f921bafe6e2a68c6144e92879f018e14))

## [0.1.12](https://github.com/cardano-foundation/cf-explorer-authentication/compare/v0.1.11...v0.1.12) (2023-08-04)


### Bug Fixes

* attempting to force a release ([dbf4e60](https://github.com/cardano-foundation/cf-explorer-authentication/commit/dbf4e606d784459402a5746e986f5e7feab673cd))

## [0.1.11](https://github.com/cardano-foundation/cf-explorer-authentication/compare/v0.1.10...v0.1.11) (2023-08-02)


### Bug Fixes

* attempting to force a release ([d976c37](https://github.com/cardano-foundation/cf-explorer-authentication/commit/d976c3722ee0ec00124572d762849f6cd0a4adf5))

## [0.1.10](https://github.com/cardano-foundation/cf-explorer-authentication/compare/v0.1.9...v0.1.10) (2023-07-27)


### Features

* adde SES Configuration To send emails ([3c594a9](https://github.com/cardano-foundation/cf-explorer-authentication/commit/3c594a987835414982067e9b18089ca325dc7f04))

## [0.1.9](https://github.com/cardano-foundation/cf-explorer-authentication/compare/v0.1.8...v0.1.9) (2023-07-25)


### Features

* update unit test for authentication ([0e27e13](https://github.com/cardano-foundation/cf-explorer-authentication/commit/0e27e13e17073eb22fdb186cb5a46066ac7ae2f9))


### Bug Fixes

* remove commment [@author](https://github.com/author) ([2e4feae](https://github.com/cardano-foundation/cf-explorer-authentication/commit/2e4feae633025a95edca7a25d4f9dcd731416ba8))

## [0.1.8](https://github.com/cardano-foundation/cf-explorer-authentication/compare/v0.1.7...v0.1.8) (2023-07-21)


### Bug Fixes

* add api check expired verify email ([5d523d2](https://github.com/cardano-foundation/cf-explorer-authentication/commit/5d523d2b180fb5d7c625e7faf1d393fa9db7c124))
* add logic check code ([a9e0406](https://github.com/cardano-foundation/cf-explorer-authentication/commit/a9e04068a2172fedee31ba913e141690c77db664))

## [0.1.7](https://github.com/cardano-foundation/cf-explorer-authentication/compare/v0.1.6...v0.1.7) (2023-07-19)


### Bug Fixes

* add controller test for authentication ([4251da8](https://github.com/cardano-foundation/cf-explorer-authentication/commit/4251da81cfc950baef164fcdb8152ebacf6a607c))
* fix conflict and update unit test ([1e1bdbc](https://github.com/cardano-foundation/cf-explorer-authentication/commit/1e1bdbcf977cff3edc6cc978825b711e7661270d))
* run unit test fail ([94bab10](https://github.com/cardano-foundation/cf-explorer-authentication/commit/94bab1001152eefb6348967e91e0bcad342bc091))
* set profile for repository test ([78c0da4](https://github.com/cardano-foundation/cf-explorer-authentication/commit/78c0da433b1763cd2403c2b6b9790d21edff090c))

## [0.1.6](https://github.com/cardano-foundation/cf-explorer-authentication/compare/v0.1.5...v0.1.6) (2023-07-11)


### Miscellaneous Chores

* release 0.1.6 ([6571a80](https://github.com/cardano-foundation/cf-explorer-authentication/commit/6571a80db3efbda326b27ff983d21bb3acf3e9c1))

## [0.1.5](https://github.com/cardano-foundation/cf-explorer-authentication/compare/v0.1.4...v0.1.5) (2023-07-05)


### Bug Fixes

* repair tests upgrade junit ([af1602c](https://github.com/cardano-foundation/cf-explorer-authentication/commit/af1602ce6e2c80fe54ce4cdd83fc13dffbbb77fc))

## [0.1.4](https://github.com/cardano-foundation/cf-explorer-authentication/compare/v0.1.3...v0.1.4) (2023-06-29)


### Features

* update new migration ([030ba89](https://github.com/cardano-foundation/cf-explorer-authentication/commit/030ba89dcf9c7d802b4deed41ca905daaf5f4d81))


### Bug Fixes

* add migration for authentication service ([141fd2e](https://github.com/cardano-foundation/cf-explorer-authentication/commit/141fd2e214760e06fe89559c5705de682bab6dfd))
* bug sign-up and login using wallet ([b96bb3d](https://github.com/cardano-foundation/cf-explorer-authentication/commit/b96bb3d8631029492d23f2b1d5e54059dfa0c33f))
* **gha:** fix condition for main branch workflow trigger ([2972f6d](https://github.com/cardano-foundation/cf-explorer-authentication/commit/2972f6db8cb77a5e260bfb2e058748543c937f47))
* **gha:** fix condition for main branch workflow trigger ([e5f0f12](https://github.com/cardano-foundation/cf-explorer-authentication/commit/e5f0f12a929e1734b93b3884d8823ce02062d810))
* **gha:** fixed PR builds ([f747e54](https://github.com/cardano-foundation/cf-explorer-authentication/commit/f747e544c61349878549697baef0c2c2ae25a3b0))
* insert role ([85828bb](https://github.com/cardano-foundation/cf-explorer-authentication/commit/85828bb1c8dba47995a73154d82d8ec0c9d48291))
* **version:** set correct snapshot version ([4d51ebe](https://github.com/cardano-foundation/cf-explorer-authentication/commit/4d51ebe6ada1b52cb28f1e02baeae3f10c495bd2))

## [0.1.3](https://github.com/cardano-foundation/cf-explorer-authentication/compare/v0.1.2...v0.1.3) (2023-05-31)


### Features

* added cluster and standalone redis mode ([32da18b](https://github.com/cardano-foundation/cf-explorer-authentication/commit/32da18b5e7ab4c764e2d7e8c326d0103d9ec8e49))

## [0.1.2](https://github.com/cardano-foundation/cf-explorer-authentication/compare/v0.1.1...v0.1.2) (2023-05-31)


### Features

* add email + stake key for user ([1f58c60](https://github.com/cardano-foundation/cf-explorer-authentication/commit/1f58c607bac00cdd63851005f4932c52349fb247))
* refactor code after remove username ([884cb06](https://github.com/cardano-foundation/cf-explorer-authentication/commit/884cb0620f9ab80ee9c3a79477762a209374f38e))
* remove username when sign up ([25310e4](https://github.com/cardano-foundation/cf-explorer-authentication/commit/25310e4bbb771fd5f5cb977199b12118a28fba37))
* set address for user info ([abed61f](https://github.com/cardano-foundation/cf-explorer-authentication/commit/abed61f98bf8a65c2e8413066e79e1dab79e531a))


### Bug Fixes

* handle bug relate upgrade version ([a3ae556](https://github.com/cardano-foundation/cf-explorer-authentication/commit/a3ae5568ae62163b7ac48780b8768b53953d0026))

## [0.1.1](https://github.com/cardano-foundation/cf-explorer-authentication/compare/v0.1.0...v0.1.1) (2023-05-15)


### Bug Fixes

* Update README.md trigger release ([c0f092e](https://github.com/cardano-foundation/cf-explorer-authentication/commit/c0f092e48d3218e7cc36dfbfdd17e7612003156b))

## 0.1.0 (2023-05-15)


### Features

* add pipeline and update common packages ([62b7f4b](https://github.com/cardano-foundation/cf-explorer-authentication/commit/62b7f4b02b7638b032ed0d1be2bd87bcbfcbbc30))
* coding sign-in, sign-up using wallet and username,password ([9f8c504](https://github.com/cardano-foundation/cf-explorer-authentication/commit/9f8c504236198e23dd08ea2443c0612577536ecc))
* upgrade java 17 and spring boot 3, re name package ([67e6eb9](https://github.com/cardano-foundation/cf-explorer-authentication/commit/67e6eb912a77f823f7803a0dcdb2fbcca08b5c20))
