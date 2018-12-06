package com.banry.pscm.persist.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.banry.pscm.service.labour.*;
import com.banry.pscm.service.util.EnumVar;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface LabourMapper {

    @Select({"select t.*,d.contract_part_second,d.contract_name,d.construction_team ",
            "from train t ",
            "left join down_contract d on t.down_contract_code = d.down_contract_code",
            "where t.down_contract_code = #{downContractCode,jdbcType=VARCHAR}"})
    @Results({
            @Result(column="train_code", property="trainCode", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="trian_date", property="trianDate", jdbcType=JdbcType.DATE),
            @Result(column="down_contract_code", property="downContractCode", jdbcType=JdbcType.VARCHAR),
            @Result(column="train_type", property="trainType", jdbcType=JdbcType.INTEGER),
            @Result(column="trainer", property="trainer", jdbcType=JdbcType.VARCHAR),
            @Result(column="trainee", property="trainee", jdbcType=JdbcType.VARCHAR),
            @Result(column="train_address", property="trainAddress", jdbcType=JdbcType.VARCHAR),
            @Result(column="train_conntent", property="trainConntent", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="train_attach", property="trainAttach", jdbcType=JdbcType.VARCHAR),
            @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
            @Result(column="contract_part_second", property="contractPartSecond", jdbcType=JdbcType.VARCHAR),
            @Result(column="contract_name", property="contractName", jdbcType=JdbcType.VARCHAR),
            @Result(column="construction_team", property="constructionTeam", jdbcType=JdbcType.VARCHAR)
    })
    List<HashMap> getTrainsByDownContractCode(String downContractCode);

    @Select({"select lio.* from labor_in_out lio ",
            " where train_code = #{trainCode,jdbcType=VARCHAR}"})
    @Results({
            @Result(column="in_id", property="inId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="id_number", property="idNumber", jdbcType=JdbcType.VARCHAR),
            @Result(column="in_work_type", property="inWorkType", jdbcType=JdbcType.INTEGER),
            @Result(column="down_contract_code", property="downContractCode", jdbcType=JdbcType.VARCHAR),
            @Result(column="train_code", property="trainCode", jdbcType=JdbcType.VARCHAR),
            @Result(column="exam_score", property="examScore", jdbcType=JdbcType.DOUBLE),
            @Result(column="in_date", property="inDate", jdbcType=JdbcType.DATE),
            @Result(column="out_date", property="outDate", jdbcType=JdbcType.DATE),
            @Result(column="out_why", property="outWhy", jdbcType=JdbcType.VARCHAR),
            @Result(column="reamrk", property="reamrk", jdbcType=JdbcType.VARCHAR),
            @Result(column="out_photo", property="outPhoto", jdbcType=JdbcType.LONGVARBINARY),
            @Result(column="in_id_photo", property="inIdPhoto", jdbcType=JdbcType.LONGVARBINARY),
            @Result(column="wage_model", property="wageModel", jdbcType=JdbcType.INTEGER),
            @Result(column="wage_unit_price", property="wageUnitPrice", jdbcType=JdbcType.DOUBLE),
            @Result(column="wage_umint", property="wageUmint", jdbcType=JdbcType.VARCHAR)
    })
    List<LaborInOut> selectLaborInOutByTrainCode(String trainCode);

    @Delete("delete from train where train_code = #{trainCode,jdbcType=VARCHAR}")
    int deleteTrainByTrainCode(String trainCode);

    @Select({"select l.* ,lio.in_id,lio.in_date,lio.exam_score,lio.in_id_photo,lio.train_code ",
            "lio.wage_model, lio.wage_unit_price, lio.wage_umint ",
        "from labor l LEFT JOIN labor_in_out lio on l.id_number = lio.id_number ",
        "where l.id_number = #{idNumber,jdbcType=VARCHAR} "})
    @Results({
            @Result(column="id_number", property="idNumber", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="sex", property="sex", jdbcType=JdbcType.VARCHAR),
            @Result(column="birthday", property="birthday", jdbcType=JdbcType.DATE),
            @Result(column="education_degree", property="educationDegree", jdbcType=JdbcType.INTEGER),
            @Result(column="height", property="height", jdbcType=JdbcType.INTEGER),
            @Result(column="weigh", property="weigh", jdbcType=JdbcType.INTEGER),
            @Result(column="worker_type", property="workerType", jdbcType=JdbcType.VARCHAR),
            @Result(column="career_year", property="careerYear", jdbcType=JdbcType.INTEGER),
            @Result(column="address", property="address", jdbcType=JdbcType.VARCHAR),
            @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
            @Result(column="id_photo", property="idPhoto", jdbcType=JdbcType.BLOB),
            @Result(column="trained_history", property="trainedHistory", jdbcType=JdbcType.VARCHAR),
            @Result(column="profess_qualify", property="professQualify", jdbcType=JdbcType.VARCHAR),
            @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
            @Result(column="in_id", property="inId", jdbcType=JdbcType.VARCHAR),
            @Result(column="in_date", property="inDate", jdbcType=JdbcType.DATE),
            @Result(column="exam_score", property="examScore", jdbcType=JdbcType.DOUBLE),
            @Result(column="in_id_photo", property="inIdPhoto", jdbcType=JdbcType.BLOB),
            @Result(column="train_code", property="trainCode", jdbcType=JdbcType.VARCHAR),
            @Result(column="wage_model", property="wageModel", jdbcType=JdbcType.INTEGER),
            @Result(column="wage_unit_price", property="wageUnitPrice", jdbcType=JdbcType.DOUBLE),
            @Result(column="wage_umint", property="wageUmint", jdbcType=JdbcType.VARCHAR)
    })
    List<HashMap> getLaborByIdNumber(String idNumber);

    @Select({"select lio.*,l.`name`,l.sex,l.birthday,l.height,l.weigh,l.education_degree,l.worker_type,l.id_photo,l.address,d.construction_team ",
            "from labor_in_out lio LEFT JOIN labor l on lio.id_number = l.id_number ",
            "LEFT JOIN down_contract d on  lio.down_contract_code = d.down_contract_code",
            "where lio.train_code = #{trainCode,jdbcType=VARCHAR} "})
    @Results({
            @Result(column="in_id", property="inId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="id_number", property="idNumber", jdbcType=JdbcType.VARCHAR),
            @Result(column="in_work_type", property="inWorkType", jdbcType=JdbcType.INTEGER),
            @Result(column="down_contract_code", property="downContractCode", jdbcType=JdbcType.VARCHAR),
            @Result(column="train_code", property="trainCode", jdbcType=JdbcType.VARCHAR),
            @Result(column="exam_score", property="examScore", jdbcType=JdbcType.DOUBLE),
            @Result(column="out_photo", property="outPhoto", jdbcType=JdbcType.BLOB),
            @Result(column="in_id_photo", property="inIdPhoto", jdbcType=JdbcType.BLOB),
            @Result(column="in_date", property="inDate", jdbcType=JdbcType.DATE),
            @Result(column="out_date", property="outDate", jdbcType=JdbcType.DATE),
            @Result(column="out_why", property="outWhy", jdbcType=JdbcType.VARCHAR),
            @Result(column="reamrk", property="reamrk", jdbcType=JdbcType.VARCHAR),
            @Result(column="wage_model", property="wageModel", jdbcType=JdbcType.INTEGER),
            @Result(column="wage_unit_price", property="wageUnitPrice", jdbcType=JdbcType.DOUBLE),
            @Result(column="wage_umint", property="wageUmint", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="sex", property="sex", jdbcType=JdbcType.VARCHAR),
            @Result(column="birthday", property="birthday", jdbcType=JdbcType.DATE),
            @Result(column="height", property="height", jdbcType=JdbcType.INTEGER),
            @Result(column="weigh", property="weigh", jdbcType=JdbcType.INTEGER),
            @Result(column="education_degree", property="educationDegree", jdbcType=JdbcType.INTEGER),
            @Result(column="worker_type", property="workerType", jdbcType=JdbcType.VARCHAR),
            @Result(column="id_photo", property="idPhoto", jdbcType=JdbcType.BLOB),
            @Result(column="address", property="address", jdbcType=JdbcType.VARCHAR),
            @Result(column="construction_team", property="constructionTeam", jdbcType=JdbcType.VARCHAR)
    })
    List<HashMap> selectLaborInOutsByTrainCode(String trainCode);

    @Select({"select lio.* from labor_in_out lio ",
            "where lio.train_code = #{trainCode,jdbcType=VARCHAR} "})
    @Results({
            @Result(column="in_id", property="inId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="id_number", property="idNumber", jdbcType=JdbcType.VARCHAR),
            @Result(column="in_work_type", property="inWorkType", jdbcType=JdbcType.INTEGER),
            @Result(column="down_contract_code", property="downContractCode", jdbcType=JdbcType.VARCHAR),
            @Result(column="train_code", property="trainCode", jdbcType=JdbcType.VARCHAR),
            @Result(column="exam_score", property="examScore", jdbcType=JdbcType.DOUBLE),
            @Result(column="out_photo", property="outPhoto", jdbcType=JdbcType.BLOB),
            @Result(column="in_id_photo", property="inIdPhoto", jdbcType=JdbcType.BLOB),
            @Result(column="in_date", property="inDate", jdbcType=JdbcType.DATE),
            @Result(column="out_date", property="outDate", jdbcType=JdbcType.DATE),
            @Result(column="out_why", property="outWhy", jdbcType=JdbcType.VARCHAR),
            @Result(column="reamrk", property="reamrk", jdbcType=JdbcType.VARCHAR),
            @Result(column="wage_model", property="wageModel", jdbcType=JdbcType.INTEGER),
            @Result(column="wage_unit_price", property="wageUnitPrice", jdbcType=JdbcType.DOUBLE),
            @Result(column="wage_umint", property="wageUmint", jdbcType=JdbcType.VARCHAR)
    })
    List<LaborInOutWithBLOBs> findLaborInOutWithBLOBsByTrainCode(String trainCode);

    /**
     * 根据平安卡号查询是否有考勤（如果有考勤，劳务人员入出场不能删除）
     * @param inId
     * @return
     */
    @Select({
        "select * from work_attendance where in_id = #{inId,jdbcType=VARCHAR} "
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
    List<WorkAttendance> selectWorkAttendanceByInId(String inId);

    @Select({
        "select lio.*,l.name,l.sex,l.birthday,l.height,l.weigh,l.education_degree,l.worker_type,l.id_photo,l.address ",
        "from labor_in_out lio LEFT JOIN labor l on lio.id_number = l.id_number ",
        "where lio.down_contract_code = #{downContractCode,jdbcType=VARCHAR} "
    })
    @Results(id = "LaborInOutForTable",value = {
            @Result(column="in_id", property="inId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="id_number", property="idNumber", jdbcType=JdbcType.VARCHAR),
            @Result(column="in_work_type", property="inWorkType", jdbcType=JdbcType.INTEGER),
            @Result(column="down_contract_code", property="downContractCode", jdbcType=JdbcType.VARCHAR),
            @Result(column="train_code", property="trainCode", jdbcType=JdbcType.VARCHAR),
            @Result(column="exam_score", property="examScore", jdbcType=JdbcType.DOUBLE),
            @Result(column="in_date", property="inDate", jdbcType=JdbcType.DATE),
            @Result(column="out_date", property="outDate", jdbcType=JdbcType.DATE),
            @Result(column="out_why", property="outWhy", jdbcType=JdbcType.VARCHAR),
            @Result(column="reamrk", property="reamrk", jdbcType=JdbcType.VARCHAR),
            @Result(column="out_photo", property="outPhoto", jdbcType=JdbcType.VARCHAR),
            @Result(column="in_id_photo", property="inIdPhoto", jdbcType=JdbcType.VARCHAR),
            @Result(column="wage_model", property="wageModel", jdbcType=JdbcType.INTEGER),
            @Result(column="wage_unit_price", property="wageUnitPrice", jdbcType=JdbcType.DOUBLE),
            @Result(column="wage_umint", property="wageUmint", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="sex", property="sex", jdbcType=JdbcType.VARCHAR),
            @Result(column="birthday", property="birthday", jdbcType=JdbcType.DATE),
            @Result(column="height", property="height", jdbcType=JdbcType.INTEGER),
            @Result(column="weigh", property="weigh", jdbcType=JdbcType.INTEGER),
            @Result(column="education_degree", property="educationDegree", jdbcType=JdbcType.INTEGER),
            @Result(column="worker_type", property="workerType", jdbcType=JdbcType.VARCHAR),
            @Result(column="id_photo", property="idPhoto", jdbcType=JdbcType.VARCHAR),
            @Result(column="address", property="address", jdbcType=JdbcType.VARCHAR)
    })
    List<HashMap> getLaborInOutByDownContractCode(String downContractCode);

    @Select({
            "select * from train where down_contract_code = #{downContractCode,jdbcType=VARCHAR}"
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
    List<Train> getTrainsByContract(String downContractCode);

    @Select({"select lio.*,l.name,l.sex,l.birthday,l.height,l.weigh,l.education_degree,l.worker_type,l.id_photo,l.address ",
            "from labor_in_out lio LEFT JOIN labor l on lio.id_number = l.id_number ",
            "where lio.train_code = #{trainCode,jdbcType=VARCHAR} "})
    @ResultMap(value="LaborInOutForTable")
    List<HashMap> getLaborInOutByTrainCode(String trainCode);

    @Select({"select in_id from labor_in_out where down_contract_code = #{downContractCode,jdbcType=VARCHAR} "})
    @Results({@Result(column="in_id", property="inId", jdbcType=JdbcType.VARCHAR)})
    List<String> getInIdsByDownContractCode(String downContractCode);
    @Select({"select in_id from labor_in_out where train_code = #{trainCode,jdbcType=VARCHAR} "})
    @Results({@Result(column="in_id", property="inId", jdbcType=JdbcType.VARCHAR)})
    List<String> getInIdsByTrainCode(String trainCode);


    @Select({
            "select ",
            "w.attendance_id, w.in_id, w.day, ",
            "(select date_format(w.day_in_time, \'%Y-%m-%d %H:%i:%s\')) day_in_time,",
            "(select date_format(w.day_out_time, \'%Y-%m-%d %H:%i:%s\')) day_out_time,",
            "w.worked_load, w.worked_load_unit, w.worked_spot, ",
            "lio.id_number,l.name,l.worker_type,l.id_photo,lio.in_id_photo ",
            "from work_attendance w ",
            "LEFT JOIN labor_in_out lio on w.in_id = lio.in_id ",
            "LEFT JOIN labor l on lio.id_number = l.id_number ",
            "where w.in_id in(${inIds}) ",
            "and w.day = #{today} "
    })
    @Results(id="WorkAttendancesForTable",value = {
            @Result(column="attendance_id", property="attendanceId", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="in_id", property="inId", jdbcType=JdbcType.VARCHAR),
            @Result(column="day", property="day", jdbcType=JdbcType.DATE),
            @Result(column="day_in_time", property="dayInTime", jdbcType=JdbcType.VARCHAR),
            @Result(column="day_out_time", property="dayOutTime", jdbcType=JdbcType.VARCHAR),
            @Result(column="worked_load", property="workedLoad", jdbcType=JdbcType.DOUBLE),
            @Result(column="worked_load_unit", property="workedLoadUnit", jdbcType=JdbcType.VARCHAR),
            @Result(column="worked_spot", property="workedSpot", jdbcType=JdbcType.VARCHAR),
            @Result(column="id_number", property="idNumber", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="worker_type", property="workerType", jdbcType=JdbcType.VARCHAR),
            @Result(column="id_photo", property="idPhoto", jdbcType=JdbcType.VARCHAR),
            @Result(column="in_id_photo", property="inIdPhoto", jdbcType=JdbcType.VARCHAR)
    })
    List<HashMap> getTodayWorkAttendancesByInIds(@Param("inIds")String inIds,@Param("today")String today);

    @Select({
            "select lio.in_id,lio.id_number,l.name,l.worker_type,l.id_photo,lio.in_id_photo ",
            "from labor_in_out lio ",
            "LEFT JOIN labor l on lio.id_number = l.id_number ",
            "where lio.down_contract_code = #{downContractCode,jdbcType=VARCHAR} ",
            "and trim(lio.in_date) != '' and lio.out_date is null or trim(lio.out_date)='' "
    })
    @Results(id="LaborInOutOfInOrOut",value = {
            @Result(column="in_id", property="inId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="id_number", property="idNumber", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="worker_type", property="workerType", jdbcType=JdbcType.VARCHAR),
            @Result(column="id_photo", property="idPhoto", jdbcType=JdbcType.VARCHAR),
            @Result(column="in_id_photo", property="inIdPhoto", jdbcType=JdbcType.VARCHAR)
    })
    List<HashMap> getLaborInOutByDownContractCodeAndIn(String downContractCode);

    @Select({
            "select lio.in_id,lio.id_number,l.name,l.worker_type,l.id_photo,lio.in_id_photo ",
            "from labor_in_out lio ",
            "LEFT JOIN labor l on lio.id_number = l.id_number ",
            "where lio.down_contract_code = #{downContractCode,jdbcType=VARCHAR} ",
            "and trim(lio.in_date) != '' and trim(lio.out_date) != '' "
    })
    @ResultMap(value="LaborInOutOfInOrOut")
    List<HashMap> getLaborInOutByDownContractCodeAndOut(String downContractCode);

    @Select({
            "select lio.in_id,lio.id_number,l.name,l.worker_type,l.id_photo,lio.in_id_photo ",
            "from labor_in_out lio ",
            "LEFT JOIN labor l on lio.id_number = l.id_number ",
            "where lio.train_code = #{trainCode,jdbcType=VARCHAR} ",
            "and trim(lio.in_date) != '' and lio.out_date is null or trim(lio.out_date)='' "
    })
    @ResultMap(value="LaborInOutOfInOrOut")
    List<HashMap> getLaborInOutByTrainCodeAndIn(String trainCode);

    @Select({
            "select lio.in_id,lio.id_number,l.name,l.worker_type,l.id_photo,lio.in_id_photo ",
            "from labor_in_out lio ",
            "LEFT JOIN labor l on lio.id_number = l.id_number ",
            "where lio.train_code = #{trainCode,jdbcType=VARCHAR} ",
            "and trim(lio.in_date) != '' and trim(lio.out_date) != '' "
    })
    @ResultMap(value="LaborInOutOfInOrOut")
    List<HashMap> getLaborInOutByTrainCodeAndOut(String trainCode);

    @Select({
            "select ",
            "w.attendance_id, w.in_id, w.day, ",
            "(select date_format(w.day_in_time, \'%Y-%m-%d %H:%i:%s\')) day_in_time,",
            "(select date_format(w.day_out_time, \'%Y-%m-%d %H:%i:%s\')) day_out_time,",
            "w.worked_load, w.worked_load_unit, w.worked_spot, ",
            "lio.id_number,l.name,l.worker_type,l.id_photo,lio.in_id_photo ",
            "from work_attendance w ",
            "LEFT JOIN labor_in_out lio on w.in_id = lio.in_id ",
            "LEFT JOIN labor l on lio.id_number = l.id_number ",
            "where w.in_id = #{inId,jdbcType=VARCHAR} "
    })
    @ResultMap(value="WorkAttendancesForTable")
    List<HashMap> selectWorkAttendanceForTableByInId(String inId);

    @Select({"select ",
            "lw.wage_id, lw.in_id, lw.year_month, lw.work_days, lw.worked_loads, lio.wage_model, lio.wage_unit_price, ",
            "lio.wage_umint, lw.gross_pay, lw.net_pay, lw.remark, l.id_number, l.name, l.worker_type ",/*, lio.wage_unit_price*lw.worked_loads month_salary*/
            "from labor_wages lw ",
            "LEFT JOIN labor_in_out lio ON lw.in_id = lio.in_id ",
            "LEFT JOIN labor l on lio.id_number = l.id_number ",
            "where lio.down_contract_code = #{downContractCode,jdbcType=VARCHAR} ",
            "and lw.year_month >= #{startDate,jdbcType=VARCHAR} and lw.year_month < #{endDate,jdbcType=VARCHAR} "
    })
    @Results({
            @Result(column="wage_id", property="wageId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="in_id", property="inId", jdbcType=JdbcType.VARCHAR),
            @Result(column="year_month", property="yearMonth", jdbcType=JdbcType.VARCHAR),
            @Result(column="work_days", property="workDays", jdbcType=JdbcType.INTEGER),
            @Result(column="worked_loads", property="workedLoads", jdbcType=JdbcType.DOUBLE),
            @Result(column="wage_model", property="wageModel", jdbcType=JdbcType.INTEGER),
            @Result(column="wage_unit_price", property="wageUnitPrice", jdbcType=JdbcType.DOUBLE),
            @Result(column="wage_umint", property="wageUmint", jdbcType=JdbcType.VARCHAR),
            @Result(column="gross_pay", property="grossPay", jdbcType=JdbcType.DOUBLE),
            @Result(column="net_pay", property="netPay", jdbcType=JdbcType.DOUBLE),
            @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="id_number", property="idNumber", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="worker_type", property="workerType", jdbcType=JdbcType.VARCHAR)/*,
            @Result(column="month_salary", property="monthSalary", jdbcType=JdbcType.DOUBLE)*/
    })
    List<HashMap> selectSalaryByDownContractCodeAndDate(
            @Param("downContractCode")String downContractCode,
            @Param("startDate")String startDate,
            @Param("endDate")String endDate
    );

    @Select({"select * from enum_var where enum_name = 'wage_model' or enum_name = 'wage_umint' or enum_name = 'wage_unit_price' "})
    @Results({
            @Result(column="enum_name", property="enumName", jdbcType=JdbcType.VARCHAR),
            @Result(column="enum_value", property="enumValue", jdbcType=JdbcType.INTEGER),
            @Result(column="enum_value_name", property="enumValueName", jdbcType=JdbcType.VARCHAR),
            @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR)
    })
    List<EnumVar> selectEnumVarForLaborWages();

    @Select({
            "select * from work_attendance w ",
            "where w.in_id = #{inId} ",
            "and w.day > #{start}  and w.day < #{end} "
    })
    @Results({
            @Result(column="attendance_id", property="attendanceId", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="in_id", property="inId", jdbcType=JdbcType.VARCHAR),
            @Result(column="day", property="day", jdbcType=JdbcType.DATE),
            @Result(column="day_in_time", property="dayInTime", jdbcType=JdbcType.VARCHAR),
            @Result(column="day_out_time", property="dayOutTime", jdbcType=JdbcType.VARCHAR),
            @Result(column="worked_load", property="workedLoad", jdbcType=JdbcType.DOUBLE),
            @Result(column="worked_load_unit", property="workedLoadUnit", jdbcType=JdbcType.VARCHAR),
            @Result(column="worked_spot", property="workedSpot", jdbcType=JdbcType.VARCHAR)
    })
    List<WorkAttendance> selectWorkAttendancesByInIdAndDate(
            @Param("inId")String inId,
            @Param("start")String start,
            @Param("end")String end
    );

    @Select({"select lw.in_id from labor_wages lw where lw.year_month > #{start}  and lw.year_month < #{end} "})
    @Results({@Result(column="in_id", property="inId", jdbcType=JdbcType.VARCHAR)})
    List<String> selectInIdsFromLaborWagesByDay(@Param("start")String start, @Param("end")String end);
}
