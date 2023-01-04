package me.yevgnenll.jdbc.repository

import me.yevgnenll.jdbc.connection.DBConnectionUtil
import me.yevgnenll.jdbc.domain.Member
import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class MemberRepositoryV0 {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Throws(SQLException::class)
    fun save(member: Member): Member? {
        val sql = """insert into member(member_id, money) values (?, ?)"""
        var con: Connection? = null
        var pstmt: PreparedStatement? = null
        return try {
            con = getConnection()
            pstmt = con.prepareStatement(sql)
            pstmt.setString(1, member.memberId)
            pstmt.setInt(2, member.money ?: 0)
            pstmt.executeUpdate()
            member
        } catch (e: SQLException) {
            log.error("db error", e)
            throw e
        } finally {
            close(con, pstmt, null)
        }
    }

    private fun close(con: Connection?, stmt: PreparedStatement?, rs: ResultSet?) {
        try {
            rs?.close()
        } catch (e: SQLException) {
            log.error("error", e)
        }

        try {
            stmt?.close()
        } catch (e: SQLException) {
            log.error("error", e)
        }

        try {
            con?.close()
        } catch (e: SQLException) {
            log.error("error", e)
        }
    }

    private fun getConnection() = DBConnectionUtil.getConnection()
}