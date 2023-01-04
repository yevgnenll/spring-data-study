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
            pstmt.setInt(2, member.money)
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

    @Throws(SQLException::class)
    fun findById(memberId: String): Member {
        val sql = """select * from member where member_id = ?"""
        var con: Connection? = null
        var pstmt: PreparedStatement? = null
        var rs: ResultSet? = null
        return try {
            con = getConnection()
            pstmt = con.prepareStatement(sql)
            pstmt.setString(1, memberId)
            rs = pstmt.executeQuery()

            if (rs.next()) {
                Member(rs.getString("member_id"), rs.getInt("money"))
            } else {
                throw NoSuchElementException("member not found memberId=$memberId")
            }
        } catch (e: SQLException) {
            log.error("db error", e)
            throw e
        } finally {
            close(con, pstmt, rs)
        }
    }

    @Throws(SQLException::class)
    fun update(memberId: String, money: Int) {
        val sql = """update member set money=? where member_id=?"""
        var con: Connection? = null
        var pstmt: PreparedStatement? = null

        try {
            con = getConnection()
            pstmt = con.prepareStatement(sql)
            pstmt.setInt(1, money)
            pstmt.setString(2, memberId)
            val resultSize = pstmt.executeUpdate()
            log.info("resultSize={}", resultSize)
        } catch (e: SQLException) {
            log.error("db error", e)
            throw e
        } finally {
            close(con, pstmt, null)
        }
    }

    @Throws(SQLException::class)
    fun delete(memberId: String) {
        val sql = "delete from member where member_id=?"
        var con: Connection? = null
        var pstmt: PreparedStatement? = null
        try {
            con = getConnection()
            pstmt = con.prepareStatement(sql)
            pstmt.setString(1, memberId)
            pstmt.executeUpdate()
        } catch (e: SQLException) {
            log.error("db error", e)
            throw e
        } finally {
            close(con, pstmt, null)
        }
    }

    private fun getConnection() = DBConnectionUtil.getConnection()
}