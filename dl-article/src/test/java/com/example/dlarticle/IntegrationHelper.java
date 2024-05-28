package com.example.dlarticle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import io.restassured.RestAssured;

import java.util.List;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationHelper extends AbstractTestExecutionListener {
    public static final String NON_ASCII = "NonAsciiCharacters";

    // 무작위로 선택한 포트를 주입
    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        // 현재 실행된 포트 지정
        RestAssured.port = this.port;

        // 외래 키 제약 조건 비활성화
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0;");

        // public 스키마에 있는 모든 테이블 조회 후 'TRUNCATE TABLE 테이블명;' 형식의 쿼리로 생성
        List<String> truncateAllTablesQuery = jdbcTemplate.queryForList(
                "SELECT CONCAT('TRUNCATE TABLE ', table_name, ';') " +
                        "FROM information_schema.tables " +
                        "WHERE table_schema = 'danlab-article';", // 데이터베이스 이름을 변경하세요
                String.class);

        // 데이터베이스의 모든 테이블 초기화
        truncateAllTables(truncateAllTablesQuery);

        // 외래 키 제약 조건 재활성화
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1;");
    }

    private void truncateAllTables(List<String> truncateAllTablesQuery) {
        // truncate 쿼리 실행
        for (String truncateQuery : truncateAllTablesQuery) {
            jdbcTemplate.execute(truncateQuery);
        }
    }
}

