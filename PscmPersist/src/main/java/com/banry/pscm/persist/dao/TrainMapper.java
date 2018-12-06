package com.banry.pscm.persist.dao;

import com.banry.pscm.persist.mapper.TrainSqlProvider;
import com.banry.pscm.service.labour.Train;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface TrainMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table train
     *
     * @mbggenerated
     */
    @Delete({
        "delete from train",
        "where train_code = #{trainCode,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String trainCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table train
     *
     * @mbggenerated
     */
    @Insert({
        "insert into train (train_code, trian_date, ",
        "down_contract_code, train_type, ",
        "trainer, trainee, ",
        "train_address, train_attach, ",
        "remark, train_conntent)",
        "values (#{trainCode,jdbcType=VARCHAR}, #{trianDate,jdbcType=DATE}, ",
        "#{downContractCode,jdbcType=VARCHAR}, #{trainType,jdbcType=INTEGER}, ",
        "#{trainer,jdbcType=VARCHAR}, #{trainee,jdbcType=VARCHAR}, ",
        "#{trainAddress,jdbcType=VARCHAR}, #{trainAttach,jdbcType=VARCHAR}, ",
        "#{remark,jdbcType=VARCHAR}, #{trainConntent,jdbcType=LONGVARCHAR})"
    })
    int insert(Train record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table train
     *
     * @mbggenerated
     */
    @InsertProvider(type= TrainSqlProvider.class, method="insertSelective")
    int insertSelective(Train record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table train
     *
     * @mbggenerated
     */
    @Select({
        "select",
        "train_code, trian_date, down_contract_code, train_type, trainer, trainee, train_address, ",
        "train_attach, remark, train_conntent",
        "from train",
        "where train_code = #{trainCode,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="train_code", property="trainCode", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="trian_date", property="trianDate", jdbcType=JdbcType.DATE),
        @Result(column="down_contract_code", property="downContractCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="train_type", property="trainType", jdbcType=JdbcType.INTEGER),
        @Result(column="trainer", property="trainer", jdbcType=JdbcType.VARCHAR),
        @Result(column="trainee", property="trainee", jdbcType=JdbcType.VARCHAR),
        @Result(column="train_address", property="trainAddress", jdbcType=JdbcType.VARCHAR),
        @Result(column="train_attach", property="trainAttach", jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="train_conntent", property="trainConntent", jdbcType=JdbcType.LONGVARCHAR)
    })
    Train selectByPrimaryKey(String trainCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table train
     *
     * @mbggenerated
     */
    @UpdateProvider(type=TrainSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Train record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table train
     *
     * @mbggenerated
     */
    @Update({
        "update train",
        "set trian_date = #{trianDate,jdbcType=DATE},",
          "down_contract_code = #{downContractCode,jdbcType=VARCHAR},",
          "train_type = #{trainType,jdbcType=INTEGER},",
          "trainer = #{trainer,jdbcType=VARCHAR},",
          "trainee = #{trainee,jdbcType=VARCHAR},",
          "train_address = #{trainAddress,jdbcType=VARCHAR},",
          "train_attach = #{trainAttach,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "train_conntent = #{trainConntent,jdbcType=LONGVARCHAR}",
        "where train_code = #{trainCode,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKeyWithBLOBs(Train record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table train
     *
     * @mbggenerated
     */
    @Update({
        "update train",
        "set trian_date = #{trianDate,jdbcType=DATE},",
          "down_contract_code = #{downContractCode,jdbcType=VARCHAR},",
          "train_type = #{trainType,jdbcType=INTEGER},",
          "trainer = #{trainer,jdbcType=VARCHAR},",
          "trainee = #{trainee,jdbcType=VARCHAR},",
          "train_address = #{trainAddress,jdbcType=VARCHAR},",
          "train_attach = #{trainAttach,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where train_code = #{trainCode,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Train record);
}