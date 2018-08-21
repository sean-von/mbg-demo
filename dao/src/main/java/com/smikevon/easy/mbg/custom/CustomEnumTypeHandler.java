package com.smikevon.easy.mbg.custom;

import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.smikevon.easy.model.annotation.MyBatisColumn;

/**
 * Created by sean (smikevon@163.com) on 2018/8/21.
 */
public class CustomEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private Logger logger = Logger.getLogger(CustomEnumTypeHandler.class.getName());

    private final Map<Integer, E> lookup = new HashMap<>();
    private final Map<E, Integer> reverse = new HashMap<>();

    public CustomEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }

        MyBatisColumn column;
        if ((column = type.getDeclaredAnnotation(MyBatisColumn.class)) == null) {
            return;
        }

        try {
            Field columnField = type.getDeclaredField(column.fieldName());
            columnField.setAccessible(true);

            for (E item : EnumSet.allOf((Class<E>) getRawType())) {
                lookup.put((Integer) columnField.get(item), item);
                reverse.put(item, (Integer) columnField.get(item));
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, reverse.get(parameter));
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Integer s = rs.getInt(columnName);
        return s == null ? null : lookup.get(s);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Integer s = rs.getInt(columnIndex);
        return s == null ? null : lookup.get(s);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Integer s = cs.getInt(columnIndex);
        return s == null ? null : lookup.get(s);
    }
}
