package com.radiantchamber.walkarama.configs

import dev.alpas.AppConfig
import dev.alpas.Environment

@Suppress("unused")
class PortConfig(env: Environment) : AppConfig(env) {
    override val appPort = env("PORT", 8080)
}