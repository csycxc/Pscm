package com.banry.pscm.service.enums;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangeTypeEnumHandler implements TypeHandler<ChangeTypeEnum> {
    @Override
    public void setParameter(PreparedStatement ps, int i, ChangeTypeEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public ChangeTypeEnum getResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return getChangeTypeEnum(code);
    }


    @Override
    public ChangeTypeEnum getResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return getChangeTypeEnum(code);
    }

    @Override
    public ChangeTypeEnum getResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return getChangeTypeEnum(code);
    }


    private ChangeTypeEnum getChangeTypeEnum(int code) {
        ChangeTypeEnum[] values = ChangeTypeEnum.values();
        for (ChangeTypeEnum value : values) {
            if (code == value.getCode()) {
                return value;
            }
        }
        return null;
    }
}
