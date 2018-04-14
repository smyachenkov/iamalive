package com.iamalive

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class IamAlive
fun main(args: Array<String>) {
    SpringApplication.run(IamAlive::class.java, *args)
}
