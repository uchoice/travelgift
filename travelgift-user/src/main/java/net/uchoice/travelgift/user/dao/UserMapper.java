package net.uchoice.travelgift.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.uchoice.travelgift.user.domain.UserDO;

@Mapper
public interface UserMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table t_vote_his
	 *
	 * @mbggenerated
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table t_vote_his
	 *
	 * @mbggenerated
	 */
	int insert(UserDO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table t_vote_his
	 *
	 * @mbggenerated
	 */
	UserDO selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table t_vote_his
	 *
	 * @mbggenerated
	 */
	int updateByPrimaryKey(UserDO record);

	/**
	 * @param param
	 * @return
	 */
	List<UserDO> queryList(UserDO param);
}