package main

object Requests {

    val CONSEIL_NETWORK: String = "mainnet"

    object ConseilRequests {

        final val CONSEIL_BUILD_INFO: String = "/info"

        final val CONSEIL_PLATFORMS: String = "/v2/metadata/platforms"

    }

    object TezosConfig {

        final val TEZOS_NETWORKS: String = "/v2/metadata/tezos/networks"

        final val TEZOS_ENTITIES: String = "/v2/metadata/tezos/" + CONSEIL_NETWORK + "/entities"

    }

    final val TEZOS_ENTITIES: Array[String] = Array(
        "accounts",
        "accounts_history",
        "baker_registry",
        "bakers",
        "baking_rights",
        "balance_updates",
        "big_map_contents",
        "big_maps",
        "blocks",
        "fees",
        "governance",
        "known_addresses",
        "operation_groups",
        "operations",
        "originated_account_maps",
        "registered_tokens"
    )

    def getTezosAttributeURI(entity: String): String = "/v2/metadata/tezos/" + CONSEIL_NETWORK + "/" + entity + "/attributes"

    object TezosChainRequests {

        final val TEZOS_BLOCK_HEAD: String = "/v2/data/tezos/" + CONSEIL_NETWORK + "/blocks/head"

        final val TEZOS_GET_OPERATION_GROUP: String = "/v2/data/tezos/" + CONSEIL_NETWORK + "/operation_groups/ooJQqGxrsdxi9GNinZjivnx8y7BtMc372G8gB7GmCKSQRnNkEtK"

        final val TEZOS_GET_ACCOUNT: String = "/v2/data/tezos/" + CONSEIL_NETWORK + "/accounts/KT1V7VoyjbvqSmnRtv9pHkRuBCPT7UubCrCX"



    }

}
