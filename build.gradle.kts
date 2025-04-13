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
    id("dev.deftu.gradle.tools.minecraft.releases")
}

toolkitLoomHelper {
    useOneConfig {
        version = "1.0.0-alpha.77"
        loaderVersion = "1.1.0-alpha.44"

        usePolyMixin = true
        polyMixinVersion = "0.8.4+build.2"

        applyLoaderTweaker = true

        for (module in arrayOf("commands", "config", "config-impl", "events", "internal", "ui", "utils")) {
            +module
        }
    }

    useDevAuth("1.2.1")
    useMixinExtras("0.4.1")

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
        if (mcData.isLegacyFabric) {
            modImplementation("net.legacyfabric.legacy-fabric-api:legacy-fabric-api:${mcData.dependencies.legacyFabric.legacyFabricApiVersion}")
        } else {
            modImplementation("net.fabricmc.fabric-api:fabric-api:${mcData.dependencies.fabric.fabricApiVersion}")
        }
    }
}