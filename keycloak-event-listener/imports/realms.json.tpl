[ {
  "id" : "9387fb08-1ac5-4ccb-bff3-4f83fb08462c",
  "realm" : "cardano_explorer",
  "notBefore" : 0,
  "defaultSignatureAlgorithm" : "RS256",
  "revokeRefreshToken" : true,
  "refreshTokenMaxReuse" : 1,
  "accessTokenLifespan" : 21600,
  "accessTokenLifespanForImplicitFlow" : 900,
  "ssoSessionIdleTimeout" : 43200,
  "ssoSessionMaxLifespan" : 86400,
  "ssoSessionIdleTimeoutRememberMe" : 0,
  "ssoSessionMaxLifespanRememberMe" : 0,
  "offlineSessionIdleTimeout" : 2592000,
  "offlineSessionMaxLifespanEnabled" : false,
  "offlineSessionMaxLifespan" : 5184000,
  "clientSessionIdleTimeout" : 0,
  "clientSessionMaxLifespan" : 0,
  "clientOfflineSessionIdleTimeout" : 0,
  "clientOfflineSessionMaxLifespan" : 0,
  "accessCodeLifespan" : 60,
  "accessCodeLifespanUserAction" : 300,
  "accessCodeLifespanLogin" : 1800,
  "actionTokenGeneratedByAdminLifespan" : 43200,
  "actionTokenGeneratedByUserLifespan" : 300,
  "oauth2DeviceCodeLifespan" : 600,
  "oauth2DevicePollingInterval" : 5,
  "enabled" : true,
  "sslRequired" : "external",
  "registrationAllowed" : false,
  "registrationEmailAsUsername" : false,
  "rememberMe" : false,
  "verifyEmail" : false,
  "loginWithEmailAllowed" : true,
  "duplicateEmailsAllowed" : false,
  "resetPasswordAllowed" : false,
  "editUsernameAllowed" : false,
  "bruteForceProtected" : false,
  "permanentLockout" : false,
  "maxFailureWaitSeconds" : 900,
  "minimumQuickLoginWaitSeconds" : 60,
  "waitIncrementSeconds" : 60,
  "quickLoginCheckMilliSeconds" : 1000,
  "maxDeltaTimeSeconds" : 43200,
  "failureFactor" : 30,
  "roles" : {
    "realm" : [ {
      "id" : "8c128d86-b25e-461e-9ca8-da4d4b30c154",
      "name" : "ROLE_PUBLIC",
      "description" : "",
      "composite" : false,
      "clientRole" : false,
      "containerId" : "9387fb08-1ac5-4ccb-bff3-4f83fb08462c",
      "attributes" : { }
    }, {
      "id" : "8db29128-2010-4a7a-8a6d-3d43728b0f95",
      "name" : "default-roles-cardano_explorer",
      "description" : "${role_default-roles}",
      "composite" : true,
      "composites" : {
        "realm" : [ "offline_access", "uma_authorization", "ROLE_PUBLIC" ],
        "client" : {
          "account" : [ "view-profile", "manage-account" ]
        }
      },
      "clientRole" : false,
      "containerId" : "9387fb08-1ac5-4ccb-bff3-4f83fb08462c",
      "attributes" : { }
    }, {
      "id" : "443137f9-a5f8-4f88-84fe-558b0b3f69f8",
      "name" : "uma_authorization",
      "description" : "${role_uma_authorization}",
      "composite" : false,
      "clientRole" : false,
      "containerId" : "9387fb08-1ac5-4ccb-bff3-4f83fb08462c",
      "attributes" : { }
    }, {
      "id" : "5a7a9727-0cfc-4d0b-9d04-b76ed5607eb3",
      "name" : "offline_access",
      "description" : "${role_offline-access}",
      "composite" : false,
      "clientRole" : false,
      "containerId" : "9387fb08-1ac5-4ccb-bff3-4f83fb08462c",
      "attributes" : { }
    } ],
    "client" : {
      "cardano_authentication_client" : [ {
        "id" : "22c437f6-e89f-4a64-ba65-e6cc6b1ab382",
        "name" : "uma_protection",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "e8a73897-2723-44f0-81b5-37262f5d55ef",
        "attributes" : { }
      } ],
      "realm-management" : [ {
        "id" : "d1dd3dc3-8038-4488-9bcd-922e23beaf68",
        "name" : "manage-events",
        "description" : "${role_manage-events}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "5808945e-6cf0-4df1-9e81-62bb1fe784c5",
        "attributes" : { }
      }, {
        "id" : "104d61f1-3d23-4485-a70b-8580c7da42a9",
        "name" : "manage-clients",
        "description" : "${role_manage-clients}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "5808945e-6cf0-4df1-9e81-62bb1fe784c5",
        "attributes" : { }
      }, {
        "id" : "577c3e21-83c0-4663-aa7e-4b6eb641b9ce",
        "name" : "manage-identity-providers",
        "description" : "${role_manage-identity-providers}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "5808945e-6cf0-4df1-9e81-62bb1fe784c5",
        "attributes" : { }
      }, {
        "id" : "8eb421f6-6dc6-40e6-aa17-8cead8002051",
        "name" : "query-realms",
        "description" : "${role_query-realms}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "5808945e-6cf0-4df1-9e81-62bb1fe784c5",
        "attributes" : { }
      }, {
        "id" : "4ae6c743-d300-48f7-9d7f-b9faac6477d1",
        "name" : "view-authorization",
        "description" : "${role_view-authorization}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "5808945e-6cf0-4df1-9e81-62bb1fe784c5",
        "attributes" : { }
      }, {
        "id" : "ae500c2a-3eea-44f5-9fad-7876ed6beccd",
        "name" : "view-events",
        "description" : "${role_view-events}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "5808945e-6cf0-4df1-9e81-62bb1fe784c5",
        "attributes" : { }
      }, {
        "id" : "55c2fb59-57de-4262-8093-238a3dc7388a",
        "name" : "realm-admin",
        "description" : "${role_realm-admin}",
        "composite" : true,
        "composites" : {
          "client" : {
            "realm-management" : [ "manage-events", "manage-clients", "view-events", "view-authorization", "manage-identity-providers", "query-realms", "view-clients", "view-identity-providers", "manage-users", "manage-authorization", "view-users", "impersonation", "create-client", "manage-realm", "query-clients", "query-users", "query-groups", "view-realm" ]
          }
        },
        "clientRole" : true,
        "containerId" : "5808945e-6cf0-4df1-9e81-62bb1fe784c5",
        "attributes" : { }
      }, {
        "id" : "48df25d0-0ebc-4c9f-9e42-f9054c1bb18f",
        "name" : "view-clients",
        "description" : "${role_view-clients}",
        "composite" : true,
        "composites" : {
          "client" : {
            "realm-management" : [ "query-clients" ]
          }
        },
        "clientRole" : true,
        "containerId" : "5808945e-6cf0-4df1-9e81-62bb1fe784c5",
        "attributes" : { }
      }, {
        "id" : "02b98d5d-143f-431b-a03b-c3266dfe510b",
        "name" : "view-identity-providers",
        "description" : "${role_view-identity-providers}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "5808945e-6cf0-4df1-9e81-62bb1fe784c5",
        "attributes" : { }
      }, {
        "id" : "727b19de-c663-48c1-9655-332245b9517c",
        "name" : "manage-users",
        "description" : "${role_manage-users}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "5808945e-6cf0-4df1-9e81-62bb1fe784c5",
        "attributes" : { }
      }, {
        "id" : "db6041a6-3325-479e-83f8-0f3a92945dd5",
        "name" : "manage-authorization",
        "description" : "${role_manage-authorization}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "5808945e-6cf0-4df1-9e81-62bb1fe784c5",
        "attributes" : { }
      }, {
        "id" : "48d4310a-f66d-44e2-9376-4ed4ffc1fc35",
        "name" : "impersonation",
        "description" : "${role_impersonation}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "5808945e-6cf0-4df1-9e81-62bb1fe784c5",
        "attributes" : { }
      }, {
        "id" : "6bb4edc3-9026-409c-b65a-4e9778231007",
        "name" : "view-users",
        "description" : "${role_view-users}",
        "composite" : true,
        "composites" : {
          "client" : {
            "realm-management" : [ "query-users", "query-groups" ]
          }
        },
        "clientRole" : true,
        "containerId" : "5808945e-6cf0-4df1-9e81-62bb1fe784c5",
        "attributes" : { }
      }, {
        "id" : "dc84e265-da45-4576-8582-23eaaa9909d0",
        "name" : "create-client",
        "description" : "${role_create-client}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "5808945e-6cf0-4df1-9e81-62bb1fe784c5",
        "attributes" : { }
      }, {
        "id" : "594a1ef9-fb38-4067-b721-bfcbf4b7e13b",
        "name" : "manage-realm",
        "description" : "${role_manage-realm}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "5808945e-6cf0-4df1-9e81-62bb1fe784c5",
        "attributes" : { }
      }, {
        "id" : "43813014-98ff-463f-b424-7a5334891141",
        "name" : "query-clients",
        "description" : "${role_query-clients}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "5808945e-6cf0-4df1-9e81-62bb1fe784c5",
        "attributes" : { }
      }, {
        "id" : "120ae01e-5626-463c-990e-2f307043caca",
        "name" : "query-users",
        "description" : "${role_query-users}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "5808945e-6cf0-4df1-9e81-62bb1fe784c5",
        "attributes" : { }
      }, {
        "id" : "4e7a5c8d-81a3-4827-afb9-cdfe4c8c3daf",
        "name" : "query-groups",
        "description" : "${role_query-groups}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "5808945e-6cf0-4df1-9e81-62bb1fe784c5",
        "attributes" : { }
      }, {
        "id" : "918bc6f7-6ca2-47b1-9172-de742def9f7f",
        "name" : "view-realm",
        "description" : "${role_view-realm}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "5808945e-6cf0-4df1-9e81-62bb1fe784c5",
        "attributes" : { }
      } ],
      "security-admin-console" : [ ],
      "admin-cli" : [ ],
      "account-console" : [ ],
      "broker" : [ {
        "id" : "38e87095-88ac-486c-818c-72bea3891a88",
        "name" : "read-token",
        "description" : "${role_read-token}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "548044c6-4a13-41cc-808e-eeb4769500c7",
        "attributes" : { }
      } ],
      "account" : [ {
        "id" : "2a431d01-0c64-413a-9db8-b0890e350d35",
        "name" : "manage-account",
        "description" : "${role_manage-account}",
        "composite" : true,
        "composites" : {
          "client" : {
            "account" : [ "manage-account-links" ]
          }
        },
        "clientRole" : true,
        "containerId" : "85e22a65-3dbd-4c39-9e31-988d99323f04",
        "attributes" : { }
      }, {
        "id" : "a0eda2f4-131c-4af5-bdb4-ebe44a275096",
        "name" : "view-profile",
        "description" : "${role_view-profile}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "85e22a65-3dbd-4c39-9e31-988d99323f04",
        "attributes" : { }
      }, {
        "id" : "b7f02336-919a-4ea2-bf59-e1fa3044ecdc",
        "name" : "manage-consent",
        "description" : "${role_manage-consent}",
        "composite" : true,
        "composites" : {
          "client" : {
            "account" : [ "view-consent" ]
          }
        },
        "clientRole" : true,
        "containerId" : "85e22a65-3dbd-4c39-9e31-988d99323f04",
        "attributes" : { }
      }, {
        "id" : "c08adc2b-40b9-416b-9ba4-d53169641ad9",
        "name" : "view-applications",
        "description" : "${role_view-applications}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "85e22a65-3dbd-4c39-9e31-988d99323f04",
        "attributes" : { }
      }, {
        "id" : "75cd82f3-7b82-4af3-9f54-5dda92650301",
        "name" : "view-groups",
        "description" : "${role_view-groups}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "85e22a65-3dbd-4c39-9e31-988d99323f04",
        "attributes" : { }
      }, {
        "id" : "b9b422ae-a1db-4f8b-95d1-06b7dff89fd6",
        "name" : "delete-account",
        "description" : "${role_delete-account}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "85e22a65-3dbd-4c39-9e31-988d99323f04",
        "attributes" : { }
      }, {
        "id" : "1353f098-48f3-4858-b766-a27705d16d25",
        "name" : "manage-account-links",
        "description" : "${role_manage-account-links}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "85e22a65-3dbd-4c39-9e31-988d99323f04",
        "attributes" : { }
      }, {
        "id" : "b107be92-eac5-4522-8d10-71f6f53cf79b",
        "name" : "view-consent",
        "description" : "${role_view-consent}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "85e22a65-3dbd-4c39-9e31-988d99323f04",
        "attributes" : { }
      } ]
    }
  },
  "groups" : [ ],
  "defaultRole" : {
    "id" : "8db29128-2010-4a7a-8a6d-3d43728b0f95",
    "name" : "default-roles-cardano_explorer",
    "description" : "${role_default-roles}",
    "composite" : true,
    "clientRole" : false,
    "containerId" : "9387fb08-1ac5-4ccb-bff3-4f83fb08462c"
  },
  "requiredCredentials" : [ "password" ],
  "otpPolicyType" : "totp",
  "otpPolicyAlgorithm" : "HmacSHA1",
  "otpPolicyInitialCounter" : 0,
  "otpPolicyDigits" : 6,
  "otpPolicyLookAheadWindow" : 1,
  "otpPolicyPeriod" : 30,
  "otpPolicyCodeReusable" : false,
  "otpSupportedApplications" : [ "totpAppMicrosoftAuthenticatorName", "totpAppFreeOTPName", "totpAppGoogleName" ],
  "webAuthnPolicyRpEntityName" : "keycloak",
  "webAuthnPolicySignatureAlgorithms" : [ "ES256" ],
  "webAuthnPolicyRpId" : "",
  "webAuthnPolicyAttestationConveyancePreference" : "not specified",
  "webAuthnPolicyAuthenticatorAttachment" : "not specified",
  "webAuthnPolicyRequireResidentKey" : "not specified",
  "webAuthnPolicyUserVerificationRequirement" : "not specified",
  "webAuthnPolicyCreateTimeout" : 0,
  "webAuthnPolicyAvoidSameAuthenticatorRegister" : false,
  "webAuthnPolicyAcceptableAaguids" : [ ],
  "webAuthnPolicyPasswordlessRpEntityName" : "keycloak",
  "webAuthnPolicyPasswordlessSignatureAlgorithms" : [ "ES256" ],
  "webAuthnPolicyPasswordlessRpId" : "",
  "webAuthnPolicyPasswordlessAttestationConveyancePreference" : "not specified",
  "webAuthnPolicyPasswordlessAuthenticatorAttachment" : "not specified",
  "webAuthnPolicyPasswordlessRequireResidentKey" : "not specified",
  "webAuthnPolicyPasswordlessUserVerificationRequirement" : "not specified",
  "webAuthnPolicyPasswordlessCreateTimeout" : 0,
  "webAuthnPolicyPasswordlessAvoidSameAuthenticatorRegister" : false,
  "webAuthnPolicyPasswordlessAcceptableAaguids" : [ ],
  "users" : [ {
    "id" : "a2a632ed-1b73-4370-8406-3c3a069107c3",
    "createdTimestamp" : 1699364741097,
    "username" : "service-account-cardano_authentication_client",
    "enabled" : true,
    "totp" : false,
    "emailVerified" : false,
    "serviceAccountClientId" : "cardano_authentication_client",
    "credentials" : [ ],
    "disableableCredentialTypes" : [ ],
    "requiredActions" : [ ],
    "realmRoles" : [ "default-roles-cardano_explorer" ],
    "clientRoles" : {
      "cardano_authentication_client" : [ "uma_protection" ],
      "realm-management" : [ "manage-realm", "manage-users" ]
    },
    "notBefore" : 0,
    "groups" : [ ]
  } ],
  "scopeMappings" : [ {
    "clientScope" : "offline_access",
    "roles" : [ "offline_access" ]
  } ],
  "clientScopeMappings" : {
    "account" : [ {
      "client" : "account-console",
      "roles" : [ "manage-account", "view-groups" ]
    } ]
  },
  "clients" : [ {
    "id" : "85e22a65-3dbd-4c39-9e31-988d99323f04",
    "clientId" : "account",
    "name" : "${client_account}",
    "rootUrl" : "${authBaseUrl}",
    "baseUrl" : "/realms/cardano_explorer/account/",
    "surrogateAuthRequired" : false,
    "enabled" : true,
    "alwaysDisplayInConsole" : false,
    "clientAuthenticatorType" : "client-secret",
    "redirectUris" : [ "/realms/cardano_explorer/account/*" ],
    "webOrigins" : [ ],
    "notBefore" : 0,
    "bearerOnly" : false,
    "consentRequired" : false,
    "standardFlowEnabled" : true,
    "implicitFlowEnabled" : false,
    "directAccessGrantsEnabled" : false,
    "serviceAccountsEnabled" : false,
    "publicClient" : true,
    "frontchannelLogout" : false,
    "protocol" : "openid-connect",
    "attributes" : {
      "post.logout.redirect.uris" : "+"
    },
    "authenticationFlowBindingOverrides" : { },
    "fullScopeAllowed" : false,
    "nodeReRegistrationTimeout" : 0,
    "defaultClientScopes" : [ "web-origins", "acr", "roles", "profile", "email" ],
    "optionalClientScopes" : [ "address", "phone", "offline_access", "microprofile-jwt" ]
  }, {
    "id" : "a064d623-6a60-4445-ac6e-e1b7962f5fd6",
    "clientId" : "account-console",
    "name" : "${client_account-console}",
    "rootUrl" : "${authBaseUrl}",
    "baseUrl" : "/realms/cardano_explorer/account/",
    "surrogateAuthRequired" : false,
    "enabled" : true,
    "alwaysDisplayInConsole" : false,
    "clientAuthenticatorType" : "client-secret",
    "redirectUris" : [ "/realms/cardano_explorer/account/*" ],
    "webOrigins" : [ ],
    "notBefore" : 0,
    "bearerOnly" : false,
    "consentRequired" : false,
    "standardFlowEnabled" : true,
    "implicitFlowEnabled" : false,
    "directAccessGrantsEnabled" : false,
    "serviceAccountsEnabled" : false,
    "publicClient" : true,
    "frontchannelLogout" : false,
    "protocol" : "openid-connect",
    "attributes" : {
      "post.logout.redirect.uris" : "+",
      "pkce.code.challenge.method" : "S256"
    },
    "authenticationFlowBindingOverrides" : { },
    "fullScopeAllowed" : false,
    "nodeReRegistrationTimeout" : 0,
    "protocolMappers" : [ {
      "id" : "32987e8c-5f2b-4d66-8218-223e56f98307",
      "name" : "audience resolve",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-audience-resolve-mapper",
      "consentRequired" : false,
      "config" : { }
    } ],
    "defaultClientScopes" : [ "web-origins", "acr", "roles", "profile", "email" ],
    "optionalClientScopes" : [ "address", "phone", "offline_access", "microprofile-jwt" ]
  }, {
    "id" : "cb8b4889-43c8-4df8-9fc0-027718a082fa",
    "clientId" : "admin-cli",
    "name" : "${client_admin-cli}",
    "surrogateAuthRequired" : false,
    "enabled" : true,
    "alwaysDisplayInConsole" : false,
    "clientAuthenticatorType" : "client-secret",
    "redirectUris" : [ ],
    "webOrigins" : [ ],
    "notBefore" : 0,
    "bearerOnly" : false,
    "consentRequired" : false,
    "standardFlowEnabled" : false,
    "implicitFlowEnabled" : false,
    "directAccessGrantsEnabled" : true,
    "serviceAccountsEnabled" : false,
    "publicClient" : true,
    "frontchannelLogout" : false,
    "protocol" : "openid-connect",
    "attributes" : { },
    "authenticationFlowBindingOverrides" : { },
    "fullScopeAllowed" : false,
    "nodeReRegistrationTimeout" : 0,
    "defaultClientScopes" : [ "web-origins", "acr", "roles", "profile", "email" ],
    "optionalClientScopes" : [ "address", "phone", "offline_access", "microprofile-jwt" ]
  }, {
    "id" : "548044c6-4a13-41cc-808e-eeb4769500c7",
    "clientId" : "broker",
    "name" : "${client_broker}",
    "surrogateAuthRequired" : false,
    "enabled" : true,
    "alwaysDisplayInConsole" : false,
    "clientAuthenticatorType" : "client-secret",
    "redirectUris" : [ ],
    "webOrigins" : [ ],
    "notBefore" : 0,
    "bearerOnly" : true,
    "consentRequired" : false,
    "standardFlowEnabled" : true,
    "implicitFlowEnabled" : false,
    "directAccessGrantsEnabled" : false,
    "serviceAccountsEnabled" : false,
    "publicClient" : false,
    "frontchannelLogout" : false,
    "protocol" : "openid-connect",
    "attributes" : { },
    "authenticationFlowBindingOverrides" : { },
    "fullScopeAllowed" : false,
    "nodeReRegistrationTimeout" : 0,
    "defaultClientScopes" : [ "web-origins", "acr", "roles", "profile", "email" ],
    "optionalClientScopes" : [ "address", "phone", "offline_access", "microprofile-jwt" ]
  }, {
    "id" : "e8a73897-2723-44f0-81b5-37262f5d55ef",
    "clientId" : "cardano_authentication_client",
    "name" : "",
    "description" : "",
    "rootUrl" : "",
    "adminUrl" : "",
    "baseUrl" : "",
    "surrogateAuthRequired" : false,
    "enabled" : true,
    "alwaysDisplayInConsole" : false,
    "clientAuthenticatorType" : "client-secret",
    "secret" : "IIykoV67FWfyBw84t1DEKcEKiUruGt89",
    "redirectUris" : [ "__REDIRECT_URI__" ],
    "webOrigins" : [ "__WEB_ORIGIN__" ],
    "notBefore" : 0,
    "bearerOnly" : false,
    "consentRequired" : false,
    "standardFlowEnabled" : true,
    "implicitFlowEnabled" : false,
    "directAccessGrantsEnabled" : true,
    "serviceAccountsEnabled" : true,
    "authorizationServicesEnabled" : true,
    "publicClient" : false,
    "frontchannelLogout" : true,
    "protocol" : "openid-connect",
    "attributes" : {
      "oidc.ciba.grant.enabled" : "false",
      "oauth2.device.authorization.grant.enabled" : "true",
      "client.secret.creation.time" : "1699364741",
      "backchannel.logout.session.required" : "true",
      "backchannel.logout.revoke.offline.tokens" : "false"
    },
    "authenticationFlowBindingOverrides" : { },
    "fullScopeAllowed" : true,
    "nodeReRegistrationTimeout" : -1,
    "protocolMappers" : [ {
      "id" : "17a30e1e-cd12-4043-8c95-3861aa49ce68",
      "name" : "Client ID",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usersessionmodel-note-mapper",
      "consentRequired" : false,
      "config" : {
        "user.session.note" : "client_id",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "client_id",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "d7071d52-2414-439b-98de-3d7812013c10",
      "name" : "Client Host",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usersessionmodel-note-mapper",
      "consentRequired" : false,
      "config" : {
        "user.session.note" : "clientHost",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "clientHost",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "cc2e0bb3-98b7-41ba-8770-9037333f70b2",
      "name" : "Client IP Address",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usersessionmodel-note-mapper",
      "consentRequired" : false,
      "config" : {
        "user.session.note" : "clientAddress",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "clientAddress",
        "jsonType.label" : "String"
      }
    } ],
    "defaultClientScopes" : [ "web-origins", "acr", "roles", "profile", "email" ],
    "optionalClientScopes" : [ "address", "phone", "offline_access", "microprofile-jwt" ],
    "authorizationSettings" : {
      "allowRemoteResourceManagement" : true,
      "policyEnforcementMode" : "ENFORCING",
      "resources" : [ {
        "name" : "Default Resource",
        "type" : "urn:cardano_authentication_client:resources:default",
        "ownerManagedAccess" : false,
        "attributes" : { },
        "_id" : "34a0b553-1859-4de7-bbc4-eea7a819abe5",
        "uris" : [ "/*" ]
      } ],
      "policies" : [ {
        "id" : "d17a54e7-c4af-4c97-acd2-fa19fac3c4e3",
        "name" : "Default Permission",
        "description" : "A permission that applies to the default resource type",
        "type" : "resource",
        "logic" : "POSITIVE",
        "decisionStrategy" : "UNANIMOUS",
        "config" : {
          "defaultResourceType" : "urn:cardano_authentication_client:resources:default"
        }
      } ],
      "scopes" : [ ],
      "decisionStrategy" : "UNANIMOUS"
    }
  }, {
    "id" : "5808945e-6cf0-4df1-9e81-62bb1fe784c5",
    "clientId" : "realm-management",
    "name" : "${client_realm-management}",
    "surrogateAuthRequired" : false,
    "enabled" : true,
    "alwaysDisplayInConsole" : false,
    "clientAuthenticatorType" : "client-secret",
    "redirectUris" : [ ],
    "webOrigins" : [ ],
    "notBefore" : 0,
    "bearerOnly" : true,
    "consentRequired" : false,
    "standardFlowEnabled" : true,
    "implicitFlowEnabled" : false,
    "directAccessGrantsEnabled" : false,
    "serviceAccountsEnabled" : false,
    "publicClient" : false,
    "frontchannelLogout" : false,
    "protocol" : "openid-connect",
    "attributes" : { },
    "authenticationFlowBindingOverrides" : { },
    "fullScopeAllowed" : false,
    "nodeReRegistrationTimeout" : 0,
    "defaultClientScopes" : [ "web-origins", "acr", "roles", "profile", "email" ],
    "optionalClientScopes" : [ "address", "phone", "offline_access", "microprofile-jwt" ]
  }, {
    "id" : "94fa6ca9-d117-41fd-acfc-3843ff1d8461",
    "clientId" : "security-admin-console",
    "name" : "${client_security-admin-console}",
    "rootUrl" : "${authAdminUrl}",
    "baseUrl" : "/admin/cardano_explorer/console/",
    "surrogateAuthRequired" : false,
    "enabled" : true,
    "alwaysDisplayInConsole" : false,
    "clientAuthenticatorType" : "client-secret",
    "redirectUris" : [ "/admin/cardano_explorer/console/*" ],
    "webOrigins" : [ "+" ],
    "notBefore" : 0,
    "bearerOnly" : false,
    "consentRequired" : false,
    "standardFlowEnabled" : true,
    "implicitFlowEnabled" : false,
    "directAccessGrantsEnabled" : false,
    "serviceAccountsEnabled" : false,
    "publicClient" : true,
    "frontchannelLogout" : false,
    "protocol" : "openid-connect",
    "attributes" : {
      "post.logout.redirect.uris" : "+",
      "pkce.code.challenge.method" : "S256"
    },
    "authenticationFlowBindingOverrides" : { },
    "fullScopeAllowed" : false,
    "nodeReRegistrationTimeout" : 0,
    "protocolMappers" : [ {
      "id" : "da2c86f3-0c4e-472a-91c4-f6fd7ac7c79e",
      "name" : "locale",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "locale",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "locale",
        "jsonType.label" : "String"
      }
    } ],
    "defaultClientScopes" : [ "web-origins", "acr", "roles", "profile", "email" ],
    "optionalClientScopes" : [ "address", "phone", "offline_access", "microprofile-jwt" ]
  } ],
  "clientScopes" : [ {
    "id" : "080ffd77-ff15-4684-9d4c-70740f87da08",
    "name" : "email",
    "description" : "OpenID Connect built-in scope: email",
    "protocol" : "openid-connect",
    "attributes" : {
      "include.in.token.scope" : "true",
      "display.on.consent.screen" : "true",
      "consent.screen.text" : "${emailScopeConsentText}"
    },
    "protocolMappers" : [ {
      "id" : "69f1a5d8-b8d0-485f-af88-c99c35a6ee0c",
      "name" : "email",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "email",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "email",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "510f19c6-1aab-45e4-8f60-e26630386a05",
      "name" : "email verified",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-property-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "emailVerified",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "email_verified",
        "jsonType.label" : "boolean"
      }
    } ]
  }, {
    "id" : "583d1f84-bb15-4a3a-99ca-862cd28514de",
    "name" : "address",
    "description" : "OpenID Connect built-in scope: address",
    "protocol" : "openid-connect",
    "attributes" : {
      "include.in.token.scope" : "true",
      "display.on.consent.screen" : "true",
      "consent.screen.text" : "${addressScopeConsentText}"
    },
    "protocolMappers" : [ {
      "id" : "5c6c060d-f549-4ecf-b2b1-cc89d6dd880d",
      "name" : "address",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-address-mapper",
      "consentRequired" : false,
      "config" : {
        "user.attribute.formatted" : "formatted",
        "user.attribute.country" : "country",
        "user.attribute.postal_code" : "postal_code",
        "userinfo.token.claim" : "true",
        "user.attribute.street" : "street",
        "id.token.claim" : "true",
        "user.attribute.region" : "region",
        "access.token.claim" : "true",
        "user.attribute.locality" : "locality"
      }
    } ]
  }, {
    "id" : "3896b4d2-c9f0-414c-9007-943551256454",
    "name" : "phone",
    "description" : "OpenID Connect built-in scope: phone",
    "protocol" : "openid-connect",
    "attributes" : {
      "include.in.token.scope" : "true",
      "display.on.consent.screen" : "true",
      "consent.screen.text" : "${phoneScopeConsentText}"
    },
    "protocolMappers" : [ {
      "id" : "11fb14e8-1d97-42e4-9895-249b3834af4a",
      "name" : "phone number verified",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "phoneNumberVerified",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "phone_number_verified",
        "jsonType.label" : "boolean"
      }
    }, {
      "id" : "fd8f4ce2-4c58-4449-b2b4-363aa04c4861",
      "name" : "phone number",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "phoneNumber",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "phone_number",
        "jsonType.label" : "String"
      }
    } ]
  }, {
    "id" : "e6ec3008-e92f-4eaf-86a3-769f666f9aa9",
    "name" : "acr",
    "description" : "OpenID Connect scope for add acr (authentication context class reference) to the token",
    "protocol" : "openid-connect",
    "attributes" : {
      "include.in.token.scope" : "false",
      "display.on.consent.screen" : "false"
    },
    "protocolMappers" : [ {
      "id" : "ab911e23-38d7-44dc-80d1-0d7a830846e2",
      "name" : "acr loa level",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-acr-mapper",
      "consentRequired" : false,
      "config" : {
        "id.token.claim" : "true",
        "access.token.claim" : "true"
      }
    } ]
  }, {
    "id" : "a96bc82a-cd64-4f79-aba6-578021f1508e",
    "name" : "role_list",
    "description" : "SAML role list",
    "protocol" : "saml",
    "attributes" : {
      "consent.screen.text" : "${samlRoleListScopeConsentText}",
      "display.on.consent.screen" : "true"
    },
    "protocolMappers" : [ {
      "id" : "e17fd314-0b5f-49af-b580-cb3ce33885fc",
      "name" : "role list",
      "protocol" : "saml",
      "protocolMapper" : "saml-role-list-mapper",
      "consentRequired" : false,
      "config" : {
        "single" : "false",
        "attribute.nameformat" : "Basic",
        "attribute.name" : "Role"
      }
    } ]
  }, {
    "id" : "0e2580c1-9cf0-4ddd-a49c-cf3385455797",
    "name" : "microprofile-jwt",
    "description" : "Microprofile - JWT built-in scope",
    "protocol" : "openid-connect",
    "attributes" : {
      "include.in.token.scope" : "true",
      "display.on.consent.screen" : "false"
    },
    "protocolMappers" : [ {
      "id" : "6255024c-78e0-492a-8283-d932843f089f",
      "name" : "groups",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-realm-role-mapper",
      "consentRequired" : false,
      "config" : {
        "multivalued" : "true",
        "user.attribute" : "foo",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "groups",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "ab70d6c7-e009-4331-95ee-1e4ff64e269a",
      "name" : "upn",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "username",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "upn",
        "jsonType.label" : "String"
      }
    } ]
  }, {
    "id" : "97a364be-3780-4bb2-8877-e35743b05196",
    "name" : "roles",
    "description" : "OpenID Connect scope for add user roles to the access token",
    "protocol" : "openid-connect",
    "attributes" : {
      "include.in.token.scope" : "false",
      "display.on.consent.screen" : "true",
      "consent.screen.text" : "${rolesScopeConsentText}"
    },
    "protocolMappers" : [ {
      "id" : "a011ac2e-47b5-4a14-8299-0328520b8cd3",
      "name" : "realm roles",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-realm-role-mapper",
      "consentRequired" : false,
      "config" : {
        "user.attribute" : "foo",
        "access.token.claim" : "true",
        "claim.name" : "realm_access.roles",
        "jsonType.label" : "String",
        "multivalued" : "true"
      }
    }, {
      "id" : "2fd74099-8bf0-48ab-8889-6fb8c827d1bb",
      "name" : "audience resolve",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-audience-resolve-mapper",
      "consentRequired" : false,
      "config" : { }
    }, {
      "id" : "21fe0d3b-67df-43cb-9b82-9344cb693c83",
      "name" : "client roles",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-client-role-mapper",
      "consentRequired" : false,
      "config" : {
        "user.attribute" : "foo",
        "access.token.claim" : "true",
        "claim.name" : "resource_access.${client_id}.roles",
        "jsonType.label" : "String",
        "multivalued" : "true"
      }
    } ]
  }, {
    "id" : "853e7df1-5de7-4fde-8f97-dda4e5fc6e0a",
    "name" : "profile",
    "description" : "OpenID Connect built-in scope: profile",
    "protocol" : "openid-connect",
    "attributes" : {
      "include.in.token.scope" : "true",
      "display.on.consent.screen" : "true",
      "consent.screen.text" : "${profileScopeConsentText}"
    },
    "protocolMappers" : [ {
      "id" : "3c76354d-905a-4844-8617-804681ab3109",
      "name" : "gender",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "gender",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "gender",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "ff458a41-1026-45b4-bcac-9f45dc15d8f8",
      "name" : "updated at",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "updatedAt",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "updated_at",
        "jsonType.label" : "long"
      }
    }, {
      "id" : "cc065f44-a9ff-42ac-8d60-e86c2cfd4066",
      "name" : "birthdate",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "birthdate",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "birthdate",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "b6086a67-d4c1-412b-90f3-00ad82ccf57b",
      "name" : "website",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "website",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "website",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "f28d6100-8961-4cc5-b1de-ac0352d93d4d",
      "name" : "family name",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "lastName",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "family_name",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "ad5786b0-9291-4864-a1d6-0acedb1cedc5",
      "name" : "username",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "username",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "preferred_username",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "732a8e8b-85bb-45e9-896d-13d650d89856",
      "name" : "full name",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-full-name-mapper",
      "consentRequired" : false,
      "config" : {
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "userinfo.token.claim" : "true"
      }
    }, {
      "id" : "2ca471d4-2745-4772-ba23-754480830067",
      "name" : "nickname",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "nickname",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "nickname",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "c0e12eca-ca22-478a-bd83-62bf33a06e5d",
      "name" : "zoneinfo",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "zoneinfo",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "zoneinfo",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "0d423e63-6242-418f-ac37-00c8fe980aa1",
      "name" : "picture",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "picture",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "picture",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "dff310a8-0f4d-4a0f-ba82-dcef19bd5038",
      "name" : "locale",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "locale",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "locale",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "bf8cb480-3615-44ed-b8ce-7f6d8ae47f74",
      "name" : "middle name",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "middleName",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "middle_name",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "2fab2317-0180-4fd4-b92a-5469b037ad14",
      "name" : "profile",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "profile",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "profile",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "d1491066-f351-488b-a3f0-890d26083ddd",
      "name" : "given name",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "firstName",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "given_name",
        "jsonType.label" : "String"
      }
    } ]
  }, {
    "id" : "18c71aea-58d3-48dd-8e2c-d7dc4e747ddc",
    "name" : "web-origins",
    "description" : "OpenID Connect scope for add allowed web origins to the access token",
    "protocol" : "openid-connect",
    "attributes" : {
      "include.in.token.scope" : "false",
      "display.on.consent.screen" : "false",
      "consent.screen.text" : ""
    },
    "protocolMappers" : [ {
      "id" : "8469441a-704a-4667-a7a9-128701384ce1",
      "name" : "allowed web origins",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-allowed-origins-mapper",
      "consentRequired" : false,
      "config" : { }
    } ]
  }, {
    "id" : "8a7ec9dd-c7ab-4708-8ba2-40413df2315f",
    "name" : "offline_access",
    "description" : "OpenID Connect built-in scope: offline_access",
    "protocol" : "openid-connect",
    "attributes" : {
      "consent.screen.text" : "${offlineAccessScopeConsentText}",
      "display.on.consent.screen" : "true"
    }
  } ],
  "defaultDefaultClientScopes" : [ "role_list", "profile", "email", "roles", "web-origins", "acr" ],
  "defaultOptionalClientScopes" : [ "offline_access", "address", "phone", "microprofile-jwt" ],
  "browserSecurityHeaders" : {
    "contentSecurityPolicyReportOnly" : "",
    "xContentTypeOptions" : "nosniff",
    "referrerPolicy" : "no-referrer",
    "xRobotsTag" : "none",
    "xFrameOptions" : "SAMEORIGIN",
    "contentSecurityPolicy" : "frame-src 'self'; frame-ancestors 'self'; object-src 'none';",
    "xXSSProtection" : "1; mode=block",
    "strictTransportSecurity" : "max-age=31536000; includeSubDomains"
  },
  "smtpServer" : { },
  "eventsEnabled" : true,
  "eventsExpiration" : 900,
  "eventsListeners" : [ "mapping-role-event-listener", "jboss-logging" ],
  "enabledEventTypes" : [ "UPDATE_CONSENT_ERROR", "SEND_RESET_PASSWORD", "GRANT_CONSENT", "VERIFY_PROFILE_ERROR", "UPDATE_TOTP", "REMOVE_TOTP", "REVOKE_GRANT", "LOGIN_ERROR", "CLIENT_LOGIN", "RESET_PASSWORD_ERROR", "IMPERSONATE_ERROR", "CODE_TO_TOKEN_ERROR", "CUSTOM_REQUIRED_ACTION", "OAUTH2_DEVICE_CODE_TO_TOKEN_ERROR", "RESTART_AUTHENTICATION", "UPDATE_PROFILE_ERROR", "IMPERSONATE", "LOGIN", "UPDATE_PASSWORD_ERROR", "OAUTH2_DEVICE_VERIFY_USER_CODE", "CLIENT_INITIATED_ACCOUNT_LINKING", "IDENTITY_PROVIDER_LOGIN", "TOKEN_EXCHANGE", "REGISTER", "LOGOUT", "AUTHREQID_TO_TOKEN", "DELETE_ACCOUNT_ERROR", "CLIENT_REGISTER", "IDENTITY_PROVIDER_LINK_ACCOUNT", "UPDATE_PASSWORD", "DELETE_ACCOUNT", "FEDERATED_IDENTITY_LINK_ERROR", "CLIENT_DELETE", "IDENTITY_PROVIDER_FIRST_LOGIN", "VERIFY_EMAIL", "CLIENT_DELETE_ERROR", "CLIENT_LOGIN_ERROR", "RESTART_AUTHENTICATION_ERROR", "REMOVE_FEDERATED_IDENTITY_ERROR", "EXECUTE_ACTIONS", "TOKEN_EXCHANGE_ERROR", "PERMISSION_TOKEN", "SEND_IDENTITY_PROVIDER_LINK_ERROR", "EXECUTE_ACTION_TOKEN_ERROR", "SEND_VERIFY_EMAIL", "OAUTH2_DEVICE_AUTH", "EXECUTE_ACTIONS_ERROR", "REMOVE_FEDERATED_IDENTITY", "OAUTH2_DEVICE_CODE_TO_TOKEN", "IDENTITY_PROVIDER_POST_LOGIN", "IDENTITY_PROVIDER_LINK_ACCOUNT_ERROR", "UPDATE_EMAIL", "OAUTH2_DEVICE_VERIFY_USER_CODE_ERROR", "REGISTER_ERROR", "REVOKE_GRANT_ERROR", "LOGOUT_ERROR", "UPDATE_EMAIL_ERROR", "EXECUTE_ACTION_TOKEN", "CLIENT_UPDATE_ERROR", "UPDATE_PROFILE", "AUTHREQID_TO_TOKEN_ERROR", "FEDERATED_IDENTITY_LINK", "CLIENT_REGISTER_ERROR", "SEND_VERIFY_EMAIL_ERROR", "SEND_IDENTITY_PROVIDER_LINK", "IDENTITY_PROVIDER_LOGIN_ERROR", "RESET_PASSWORD", "CLIENT_INITIATED_ACCOUNT_LINKING_ERROR", "OAUTH2_DEVICE_AUTH_ERROR", "UPDATE_CONSENT", "REMOVE_TOTP_ERROR", "VERIFY_EMAIL_ERROR", "SEND_RESET_PASSWORD_ERROR", "CLIENT_UPDATE", "IDENTITY_PROVIDER_POST_LOGIN_ERROR", "CUSTOM_REQUIRED_ACTION_ERROR", "UPDATE_TOTP_ERROR", "CODE_TO_TOKEN", "VERIFY_PROFILE", "GRANT_CONSENT_ERROR", "IDENTITY_PROVIDER_FIRST_LOGIN_ERROR" ],
  "adminEventsEnabled" : true,
  "adminEventsDetailsEnabled" : false,
  "identityProviders" : [ ],
  "identityProviderMappers" : [ ],
  "components" : {
    "org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy" : [ {
      "id" : "0d428b0c-9c55-4e3f-9c4c-8b4d8878a121",
      "name" : "Max Clients Limit",
      "providerId" : "max-clients",
      "subType" : "anonymous",
      "subComponents" : { },
      "config" : {
        "max-clients" : [ "200" ]
      }
    }, {
      "id" : "de9745fd-8363-4156-ad0a-af62ce2764ad",
      "name" : "Allowed Protocol Mapper Types",
      "providerId" : "allowed-protocol-mappers",
      "subType" : "authenticated",
      "subComponents" : { },
      "config" : {
        "allowed-protocol-mapper-types" : [ "oidc-usermodel-attribute-mapper", "oidc-address-mapper", "oidc-sha256-pairwise-sub-mapper", "oidc-full-name-mapper", "saml-user-attribute-mapper", "oidc-usermodel-property-mapper", "saml-role-list-mapper", "saml-user-property-mapper" ]
      }
    }, {
      "id" : "43c420ab-5659-4a85-8d94-ae70b3a4cb9a",
      "name" : "Consent Required",
      "providerId" : "consent-required",
      "subType" : "anonymous",
      "subComponents" : { },
      "config" : { }
    }, {
      "id" : "bd20411a-85fc-441c-977d-ea763e5dc3f0",
      "name" : "Trusted Hosts",
      "providerId" : "trusted-hosts",
      "subType" : "anonymous",
      "subComponents" : { },
      "config" : {
        "host-sending-registration-request-must-match" : [ "true" ],
        "client-uris-must-match" : [ "true" ]
      }
    }, {
      "id" : "18948591-6eec-476c-9577-c3779882fe3d",
      "name" : "Full Scope Disabled",
      "providerId" : "scope",
      "subType" : "anonymous",
      "subComponents" : { },
      "config" : { }
    }, {
      "id" : "a1dbf8ed-de13-4876-b2f8-46f22ed956f9",
      "name" : "Allowed Client Scopes",
      "providerId" : "allowed-client-templates",
      "subType" : "anonymous",
      "subComponents" : { },
      "config" : {
        "allow-default-scopes" : [ "true" ]
      }
    }, {
      "id" : "641c2b30-fb14-4cf2-b003-03f52734d482",
      "name" : "Allowed Protocol Mapper Types",
      "providerId" : "allowed-protocol-mappers",
      "subType" : "anonymous",
      "subComponents" : { },
      "config" : {
        "allowed-protocol-mapper-types" : [ "oidc-usermodel-property-mapper", "oidc-address-mapper", "oidc-usermodel-attribute-mapper", "oidc-sha256-pairwise-sub-mapper", "saml-user-attribute-mapper", "saml-user-property-mapper", "oidc-full-name-mapper", "saml-role-list-mapper" ]
      }
    }, {
      "id" : "13c556f1-331b-4c8e-9e44-519366df5809",
      "name" : "Allowed Client Scopes",
      "providerId" : "allowed-client-templates",
      "subType" : "authenticated",
      "subComponents" : { },
      "config" : {
        "allow-default-scopes" : [ "true" ]
      }
    } ],
    "org.keycloak.userprofile.UserProfileProvider" : [ {
      "id" : "7f48ceb3-392d-40c9-b066-147e898a238e",
      "providerId" : "declarative-user-profile",
      "subComponents" : { },
      "config" : { }
    } ],
    "org.keycloak.keys.KeyProvider" : [ {
      "id" : "53d25f28-bc7b-4eb4-9fcf-4ac10caf41e4",
      "name" : "rsa-generated",
      "providerId" : "rsa-generated",
      "subComponents" : { },
      "config" : {
        "privateKey" : [ "MIIEowIBAAKCAQEAxO4kLlngYtB6fZqW3MpE1dqC6rbbv0TSVAqDfpzVqN11J8OEAMWLoJuUtaVocHOY3eiaMUIdu4Y1/ihlYj9gvjDe10zAeFDndeEiWPbJ32jlXysNCdh9i6v+sadZQ47E6tGa5yAsj0yA9CGzbzv4SNw2Mead2T7GVfW/APdQjNu9iBfxjgEHfEpdgsIALa+ZUK8j88lJ9ZVu01qDOGAL+fSbQw2iDzyCC3aoRKQ0DUibCFqVIaWbXwXQg7JMLu2M10UevOpAH+8qINMaW6MIqjSWd79UtL99gnZio2Eqmw8D86v5IKkBlml8bTsze7F7orkB4d2qGw4nxddY3/5XwwIDAQABAoIBACy6PeN9LFalLTz78InjMvEzeQqgT5bIpdDKtoT6UBjTe7l1UbTvKUBoNcVv9SGdT7oPC35mmuQtnnPEbwp6hJmN9f8GgqyRC5EdejSkkMM6SuZrm8GBtzoS7X0iGkca1STUSI94IkbFxIAua1e3UjQSOAjrbSybcmaxsxkgEt564Dk6i3Z44fLJPVWzoCA8fjpvaVLx0KiYV6HSIL5jcVMOi1QU+2UlttGnTXlDACe7B+HBkyXCjyw/r+UbRByhCCKzNFiv9VpFiFBVD+WDoYjoUOzMu22UOYr8Crq2jKgoYgZU/8tvT+vDn3RL2PhDAe+hX6iHjqljGbGRvs/gLDECgYEA4hiyQXNoT1IaeE296TIuBPkwUJeWmbKUXSCd8nZ7AsxbdnuS2cUe8b+urxladjTVIyrwQDRvXONwu6fXxFdycwTVcVR/1F2YMqPUszt8kkACmTlRWalwdDULo8jUQQjSC6SQN4iil2d6Yqxo7glLe7gdCWFAr07tAqGtHsbT55kCgYEA3vnrCInUpiV9GPD2TK25S3BAEoYxxEGuDEchKsdKs4VY6vJvqfdfG5kEgoqK4eXy3UVjx+AymWFW/go31DARZx3lwvoHxP+RhIS6BH/VcIGoGChV3UkOsc9XfPQEQ4ZI1XPYE+7S/C4mlEtIDzNq93YD4RRlegzaArcrnQxEY7sCgYBpg+8EvpBKnF2AEX/0qZdyeLzEj8oX1pCI2aT8t7B2wNpadBc3jFgBqFwkmWdvTP6/F9XhkNb7+aTt3snCdrER3oqiBjZV7IwP7gdg0f0z362WxnohpwK1KOBSwnHUHAKyrk0jrZ97my4xiJa7qBjTFzxt2TYv2lyf4sYBW/ce2QKBgGnRdUdCEhcfZ3T0tg7By1QP23hvEyMugGj3QLhKiBsWR5KIp5GkAhDY15kUIsD+HfoAuk/bCGheF6jMNJ6QiVHEkIutmQ5WszqOurDz8dlIzvtX+RfhWgXsfnqgW32oNtyF85/SEXL4e/qYl4QPRM5XxZOF2HrJqpTrVNldPv+NAoGBALj3sF80Uy3JLi3NY6C3DTLCJAeFJqkhoW7ndAFkRUlXs+vhpHqsRhSkmWXDKCrcxKcDhzstEsTmHXl30/3lyJNPJMM7ny2hIvlvz8NfJMeayutWJHIdnFXGytcU0epYTXC7Eo2HUQswnhMK2PpKh/tXzqpZdlYiBQJ3WTb5rJ44" ],
        "keyUse" : [ "SIG" ],
        "certificate" : [ "MIICrzCCAZcCBgGLqgRQajANBgkqhkiG9w0BAQsFADAbMRkwFwYDVQQDDBBjYXJkYW5vX2V4cGxvcmVyMB4XDTIzMTEwNzEzMzk1MVoXDTMzMTEwNzEzNDEzMVowGzEZMBcGA1UEAwwQY2FyZGFub19leHBsb3JlcjCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMTuJC5Z4GLQen2altzKRNXaguq2279E0lQKg36c1ajddSfDhADFi6CblLWlaHBzmN3omjFCHbuGNf4oZWI/YL4w3tdMwHhQ53XhIlj2yd9o5V8rDQnYfYur/rGnWUOOxOrRmucgLI9MgPQhs287+EjcNjHmndk+xlX1vwD3UIzbvYgX8Y4BB3xKXYLCAC2vmVCvI/PJSfWVbtNagzhgC/n0m0MNog88ggt2qESkNA1ImwhalSGlm18F0IOyTC7tjNdFHrzqQB/vKiDTGlujCKo0lne/VLS/fYJ2YqNhKpsPA/Or+SCpAZZpfG07M3uxe6K5AeHdqhsOJ8XXWN/+V8MCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEASl/HpyP7uv9x+ITuMk7eOOWC9E67VUsUvk1LrbXkY15FJFybyw80wc/ktaGKyC9ygqLT5HhIGsyT0xIu8rlM/BhIKEqTum4a17YQGPcPUa5ep4p+KxTuC39FTent0nlf6qoOjosdSNZu4fgJ/7GQfD/65jgm/xbuR0jMWRGuOZAHox6GyjfWuiHpOCF7AlUx9MkUsbfnIcurtOkxKXfQ+V3PMTrCHVlfuxsOwNFnw2/R6lKhDdUD5+pnUF18m6M/IhC2LotbVpnGIWGObj1kBr9fp/o5rRZ8UxpFFrmHBWY/j77AxnTawCC9ykW/2LEQygg6q/0E4IKnza29SoJ52g==" ],
        "priority" : [ "100" ]
      }
    }, {
      "id" : "474090ca-a140-49cc-b9c6-9b1ec96055be",
      "name" : "aes-generated",
      "providerId" : "aes-generated",
      "subComponents" : { },
      "config" : {
        "kid" : [ "ff7df3c6-21d6-4cd8-9744-107fb5bdf46a" ],
        "secret" : [ "aNPV2d8Ra_pa-_NwRHnYIQ" ],
        "priority" : [ "100" ]
      }
    }, {
      "id" : "1e764097-66d4-4f54-9287-693fc969a464",
      "name" : "rsa-enc-generated",
      "providerId" : "rsa-enc-generated",
      "subComponents" : { },
      "config" : {
        "privateKey" : [ "MIIEowIBAAKCAQEAyfQgmcv9PG1NPM8YVo8tbjxHof7dbZjDoVxdbT/fSL9znc9x8i2o1ov4lwF/JGhjjgS5MOifnFhVqUhFVGsPYaP0WzOOLhPGYepaw6mwzLSgUP8bnjeFKVP5iYkX5jpmkFAbB8xJSTiVZt/Ah9w/EmjIFpCKqTYke4mIMpTWYjVSV9Oj4Xrzew1/0UPfHl1fpTkkhFXpBKjkwFAT0zPdi+i5gz55lFAtX6uUxVYVk/M1fBLpFpC8cOVJZMSsr/6MrRHf0SlEmUBQwBVZsveQX66R1KRrWKT+334yoBWuttwfvlTVpjeQwkovBK/sGxd6LyNDcIDEQzdPyN6ZM7Z6OQIDAQABAoIBAAdvThHxzhLyIGFOnuIOgSKPsPPR5HuUuUMEADAcJVxR9aL69s8IU+QBVCrVZ18pdRAB5+ilvIm7c/yru42sWu0by/+qVrlaItGkMiifSYuuH9n+tX9HvCz1n/hEJhXLBmj/e5DebVl3B7afu/OxR1fzDpN+4vPwZjuidQ0NQkhsOeSfjAhV6WhXHfoblkniZu9WSCcyb4WX9yzYFoXJxCVOfPl107zvsjMaSQU2t/n+gacWQaMP5E/3fwtcuEq8IEm/BJfWQyA9nlu9mpKO/0RKaqsq3Bx32SK3+xGpk/ZvWTTw2xySpFcY0r95OnRjj9UaEY7FZEvp3YSRJHoAaVkCgYEA+nKlFhhQ97J1ODkAAUBPkgjRXDyrjW7kLwGfxQCUHp3FqvRHZUJHg97DSsqVfaSgdHavf04kbGAzjqgX0ik2uv1x1dCRUsKpo/XmRsQOqpQjAUls+luxV70wdW7pCxcObdDc9wU+E7oNRlsjoGocYxx1DePCMcyadmYrVtaeKWUCgYEAzm5D+Hc+/5ZUh5hE191oXKWwFV4bn3/3Eh55SQgtLjHsiAs0HHvbXKxDeSzZ3miXul5zusrRaZk4CUUWfHgSi+oJEYT0Mkv9PzgiVE8DMBSkjoTWLU+s+c3XmlJDex9cqtdGZdihtMlM2gfeEWUy4ML2rDawF5qDGs58cM/T6kUCgYEAsEvFg/EoBcS8mRAevcK8gGrunUQj6tJVk/VLt0x597dN7tI388KdPumvhVVnt+MMBA+L6ExkfVmW0WPoKMtUOHqyCDXiJmTbL4yoRcY7IL5Yic5bBT1IcB/AHdot992KDamJ8eTh1tcQAyeiw4gOZYIhr95U0+NYE+JFvcIj4bUCgYBUcAf/fw9Wzm//Esq+HYONufw/kUHtSROP5sUnmV+ZsLQWpbaIHCWLT689xpnLfq7pfa8K7eEgt5qTOB5NhXPYpT9WWjLky+5nyJdKMxOTvC3fWfidiiSv/YG+Tv3pQ9fMzceu6yCh6WE1Idi09tcL1tO9d5Up4nrUITgHkQsWnQKBgGghVNiHrwhtWUy4RmAHCDIwvfq6wuOucy00CaHTakm36a8cnTKYOwDnIwHDNQsOUnfOUYVSj6pTbez+X6ehWoCwjK1TFqBegmFa6ycTStfPfMeuhI/MhCCbW5wmCzS0yy6aQHuNCXPfARqQVdTXFAxoIk4Xm1nbrinzHonZCaCj" ],
        "keyUse" : [ "ENC" ],
        "certificate" : [ "MIICrzCCAZcCBgGLqgRStjANBgkqhkiG9w0BAQsFADAbMRkwFwYDVQQDDBBjYXJkYW5vX2V4cGxvcmVyMB4XDTIzMTEwNzEzMzk1MVoXDTMzMTEwNzEzNDEzMVowGzEZMBcGA1UEAwwQY2FyZGFub19leHBsb3JlcjCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMn0IJnL/TxtTTzPGFaPLW48R6H+3W2Yw6FcXW0/30i/c53PcfItqNaL+JcBfyRoY44EuTDon5xYValIRVRrD2Gj9Fszji4TxmHqWsOpsMy0oFD/G543hSlT+YmJF+Y6ZpBQGwfMSUk4lWbfwIfcPxJoyBaQiqk2JHuJiDKU1mI1UlfTo+F683sNf9FD3x5dX6U5JIRV6QSo5MBQE9Mz3YvouYM+eZRQLV+rlMVWFZPzNXwS6RaQvHDlSWTErK/+jK0R39EpRJlAUMAVWbL3kF+ukdSka1ik/t9+MqAVrrbcH75U1aY3kMJKLwSv7BsXei8jQ3CAxEM3T8jemTO2ejkCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAeLFHunLYZ4FOrZXi+d7KpDfEV5yq6VEoWwYJeunzq1atrjJ40yLDrveSCBIfMUzSv7qrs6RF8BYJdBgB9lVvQehTO0CcJQsCwSqnDTxRjYzykBcyOgRnfQjaf2MH7PbzmgrmDUURTZqL6SmY4maAMz6cdCfbDBeso6eGreeBCQJBxxNFgNzRDDXJk7govwnZtzsrxWiIoa7GCuF5g1qxoObpDLApW0BD5wgn5gRaVz27W7QTSRtmIexMY5YNZI6GXiXZx5zWkuc6Jvb0eXYoqY/NAogmE14zjdm+qi366fSiXQwlqW986HA2kfBrEuJsvaqdoE9zeVlfJiW88VJ87g==" ],
        "priority" : [ "100" ],
        "algorithm" : [ "RSA-OAEP" ]
      }
    }, {
      "id" : "e174d118-88ee-4133-a262-2092210b0237",
      "name" : "hmac-generated",
      "providerId" : "hmac-generated",
      "subComponents" : { },
      "config" : {
        "kid" : [ "7f563994-e655-4850-ae74-07b68a6c78e6" ],
        "secret" : [ "_Sd2DBfDCutKY4F_wl9VstFE5zCQFHCjUtNGbgxljAKECX2g-DKnPdVMxCBJ_xoGq6M6DDHULVmBC8zfth3gxw" ],
        "priority" : [ "100" ],
        "algorithm" : [ "HS256" ]
      }
    } ]
  },
  "internationalizationEnabled" : false,
  "supportedLocales" : [ ],
  "authenticationFlows" : [ {
    "id" : "ad6958cb-133d-44dd-9a64-7bf7a0470e3b",
    "alias" : "Account verification options",
    "description" : "Method with which to verity the existing account",
    "providerId" : "basic-flow",
    "topLevel" : false,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "idp-email-verification",
      "authenticatorFlow" : false,
      "requirement" : "ALTERNATIVE",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticatorFlow" : true,
      "requirement" : "ALTERNATIVE",
      "priority" : 20,
      "autheticatorFlow" : true,
      "flowAlias" : "Verify Existing Account by Re-authentication",
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "3294e495-04f7-4174-a2d4-469ddacfaf0e",
    "alias" : "Browser - Conditional OTP",
    "description" : "Flow to determine if the OTP is required for the authentication",
    "providerId" : "basic-flow",
    "topLevel" : false,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "conditional-user-configured",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "auth-otp-form",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 20,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "71652a01-7ef2-4189-8767-eba315c4a852",
    "alias" : "Direct Grant - Conditional OTP",
    "description" : "Flow to determine if the OTP is required for the authentication",
    "providerId" : "basic-flow",
    "topLevel" : false,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "conditional-user-configured",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "direct-grant-validate-otp",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 20,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "18cfdca5-703b-42f6-80ae-d9302daf759e",
    "alias" : "First broker login - Conditional OTP",
    "description" : "Flow to determine if the OTP is required for the authentication",
    "providerId" : "basic-flow",
    "topLevel" : false,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "conditional-user-configured",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "auth-otp-form",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 20,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "30b9226e-c391-49c0-a33d-134e058230b5",
    "alias" : "Handle Existing Account",
    "description" : "Handle what to do if there is existing account with same email/username like authenticated identity provider",
    "providerId" : "basic-flow",
    "topLevel" : false,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "idp-confirm-link",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticatorFlow" : true,
      "requirement" : "REQUIRED",
      "priority" : 20,
      "autheticatorFlow" : true,
      "flowAlias" : "Account verification options",
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "c5c72bd8-d3ac-494d-9579-394551937c64",
    "alias" : "Reset - Conditional OTP",
    "description" : "Flow to determine if the OTP should be reset or not. Set to REQUIRED to force.",
    "providerId" : "basic-flow",
    "topLevel" : false,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "conditional-user-configured",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "reset-otp",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 20,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "4aedb07c-1bbd-479c-881d-f99a3f4ae7dc",
    "alias" : "User creation or linking",
    "description" : "Flow for the existing/non-existing user alternatives",
    "providerId" : "basic-flow",
    "topLevel" : false,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticatorConfig" : "create unique user config",
      "authenticator" : "idp-create-user-if-unique",
      "authenticatorFlow" : false,
      "requirement" : "ALTERNATIVE",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticatorFlow" : true,
      "requirement" : "ALTERNATIVE",
      "priority" : 20,
      "autheticatorFlow" : true,
      "flowAlias" : "Handle Existing Account",
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "d4c4ee4e-3665-4157-958a-85dfc52b41ca",
    "alias" : "Verify Existing Account by Re-authentication",
    "description" : "Reauthentication of existing account",
    "providerId" : "basic-flow",
    "topLevel" : false,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "idp-username-password-form",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticatorFlow" : true,
      "requirement" : "CONDITIONAL",
      "priority" : 20,
      "autheticatorFlow" : true,
      "flowAlias" : "First broker login - Conditional OTP",
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "2eaa2a4a-717b-43f5-80c3-6e6e4211b255",
    "alias" : "browser",
    "description" : "browser based authentication",
    "providerId" : "basic-flow",
    "topLevel" : true,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "auth-cookie",
      "authenticatorFlow" : false,
      "requirement" : "ALTERNATIVE",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "auth-spnego",
      "authenticatorFlow" : false,
      "requirement" : "DISABLED",
      "priority" : 20,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "identity-provider-redirector",
      "authenticatorFlow" : false,
      "requirement" : "ALTERNATIVE",
      "priority" : 25,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticatorFlow" : true,
      "requirement" : "ALTERNATIVE",
      "priority" : 30,
      "autheticatorFlow" : true,
      "flowAlias" : "forms",
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "334d49b7-213d-473b-a156-ca73bdcf6ba9",
    "alias" : "clients",
    "description" : "Base authentication for clients",
    "providerId" : "client-flow",
    "topLevel" : true,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "client-secret",
      "authenticatorFlow" : false,
      "requirement" : "ALTERNATIVE",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "client-jwt",
      "authenticatorFlow" : false,
      "requirement" : "ALTERNATIVE",
      "priority" : 20,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "client-secret-jwt",
      "authenticatorFlow" : false,
      "requirement" : "ALTERNATIVE",
      "priority" : 30,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "client-x509",
      "authenticatorFlow" : false,
      "requirement" : "ALTERNATIVE",
      "priority" : 40,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "a1106566-7dcc-47de-b67b-741840b76742",
    "alias" : "direct grant",
    "description" : "OpenID Connect Resource Owner Grant",
    "providerId" : "basic-flow",
    "topLevel" : true,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "direct-grant-validate-username",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "direct-grant-validate-password",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 20,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticatorFlow" : true,
      "requirement" : "CONDITIONAL",
      "priority" : 30,
      "autheticatorFlow" : true,
      "flowAlias" : "Direct Grant - Conditional OTP",
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "2cffd293-4a6b-4430-bbdd-eee7ce69be8f",
    "alias" : "docker auth",
    "description" : "Used by Docker clients to authenticate against the IDP",
    "providerId" : "basic-flow",
    "topLevel" : true,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "docker-http-basic-authenticator",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "cc7a609b-6cb5-4fdf-a70b-26e76806ae30",
    "alias" : "first broker login",
    "description" : "Actions taken after first broker login with identity provider account, which is not yet linked to any Keycloak account",
    "providerId" : "basic-flow",
    "topLevel" : true,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticatorConfig" : "review profile config",
      "authenticator" : "idp-review-profile",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticatorFlow" : true,
      "requirement" : "REQUIRED",
      "priority" : 20,
      "autheticatorFlow" : true,
      "flowAlias" : "User creation or linking",
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "6805421c-d588-4590-865d-38d0f5306c16",
    "alias" : "forms",
    "description" : "Username, password, otp and other auth forms.",
    "providerId" : "basic-flow",
    "topLevel" : false,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "auth-username-password-form",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticatorFlow" : true,
      "requirement" : "CONDITIONAL",
      "priority" : 20,
      "autheticatorFlow" : true,
      "flowAlias" : "Browser - Conditional OTP",
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "b8aa3908-563d-4ade-a3b4-944a9af1db03",
    "alias" : "registration",
    "description" : "registration flow",
    "providerId" : "basic-flow",
    "topLevel" : true,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "registration-page-form",
      "authenticatorFlow" : true,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : true,
      "flowAlias" : "registration form",
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "89026a52-2afb-4f3b-842d-5e4b576c5e15",
    "alias" : "registration form",
    "description" : "registration form",
    "providerId" : "form-flow",
    "topLevel" : false,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "registration-user-creation",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 20,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "registration-profile-action",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 40,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "registration-password-action",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 50,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "registration-recaptcha-action",
      "authenticatorFlow" : false,
      "requirement" : "DISABLED",
      "priority" : 60,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "45150fb5-aaac-4fa4-8802-fdd4742c56d8",
    "alias" : "reset credentials",
    "description" : "Reset credentials for a user if they forgot their password or something",
    "providerId" : "basic-flow",
    "topLevel" : true,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "reset-credentials-choose-user",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "reset-credential-email",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 20,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "reset-password",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 30,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticatorFlow" : true,
      "requirement" : "CONDITIONAL",
      "priority" : 40,
      "autheticatorFlow" : true,
      "flowAlias" : "Reset - Conditional OTP",
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "17f984dc-20f4-4228-8028-ed50b9bc0d68",
    "alias" : "saml ecp",
    "description" : "SAML ECP Profile Authentication Flow",
    "providerId" : "basic-flow",
    "topLevel" : true,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "http-basic-authenticator",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    } ]
  } ],
  "authenticatorConfig" : [ {
    "id" : "47d83174-c901-4421-9230-a519c175b51c",
    "alias" : "create unique user config",
    "config" : {
      "require.password.update.after.registration" : "false"
    }
  }, {
    "id" : "b637396e-a271-4438-804a-46c502936387",
    "alias" : "review profile config",
    "config" : {
      "update.profile.on.first.login" : "missing"
    }
  } ],
  "requiredActions" : [ {
    "alias" : "CONFIGURE_TOTP",
    "name" : "Configure OTP",
    "providerId" : "CONFIGURE_TOTP",
    "enabled" : true,
    "defaultAction" : false,
    "priority" : 10,
    "config" : { }
  }, {
    "alias" : "TERMS_AND_CONDITIONS",
    "name" : "Terms and Conditions",
    "providerId" : "TERMS_AND_CONDITIONS",
    "enabled" : false,
    "defaultAction" : false,
    "priority" : 20,
    "config" : { }
  }, {
    "alias" : "UPDATE_PASSWORD",
    "name" : "Update Password",
    "providerId" : "UPDATE_PASSWORD",
    "enabled" : true,
    "defaultAction" : false,
    "priority" : 30,
    "config" : { }
  }, {
    "alias" : "UPDATE_PROFILE",
    "name" : "Update Profile",
    "providerId" : "UPDATE_PROFILE",
    "enabled" : true,
    "defaultAction" : false,
    "priority" : 40,
    "config" : { }
  }, {
    "alias" : "VERIFY_EMAIL",
    "name" : "Verify Email",
    "providerId" : "VERIFY_EMAIL",
    "enabled" : true,
    "defaultAction" : false,
    "priority" : 50,
    "config" : { }
  }, {
    "alias" : "delete_account",
    "name" : "Delete Account",
    "providerId" : "delete_account",
    "enabled" : false,
    "defaultAction" : false,
    "priority" : 60,
    "config" : { }
  }, {
    "alias" : "webauthn-register",
    "name" : "Webauthn Register",
    "providerId" : "webauthn-register",
    "enabled" : true,
    "defaultAction" : false,
    "priority" : 70,
    "config" : { }
  }, {
    "alias" : "webauthn-register-passwordless",
    "name" : "Webauthn Register Passwordless",
    "providerId" : "webauthn-register-passwordless",
    "enabled" : true,
    "defaultAction" : false,
    "priority" : 80,
    "config" : { }
  }, {
    "alias" : "update_user_locale",
    "name" : "Update User Locale",
    "providerId" : "update_user_locale",
    "enabled" : true,
    "defaultAction" : false,
    "priority" : 1000,
    "config" : { }
  } ],
  "browserFlow" : "browser",
  "registrationFlow" : "registration",
  "directGrantFlow" : "direct grant",
  "resetCredentialsFlow" : "reset credentials",
  "clientAuthenticationFlow" : "clients",
  "dockerAuthenticationFlow" : "docker auth",
  "attributes" : {
    "cibaBackchannelTokenDeliveryMode" : "poll",
    "cibaAuthRequestedUserHint" : "login_hint",
    "oauth2DevicePollingInterval" : "5",
    "clientOfflineSessionMaxLifespan" : "0",
    "clientSessionIdleTimeout" : "0",
    "actionTokenGeneratedByUserLifespan-execute-actions" : "",
    "actionTokenGeneratedByUserLifespan-verify-email" : "",
    "clientOfflineSessionIdleTimeout" : "0",
    "actionTokenGeneratedByUserLifespan-reset-credentials" : "",
    "cibaInterval" : "5",
    "realmReusableOtpCode" : "false",
    "cibaExpiresIn" : "120",
    "oauth2DeviceCodeLifespan" : "600",
    "actionTokenGeneratedByUserLifespan-idp-verify-account-via-email" : "",
    "parRequestUriLifespan" : "60",
    "clientSessionMaxLifespan" : "0",
    "adminEventsExpiration" : "900",
    "shortVerificationUri" : ""
  },
  "keycloakVersion" : "22.0.4",
  "userManagedAccessAllowed" : false,
  "clientProfiles" : {
    "profiles" : [ ]
  },
  "clientPolicies" : {
    "policies" : [ ]
  }
}, {
  "id" : "049e637e-6b28-4073-9929-ab5494d2b6d2",
  "realm" : "master",
  "displayName" : "Keycloak",
  "displayNameHtml" : "<div class=\"kc-logo-text\"><span>Keycloak</span></div>",
  "notBefore" : 0,
  "defaultSignatureAlgorithm" : "RS256",
  "revokeRefreshToken" : false,
  "refreshTokenMaxReuse" : 0,
  "accessTokenLifespan" : 60,
  "accessTokenLifespanForImplicitFlow" : 900,
  "ssoSessionIdleTimeout" : 1800,
  "ssoSessionMaxLifespan" : 36000,
  "ssoSessionIdleTimeoutRememberMe" : 0,
  "ssoSessionMaxLifespanRememberMe" : 0,
  "offlineSessionIdleTimeout" : 2592000,
  "offlineSessionMaxLifespanEnabled" : false,
  "offlineSessionMaxLifespan" : 5184000,
  "clientSessionIdleTimeout" : 0,
  "clientSessionMaxLifespan" : 0,
  "clientOfflineSessionIdleTimeout" : 0,
  "clientOfflineSessionMaxLifespan" : 0,
  "accessCodeLifespan" : 60,
  "accessCodeLifespanUserAction" : 300,
  "accessCodeLifespanLogin" : 1800,
  "actionTokenGeneratedByAdminLifespan" : 43200,
  "actionTokenGeneratedByUserLifespan" : 300,
  "oauth2DeviceCodeLifespan" : 600,
  "oauth2DevicePollingInterval" : 5,
  "enabled" : true,
  "sslRequired" : "external",
  "registrationAllowed" : false,
  "registrationEmailAsUsername" : false,
  "rememberMe" : false,
  "verifyEmail" : false,
  "loginWithEmailAllowed" : true,
  "duplicateEmailsAllowed" : false,
  "resetPasswordAllowed" : false,
  "editUsernameAllowed" : false,
  "bruteForceProtected" : false,
  "permanentLockout" : false,
  "maxFailureWaitSeconds" : 900,
  "minimumQuickLoginWaitSeconds" : 60,
  "waitIncrementSeconds" : 60,
  "quickLoginCheckMilliSeconds" : 1000,
  "maxDeltaTimeSeconds" : 43200,
  "failureFactor" : 30,
  "roles" : {
    "realm" : [ {
      "id" : "0b9d0902-0f67-41ba-bd77-06eda37f74c1",
      "name" : "create-realm",
      "description" : "${role_create-realm}",
      "composite" : false,
      "clientRole" : false,
      "containerId" : "049e637e-6b28-4073-9929-ab5494d2b6d2",
      "attributes" : { }
    }, {
      "id" : "99088bcd-809a-4419-b7e5-246cfa55d7df",
      "name" : "offline_access",
      "description" : "${role_offline-access}",
      "composite" : false,
      "clientRole" : false,
      "containerId" : "049e637e-6b28-4073-9929-ab5494d2b6d2",
      "attributes" : { }
    }, {
      "id" : "e736e20c-e7c7-4b97-bd66-074b8c2f0d91",
      "name" : "admin",
      "description" : "${role_admin}",
      "composite" : true,
      "composites" : {
        "realm" : [ "create-realm" ],
        "client" : {
          "cardano_explorer-realm" : [ "query-clients", "manage-identity-providers", "view-clients", "view-realm", "manage-realm", "manage-events", "query-groups", "create-client", "query-realms", "manage-users", "impersonation", "manage-authorization", "view-users", "manage-clients", "view-events", "query-users", "view-authorization", "view-identity-providers" ],
          "master-realm" : [ "manage-clients", "create-client", "query-users", "manage-authorization", "view-realm", "view-identity-providers", "manage-events", "impersonation", "manage-realm", "query-groups", "manage-identity-providers", "query-realms", "query-clients", "view-users", "view-authorization", "view-events", "manage-users", "view-clients" ]
        }
      },
      "clientRole" : false,
      "containerId" : "049e637e-6b28-4073-9929-ab5494d2b6d2",
      "attributes" : { }
    }, {
      "id" : "a44bffba-d9c8-43aa-8aa9-aee6238bde3d",
      "name" : "default-roles-master",
      "description" : "${role_default-roles}",
      "composite" : true,
      "composites" : {
        "realm" : [ "offline_access", "uma_authorization" ],
        "client" : {
          "account" : [ "view-profile", "manage-account" ]
        }
      },
      "clientRole" : false,
      "containerId" : "049e637e-6b28-4073-9929-ab5494d2b6d2",
      "attributes" : { }
    }, {
      "id" : "482d4cc7-b079-4898-8541-88894d451d9e",
      "name" : "uma_authorization",
      "description" : "${role_uma_authorization}",
      "composite" : false,
      "clientRole" : false,
      "containerId" : "049e637e-6b28-4073-9929-ab5494d2b6d2",
      "attributes" : { }
    } ],
    "client" : {
      "security-admin-console" : [ ],
      "cardano_explorer-realm" : [ {
        "id" : "60f4ef96-8894-49aa-8c39-e470835fb349",
        "name" : "query-realms",
        "description" : "${role_query-realms}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "3431d139-1ae0-4ce9-b051-612867928c41",
        "attributes" : { }
      }, {
        "id" : "7141651a-05f4-47fe-81f7-d4eb63e54ca2",
        "name" : "manage-users",
        "description" : "${role_manage-users}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "3431d139-1ae0-4ce9-b051-612867928c41",
        "attributes" : { }
      }, {
        "id" : "de488c9f-df57-46d5-88fb-656ff250ac95",
        "name" : "query-clients",
        "description" : "${role_query-clients}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "3431d139-1ae0-4ce9-b051-612867928c41",
        "attributes" : { }
      }, {
        "id" : "53eaaa98-1e51-43c8-955e-44f7e912d66e",
        "name" : "manage-identity-providers",
        "description" : "${role_manage-identity-providers}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "3431d139-1ae0-4ce9-b051-612867928c41",
        "attributes" : { }
      }, {
        "id" : "bc19d36a-59f7-4351-af40-151312e7e613",
        "name" : "impersonation",
        "description" : "${role_impersonation}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "3431d139-1ae0-4ce9-b051-612867928c41",
        "attributes" : { }
      }, {
        "id" : "6c9c4560-5dc0-45db-b68c-1e67a7770949",
        "name" : "view-clients",
        "description" : "${role_view-clients}",
        "composite" : true,
        "composites" : {
          "client" : {
            "cardano_explorer-realm" : [ "query-clients" ]
          }
        },
        "clientRole" : true,
        "containerId" : "3431d139-1ae0-4ce9-b051-612867928c41",
        "attributes" : { }
      }, {
        "id" : "7a69e547-264b-408d-8c76-f1812d7b6fbc",
        "name" : "manage-authorization",
        "description" : "${role_manage-authorization}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "3431d139-1ae0-4ce9-b051-612867928c41",
        "attributes" : { }
      }, {
        "id" : "4adc59df-283c-4109-ba9c-aebef6ff9cf5",
        "name" : "view-realm",
        "description" : "${role_view-realm}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "3431d139-1ae0-4ce9-b051-612867928c41",
        "attributes" : { }
      }, {
        "id" : "b3ce5d5f-4cf4-412f-b873-e461eb64dad0",
        "name" : "manage-clients",
        "description" : "${role_manage-clients}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "3431d139-1ae0-4ce9-b051-612867928c41",
        "attributes" : { }
      }, {
        "id" : "6b504f2c-4de9-49d1-b282-9a2956b32cea",
        "name" : "view-users",
        "description" : "${role_view-users}",
        "composite" : true,
        "composites" : {
          "client" : {
            "cardano_explorer-realm" : [ "query-users", "query-groups" ]
          }
        },
        "clientRole" : true,
        "containerId" : "3431d139-1ae0-4ce9-b051-612867928c41",
        "attributes" : { }
      }, {
        "id" : "ea650265-8ddd-46f7-b19a-e5aedb16982f",
        "name" : "manage-realm",
        "description" : "${role_manage-realm}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "3431d139-1ae0-4ce9-b051-612867928c41",
        "attributes" : { }
      }, {
        "id" : "bb70ff29-616d-4b3b-9658-60dc89c44dee",
        "name" : "query-users",
        "description" : "${role_query-users}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "3431d139-1ae0-4ce9-b051-612867928c41",
        "attributes" : { }
      }, {
        "id" : "8103859b-4974-42d9-8821-3ffdfa4982e3",
        "name" : "view-events",
        "description" : "${role_view-events}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "3431d139-1ae0-4ce9-b051-612867928c41",
        "attributes" : { }
      }, {
        "id" : "fbb6635e-c85a-4f21-bebf-01b11f1d0720",
        "name" : "manage-events",
        "description" : "${role_manage-events}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "3431d139-1ae0-4ce9-b051-612867928c41",
        "attributes" : { }
      }, {
        "id" : "628b049b-ee13-45b4-a104-a4f30f418437",
        "name" : "query-groups",
        "description" : "${role_query-groups}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "3431d139-1ae0-4ce9-b051-612867928c41",
        "attributes" : { }
      }, {
        "id" : "14f65a1e-c505-4147-8b08-48b2c82d714c",
        "name" : "view-authorization",
        "description" : "${role_view-authorization}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "3431d139-1ae0-4ce9-b051-612867928c41",
        "attributes" : { }
      }, {
        "id" : "b2eeeab7-89dc-47b9-a696-c92d48d9f837",
        "name" : "view-identity-providers",
        "description" : "${role_view-identity-providers}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "3431d139-1ae0-4ce9-b051-612867928c41",
        "attributes" : { }
      }, {
        "id" : "d0f13d25-db17-40f3-a923-e7708c8371a6",
        "name" : "create-client",
        "description" : "${role_create-client}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "3431d139-1ae0-4ce9-b051-612867928c41",
        "attributes" : { }
      } ],
      "admin-cli" : [ ],
      "account-console" : [ ],
      "broker" : [ {
        "id" : "c16a3ee7-472b-4996-a029-c19a4efd2ffc",
        "name" : "read-token",
        "description" : "${role_read-token}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "6099e1db-4454-4a73-97f9-e71a64493aca",
        "attributes" : { }
      } ],
      "master-realm" : [ {
        "id" : "9fb96f4a-0f0e-499c-baa2-24d4a0095eb9",
        "name" : "manage-clients",
        "description" : "${role_manage-clients}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "a944f161-e490-4a54-93a1-7c318a3d6109",
        "attributes" : { }
      }, {
        "id" : "ebb66d4f-839a-4325-a40e-56594230966f",
        "name" : "create-client",
        "description" : "${role_create-client}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "a944f161-e490-4a54-93a1-7c318a3d6109",
        "attributes" : { }
      }, {
        "id" : "ea083a9a-62f9-4c61-b4b1-86c8e01d1344",
        "name" : "query-realms",
        "description" : "${role_query-realms}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "a944f161-e490-4a54-93a1-7c318a3d6109",
        "attributes" : { }
      }, {
        "id" : "c32bac20-9732-4d69-80c0-93a37c3ec566",
        "name" : "query-users",
        "description" : "${role_query-users}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "a944f161-e490-4a54-93a1-7c318a3d6109",
        "attributes" : { }
      }, {
        "id" : "765be206-abd8-49ee-a764-5adb7a8186b4",
        "name" : "manage-authorization",
        "description" : "${role_manage-authorization}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "a944f161-e490-4a54-93a1-7c318a3d6109",
        "attributes" : { }
      }, {
        "id" : "c7eaef33-9494-414b-9a40-6b6e980d6744",
        "name" : "query-clients",
        "description" : "${role_query-clients}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "a944f161-e490-4a54-93a1-7c318a3d6109",
        "attributes" : { }
      }, {
        "id" : "a364bb32-3380-41d5-8077-a6b023cdb39c",
        "name" : "view-realm",
        "description" : "${role_view-realm}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "a944f161-e490-4a54-93a1-7c318a3d6109",
        "attributes" : { }
      }, {
        "id" : "a649963f-6d3d-4929-b056-7d83027da343",
        "name" : "view-identity-providers",
        "description" : "${role_view-identity-providers}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "a944f161-e490-4a54-93a1-7c318a3d6109",
        "attributes" : { }
      }, {
        "id" : "73beab4a-8af4-4175-8912-6712996dd6ba",
        "name" : "manage-events",
        "description" : "${role_manage-events}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "a944f161-e490-4a54-93a1-7c318a3d6109",
        "attributes" : { }
      }, {
        "id" : "06bfabbe-370b-42b7-a59d-b93c8c635cde",
        "name" : "view-users",
        "description" : "${role_view-users}",
        "composite" : true,
        "composites" : {
          "client" : {
            "master-realm" : [ "query-users", "query-groups" ]
          }
        },
        "clientRole" : true,
        "containerId" : "a944f161-e490-4a54-93a1-7c318a3d6109",
        "attributes" : { }
      }, {
        "id" : "25e0b55d-b8cd-4fbe-adfb-e9c11d52c0c3",
        "name" : "view-authorization",
        "description" : "${role_view-authorization}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "a944f161-e490-4a54-93a1-7c318a3d6109",
        "attributes" : { }
      }, {
        "id" : "0b9628f1-83af-4987-9edf-35047c44c15f",
        "name" : "impersonation",
        "description" : "${role_impersonation}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "a944f161-e490-4a54-93a1-7c318a3d6109",
        "attributes" : { }
      }, {
        "id" : "21cb8791-8729-46fd-b809-b6427df4d91a",
        "name" : "manage-users",
        "description" : "${role_manage-users}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "a944f161-e490-4a54-93a1-7c318a3d6109",
        "attributes" : { }
      }, {
        "id" : "44580d07-22df-4709-b1ca-ae1892047a63",
        "name" : "view-events",
        "description" : "${role_view-events}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "a944f161-e490-4a54-93a1-7c318a3d6109",
        "attributes" : { }
      }, {
        "id" : "1803c8fa-a312-431a-8586-fe46695efdf6",
        "name" : "manage-realm",
        "description" : "${role_manage-realm}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "a944f161-e490-4a54-93a1-7c318a3d6109",
        "attributes" : { }
      }, {
        "id" : "9d7b997a-a5af-487b-a760-d5b20407e50f",
        "name" : "query-groups",
        "description" : "${role_query-groups}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "a944f161-e490-4a54-93a1-7c318a3d6109",
        "attributes" : { }
      }, {
        "id" : "d618efd0-6fbd-4bda-97fa-1d1d167be1e3",
        "name" : "view-clients",
        "description" : "${role_view-clients}",
        "composite" : true,
        "composites" : {
          "client" : {
            "master-realm" : [ "query-clients" ]
          }
        },
        "clientRole" : true,
        "containerId" : "a944f161-e490-4a54-93a1-7c318a3d6109",
        "attributes" : { }
      }, {
        "id" : "e5ab22c0-5031-4c01-b061-d912817ee77e",
        "name" : "manage-identity-providers",
        "description" : "${role_manage-identity-providers}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "a944f161-e490-4a54-93a1-7c318a3d6109",
        "attributes" : { }
      } ],
      "account" : [ {
        "id" : "3cac588a-09ad-499a-a1c0-3cdbb0401d44",
        "name" : "view-groups",
        "description" : "${role_view-groups}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "0628fb29-333c-43ae-ab75-f402f7b5b6dc",
        "attributes" : { }
      }, {
        "id" : "20ae68ad-3ba3-4365-915b-fd424adac913",
        "name" : "view-profile",
        "description" : "${role_view-profile}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "0628fb29-333c-43ae-ab75-f402f7b5b6dc",
        "attributes" : { }
      }, {
        "id" : "8c2cc099-90e7-4d0f-b0d3-76c4e72e8a9f",
        "name" : "manage-account-links",
        "description" : "${role_manage-account-links}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "0628fb29-333c-43ae-ab75-f402f7b5b6dc",
        "attributes" : { }
      }, {
        "id" : "232e01b6-da32-4918-95b6-3da54808e017",
        "name" : "manage-consent",
        "description" : "${role_manage-consent}",
        "composite" : true,
        "composites" : {
          "client" : {
            "account" : [ "view-consent" ]
          }
        },
        "clientRole" : true,
        "containerId" : "0628fb29-333c-43ae-ab75-f402f7b5b6dc",
        "attributes" : { }
      }, {
        "id" : "4d854782-d5ce-48df-b295-cf665e2b79b5",
        "name" : "delete-account",
        "description" : "${role_delete-account}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "0628fb29-333c-43ae-ab75-f402f7b5b6dc",
        "attributes" : { }
      }, {
        "id" : "47bf871a-f497-44c8-ac08-cb1125e54711",
        "name" : "manage-account",
        "description" : "${role_manage-account}",
        "composite" : true,
        "composites" : {
          "client" : {
            "account" : [ "manage-account-links" ]
          }
        },
        "clientRole" : true,
        "containerId" : "0628fb29-333c-43ae-ab75-f402f7b5b6dc",
        "attributes" : { }
      }, {
        "id" : "c078243b-5031-4463-861c-d72796ffeb0c",
        "name" : "view-consent",
        "description" : "${role_view-consent}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "0628fb29-333c-43ae-ab75-f402f7b5b6dc",
        "attributes" : { }
      }, {
        "id" : "88cb2121-84d6-413f-8ef0-a802ebaf5eac",
        "name" : "view-applications",
        "description" : "${role_view-applications}",
        "composite" : false,
        "clientRole" : true,
        "containerId" : "0628fb29-333c-43ae-ab75-f402f7b5b6dc",
        "attributes" : { }
      } ]
    }
  },
  "groups" : [ ],
  "defaultRole" : {
    "id" : "a44bffba-d9c8-43aa-8aa9-aee6238bde3d",
    "name" : "default-roles-master",
    "description" : "${role_default-roles}",
    "composite" : true,
    "clientRole" : false,
    "containerId" : "049e637e-6b28-4073-9929-ab5494d2b6d2"
  },
  "requiredCredentials" : [ "password" ],
  "otpPolicyType" : "totp",
  "otpPolicyAlgorithm" : "HmacSHA1",
  "otpPolicyInitialCounter" : 0,
  "otpPolicyDigits" : 6,
  "otpPolicyLookAheadWindow" : 1,
  "otpPolicyPeriod" : 30,
  "otpPolicyCodeReusable" : false,
  "otpSupportedApplications" : [ "totpAppMicrosoftAuthenticatorName", "totpAppFreeOTPName", "totpAppGoogleName" ],
  "webAuthnPolicyRpEntityName" : "keycloak",
  "webAuthnPolicySignatureAlgorithms" : [ "ES256" ],
  "webAuthnPolicyRpId" : "",
  "webAuthnPolicyAttestationConveyancePreference" : "not specified",
  "webAuthnPolicyAuthenticatorAttachment" : "not specified",
  "webAuthnPolicyRequireResidentKey" : "not specified",
  "webAuthnPolicyUserVerificationRequirement" : "not specified",
  "webAuthnPolicyCreateTimeout" : 0,
  "webAuthnPolicyAvoidSameAuthenticatorRegister" : false,
  "webAuthnPolicyAcceptableAaguids" : [ ],
  "webAuthnPolicyPasswordlessRpEntityName" : "keycloak",
  "webAuthnPolicyPasswordlessSignatureAlgorithms" : [ "ES256" ],
  "webAuthnPolicyPasswordlessRpId" : "",
  "webAuthnPolicyPasswordlessAttestationConveyancePreference" : "not specified",
  "webAuthnPolicyPasswordlessAuthenticatorAttachment" : "not specified",
  "webAuthnPolicyPasswordlessRequireResidentKey" : "not specified",
  "webAuthnPolicyPasswordlessUserVerificationRequirement" : "not specified",
  "webAuthnPolicyPasswordlessCreateTimeout" : 0,
  "webAuthnPolicyPasswordlessAvoidSameAuthenticatorRegister" : false,
  "webAuthnPolicyPasswordlessAcceptableAaguids" : [ ],
  "users" : [ {
    "id" : "b6012192-75c2-488e-b76b-bdd487bc7315",
    "createdTimestamp" : 1699281486619,
    "username" : "admin",
    "enabled" : true,
    "totp" : false,
    "emailVerified" : false,
    "credentials" : [ {
      "id" : "3b800dbd-bcfb-4734-925f-85a9f75f1cef",
      "type" : "password",
      "createdDate" : 1699281486748,
      "secretData" : "{\"value\":\"UfR+hbLE0/ckGud1KvPj3MVRO0IoqWce5O8txRQ6r0w=\",\"salt\":\"LKbGAWUZgdTb8SobErxyqg==\",\"additionalParameters\":{}}",
      "credentialData" : "{\"hashIterations\":27500,\"algorithm\":\"pbkdf2-sha256\",\"additionalParameters\":{}}"
    } ],
    "disableableCredentialTypes" : [ ],
    "requiredActions" : [ ],
    "realmRoles" : [ "admin", "default-roles-master" ],
    "clientRoles" : {
      "cardano_explorer-realm" : [ "query-realms", "manage-users", "query-clients", "manage-identity-providers", "view-clients", "view-realm", "manage-authorization", "view-users", "manage-clients", "view-events", "manage-realm", "query-users", "view-authorization", "manage-events", "query-groups", "view-identity-providers", "create-client" ]
    },
    "notBefore" : 0,
    "groups" : [ ]
  } ],
  "scopeMappings" : [ {
    "clientScope" : "offline_access",
    "roles" : [ "offline_access" ]
  } ],
  "clientScopeMappings" : {
    "account" : [ {
      "client" : "account-console",
      "roles" : [ "manage-account", "view-groups" ]
    } ]
  },
  "clients" : [ {
    "id" : "0628fb29-333c-43ae-ab75-f402f7b5b6dc",
    "clientId" : "account",
    "name" : "${client_account}",
    "rootUrl" : "${authBaseUrl}",
    "baseUrl" : "/realms/master/account/",
    "surrogateAuthRequired" : false,
    "enabled" : true,
    "alwaysDisplayInConsole" : false,
    "clientAuthenticatorType" : "client-secret",
    "redirectUris" : [ "/realms/master/account/*" ],
    "webOrigins" : [ ],
    "notBefore" : 0,
    "bearerOnly" : false,
    "consentRequired" : false,
    "standardFlowEnabled" : true,
    "implicitFlowEnabled" : false,
    "directAccessGrantsEnabled" : false,
    "serviceAccountsEnabled" : false,
    "publicClient" : true,
    "frontchannelLogout" : false,
    "protocol" : "openid-connect",
    "attributes" : {
      "post.logout.redirect.uris" : "+"
    },
    "authenticationFlowBindingOverrides" : { },
    "fullScopeAllowed" : false,
    "nodeReRegistrationTimeout" : 0,
    "defaultClientScopes" : [ "web-origins", "acr", "roles", "profile", "email" ],
    "optionalClientScopes" : [ "address", "phone", "offline_access", "microprofile-jwt" ]
  }, {
    "id" : "7bf7aa4f-bd63-4102-a45d-1c3e9e9e1af6",
    "clientId" : "account-console",
    "name" : "${client_account-console}",
    "rootUrl" : "${authBaseUrl}",
    "baseUrl" : "/realms/master/account/",
    "surrogateAuthRequired" : false,
    "enabled" : true,
    "alwaysDisplayInConsole" : false,
    "clientAuthenticatorType" : "client-secret",
    "redirectUris" : [ "/realms/master/account/*" ],
    "webOrigins" : [ ],
    "notBefore" : 0,
    "bearerOnly" : false,
    "consentRequired" : false,
    "standardFlowEnabled" : true,
    "implicitFlowEnabled" : false,
    "directAccessGrantsEnabled" : false,
    "serviceAccountsEnabled" : false,
    "publicClient" : true,
    "frontchannelLogout" : false,
    "protocol" : "openid-connect",
    "attributes" : {
      "post.logout.redirect.uris" : "+",
      "pkce.code.challenge.method" : "S256"
    },
    "authenticationFlowBindingOverrides" : { },
    "fullScopeAllowed" : false,
    "nodeReRegistrationTimeout" : 0,
    "protocolMappers" : [ {
      "id" : "f298c1b4-9a99-4360-99a6-83688ccd91e2",
      "name" : "audience resolve",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-audience-resolve-mapper",
      "consentRequired" : false,
      "config" : { }
    } ],
    "defaultClientScopes" : [ "web-origins", "acr", "roles", "profile", "email" ],
    "optionalClientScopes" : [ "address", "phone", "offline_access", "microprofile-jwt" ]
  }, {
    "id" : "f083a244-734a-4fe7-8e55-2fc9f0c5b629",
    "clientId" : "admin-cli",
    "name" : "${client_admin-cli}",
    "surrogateAuthRequired" : false,
    "enabled" : true,
    "alwaysDisplayInConsole" : false,
    "clientAuthenticatorType" : "client-secret",
    "redirectUris" : [ ],
    "webOrigins" : [ ],
    "notBefore" : 0,
    "bearerOnly" : false,
    "consentRequired" : false,
    "standardFlowEnabled" : false,
    "implicitFlowEnabled" : false,
    "directAccessGrantsEnabled" : true,
    "serviceAccountsEnabled" : false,
    "publicClient" : true,
    "frontchannelLogout" : false,
    "protocol" : "openid-connect",
    "attributes" : { },
    "authenticationFlowBindingOverrides" : { },
    "fullScopeAllowed" : false,
    "nodeReRegistrationTimeout" : 0,
    "defaultClientScopes" : [ "web-origins", "acr", "roles", "profile", "email" ],
    "optionalClientScopes" : [ "address", "phone", "offline_access", "microprofile-jwt" ]
  }, {
    "id" : "6099e1db-4454-4a73-97f9-e71a64493aca",
    "clientId" : "broker",
    "name" : "${client_broker}",
    "surrogateAuthRequired" : false,
    "enabled" : true,
    "alwaysDisplayInConsole" : false,
    "clientAuthenticatorType" : "client-secret",
    "redirectUris" : [ ],
    "webOrigins" : [ ],
    "notBefore" : 0,
    "bearerOnly" : true,
    "consentRequired" : false,
    "standardFlowEnabled" : true,
    "implicitFlowEnabled" : false,
    "directAccessGrantsEnabled" : false,
    "serviceAccountsEnabled" : false,
    "publicClient" : false,
    "frontchannelLogout" : false,
    "protocol" : "openid-connect",
    "attributes" : { },
    "authenticationFlowBindingOverrides" : { },
    "fullScopeAllowed" : false,
    "nodeReRegistrationTimeout" : 0,
    "defaultClientScopes" : [ "web-origins", "acr", "roles", "profile", "email" ],
    "optionalClientScopes" : [ "address", "phone", "offline_access", "microprofile-jwt" ]
  }, {
    "id" : "3431d139-1ae0-4ce9-b051-612867928c41",
    "clientId" : "cardano_explorer-realm",
    "name" : "cardano_explorer Realm",
    "surrogateAuthRequired" : false,
    "enabled" : true,
    "alwaysDisplayInConsole" : false,
    "clientAuthenticatorType" : "client-secret",
    "redirectUris" : [ ],
    "webOrigins" : [ ],
    "notBefore" : 0,
    "bearerOnly" : true,
    "consentRequired" : false,
    "standardFlowEnabled" : true,
    "implicitFlowEnabled" : false,
    "directAccessGrantsEnabled" : false,
    "serviceAccountsEnabled" : false,
    "publicClient" : false,
    "frontchannelLogout" : false,
    "attributes" : { },
    "authenticationFlowBindingOverrides" : { },
    "fullScopeAllowed" : false,
    "nodeReRegistrationTimeout" : 0,
    "defaultClientScopes" : [ ],
    "optionalClientScopes" : [ ]
  }, {
    "id" : "a944f161-e490-4a54-93a1-7c318a3d6109",
    "clientId" : "master-realm",
    "name" : "master Realm",
    "surrogateAuthRequired" : false,
    "enabled" : true,
    "alwaysDisplayInConsole" : false,
    "clientAuthenticatorType" : "client-secret",
    "redirectUris" : [ ],
    "webOrigins" : [ ],
    "notBefore" : 0,
    "bearerOnly" : true,
    "consentRequired" : false,
    "standardFlowEnabled" : true,
    "implicitFlowEnabled" : false,
    "directAccessGrantsEnabled" : false,
    "serviceAccountsEnabled" : false,
    "publicClient" : false,
    "frontchannelLogout" : false,
    "attributes" : { },
    "authenticationFlowBindingOverrides" : { },
    "fullScopeAllowed" : false,
    "nodeReRegistrationTimeout" : 0,
    "defaultClientScopes" : [ "web-origins", "acr", "roles", "profile", "email" ],
    "optionalClientScopes" : [ "address", "phone", "offline_access", "microprofile-jwt" ]
  }, {
    "id" : "2037ddcb-8730-47ab-9c4c-a64917437a46",
    "clientId" : "security-admin-console",
    "name" : "${client_security-admin-console}",
    "rootUrl" : "${authAdminUrl}",
    "baseUrl" : "/admin/master/console/",
    "surrogateAuthRequired" : false,
    "enabled" : true,
    "alwaysDisplayInConsole" : false,
    "clientAuthenticatorType" : "client-secret",
    "redirectUris" : [ "/admin/master/console/*" ],
    "webOrigins" : [ "+" ],
    "notBefore" : 0,
    "bearerOnly" : false,
    "consentRequired" : false,
    "standardFlowEnabled" : true,
    "implicitFlowEnabled" : false,
    "directAccessGrantsEnabled" : false,
    "serviceAccountsEnabled" : false,
    "publicClient" : true,
    "frontchannelLogout" : false,
    "protocol" : "openid-connect",
    "attributes" : {
      "post.logout.redirect.uris" : "+",
      "pkce.code.challenge.method" : "S256"
    },
    "authenticationFlowBindingOverrides" : { },
    "fullScopeAllowed" : false,
    "nodeReRegistrationTimeout" : 0,
    "protocolMappers" : [ {
      "id" : "d5e9c30e-a230-452f-bae0-20255fcc3523",
      "name" : "locale",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "locale",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "locale",
        "jsonType.label" : "String"
      }
    } ],
    "defaultClientScopes" : [ "web-origins", "acr", "roles", "profile", "email" ],
    "optionalClientScopes" : [ "address", "phone", "offline_access", "microprofile-jwt" ]
  } ],
  "clientScopes" : [ {
    "id" : "fd7cfd67-b50a-4d6f-a41e-3e0a66507762",
    "name" : "email",
    "description" : "OpenID Connect built-in scope: email",
    "protocol" : "openid-connect",
    "attributes" : {
      "include.in.token.scope" : "true",
      "display.on.consent.screen" : "true",
      "consent.screen.text" : "${emailScopeConsentText}"
    },
    "protocolMappers" : [ {
      "id" : "095bdd4c-b830-4fbb-821d-4c62b2c9379e",
      "name" : "email verified",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-property-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "emailVerified",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "email_verified",
        "jsonType.label" : "boolean"
      }
    }, {
      "id" : "757e3226-879f-46c9-b093-590065754919",
      "name" : "email",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "email",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "email",
        "jsonType.label" : "String"
      }
    } ]
  }, {
    "id" : "469199ac-9687-43bc-afa3-a3ae944baf39",
    "name" : "roles",
    "description" : "OpenID Connect scope for add user roles to the access token",
    "protocol" : "openid-connect",
    "attributes" : {
      "include.in.token.scope" : "false",
      "display.on.consent.screen" : "true",
      "consent.screen.text" : "${rolesScopeConsentText}"
    },
    "protocolMappers" : [ {
      "id" : "b00549d8-7797-4f41-ab94-26e113e4e031",
      "name" : "client roles",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-client-role-mapper",
      "consentRequired" : false,
      "config" : {
        "user.attribute" : "foo",
        "access.token.claim" : "true",
        "claim.name" : "resource_access.${client_id}.roles",
        "jsonType.label" : "String",
        "multivalued" : "true"
      }
    }, {
      "id" : "31ef32a9-3149-4bb3-8b68-263825cb416c",
      "name" : "audience resolve",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-audience-resolve-mapper",
      "consentRequired" : false,
      "config" : { }
    }, {
      "id" : "6f5f46e1-2235-4499-9afc-93e192c02500",
      "name" : "realm roles",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-realm-role-mapper",
      "consentRequired" : false,
      "config" : {
        "user.attribute" : "foo",
        "access.token.claim" : "true",
        "claim.name" : "realm_access.roles",
        "jsonType.label" : "String",
        "multivalued" : "true"
      }
    } ]
  }, {
    "id" : "3c2bebc5-6a68-42e8-8496-a30dc3f07d0a",
    "name" : "offline_access",
    "description" : "OpenID Connect built-in scope: offline_access",
    "protocol" : "openid-connect",
    "attributes" : {
      "consent.screen.text" : "${offlineAccessScopeConsentText}",
      "display.on.consent.screen" : "true"
    }
  }, {
    "id" : "cd0590a8-c643-442e-9bf6-1bb27f3ee816",
    "name" : "web-origins",
    "description" : "OpenID Connect scope for add allowed web origins to the access token",
    "protocol" : "openid-connect",
    "attributes" : {
      "include.in.token.scope" : "false",
      "display.on.consent.screen" : "false",
      "consent.screen.text" : ""
    },
    "protocolMappers" : [ {
      "id" : "76c7864e-fcc3-4b37-8b8c-02d17ae50208",
      "name" : "allowed web origins",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-allowed-origins-mapper",
      "consentRequired" : false,
      "config" : { }
    } ]
  }, {
    "id" : "c77b23c0-a9ba-4887-9f32-90720ea12d5b",
    "name" : "role_list",
    "description" : "SAML role list",
    "protocol" : "saml",
    "attributes" : {
      "consent.screen.text" : "${samlRoleListScopeConsentText}",
      "display.on.consent.screen" : "true"
    },
    "protocolMappers" : [ {
      "id" : "8fd5e735-9dd7-4c36-ba17-5a3674ccddb1",
      "name" : "role list",
      "protocol" : "saml",
      "protocolMapper" : "saml-role-list-mapper",
      "consentRequired" : false,
      "config" : {
        "single" : "false",
        "attribute.nameformat" : "Basic",
        "attribute.name" : "Role"
      }
    } ]
  }, {
    "id" : "14e3bed0-edde-4f8b-8d06-01d49160cf8e",
    "name" : "profile",
    "description" : "OpenID Connect built-in scope: profile",
    "protocol" : "openid-connect",
    "attributes" : {
      "include.in.token.scope" : "true",
      "display.on.consent.screen" : "true",
      "consent.screen.text" : "${profileScopeConsentText}"
    },
    "protocolMappers" : [ {
      "id" : "5d839dec-076f-41f2-992b-ff1270b4e2e4",
      "name" : "username",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "username",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "preferred_username",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "74065cac-6176-4556-89a8-45e0d80a80da",
      "name" : "full name",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-full-name-mapper",
      "consentRequired" : false,
      "config" : {
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "userinfo.token.claim" : "true"
      }
    }, {
      "id" : "566f80c7-2b01-4555-8f8b-57391d74c385",
      "name" : "middle name",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "middleName",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "middle_name",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "50fcd847-ef24-4509-affb-621241ba6dba",
      "name" : "updated at",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "updatedAt",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "updated_at",
        "jsonType.label" : "long"
      }
    }, {
      "id" : "2ff62f0b-f61e-4969-b5a2-9c50cf285d80",
      "name" : "profile",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "profile",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "profile",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "7df8dc78-ddcb-4c2d-baf3-36d3dbb9d49c",
      "name" : "gender",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "gender",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "gender",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "2a3147a6-f225-491e-bd4f-7fadbc498b0f",
      "name" : "website",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "website",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "website",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "09f79448-4fb8-4844-a7eb-484ba8359453",
      "name" : "locale",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "locale",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "locale",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "d066d08d-c857-417d-8cb4-84aac66bdeb1",
      "name" : "given name",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "firstName",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "given_name",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "9da07225-e470-4ea4-b101-e5da8f4c8f8f",
      "name" : "family name",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "lastName",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "family_name",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "1077afc6-f3f0-4028-b8fe-5b36f11e6f4a",
      "name" : "nickname",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "nickname",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "nickname",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "89ea90b1-6df2-43f0-b8e6-7ceaa2a4323e",
      "name" : "birthdate",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "birthdate",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "birthdate",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "e6d6457a-7e39-425a-bdeb-b04d89993569",
      "name" : "picture",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "picture",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "picture",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "3422e167-2b0f-4007-b173-e0b6ec15598a",
      "name" : "zoneinfo",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "zoneinfo",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "zoneinfo",
        "jsonType.label" : "String"
      }
    } ]
  }, {
    "id" : "5081f9cd-5679-4283-93fd-f200a51ffe13",
    "name" : "acr",
    "description" : "OpenID Connect scope for add acr (authentication context class reference) to the token",
    "protocol" : "openid-connect",
    "attributes" : {
      "include.in.token.scope" : "false",
      "display.on.consent.screen" : "false"
    },
    "protocolMappers" : [ {
      "id" : "87a6b610-c28e-427f-a8a8-19b8f9da9cbb",
      "name" : "acr loa level",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-acr-mapper",
      "consentRequired" : false,
      "config" : {
        "id.token.claim" : "true",
        "access.token.claim" : "true"
      }
    } ]
  }, {
    "id" : "0c79efab-0c80-456d-a8f0-6d37b6d73df3",
    "name" : "phone",
    "description" : "OpenID Connect built-in scope: phone",
    "protocol" : "openid-connect",
    "attributes" : {
      "include.in.token.scope" : "true",
      "display.on.consent.screen" : "true",
      "consent.screen.text" : "${phoneScopeConsentText}"
    },
    "protocolMappers" : [ {
      "id" : "71a7af39-4635-4053-a2b1-85ffd0462803",
      "name" : "phone number",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "phoneNumber",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "phone_number",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "775935e8-a05b-44c7-9ed9-5bf199f4135b",
      "name" : "phone number verified",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "phoneNumberVerified",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "phone_number_verified",
        "jsonType.label" : "boolean"
      }
    } ]
  }, {
    "id" : "ab94a0f7-8bc7-4745-b0c6-f9063f0e6dd6",
    "name" : "microprofile-jwt",
    "description" : "Microprofile - JWT built-in scope",
    "protocol" : "openid-connect",
    "attributes" : {
      "include.in.token.scope" : "true",
      "display.on.consent.screen" : "false"
    },
    "protocolMappers" : [ {
      "id" : "16f253d9-138f-400a-aa46-4e5b91ef7376",
      "name" : "upn",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-attribute-mapper",
      "consentRequired" : false,
      "config" : {
        "userinfo.token.claim" : "true",
        "user.attribute" : "username",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "upn",
        "jsonType.label" : "String"
      }
    }, {
      "id" : "f311b745-7ebd-469f-a38b-ee53531031fb",
      "name" : "groups",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-usermodel-realm-role-mapper",
      "consentRequired" : false,
      "config" : {
        "multivalued" : "true",
        "user.attribute" : "foo",
        "id.token.claim" : "true",
        "access.token.claim" : "true",
        "claim.name" : "groups",
        "jsonType.label" : "String"
      }
    } ]
  }, {
    "id" : "30415499-c5ea-457c-a1fb-43c97b3ea859",
    "name" : "address",
    "description" : "OpenID Connect built-in scope: address",
    "protocol" : "openid-connect",
    "attributes" : {
      "include.in.token.scope" : "true",
      "display.on.consent.screen" : "true",
      "consent.screen.text" : "${addressScopeConsentText}"
    },
    "protocolMappers" : [ {
      "id" : "4766bd27-faf2-4530-8ed7-5e55bc86f9c1",
      "name" : "address",
      "protocol" : "openid-connect",
      "protocolMapper" : "oidc-address-mapper",
      "consentRequired" : false,
      "config" : {
        "user.attribute.formatted" : "formatted",
        "user.attribute.country" : "country",
        "user.attribute.postal_code" : "postal_code",
        "userinfo.token.claim" : "true",
        "user.attribute.street" : "street",
        "id.token.claim" : "true",
        "user.attribute.region" : "region",
        "access.token.claim" : "true",
        "user.attribute.locality" : "locality"
      }
    } ]
  } ],
  "defaultDefaultClientScopes" : [ "role_list", "profile", "email", "roles", "web-origins", "acr" ],
  "defaultOptionalClientScopes" : [ "offline_access", "address", "phone", "microprofile-jwt" ],
  "browserSecurityHeaders" : {
    "contentSecurityPolicyReportOnly" : "",
    "xContentTypeOptions" : "nosniff",
    "referrerPolicy" : "no-referrer",
    "xRobotsTag" : "none",
    "xFrameOptions" : "SAMEORIGIN",
    "xXSSProtection" : "1; mode=block",
    "contentSecurityPolicy" : "frame-src 'self'; frame-ancestors 'self'; object-src 'none';",
    "strictTransportSecurity" : "max-age=31536000; includeSubDomains"
  },
  "smtpServer" : { },
  "eventsEnabled" : false,
  "eventsListeners" : [ "jboss-logging" ],
  "enabledEventTypes" : [ ],
  "adminEventsEnabled" : false,
  "adminEventsDetailsEnabled" : false,
  "identityProviders" : [ ],
  "identityProviderMappers" : [ ],
  "components" : {
    "org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy" : [ {
      "id" : "ebc0f315-b61e-4ee1-af3e-026996b524d7",
      "name" : "Full Scope Disabled",
      "providerId" : "scope",
      "subType" : "anonymous",
      "subComponents" : { },
      "config" : { }
    }, {
      "id" : "4acdaa2c-4abb-46e4-bf6a-c0e1aa437abd",
      "name" : "Trusted Hosts",
      "providerId" : "trusted-hosts",
      "subType" : "anonymous",
      "subComponents" : { },
      "config" : {
        "host-sending-registration-request-must-match" : [ "true" ],
        "client-uris-must-match" : [ "true" ]
      }
    }, {
      "id" : "6347bb21-1bbc-446a-be6a-354bdc8ca19c",
      "name" : "Max Clients Limit",
      "providerId" : "max-clients",
      "subType" : "anonymous",
      "subComponents" : { },
      "config" : {
        "max-clients" : [ "200" ]
      }
    }, {
      "id" : "3c5d1b49-a298-49f0-b047-5bddedcef2ad",
      "name" : "Consent Required",
      "providerId" : "consent-required",
      "subType" : "anonymous",
      "subComponents" : { },
      "config" : { }
    }, {
      "id" : "747f9f57-a75a-49aa-a73f-7a940206fae8",
      "name" : "Allowed Protocol Mapper Types",
      "providerId" : "allowed-protocol-mappers",
      "subType" : "anonymous",
      "subComponents" : { },
      "config" : {
        "allowed-protocol-mapper-types" : [ "oidc-usermodel-attribute-mapper", "saml-user-property-mapper", "oidc-address-mapper", "oidc-usermodel-property-mapper", "saml-user-attribute-mapper", "oidc-full-name-mapper", "saml-role-list-mapper", "oidc-sha256-pairwise-sub-mapper" ]
      }
    }, {
      "id" : "3ed4194e-59f0-45de-98ba-660468b76c8e",
      "name" : "Allowed Protocol Mapper Types",
      "providerId" : "allowed-protocol-mappers",
      "subType" : "authenticated",
      "subComponents" : { },
      "config" : {
        "allowed-protocol-mapper-types" : [ "saml-user-property-mapper", "oidc-usermodel-property-mapper", "oidc-address-mapper", "oidc-full-name-mapper", "saml-user-attribute-mapper", "oidc-usermodel-attribute-mapper", "saml-role-list-mapper", "oidc-sha256-pairwise-sub-mapper" ]
      }
    }, {
      "id" : "c8af63a3-6e7e-41b8-97bb-67c3194f63d4",
      "name" : "Allowed Client Scopes",
      "providerId" : "allowed-client-templates",
      "subType" : "authenticated",
      "subComponents" : { },
      "config" : {
        "allow-default-scopes" : [ "true" ]
      }
    }, {
      "id" : "352c37a2-6e04-4a38-a160-c4ea9795ed2d",
      "name" : "Allowed Client Scopes",
      "providerId" : "allowed-client-templates",
      "subType" : "anonymous",
      "subComponents" : { },
      "config" : {
        "allow-default-scopes" : [ "true" ]
      }
    } ],
    "org.keycloak.keys.KeyProvider" : [ {
      "id" : "9ac16ece-6ea8-480e-bf9a-b47fc9a31bb1",
      "name" : "rsa-enc-generated",
      "providerId" : "rsa-enc-generated",
      "subComponents" : { },
      "config" : {
        "privateKey" : [ "MIIEogIBAAKCAQEAwxjS0J8BxLD1LKZyOz8E3ZObLwhjs/z6vEWi1rqGPCt+O6cJ3n5sMnixXHsHZ8c6CFbBRNLvaKMwGy1kOyCbxvrwM9L9NU0jFscsSLkO07ftuv3XCjsRrwsQTTzdtmm3JMzUfbrLdaMsYiN65GHFnJjWjsg6SK7KCPPauvZbcDLjvt0Ujk8obt8j99Sz2A0fMu9nDyK80ECfZDKLGj1p6sxaHxRYLjYYS2b56dlonQwPsWrbAhWzxzsHkXBm1/f3mx09CpKfvVoY9qRRMk6ofT2fVaWzmm1Fow2EfmVFTSsBlIZHGX3sXlNs4RdWSAvZXF8MeKps7SiXy9s6YtfGcQIDAQABAoIBADMkQTL9041XY9WiBiTAtspMpKvWE6i9Yo7S5EZMm96O+iax+VTRKBSs7bVT7NOH9o70CvK7JpXz3SrqobEcMamd4zR6lPSRdWbFrDaeCCR4vQE9BE9Cv7Z2fH36vBXSrXcgmH0i5629KefJ7C+M76vGpsvD8f9WjlB9d/N1m++TelC099RnWdiP2D9cXtW0FNi3oq0Hh3FDXjZRJBj9Ola11i/L906NxZF1Flz9hOYTPXXsJEERD+7nmijlkc70VwcHS7pVGhHLUUJJbgwlJgRCHdodeTfYMrDknSK8/+Ob5VA+x5HUvnRETcw1ji7AdOtZLC8k3xm1FTPgftHi+n8CgYEA4uV18yHJdn2zpr5o/kjLCKMuHd0l8cVrNKdcAiGaO9CjzbTuDBulWdINGiT8ynV43CImUfELsqTG32snQhBEtY5lx/moUItnia3mginiu0pWRzpdH1agz5t6BkQdIqJOQw9Vu7aH3h6X3uWy4ClWjvp6z/o+krKWpPj3aQ/RqA8CgYEA3B8swOL2x1luxa4AL0YX13i/Vd3UC6LJcagY7CN/Jp9gAYItC8W3oKzTb4PVXrASDLs9jxOCDP8k5wATLMY81/m2YWyMCV5J3d99TXxaKV1uN0C3+GAuBylFdB6Er4l2vL6xbDYFZHnpYC6eU1IG29TJvbFBwnPKAHWRAdhlKX8CgYBpLrBAFqCbImie2tQ3VS1yApuFUpidfMRW82KOxl8GbZER8lcMG+DKQ7q5LP5XKJ/vhJE5GUIv/X0H41eeo4YGmE2Mp6qkNAu6YnhiTOKDqlFW0bKLrY/8zfmCYB8ViCI5qik3TMWtbYc/2EHU8rTisTEvqrVJMirtrVCcTq4FdwKBgAfEzeaM3BrTDDbL2lPQzHydVVtpdddnSmbJgP+sjQtG6rOC7aYRt1ZjYx73bytlWW27mgOXs6pTwvCoLwUQhgs1U/uCQjr+aer7vUdj0SbOllo63YNVmapFCZ98iEBNwLQOouJn0a7nIpZzbsEQDw/pJamdPO3o+kMj4xdGlONvAoGAJewbEx+W3nq3emy1oF4/ZLoq9riqt4cT5qfxOo4G/fgmxjsgTjB2zRrDsF/N36/x6FgoZrihFTmvZ1giDajwE2aJtu+KBwg+o7Yq/FCIPOcy2EGsO+Y4S/gYzsDaLRtroGZXWCXhaJukC53C4UnFV+071caPG7co5dZOimCT5EA=" ],
        "keyUse" : [ "ENC" ],
        "certificate" : [ "MIICmzCCAYMCBgGLpRHB2TANBgkqhkiG9w0BAQsFADARMQ8wDQYDVQQDDAZtYXN0ZXIwHhcNMjMxMTA2MTQzNjI2WhcNMzMxMTA2MTQzODA2WjARMQ8wDQYDVQQDDAZtYXN0ZXIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDDGNLQnwHEsPUspnI7PwTdk5svCGOz/Pq8RaLWuoY8K347pwnefmwyeLFcewdnxzoIVsFE0u9oozAbLWQ7IJvG+vAz0v01TSMWxyxIuQ7Tt+26/dcKOxGvCxBNPN22abckzNR9ust1oyxiI3rkYcWcmNaOyDpIrsoI89q69ltwMuO+3RSOTyhu3yP31LPYDR8y72cPIrzQQJ9kMosaPWnqzFofFFguNhhLZvnp2WidDA+xatsCFbPHOweRcGbX9/ebHT0Kkp+9Whj2pFEyTqh9PZ9VpbOabUWjDYR+ZUVNKwGUhkcZfexeU2zhF1ZIC9lcXwx4qmztKJfL2zpi18ZxAgMBAAEwDQYJKoZIhvcNAQELBQADggEBAIxFkec78ncBkgodSjP+F9UdxBiU93nKtXxzZ+sopZbi5xhf13QSH61xNWgJrWVs63fPq5DdxIBQLTmAV6L3Z2G5IUv9F6BILENw1hKFsURlRKtuQALLXy3T/eoaT8DWw/ViU3FTYUf153vso0k2bDsH1qDCS5lKICrCAnFfjYM0emTUmG44opC3SWNIK7ve/efTgyFAVMAYTTFvI1bALPDsAxE2oL+97Dw2Nf2ZSjsWO/Y1Tg5cI1kINRefJGE/ETqP1sGQgO3DAa1SEbaxskmch7VLHsSWr+rgSdDE8rNbVSy+NBrGLmUf1qeSbBCwkRMM5ju4sla9uyO/okGcpSo=" ],
        "priority" : [ "100" ],
        "algorithm" : [ "RSA-OAEP" ]
      }
    }, {
      "id" : "eaf75282-5d7f-4a93-92c0-2554275b8cbd",
      "name" : "rsa-generated",
      "providerId" : "rsa-generated",
      "subComponents" : { },
      "config" : {
        "privateKey" : [ "MIIEowIBAAKCAQEA24M3MohxwX+sqKCejIljZDTEgUByU8MPj6ORaAqWq9SLdVHZWKRzpSbDEJK8lYWDLz42ZJelmpDnwrOxQmqqmLVs78PtfpMqew9P4Pb8Lni2oskw9q31jkhA51LaoiHjm+cIEJpaxlxWXtmNptkn1k2aFm7CqzLEbsWq15FFghJPESc5u6RO+UXWlZiYYwtODXSCENbbWgO3MnDzhUr42pdaAdtxEc4CctUuIC/hfRNp58axZGd1ZJJXVTK6fXr9BgJaP48btFFfBCRKEG3p5zBrL6bkMfi43ThCuiZR/M9aWb/zRmsMGvejPU1PskPuyVhDS6d9xXcjp5xmG6MlewIDAQABAoIBAGc4tSCAk8lzQZwL53Jbeo7Y11yWEwy7SVp4hnwfmolTiX6SDfvGXREYgJqO/bnrzevNu+Migj+EC099kwmRwgXMLl0yyz99cXjrktrNGXm6ztsOc4VT+Qe+hzKfveqGuBKMGblA9BWZvw2cDKWcMMTvudj5nXXGiiMytSroB4DpPwpemZwsPkcSf5wqyX3KY4BoMDMlTtWI/exZopm6QQfoa15Cjepq4Wg5LSplA6+/EM+oroaOgICcKTrsMRbz633CYjbZ+/SLoZ329T7MdN6osSmBsicTE1Xug5wJKJw2cJC070xndOr/9xZJiiT5+tePMUGXIV2tBhOTw1AtFuECgYEA9ZbKuXjnTKP3fMZ+dH7Mz8jFNls47x3+YdEHZ/t9xs7HsBw5h1Am5u+uxvTH7aCyGooRNZgbE8h4FVKY09OXdrzg+dTyl0t4B8C2nhWnTaavmbHmE/kKtDC74tqlHNqBvYJMq+cFMIZXhZCAYJBPUK+q0Hq6B4Oe1jrO2kKI0BsCgYEA5NFvDgeApq4I3qxlfcwYxNRSt8SS0hkO6WuZrRIMq8meFdtmIFu/rDGkLxN/QuIEuWcTONeGZpwRZ2leCWkU9bSek0g/exSh2VZWrjZH9w8Cf/kppV9vUBSJOIwmfobyIk72cQ7wSVp2BhbkVM0ukHju6cDBPotCOPW/r+A6FiECgYBJHymjCY2MszFH9G3IT8y5fSTC52xPPdX01+Dlo+qyG/U2yWCTmljK38hhMpaoePUuk7dGBtDhFgozBxG72D87ukcd+7pzM6Q8YrO84+OZaizUjTRc9ASs2+nFRKZurdx9SWN8t19DMGy0tu6SUcVb18H7Kt9ix1yumnnmYHMNywKBgGGZe5cuWA/AmAPy4O+BiOcb8ZR1AnMfQR8LSGdsFX+I5idIiA0xVVZrbi6uUFAE/CLg9m/+blOCKmJDrVqyOcB8JE+KN5mWOVrs2thASv30pwpTCxKA3If52nYGQb0iblF4AOJEjvWa4lDCnu4U/tGc47tM/qBg4gRNvk+UgvJBAoGBALgvXE5M0QtA6CiVrHKG6XLWmBTXV0FCJl53VL8lPdLjmGFN4q18YMCmgakjQdrrTPcFwQLPFTgK912PLk3z8/8ENCiDOIaDaKdGVgBXyfuhTaivxV+DW+Rutv9ZSVI46zKe0U8oE/O/78rY8lslx8hTQKfziVQQz9qQ9l1SOym+" ],
        "keyUse" : [ "SIG" ],
        "certificate" : [ "MIICmzCCAYMCBgGLpRHBADANBgkqhkiG9w0BAQsFADARMQ8wDQYDVQQDDAZtYXN0ZXIwHhcNMjMxMTA2MTQzNjI2WhcNMzMxMTA2MTQzODA2WjARMQ8wDQYDVQQDDAZtYXN0ZXIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDbgzcyiHHBf6yooJ6MiWNkNMSBQHJTww+Po5FoCpar1It1UdlYpHOlJsMQkryVhYMvPjZkl6WakOfCs7FCaqqYtWzvw+1+kyp7D0/g9vwueLaiyTD2rfWOSEDnUtqiIeOb5wgQmlrGXFZe2Y2m2SfWTZoWbsKrMsRuxarXkUWCEk8RJzm7pE75RdaVmJhjC04NdIIQ1ttaA7cycPOFSvjal1oB23ERzgJy1S4gL+F9E2nnxrFkZ3VkkldVMrp9ev0GAlo/jxu0UV8EJEoQbennMGsvpuQx+LjdOEK6JlH8z1pZv/NGawwa96M9TU+yQ+7JWENLp33FdyOnnGYboyV7AgMBAAEwDQYJKoZIhvcNAQELBQADggEBAARhrvE1hWP9hKyNaQ8LtGIty0/+VjXVx0lGFG2idhr5WY1HvW/mBXegDcUnedJFs580s/4maCz5Ka1/hMITrQPSVp4/TXPp6HCv9iAcd8tCSL/cEGpltj9FQK+qJ6L29985hLNOVv7RiiiGFrwjp2DEVzt6TD57KPS0wdjSKmD2TvwodcQMd71HUPIhveh6R/qxkAAlv+ZqWjNHxv8q5S1iN0tbVfOBI5bhHPD3IDVZWt6UcNBgFWeGYs9ZTT/DydrFvgRbSb0EhY0j2wa6iJq5dDzgiQ6XYT7++DVwVXgi3IIrH7S0i77pgXU/XlKJQCWN7nNkN1SnyL68ia4FCkM=" ],
        "priority" : [ "100" ]
      }
    }, {
      "id" : "6684a000-a290-408e-98a1-088a519ab8d8",
      "name" : "hmac-generated",
      "providerId" : "hmac-generated",
      "subComponents" : { },
      "config" : {
        "kid" : [ "f3578265-1d04-4e0d-8221-f7cae6eeae77" ],
        "secret" : [ "5fAn5na8w3BY6Ao2haDxL4dDyHWDnfyDVu2z-z_I1devlpI7fOF9LZSDQ62kAF9YN9_YIdNIeVN61bZ_AObrWg" ],
        "priority" : [ "100" ],
        "algorithm" : [ "HS256" ]
      }
    }, {
      "id" : "e95fae0e-d42c-4a1a-8a8e-bf1b758cb974",
      "name" : "aes-generated",
      "providerId" : "aes-generated",
      "subComponents" : { },
      "config" : {
        "kid" : [ "b28f99b1-5db9-40ca-8194-9ba97de1deda" ],
        "secret" : [ "GdHM3dvY1gwC3LA3EyzFOg" ],
        "priority" : [ "100" ]
      }
    } ]
  },
  "internationalizationEnabled" : false,
  "supportedLocales" : [ ],
  "authenticationFlows" : [ {
    "id" : "5b9fe2ff-edea-4952-bc60-326d6d6b40fa",
    "alias" : "Account verification options",
    "description" : "Method with which to verity the existing account",
    "providerId" : "basic-flow",
    "topLevel" : false,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "idp-email-verification",
      "authenticatorFlow" : false,
      "requirement" : "ALTERNATIVE",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticatorFlow" : true,
      "requirement" : "ALTERNATIVE",
      "priority" : 20,
      "autheticatorFlow" : true,
      "flowAlias" : "Verify Existing Account by Re-authentication",
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "186a5ef7-9e89-4deb-be7c-1fb7057d3c4c",
    "alias" : "Browser - Conditional OTP",
    "description" : "Flow to determine if the OTP is required for the authentication",
    "providerId" : "basic-flow",
    "topLevel" : false,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "conditional-user-configured",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "auth-otp-form",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 20,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "0eddda4f-2a48-4abe-abb5-3e566b5d10b0",
    "alias" : "Direct Grant - Conditional OTP",
    "description" : "Flow to determine if the OTP is required for the authentication",
    "providerId" : "basic-flow",
    "topLevel" : false,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "conditional-user-configured",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "direct-grant-validate-otp",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 20,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "03144e59-6c6b-43eb-9615-0b740a1dc51c",
    "alias" : "First broker login - Conditional OTP",
    "description" : "Flow to determine if the OTP is required for the authentication",
    "providerId" : "basic-flow",
    "topLevel" : false,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "conditional-user-configured",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "auth-otp-form",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 20,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "28cc58fe-2cc3-4dfe-bb64-8d427a42d620",
    "alias" : "Handle Existing Account",
    "description" : "Handle what to do if there is existing account with same email/username like authenticated identity provider",
    "providerId" : "basic-flow",
    "topLevel" : false,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "idp-confirm-link",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticatorFlow" : true,
      "requirement" : "REQUIRED",
      "priority" : 20,
      "autheticatorFlow" : true,
      "flowAlias" : "Account verification options",
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "bd985536-7f80-47c4-a67f-71f621437fd5",
    "alias" : "Reset - Conditional OTP",
    "description" : "Flow to determine if the OTP should be reset or not. Set to REQUIRED to force.",
    "providerId" : "basic-flow",
    "topLevel" : false,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "conditional-user-configured",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "reset-otp",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 20,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "376c9cff-1615-488d-974e-6078a998b86a",
    "alias" : "User creation or linking",
    "description" : "Flow for the existing/non-existing user alternatives",
    "providerId" : "basic-flow",
    "topLevel" : false,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticatorConfig" : "create unique user config",
      "authenticator" : "idp-create-user-if-unique",
      "authenticatorFlow" : false,
      "requirement" : "ALTERNATIVE",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticatorFlow" : true,
      "requirement" : "ALTERNATIVE",
      "priority" : 20,
      "autheticatorFlow" : true,
      "flowAlias" : "Handle Existing Account",
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "95f2e2fc-083b-4b09-a13a-252debe3765d",
    "alias" : "Verify Existing Account by Re-authentication",
    "description" : "Reauthentication of existing account",
    "providerId" : "basic-flow",
    "topLevel" : false,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "idp-username-password-form",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticatorFlow" : true,
      "requirement" : "CONDITIONAL",
      "priority" : 20,
      "autheticatorFlow" : true,
      "flowAlias" : "First broker login - Conditional OTP",
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "09f20ff5-b56e-4d5f-93ea-c083b59e25eb",
    "alias" : "browser",
    "description" : "browser based authentication",
    "providerId" : "basic-flow",
    "topLevel" : true,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "auth-cookie",
      "authenticatorFlow" : false,
      "requirement" : "ALTERNATIVE",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "auth-spnego",
      "authenticatorFlow" : false,
      "requirement" : "DISABLED",
      "priority" : 20,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "identity-provider-redirector",
      "authenticatorFlow" : false,
      "requirement" : "ALTERNATIVE",
      "priority" : 25,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticatorFlow" : true,
      "requirement" : "ALTERNATIVE",
      "priority" : 30,
      "autheticatorFlow" : true,
      "flowAlias" : "forms",
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "42217f64-a7c2-42c2-8a48-a843c4bbdbbe",
    "alias" : "clients",
    "description" : "Base authentication for clients",
    "providerId" : "client-flow",
    "topLevel" : true,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "client-secret",
      "authenticatorFlow" : false,
      "requirement" : "ALTERNATIVE",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "client-jwt",
      "authenticatorFlow" : false,
      "requirement" : "ALTERNATIVE",
      "priority" : 20,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "client-secret-jwt",
      "authenticatorFlow" : false,
      "requirement" : "ALTERNATIVE",
      "priority" : 30,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "client-x509",
      "authenticatorFlow" : false,
      "requirement" : "ALTERNATIVE",
      "priority" : 40,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "9c65086e-0ea3-4d6c-991f-c4d01aa4d5c0",
    "alias" : "direct grant",
    "description" : "OpenID Connect Resource Owner Grant",
    "providerId" : "basic-flow",
    "topLevel" : true,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "direct-grant-validate-username",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "direct-grant-validate-password",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 20,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticatorFlow" : true,
      "requirement" : "CONDITIONAL",
      "priority" : 30,
      "autheticatorFlow" : true,
      "flowAlias" : "Direct Grant - Conditional OTP",
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "02d2b7c0-d88b-408b-bfb3-14229ac68184",
    "alias" : "docker auth",
    "description" : "Used by Docker clients to authenticate against the IDP",
    "providerId" : "basic-flow",
    "topLevel" : true,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "docker-http-basic-authenticator",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "5d420a9b-0477-459e-92d3-ad5dfda379bc",
    "alias" : "first broker login",
    "description" : "Actions taken after first broker login with identity provider account, which is not yet linked to any Keycloak account",
    "providerId" : "basic-flow",
    "topLevel" : true,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticatorConfig" : "review profile config",
      "authenticator" : "idp-review-profile",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticatorFlow" : true,
      "requirement" : "REQUIRED",
      "priority" : 20,
      "autheticatorFlow" : true,
      "flowAlias" : "User creation or linking",
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "1e1d766c-dcf2-47d5-909c-262fab774f4d",
    "alias" : "forms",
    "description" : "Username, password, otp and other auth forms.",
    "providerId" : "basic-flow",
    "topLevel" : false,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "auth-username-password-form",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticatorFlow" : true,
      "requirement" : "CONDITIONAL",
      "priority" : 20,
      "autheticatorFlow" : true,
      "flowAlias" : "Browser - Conditional OTP",
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "81b4d4d9-6a2d-4f0c-b4ac-6f62d501afae",
    "alias" : "registration",
    "description" : "registration flow",
    "providerId" : "basic-flow",
    "topLevel" : true,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "registration-page-form",
      "authenticatorFlow" : true,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : true,
      "flowAlias" : "registration form",
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "6d347981-8eb8-4b52-9f51-f4b54238092e",
    "alias" : "registration form",
    "description" : "registration form",
    "providerId" : "form-flow",
    "topLevel" : false,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "registration-user-creation",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 20,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "registration-profile-action",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 40,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "registration-password-action",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 50,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "registration-recaptcha-action",
      "authenticatorFlow" : false,
      "requirement" : "DISABLED",
      "priority" : 60,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "registration-terms-and-conditions",
      "authenticatorFlow" : false,
      "requirement" : "DISABLED",
      "priority" : 70,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "0f038763-8391-4a21-b236-ae2c6b98a960",
    "alias" : "reset credentials",
    "description" : "Reset credentials for a user if they forgot their password or something",
    "providerId" : "basic-flow",
    "topLevel" : true,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "reset-credentials-choose-user",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "reset-credential-email",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 20,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticator" : "reset-password",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 30,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    }, {
      "authenticatorFlow" : true,
      "requirement" : "CONDITIONAL",
      "priority" : 40,
      "autheticatorFlow" : true,
      "flowAlias" : "Reset - Conditional OTP",
      "userSetupAllowed" : false
    } ]
  }, {
    "id" : "f2e79424-271f-489e-ad6a-1593cdf3ceb4",
    "alias" : "saml ecp",
    "description" : "SAML ECP Profile Authentication Flow",
    "providerId" : "basic-flow",
    "topLevel" : true,
    "builtIn" : true,
    "authenticationExecutions" : [ {
      "authenticator" : "http-basic-authenticator",
      "authenticatorFlow" : false,
      "requirement" : "REQUIRED",
      "priority" : 10,
      "autheticatorFlow" : false,
      "userSetupAllowed" : false
    } ]
  } ],
  "authenticatorConfig" : [ {
    "id" : "b41434d6-1b86-4214-a405-8a9679cec489",
    "alias" : "create unique user config",
    "config" : {
      "require.password.update.after.registration" : "false"
    }
  }, {
    "id" : "d075e496-ad61-47fc-a0bc-470b96b88c40",
    "alias" : "review profile config",
    "config" : {
      "update.profile.on.first.login" : "missing"
    }
  } ],
  "requiredActions" : [ {
    "alias" : "CONFIGURE_TOTP",
    "name" : "Configure OTP",
    "providerId" : "CONFIGURE_TOTP",
    "enabled" : true,
    "defaultAction" : false,
    "priority" : 10,
    "config" : { }
  }, {
    "alias" : "TERMS_AND_CONDITIONS",
    "name" : "Terms and Conditions",
    "providerId" : "TERMS_AND_CONDITIONS",
    "enabled" : false,
    "defaultAction" : false,
    "priority" : 20,
    "config" : { }
  }, {
    "alias" : "UPDATE_PASSWORD",
    "name" : "Update Password",
    "providerId" : "UPDATE_PASSWORD",
    "enabled" : true,
    "defaultAction" : false,
    "priority" : 30,
    "config" : { }
  }, {
    "alias" : "UPDATE_PROFILE",
    "name" : "Update Profile",
    "providerId" : "UPDATE_PROFILE",
    "enabled" : true,
    "defaultAction" : false,
    "priority" : 40,
    "config" : { }
  }, {
    "alias" : "VERIFY_EMAIL",
    "name" : "Verify Email",
    "providerId" : "VERIFY_EMAIL",
    "enabled" : true,
    "defaultAction" : false,
    "priority" : 50,
    "config" : { }
  }, {
    "alias" : "delete_account",
    "name" : "Delete Account",
    "providerId" : "delete_account",
    "enabled" : false,
    "defaultAction" : false,
    "priority" : 60,
    "config" : { }
  }, {
    "alias" : "webauthn-register",
    "name" : "Webauthn Register",
    "providerId" : "webauthn-register",
    "enabled" : true,
    "defaultAction" : false,
    "priority" : 70,
    "config" : { }
  }, {
    "alias" : "webauthn-register-passwordless",
    "name" : "Webauthn Register Passwordless",
    "providerId" : "webauthn-register-passwordless",
    "enabled" : true,
    "defaultAction" : false,
    "priority" : 80,
    "config" : { }
  }, {
    "alias" : "update_user_locale",
    "name" : "Update User Locale",
    "providerId" : "update_user_locale",
    "enabled" : true,
    "defaultAction" : false,
    "priority" : 1000,
    "config" : { }
  } ],
  "browserFlow" : "browser",
  "registrationFlow" : "registration",
  "directGrantFlow" : "direct grant",
  "resetCredentialsFlow" : "reset credentials",
  "clientAuthenticationFlow" : "clients",
  "dockerAuthenticationFlow" : "docker auth",
  "attributes" : {
    "cibaBackchannelTokenDeliveryMode" : "poll",
    "cibaExpiresIn" : "120",
    "cibaAuthRequestedUserHint" : "login_hint",
    "parRequestUriLifespan" : "60",
    "cibaInterval" : "5",
    "realmReusableOtpCode" : "false"
  },
  "keycloakVersion" : "22.0.4",
  "userManagedAccessAllowed" : false,
  "clientProfiles" : {
    "profiles" : [ ]
  },
  "clientPolicies" : {
    "policies" : [ ]
  }
} ]
