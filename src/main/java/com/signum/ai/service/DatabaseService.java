package com.signum.ai.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DatabaseService {

    private final JdbcTemplate jdbc;

    public DatabaseService(JdbcTemplate jdbc) {

        this.jdbc = jdbc;

    }

    // ===========================
    // USER
    // ===========================

    public String getUserName(int userId){

        String sql =
                "SELECT username FROM users WHERE id=?";

        return jdbc.queryForObject(
                sql,
                String.class,
                userId
        );

    }

    // ===========================
    // POLICIES
    // ===========================

    public int getPolicyCount(int userId){

        String sql =

                "SELECT COUNT(*) " +
                "FROM user_policies " +
                "WHERE user_id=?";

        Integer count =

                jdbc.queryForObject(
                        sql,
                        Integer.class,
                        userId
                );

        return count == null ? 0 : count;

    }

    public List<Map<String,Object>> getPolicies(int userId){

        String sql =

                """
                SELECT
                p.policy_name,
                p.policy_type,
                up.start_date,
                up.end_date,
                up.premium_paid,
                up.maturity_amount
                FROM user_policies up
                JOIN policies p
                ON up.policy_id=p.policy_id
                WHERE up.user_id=?
                """;

        return jdbc.queryForList(sql,userId);

    }

    // ===========================
    // CLAIMS
    // ===========================

    public int getClaimCount(int userId){

        String sql=

                "SELECT COUNT(*) FROM claims WHERE user_id=?";

        Integer count=

                jdbc.queryForObject(
                        sql,
                        Integer.class,
                        userId
                );

        return count==null?0:count;

    }

    public List<Map<String,Object>> getClaims(int userId){

        String sql=

                """
                SELECT
                claim_id,
                policy_id,
                claim_amount,
                incident_date,
                status
                FROM claims
                WHERE user_id=?
                ORDER BY claim_id DESC
                """;

        return jdbc.queryForList(sql,userId);

    }

    public Map<String,Object> latestClaim(int userId){

        String sql=

                """
                SELECT *
                FROM claims
                WHERE user_id=?
                ORDER BY claim_id DESC
                LIMIT 1
                """;

        List<Map<String,Object>> list=

                jdbc.queryForList(sql,userId);

        if(list.isEmpty()){

            return null;

        }

        return list.get(0);

    }

    public int getPendingClaims(int userId){

        String sql=

                """
                SELECT COUNT(*)
                FROM claims
                WHERE user_id=?
                AND status='Pending'
                """;

        Integer count=

                jdbc.queryForObject(
                        sql,
                        Integer.class,
                        userId
                );

        return count==null?0:count;

    }

    public int getApprovedClaims(int userId){

        String sql=

                """
                SELECT COUNT(*)
                FROM claims
                WHERE user_id=?
                AND status='Approved'
                """;

        Integer count=

                jdbc.queryForObject(
                        sql,
                        Integer.class,
                        userId
                );

        return count==null?0:count;

    }

    public int getRejectedClaims(int userId){

        String sql=

                """
                SELECT COUNT(*)
                FROM claims
                WHERE user_id=?
                AND status='Rejected'
                """;

        Integer count=

                jdbc.queryForObject(
                        sql,
                        Integer.class,
                        userId
                );

        return count==null?0:count;

    }

}