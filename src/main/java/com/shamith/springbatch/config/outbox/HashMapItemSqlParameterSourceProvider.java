package com.shamith.springbatch.config.outbox;

import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Map;

public class HashMapItemSqlParameterSourceProvider implements ItemSqlParameterSourceProvider<Map<String, Object>> {

    @Override
    public SqlParameterSource createSqlParameterSource(Map<String, Object> item) {
        return new MapSqlParameterSource(item);
    }
}