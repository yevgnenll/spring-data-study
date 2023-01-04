package me.yevgnenll.jdbc.connection

import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DBConnectionUtil {

    companion object {

        private val log = LoggerFactory.getLogger(DBConnectionUtil::class.java)

        fun getConnection(): Connection {
            // 어떻게 H2 driver를 찾을까?
            return try {
                DriverManager.getConnection(URL, USERNAME, PASSWORD).also {
                    log.info("get connection={}, class={}", it, it.javaClass)
                }
            } catch (e: SQLException) {
                throw IllegalStateException(e)
            }
        }
    }

}