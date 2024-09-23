package com.kroger.merchandising.magicdatareader.batch.writer;

import com.kroger.merchandising.magicdatareader.entity.DataItem;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;


public class CustomUpsertDataItemWriter implements ItemWriter<DataItem> {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String upsertSql;

    public CustomUpsertDataItemWriter(JdbcTemplate jdbcTemplate, String upsertSql) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.upsertSql = upsertSql;
    }

    @Override
    public void write(Chunk<? extends DataItem> chunk) throws Exception {
        for(DataItem dataItem : chunk) {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("location", dataItem.getLocation())
                    .addValue("upc", dataItem.getUpc())
                    .addValue("quantitie1", dataItem.getQuantitie1())
                    .addValue("permanentPrice", dataItem.getPermanentPrice())
                    .addValue("quantitie2", dataItem.getQuantitie2())
                    .addValue("temporaryPrice", dataItem.getTemporaryPrice())
                    .addValue("effectiveDatoFrom", dataItem.getEffectiveDateFrom())
                    .addValue("effectiveDateTo", dataItem.getEffectiveDateTo())
                    .addValue("timingFlag", dataItem.getTimingFlag())
                    .addValue("durationFlag", dataItem.getDurationFlag())
                    .addValue("sku", dataItem.getSku())
                    .addValue("magicCoupon", dataItem.getMagicCoupon())
                    .addValue("couponUpc", dataItem.getCouponUpc());
            namedParameterJdbcTemplate.update(upsertSql, sqlParameterSource);
        }
    }
}
