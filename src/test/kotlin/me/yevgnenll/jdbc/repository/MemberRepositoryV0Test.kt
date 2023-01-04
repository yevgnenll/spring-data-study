package me.yevgnenll.jdbc.repository

import me.yevgnenll.jdbc.domain.Member
import org.junit.jupiter.api.Test

class MemberRepositoryV0Test {

    private val memberRepository = MemberRepositoryV0()

    @Test
    fun `save, select, delete test` () {
        val member = Member("member-1", 10000)
        memberRepository.save(member)
    }

}