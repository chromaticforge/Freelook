@file:Suppress("UnstableApiUsage", "PropertyName")

import dev.deftu.gradle.utils.GameSide

plugins {
    java
    kotlin("jvm")
    id("dev.deftu.gradle.multiversion")
    id("dev.deftu.gradle.tools")
    id("dev.deftu.gradle.tools.resources")
    id("dev.deftu.gradle.tools.bloom")
    id("dev.deftu.gradle.tools.shadow")
    id("dev.deftu.gradle.tools.minecraft.loom")
}

toolkitLoomHelper {
    useOneConfig {
        version = "1.0.0-alpha.77"
        loaderVersion = "1.1.0-alpha.44"

        usePolyMixin = true
        polyMixinVersion = "0.8.4+build.2"

        applyLoaderTweaker = true

        // TODO: Only add modules that are actually used
        for (module in arrayOf("commands", "config", "config-impl", "events", "internal", "ui", "utils")) {
            +module
        }
    }

    useDevAuth("1.2.1")

    disableRunConfigs(GameSide.SERVER)

    if (!mcData.isNeoForge) {
        useMixinRefMap(modData.id)
    }

    if (mcData.isForge) {
        useForgeMixin(modData.id)
    }
}

dependencies {
    if (mcData.isFabric) {
        modImplementation("net.fabricmc:fabric-language-kotlin:1.11.0+kotlin.2.0.0")

        if (mcData.isLegacyFabric) {
            // 1.8.9 - 1.13
            modImplementation("net.legacyfabric.legacy-fabric-api:legacy-fabric-api:${mcData.dependencies.legacyFabric.legacyFabricApiVersion}")
        } else {
            // 1.16.5+
            modImplementation("net.fabricmc.fabric-api:fabric-api:${mcData.dependencies.fabric.fabricApiVersion}")
        }
    }
}