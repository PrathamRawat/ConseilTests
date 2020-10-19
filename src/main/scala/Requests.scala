package main

object Requests {

    val CONSEIL_NETWORK: String = "mainnet"

    object ConseilRequests {

        final val CONSEIL_BUILD_INFO: String = "/info"

        final val CONSEIL_PLATFORMS: String = "/v2/metadata/platforms"

    }

    object TezosConfigRequests {

        final val TEZOS_NETWORKS: String = "/v2/metadata/tezos/networks"

        final val TEZOS_ENTITIES: String = "/v2/metadata/tezos/" + CONSEIL_NETWORK + "/entities"

    }

    object TezosEntities {

        final val TEZOS_ACCOUNTS_ATTRIBUTES: String = "/v2/metadata/tezos/" + CONSEIL_NETWORK + "/accounts/attributes"

        final val TEZOS_ACCOUNTS_HISTORY_ATTRIBUTES: String = "/v2/metadata/tezos/" + CONSEIL_NETWORK + "/accounts_history/attributes"

        final val TEZOS_BAKER_REGISTRY_ATTRIBUTES: String = "/v2/metadata/tezos/" + CONSEIL_NETWORK + "/baker_registry/attributes"

        final val TEZOS_BAKER_ATTRIBUTES: String = "/v2/metadata/tezos/" + CONSEIL_NETWORK + "/bakers/attributes"

        final val TEZOS_BAKERS_HISTORY_ATTRIBUTES: String = "/v2/metadata/tezos/" + CONSEIL_NETWORK + "/bakers_history/attributes"

        final val TEZOS_BAKING_RIGHTS_ATTRIBUTES: String = "/v2/metadata/tezos/" + CONSEIL_NETWORK + "/baking_rights/attributes"

        final val TEZOS_BALANCE_UPDATES_ATTRIBUTES: String = "/v2/metadata/tezos/" + CONSEIL_NETWORK + "/balance_updates/attributes"

        final val TEZOS_BIG_MAP_CONTENTS_ATTRIBUTES: String = "/v2/metadata/tezos/" + CONSEIL_NETWORK + "/big_map_contents/attributes"

        final val TEZOS_BIG_MAPS_ATTRIBUTES: String = "/v2/metadata/tezos/" + CONSEIL_NETWORK + "/big_maps/attributes"

        final val TEZOS_BLOCK_ATTRIBUTES: String = "/v2/metadata/tezos/" + CONSEIL_NETWORK + "/blocks/attributes"

        final val TEZOS_FEES_ATTRIBUTES: String = "/v2/metadata/tezos/" + CONSEIL_NETWORK + "/fees/attributes"

        final val TEZOS_GOVERNANCE_ATTRIBUTES: String = "/v2/metadata/tezos/" + CONSEIL_NETWORK + "/governance/attributes"

        final val TEZOS_KNOWN_ADDRESSES_ATTRIBUTES: String = "/v2/metadata/tezos/" + CONSEIL_NETWORK + "/known_addresses/attributes"

        final val TEZOS_OPERATION_GROUPS_ATTRIBUTES: String = "/v2/metadata/tezos/" + CONSEIL_NETWORK + "/operation_groups/attributes"

        final val TEZOS_OPERATIONS_ATTRIBUTES: String = "/v2/metadata/tezos/" + CONSEIL_NETWORK + "/operations/attributes"

        final val TEZOS_ORIGINATED_ACCOUNT_MAPS_ATTRIBUTES: String = "/v2/metadata/tezos/" + CONSEIL_NETWORK + "/originated_account_maps/attributes"

        final val TEZOS_REGISTERED_TOKENS_ATTRIBUTES: String = "/v2/metadata/tezos/" + CONSEIL_NETWORK + "/registered_tokens/attributes"

    }

    object TezosChainRequests {

        final val TEZOS_BLOCK_HEAD: String = "/v2/data/tezos/" + CONSEIL_NETWORK + "/blocks/head"

        final val TEZOS_GET_OPERATION_GROUP: String = "/v2/data/tezos/" + CONSEIL_NETWORK + "/operation_groups/ooJQqGxrsdxi9GNinZjivnx8y7BtMc372G8gB7GmCKSQRnNkEtK"

        final val TEZOS_GET_ACCOUNT: String = "/v2/data/tezos/" + CONSEIL_NETWORK + "/accounts/KT1V7VoyjbvqSmnRtv9pHkRuBCPT7UubCrCX"

    }

}
