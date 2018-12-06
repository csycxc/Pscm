package com.banry.pscm.persist.dao;

import com.banry.pscm.persist.mapper.WorkAttendanceSqlProvider;
import com.banry.pscm.service.labour.WorkAttendance;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface WorkAttendanceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table work_attendance
     *
     * @mbggenerated
     */
    @Delete({
        "delete from work_attendance",
        "where attendance_id = #{attendanceId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer attendanceId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table work_attendance
     *
     * @mbggenerated
     */
    @Insert({
        "insert into work_attendance (attendance_id, in_id, ",
        "day, day_in_time, day_out_time, ",
        "worked_load, worked_load_unit, ",
        "worked_spot)",
        "values (#{attendanceId,jdbcType=INTEGER}, #{inId,jdbcType=VARCHAR}, ",
        "#{day,jdbcType=DATE}, #{dayInTime,jdbcType=TIMESTAMP}, #{dayOutTime,jdbcType=TIMESTAMP}, ",
        "#{workedLoad,jdbcType=DOUBLE}, #{workedLoadUnit,jdbcType=VARCHAR}, ",
        "#{workedSpot,jdbcType=VARCHAR})"
    })
    int insert(WorkAttendance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table work_attendance
     *
     * @mbggenerated
     */
    @InsertProvider(type= WorkAttendanceSqlProvider.class, method="insertSelective")
    int insertSelective(WorkAttendance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table work_attendance
     *
     * @mbggenerated
     */
    @Select({
        "select",
        "attendance_id, in_id, day, day_in_time, day_out_time, worked_load, worked_load_unit, ",
        "worked_spot",
        "from work_attendance",
        "where attendance_id = #{attendanceId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="attendance_id", property="attendanceId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="in_id", property="inId", jdbcType=JdbcType.VARCHAR),
        @Result(column="day", property="day", jdbcType=JdbcType.DATE),
        @Result(column="day_in_time", property="dayInTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="day_out_time", property="dayOutTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="worked_load", property="workedLoad", jdbcType=JdbcType.DOUBLE),
        @Result(column="worked_load_unit", property="workedLoadUnit", jdbcType=JdbcType.VARCHAR),
        @Result(column="worked_spot", property="workedSpot", jdbcType=JdbcType.VARCHAR)
    })
    WorkAttendance selectByPrimaryKey(Integer attendanceId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table work_attendance
     *
     * @mbggenerated
     */
    @UpdateProvider(type=WorkAttendanceSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(WorkAttendance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table work_attendance
     *
     * @mbggenerated
     */
    @Update({
        "update work_attendance",
        "set in_id = #{inId,jdbcType=VARCHAR},",
          "day = #{day,jdbcType=DATE},",
          "day_in_time = #{dayInTime,jdbcType=TIMESTAMP},",
          "day_out_time = #{dayOutTime,jdbcType=TIMESTAMP},",
          "worked_load = #{workedLoad,jdbcType=DOUBLE},",
          "worked_load_unit = #{workedLoadUnit,jdbcType=VARCHAR},",
          "worked_spot = #{workedSpot,jdbcType=VARCHAR}",
        "where attendance_id = #{attendanceId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(WorkAttendance record);
}