package com.hvdbs.testoraclequerycancel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@RequiredArgsConstructor
@RequestMapping
@RestController
public class TestController {
    private final JdbcTemplate jdbcTemplate;
    private PreparedStatement preparedStatement;

    @GetMapping("/start")
    public String start(int sleepTime) {
        return jdbcTemplate.query(con ->
        {
           // preparedStatement = con.prepareStatement("SELECT TEST_SLEEP(" + sleepTime + ") FROM DUAL");
            preparedStatement = con.prepareStatement("select count(*) from ALL_SOURCE a1\n" +
                    "cross join ALL_SOURCE a2");

            return preparedStatement;
        }, rs -> {
            rs.next();

            return rs.getString(1);
        });
    }

    @GetMapping("/stop")
    public String stop() throws SQLException {
        preparedStatement.cancel();

        return "d";
    }
}
