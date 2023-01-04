package me.yevgnenll.jdbc.repository

import me.yevgnenll.jdbc.domain.Member
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory


class MemberRepositoryV0Test {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    private val memberRepository = MemberRepositoryV0()

    @Test
    fun `save, select, delete test` () {
        val member = Member("member-1", 10000)
        memberRepository.save(member)

        val findMember: Member = memberRepository.findById(member.memberId)
        log.info("findMember={}", findMember)
        assertThat(findMember).isEqualTo(member)

        // update: money: 10000 -> 20000
        memberRepository.update(member.memberId, 20000)
        val updatedMember: Member = memberRepository.findById(member.memberId)
        assertThat(updatedMember.money).isEqualTo(20000)

        // delete
        memberRepository.delete(member.memberId)

        // delete
        assertThatThrownBy { memberRepository.findById(member.memberId) }
            .isInstanceOf(NoSuchElementException::class.java)
    }

}