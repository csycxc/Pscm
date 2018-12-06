package com.banry.pscm.persist.dao;

import com.banry.pscm.persist.mapper.LaborWageBillSqlProvider;
import com.banry.pscm.service.labour.LaborWageBill;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface LaborWageBillMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table labor_wage_bill
     *
     * @mbggenerated
     */
    @Delete({
        "delete from labor_wage_bill",
        "where wage_bill_id = #{wageBillId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String wageBillId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table labor_wage_bill
     *
     * @mbggenerated
     */
    @Insert({
        "insert into labor_wage_bill (wage_bill_id, wage_id, ",
        "year_month, wage_item, ",
        "wage_item_plus)",
        "values (#{wageBillId,jdbcType=VARCHAR}, #{wageId,jdbcType=VARCHAR}, ",
        "#{yearMonth,jdbcType=DATE}, #{wageItem,jdbcType=VARCHAR}, ",
        "#{wageItemPlus,jdbcType=INTEGER})"
    })
    int insert(LaborWageBill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table labor_wage_bill
     *
     * @mbggenerated
     */
    @InsertProvider(type= LaborWageBillSqlProvider.class, method="insertSelective")
    int insertSelective(LaborWageBill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table labor_wage_bill
     *
     * @mbggenerated
     */
    @Select({
        "select",
        "wage_bill_id, wage_id, year_month, wage_item, wage_item_plus",
        "from labor_wage_bill",
        "where wage_bill_id = #{wageBillId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="wage_bill_id", property="wageBillId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="wage_id", property="wageId", jdbcType=JdbcType.VARCHAR),
        @Result(column="year_month", property="yearMonth", jdbcType=JdbcType.DATE),
        @Result(column="wage_item", property="wageItem", jdbcType=JdbcType.VARCHAR),
        @Result(column="wage_item_plus", property="wageItemPlus", jdbcType=JdbcType.INTEGER)
    })
    LaborWageBill selectByPrimaryKey(String wageBillId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table labor_wage_bill
     *
     * @mbggenerated
     */
    @UpdateProvider(type=LaborWageBillSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(LaborWageBill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table labor_wage_bill
     *
     * @mbggenerated
     */
    @Update({
        "update labor_wage_bill",
        "set wage_id = #{wageId,jdbcType=VARCHAR},",
          "year_month = #{yearMonth,jdbcType=DATE},",
          "wage_item = #{wageItem,jdbcType=VARCHAR},",
          "wage_item_plus = #{wageItemPlus,jdbcType=INTEGER}",
        "where wage_bill_id = #{wageBillId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(LaborWageBill record);
}