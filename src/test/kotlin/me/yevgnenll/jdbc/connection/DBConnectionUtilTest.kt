package me.yevgnenll.jdbc.connection

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DBConnectionUtilTest {

    @Test
    fun `connection 가져오기`() {
        val connection = DBConnectionUtil.getConnection()
        // get connection=conn0: url=jdbc:h2:tcp://localhost/~/test user=SA, class=class org.h2.jdbc.JdbcConnection
        assertThat(connection).isNotNull
    }

}