package com.github.chromaticforge.freelook.client

import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.entity.Entity

object CameraStateTracker {
    private val pitchMap = mutableMapOf<Entity, Float>()
    private val yawMap = mutableMapOf<Entity, Float>()

    fun getCameraPitch(entity: Entity?): Float = pitchMap[entity] ?: 0f
    fun getCameraYaw(entity: Entity?): Float = yawMap[entity] ?: 0f

    fun setCameraPitch(entity: Entity?, value: Float) {
        pitchMap[entity as Entity] = value
    }

    fun setCameraYaw(entity: Entity?, value: Float) {
        yawMap[entity as Entity] = value
    }
}
