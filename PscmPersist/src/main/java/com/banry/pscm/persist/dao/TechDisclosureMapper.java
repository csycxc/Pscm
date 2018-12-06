package com.banry.pscm.persist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;

import com.banry.pscm.persist.mapper.TechDisclosureSqlProvider;
import com.banry.pscm.service.schedule.TechDisclosure;

public interface TechDisclosureMapper {

	/**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tech_disclosure
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    @Delete({
        "delete from tech_disclosure",
        "where disclosure_code = #{disclosureCode,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String disclosureCode);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tech_disclosure
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    @Insert({
        "insert into tech_disclosure (disclosure_code, division_sn_code, ",
        "disclosurer, dis_role, ",
        "dis_date, dis_level, ",
        "dis_recipient, recipient_role, ",
        "dis_include, dis_content, ",
        "dis_attach)",
        "values (#{disclosureCode,jdbcType=VARCHAR}, #{divisionSnCode.divisionSnCode}, ",
        "#{disclosurer,jdbcType=VARCHAR}, #{disRole,jdbcType=VARCHAR}, ",
        "#{disDate,jdbcType=DATE}, #{disLevel,jdbcType=INTEGER}, ",
        "#{disRecipient,jdbcType=VARCHAR}, #{recipientRole,jdbcType=VARCHAR}, ",
        "#{disInclude,jdbcType=VARCHAR}, #{disContent,jdbcType=LONGVARCHAR}, ",
        "#{disAttach,jdbcType=LONGVARCHAR})"
    })
    int insert(TechDisclosure record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tech_disclosure
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    @InsertProvider(type=TechDisclosureSqlProvider.class, method="insertSelective")
    int insertSelective(TechDisclosure record);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tech_disclosure
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    @Select({
        "select",
        "disclosure_code, division_sn_code, disclosurer, dis_role, dis_date, dis_level, ",
        "dis_recipient, recipient_role, dis_include, dis_content, dis_attach",
        "from tech_disclosure",
        "where disclosure_code = #{disclosureCode,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="disclosure_code", property="disclosureCode", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="division_sn_code", property="divisionSnCode",
    		one=@One(
    				select="com.banry.pscm.persist.dao.EngDivisionMapper.selectByPrimaryKey",
    				fetchType=FetchType.EAGER
    				)),
        @Result(column="disclosurer", property="disclosurer", jdbcType=JdbcType.VARCHAR),
        @Result(column="dis_role", property="disRole", jdbcType=JdbcType.VARCHAR),
        @Result(column="dis_date", property="disDate", jdbcType=JdbcType.DATE),
        @Result(column="dis_level", property="disLevel", jdbcType=JdbcType.INTEGER),
        @Result(column="dis_recipient", property="disRecipient", jdbcType=JdbcType.VARCHAR),
        @Result(column="recipient_role", property="recipientRole", jdbcType=JdbcType.VARCHAR),
        @Result(column="dis_include", property="disInclude", jdbcType=JdbcType.VARCHAR),
        @Result(column="dis_content", property="disContent", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="dis_attach", property="disAttach", jdbcType=JdbcType.LONGVARCHAR)
    })
    TechDisclosure selectByPrimaryKey(String disclosureCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tech_disclosure
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    @UpdateProvider(type=TechDisclosureSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TechDisclosure record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tech_disclosure
     *
     * @mbg.generated Tue Jun 12 14:31:02 CST 2018
     */
    @Update({
        "update tech_disclosure",
        "set division_sn_code = #{divisionSnCode.divisionSnCode},",
          "disclosurer = #{disclosurer,jdbcType=VARCHAR},",
          "dis_role = #{disRole,jdbcType=VARCHAR},",
          "dis_date = #{disDate,jdbcType=DATE},",
          "dis_level = #{disLevel,jdbcType=INTEGER},",
          "dis_recipient = #{disRecipient,jdbcType=VARCHAR},",
          "recipient_role = #{recipientRole,jdbcType=VARCHAR},",
          "dis_include = #{disInclude,jdbcType=VARCHAR},",
          "dis_content = #{disContent,jdbcType=LONGVARCHAR},",
          "dis_attach = #{disAttach,jdbcType=LONGVARCHAR}",
        "where disclosure_code = #{disclosureCode,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(TechDisclosure record);


	@Select({
        "select",
        "disclosure_code, division_sn_code, disclosurer, dis_role, dis_date, dis_level, ",
        "dis_recipient, recipient_role, dis_include, dis_content, dis_attach",
        "from tech_disclosure",
        "where dis_date is not null and instr(ifnull(dis_recipient,' '), #{disRecipient,jdbcType=VARCHAR}) = 0 and exists (select 1 from labor b where a.recipient_role=b.laborCompany and b.idcard = #{disRecipient,jdbcType=VARCHAR})\" })"
    })
    @Results({
        @Result(column="disclosure_code", property="disclosureCode", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="division_sn_code", property="divisionSnCode",
    		one=@One(
    				select="com.banry.pscm.persist.dao.EngDivisionMapper.selectByPrimaryKey",
    				fetchType=FetchType.EAGER
    				)),
        @Result(column="disclosurer", property="disclosurer", jdbcType=JdbcType.VARCHAR),
        @Result(column="dis_role", property="disRole", jdbcType=JdbcType.VARCHAR),
        @Result(column="dis_date", property="disDate", jdbcType=JdbcType.DATE),
        @Result(column="dis_level", property="disLevel", jdbcType=JdbcType.INTEGER),
        @Result(column="dis_recipient", property="disRecipient", jdbcType=JdbcType.VARCHAR),
        @Result(column="recipient_role", property="recipientRole", jdbcType=JdbcType.VARCHAR),
        @Result(column="dis_include", property="disInclude", jdbcType=JdbcType.VARCHAR),
        @Result(column="dis_content", property="disContent", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="dis_attach", property="disAttach", jdbcType=JdbcType.LONGVARCHAR)
    })
	List<TechDisclosure> findTechDisclosureByDisclo(String disclo);
	
	
	@Select({
        "select",
        "disclosure_code, division_sn_code, disclosurer, dis_role, dis_date, dis_level, ",
        "dis_recipient, recipient_role, dis_include, dis_content, dis_attach",
        "from tech_disclosure",
        "where instr(ifnull(dis_recipient,' '), #{disRecipient,jdbcType=VARCHAR}) > 0 ",
        "and exists (select 1 from eng_division b, sub_div_work_bill c where a.division_sn_code=b.division_sn_code ",
        "and b.division_sn_code = c.division_sn_code and c.raw_con_map_quan + c.cons_map_sum_vary_quan > getSumFinishByDivSnCode(b.division_sn_code))  "
    })
    @Results({
        @Result(column="disclosure_code", property="disclosureCode", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="division_sn_code", property="divisionSnCode",
    		one=@One(
    				select="com.banry.pscm.persist.dao.EngDivisionMapper.selectByPrimaryKey",
    				fetchType=FetchType.EAGER
    				)),
        @Result(column="disclosurer", property="disclosurer", jdbcType=JdbcType.VARCHAR),
        @Result(column="dis_role", property="disRole", jdbcType=JdbcType.VARCHAR),
        @Result(column="dis_date", property="disDate", jdbcType=JdbcType.DATE),
        @Result(column="dis_level", property="disLevel", jdbcType=JdbcType.INTEGER),
        @Result(column="dis_recipient", property="disRecipient", jdbcType=JdbcType.VARCHAR),
        @Result(column="recipient_role", property="recipientRole", jdbcType=JdbcType.VARCHAR),
        @Result(column="dis_include", property="disInclude", jdbcType=JdbcType.VARCHAR),
        @Result(column="dis_content", property="disContent", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="dis_attach", property="disAttach", jdbcType=JdbcType.LONGVARCHAR)
    })
	List<TechDisclosure> findHisTechDisclosureByDisclo(String disclo);
	
	
	@Select({ "select",
        "disclosure_code, division_sn_code, disclosurer, dis_role, dis_date, dis_level, ",
        "dis_recipient, recipient_role, dis_include, dis_content, dis_attach",
        "from tech_disclosure",
		"where division_sn_code = #{disclosureCode,jdbcType=VARCHAR}" })
	@Results({
        @Result(column="disclosure_code", property="disclosureCode", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="division_sn_code", property="divisionSnCode",
    		one=@One(
    				select="com.banry.pscm.persist.dao.EngDivisionMapper.selectByPrimaryKey",
    				fetchType=FetchType.EAGER
    				)),
        @Result(column="disclosurer", property="disclosurer", jdbcType=JdbcType.VARCHAR),
        @Result(column="dis_role", property="disRole", jdbcType=JdbcType.VARCHAR),
        @Result(column="dis_date", property="disDate", jdbcType=JdbcType.DATE),
        @Result(column="dis_level", property="disLevel", jdbcType=JdbcType.INTEGER),
        @Result(column="dis_recipient", property="disRecipient", jdbcType=JdbcType.VARCHAR),
        @Result(column="recipient_role", property="recipientRole", jdbcType=JdbcType.VARCHAR),
        @Result(column="dis_include", property="disInclude", jdbcType=JdbcType.VARCHAR),
        @Result(column="dis_content", property="disContent", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="dis_attach", property="disAttach", jdbcType=JdbcType.LONGVARCHAR)
    })
	List<TechDisclosure> findTechDisclosureByDisDivSnCode(String disDivCode);
	
	@SelectProvider(type = TechDisclosureSqlProvider.class, method = "findTechDisclosureBySqlWhere")
	@Results({
        @Result(column="division_sn_code", property="divisionSnCode",
    		one=@One(
				select="com.banry.pscm.persist.dao.EngDivisionMapper.selectByPrimaryKey",
				fetchType=FetchType.EAGER
				))
    })
	List<TechDisclosure> findTechDisclosureBySqlWhere(String sqlWhere);
}