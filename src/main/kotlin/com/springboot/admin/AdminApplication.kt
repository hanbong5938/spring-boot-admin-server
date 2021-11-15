package com.springboot.admin

import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableAdminServer
class AdminApplication

fun main(args: Array<String>) {
    runApplication<AdminApplication>(*args)
}
